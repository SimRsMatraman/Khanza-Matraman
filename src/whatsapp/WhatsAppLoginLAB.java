package whatsapp;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;
import fungsi.koneksiDB;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

public class WhatsAppLoginLAB extends Application {

    private Label statusLabel;
    private ImageView qrImage;
    private Button refreshBtn, logoutBtn, reconnectBtn;
    private VBox card;
    private ProgressBar qrProgressBar;
    private Timeline qrTimeline;

    private static String AUTH_USER = "simrs";
    private static String AUTH_PASS = "RotiBakar69";
    private static String BASE_URL = "http://100.10.1.5:3000";
    
    public WhatsAppLoginLAB(){
        super();
        try {
            AUTH_USER = koneksiDB.APIWA_USER();
            AUTH_PASS = koneksiDB.APIWA_PASS();
            BASE_URL = koneksiDB.APIWA_LAB();
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Login WhatsApp");

        statusLabel = new Label("Please scan to connect");

        qrImage = new ImageView();
        qrImage.setFitWidth(250);
        qrImage.setFitHeight(250);

        qrProgressBar = new ProgressBar(1.0);
        qrProgressBar.setPrefWidth(250);
        qrProgressBar.setStyle("-fx-accent: #22c55e;"); // awal hijau

        refreshBtn = new Button("Refresh QR Code");
        refreshBtn.setOnAction(e -> refreshQR());

        logoutBtn = new Button("Logout");
        logoutBtn.setVisible(false);
        logoutBtn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        logoutBtn.setOnAction(e -> logout());
        
        reconnectBtn = new Button("Reconnect");
        reconnectBtn.setVisible(false);
        reconnectBtn.setOnAction(e -> reconnect());

        card = new VBox(15, statusLabel, qrImage, qrProgressBar, refreshBtn, reconnectBtn, logoutBtn);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-padding: 20; -fx-background-color: white; -fx-background-radius: 15;");
        card.setEffect(new DropShadow(10, Color.GRAY));

        Scene scene = new Scene(card, 450, 550);
        stage.setScene(scene);
        stage.show();
        reconnect();
        refreshQR();
    }
    
    private void reconnect() {

        new Thread(() -> {
            try {
                JSONObject data = getJsonFromUrl(BASE_URL + "/app/reconnect");

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> updateStatus("Error fetching QR: " + e.getMessage()));
            }
        }).start();
    }

    private void refreshQR() {
        if (qrTimeline != null) qrTimeline.stop();

        new Thread(() -> {
            try {
                JSONObject data = getJsonFromUrl(BASE_URL + "/app/login");

                if (data.has("code") && data.getString("code").equals("ALREADY_LOGGED_IN")) {
                    Platform.runLater(() -> {
                        updateStatus("⚠️ Anda sudah login");
                        qrImage.setImage(null);
                        qrProgressBar.setVisible(false);
                        qrProgressBar.setStyle("-fx-accent: #22c55e;");
                        refreshBtn.setVisible(false);
                        logoutBtn.setVisible(true);
                        reconnectBtn.setVisible(true);
                        card.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-background-radius: 15;");
                        getDevices();
                    });
                    return;
                }

                if (data.has("code") && data.getString("code").equals("SUCCESS")) {
                    String qrLink = data.getJSONObject("results").getString("qr_link");
                    int duration = data.getJSONObject("results").getInt("qr_duration");

                    Platform.runLater(() -> {
                        qrImage.setImage(new Image(qrLink));
                        fadeInQR();
                        qrProgressBar.setVisible(true);
                        qrProgressBar.setProgress(1.0);
                        qrProgressBar.setStyle("-fx-accent: #22c55e;"); // hijau awal
                        refreshBtn.setVisible(true);
                        logoutBtn.setVisible(false);
                        reconnectBtn.setVisible(true);
                        card.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-background-radius: 15;");

                        qrTimeline = new Timeline(
                                new KeyFrame(Duration.ZERO, e -> qrProgressBar.setProgress(1.0)),
                                new KeyFrame(Duration.seconds(duration), e -> refreshQR())
                        );
                        qrTimeline.setCycleCount(1);

                        qrTimeline.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
                            double progress = 1.0 - newTime.toSeconds() / duration;
                            qrProgressBar.setProgress(progress);

                            // ubah warna progres berdasarkan sisa waktu
                            if (progress > 0.5) {
                                qrProgressBar.setStyle("-fx-accent: #22c55e;"); // hijau
                            } else if (progress > 0.25) {
                                qrProgressBar.setStyle("-fx-accent: #facc15;"); // kuning
                            } else {
                                qrProgressBar.setStyle("-fx-accent: #dc2626;"); // merah
                            }
                        });

                        qrTimeline.play();
                    });

                } else {
                    Platform.runLater(() -> {
                        try {
                            updateStatus("Error: " + data.getString("message"));
                        } catch (JSONException ex) {
                            Logger.getLogger(WhatsAppLoginLAB.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> updateStatus("Error fetching QR: " + e.getMessage()));
            }
        }).start();
    }

    private void getDevices() {
        new Thread(() -> {
            try {
                JSONObject data = getJsonFromUrl(BASE_URL + "/app/devices");
                if (data.getString("code").equals("SUCCESS")) {
                    JSONArray devices = data.getJSONArray("results");
                    if (devices.length() > 0) {
                        JSONObject device = devices.getJSONObject(0);
                        String name = device.getString("name");
                        String dev = device.getString("device");

                        Platform.runLater(() -> {
                            qrImage.setImage(null);
                            qrProgressBar.setProgress(0);
                            updateStatus("✅ Anda sudah login\nName: " + name + "\nDevice: " + dev);
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> statusLabel.setText("Error getDevices: " + e.getMessage()));
            }
        }).start();
    }

    private void logout() {
        new Thread(() -> {
            try {
                JSONObject data = getJsonFromUrl(BASE_URL + "/app/logout");
                if (data.getString("code").equals("SUCCESS")) {
                    Platform.runLater(() -> {
                        updateStatus("Logout berhasil!");
                        qrImage.setImage(null);
                        qrProgressBar.setVisible(false);
                        qrProgressBar.setStyle("-fx-accent: #22c55e;");
                        refreshBtn.setVisible(true);
                        logoutBtn.setVisible(false);
                        reconnectBtn.setVisible(true);
                        card.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-background-radius: 15;");
                        refreshQR();

                        PauseTransition pause = new PauseTransition(Duration.seconds(1));
                        pause.setOnFinished(e -> updateStatus("Please scan to connect"));
                        pause.play();
                    });
                } else {
                    Platform.runLater(() -> {
                        try {
                            updateStatus("Gagal logout: " + data.getString("message"));
                        } catch (JSONException ex) {
                            Logger.getLogger(WhatsAppLoginLAB.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> updateStatus("Error logout: " + e.getMessage()));
            }
        }).start();
    }

    private JSONObject getJsonFromUrl(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        String auth = AUTH_USER + ":" + AUTH_PASS;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes("UTF-8"));
        con.setRequestProperty("Authorization", "Basic " + encodedAuth);

        int status = con.getResponseCode();
        BufferedReader in;
        if (status >= 200 && status < 300) {
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {
            in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }

        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        return new JSONObject(response.toString());
    }

    private void fadeInQR() {
        FadeTransition ft = new FadeTransition(Duration.millis(500), qrImage);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    private void updateStatus(String text) {
        statusLabel.setText(text);
    }

    public static void main(String[] args) {
        launch();
    }
}
