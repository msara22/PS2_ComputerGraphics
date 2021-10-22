package PS22_8;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.security.SecureRandom;


public class ShapesAnimationController {
    @FXML Pane pane;

    private SecureRandom random = new SecureRandom();

    private int num;
    private int[] dx;
    private int[] dy;

    public void initialize() {
        num = random.nextInt(50) + 12;

        dx = new int[num];
        dy = new int[num];

        for (int i = 0; i < num; i++) {
            Circle circle = new Circle();
            circle.setCenterX(random.nextInt(300) + 150);
            circle.setCenterY(random.nextInt(200) + 150);
            circle.setRadius(random.nextInt(100));
            circle.setFill(randomColor());
            circle.setStrokeWidth(random.nextInt(10));
            circle.setStroke(randomColor());


            pane.getChildren().add(circle);
            dx[i] = 1 + random.nextInt(5);
            dy[i] = 1 + random.nextInt(5);
        }
        Timeline timelineAnimation = new Timeline(
                new KeyFrame(Duration.millis(10), event -> moveCircles())
        );
        timelineAnimation.setCycleCount(Timeline.INDEFINITE);
        timelineAnimation.play();
    }

    private void moveCircles() {
        for (int i = 0; i < pane.getChildren().size(); i++) {
            Circle circle = (Circle) pane.getChildren().get(i);
            circle.setCenterX(circle.getCenterX() + dx[i]);
            circle.setCenterY(circle.getCenterY() + dy[i]);
            if (circle.getCenterX() + circle.getRadius() > pane.getWidth() || circle.getCenterX() - circle.getRadius() < 0) dx[i] = -dx[i];
            if (circle.getCenterY() + circle.getRadius() > pane.getHeight() || circle.getCenterY() - circle.getRadius() < 0) dy[i] = -dy[i];
        }
    }
    private Color randomColor(){
        return Color.rgb(
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256),
                (double) random.nextInt(70) / 100);
    }
}
