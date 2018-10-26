package Controller;
/*
 * Controller for the project
 *
 *
 * @author:Sushanth Kille
 * @author:Sanjana Nambiar
 * */

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Controller {
    //TWO VIEWS OF UI
    @FXML
    public AnchorPane homePane, detectPane;
    //TWO DIFFERENT STAGES
    @FXML
    private
    Stage homeStage, renderStage;
    private boolean home = true;
    private ImageView i;
    @FXML
    private WritableImage image;
    private ScheduledExecutorService timer;
    private FaceDetectorCamera cam;

    private void ready(boolean frame) {
        //SECOND STAGE FOR SHOWING DETECTED FACES
        renderStage = new Stage();
        i = new ImageView();
        GridPane gp = new GridPane();
        Button cb = new Button("Close");
        gp.add(i, 0, 0,2,1);
        //FRAME OR STREAM
        if (!frame) {
            renderStage.initStyle(StageStyle.UNDECORATED);
            cb.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    timer.shutdown();
                    cam.close();
                    renderStage.close();
                }
            });
            gp.setAlignment(Pos.TOP_RIGHT);
            gp.add(cb, 0, 1);
        }
        renderStage.setScene(new Scene(gp));
    }//END OF READY

    //GET HOME STAGE FROOM MODEL(MAIN)
    @FXML
    public void setStage(Stage stage) {
        this.homeStage = stage;
    }//END OF SET STAGE

    //ON HOME IS ASKED
    public void onHome() {
        if (!home) {
            homePane.setVisible(true);
            home = true;
            detectPane.setVisible(false);
        }
    }//END OF ONHOME

    //ON DETECTION PAGE IS ASKED
    @FXML
    public void onSessionPage() {
        if (home) {
            homePane.setVisible(false);
            home = false;
            detectPane.setVisible(true);
        }
    }//END OF ONSESSIONPAGE

    //ON PICK FRAME SELECTED
    @FXML
    public void onPickFrame() {
        ready(true);
        cam = new FaceDetectorCamera();
        i.setImage(cam.captureFaceDetectedFrame());
        cam.close();
        renderStage.show();
    }//END OF ONPICKFRAME

    //ON STREAM SELECTED
    public void onStream() {
        ready(false);
        cam = new FaceDetectorCamera();
        i.setImage(cam.captureFaceDetectedFrame());
        renderStage.show();
        //RENDERS UMAGE AS VIDEO
        Runnable framegrabber = new Runnable() {
            @Override
            public void run() {
                image = cam.captureFaceDetectedFrame();
                //ASK FXTHREAD TO SET THE IMAGE
                setimage(image);
            }
        };
        timer = Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(framegrabber, 0, 5, TimeUnit.MICROSECONDS);
    }//END OF ONSTREAM

    //SETS IMAGE FOR STREAM FRAME
    private void setimage(WritableImage image) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                i.setImage(image);
            }
        });
    }//END OF SETIMAGE

    //CLOSES APPLICATION
    @FXML
    public void onClose() {
        homeStage.close();
    }//END OF ONCLOSE

}//END OF CONTROLLER
