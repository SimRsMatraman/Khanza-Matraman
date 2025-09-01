package whatsapp;

import fungsi.koneksiDB;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;

/**
 * Satu file gabungan:
 * - UI & layout persis seperti SendFileAppLink lama (Type, Phone, Message, File URL/Path, Preview, Send).
 * - Hanya dua mode: "Send File" dan "Send Message".
 * - Pakai toast, loading state, normalisasi & validasi nomor, endpoint dari koneksiDB.
 */
public class WhatsAppSendLAB extends Application {

    // ===== Konfigurasi (dioverride dari koneksiDB pada constructor) =====
    private static String AUTH_USER = "simrs";
    private static String AUTH_PASS = "RotiBakar69";
    private static String BASE_URL  = "http://100.10.1.5:3000";

    // Endpoint (bukan static final agar ikut BASE_URL terbaru)
    private String ENDPOINT_FILE;
    private String ENDPOINT_CHAT;
    private String ENDPOINT_RECONN;

    // ===== State UI =====
    private Stage stage;
    private StackPane rootStack;
    private VBox toastLayer;
    private Button sendButton;
    private CheckBox cbCloseAfterSuccess;

    // Prefill (optional)
    private String prefillPhone;
    private String prefillFileUrl;
    private String prefillTanggal;
    private String prefillNama;

    // Konstanta
    private static final int TOAST_MS = 3000;

    public WhatsAppSendLAB() {
        super();
        try {
            AUTH_USER = koneksiDB.APIWA_USER();
            AUTH_PASS = koneksiDB.APIWA_PASS();
            BASE_URL  = koneksiDB.APIWA_LAB();
        } catch (Exception e) {
            System.out.println("Notif koneksiDB: " + e.getMessage());
        }
        ENDPOINT_FILE   = BASE_URL + "/send/file";
        ENDPOINT_CHAT   = BASE_URL + "/send/message";
        ENDPOINT_RECONN = BASE_URL + "/app/reconnect";
    }

    // API untuk prefill dari luar
    public void setPrefillData(String tanggal, String nama, String phone, String fileUrl) {
        this.prefillPhone   = phone;
        this.prefillFileUrl = fileUrl;
        this.prefillTanggal = tanggal;
        this.prefillNama    = nama;
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        primaryStage.setTitle("Send File or Message");

        // Checkbox tutup otomatis setelah sukses kirim
        cbCloseAfterSuccess = new CheckBox("Tutup setelah berhasil kirim");
        cbCloseAfterSuccess.setSelected(true);

        // === Komponen sesuai layout lama ===
        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Send File", "Send Message");
        typeCombo.setValue("Send File");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone");

        // Label info normalisasi
        Label normalizedPreview = new Label("Nomor akan dikirim sebagai: -");
        normalizedPreview.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 11px;");
        phoneField.textProperty().addListener((obs, o, n) -> {
            // ijinkan angka saja pada input
            if (!n.matches("\\d*")) phoneField.setText(n.replaceAll("[^\\d]", ""));
            String normalized = normalizePhone(phoneField.getText());
            normalizedPreview.setText("Nomor akan dikirim sebagai: " + (normalized.isEmpty() ? "-" : normalized));
        });

        TextArea captionArea = new TextArea();
        captionArea.setPromptText("Pesan (optional)");

        TextArea messageArea = new TextArea();
        messageArea.setPromptText("Type your message here");
        messageArea.setVisible(false);   // default tidak tampil karena default "Send File"
        messageArea.setManaged(false);

        Label fileUrlLabel = new Label("File URL / Path");
        TextField fileUrlField = new TextField();
        fileUrlField.setPromptText("Enter file URL or local path");

        Button previewBtn = new Button("Preview File");
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
                showToast("Phone number is required!", true, false);
                return;
            }
            String phone = normalizePhone(rawPhone);
            if (!isValidMsisdn(phone)) {
                showToast("Nomor tidak valid.\nContoh: 0812xxxx → 62812xxxx (total 10–15 digit).", true, false);
                return;
            }

            boolean isFile = typeCombo.getValue().equals("Send File");
            setLoading(true);

            if (isFile) {
                String fileUrl = fileUrlField.getText();
                if (fileUrl == null || fileUrl.isEmpty()) {
                    setLoading(false);
                    showToast("Phone and file URL are required!", true, false);
                    return;
                }
                runAsync(() -> {
                    try { sendFileFromUrl(phone, captionArea.getText(), fileUrl); }
                    finally { setLoading(false); }
                });
            } else { // Send Message
                String message = messageArea.getText();
                if (message == null || message.isEmpty()) {
                    setLoading(false);
                    showToast("Message is required!", true, false);
                    return;
                }
                runAsync(() -> {
                    try { sendChat(phone, message); }
                    finally { setLoading(false); }
                });
            }
        });

        // Root lama
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

        // Toast layer
        rootStack = new StackPane(contentRoot);
        toastLayer = new VBox(6);
        toastLayer.setMouseTransparent(true);
        toastLayer.setPadding(new Insets(0, 12, 12, 0));
        StackPane.setAlignment(toastLayer, Pos.BOTTOM_RIGHT);
        rootStack.getChildren().add(toastLayer);

        // Listener untuk ganti layout saat combo berubah (sesuai versi lama)
        typeCombo.setOnAction(e -> {
            boolean isFile = typeCombo.getValue().equals("Send File");

            // Show/hide captionArea (untuk file) dan kontrol preview
            captionArea.setVisible(isFile);
            captionArea.setManaged(isFile);
            fileUrlField.setVisible(isFile);
            fileUrlField.setManaged(isFile);
            previewBtn.setVisible(isFile);
            previewBtn.setManaged(isFile);
            fileUrlLabel.setVisible(isFile);
            fileUrlLabel.setManaged(isFile);

            // Show/hide messageArea (untuk chat)
            messageArea.setVisible(!isFile);
            messageArea.setManaged(!isFile);

            if (isFile) messageArea.clear();
            else captionArea.clear();
        });

        // Prefill
        if (prefillPhone != null)    phoneField.setText(prefillPhone);
        if (prefillFileUrl != null)  fileUrlField.setText(prefillFileUrl);
        if (prefillNama != null) {
            captionArea.setText(
                "Yth Bp/Ibu/Sdr " + prefillNama + ".\n" +
                "Berikut kami kirimkan hasil pemeriksaan laboratorium anda pada tanggal " + (prefillTanggal != null ? prefillTanggal : "-") + ".\n\n" +
                "Pesan ini dikirim secara elektronik, mohon unduh PDF dalam 24 jam setelah anda menerima pesan ini.\n" +
                "Terima kasih."
            );
        }

        // Scene
        Scene scene = new Scene(rootStack, 620, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Reconnect saat form dibuka (tidak blocking)
        reconnect();
    }

    /* =======================
       ------- NETWORK -------
       ======================= */

    private void reconnect() {
        runAsync(() -> {
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL(ENDPOINT_RECONN).openConnection();
                conn.setRequestMethod("GET");
                setBasicAuth(conn, AUTH_USER, AUTH_PASS);
                conn.setConnectTimeout(15000);
                conn.setReadTimeout(60000);
                int code = conn.getResponseCode();
                String body = readBody(conn, code);
                System.out.println("Reconnect (" + code + "): " + body);
            } catch (Exception ex) {
                System.err.println("Reconnect failed: " + ex.getMessage());
            } finally {
                if (conn != null) conn.disconnect();
            }
        });
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

    /** POST /send/file — multipart/form-data, ambil file dari URL (atau path lokal) sebagai stream. */
    private void sendFileFromUrl(String phone, String caption, String fileUrlStr) {
        final String boundary = "===" + System.currentTimeMillis() + "===";
        final String LF = "\r\n";
        HttpURLConnection connection = null;

        try {
            // Siapkan sumber stream
            InputStream fileStream;
            String fileName;
            String contentType = "application/octet-stream";

            // Coba treat sebagai URL dahulu
            try {
                URL src = new URL(fileUrlStr);
                fileStream = src.openStream();
                fileName = new File(src.getPath()).getName();
                if (fileName.toLowerCase().endsWith(".pdf")) contentType = "application/pdf";
            } catch (Exception notUrl) {
                // Jika bukan URL, treat sebagai path lokal
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

            // Koneksi ke endpoint
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

                // File part
                writer.append("--").append(boundary).append(LF);
                writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"")
                      .append(fileName).append("\"").append(LF);
                writer.append("Content-Type: ").append(contentType).append(LF);
                writer.append(LF).flush();

                byte[] buffer = new byte[8192];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                }
                outputStream.flush();

                writer.append(LF).flush();
                writer.append("--").append(boundary).append("--").append(LF).flush();
            }

            // Respons
            int status = connection.getResponseCode();
            String responseBody = readBody(connection, status);

            if (status == HttpURLConnection.HTTP_OK) {
                showToast("Pesan WhatsApp berhasil dikirim", false, false);
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

    private static void runAsync(Runnable r) {
        Thread t = new Thread(r, "send-wa-thread");
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

    /** Ambil field "message" dari JSON error body; jika tidak ada, kembalikan null. */
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

    /** Toast pojok kanan bawah (auto-close 3 detik). */
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

            toastLayer.getChildren().add(box);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(150), box);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            PauseTransition stay = new PauseTransition(Duration.millis(TOAST_MS));

            FadeTransition fadeOut = new FadeTransition(Duration.millis(220), box);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(e -> {
                toastLayer.getChildren().remove(box);
                if (closeAfter && stage != null) stage.close();
            });

            fadeIn.setOnFinished(e -> stay.play());
            stay.setOnFinished(e -> fadeOut.play());
            fadeIn.play();
        });
    }

    /** Ubah tombol Send -> loading ON/OFF (disable + teks). */
    private void setLoading(boolean loading) {
        Platform.runLater(() -> {
            sendButton.setDisable(loading);
            sendButton.setText(loading ? "Sending..." : "Send");
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

    // Escape sederhana JSON
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
                    if (c < 0x20) sb.append(String.format("\\u%04x", (int)c));
                    else sb.append(c);
            }
        }
        return sb.toString();
    }

    /* =======================
       ----- PREVIEWER -------
       ======================= */

    /** Inner static class untuk preview (gabungan dari FilePreviewer lama). */
    private static class FilePreviewer {
        static void showPreview(String fileUrl) {
            try {
                // Gambar yang didukung
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
                // PDF → buka di browser default
                if (fileUrl.matches("(?i).*\\.(pdf)$")) {
                    try {
                        java.awt.Desktop.getDesktop().browse(new URI(fileUrl));
                        return;
                    } catch (Exception openEx) {
                        // fallback kecil
                        Stage previewStage = new Stage();
                        WebView fallback = new WebView();
                        fallback.getEngine().loadContent("<h3>Could not open PDF in browser</h3>");
                        previewStage.setScene(new Scene(fallback, 600, 200));
                        previewStage.show();
                        return;
                    }
                }
                // Tipe lain → HTML sederhana
                Stage previewStage = new Stage();
                WebView webView = new WebView();
                webView.getEngine().loadContent(
                    "<html><body style='font-family:sans-serif'><h3>Preview not supported for this file type.</h3>" +
                    "<p><a href='" + fileUrl + "' target='_blank'>Open File</a></p></body></html>"
                );
                previewStage.setScene(new Scene(webView, 600, 200));
                previewStage.show();
            } catch (Exception ex) {
                Platform.runLater(() -> {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Preview error: " + ex.getMessage());
                    a.show();
                });
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
