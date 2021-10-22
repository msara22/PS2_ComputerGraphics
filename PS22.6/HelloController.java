package shapecanvas.ps2;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.util.Stack;

public class HelloController {

    @FXML
    private Slider redSlider;
    @FXML
    private Slider greenSlider;
    @FXML
    private Slider blueSlider;
    @FXML
    private Slider thicknessSlider;
    @FXML
    private Rectangle colorRectangle;
    @FXML
    private Canvas drawingAreaCanvas;
    @FXML
    private RadioButton lineRadioButton;
    @FXML
    private RadioButton rectangleRadioButton;
    @FXML
    private RadioButton ovalRadioButton;
    @FXML
    private Button erasorButton;

    @FXML
    private TextField redTextField;

    @FXML
    private TextField greenTextField;

    @FXML
    private TextField blueTextField;
    @FXML
    private CheckBox eraser;
    @FXML



    private int red = 0;
    private int green = 0;
    private int blue = 0;
    private double alpha = 1.0;
    Line line = new Line();
    Rectangle rect = new Rectangle();
    Ellipse oval = new Ellipse();
    Stack<Shape> history = new Stack();

    double xS;
    double yS;
    GraphicsContext graphics;


    public void initialize() {

        graphics = drawingAreaCanvas.getGraphicsContext2D();





        redTextField.textProperty().bind(redSlider.valueProperty().asString("%.0f"));
        greenTextField.textProperty().bind(greenSlider.valueProperty().asString("%.0f"));
        blueTextField.textProperty().bind(blueSlider.valueProperty().asString("%.0f"));

        redSlider.valueProperty().addListener(
                (ov, oldValue, newValue) -> {
                    red = newValue.intValue();
                    colorRectangle.setFill(Color.rgb(red, green, blue, alpha));
                }
        );
        greenSlider.valueProperty().addListener(
                (ov, oldValue, newValue) -> {
                    green = newValue.intValue();
                    colorRectangle.setFill(Color.rgb(red, green, blue, alpha));
                }
        );
        blueSlider.valueProperty().addListener(
                (ov, oldValue, newValue) -> {
                    blue = newValue.intValue();
                    colorRectangle.setFill(Color.rgb(red, green, blue, alpha));
                }
        );
        drawingAreaCanvas.setOnMousePressed(e -> {

            if (lineRadioButton.isSelected()) {
                line.setStartX(e.getX());
                line.setStartY(e.getY());
                line.setEndX(e.getX());
                line.setEndY(e.getY());
            } else if (rectangleRadioButton.isSelected()) {
                rect.setX(e.getX());
                rect.setY(e.getY());
            } else if (ovalRadioButton.isSelected()) {
                oval.setCenterX(e.getX());
                oval.setCenterY(e.getY());
            }


        });



        drawingAreaCanvas.setOnMouseReleased(e -> {

            xS= e.getX();
            yS = e.getY();

            graphics.setStroke(Color.rgb(red, green, blue));


            if (lineRadioButton.isSelected()) {
                line.setStrokeWidth(thicknessSlider.getValue());
                line.setFill(Color.rgb(red, green, blue));
                line.setEndX(e.getX());
                line.setEndY(e.getY());
                graphics.setLineWidth(thicknessSlider.getValue());
                graphics.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
                Line tempLine = new Line(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
                tempLine.setStroke(Color.rgb(red, green, blue));
                tempLine.setStrokeWidth(line.getStrokeWidth());
                history.push(tempLine);
            } else if (rectangleRadioButton.isSelected()) {
                rect.setWidth(Math.abs((e.getX() - rect.getX())));
                rect.setHeight(Math.abs((e.getY() - rect.getY())));
                rect.setX(Math.min(rect.getX(), e.getX()));
                rect.setY(Math.min(rect.getY(), e.getY()));
                rect.setFill(Color.rgb(red, green, blue));
                graphics.setFill(Color.rgb(red, green, blue));
                graphics.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                graphics.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                Rectangle tempRect = new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                tempRect.setFill(Color.rgb(red, green, blue));
                history.push(tempRect);
            } else if (ovalRadioButton.isSelected()) {
                oval.setRadiusX(Math.abs(e.getX() - oval.getCenterX()));
                oval.setRadiusY(Math.abs(e.getY() - oval.getCenterY()));
                oval.setCenterX(Math.min(oval.getCenterX(), e.getX()));
                oval.setCenterY(Math.min(oval.getCenterY(), e.getY()));
                oval.setFill(Color.rgb(red, green, blue));
                graphics.setFill(Color.rgb(red, green, blue));
                graphics.fillOval(oval.getCenterX(), oval.getCenterY(), oval.getRadiusX(), oval.getRadiusY());
                graphics.strokeOval(oval.getCenterX(), oval.getCenterY(), oval.getRadiusX(), oval.getRadiusY());
                Ellipse tempOval = new Ellipse(oval.getCenterX(), oval.getCenterY(), oval.getRadiusX(), oval.getRadiusY());
                tempOval.setFill(Color.rgb(red, green, blue));
                history.push(tempOval);


            }




        });
    }



    @FXML
    private void undoButtonPressed(ActionEvent event) {
        if (!history.isEmpty()) {
            history.pop();
            graphics.clearRect(0, 0, drawingAreaCanvas.getWidth(), drawingAreaCanvas.getHeight());
            drawShapes(history);
        }
    }

    private void drawShapes(Stack<Shape> shapes) {
        for (Shape s : shapes) {

            if (s.getClass() == Line.class) {
                Line temp = (Line) s;
                graphics.setLineWidth(temp.getStrokeWidth());
                graphics.setStroke(temp.getStroke());
                graphics.strokeLine(temp.getStartX(), temp.getStartY(), temp.getEndX(), temp.getEndY());
            } else if (s.getClass() == Rectangle.class) {
                Rectangle temp = (Rectangle) s;
                graphics.setLineWidth(temp.getStrokeWidth());
                graphics.setFill(temp.getFill());
                graphics.setStroke(temp.getStroke());
                graphics.fillRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
                graphics.strokeRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
            } else if (s.getClass() == Ellipse.class) {
                Ellipse temp = (Ellipse) s;
                graphics.setStroke(temp.getStroke());
                graphics.setLineWidth(temp.getStrokeWidth());
                graphics.setFill(temp.getFill());
                graphics.fillOval(temp.getCenterX(), temp.getCenterY(), temp.getRadiusX(), temp.getRadiusY());
                graphics.strokeOval(temp.getCenterX(), temp.getCenterY(), temp.getRadiusX(), temp.getRadiusY());
            }
        }
    }



    @FXML
    private void clearButtonPressed(ActionEvent event) {
        graphics.clearRect(0, 0, drawingAreaCanvas.getWidth(), drawingAreaCanvas.getHeight());
        history.clear();
    }










}