package PS22_7;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.text.NumberFormat;

public class ShapesController {
    @FXML    private ToggleButton drowbtn;
    @FXML    private ToggleButton rubberbtn;
    @FXML    private ToggleButton linebtn;
    @FXML    private ToggleButton rectbtn;
    @FXML    private ToggleButton circlebtn;
    @FXML    private Label Borderclr;
    @FXML    private ColorPicker cpLine;
    @FXML    private ColorPicker cpFill;

    @FXML
    private RadioButton lineRadioButton;

    @FXML
    private ToggleGroup sizeToggleGroup;

    @FXML
    private RadioButton rectangleRadioButton;

    @FXML
    private RadioButton ovalRadioButton;

    @FXML
    private RadioButton eraserRadioButton;

    @FXML
    private Rectangle colorRectangle;

    @FXML
    private Slider redSlider;

    @FXML
    private Slider greenSlider;

    @FXML
    private Slider blueSlider;

    @FXML
    private Slider thicknessSlider;

    @FXML
    private TextField thicknessTextField;

    @FXML
    private Slider slider;



    @FXML
    private Button clearButton;

    @FXML
    private Pane paneField;

    private int red = 0;
    private int green = 0;
    private int blue = 0;






    double startX=0.0;
    double startY=0.0;

    public void initialize(){

        thicknessTextField.textProperty().bindBidirectional(thicknessSlider.valueProperty(), NumberFormat.getNumberInstance());

        thicknessTextField.textProperty().addListener((args, oldValue, newValue )->{
            int val= (int)thicknessSlider.getValue();
            thicknessTextField.setText(String.valueOf(val));

        });

        redSlider.valueProperty().addListener(
                (ov, oldValue, newValue) -> {
                    red = newValue.intValue();
                    colorRectangle.setFill(Color.rgb(red, green, blue));
                }
        );
        greenSlider.valueProperty().addListener(
                (ov, oldValue, newValue) -> {
                    green = newValue.intValue();
                    colorRectangle.setFill(Color.rgb(red, green, blue));
                }
        );
        blueSlider.valueProperty().addListener(
                (ov, oldValue, newValue) -> {
                    blue = newValue.intValue();
                    colorRectangle.setFill(Color.rgb(red, green, blue));
                }
        );



        paneField.setOnMousePressed(e->{

            if(eraserRadioButton.isSelected()) {
                eraserRadioButtonPressed(e.getX(),e.getY(),slider.getValue());
            }

            else if(lineRadioButton.isSelected()) {
                startX=e.getX();
                startY=e.getY();

            }
            else if(rectangleRadioButton.isSelected()) {
                startX=e.getX();
                startY=e.getY();
            }
            else if(ovalRadioButton.isSelected()) {
                startX=e.getX();
                startY=e.getY();

            }

        });

        paneField.setOnMouseDragged(e->{

            if(eraserRadioButton.isSelected()){
                eraserRadioButtonPressed(e.getX(),e.getY(),slider.getValue());

            }
        });

        paneField.setOnMouseReleased(e->{

            if(lineRadioButton.isSelected()) {

                lineRadioButtonPressed(startX,startY,e.getX(),e.getY());
                startY=0.0;
                startX=0.0;

            }
            else if(rectangleRadioButton.isSelected()) {

                rectangleRadioButtonPressed(e.getX(),e.getY(),startX,startY);
                startY=0.0;
                startX=0.0;

            }
            else if(ovalRadioButton.isSelected()) {
                ovalRadioButtonPressed(e.getX(),e.getY(),startX,startY);
                startY=0.0;
                startX=0.0;
            }

        });

        undo.setOnAction(e->{
            int count = paneField.getChildren().size();

            if (count > 0) {
                paneField.getChildren().remove(count - 1);
            }

        });


        clearButton.setOnAction(e-> {
            int count = paneField.getChildren().size();
            if (count>0) {
                for (int i = 1; i <= count; i++) {
                    paneField.getChildren().remove(count-i);
                }
            }
        });
    }



    void eraserRadioButtonPressed(double x,double y,double r){
        Circle circle = new Circle(x,y,r,Color.WHITE);
        paneField.getChildren().add(circle);
    }
    void lineRadioButtonPressed(double x1,double y1,double x2,double y2){
        Line line = new Line(x1,y1,x2,y2);
        line.setStroke(Color.rgb(red, green, blue));
        line.setFill(Color.rgb(red, green, blue));

        line.setStrokeWidth(slider.getValue());
        paneField.getChildren().add(line);
    }
    void rectangleRadioButtonPressed(double x2,double y2,double x1,double y1){
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(Math.abs((x2 - x1)));
        rectangle.setHeight(Math.abs((y2 - y1)));
        if(x2 > x1) {
            rectangle.setX(x1);
        }
        else {
            rectangle.setX(x2);
        }

        if(y2 > y1) {
            rectangle.setY(y1);
        }
        else{
            rectangle.setY(y2);
        }

        rectangle.setStroke(Color.rgb(red, green, blue));
        rectangle.setFill(Color.rgb(red, green, blue));

//        System.out.println(red+" "+green+" "+blue);

        paneField.getChildren().add(rectangle);
    }
    void ovalRadioButtonPressed(double x2,double y2,double x1,double y1){

        Circle circle = new Circle();
        double r = (Math.abs(x2 - x1) + Math.abs(y1 - y2)) / 2;
        circle.setRadius(r);

        double cx = (x1+x2)/2;
        double cy = (y1+y2)/2;
        circle.setCenterX(cx);
        circle.setCenterY(cy);

        circle.setStroke(Color.rgb(red, green, blue));
        circle.setFill(Color.rgb(red, green, blue));

//        System.out.println(red+" "+green+" "+blue);

        paneField.getChildren().add(circle);

    }


}