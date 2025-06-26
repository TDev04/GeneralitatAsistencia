package org.vinyes.asistencia.Controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.vinyes.asistencia.NFCDriver.NFCReader;

import java.io.IOException;

public class FirstScreenController {

    @FXML
    private Label onUpdateText;

    @FXML
    public void initialize() {
        // mira si se puede instanciar el NFCReader primero
        NFCReader nfcReader = new NFCReader();
        onUpdateText.textProperty().bind(nfcReader.cardInfoProperty());
        nfcReader.iniciarLectura(false);
    }

    protected int v = 0;
    // por cada segundo...
    private void readCards() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            // logica para ir leyendo cada 1 segundos un llavero, tarjeta...
            // TEST
            onUpdateText.setText("Esperando tarjeta...\nSegundos pasados desde la ejecucion: " + ++v);
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    protected void onManageUsers(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/vinyes/asistencia/login-screen.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
