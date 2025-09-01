package whatsapp;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import java.awt.Robot;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.function.Consumer;

class SnipCapture {

    public static void capture(Stage owner, Consumer<File> onCaptured) {
        Platform.runLater(() -> {
            Stage overlay = new Stage(StageStyle.TRANSPARENT);
            overlay.initOwner(owner);
            overlay.initModality(Modality.APPLICATION_MODAL);

            Rectangle2D bounds = getAllScreensBounds();

            Canvas canvas = new Canvas(bounds.getWidth(), bounds.getHeight());
            GraphicsContext g = canvas.getGraphicsContext2D();

            Pane root = new Pane(canvas);
            root.setStyle("-fx-background-color: rgba(0,0,0,0.25);");
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            overlay.setScene(scene);
            overlay.setX(bounds.getMinX());
            overlay.setY(bounds.getMinY());

            final double[] start = new double[2];
            final double[] end   = new double[2];
            final boolean[] dragging = { false };

            Runnable redraw = () -> {
                g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                if (dragging[0]) {
                    double x = Math.min(start[0], end[0]);
                    double y = Math.min(start[1], end[1]);
                    double w = Math.abs(end[0] - start[0]);
                    double h = Math.abs(end[1] - start[1]);
                    g.setFill(Color.color(1,1,1,0.15));
                    g.fillRect(x, y, w, h);
                    g.setStroke(Color.WHITE);
                    g.setLineWidth(2);
                    g.strokeRect(x + 1, y + 1, Math.max(0, w - 2), Math.max(0, h - 2));
                }
            };

            scene.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
                dragging[0] = true;
                start[0] = e.getScreenX() - bounds.getMinX();
                start[1] = e.getScreenY() - bounds.getMinY();
                end[0] = start[0];
                end[1] = start[1];
                redraw.run();
            });

            scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> {
                if (!dragging[0]) return;
                end[0] = e.getScreenX() - bounds.getMinX();
                end[1] = e.getScreenY() - bounds.getMinY();
                redraw.run();
            });

            scene.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
                if (!dragging[0]) return;
                dragging[0] = false;

                double x = Math.min(start[0], end[0]);
                double y = Math.min(start[1], end[1]);
                double w = Math.abs(end[0] - start[0]);
                double h = Math.abs(end[1] - start[1]);

                overlay.hide();

                if (w < 2 || h < 2) {
                    if (onCaptured != null) onCaptured.accept(null);
                    overlay.close();
                    return;
                }

                int capX = (int)Math.round(bounds.getMinX() + x);
                int capY = (int)Math.round(bounds.getMinY() + y);
                int capW = (int)Math.round(w);
                int capH = (int)Math.round(h);

                try {
                    Robot robot = new Robot();
                    BufferedImage img = robot.createScreenCapture(new Rectangle(capX, capY, capW, capH));
                    File out = File.createTempFile("snip_", ".png");
                    ImageIO.write(img, "png", out);
                    if (onCaptured != null) onCaptured.accept(out);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    if (onCaptured != null) onCaptured.accept(null);
                } finally {
                    overlay.close();
                }
            });

            scene.setOnKeyPressed(ke -> {
                switch (ke.getCode()) {
                    case ESCAPE:
                        overlay.close();
                        if (onCaptured != null) onCaptured.accept(null);
                        break;
                }
            });

            overlay.show();
        });
    }

    private static Rectangle2D getAllScreensBounds() {
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
        for (Screen s : Screen.getScreens()) {
            Rectangle2D b = s.getBounds();
            minX = Math.min(minX, b.getMinX());
            minY = Math.min(minY, b.getMinY());
            maxX = Math.max(maxX, b.getMaxX());
            maxY = Math.max(maxY, b.getMaxY());
        }
        return new Rectangle2D(minX, minY, maxX - minX, maxY - minY);
    }
}
