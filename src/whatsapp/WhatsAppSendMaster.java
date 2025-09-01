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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;

public class WhatsAppSendMaster extends Application {

    // ===== Konfigurasi (akan dioverride di constructor) =====
    private static String AUTH_USER = "simrs";
    private static String AUTH_PASS = "RotiBakar69";
    private static String BASE_URL  = "http://100.10.1.5:3000";

    // Endpoint (bukan static final agar ikut BASE_URL terbaru)
    private String ENDPOINT_FILE;
    private String ENDPOINT_IMAGE;
    private String ENDPOINT_RECONN;
    private String ENDPOINT_CHAT;

    // ===== State UI =====
    private File selectedFile;       // Kirim File
    private File selectedImageFile;  // Kirim Foto
    private Stage stage;

    private StackPane rootStack;
    private VBox toastLayer;
    private Button sendButton;             // untuk loading state
    private CheckBox cbCloseAfterSuccess;  // pilihan tutup setelah sukses
    private ImageView photoPreview;        // preview image

    // Prefill
    private String prefillNama;
    private String prefillPhone;

    // Konstanta
    private static final long MAX_BYTES_5MB = 5L * 1024 * 1024;
    private static final int  TOAST_MS = 3000;

    public WhatsAppSendMaster() {
        super();
        try {
            AUTH_USER = koneksiDB.APIWA_USER();
            AUTH_PASS = koneksiDB.APIWA_PASS();
            BASE_URL  = koneksiDB.APIWA_LAB();
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
        ENDPOINT_FILE   = BASE_URL + "/send/file";
        ENDPOINT_IMAGE  = BASE_URL + "/send/image";
        ENDPOINT_RECONN = BASE_URL + "/app/reconnect";
        ENDPOINT_CHAT   = BASE_URL + "/send/message";
    }

    public void setPrefillData(String nama, String phone) {
        this.prefillNama  = nama;
        this.prefillPhone = phone;
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        primaryStage.setTitle("Kirim WhatsApp");

        // Reconnect saat form dibuka
        reconnect();

        // ===== Baris paling atas: opsi close-after-success =====
        cbCloseAfterSuccess = new CheckBox("Tutup setelah berhasil kirim");
        cbCloseAfterSuccess.setSelected(true);

        // Pilihan mode
        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Kirim Chat", "Kirim File", "Kirim Foto");
        typeCombo.setValue("Kirim Chat");

        // Phone + preview normalisasi
        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone (hanya angka)");
        Label normalizedPreview = new Label("Nomor akan dikirim sebagai: -");
        normalizedPreview.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 11px;");
        phoneField.textProperty().addListener((obs, o, n) -> {
            if (!n.matches("\\d*")) phoneField.setText(n.replaceAll("[^\\d]", ""));
            String normalized = normalizePhone(phoneField.getText());
            normalizedPreview.setText("Nomor akan dikirim sebagai: " + (normalized.isEmpty() ? "-" : normalized));
        });

        // Label dinamis
        Label formLabel = new Label("Pesan (Kirim Chat)");

        // ==== Kirim Chat ====
        TextArea messageArea = new TextArea();
        messageArea.setPromptText("Tulis pesan chat di sini");

        // ==== Kirim File ====
        TextArea captionArea = new TextArea();
        captionArea.setPromptText("Caption (opsional)");
        Label fileLabel = new Label("No file selected");
        Button uploadButton = new Button("Upload file");
        uploadButton.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            File f = fc.showOpenDialog(primaryStage);
            if (f != null) {
                if (f.length() > MAX_BYTES_5MB) {
                    showToast("Ukuran file maksimal 5 MB!", true, false);
                    return;
                }
                selectedFile = f;
                fileLabel.setText("Selected file: " + f.getName());
            }
        });

        VBox fileUploadBox = new VBox(5, uploadButton, fileLabel);
        fileUploadBox.setPadding(new Insets(10, 0, 10, 0));

        // ==== Kirim Foto ====
        TextArea photoCaptionArea = new TextArea();
        photoCaptionArea.setPromptText("Caption (opsional)");
        CheckBox cbViewOnce = new CheckBox("View Once");
        CheckBox cbCompress = new CheckBox("Compress");

        Label imageLabel = new Label("No image selected");
        Button imageUploadBtn = new Button("Upload image");
        imageUploadBtn.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif", "*.webp")
            );
            File f = fc.showOpenDialog(primaryStage);
            if (f != null) {
                if (f.length() > MAX_BYTES_5MB) {
                    showToast("Ukuran gambar maksimal 5 MB!", true, false);
                    return;
                }
                selectedImageFile = f;
                imageLabel.setText("Selected image: " + f.getName());
                updatePhotoPreview(f);
            }
        });

        // Tombol Snip Screen — minimize sebelum snip, restore sesudahnya (termasuk jika batal)
        Button snipButton = new Button("Snip Screen");
        snipButton.setOnAction(ev -> {
            if (stage != null) stage.setIconified(true);
            // Pastikan restore dilakukan di callback—baik sukses maupun batal.
            SnipCapture.capture(stage, file -> {
                try {
                    if (file != null) {
                        if (file.length() > MAX_BYTES_5MB) {
                            Platform.runLater(() -> showToast("Cuplikan layar maksimal 5 MB!", true, false));
                            return;
                        }
                        selectedImageFile = file;
                        Platform.runLater(() -> {
                            imageLabel.setText("Selected image: " + file.getName());
                            updatePhotoPreview(file);
                            showToast("Cuplikan layar berhasil diambil", false, false);
                        });
                    }
                } finally {
                    Platform.runLater(() -> {
                        if (stage != null) {
                            stage.setIconified(false);
                            stage.toFront();
                            stage.requestFocus();
                        }
                    });
                }
            });
        });

        // Image preview
        photoPreview = new ImageView();
        photoPreview.setFitWidth(260);
        photoPreview.setFitHeight(260);
        photoPreview.setPreserveRatio(true);
        photoPreview.setSmooth(true);
        photoPreview.setStyle("-fx-background-color:#f1f5f9; -fx-padding:6; -fx-border-color:#cbd5e1; -fx-border-radius:10; -fx-background-radius:10;");
        photoPreview.setVisible(false);
        photoPreview.setManaged(false);

        HBox photoActions = new HBox(8, imageUploadBtn, snipButton);
        photoActions.setAlignment(Pos.CENTER_LEFT);

        VBox photoBox = new VBox(8,
                photoCaptionArea, cbViewOnce, cbCompress,
                photoActions, imageLabel, photoPreview
        );
        photoBox.setPadding(new Insets(10, 0, 10, 0));

        // Tombol kirim
        sendButton = new Button("Send");
        sendButton.setOnAction(e -> {
            String rawPhone = phoneField.getText();
            if (rawPhone.isEmpty()) {
                showToast("Nomor handphone wajib diisi!", true, false);
                return;
            }
            String phone = normalizePhone(rawPhone);
            if (!isValidMsisdn(phone)) {
                showToast("Nomor tidak valid.\nContoh: 0812xxxx → 62812xxxx (total 10–15 digit).", true, false);
                return;
            }

            String mode = typeCombo.getValue();
            setLoading(true);  // mulai loading

            switch (mode) {
                case "Kirim File":
                    if (selectedFile == null) {
                        setLoading(false);
                        showToast("File belum dipilih!", true, false);
                        return;
                    }
                    runAsync(() -> {
                        try { sendFile(phone, captionArea.getText(), selectedFile); }
                        finally { setLoading(false); }
                    });
                    break;

                case "Kirim Foto":
                    if (selectedImageFile == null) {
                        setLoading(false);
                        showToast("Gambar belum dipilih!", true, false);
                        return;
                    }
                    boolean viewOnce = cbViewOnce.isSelected();
                    boolean compress = cbCompress.isSelected();
                    runAsync(() -> {
                        try { sendImage(phone, photoCaptionArea.getText(), viewOnce, selectedImageFile, compress); }
                        catch (Exception ex) { showToast("Gagal mengirim foto: " + ex.getMessage(), true, false); }
                        finally { setLoading(false); }
                    });
                    break;

                default: // Kirim Chat
                    String msg = messageArea.getText();
                    if (msg.isEmpty()) {
                        setLoading(false);
                        showToast("Pesan chat wajib diisi!", true, false);
                        return;
                    }
                    runAsync(() -> {
                        try { sendChat(phone, msg); }
                        finally { setLoading(false); }
                    });
            }
        });

        // Konten utama
        VBox topRow = new VBox(6, cbCloseAfterSuccess);
        VBox mainRoot = new VBox(10,
            topRow,
            new Label("Type"), typeCombo,
            phoneField,
            normalizedPreview,
            formLabel,
            messageArea,                 // chat
            captionArea, fileUploadBox,  // file
            photoBox,                    // foto
            sendButton
        );
        mainRoot.setPadding(new Insets(15));

        // StackPane untuk overlay toast
        rootStack = new StackPane(mainRoot);
        toastLayer = new VBox(6);
        toastLayer.setMouseTransparent(true);
        toastLayer.setPadding(new Insets(0, 12, 12, 0));
        StackPane.setAlignment(toastLayer, Pos.BOTTOM_RIGHT);
        rootStack.getChildren().add(toastLayer);

        // Visibilitas awal
        setFormVisibility(typeCombo.getValue(), formLabel, messageArea, captionArea, fileUploadBox, photoBox);

        // Saat ganti mode
        typeCombo.setOnAction(e -> {
            setFormVisibility(typeCombo.getValue(), formLabel, messageArea, captionArea, fileUploadBox, photoBox);
            if ("Kirim Chat".equals(typeCombo.getValue())) {
                selectedFile = null;
                fileLabel.setText("No file selected");
                clearPhotoSelection(imageLabel);
            } else if ("Kirim File".equals(typeCombo.getValue())) {
                clearPhotoSelection(imageLabel);
            } else { // Kirim Foto
                selectedFile = null;
                fileLabel.setText("No file selected");
            }
        });

        // Prefill
        if (prefillPhone != null) phoneField.setText(prefillPhone);
        if (prefillNama != null) {
            String salam = "Yth Bp/Ibu/Sdr " + prefillNama + ".\n\n\n\nTerima kasih.";
            captionArea.setText(salam);
            messageArea.setText(salam);
            photoCaptionArea.setText(salam);
        }

        primaryStage.setScene(new Scene(rootStack, 460, 690));
        primaryStage.show();
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

    /** POST /send/message (JSON). */
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

    /** POST /send/file (multipart/form-data). */
    private void sendFile(String phone, String caption, File file) {
        final String boundary = "===" + System.currentTimeMillis() + "===";
        final String LF = "\r\n";
        HttpURLConnection connection = null;

        try {
            if (file == null || !file.exists() || !file.isFile()) {
                showToast("File tidak ditemukan atau tidak valid", true, false);
                return;
            }

            String nameLower = file.getName().toLowerCase();
            String contentType = Files.probeContentType(file.toPath());
            if (contentType == null || contentType.isBlank()) {
                contentType = nameLower.endsWith(".pdf") ? "application/pdf" : "application/octet-stream";
            } else if (nameLower.endsWith(".pdf")) {
                contentType = "application/pdf";
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
                 InputStream fileIn = new BufferedInputStream(new FileInputStream(file))) {

                addFormField(writer, boundary, "phone", phone, LF);
                addFormField(writer, boundary, "caption", caption, LF);

                writer.append("--").append(boundary).append(LF);
                writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"")
                      .append(file.getName()).append("\"").append(LF);
                writer.append("Content-Type: ").append(contentType).append(LF);
                writer.append(LF).flush();

                byte[] buffer = new byte[8192];
                int read;
                while ((read = fileIn.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                }
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

    /** POST /send/image (multipart/form-data). */
    private void sendImage(String phone /* nomor normal */, String caption, boolean viewOnce, File imageFile, boolean compress) throws Exception {
        String boundary = "----JavaFormBoundary" + System.currentTimeMillis();
        HttpURLConnection conn = (HttpURLConnection) new URL(ENDPOINT_IMAGE).openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(60000);
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        setBasicAuth(conn, AUTH_USER, AUTH_PASS);

        try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
            writeFormField(out, boundary, "phone", phone);
            writeFormField(out, boundary, "caption", caption == null ? "" : caption);
            writeFormField(out, boundary, "view_once", String.valueOf(viewOnce));
            writeFileField(out, boundary, "image", imageFile);
            writeFormField(out, boundary, "compress", String.valueOf(compress));
            out.writeBytes("--" + boundary + "--\r\n");
            out.flush();
        }

        int code = conn.getResponseCode();
        String body = readBody(conn, code);

        if (code == HttpURLConnection.HTTP_OK) {
            showToast("Pesan WhatsApp berhasil dikirim", false, cbCloseAfterSuccess.isSelected());
        } else {
            String msg = extractMessageFromJson(body);
            showToast("Response (" + code + "): " + (msg != null ? msg : body), true, false);
        }
        conn.disconnect();
    }

    /* =======================
       -------- HELPERS ------
       ======================= */

    private static void runAsync(Runnable r) {
        Thread t = new Thread(r, "wa-send-thread");
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

    private static void writeFormField(DataOutputStream out, String boundary, String name, String value) throws IOException {
        out.writeBytes("--" + boundary + "\r\n");
        out.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"\r\n\r\n");
        out.writeBytes((value != null ? value : "") + "\r\n");
    }

    private static void writeFileField(DataOutputStream out, String boundary, String name, File file) throws IOException {
        String fileName = file.getName();
        String mime = Files.probeContentType(file.toPath());
        if (mime == null) mime = "application/octet-stream";

        out.writeBytes("--" + boundary + "\r\n");
        out.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + fileName + "\"\r\n");
        out.writeBytes("Content-Type: " + mime + "\r\n\r\n");

        try (InputStream is = new FileInputStream(file)) {
            byte[] buf = new byte[8192];
            int r;
            while ((r = is.read(buf)) != -1) out.write(buf, 0, r);
        }
        out.writeBytes("\r\n");
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

    /** Toast custom: pojok kanan bawah, auto-close 3 detik; close form jika closeAfter=true */
    private void showToast(String message, boolean isError, boolean closeAfter) {
        Platform.runLater(() -> {
            Label lbl = new Label(message);
            lbl.setWrapText(true);
            lbl.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

            HBox box = new HBox(lbl);
            box.setPadding(new Insets(10, 12, 10, 12));
            box.setMaxWidth(360);
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

    /** Ubah tombol Send -> loading ON/OFF (disable + teks) */
    private void setLoading(boolean loading) {
        Platform.runLater(() -> {
            sendButton.setDisable(loading);
            sendButton.setText(loading ? "Sending..." : "Send");
        });
    }

    // === Visibilitas UI ===
    private void setFormVisibility(
            String mode,
            Label formLabel,
            TextArea messageArea,
            TextArea captionArea,
            VBox fileUploadBox,
            VBox photoBox
    ) {
        boolean isChat  = "Kirim Chat".equals(mode);
        boolean isFile  = "Kirim File".equals(mode);
        boolean isPhoto = "Kirim Foto".equals(mode);

        formLabel.setText(
            isChat  ? "Pesan (Kirim Chat)" :
            isFile  ? "Caption (Kirim File)" :
                      "Caption (Kirim Foto)"
        );

        messageArea.setVisible(isChat);
        messageArea.setManaged(isChat);

        captionArea.setVisible(isFile);
        captionArea.setManaged(isFile);
        fileUploadBox.setVisible(isFile);
        fileUploadBox.setManaged(isFile);

        photoBox.setVisible(isPhoto);
        photoBox.setManaged(isPhoto);
    }

    // Helpers preview foto
    private void updatePhotoPreview(File file) {
        try {
            Image img = new Image(file.toURI().toString(), 260, 260, true, true, true);
            photoPreview.setImage(img);
            photoPreview.setVisible(true);
            photoPreview.setManaged(true);
        } catch (Exception ignored) {
            photoPreview.setImage(null);
            photoPreview.setVisible(false);
            photoPreview.setManaged(false);
        }
    }

    private void clearPhotoSelection(Label imageLabel) {
        selectedImageFile = null;
        imageLabel.setText("No image selected");
        photoPreview.setImage(null);
        photoPreview.setVisible(false);
        photoPreview.setManaged(false);
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

    public static void main(String[] args) {
        launch(args);
    }
}
