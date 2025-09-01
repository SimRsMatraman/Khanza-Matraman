package whatsapp;

import fungsi.koneksiDB;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;

public class WhatsAppSendLAB extends Application {

    // ===== Konfigurasi (dioverride oleh koneksiDB di constructor) =====
    private static String AUTH_USER = "simrs";
    private static String AUTH_PASS = "RotiBakar69";
    private static String BASE_URL  = "http://100.10.1.5:3000";

    // Endpoint (bukan static final agar ikut BASE_URL terbaru)
    private String ENDPOINT_FILE;
    private String ENDPOINT_CHAT;
    private String ENDPOINT_RECONN;
    private String ENDPOINT_LOGIN;
    private String ENDPOINT_DEVICES;
    private String ENDPOINT_LOGOUT;

    // ===== Root & Sidebar =====
    private Stage stage;
    private BorderPane root;
    private VBox sideBar;
    private Button tabLoginBtn, tabSendBtn;
    private StackPane contentStack;
    private Pane loginPane, sendPane;

    // ===== Toast Layer (per pane) =====
    private VBox toastLayerLogin;
    private VBox toastLayerSend;
    private static final int TOAST_MS = 3000;

    // ======= LOGIN PANE =======
    private Label statusLabel;
    private ImageView qrImage;
    private ProgressBar qrProgressBar;
    private Button refreshBtn, reconnectBtn, logoutBtn;
    private VBox loginCard;
    private Timeline qrTimeline;

    // ======= SEND PANE =======
    private CheckBox cbCloseAfterSuccess;
    private ComboBox<String> typeCombo;
    private TextField phoneField;
    private Label normalizedPreview;
    private TextArea captionArea;
    private TextArea messageArea;
    private Label fileUrlLabel;
    private TextField fileUrlField;
    private Button previewBtn;
    private Button sendButton;

    // ===== Prefill (opsional) untuk SEND =====
    private String prefillPhone;
    private String prefillFileUrl;
    private String prefillTanggal;
    private String prefillNama;

    public WhatsAppSendLAB() {
        super();
        try {
            AUTH_USER = koneksiDB.APIWA_USER();
            AUTH_PASS = koneksiDB.APIWA_PASS();
            BASE_URL  = koneksiDB.APIWA_LAB();
        } catch (Exception e) {
            System.out.println("Notif koneksiDB: " + e.getMessage());
        }
        ENDPOINT_FILE    = BASE_URL + "/send/file";
        ENDPOINT_CHAT    = BASE_URL + "/send/message";
        ENDPOINT_RECONN  = BASE_URL + "/app/reconnect";
        ENDPOINT_LOGIN   = BASE_URL + "/app/login";
        ENDPOINT_DEVICES = BASE_URL + "/app/devices";
        ENDPOINT_LOGOUT  = BASE_URL + "/app/logout";
    }

    // Prefill untuk SEND (opsional)
    public void setPrefillData(String tanggal, String nama, String phone, String fileUrl) {
        this.prefillPhone   = phone;
        this.prefillFileUrl = fileUrl;
        this.prefillTanggal = tanggal;
        this.prefillNama    = nama;
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        stage.setTitle("WhatsApp RSUD Matraman");

        // ===== Root lebih dulu =====
        root = new BorderPane();

        // ===== Sidebar (2 tab kiri) =====
        sideBar = new VBox(8);
        sideBar.setPadding(new Insets(12));
        sideBar.setPrefWidth(140);
        sideBar.setStyle("-fx-background-color:#0f172a;");

        tabLoginBtn = new Button("Login");
        tabSendBtn  = new Button("Kirim WA");
        for (Button b : new Button[]{tabLoginBtn, tabSendBtn}) {
            b.setMaxWidth(Double.MAX_VALUE);
            b.setStyle(
                "-fx-background-color:#1e293b; -fx-text-fill:white; -fx-font-weight:bold; " +
                "-fx-background-radius:10; -fx-padding:10 12;"
            );
        }
        tabLoginBtn.setOnAction(e -> showLoginPane());
        tabSendBtn.setOnAction(e -> showSendPane());
        sideBar.getChildren().addAll(tabLoginBtn, tabSendBtn);
        root.setLeft(sideBar);

        // ===== Content stack =====
        contentStack = new StackPane();
        contentStack.setStyle("-fx-background-color:#f8fafc;");
        root.setCenter(contentStack);

        // ===== Init PANE: LOGIN & SEND =====
        initLoginPane();
        initSendPane();

        // Default: ke LOGIN lalu pindah ke Kirim (opsional)
        contentStack.getChildren().addAll(loginPane, sendPane);
        setOnly(loginPane, true);
        setOnly(sendPane, false);
        styleActiveTab(tabLoginBtn, tabSendBtn);
        showSendPane();

        // Scene
        Scene scene = new Scene(root, 920, 600);
        stage.setScene(scene);
        stage.show();

        // Mulai aksi awal
        reconnectLogin();
        refreshQR();
    }

    /* ----------------------
       PANE: LOGIN (kiri)
       ---------------------- */
    private void initLoginPane() {
        statusLabel = new Label("Please scan to connect");

        qrImage = new ImageView();
        qrImage.setFitWidth(250);
        qrImage.setFitHeight(250);

        qrProgressBar = new ProgressBar(1.0);
        qrProgressBar.setPrefWidth(250);
        qrProgressBar.setStyle("-fx-accent: #22c55e;"); // hijau awal

        refreshBtn = new Button("Refresh QR Code");
        refreshBtn.setOnAction(e -> refreshQR());

        reconnectBtn = new Button("Reconnect");
        reconnectBtn.setVisible(true);
        reconnectBtn.setOnAction(e -> reconnectLogin());

        logoutBtn = new Button("Logout");
        logoutBtn.setVisible(false);
        logoutBtn.setStyle("-fx-background-color:#dc2626; -fx-text-fill:white;");
        logoutBtn.setOnAction(e -> logout());

        loginCard = new VBox(15, statusLabel, qrImage, qrProgressBar, refreshBtn, reconnectBtn, logoutBtn);
        loginCard.setAlignment(Pos.CENTER);
        loginCard.setStyle("-fx-padding: 20; -fx-background-color: white; -fx-background-radius: 15;");
        loginCard.setEffect(new DropShadow(10, Color.GRAY));

        // ==== TOAST LAYER untuk LOGIN (kanan-atas card) ====
        toastLayerLogin = new VBox(6);
        toastLayerLogin.setMouseTransparent(true);
        toastLayerLogin.setFillWidth(false);
        toastLayerLogin.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        StackPane.setAlignment(toastLayerLogin, Pos.TOP_RIGHT);
        StackPane.setMargin(toastLayerLogin, new Insets(12, 12, 0, 0));

        // Tumpuk card + toast
        StackPane loginStack = new StackPane(loginCard);
        loginStack.getChildren().add(toastLayerLogin);

        VBox wrapper = new VBox(loginStack);
        wrapper.setAlignment(Pos.CENTER);
        wrapper.setPadding(new Insets(20));

        loginPane = wrapper;
    }

    private void reconnectLogin() {
        runAsync(() -> {
            try {
                getJsonFromUrl(ENDPOINT_RECONN); // trigger reconnect
            } catch (Exception e) {
                Platform.runLater(() -> updateLoginStatus("Error reconnect: " + e.getMessage()));
            }
        });
    }

    private void refreshQR() {
        if (qrTimeline != null) qrTimeline.stop();

        runAsync(() -> {
            try {
                JSONObject data = getJsonFromUrl(ENDPOINT_LOGIN);

                if (data.has("code") && "ALREADY_LOGGED_IN".equals(data.getString("code"))) {
                    Platform.runLater(() -> {
                        updateLoginStatus("⚠️ Anda sudah login");
                        qrImage.setImage(null);
                        qrProgressBar.setVisible(false);
                        qrProgressBar.setStyle("-fx-accent: #22c55e;");
                        refreshBtn.setVisible(false);
                        logoutBtn.setVisible(true);
                        reconnectBtn.setVisible(true);
                        getDevices();
                    });
                    return;
                }

                if (data.has("code") && "SUCCESS".equals(data.getString("code"))) {
                    JSONObject results = data.getJSONObject("results");
                    String qrLink = results.optString("qr_link", "");
                    int duration = results.optInt("qr_duration", 20);

                    Platform.runLater(() -> {
                        if (!qrLink.isEmpty()) {
                            qrImage.setImage(new Image(qrLink, true));
                            fadeInQR();
                        } else {
                            qrImage.setImage(null);
                        }
                        qrProgressBar.setVisible(true);
                        qrProgressBar.setProgress(1.0);
                        qrProgressBar.setStyle("-fx-accent:#22c55e;");
                        refreshBtn.setVisible(true);
                        logoutBtn.setVisible(false);
                        reconnectBtn.setVisible(true);

                        qrTimeline = new Timeline(
                                new KeyFrame(Duration.ZERO, e -> qrProgressBar.setProgress(1.0)),
                                new KeyFrame(Duration.seconds(duration), e -> refreshQR())
                        );
                        qrTimeline.setCycleCount(1);

                        qrTimeline.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
                            double progress = 1.0 - newTime.toSeconds() / duration;
                            qrProgressBar.setProgress(progress);
                            if (progress > 0.5)        qrProgressBar.setStyle("-fx-accent:#22c55e;");
                            else if (progress > 0.25)  qrProgressBar.setStyle("-fx-accent:#facc15;");
                            else                       qrProgressBar.setStyle("-fx-accent:#dc2626;");
                        });

                        qrTimeline.play();
                    });
                } else {
                    Platform.runLater(() -> {
                        try { updateLoginStatus("Error: " + data.getString("message")); }
                        catch (JSONException ex) { updateLoginStatus("Error refresh QR"); }
                    });
                }
            } catch (Exception e) {
                Platform.runLater(() -> updateLoginStatus("Error fetching QR: " + e.getMessage()));
            }
        });
    }

    private void getDevices() {
        runAsync(() -> {
            try {
                JSONObject data = getJsonFromUrl(ENDPOINT_DEVICES);
                if ("SUCCESS".equals(data.optString("code"))) {
                    JSONArray devices = data.optJSONArray("results");
                    if (devices != null && devices.length() > 0) {
                        JSONObject device = devices.getJSONObject(0);
                        String name = device.optString("name", "-");
                        String dev  = device.optString("device", "-");
                        Platform.runLater(() -> {
                            qrImage.setImage(null);
                            qrProgressBar.setProgress(0);
                            updateLoginStatus("✅ Anda sudah login\nName: " + name + "\nDevice: " + dev);
                        });
                    }
                }
            } catch (Exception e) {
                Platform.runLater(() -> updateLoginStatus("Error getDevices: " + e.getMessage()));
            }
        });
    }

    private void logout() {
        runAsync(() -> {
            try {
                JSONObject data = getJsonFromUrl(ENDPOINT_LOGOUT);
                if ("SUCCESS".equals(data.optString("code"))) {
                    Platform.runLater(() -> {
                        updateLoginStatus("Logout berhasil!");
                        qrImage.setImage(null);
                        qrProgressBar.setVisible(false);
                        qrProgressBar.setStyle("-fx-accent:#22c55e;");
                        refreshBtn.setVisible(true);
                        logoutBtn.setVisible(false);
                        reconnectBtn.setVisible(true);
                        refreshQR();

                        PauseTransition pause = new PauseTransition(Duration.seconds(1));
                        pause.setOnFinished(e -> updateLoginStatus("Please scan to connect"));
                        pause.play();
                    });
                } else {
                    Platform.runLater(() -> updateLoginStatus("Gagal logout"));
                }
            } catch (Exception e) {
                Platform.runLater(() -> updateLoginStatus("Error logout: " + e.getMessage()));
            }
        });
    }

    private void fadeInQR() {
        FadeTransition ft = new FadeTransition(Duration.millis(500), qrImage);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    private void updateLoginStatus(String text) {
        if (statusLabel != null) statusLabel.setText(text);
    }

    /* ----------------------
       PANE: SEND (kanan)
       ---------------------- */
    private void initSendPane() {
        cbCloseAfterSuccess = new CheckBox("Tutup setelah berhasil kirim");
        cbCloseAfterSuccess.setSelected(true);

        typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Send File", "Send Message");
        typeCombo.setValue("Send File");

        phoneField = new TextField();
        phoneField.setPromptText("Phone");

        normalizedPreview = new Label("Nomor akan dikirim sebagai: -");
        normalizedPreview.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 11px;");
        phoneField.textProperty().addListener((obs, o, n) -> {
            if (!n.matches("\\d*")) phoneField.setText(n.replaceAll("[^\\d]", ""));
            String normalized = normalizePhone(phoneField.getText());
            normalizedPreview.setText("Nomor akan dikirim sebagai: " + (normalized.isEmpty() ? "-" : normalized));
        });

        captionArea = new TextArea();
        captionArea.setPromptText("Pesan (optional)");

        messageArea = new TextArea();
        messageArea.setPromptText("Type your message here");
        messageArea.setVisible(false);
        messageArea.setManaged(false);

        fileUrlLabel = new Label("File URL / Path");
        fileUrlField = new TextField();
        fileUrlField.setPromptText("Enter file URL or local path");

        previewBtn = new Button("Preview File");
        previewBtn.setOnAction(e -> {
            String fileUrl = fileUrlField.getText();
            if (fileUrl != null && !fileUrl.isEmpty()) {
                FilePreviewer.showPreview(fileUrl);
            }
        });

        sendButton = new Button("Send");
        sendButton.setOnAction(e -> {
            String rawPhone = phoneField.getText();
            if (rawPhone == null || rawPhone.isEmpty()) {
                showToast("Nomor handphone wajib diisi!", true, false);
                return;
            }
            String phone = normalizePhone(rawPhone);
            if (!isValidMsisdn(phone)) {
                showToast("Nomor tidak valid.\nContoh: 0812xxxx → 62812xxxx (total 10–15 digit).", true, false);
                return;
            }

            boolean isFile = "Send File".equals(typeCombo.getValue());
            setLoading(true);

            if (isFile) {
                String fileUrl = fileUrlField.getText();
                if (fileUrl == null || fileUrl.isEmpty()) {
                    setLoading(false);
                    showToast("Nomor handphone and file URL wajib diisi!", true, false);
                    return;
                }
                runAsync(() -> {
                    try { sendFileFromUrl(phone, captionArea.getText(), fileUrl); }
                    finally { setLoading(false); }
                });
            } else {
                String message = messageArea.getText();
                if (message == null || message.isEmpty()) {
                    setLoading(false);
                    showToast("Message wajib diisi!", true, false);
                    return;
                }
                runAsync(() -> {
                    try { sendChat(phone, message); }
                    finally { setLoading(false); }
                });
            }
        });

        VBox contentRoot = new VBox(10,
            cbCloseAfterSuccess,
            new Label("Type"), typeCombo,
            new Label("Phone"), phoneField,
            normalizedPreview,
            new Label("Message"), captionArea, messageArea,
            fileUrlLabel, fileUrlField,
            previewBtn,
            sendButton
        );
        contentRoot.setPadding(new Insets(15));

        // Toggle file/chat
        typeCombo.setOnAction(e -> {
            boolean isFile = "Send File".equals(typeCombo.getValue());
            captionArea.setVisible(isFile);
            captionArea.setManaged(isFile);
            fileUrlField.setVisible(isFile);
            fileUrlField.setManaged(isFile);
            previewBtn.setVisible(isFile);
            previewBtn.setManaged(isFile);
            fileUrlLabel.setVisible(isFile);
            fileUrlLabel.setManaged(isFile);

            messageArea.setVisible(!isFile);
            messageArea.setManaged(!isFile);

            if (isFile) messageArea.clear();
            else captionArea.clear();
        });

        // Prefill (jika ada)
        if (prefillPhone != null)   phoneField.setText(prefillPhone);
        if (prefillFileUrl != null) fileUrlField.setText(prefillFileUrl);
        if (prefillNama != null) {
            captionArea.setText(
                "Yth Bp/Ibu/Sdr " + prefillNama + ".\n" +
                "Berikut kami kirimkan hasil pemeriksaan laboratorium anda pada tanggal " +
                (prefillTanggal != null ? prefillTanggal : "-") + ".\n\n" +
                "Pesan ini dikirim secara elektronik, mohon unduh PDF dalam 24 jam setelah anda menerima pesan ini.\n" +
                "Terima kasih."
            );
        }

        // ==== TOAST LAYER untuk SEND (kanan-atas area konten) ====
        toastLayerSend = new VBox(6);
        toastLayerSend.setMouseTransparent(true);
        toastLayerSend.setFillWidth(false);
        toastLayerSend.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        StackPane.setAlignment(toastLayerSend, Pos.TOP_RIGHT);
        StackPane.setMargin(toastLayerSend, new Insets(12, 12, 0, 0));

        // Tumpuk konten + toast
        StackPane wrapper = new StackPane(contentRoot);
        wrapper.getChildren().add(toastLayerSend);
        wrapper.setPadding(new Insets(20));
        sendPane = wrapper;
    }

    private void showLoginPane() {
        if (qrTimeline != null) qrTimeline.stop();
        setOnly(loginPane, true);
        setOnly(sendPane, false);
        styleActiveTab(tabLoginBtn, tabSendBtn);
        refreshQR(); // opsional
    }

    private void showSendPane() {
        if (qrTimeline != null) qrTimeline.stop();
        setOnly(sendPane, true);
        setOnly(loginPane, false);
        styleActiveTab(tabSendBtn, tabLoginBtn);
    }

    private void setOnly(Pane p, boolean on) {
        p.setVisible(on);
        p.setManaged(on);
        if (on) p.toFront();
    }

    private void styleActiveTab(Button active, Button inactive) {
        active.setStyle("-fx-background-color:#22c55e; -fx-text-fill:white; -fx-font-weight:bold; -fx-background-radius:10; -fx-padding:10 12;");
        inactive.setStyle("-fx-background-color:#1e293b; -fx-text-fill:white; -fx-font-weight:bold; -fx-background-radius:10; -fx-padding:10 12;");
    }

    /* =======================
       ------- NETWORK -------
       ======================= */

    private JSONObject getJsonFromUrl(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        setBasicAuth(con, AUTH_USER, AUTH_PASS);
        con.setConnectTimeout(15000);
        con.setReadTimeout(60000);

        int status = con.getResponseCode();
        InputStream in = (status >= 200 && status < 300) ? con.getInputStream() : con.getErrorStream();

        StringBuilder response = new StringBuilder();
        if (in != null) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) response.append(line);
            }
        }
        con.disconnect();
        return new JSONObject(response.toString());
    }

    /** POST /send/message — JSON */
    private void sendChat(String phone, String message) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(ENDPOINT_CHAT);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            setBasicAuth(connection, AUTH_USER, AUTH_PASS);
            connection.setDoOutput(true);

            String json = String.format("{\"phone\":\"%s\",\"message\":\"%s\"}",
                    jsonEscape(phone), jsonEscape(message));
            try (OutputStream os = connection.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }

            int status = connection.getResponseCode();
            String responseBody = readBody(connection, status);

            if (status == HttpURLConnection.HTTP_OK) {
                showToast("Pesan WhatsApp berhasil dikirim", false, cbCloseAfterSuccess.isSelected());
            } else {
                String msg = extractMessageFromJson(responseBody);
                showToast("Response (" + status + "): " + (msg != null ? msg : responseBody), true, false);
            }
        } catch (Exception e) {
            showToast("Error: " + e.getMessage(), true, false);
        } finally {
            if (connection != null) connection.disconnect();
        }
    }

    /** POST /send/file — multipart/form-data (stream dari URL atau path lokal). */
    private void sendFileFromUrl(String phone, String caption, String fileUrlStr) {
        final String boundary = "===" + System.currentTimeMillis() + "===";
        final String LF = "\r\n";
        HttpURLConnection connection = null;

        try {
            InputStream fileStream;
            String fileName;
            String contentType = "application/octet-stream";

            try {
                URL src = new URL(fileUrlStr);
                fileStream = src.openStream();
                fileName = new File(src.getPath()).getName();
                if (fileName.toLowerCase().endsWith(".pdf")) contentType = "application/pdf";
            } catch (Exception notUrl) {
                File f = new File(fileUrlStr);
                if (!f.exists() || !f.isFile()) {
                    showToast("File tidak ditemukan: " + fileUrlStr, true, false);
                    return;
                }
                fileStream = new BufferedInputStream(new FileInputStream(f));
                fileName = f.getName();
                String probe = null;
                try { probe = Files.probeContentType(f.toPath()); } catch (IOException ignored) {}
                if (probe != null && !probe.isBlank()) contentType = probe;
                if (fileName.toLowerCase().endsWith(".pdf")) contentType = "application/pdf";
            }

            URL url = new URL(ENDPOINT_FILE);
            connection = (HttpURLConnection) url.openConnection();
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            setBasicAuth(connection, AUTH_USER, AUTH_PASS);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            try (OutputStream outputStream = connection.getOutputStream();
                 PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);
                 InputStream in = fileStream) {

                addFormField(writer, boundary, "phone", phone, LF);
                addFormField(writer, boundary, "caption", caption, LF);

                writer.append("--").append(boundary).append(LF);
                writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"")
                      .append(fileName).append("\"").append(LF);
                writer.append("Content-Type: ").append(contentType).append(LF);
                writer.append(LF).flush();

                byte[] buffer = new byte[8192];
                int read;
                while ((read = in.read(buffer)) != -1) outputStream.write(buffer, 0, read);
                outputStream.flush();

                writer.append(LF).flush();
                writer.append("--").append(boundary).append("--").append(LF).flush();
            }

            int status = connection.getResponseCode();
            String responseBody = readBody(connection, status);

            if (status == HttpURLConnection.HTTP_OK) {
                showToast("Pesan WhatsApp berhasil dikirim", false, cbCloseAfterSuccess.isSelected());
            } else {
                String msg = extractMessageFromJson(responseBody);
                showToast("Response (" + status + "): " + (msg != null ? msg : responseBody), true, false);
            }
        } catch (Exception ex) {
            showToast("Error: " + ex.getMessage(), true, false);
        } finally {
            if (connection != null) connection.disconnect();
        }
    }

    /* =======================
       -------- HELPERS ------
       ======================= */

    // Escape sederhana JSON (untuk body POST)
    private static String jsonEscape(String s) {
        if (s == null) return "";
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\\': sb.append("\\\\"); break;
                case '"':  sb.append("\\\""); break;
                case '\b': sb.append("\\b");  break;
                case '\f': sb.append("\\f");  break;
                case '\n': sb.append("\\n");  break;
                case '\r': sb.append("\\r");  break;
                case '\t': sb.append("\\t");  break;
                default:
                    if (c < 0x20) sb.append(String.format("\\u%04x", (int) c));
                    else sb.append(c);
            }
        }
        return sb.toString();
    }

    private static void runAsync(Runnable r) {
        Thread t = new Thread(r, "wa-suite-thread");
        t.setDaemon(true);
        t.start();
    }

    private static void setBasicAuth(HttpURLConnection conn, String user, String pass) {
        String token = Base64.getEncoder().encodeToString((user + ":" + pass).getBytes(StandardCharsets.UTF_8));
        conn.setRequestProperty("Authorization", "Basic " + token);
    }

    private static String readBody(HttpURLConnection conn, int code) {
        try (InputStream is = (code >= 200 && code < 300) ? conn.getInputStream() : conn.getErrorStream()) {
            if (is == null) return "";
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                StringBuilder sb = new StringBuilder();
                String s;
                while ((s = br.readLine()) != null) sb.append(s);
                return sb.toString();
            }
        } catch (IOException e) {
            return "";
        }
    }

    private static void addFormField(PrintWriter writer, String boundary, String name, String value, String LF) {
        writer.append("--").append(boundary).append(LF);
        writer.append("Content-Disposition: form-data; name=\"").append(name).append("\"").append(LF);
        writer.append("Content-Type: text/plain; charset=UTF-8").append(LF);
        writer.append(LF);
        writer.append(value != null ? value : "").append(LF);
        writer.flush();
    }

    /** Ambil field "message" dari JSON error body; jika tidak ada, null. */
    private static String extractMessageFromJson(String body) {
        try {
            if (body == null) return null;
            int i = body.indexOf("\"message\"");
            if (i == -1) return null;
            int colon = body.indexOf(':', i);
            if (colon == -1) return null;
            int firstQuote = body.indexOf('"', colon + 1);
            if (firstQuote == -1) return null;
            int secondQuote = body.indexOf('"', firstQuote + 1);
            if (secondQuote == -1) return null;
            String msg = body.substring(firstQuote + 1, secondQuote);
            return msg.replaceAll("@s\\.whatsapp\\.net", "");
        } catch (Exception e) {
            return null;
        }
    }

    /** Toast kanan–atas card/tab aktif (auto-close 3 detik). */
    private void showToast(String message, boolean isError, boolean closeAfter) {
        Platform.runLater(() -> {
            Label lbl = new Label(message);
            lbl.setWrapText(true);
            lbl.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

            HBox box = new HBox(lbl);
            box.setPadding(new Insets(10, 12, 10, 12));
            box.setMaxWidth(420);
            box.setStyle(
                "-fx-background-color: " + (isError ? "#dc2626" : "#16a34a") + ";" +
                "-fx-background-radius: 10;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 10, 0.2, 0, 2);"
            );

            // pilih layer sesuai tab aktif
            VBox layer = (sendPane != null && sendPane.isVisible()) ? toastLayerSend : toastLayerLogin;
            if (layer == null) return;
            layer.getChildren().add(box);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(150), box);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            PauseTransition stay = new PauseTransition(Duration.millis(TOAST_MS));

            FadeTransition fadeOut = new FadeTransition(Duration.millis(220), box);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(e -> layer.getChildren().remove(box));

            fadeIn.setOnFinished(e -> stay.play());
            stay.setOnFinished(e -> fadeOut.play());
            fadeIn.play();

            if (closeAfter && stage != null) {
                PauseTransition closeLater = new PauseTransition(Duration.millis(TOAST_MS + 240));
                closeLater.setOnFinished(ev -> stage.close());
                closeLater.play();
            }
        });
    }

    /** Loading state tombol Send. */
    private void setLoading(boolean loading) {
        Platform.runLater(() -> {
            if (sendButton != null) {
                sendButton.setDisable(loading);
                sendButton.setText(loading ? "Sending..." : "Send");
            }
        });
    }

    // Utils nomor
    private static String normalizePhone(String input) {
        if (input == null) return "";
        String digits = input.replaceAll("\\D+", "");
        if (digits.isEmpty()) return "";
        if (digits.startsWith("0"))  return "62" + digits.substring(1);
        if (digits.startsWith("62")) return digits;
        if (digits.startsWith("8"))  return "62" + digits;
        return digits;
    }

    private static boolean isValidMsisdn(String msisdn) {
        return msisdn != null && msisdn.matches("^62\\d{8,13}$");
    }

    /* =======================
       ----- PREVIEWER -------
       ======================= */

    /** Previewer sederhana (gambar & PDF) */
    private static class FilePreviewer {
        static void showPreview(String fileUrl) {
            try {
                if (fileUrl.matches("(?i).*\\.(png|jpg|jpeg|gif|bmp|webp)$")) {
                    Stage previewStage = new Stage();
                    previewStage.setTitle("File Preview");
                    Image image = new Image(fileUrl, true);
                    ImageView imageView = new ImageView(image);
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(800);
                    imageView.setFitHeight(600);
                    StackPane pane = new StackPane(imageView);
                    previewStage.setScene(new Scene(pane, 800, 600));
                    previewStage.show();
                    return;
                }
                if (fileUrl.matches("(?i).*\\.(pdf)$")) {
                    try {
                        java.awt.Desktop.getDesktop().browse(new URI(fileUrl));
                        return;
                    } catch (Exception openEx) {
                        Stage previewStage = new Stage();
                        WebView fallback = new WebView();
                        fallback.getEngine().loadContent("<h3>Could not open PDF in browser</h3>");
                        previewStage.setScene(new Scene(fallback, 600, 200));
                        previewStage.show();
                        return;
                    }
                }
                Stage previewStage = new Stage();
                WebView webView = new WebView();
                webView.getEngine().loadContent(
                    "<html><body style='font-family:sans-serif'><h3>Preview not supported for this file type.</h3>" +
                    "<p><a href='" + fileUrl + "' target='_blank'>Open File</a></p></body></html>"
                );
                previewStage.setScene(new Scene(webView, 600, 200));
                previewStage.show();
            } catch (Exception ex) {
                Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "Preview error: " + ex.getMessage()).show());
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
