/*
 * Controller for the project
 *
 *
 * @author:Sushanth Kille
 * @author:Sanjana Nambiar
 * */

import Controller.Controller;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    double xOffset, yOffset;

    @Override
    public void start(Stage stage) throws Exception {
        //LOADS FXML
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("sample.fxml"));//fxmlloader
        stage.initStyle(StageStyle.TRANSPARENT);//sets stage transparent
        Parent root = (Parent) fxml.load();
        Scene scene = new Scene(root);//loads fxml to scene
        scene.setFill(Color.TRANSPARENT);//sets scene transparent
        setSceneControll(root, stage);
        stage.setScene(scene);
        Controller controller = (Controller) fxml.getController();//get controller
        //SETS CONTROLLER
        controller.setStage(stage);//pass stage to controller
        stage.show();
    }//END OF START

    //GIVES DRAGGABILITY TO FRAME
    private void setSceneControll(Parent root, Stage primaryStage) {
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
    }//END OF SETSCENECONTROLL

    @Override
    public void stop() throws Exception {
        super.stop();
    }//END OF STOP
}//END OF APPLICATION