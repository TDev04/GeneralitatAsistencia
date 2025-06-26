package org.vinyes.asistencia.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.vinyes.asistencia.Database.RegistroDAO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class UsuariosScreenController {

    @FXML
    public TextArea textAreaRegistros;

    @FXML
    public void initialize() {
        
    }

    @FXML
    public void setupTextbox(String texto) {
        textAreaRegistros.appendText(texto + "\n");
    }

    public void leerArchivo(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            textAreaRegistros.clear();
            while ((linea = br.readLine()) != null) {
                setupTextbox(linea);
            }
        } catch (IOException e) {
            setupTextbox("No s'ha pogut llegir l'archiu.");
            System.out.print("Error: " + e.getMessage());
        }
    }

    public void handleRegistrarUsuarios(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/vinyes/asistencia/register-screen.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Gestió usuaris");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handleModificarUsuarios(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/vinyes/asistencia/gestion-screen.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Gestió usuaris");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handleExportarCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archiu CSV", "*.csv"));
        File archivo = fileChooser.showSaveDialog(null);

        if (archivo != null) {
            RegistroDAO.exportarDatosA_CSV(archivo);
        }
    }
}