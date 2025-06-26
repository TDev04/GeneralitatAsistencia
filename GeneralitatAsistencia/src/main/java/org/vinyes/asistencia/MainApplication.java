package org.vinyes.asistencia;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.vinyes.asistencia.NFCDriver.NFCReader;
import org.vinyes.asistencia.NFCDriver.RegistroScheduler;

import java.awt.*;
import java.io.IOException;

public class MainApplication extends Application {
    NFCReader nfcReader = new NFCReader();
    Rectangle2D pantalla = Screen.getPrimary().getVisualBounds();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("first-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Asistencia");
        stage.setWidth(pantalla.getWidth());
        stage.setHeight(pantalla.getHeight());
        stage.setScene(scene);
        stage.show();

        RegistroScheduler.iniciarTareaCorreccionDiaria(); // ten ese runnable ahi

        // mata la app entera si el mainstage se cierra
        stage.setOnCloseRequest(event -> {
            nfcReader.detenerLectura();
            Platform.exit();
            System.exit(0);
        });

    }

    public static void main(String[] args) {
        launch();
    }
}