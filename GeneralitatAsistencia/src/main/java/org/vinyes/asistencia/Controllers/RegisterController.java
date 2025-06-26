package org.vinyes.asistencia.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.vinyes.asistencia.Database.RegistroDAO;
import org.vinyes.asistencia.Entities.Usuario;
import org.vinyes.asistencia.NFCDriver.NFCReader;

public class RegisterController {
    @FXML private TextField onPassCardNfc;
    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private TextField departmentField;
    @FXML private Button cerrarButton;

    @FXML
    public void initialize() {
        NFCReader nfcReader = new NFCReader(); // este se ejectua 1 sola vez
        onPassCardNfc.textProperty().bind(nfcReader.cardInfoProperty());
        nfcReader.iniciarLectura(true);
    }

    public void handleCerrar(ActionEvent actionEvent) {
        // cerramos el stage
        Stage stg = (Stage)(cerrarButton.getScene().getWindow()); // kinda lame ngl
        stg.close(); // cierra el form al presionar cancelar, y sigue su curso.
    }

    public void handleRegister(ActionEvent actionEvent) {
        String uuid = onPassCardNfc.getText().trim();
        String nombre = nameField.getText().trim() + " " + surnameField.getText().trim();
        String departamento = departmentField.getText().trim();

        if (!uuid.isEmpty() && !nombre.isEmpty() && !departamento.isEmpty()) {
            if (RegistroDAO.existeUsuario(uuid)) {
                textboxHelper("Registre", "El dispositiu ja es registrat.", Alert.AlertType.ERROR);
            } else {
                Usuario nuevoUsuario = new Usuario(uuid, nombre, departamento);
                RegistroDAO.registrarUsuarioEnBD(nuevoUsuario);
                textboxHelper("Registre", "Registre realitzat!", Alert.AlertType.CONFIRMATION);
            }
        } else {
            textboxHelper("Registre", "Tots els camps son obligatoris.", Alert.AlertType.WARNING);
        }
    }


    public void textboxHelper(String title, String content, Alert.AlertType alertType) {
        // tira una alerta si la contraseña y el usuario esta mal
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
