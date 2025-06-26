package org.vinyes.asistencia.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import org.vinyes.asistencia.Database.RegistroDAO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MRegistrosController {
    @FXML private TextArea textAreaUsuario;
    private String uuidActual;

    public void cargarRegistrosUsuario(String uuid) {
        this.uuidActual = uuid; // guardar el uuid
        ObservableList<String> registros = RegistroDAO.obtenerRegistrosPorUsuario(uuid);

        if (registros.isEmpty()) {
            textAreaUsuario.setText("No hi han registres disponibles per aquest usuari.");
        } else {
            StringBuilder texto = new StringBuilder();
            registros.forEach(registro -> texto.append(registro).append("\n"));
            textAreaUsuario.setText(texto.toString());
        }
    }


    public void userToCSV(ActionEvent actionEvent) {
        var usuario = RegistroDAO.obtenerUsuarioPorSUID(uuidActual);

        if (uuidActual == null || uuidActual.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No hi ha un usuari seleccionat.");
            alert.show();
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar CSV del usuari");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo CSV", "*.csv"));
        fileChooser.setInitialFileName("registro_" + usuario.getNombreCompleto() + ".csv");
        File archivo = fileChooser.showSaveDialog(null);

        if (archivo != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                writer.write("UID,Nombre,Fecha,Tipo\n");

                ObservableList<String> registros = RegistroDAO.obtenerRegistrosPorUsuario(uuidActual);

                for (String registro : registros) {
                    // registro: "2025-03-30 12:23:00 - entrada"
                    String[] partes = registro.split(" - ");
                    String fecha = partes[0];
                    String tipo = partes.length > 1 ? partes[1] : "";

                    writer.write(String.format("%s,%s,%s,%s\n",
                            uuidActual,
                            usuario.getNombreCompleto(),
                            fecha,
                            tipo
                    ));
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "CSV exportat de manera correcta:\n" + archivo.getAbsolutePath());
                alert.show();

            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error al exportar el CSV.");
                alert.show();
            }
        }
    }

}
