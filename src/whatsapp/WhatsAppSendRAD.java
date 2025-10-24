package whatsapp;

import fungsi.koneksiDB;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;

//QR
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.time.*;
import java.time.format.*;

// PDFBox
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

public class WhatsAppSendRAD extends Application {

    // ======= Konfigurasi WA (dioverride koneksiDB) =======
    private static String AUTH_USER = "simrs";
    private static String AUTH_PASS = "RotiBakar69";
    private static String BASE_URL = "http://100.10.1.5:3200";

    // Endpoint WA
    private String ENDPOINT_FILE;
    private String ENDPOINT_CHAT;
    private String ENDPOINT_RECONN;
    private String ENDPOINT_LOGIN;
    private String ENDPOINT_DEVICES;
    private String ENDPOINT_LOGOUT;
    private String ENDPOINT_IMAGE;

    // ======= Konfigurasi API Website =======
    private String API_WEBSITE_BASE = "https://rsudmatraman.my.id/api-website";
    private String API_WEBSITE_KEY = "Administrator86";
    private String API_RAD_KUNJUNGAN;
    private String API_RAD_LOGSEND;
    private String API_RAD_UPLOAD;
    private String API_RAD_SAVEHASIL;
    private String API_DOKTER_LIST;

    // ======= Orthanc =======
    private String ORTHANC_BASE = "https://dicom.rsudmatraman.my.id";
    private String ORTHANC_LINK_DOKTER = "https://dicom.rsudmatraman.my.id";
    private String ORTHANC_USER = "orthanc";
    private String ORTHANC_PASS = "orthanc";

    // ======= Root & Sidebar =======
    private Stage stage;
    private BorderPane root;
    private VBox sideBar;
    private Button tabLoginBtn, tabSendBtn, tabRadBtn;
    private StackPane contentStack;
    private Pane loginPane, sendPane, radPane;

    // ======= Toast Layer =======
    private VBox toastLayerLogin, toastLayerSend, toastLayerRad;
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

    // ======= ICONS =======
    private Image iconSend16;

    // ======= DATA RAD =======
    private DatePicker dpRadStart, dpRadEnd;
    private TextField tfRadSearch;
    private Button btnRadLoad;
    private TableView<RadiologiRow> tvRad;
    private ObservableList<RadiologiRow> radData = FXCollections.observableArrayList();

    // ======= Prefill meta untuk logging (WA) =======
    private String logNoRawat, logTglPeriksa, logJam, logNoRM, logNama, logNoTelp;

    public WhatsAppSendRAD() {
        super();
        try {
            // WA
            AUTH_USER = koneksiDB.APIWA_USER();
            AUTH_PASS = koneksiDB.APIWA_PASS();
            BASE_URL = koneksiDB.APIWA_RAD();

            // Website
            API_WEBSITE_BASE = koneksiDB.APIWEBSITE_BASE();
            API_WEBSITE_KEY = koneksiDB.APIWEBSITE_KEY();

            // Orthanc
            ORTHANC_BASE = koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC();
            ORTHANC_USER = koneksiDB.USERORTHANC();
            ORTHANC_PASS = koneksiDB.PASSORTHANC();
        } catch (Throwable ignore) {
        }

        ENDPOINT_FILE = BASE_URL + "/send/file";
        ENDPOINT_CHAT = BASE_URL + "/send/message";
        ENDPOINT_RECONN = BASE_URL + "/app/reconnect";
        ENDPOINT_LOGIN = BASE_URL + "/app/login";
        ENDPOINT_DEVICES = BASE_URL + "/app/devices";
        ENDPOINT_LOGOUT = BASE_URL + "/app/logout";
        ENDPOINT_IMAGE = BASE_URL + "/send/image";

        API_RAD_KUNJUNGAN = API_WEBSITE_BASE + "/rad/kunjungan";
        API_RAD_LOGSEND = API_WEBSITE_BASE + "/rad/log-send";
        API_RAD_UPLOAD = API_WEBSITE_BASE + "/rad/upload";
        API_RAD_SAVEHASIL = API_WEBSITE_BASE + "/rad/hasil/save";
        API_DOKTER_LIST = API_WEBSITE_BASE + "/list-dokter";
    }

    // ======= MODEL =======
    public static class RadiologiRow {

        public String no_rawat, nm_pasien, no_rkm_medis, tgl_periksa, jam, png_jawab;
        public String nm_perawatan;
        public String hasil_mask, hasil_exp, umur, jk, nm_dokter, tgl_lahir, nm_petugas;   // "Sudah"/"Belum"
        public boolean sent;
        public String log_sent_at;
        public int retry_count;
        public String no_telp;
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        // Load icon window
        try {
            stage.getIcons().add(new Image("https://rsudmatraman.my.id/upload/image/whatsapp.png"));
        } catch (Throwable ignore) {
        }

        // Load icon send (paper plane)
        try {
            URL r = getClass().getResource("/icons/paper-plane-16.png");
            if (r != null) {
                iconSend16 = new Image(r.toExternalForm(), 16, 16, true, true);
            }
        } catch (Throwable ignore) {
        }

        stage.setTitle("WhatsApp Radiologi RSUD Matraman");

        // Root
        root = new BorderPane();

        // Sidebar
        sideBar = new VBox(8);
        sideBar.setPadding(new Insets(12));
        sideBar.setPrefWidth(140);
        sideBar.setStyle("-fx-background-color:#0f172a;");

        tabLoginBtn = new Button("Login");
        tabSendBtn = new Button("Kirim WA");
        tabRadBtn = new Button("Kunjungan");

        for (Button b : new Button[]{tabLoginBtn, tabSendBtn, tabRadBtn}) {
            b.setMaxWidth(Double.MAX_VALUE);
            b.setStyle("-fx-background-color:#1e293b; -fx-text-fill:white; -fx-font-weight:bold; -fx-background-radius:10; -fx-padding:10 12;");
        }
        if (iconSend16 != null) {
            ImageView iv = new ImageView(iconSend16);
            tabSendBtn.setGraphic(iv);
            tabSendBtn.setContentDisplay(ContentDisplay.LEFT);
        }
        tabLoginBtn.setOnAction(e -> showLoginPane());
        tabSendBtn.setOnAction(e -> showSendPane());
        tabRadBtn.setOnAction(e -> showRadPane());

        sideBar.getChildren().addAll(tabLoginBtn, tabSendBtn, tabRadBtn);
        root.setLeft(sideBar);

        // Content
        contentStack = new StackPane();
        contentStack.setStyle("-fx-background-color:#f8fafc;");
        root.setCenter(contentStack);

        // Init panes
        initLoginPane();
        initSendPane();
        initRadPane();

        contentStack.getChildren().addAll(loginPane, sendPane, radPane);
        setOnly(loginPane, true);
        setOnly(sendPane, false);
        setOnly(radPane, false);
        styleActiveTab(tabLoginBtn, tabSendBtn, tabRadBtn);

        // Scene
        Scene scene = new Scene(root, 1080, 600);
        // Enter to search (global) ketika tab RAD aktif
        scene.setOnKeyPressed(ev -> {
            if (ev.getCode() == KeyCode.ENTER && radPane.isVisible() && !btnRadLoad.isDisabled()) {
                btnRadLoad.fire();
                ev.consume();
            }
        });
        stage.setScene(scene);
        stage.show();

        // Login & QR awal
        reconnectLogin();
        refreshQR();
        // default membuka tab Kunjungan RAD
        showRadPane();
    }

    /* ---------------------- LOGIN PANE ---------------------- */
    private void initLoginPane() {
        statusLabel = new Label("Please scan to connect");

        qrImage = new ImageView();
        qrImage.setFitWidth(250);
        qrImage.setFitHeight(250);

        qrProgressBar = new ProgressBar(1.0);
        qrProgressBar.setPrefWidth(250);
        qrProgressBar.setStyle("-fx-accent: #22c55e;");

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

        toastLayerLogin = new VBox(6);
        toastLayerLogin.setMouseTransparent(true);
        toastLayerLogin.setFillWidth(false);
        toastLayerLogin.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        StackPane.setAlignment(toastLayerLogin, Pos.TOP_RIGHT);
        StackPane.setMargin(toastLayerLogin, new Insets(12, 12, 0, 0));

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
                getJsonFromUrl(ENDPOINT_RECONN);
            } catch (Exception e) {
                Platform.runLater(() -> updateLoginStatus("Error reconnect: " + e.getMessage()));
            }
        });
    }

    private void refreshQR() {
        if (qrTimeline != null) {
            qrTimeline.stop();
        }
        runAsync(() -> {
            try {
                JSONObject data = getJsonFromUrl(ENDPOINT_LOGIN);

                if (data.has("code") && "ALREADY_LOGGED_IN".equals(data.getString("code"))) {
                    Platform.runLater(() -> {
                        updateLoginStatus("⚠️ Anda sudah login");
                        qrImage.setImage(null);
                        qrProgressBar.setVisible(false);
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
                        });

                        qrTimeline.play();
                    });
                } else {
                    Platform.runLater(() -> updateLoginStatus("Error: " + data.optString("message", "refresh QR")));
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
                        String dev = device.optString("device", "-");
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
                        refreshBtn.setVisible(true);
                        logoutBtn.setVisible(false);
                        reconnectBtn.setVisible(true);
                        refreshQR();
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
        if (statusLabel != null) {
            statusLabel.setText(text);
        }
    }

    private void showLoginPane() {
        if (qrTimeline != null) {
            qrTimeline.stop();
        }
        setOnly(loginPane, true);
        setOnly(sendPane, false);
        setOnly(radPane, false);
        styleActiveTab(tabLoginBtn, tabSendBtn, tabRadBtn);
        refreshQR();
    }

    private void showSendPane() {
        if (qrTimeline != null) {
            qrTimeline.stop();
        }
        setOnly(sendPane, true);
        setOnly(loginPane, false);
        setOnly(radPane, false);
        styleActiveTab(tabSendBtn, tabLoginBtn, tabRadBtn);
    }

    private void showRadPane() {
        if (qrTimeline != null) {
            qrTimeline.stop();
        }
        setOnly(radPane, true);
        setOnly(loginPane, false);
        setOnly(sendPane, false);
        styleActiveTab(tabRadBtn, tabLoginBtn, tabSendBtn);
    }

    private void setOnly(Pane p, boolean on) {
        p.setVisible(on);
        p.setManaged(on);
        if (on) {
            p.toFront();
        }
    }

    private void styleActiveTab(Button active, Button other1, Button other2) {
        Button[] arr = new Button[]{active, other1, other2};
        for (int i = 0; i < arr.length; i++) {
            Button b = arr[i];
            boolean isAct = (i == 0);
            b.setStyle(isAct
                    ? "-fx-background-color:#22c55e; -fx-text-fill:white; -fx-font-weight:bold; -fx-background-radius:10; -fx-padding:10 12;"
                    : "-fx-background-color:#1e293b; -fx-text-fill:white; -fx-font-weight:bold; -fx-background-radius:10; -fx-padding:10 12;");
        }
    }

    /* ---------------------- SEND PANE ---------------------- */
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
            if (!n.matches("\\d*")) {
                phoneField.setText(n.replaceAll("[^\\d]", ""));
            }
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
                    showToast("Nomor & file URL wajib diisi!", true, false);
                    return;
                }
                runAsync(() -> {
                    try {
                        sendFileFromUrl(phone, captionArea.getText(), fileUrl);
                    } finally {
                        setLoading(false);
                    }
                });
            } else {
                String message = messageArea.getText();
                if (message == null || message.isEmpty()) {
                    setLoading(false);
                    showToast("Message wajib diisi!", true, false);
                    return;
                }
                runAsync(() -> {
                    try {
                        sendChat(phone, message);
                    } finally {
                        setLoading(false);
                    }
                });
            }
        });

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
            if (isFile) {
                messageArea.clear();
            } else {
                captionArea.clear();
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

        toastLayerSend = new VBox(6);
        toastLayerSend.setMouseTransparent(true);
        toastLayerSend.setFillWidth(false);
        toastLayerSend.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        StackPane.setAlignment(toastLayerSend, Pos.TOP_RIGHT);
        StackPane.setMargin(toastLayerSend, new Insets(12, 12, 0, 0));

        StackPane wrapper = new StackPane(contentRoot);
        wrapper.getChildren().add(toastLayerSend);
        wrapper.setPadding(new Insets(20));
        sendPane = wrapper;
    }

    /* ---------------------- RAD PANE ---------------------- */
    private void initRadPane() {
        dpRadStart = new DatePicker(LocalDate.now());
        dpRadEnd = new DatePicker(LocalDate.now());
        tfRadSearch = new TextField();
        tfRadSearch.setPromptText("Cari (No Rawat / RM / Nama / Pemeriksaan)");
        btnRadLoad = new Button("Cari");
        btnRadLoad.setOnAction(e -> loadRad());

        tfRadSearch.setOnAction(e -> btnRadLoad.fire());
//        dpRadStart.setOnAction(e -> btnRadLoad.fire());
        dpRadEnd.setOnAction(e -> btnRadLoad.fire());

        HBox filter = new HBox(8, new Label("Tanggal 1"), dpRadStart, new Label("Tanggal 2"), dpRadEnd, tfRadSearch, btnRadLoad);
        filter.setAlignment(Pos.CENTER_LEFT);
        filter.setPadding(new Insets(10));

        tvRad = new TableView<>();
        tvRad.setItems(radData);
        tvRad.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<RadiologiRow, String> cNoRawat = new TableColumn<>("No Rawat");
        cNoRawat.setCellValueFactory(cd -> new ReadOnlyStringWrapper(safe(cd.getValue().no_rawat)));

        TableColumn<RadiologiRow, String> cNama = new TableColumn<>("Nama Pasien");
        cNama.setCellValueFactory(cd -> new ReadOnlyStringWrapper(safe(cd.getValue().nm_pasien)));

        TableColumn<RadiologiRow, String> cNoRM = new TableColumn<>("No RM");
        cNoRM.setCellValueFactory(cd -> new ReadOnlyStringWrapper(safe(cd.getValue().no_rkm_medis)));

        TableColumn<RadiologiRow, String> cPJ = new TableColumn<>("Cara Bayar");
        cPJ.setCellValueFactory(cd -> new ReadOnlyStringWrapper(safe(cd.getValue().png_jawab)));

        TableColumn<RadiologiRow, String> cTgl = new TableColumn<>("Tanggal");
        cTgl.setCellValueFactory(cd -> new ReadOnlyStringWrapper(safe(cd.getValue().tgl_periksa)));

        TableColumn<RadiologiRow, String> cJam = new TableColumn<>("Jam");
        cJam.setCellValueFactory(cd -> new ReadOnlyStringWrapper(safe(cd.getValue().jam)));

        TableColumn<RadiologiRow, String> cPeriksa = new TableColumn<>("Pemeriksaan");
        cPeriksa.setCellValueFactory(cd -> new ReadOnlyStringWrapper(safe(cd.getValue().nm_perawatan)));

        TableColumn<RadiologiRow, String> cHasil = new TableColumn<>("Hasil");
        cHasil.setCellValueFactory(cd -> new ReadOnlyStringWrapper(safe(cd.getValue().hasil_mask)));

        TableColumn<RadiologiRow, String> cStatus = new TableColumn<>("Status");
        cStatus.setCellValueFactory(cd -> {
            RadiologiRow v = cd.getValue();
            String s = "-";
            if (v != null && v.sent && v.log_sent_at != null && !v.log_sent_at.isBlank()) {
                s = "✓ " + shortDate(v.log_sent_at) + (v.retry_count > 0 ? " (+" + v.retry_count + ")" : "");
            }
            return new ReadOnlyStringWrapper(s);
        });

        TableColumn<RadiologiRow, Void> cAct = new TableColumn<>("Kirim Whatsapp");
        cAct.setCellFactory(col -> new TableCell<RadiologiRow, Void>() {
            private final Button btnPasien = new Button();
            private final Button btnDokter = new Button("Dokter");
            private final HBox box = new HBox(6, btnPasien, btnDokter);

            {
                if (iconSend16 != null) {
                    btnPasien.setGraphic(new ImageView(iconSend16));
                    btnPasien.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    btnPasien.setText("Pasien");
                }
                btnPasien.setTooltip(new Tooltip("Pilih gambar & buat PDF (kirim ke pasien)"));
                btnDokter.setTooltip(new Tooltip("Kirim gambar ke dokter"));

                btnPasien.setMinWidth(50);
                btnDokter.setMinWidth(60);

                btnPasien.setOnAction(e -> {
                    RadiologiRow r = getTableView().getItems().get(getIndex());
                    openRadBuilder(r);
                });
                btnDokter.setOnAction(e -> {
                    RadiologiRow r = getTableView().getItems().get(getIndex());
                    openSendToDoctor(r);
                });
            }

            @Override
            protected void updateItem(Void it, boolean empty) {
                super.updateItem(it, empty);
                setGraphic(empty ? null : box);
            }
        });

        tvRad.getColumns().setAll(cNoRawat, cNama, cNoRM, cPJ, cTgl, cJam, cPeriksa, cHasil, cStatus, cAct);
        cNoRawat.setMinWidth(110);
        cNama.setMinWidth(180);
        cNoRM.setMinWidth(50);
        cPJ.setMinWidth(50);
        cTgl.setMinWidth(60);
        cJam.setMinWidth(50);
        cPeriksa.setMinWidth(90);
        cHasil.setMinWidth(40);
        cStatus.setMinWidth(80);
        cAct.setMinWidth(100);

        toastLayerRad = new VBox(6);
        toastLayerRad.setMouseTransparent(true);
        toastLayerRad.setFillWidth(false);
        toastLayerRad.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        StackPane.setAlignment(toastLayerRad, Pos.TOP_RIGHT);
        StackPane.setMargin(toastLayerRad, new Insets(12, 12, 0, 0));

        BorderPane content = new BorderPane();
        content.setTop(filter);
        content.setCenter(tvRad);

        StackPane wrapper = new StackPane(content);
        wrapper.getChildren().add(toastLayerRad);
        wrapper.setPadding(new Insets(10));
        radPane = wrapper;
    }

    private void loadRad() {
        LocalDate s = (dpRadStart.getValue() != null) ? dpRadStart.getValue() : LocalDate.now();
        LocalDate e = (dpRadEnd.getValue() != null) ? dpRadEnd.getValue() : LocalDate.now();
        String q = (tfRadSearch.getText() != null) ? tfRadSearch.getText().trim() : "";
        String url = API_RAD_KUNJUNGAN + "?start=" + s + "&end=" + e;
        if (!q.isEmpty()) {
            url += "&q=" + encode(q);
        }

        btnRadLoad.setDisable(true);
        final String fUrl = url;

        runAsync(() -> {
            try {
                Map<String, String> headers = new HashMap<>();
                if (API_WEBSITE_KEY != null && !API_WEBSITE_KEY.trim().isEmpty()) {
                    headers.put("X-Api-Key", API_WEBSITE_KEY);
                }
                JSONObject resp = getJsonOpen(fUrl, headers);
                if (!resp.optBoolean("ok")) {
                    String msg = resp.optString("error", "ok=false");
                    Platform.runLater(() -> {
                        btnRadLoad.setDisable(false);
                        showToastRad("API RAD error: " + msg, true);
                    });
                    return;
                }
                JSONArray arr = resp.optJSONArray("data");
                ObservableList<RadiologiRow> tmp = FXCollections.observableArrayList();
                if (arr != null) {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject it = arr.getJSONObject(i);
                        RadiologiRow r = new RadiologiRow();
                        r.no_rawat = it.optString("no_rawat", "");
                        r.nm_pasien = it.optString("nm_pasien", "");
                        r.no_rkm_medis = it.optString("no_rkm_medis", "");
                        r.png_jawab = it.optString("png_jawab", "");
                        r.tgl_periksa = it.optString("tgl_periksa", "");
                        r.jam = it.optString("jam", "");
                        r.nm_perawatan = it.optString("nm_perawatan", "");
                        r.hasil_mask = it.optString("hasil_mask", "-");
                        r.hasil_exp = it.optString("hasil_exp", "");
                        r.umur = it.optString("umur", "");
                        r.jk = it.optString("jk", "");
                        r.tgl_lahir = it.optString("tgl_lahir", "");
                        r.nm_dokter = it.optString("nm_dokter", "");
                        r.nm_petugas = it.optString("nama", "");
                        r.sent = it.optBoolean("sent", false);
                        r.log_sent_at = cleanTs(it.optString("log_sent_at", ""));
                        r.retry_count = it.optInt("retry_count", 0);
                        r.no_telp = it.optString("no_telp", "");
                        tmp.add(r);
                    }
                }
                Platform.runLater(() -> {
                    radData.setAll(tmp);
                    btnRadLoad.setDisable(false);
                    showToastRad("Radiologi dimuat: " + tmp.size() + " baris", false);
                });
            } catch (Exception ex) {
                Platform.runLater(() -> {
                    btnRadLoad.setDisable(false);
                    showToastRad("Gagal muat: " + ex.getMessage(), true);
                });
            }
        });
    }

    private static BufferedImage generateQR(String text, int size) throws Exception {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, size, size);
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        // background putih
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                img.setRGB(x, y, matrix.get(x, y) ? 0x000000 : 0xFFFFFF);
            }
        }
        return img;
    }

    private static class Dokter {

        String nama;
        String phone;

        Dokter(String n, String p) {
            this.nama = n;
            this.phone = p;
        }

        @Override
        public String toString() {
            return nama;
        } // agar tampil nama di ComboBox
    }

    private List<Dokter> fetchDoctors() throws Exception {
        Map<String, String> headers = new HashMap<>();
        if (API_WEBSITE_KEY != null && !API_WEBSITE_KEY.isBlank()) {
            headers.put("X-Api-Key", API_WEBSITE_KEY);
        }
        JSONObject resp = getJsonOpen(API_DOKTER_LIST, headers);
        List<Dokter> out = new ArrayList<>();
        // asumsi bentuk: { ok:true, data:[{nm_dokter:"...", no_telp:"..."}] }
        JSONArray arr = resp.optJSONArray("data");
        if (arr != null) {
            for (int i = 0; i < arr.length(); i++) {
                JSONObject it = arr.getJSONObject(i);
                String nama = it.optString("nm_dokter", "");
                String telp = it.optString("no_telp", "");
                if (!nama.isBlank()) {
                    out.add(new Dokter(nama, telp));
                }
            }
        }
        return out;
    }

    // util multipart
    private static void writeFormField(DataOutputStream out, String boundary, String name, String value) throws IOException {
        out.writeBytes("--" + boundary + "\r\n");
        out.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"\r\n");
        out.writeBytes("Content-Type: text/plain; charset=UTF-8\r\n\r\n");
        byte[] bytes = (value == null ? "" : value).getBytes(StandardCharsets.UTF_8);
        out.write(bytes);                     // <<— tulis sebagai byte[]
        out.writeBytes("\r\n");
    }

    private static void writeFileField(DataOutputStream out, String boundary,
            String fieldName, String mimeType,
            String fileName, byte[] data) throws IOException {
        out.writeBytes("--" + boundary + "\r\n");
        out.writeBytes("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + fileName + "\"\r\n");
        out.writeBytes("Content-Type: " + mimeType + "\r\n\r\n");
        out.write(data);                   // <— tulis bytes mentah
        out.writeBytes("\r\n");
    }


    /* ---------------------- RAD BUILDER (Orthanc → PDF → Upload → Prefill) ---------------------- */
    private static class SelectedImage {

        String instanceId;
        String seriesDesc;
        byte[] bytes;
        boolean selected;

        SelectedImage(String id, String desc, byte[] b, boolean sel) {
            instanceId = id;
            seriesDesc = desc;
            bytes = b;
            selected = sel;
        }
    }

    private static class SelectedImageDokter {

        String instanceId;
        String seriesDesc;
        String seriesId;
        String studyUid;   // ← DICOM StudyInstanceUID (untuk viewer)
        byte[] bytes;
        boolean selected;

        SelectedImageDokter(String id, String desc, byte[] b, boolean sel,
                String seriesId, String studyUid) {
            this.instanceId = id;
            this.seriesDesc = desc;
            this.bytes = b;
            this.selected = sel;
            this.seriesId = seriesId;
            this.studyUid = studyUid;
        }
    }

    private void openSendToDoctor(RadiologiRow row) {
        Stage dlg = new Stage();
        dlg.getIcons().add(new Image("https://rsudmatraman.my.id/upload/image/whatsapp.png"));
        dlg.setTitle("Kirim ke Dokter — " + safe(row.nm_pasien));

        // --- UI atas: pilih dokter + nomor
        ComboBox<Dokter> cbDokter = new ComboBox<>();
        TextField tfPhone = new TextField();
        tfPhone.setPromptText("No. WhatsApp dokter");
        tfPhone.setPrefWidth(240);

        Label loadLbl = new Label("Memuat daftar dokter...");
        loadLbl.setStyle("-fx-text-fill:#64748b;");

        runAsync(() -> {
            try {
                List<Dokter> ds = fetchDoctors();
                Platform.runLater(() -> {
                    cbDokter.getItems().setAll(ds);
                    loadLbl.setText("Pilih dokter.");
                });
            } catch (Exception ex) {
                Platform.runLater(() -> loadLbl.setText("Gagal muat dokter: " + ex.getMessage()));
            }
        });

        cbDokter.valueProperty().addListener((o, ov, nv) -> {
            if (nv != null) {
                tfPhone.setText(normalizePhone(safe(nv.phone)));
            }
        });

        // --- Pesan default
        TextArea taMsg = new TextArea();
        taMsg.setPrefRowCount(5);
        String defMsg
                = "Mohon dibaca expertise radiologi.\n"
                + "Pasien: " + safe(row.nm_pasien) + " (RM " + safe(row.no_rkm_medis) + ")\n"
                + "Tgl lahir: " + safe(row.tgl_lahir) + "   Umur: " + safe(row.umur) + "\n"
                + "Pemeriksaan: " + safe(row.nm_perawatan) + "\n"
                + "Terima kasih.";
        taMsg.setText(defMsg);

        // --- Area gambar (pakai mekanisme Orthanc yang sama)
        FlowPane grid = new FlowPane(8, 8);
        grid.setPadding(new Insets(10));
        Button btnLoadImg = new Button("Muat Gambar");
        Button btnSend = new Button("Kirim");
        Label info = new Label("Pilih gambar yang akan dikirim.");

        List<SelectedImageDokter> selected = new ArrayList<>();

        btnLoadImg.setOnAction(e -> {
            btnLoadImg.setDisable(true);
            runAsync(() -> {
                try {
                    String yyyymmdd = row.tgl_periksa.replace("-", "");
                    JSONObject req = new JSONObject()
                            .put("Level", "Study").put("Expand", true)
                            .put("Query", new JSONObject()
                                    .put("StudyDate", yyyymmdd + "-" + yyyymmdd)
                                    .put("PatientID", row.no_rkm_medis));

                    HttpURLConnection con = (HttpURLConnection) new URL(ORTHANC_BASE + "/tools/find").openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setRequestProperty("Authorization", basicAuthHeader(ORTHANC_USER, ORTHANC_PASS));
                    con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    try (OutputStream os = con.getOutputStream()) {
                        os.write(req.toString().getBytes(StandardCharsets.UTF_8));
                    }
                    int status = con.getResponseCode();
                    String body = readBody(con, status);
                    con.disconnect();
                    if (status < 200 || status >= 300) {
                        throw new IOException("Find HTTP " + status + ": " + body);
                    }

                    JSONArray studies = new JSONArray(body);
                    List<SelectedImageDokter> all = new ArrayList<>();
                    String auth = basicAuthHeader(ORTHANC_USER, ORTHANC_PASS);

                    for (int i = 0; i < studies.length(); i++) {
                        JSONObject st = studies.getJSONObject(i);
                        JSONArray seriesArr = st.optJSONArray("Series");
                        if (seriesArr == null) {
                            continue;
                        }

                        for (int sidx = 0; sidx < seriesArr.length(); sidx++) {
                            String seriesId = seriesArr.getString(sidx);
                            JSONObject series = getJsonWithAuth(ORTHANC_BASE + "/series/" + seriesId, auth);

                            JSONObject mt = series.optJSONObject("MainDicomTags");
                            String seriesDesc = (mt != null ? mt.optString("SeriesDescription", "Series") : "Series");
                            String studyUid = (mt != null ? mt.optString("StudyInstanceUID", null) : null);

                            // fallback kalau StudyInstanceUID tidak ada di level series
                            if (studyUid == null || studyUid.isBlank()) {
                                String parentStudy = series.optString("ParentStudy", null);
                                if (parentStudy != null) {
                                    JSONObject studyJson = getJsonWithAuth(ORTHANC_BASE + "/studies/" + parentStudy, auth);
                                    JSONObject mtStudy = studyJson.optJSONObject("MainDicomTags");
                                    if (mtStudy != null) {
                                        studyUid = mtStudy.optString("StudyInstanceUID", null);
                                    }
                                }
                            }

                            JSONArray instArr = series.optJSONArray("Instances");
                            if (instArr == null) {
                                continue;
                            }

                            for (int k = 0; k < instArr.length(); k++) {
                                String instId = instArr.getString(k);
                                byte[] jpg = getBytesWithAuth(ORTHANC_BASE + "/instances/" + instId + "/preview", auth);
                                all.add(new SelectedImageDokter(instId, seriesDesc, jpg, true, seriesId, studyUid));
                            }
                        }
                    }

                    Platform.runLater(() -> {
                        grid.getChildren().clear();
                        selected.clear();
                        selected.addAll(all);
                        for (SelectedImageDokter si : selected) {
                            ImageView iv = new ImageView(new Image(new ByteArrayInputStream(si.bytes)));
                            iv.setFitWidth(180);
                            iv.setPreserveRatio(true);
                            CheckBox cb = new CheckBox((si.seriesDesc == null ? "" : si.seriesDesc)
                                    + " (" + si.instanceId.substring(0, 8) + "…)");
                            cb.setSelected(true);
                            cb.selectedProperty().addListener((ob, ov, nv) -> si.selected = nv);
                            VBox cell = new VBox(iv, cb);
                            cell.setSpacing(4);
                            grid.getChildren().add(cell);
                        }
                        info.setText("Gambar dimuat: " + selected.size());
                        btnLoadImg.setDisable(false);
                    });
                } catch (Exception ex) {
                    Platform.runLater(() -> {
                        info.setText("Gagal muat gambar: " + ex.getMessage());
                        btnLoadImg.setDisable(false);
                    });
                }
            });
        });

        btnSend.setOnAction(e -> {
            String phone = normalizePhone(tfPhone.getText());
            if (!isValidMsisdn(phone)) {
                showToastRad("Nomor dokter tidak valid", true);
                return;
            }

            List<SelectedImageDokter> picks = selected.stream().filter(x -> x.selected).collect(Collectors.toList());
            if (picks.isEmpty()) {
                showToastRad("Pilih minimal 1 gambar.", true);
                return;
            }

            btnSend.setDisable(true);
            runAsync(() -> {
                try {
                    // track study yang sudah dikasih link
                    Set<String> linkedStudies = new HashSet<>();
                    Map<String, byte[]> annotatedCache = new HashMap<>();

                    for (int i = 0; i < picks.size(); i++) {
                        SelectedImageDokter si = picks.get(i);

                        // 1) siapkan link viewer (sekali per StudyInstanceUID)
                        String link = null;
                        if (si.studyUid != null && !si.studyUid.isBlank() && linkedStudies.add(si.studyUid)) {
                            link = buildViewerLink(si.studyUid);
                        }

                        // 2) caption (gambar pertama = pesan + link; berikutnya hanya link jika beda study)
                        String caption = "";
                        if (i == 0) {
                            caption = taMsg.getText().trim();
                            if (link != null) {
                                caption += "\nViewer: " + link;
                            }
                        } else if (link != null) {
                            caption = "Viewer: " + link;
                        }

                        // 3) ambil bytes annotated (fallback: pakai asli bila gagal anotasi)
                        byte[] toSend = si.bytes;
                        try {
                            byte[] ann = annotatedCache.get(si.instanceId);
                            if (ann == null) {
                                InstanceInfo meta = fetchInstanceInfo(si.instanceId);
                                // pakai fungsi annotate kamu (yang posisi header di atas / sesuai versi terakhir)
                                ann = annotateJpegBottomPad(si.bytes, meta);
                                annotatedCache.put(si.instanceId, ann);
                            }
                            toSend = ann;
                        } catch (Exception ignore) {
                            // kalau gagal annotate, tetap kirim gambar asli
                        }

                        // 4) kirim
                        sendSingleImageToDoctor(
                                phone,
                                caption,
                                toSend,
                                "img-" + si.instanceId // ekstensi akan ditambahkan oleh sendSingleImageToDoctor()
                        );
                    }

                    Platform.runLater(() -> {
                        showToastRad("Gambar terkirim ke dokter.", false);
                        dlg.close();
                    });
                } catch (Exception ex) {
                    Platform.runLater(() -> {
                        btnSend.setDisable(false);
                        showToastRad("Gagal kirim: " + ex.getMessage(), true);
                    });
                }
            });
        });

        GridPane form = new GridPane();
        form.setHgap(8);
        form.setVgap(6);
        form.add(new Label("Dokter"), 0, 0);
        form.add(cbDokter, 1, 0);
        form.add(new Label("No. Dokter"), 0, 1);
        form.add(tfPhone, 1, 1);
        form.add(new Label("Message"), 0, 2);
        form.add(taMsg, 1, 2);
        GridPane.setHgrow(taMsg, Priority.ALWAYS);

        VBox rootBox = new VBox(10,
                loadLbl,
                form,
                new HBox(8, btnLoadImg, btnSend),
                new ScrollPane(grid),
                info
        );
        rootBox.setPadding(new Insets(12));
        dlg.setScene(new Scene(rootBox, 960, 720));
        dlg.show();
    }

    private void sendSingleImageToDoctor(String phone, String caption,
            byte[] imageBytes, String baseFileName) throws IOException {
        // Normalisasi dulu agar valid (jpg/png)
        ImagePayload pay = normalizeToAllowedImage(imageBytes);
        String fileName = baseFileName + pay.ext;

        String boundary = "===" + System.currentTimeMillis() + "===";
        URL url = new URL(BASE_URL + "/send/image");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(60000);
        setBasicAuth(conn, AUTH_USER, AUTH_PASS);
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
            writeFormField(out, boundary, "phone", phone);
            if (caption != null && !caption.isBlank()) {
                writeFormField(out, boundary, "caption", caption);
            }
            writeFileField(out, boundary, "image", pay.mime, fileName, pay.data);
            out.writeBytes("--" + boundary + "--\r\n");
            out.flush();
        }

        int code = conn.getResponseCode();
        String body = readBody(conn, code);
        conn.disconnect();
        if (code < 200 || code >= 300) {
            throw new IOException("Send image HTTP " + code + ": " + body);
        }
    }

    private String buildViewerLink(String studyUid) {
        if (studyUid == null || studyUid.isBlank()) {
            return null;
        }
        return ORTHANC_LINK_DOKTER + "/stone-webviewer/index.html?study=" + studyUid;
    }

    private static String buildDesiredPdfName(RadiologiRow r) {
        String base = (r.no_rawat + "_" + r.tgl_periksa + "_" + r.jam)
                .replaceAll("-", "_").replaceAll("[^0-9A-Za-z_]+", "");
        if (!base.toLowerCase().endsWith(".pdf")) {
            base += ".pdf";
        }
        return base;
    }

    private void openRadBuilder(RadiologiRow row) {
        Stage dlg = new Stage();
        dlg.getIcons().add(new Image("https://rsudmatraman.my.id/upload/image/whatsapp.png"));
        dlg.setTitle("Pilih Gambar Radiologi — " + safe(row.nm_pasien));

        TextArea taExpert = new TextArea();
        taExpert.setPromptText("Hasil expertise (opsional, akan ditaruh di PDF)");
        taExpert.setText(row.hasil_exp);

        FlowPane grid = new FlowPane();
        grid.setHgap(8);
        grid.setVgap(8);
        grid.setPadding(new Insets(10));

        CheckBox cbSelectAll = new CheckBox("Pilih semua");
        Button btnLoadImg = new Button("Muat Gambar");
        Button btnCreatePdf = new Button("Buat PDF & Upload & Prefill WA");
        Label info = new Label("Pilih gambar lalu buat PDF");

        VBox rootBox = new VBox(10,
                new Label("No. Rawat: " + row.no_rawat + " | Pasien: " + row.nm_pasien + " | RM: " + row.no_rkm_medis),
                new Label("Tanggal: " + row.tgl_periksa + " " + row.jam + " | Pemeriksaan: " + row.nm_perawatan + " | Hasil SIMRS: " + row.hasil_mask),
                new Label("Expertise:"), taExpert,
                cbSelectAll, btnLoadImg, new ScrollPane(grid),
                info, btnCreatePdf
        );
        rootBox.setPadding(new Insets(12));

        List<SelectedImage> selected = new ArrayList<>();

        btnLoadImg.setOnAction(e -> {
            btnLoadImg.setDisable(true);
            runAsync(() -> {
                try {
                    // POST /tools/find
                    String yyyymmdd = row.tgl_periksa.replace("-", "");
                    JSONObject req = new JSONObject()
                            .put("Level", "Study")
                            .put("Expand", true)
                            .put("Query", new JSONObject()
                                    .put("StudyDate", yyyymmdd + "-" + yyyymmdd)
                                    .put("PatientID", row.no_rkm_medis));

                    HttpURLConnection con = (HttpURLConnection) new URL(ORTHANC_BASE + "/tools/find").openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setRequestProperty("Authorization", basicAuthHeader(ORTHANC_USER, ORTHANC_PASS));
                    con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    try (OutputStream os = con.getOutputStream()) {
                        os.write(req.toString().getBytes(StandardCharsets.UTF_8));
                    }
                    int status = con.getResponseCode();
                    String body = readBody(con, status);
                    con.disconnect();
                    if (status < 200 || status >= 300) {
                        throw new IOException("Find HTTP " + status + ": " + body);
                    }
                    JSONArray studies = new JSONArray(body);

                    List<SelectedImage> all = new ArrayList<>();
                    String auth = basicAuthHeader(ORTHANC_USER, ORTHANC_PASS);

                    for (int i = 0; i < studies.length(); i++) {
                        JSONObject st = studies.getJSONObject(i);
                        JSONArray seriesArr = st.optJSONArray("Series");
                        if (seriesArr == null) {
                            continue;
                        }
                        for (int sidx = 0; sidx < seriesArr.length(); sidx++) {
                            String seriesId = seriesArr.getString(sidx);
                            JSONObject series = getJsonWithAuth(ORTHANC_BASE + "/series/" + seriesId, auth);
                            String seriesDesc = series.optJSONObject("MainDicomTags").optString("SeriesDescription", "Series");
                            JSONArray instArr = series.optJSONArray("Instances");
                            if (instArr == null) {
                                continue;
                            }
                            for (int k = 0; k < instArr.length(); k++) {
                                String instId = instArr.getString(k);
                                byte[] jpg = getBytesWithAuth(ORTHANC_BASE + "/instances/" + instId + "/preview", auth);
                                all.add(new SelectedImage(instId, seriesDesc, jpg, true));
                            }
                        }
                    }

                    Platform.runLater(() -> {
                        grid.getChildren().clear();
                        selected.clear();
                        selected.addAll(all);
                        for (SelectedImage si : selected) {
                            ImageView iv = new ImageView(new Image(new ByteArrayInputStream(si.bytes)));
                            iv.setFitWidth(180);
                            iv.setPreserveRatio(true);
                            CheckBox cb = new CheckBox(si.seriesDesc + " (" + si.instanceId.substring(0, 8) + "…)");
                            cb.setSelected(true);
                            cb.selectedProperty().addListener((ob, ov, nv) -> si.selected = nv);
                            VBox cell = new VBox(iv, cb);
                            cell.setSpacing(4);
                            grid.getChildren().add(cell);
                        }
                        cbSelectAll.setSelected(true);
                        cbSelectAll.setOnAction(ev -> {
                            boolean on = cbSelectAll.isSelected();
                            for (int idx = 0; idx < grid.getChildren().size(); idx++) {
                                VBox cell = (VBox) grid.getChildren().get(idx);
                                ((CheckBox) cell.getChildren().get(1)).setSelected(on);
                            }
                        });
                        info.setText("Gambar dimuat: " + selected.size());
                        btnLoadImg.setDisable(false);
                    });
                } catch (Exception ex1) {
                    Platform.runLater(() -> {
                        info.setText("Gagal muat gambar: " + ex1.getMessage());
                        btnLoadImg.setDisable(false);
                    });
                }
            });
        });

        btnCreatePdf.setOnAction(e -> {
            List<SelectedImage> picks = selected.stream().filter(x -> x.selected).collect(Collectors.toList());
            if (picks.isEmpty()) {
                info.setText("Pilih minimal 1 gambar.");
                return;
            }

            runAsync(() -> {
                try {
                    // 1) Upsert expertise bila berubah
                    String newExpert = taExpert.getText();
                    saveExpertiseIfChanged(row, newExpert, API_WEBSITE_KEY);
                    // 2) Lanjut buat PDF dari teks terkini (pakai newExpert)
                    File pdf = buildRadiologyPdf(row, newExpert, picks);
                    String desiredName = buildDesiredPdfName(row);
                    String uploadedUrl = uploadRadiologyPdf(pdf, desiredName);
                    if (uploadedUrl == null) {
                        throw new IOException("Upload gagal");
                    }

                    // >>> HAPUS TEMP SETELAH SUKSES
                    try {
                        if (!pdf.delete()) {
                            pdf.deleteOnExit();
                        }
                    } catch (Throwable ignore) {
                    }

                    // simpan meta log utk pengiriman WA
                    logNoRawat = row.no_rawat;
                    logTglPeriksa = row.tgl_periksa;
                    logJam = row.jam;
                    logNoRM = row.no_rkm_medis;
                    logNama = row.nm_pasien;
                    logNoTelp = row.no_telp;

                    Platform.runLater(() -> {
                        if (typeCombo != null) {
                            typeCombo.setValue("Send File");
                        }
                        if (phoneField != null) {
                            phoneField.setText(normalizePhone(safe(row.no_telp)));
                        }
                        if (fileUrlField != null) {
                            fileUrlField.setText(uploadedUrl);
                        }
                        String caption = "Yth Bp/Ibu/Sdr " + safe(row.nm_pasien) + ".\n"
                                + "Berikut kami kirimkan hasil radiologi pada tanggal " + safe(row.tgl_periksa) + ".\n\n"
                                + "Mohon unduh PDF dalam 24 jam setelah menerima pesan ini.\nTerima kasih.";
                        if (captionArea != null) {
                            captionArea.setText(caption);
                        }
                        showSendPane();
                        showToast("PDF radiologi telah dibuat & diunggah.", false, false);
                    });

                    // Pre-log FAILED (akan diupdate saat benar2 terkirim via WA)
                    try {
                        JSONObject payload = new JSONObject();
                        payload.put("no_rawat", row.no_rawat);
                        payload.put("tgl_periksa", row.tgl_periksa);
                        payload.put("jam", row.jam);
                        payload.put("no_rkm_medis", row.no_rkm_medis);
                        payload.put("nm_pasien", row.nm_pasien);
                        payload.put("no_telp", row.no_telp);
                        payload.put("series_desc", picks.stream().map(p -> p.seriesDesc).distinct().collect(Collectors.joining(", ")));
                        JSONArray inst = new JSONArray();
                        for (SelectedImage si : picks) {
                            inst.put(si.instanceId);
                        }
                        payload.put("instances", inst);
                        payload.put("file_url", uploadedUrl);
                        payload.put("status", "FAILED");
                        payload.put("sent_by", "wa-rsudm");
                        payload.put("last_error", "");
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json; charset=UTF-8");
                        if (API_WEBSITE_KEY != null && !API_WEBSITE_KEY.trim().isEmpty()) {
                            headers.put("X-Api-Key", API_WEBSITE_KEY);
                        }
                        httpPostJsonOpen(API_RAD_LOGSEND, payload.toString(), headers);
                    } catch (Throwable logEx) {
                        System.err.println("[rad-log] gagal: " + logEx.getMessage());
                    }

                    Platform.runLater(dlg::close);
                } catch (Exception ex2) {
                    Platform.runLater(() -> info.setText("Gagal membuat/unggah PDF: " + ex2.getMessage()));
                }
            });
        });

        dlg.setScene(new Scene(rootBox, 960, 720));
        dlg.show();
    }

    private File buildRadiologyPdf(RadiologiRow row, String expertise, List<SelectedImage> picks) throws Exception {
        File out = File.createTempFile("RAD-" + row.no_rawat.replace("/", "-") + "-", ".pdf");
        try (PDDocument doc = new PDDocument()) {

            // 1) FONT aman Unicode (fallback ke Type1)
            PDType0Font fontReg = null, fontBold = null, fontItal = null;
            try (InputStream r = getResourceOrNull("/fonts/NotoSans-Regular.ttf")) {
                if (r != null) {
                    fontReg = PDType0Font.load(doc, r, true);
                }
            }
            try (InputStream r = getResourceOrNull("/fonts/NotoSans-Bold.ttf")) {
                if (r != null) {
                    fontBold = PDType0Font.load(doc, r, true);
                }
            }
            try (InputStream r = getResourceOrNull("/fonts/NotoSans-Italic.ttf")) {
                if (r != null) {
                    fontItal = PDType0Font.load(doc, r, true);
                }
            }
            if (fontReg == null) {
                /* fallback aman */ }
            final PDFont F_REG = (fontReg != null) ? fontReg : PDType1Font.HELVETICA;
            final PDFont F_BOLD = (fontBold != null) ? fontBold : PDType1Font.HELVETICA_BOLD;
            final PDFont F_ITAL = (fontItal != null) ? fontItal : PDType1Font.HELVETICA_OBLIQUE;

            // 2) PAGE 1 — Header + Identitas + Expertise
            PDPage p1 = new PDPage(PDRectangle.A4);
            doc.addPage(p1);
            try (PDPageContentStream cs = new PDPageContentStream(doc, p1)) {

                // === Kop Surat full width ===
                final float MARGIN = 40f;
                final float PAGE_W = p1.getMediaBox().getWidth();
                final float PAGE_H = p1.getMediaBox().getHeight();
                float y = PAGE_H - MARGIN;

                // — Kop surat baru: logo kiri + 4 baris kanan, ada garis pemisah di bawah
                y = drawKopSurat(doc, cs, p1.getMediaBox(), y, MARGIN, F_BOLD, F_REG);
                y -= 30;

                // === Identitas pasien (Umur/Poli/Dokter masih kosong)
                float labelX = MARGIN, valX = MARGIN + 110;

                textRow(cs, F_REG, labelX, valX, y, "Nama Pasien/RM", clean(row.nm_pasien + "    |    " + row.no_rkm_medis));
                y -= 18;
                textRow(cs, F_REG, labelX, valX, y, "Tanggal Lahir", clean(row.tgl_lahir));
                y -= 18;
                textRow(cs, F_REG, labelX, valX, y, "Umur", clean(row.umur));
                y -= 18;
                textRow(cs, F_REG, labelX, valX, y, "Jenis Kelamin", clean(row.jk));
                y -= 18;
                textRow(cs, F_REG, labelX, valX, y, "Tanggal", clean(row.tgl_periksa));
                y -= 24;

                drawSeparator(cs, MARGIN, y, PAGE_W - MARGIN);
                y -= 18;

                // Judul bagian
                cs.beginText();
                cs.setFont(F_BOLD, 13);
                cs.newLineAtOffset((PAGE_W / 2) - 90, y);
                cs.showText("HASIL PEMERIKSAAN RADIOLOGI");
                cs.endText();
                y -= 24;

                textRow(cs, F_REG, labelX, valX, y, "Penanggung Jawab", clean(row.nm_dokter));
                y -= 5;
                drawSeparator(cs, MARGIN, y, PAGE_W - MARGIN);
                y -= 17;

                // Pemeriksaan
                sectionLabel(cs, F_BOLD, MARGIN, y, "Pemeriksaan");
                y -= 15;
                bodyLine(cs, F_REG, MARGIN, y, clean(row.nm_perawatan != null ? row.nm_perawatan : "-"));
                y -= 24;

                // Hasil (multi-baris)
                sectionLabel(cs, F_BOLD, MARGIN, y, "Hasil");
                y -= 15;
                cs.beginText();
                cs.setFont(F_REG, 11);
                cs.newLineAtOffset(MARGIN, y);
                for (String ln : (expertise == null ? "" : expertise).split("\\r?\\n")) {
                    cs.showText(clean(ln));
                    cs.newLineAtOffset(0, -14);
                }
                cs.endText();

                // === Footer halaman 1 (tanpa garis) ===
                final float FOOTER_Y = 40f;                 // baseline teks terbawah (otorisasi)
                final float LEFT_X = MARGIN;

                final String timestamp = java.time.ZonedDateTime.now(java.time.ZoneId.of("Asia/Jakarta"))
                        .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                String petugas = clean(row.nm_petugas != null ? row.nm_petugas : "-");
                String pjDokter = clean(row.nm_dokter != null ? row.nm_dokter : "-");

                // --- kiri: petugas -> tanggal cetak -> catatan otorisasi
                cs.beginText();
                cs.setFont(F_REG, 9);
                cs.newLineAtOffset(LEFT_X, FOOTER_Y + 24f);
                cs.showText("Petugas Pemeriksa : " + petugas);
                cs.endText();

                cs.beginText();
                cs.setFont(F_REG, 9);
                cs.newLineAtOffset(LEFT_X, FOOTER_Y + 12f);
                cs.showText("Tanggal Cetak : " + timestamp);
                cs.endText();

                cs.beginText();
                cs.setFont(F_REG, 9);
                cs.newLineAtOffset(LEFT_X, FOOTER_Y);
                cs.showText("Hasil pemeriksaan diotorisasi secara elektronik");
                cs.endText();

                // --- kanan bawah: blok QR Penanggung Jawab
                String qrPayload = "Dokumen ini ditandatangani secara elektronik oleh dokter spesialis radiologi RSUD Matraman, "
                        + pjDokter + " pada " + timestamp + "\n\n" + pjDokter;

                final float qrSize = 96f;                               // ukuran QR
                final float qrX = PAGE_W - MARGIN - qrSize;          // kanan
                final float qrY = FOOTER_Y;                          // bawah (selaras dengan teks kiri)

                // judul di atas QR (dirapatkan)
                cs.beginText();
                cs.setFont(F_BOLD, 10);
                cs.newLineAtOffset(qrX, qrY + qrSize + 8f);
                cs.showText("Penanggung Jawab");
                cs.endText();

                // gambar QR
                try {
                    BufferedImage qrImgBuf = generateQR(qrPayload, (int) qrSize);
                    PDImageXObject qrImg = LosslessFactory.createFromImage(doc, qrImgBuf);
                    cs.drawImage(qrImg, qrX, qrY, qrSize, qrSize);
                } catch (Exception qre) {
                    cs.beginText();
                    cs.setFont(F_REG, 9);
                    cs.newLineAtOffset(qrX, qrY + qrSize / 2f);
                    cs.showText("[QR gagal dibuat]");
                    cs.endText();
                }

                // nama dokter tepat di bawah QR (lebih rapat)
                cs.beginText();
                cs.setFont(F_REG, 9);
                cs.newLineAtOffset(qrX, qrY - 6f);
                cs.showText(pjDokter);
                cs.endText();

            }

            // 3) LAMPIRAN FOTO — 1 foto per halaman
            if (picks != null && !picks.isEmpty()) {
                for (int i = 0; i < picks.size(); i++) {
                    SelectedImage si = picks.get(i);
                    PDPage p = new PDPage(PDRectangle.A4);
                    doc.addPage(p);

                    try (PDPageContentStream cs = new PDPageContentStream(doc, p)) {
                        final float MARGIN = 40f;
                        final float PAGE_W = p.getMediaBox().getWidth();
                        final float PAGE_H = p.getMediaBox().getHeight();
                        float y = PAGE_H - MARGIN;

                        // Judul halaman lampiran hanya di halaman pertama lampiran
                        cs.beginText();
                        cs.setFont(F_BOLD, 13);
                        cs.newLineAtOffset((PAGE_W / 2) - 60, y - 10);
                        cs.showText(i == 0 ? "Lampiran Foto" : "Lampiran Foto (lanjutan)");
                        cs.endText();
                        y -= 36;

                        BufferedImage bimg = ImageIO.read(new ByteArrayInputStream(si.bytes));
                        if (bimg != null) {
                            // Ambil meta untuk badge L/R + identitas
                            InstanceInfo meta = fetchInstanceInfo(si.instanceId);
                            byte[] annotated = annotateJpegBottomPad(si.bytes, meta);
                            BufferedImage b2 = ImageIO.read(new ByteArrayInputStream(annotated));
                            PDImageXObject img = LosslessFactory.createFromImage(doc, b2);

                            // area gambar: sisakan 2*panelH (dua baris) + margin bawah
                            float maxW = PAGE_W - (MARGIN * 2);
                            float maxH = PAGE_H - (MARGIN * 2) - 70; // space aman untuk footer panel
                            float scale = Math.min(maxW / img.getWidth(), maxH / img.getHeight());
                            float w = img.getWidth() * scale;
                            float h = img.getHeight() * scale;
                            float x = (PAGE_W - w) / 2f;
                            float drawY = (PAGE_H - h) / 2f + 24f; // sedikit naik supaya panel muat

                            cs.drawImage(img, x, drawY, w, h);

                            // --- footer panel 2-baris, kiri-kanan sejajar ---
//                            drawLampiranFooter(cs, F_REG, PAGE_W, MARGIN, meta, si.seriesDesc);
                            // caption tipis paling bawah (instance id — series) opsional
                            cs.beginText();
                            cs.setFont(F_ITAL, 9);
                            cs.newLineAtOffset(MARGIN, MARGIN - 6); // tepat di bawah panel
                            cs.showText(clean((si.instanceId != null ? si.instanceId : "") + " — " + (si.seriesDesc != null ? si.seriesDesc : "Gambar")));
                            cs.endText();
                        }
                    }
                }
            }

            doc.save(out);
        }
        return out;
    }

    /**
     * Kop surat: logo kiri + judul + alamat + (Telp | Email). - Logo
     * disejajarkan vertikal dengan baris alamat. - Mengembalikan Y posisi di
     * bawah separator (untuk lanjut konten).
     */
    private float drawKopSurat(PDDocument doc, PDPageContentStream cs, PDRectangle box,
            float yTop, float margin, PDFont fBold, PDFont fReg) throws IOException {
        final float pageW = box.getWidth();

        // layout dasar
        final float xLeft = margin;
        final float logoBoxW = 150f;    // lebar kolom logo
        final float logoMaxW = 140f;    // batas lebar logo
        final float logoMaxH = 70f;     // batas tinggi logo
        final float sepX = xLeft + logoBoxW + 10f; // garis vertikal
        final float textLeft = sepX + 14f;             // start teks
        final float fsTitle = 16f;
        final float fsText = 11f;

        // ---- hitung baseline teks (3 baris: judul, alamat, kontak)
        float yTitle = yTop - 16f;          // judul agak dekat ke atas
        float yAddress = yTitle - 18f;
        // gabungkan telp + email jadi satu baris
        float yContact = yAddress - 16f;

        // ---- gambar teks
        cs.beginText();
        cs.setFont(fBold, fsTitle);
        cs.newLineAtOffset(textLeft, yTitle);
        cs.showText("RUMAH SAKIT UMUM DAERAH MATRAMAN");
        cs.endText();

        cs.beginText();
        cs.setFont(fReg, fsText);
        cs.newLineAtOffset(textLeft, yAddress);
        cs.showText("JL. KEBON KELAPA NO.29, DKI JAKARTA, KOTA JAKARTA TIMUR");
        cs.endText();

        cs.beginText();
        cs.setFont(fReg, fsText);
        cs.newLineAtOffset(textLeft, yContact);
        cs.showText("Telp: (021)8581957, Email : rsudmatraman@jakarta.go.id");
        cs.endText();

        // ---- logo kiri: center-kan terhadap baris alamat
        PDImageXObject logo = loadHttpImage(doc, "https://rsudmatraman.my.id/upload/image/logo-ppid.png");
        float logoTop = yTop - 6f; // anchor atas
        if (logo != null) {
            float scale = Math.min(logoMaxW / logo.getWidth(), logoMaxH / logo.getHeight());
            float w = logo.getWidth() * scale;
            float h = logo.getHeight() * scale;
            float x = xLeft + (logoBoxW - w) / 2f;

            // posisikan tengah logo ≈ di tengah tinggi baris alamat
            // pakai pendekatan baseline + (0.6 * size)
            float addressMid = yAddress + (0.6f * fsText);
            float y = addressMid - (h / 2f);
            // jaga agar tidak lewat yTop
            if (y + h > logoTop) {
                y = logoTop - h;
            }
            cs.drawImage(logo, x, y, w, h);
        }

        // ---- garis vertikal (rapat top & bottom blok teks)
        cs.setLineWidth(1f);
        float vTop = yTop - 4f;
        float vBot = yContact - 2f;
        cs.moveTo(sepX, vTop);
        cs.lineTo(sepX, Math.max(vBot, margin)); // jangan sampai minus
        cs.stroke();

        // ---- separator bawah kop
        float sepY = yContact - 14f;
        cs.setLineWidth(0.9f);
        cs.moveTo(margin, sepY);
        cs.lineTo(pageW - margin, sepY);
        cs.stroke();

        return sepY; // gunakan ini sebagai y berikutnya
    }

    private float textWidth(PDFont font, String s, float fontSize) throws IOException {
        if (s == null || s.isEmpty()) {
            return 0f;
        }
        return font.getStringWidth(s) / 1000f * fontSize;
    }

    private void textRow(PDPageContentStream cs, PDFont f, float lx, float vx, float y, String label, String value) throws IOException {
        cs.beginText();
        cs.setFont(f, 11);
        cs.newLineAtOffset(lx, y);
        cs.showText(label);
        cs.endText();
        cs.beginText();
        cs.setFont(f, 11);
        cs.newLineAtOffset(vx, y);
        cs.showText(":  " + clean(value));
        cs.endText();
    }

    private void sectionLabel(PDPageContentStream cs, PDFont f, float x, float y, String t) throws IOException {
        cs.beginText();
        cs.setFont(f, 11);
        cs.newLineAtOffset(x, y);
        cs.showText(t);
        cs.endText();
    }

    private void bodyLine(PDPageContentStream cs, PDFont f, float x, float y, String t) throws IOException {
        cs.beginText();
        cs.setFont(f, 11);
        cs.newLineAtOffset(x, y);
        cs.showText(t);
        cs.endText();
    }

// Resource helper: tidak melempar NPE kalau file tidak ada
    private InputStream getResourceOrNull(String path) {
        try {
            java.net.URL u = getClass().getResource(path);
            return (u != null) ? u.openStream() : null;
        } catch (Exception e) {
            return null;
        }
    }

// Bersihkan NBSP, ZWSP, control char non \r\n\t
    private static String clean(String s) {
        if (s == null) {
            return "";
        }
        return s.replace('\u00A0', ' ')
                .replace('\u200B', ' ')
                .replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
    }

    private void drawSeparator(PDPageContentStream cs, float x1, float y, float x2) throws IOException {
        cs.setLineWidth(0.6f);
        cs.moveTo(x1, y);
        cs.lineTo(x2, y);
        cs.stroke();
    }

    private PDImageXObject loadHttpImage(PDDocument doc, String url) {
        try (InputStream in = new java.net.URL(url).openStream()) {
            byte[] bytes = in.readAllBytes();
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
            if (img == null) {
                return null;
            }
            return LosslessFactory.createFromImage(doc, img);
        } catch (Exception e) {
            return null; // biarkan tanpa logo bila gagal
        }
    }

    private String uploadRadiologyPdf(File pdf, String desiredName) throws Exception {
        final String endpoint = API_RAD_UPLOAD;
        final String boundary = "===" + System.currentTimeMillis() + "===";
        final String CRLF = "\r\n";

        // part teks "name"
        String partName
                = "--" + boundary + CRLF
                + "Content-Disposition: form-data; name=\"name\"" + CRLF + CRLF
                + desiredName + CRLF;

        // header part file
        String partFileHeader
                = "--" + boundary + CRLF
                + "Content-Disposition: form-data; name=\"file\"; filename=\"" + pdf.getName() + "\"" + CRLF
                + "Content-Type: application/pdf" + CRLF + CRLF;

        String partClose = CRLF + "--" + boundary + "--" + CRLF;

        byte[] bName = partName.getBytes(StandardCharsets.UTF_8);
        byte[] bHeader = partFileHeader.getBytes(StandardCharsets.UTF_8);
        byte[] bClose = partClose.getBytes(StandardCharsets.UTF_8);
        long totalLen = (long) bName.length + (long) bHeader.length + pdf.length() + (long) bClose.length;

        HttpURLConnection con = (HttpURLConnection) new URL(endpoint).openConnection();
        con.setInstanceFollowRedirects(false);
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setConnectTimeout(30000);
        con.setReadTimeout(120000);
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        con.setRequestProperty("Connection", "close");
        if (API_WEBSITE_KEY != null && !API_WEBSITE_KEY.trim().isEmpty()) {
            con.setRequestProperty("X-Api-Key", API_WEBSITE_KEY.trim());
        }
        con.setFixedLengthStreamingMode(totalLen);

        try (OutputStream os = con.getOutputStream(); InputStream in = new FileInputStream(pdf)) {
            os.write(bName);
            os.write(bHeader);
            byte[] buf = new byte[8192];
            int r;
            while ((r = in.read(buf)) != -1) {
                os.write(buf, 0, r);
            }
            os.write(bClose);
            os.flush();
        }

        int status = con.getResponseCode();
        String body = readBody(con, status);
        con.disconnect();

        if (status >= 200 && status < 300) {
            JSONObject resp = new JSONObject(body);
            if (resp.optBoolean("ok") && resp.has("url")) {
                return resp.getString("url");
            }
            throw new IOException("Upload OK but invalid JSON: " + body);
        } else {
            throw new IOException("Upload HTTP " + status + " — " + (body == null ? "(empty)" : body));
        }
    }

    /* ---------------------- NETWORK HELPERS ---------------------- */
    private JSONObject getJsonFromUrl(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        setBasicAuth(con, AUTH_USER, AUTH_PASS);
        con.setConnectTimeout(15000);
        con.setReadTimeout(60000);

        int status = con.getResponseCode();
        String body = readBody(con, status);
        con.disconnect();
        return new JSONObject(body);
    }

    private JSONObject getJsonOpen(String urlStr, Map<String, String> headers) throws Exception {
        HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(15000);
        con.setReadTimeout(60000);
        if (headers != null) {
            for (Map.Entry<String, String> h : headers.entrySet()) {
                con.setRequestProperty(h.getKey(), h.getValue());
            }
        }
        int status = con.getResponseCode();
        String body = readBody(con, status);
        con.disconnect();
        return new JSONObject(body);
    }

    private String httpPostJsonOpen(String urlStr, String json, Map<String, String> headers) throws Exception {
        HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setConnectTimeout(15000);
        con.setReadTimeout(30000);
        if (headers != null) {
            for (Map.Entry<String, String> e : headers.entrySet()) {
                con.setRequestProperty(e.getKey(), e.getValue());
            }
        }
        try (OutputStream os = con.getOutputStream()) {
            os.write(json.getBytes(StandardCharsets.UTF_8));
        }
        int status = con.getResponseCode();
        String body = readBody(con, status);
        con.disconnect();
        return body;
    }

    private boolean saveExpertiseIfChanged(RadiologiRow row, String newExpertise, String apiKey) {
        String oldExp = safe(row.hasil_exp);
        String newExp = safe(newExpertise);
        if (newExp.equals(oldExp)) {
            return true; // tidak berubah
        }
        try {
            JSONObject payload = new JSONObject();
            payload.put("no_rawat", row.no_rawat);
            payload.put("tgl_periksa", row.tgl_periksa);
            payload.put("jam", row.jam);
            payload.put("hasil", newExpertise);

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json; charset=UTF-8");
            if (apiKey != null && !apiKey.trim().isEmpty()) {
                headers.put("X-Api-Key", apiKey.trim());
            }

            String resp = httpPostJsonOpen(API_RAD_SAVEHASIL, payload.toString(), headers);
            JSONObject obj = new JSONObject(resp);
            if (!obj.optBoolean("ok")) {
                throw new IOException("ok=false");
            }
            // Update cache di row agar konsisten
            row.hasil_exp = newExpertise;
            row.hasil_mask = (newExpertise.trim().isEmpty() ? "Belum" : "Sudah");
            return true;
        } catch (Exception ex) {
            Platform.runLater(() -> showToastRad("Simpan expertise gagal: " + ex.getMessage(), true));
            return false;
        }
    }

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

            String json = String.format("{\"phone\":\"%s\",\"message\":\"%s\"}", jsonEscape(phone), jsonEscape(message));
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
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Kirim WA file + LOG ke /rad/log-send
     */
    private void sendFileFromUrl(String phone, String caption, String fileUrlStr) {
        final String boundary = "===" + System.currentTimeMillis() + "===";
        final String LF = "\r\n";
        HttpURLConnection connection = null;

        boolean success = false;
        String lastError = null;

        try {
            InputStream fileStream;
            String fileName;
            String contentType = "application/octet-stream";

            try {
                URL src = new URL(fileUrlStr);
                fileStream = src.openStream();
                fileName = new File(src.getPath()).getName();
                if (fileName.toLowerCase().endsWith(".pdf")) {
                    contentType = "application/pdf";
                }
            } catch (Exception notUrl) {
                File f = new File(fileUrlStr);
                if (!f.exists() || !f.isFile()) {
                    showToast("File tidak ditemukan: " + fileUrlStr, true, false);
                    return;
                }
                fileStream = new BufferedInputStream(new FileInputStream(f));
                fileName = f.getName();
                String probe = null;
                try {
                    probe = Files.probeContentType(f.toPath());
                } catch (IOException ignored) {
                }
                if (probe != null && !probe.trim().isEmpty()) {
                    contentType = probe;
                }
                if (fileName.toLowerCase().endsWith(".pdf")) {
                    contentType = "application/pdf";
                }
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

            try (OutputStream outputStream = connection.getOutputStream(); PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true); InputStream in = fileStream) {

                addFormField(writer, boundary, "phone", phone, LF);
                addFormField(writer, boundary, "caption", caption, LF);

                writer.append("--").append(boundary).append(LF);
                writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(fileName).append("\"").append(LF);
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

            int status = connection.getResponseCode();
            String responseBody = readBody(connection, status);

            if (status == HttpURLConnection.HTTP_OK) {
                success = true;
                showToast("Pesan WhatsApp berhasil dikirim", false, cbCloseAfterSuccess.isSelected());
            } else {
                String msg = extractMessageFromJson(responseBody);
                lastError = "Response (" + status + "): " + (msg != null ? msg : responseBody);
                showToast(lastError, true, false);
            }
        } catch (Exception ex) {
            lastError = ex.getMessage();
            showToast("Error: " + ex.getMessage(), true, false);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            // LOG RAD (pakai endpoint radiologi)
            if (logNoRawat != null && !logNoRawat.trim().isEmpty()
                    && logTglPeriksa != null && !logTglPeriksa.trim().isEmpty()
                    && logJam != null && !logJam.trim().isEmpty()) {
                try {
                    JSONObject payload = new JSONObject();
                    payload.put("no_rawat", logNoRawat);
                    payload.put("tgl_periksa", logTglPeriksa);
                    payload.put("jam", logJam);
                    payload.put("no_rkm_medis", logNoRM != null ? logNoRM : JSONObject.NULL);
                    payload.put("nm_pasien", logNama != null ? logNama : JSONObject.NULL);
                    payload.put("no_telp", logNoTelp != null ? logNoTelp : JSONObject.NULL);
                    payload.put("series_desc", JSONObject.NULL); // di-prelog sudah dikirim; di sini optional
                    payload.put("instances", new JSONArray()); // optional
                    payload.put("file_url", fileUrlStr != null ? fileUrlStr : JSONObject.NULL);
                    payload.put("status", success ? "SENT" : "FAILED");
                    payload.put("sent_by", "wa-rad");
                    payload.put("last_error", lastError != null ? lastError : "");

                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    if (API_WEBSITE_KEY != null && !API_WEBSITE_KEY.trim().isEmpty()) {
                        headers.put("X-Api-Key", API_WEBSITE_KEY);
                    }

                    httpPostJsonOpen(API_RAD_LOGSEND, payload.toString(), headers);
                } catch (Throwable logEx) {
                    System.err.println("[rad-log send] gagal: " + logEx.getMessage());
                }
            }
        }
    }

    /* ---------------------- HELPERS ---------------------- */
    private static void runAsync(Runnable r) {
        Thread t = new Thread(r, "wa-rad-thread");
        t.setDaemon(true);
        t.start();
    }

    private static void setBasicAuth(HttpURLConnection conn, String user, String pass) {
        String token = Base64.getEncoder().encodeToString((user + ":" + pass).getBytes(StandardCharsets.UTF_8));
        conn.setRequestProperty("Authorization", "Basic " + token);
    }

    // --- helper deteksi header file ---
    private static boolean isJpeg(byte[] b) {
        return b != null && b.length > 2 && (b[0] & 0xFF) == 0xFF && (b[1] & 0xFF) == 0xD8;
    }

    private static boolean isPng(byte[] b) {
        return b != null && b.length >= 8
                && (b[0] & 0xFF) == 0x89 && b[1] == 0x50 && b[2] == 0x4E && b[3] == 0x47
                && b[4] == 0x0D && b[5] == 0x0A && b[6] == 0x1A && b[7] == 0x0A;
    }

// payload yang siap dikirim ke API WA
    private static class ImagePayload {

        final byte[] data;     // sudah dijamin jpg/png
        final String mime;     // "image/jpeg" atau "image/png"
        final String ext;      // ".jpg" atau ".png"

        ImagePayload(byte[] d, String m, String e) {
            data = d;
            mime = m;
            ext = e;
        }
    }

    private static class InstanceInfo {

        String patientName;
        String patientId;
        String birthDate;       // yyyyMMdd
        String studyDate;       // yyyyMMdd
        String seriesDesc;
        String laterality;          // (0020,0060)
        String imageLaterality;     // (0020,0062)
        String patientOrientation;  // (0020,0020)
        String patientSex;          // (0010,0040)
        String viewPosition;        // (0018,5101)  ← NEW
    }

    /**
     * Normalkan bytes gambar agar sesuai dengan validasi API (jpg/png). - Jika
     * sudah JPEG -> pakai apa adanya - Jika sudah PNG -> pakai apa adanya -
     * Lainnya -> transcode ke PNG
     */
    private static ImagePayload normalizeToAllowedImage(byte[] inBytes) throws IOException {
        if (isJpeg(inBytes)) {
            return new ImagePayload(inBytes, "image/jpeg", ".jpg");
        }
        if (isPng(inBytes)) {
            return new ImagePayload(inBytes, "image/png", ".png");
        }

        // Transcode ke PNG kalau formatnya “aneh”
        BufferedImage bi = ImageIO.read(new ByteArrayInputStream(inBytes));
        if (bi == null) {
            throw new IOException("Format gambar tidak dikenal");
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bi, "png", bos);
        return new ImagePayload(bos.toByteArray(), "image/png", ".png");
    }

    private static String shortDate(String ts) {
        if (ts == null || ts.isBlank()) {
            return "";
        }
        // coba parse "yyyy-MM-dd HH:mm[:ss]"
        DateTimeFormatter[] in = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        };
        for (DateTimeFormatter f : in) {
            try {
                LocalDateTime dt = LocalDateTime.parse(ts.trim(), f);
                return dt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } catch (DateTimeParseException ignore) {
            }
        }
        // fallback: kalau sudah "yyyy-MM-dd …", ambil tanggalnya saja
        if (ts.length() >= 10 && ts.charAt(4) == '-' && ts.charAt(7) == '-') {
            String d = ts.substring(0, 10); // yyyy-MM-dd
            try {
                LocalDate ld = LocalDate.parse(d);
                return ld.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } catch (DateTimeParseException ignore) {
            }
        }
        return ts; // terakhir: tampilkan apa adanya
    }

    private static String jsonEscape(String s) {
        if (s == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\\':
                    sb.append("\\\\");
                    break;
                case '"':
                    sb.append("\\\"");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
            }
        }
        return sb.toString();
    }

    private static String readBody(HttpURLConnection conn, int code) {
        try (InputStream is = (code >= 200 && code < 300) ? conn.getInputStream() : conn.getErrorStream()) {
            if (is == null) {
                return "";
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                StringBuilder sb = new StringBuilder();
                String s;
                while ((s = br.readLine()) != null) {
                    sb.append(s);
                }
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

    private static String extractMessageFromJson(String body) {
        try {
            if (body == null) {
                return null;
            }
            int i = body.indexOf("\"message\"");
            if (i == -1) {
                return null;
            }
            int colon = body.indexOf(':', i);
            if (colon == -1) {
                return null;
            }
            int firstQuote = body.indexOf('"', colon + 1);
            if (firstQuote == -1) {
                return null;
            }
            int secondQuote = body.indexOf('"', firstQuote + 1);
            if (secondQuote == -1) {
                return null;
            }
            String msg = body.substring(firstQuote + 1, secondQuote);
            return msg.replaceAll("@s\\.whatsapp\\.net", "");
        } catch (Exception e) {
            return null;
        }
    }

    private void showToast(String message, boolean isError, boolean closeAfter) {
        Platform.runLater(() -> {
            Label lbl = new Label(message);
            lbl.setWrapText(true);
            lbl.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

            HBox box = new HBox(lbl);
            box.setPadding(new Insets(10, 12, 10, 12));
            box.setMaxWidth(420);
            box.setStyle("-fx-background-color: " + (isError ? "#dc2626" : "#16a34a") + "; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 10, 0.2, 0, 2);");

            VBox layer = (sendPane != null && sendPane.isVisible()) ? toastLayerSend : (radPane != null && radPane.isVisible()) ? toastLayerRad : toastLayerLogin;
            if (layer == null) {
                return;
            }
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

    private void showToastRad(String msg, boolean err) {
        showToast(msg, err, false);
    }

    private void setLoading(boolean loading) {
        Platform.runLater(() -> {
            if (sendButton != null) {
                sendButton.setDisable(loading);
                sendButton.setText(loading ? "Sending..." : "Send");
            }
        });
    }

    private static String normalizePhone(String input) {
        if (input == null) {
            return "";
        }
        String digits = input.replaceAll("\\D+", "");
        if (digits.isEmpty()) {
            return "";
        }
        if (digits.startsWith("0")) {
            return "62" + digits.substring(1);
        }
        if (digits.startsWith("62")) {
            return digits;
        }
        if (digits.startsWith("8")) {
            return "62" + digits;
        }
        return digits;
    }

    private static boolean isValidMsisdn(String msisdn) {
        return msisdn != null && msisdn.matches("^62\\d{8,13}$");
    }

    private static String basicAuthHeader(String user, String pass) {
        String token = Base64.getEncoder().encodeToString((user + ":" + pass).getBytes(StandardCharsets.UTF_8));
        return "Basic " + token;
    }

    private JSONObject getJsonWithAuth(String urlStr, String authHeader) throws Exception {
        HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", authHeader);
        con.setConnectTimeout(15000);
        con.setReadTimeout(60000);
        int status = con.getResponseCode();
        String body = readBody(con, status);
        con.disconnect();
        if (status < 200 || status >= 300) {
            throw new IOException("HTTP " + status + ": " + body);
        }
        return new JSONObject(body);
    }

    private byte[] getBytesWithAuth(String urlStr, String authHeader) throws Exception {
        HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", authHeader);
        con.setConnectTimeout(15000);
        con.setReadTimeout(60000);
        int status = con.getResponseCode();
        InputStream in = (status >= 200 && status < 300) ? con.getInputStream() : con.getErrorStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int r;
        while ((r = in.read(buf)) != -1) {
            bos.write(buf, 0, r);
        }
        con.disconnect();
        if (status < 200 || status >= 300) {
            throw new IOException("HTTP " + status);
        }
        return bos.toByteArray();
    }

    // Buat kanvas tambahan di bawah gambar untuk teks; marker L/R tetap di sudut atas gambar.
    private byte[] annotateJpegBottomPad(byte[] jpegBytes, InstanceInfo meta) throws IOException {
        BufferedImage src = ImageIO.read(new ByteArrayInputStream(jpegBytes));
        if (src == null) {
            return jpegBytes;
        }

        int w = src.getWidth(), h = src.getHeight();

        // Tinggi header adaptif (6–14% dari tinggi gambar)
        int headerH = Math.max(80, Math.min(140, (int) (h * 0.085)));

        BufferedImage out = new BufferedImage(w, h + headerH, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = out.createGraphics();
        g.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
                java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // ===== background putih + header band gelap =====
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, w, h + headerH);

        // header band (gelap) di bagian ATAS
        g.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.85f));
        g.setColor(java.awt.Color.BLACK);
        g.fillRect(0, 0, w, headerH);
        g.setComposite(java.awt.AlphaComposite.SrcOver);

        // gambar asli digeser ke bawah sebesar headerH
        g.drawImage(src, 0, headerH, null);

        // ===== siapkan teks =====
        String nm = safe(meta.patientName).replace('^', ' ').trim();
        String rm = safe(meta.patientId).trim();
        String dob = dicomDateToId(meta.birthDate);
        String sdate = dicomDateToId(meta.studyDate);
        String jk = sexIndo(meta.patientSex);
        String ser = (meta.seriesDesc == null || meta.seriesDesc.isBlank()) ? "-" : meta.seriesDesc;

        String line1L = (nm.isBlank() ? "" : nm) + (rm.isBlank() ? "" : "   |   RM " + rm);
        String line1R = (sdate.isBlank() ? "" : "Tgl Pemeriksaan: " + sdate);
        String line2L = (dob.isBlank() ? "" : "Tgl Lahir: " + dob)
                + (jk.isBlank() ? "" : (dob.isBlank() ? "" : "     ") + "JK: " + jk);
        String line2R = (ser.isBlank() ? "" : "Series: " + ser);

        int padX = Math.max(16, (int) (w * 0.02));
        int fsSmall = Math.max(12, (int) (headerH * 0.28));
        int lineGap = Math.max(6, (int) (headerH * 0.18));  // jarak antarbaris

        g.setColor(java.awt.Color.WHITE);
        java.awt.Font f = new java.awt.Font("SansSerif", java.awt.Font.PLAIN, fsSmall);
        g.setFont(f);
        java.awt.FontMetrics fm = g.getFontMetrics();

        // padding dari tepi atas header
        int topPad = Math.max(10, (int) (headerH * 0.22));
        // tinggi baris riil (mengikuti font)
        int lineH = fm.getHeight();
        // extra jarak antarbaris
        int extraGap = Math.max(2, (int) (headerH * 0.06));

        // baseline baris-1 dan baris-2
        int y1 = topPad + fm.getAscent();
        int y2 = y1 + lineH + extraGap;

        // Baris 1: kiri-kanan sejajar
        if (!line1L.isBlank()) {
            g.drawString(line1L, padX, y1);
        }
        if (!line1R.isBlank()) {
            int wR = fm.stringWidth(line1R);
            g.drawString(line1R, Math.max(padX, w - padX - wR), y1);
        }
        // Baris 2
        if (!line2L.isBlank()) {
            g.drawString(line2L, padX, y2);
        }
        if (!line2R.isBlank()) {
            int wR = fm.stringWidth(line2R);
            g.drawString(line2R, Math.max(padX, w - padX - wR), y2);
        }

        // ===== marker L/R putih saja (tanpa badge) — pojok atas dalam area gambar =====
//        String LR = pickLR(meta);
//        if ("L".equals(LR) || "R".equals(LR)) {
//            int fsLR = Math.max(26, (int) (Math.min(w, h) * 0.05));
//            java.awt.Font fLR = new java.awt.Font("SansSerif", java.awt.Font.BOLD, fsLR);
//            g.setFont(fLR);
//
//            // sedikit outline tipis supaya terbaca di latar terang
//            java.awt.FontMetrics fmLR = g.getFontMetrics();
//            int tx = "L".equals(LR) ? padX : (w - padX - fmLR.stringWidth(LR));
//            int ty = headerH + padX + fmLR.getAscent();
//
//            // outline (stroke) hitam tipis
//            g.setColor(new java.awt.Color(0, 0, 0, 180));
//            g.drawString(LR, tx + 1, ty + 1);
//
//            // huruf utama putih
//            g.setColor(java.awt.Color.WHITE);
//            g.drawString(LR, tx, ty);
//        }

        g.dispose();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(out, "jpg", bos);
        return bos.toByteArray();
    }

    private static String safe(String s) {
        return s == null ? "" : s;
    }

    private static String encode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (Exception e) {
            return s;
        }
    }

    private static String cleanTs(String ts) {
        if (ts == null) {
            return "";
        }
        return ts.replace('T', ' ').replace("Z", "");
    }

    /* ---------------------- PREVIEWER ---------------------- */
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
                webView.getEngine().loadContent("<html><body style='font-family:sans-serif'><h3>Preview not supported.</h3><p><a href='" + fileUrl + "' target='_blank'>Open File</a></p></body></html>");
                previewStage.setScene(new Scene(webView, 600, 200));
                previewStage.show();
            } catch (Exception ex) {
                Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "Preview error: " + ex.getMessage()).show());
            }
        }
    }

    //hapus logj
    public static void main(String[] args) {
        try {
            org.apache.log4j.BasicConfigurator.configure();
            org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.ERROR);
        } catch (Throwable ignore) {
        }
        launch(args);
    }

    private InstanceInfo fetchInstanceInfo(String instanceId) throws Exception {
        String auth = basicAuthHeader(ORTHANC_USER, ORTHANC_PASS);
        JSONObject tags = getJsonWithAuth(ORTHANC_BASE + "/instances/" + instanceId + "/simplified-tags", auth);

        InstanceInfo m = new InstanceInfo();
        m.patientName = tags.optString("PatientName", "");
        m.patientId = tags.optString("PatientID", "");
        m.birthDate = tags.optString("PatientBirthDate", "");
        m.studyDate = tags.optString("StudyDate", "");
        m.seriesDesc = tags.optString("SeriesDescription", "");
        m.laterality = tags.optString("Laterality", "");
        m.imageLaterality = tags.optString("ImageLaterality", "");
        m.patientOrientation = tags.optString("PatientOrientation", "");
        m.patientSex = tags.optString("PatientSex", "");
        m.viewPosition = tags.optString("ViewPosition", ""); // ← NEW
        return m;
    }

    private static String sexIndo(String s) {
        if (s == null) {
            return "";
        }
        s = s.trim().toUpperCase();
        if ("M".equals(s)) {
            return "Laki-laki";
        }
        if ("F".equals(s)) {
            return "Perempuan";
        }
        return s; // kalau "O" atau lainnya, tampilkan apa adanya
    }

    private static String dicomDateToId(String yyyymmdd) {
        if (yyyymmdd == null || yyyymmdd.length() != 8) {
            return "";
        }
        String y = yyyymmdd.substring(0, 4), m = yyyymmdd.substring(4, 6), d = yyyymmdd.substring(6, 8);
        return d + "-" + m + "-" + y;
    }

    private static String pickLR(InstanceInfo m) {
        // Prioritas: ImageLaterality → Laterality → SeriesDescription → ViewPosition → Orientation
        String s = upperNZ(m.imageLaterality);
        if (s.equals("L") || s.equals("R")) {
            return s;
        }

        s = upperNZ(m.laterality);
        if (s.equals("L") || s.equals("R")) {
            return s;
        }

        s = parseLRFromText(m.seriesDesc);
        if (!s.isEmpty()) {
            return s;
        }

        s = parseLRFromView(m.viewPosition);
        if (!s.isEmpty()) {
            return s;
        }

        s = parseLRFromOrientation(m.patientOrientation);
        if (!s.isEmpty()) {
            return s;
        }

        return ""; // tidak berani menebak
    }

    private static String upperNZ(String s) {
        return s == null ? "" : s.trim().toUpperCase();
    }

    private static String parseLRFromText(String t) {
        if (t == null) {
            return "";
        }
        String u = t.toUpperCase();
        if (u.contains("(L)") || u.endsWith(" L") || u.matches(".*\\bLEFT\\b.*") || u.matches(".*\\bLT\\b.*") || u.contains(" LLAT")) {
            return "L";
        }
        if (u.contains("(R)") || u.endsWith(" R") || u.matches(".*\\BRIGHT\\b.*") || u.matches(".*\\bRT\\b.*") || u.contains(" RLAT")) {
            return "R";
        }
        return "";
    }

    private static String parseLRFromView(String vp) {
        if (vp == null) {
            return "";
        }
        String u = vp.toUpperCase();
        if (u.contains("RAO") || u.contains("RPO") || u.contains("RLAT") || u.matches(".*\\bR\\b.*")) {
            return "R";
        }
        if (u.contains("LAO") || u.contains("LPO") || u.contains("LLAT") || u.matches(".*\\bL\\b.*")) {
            return "L";
        }
        return "";
    }

    private static String parseLRFromOrientation(String ori) {
        if (ori == null) {
            return "";
        }
        String u = ori.toUpperCase();
        if (u.contains("L")) {
            return "L";
        }
        if (u.contains("R")) {
            return "R";
        }
        return "";
    }

}
