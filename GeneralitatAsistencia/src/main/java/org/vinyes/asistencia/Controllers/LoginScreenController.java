/*
    Este login no es que sea el mas seguro del mundo
    contra ingieneria inversa...
 */

package org.vinyes.asistencia.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;

import org.vinyes.asistencia.Entities.Login;
import org.vinyes.asistencia.Security.Encryption;

public class LoginScreenController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void onLoginClick(ActionEvent event) {
        Login login = new Login();

        String username = usernameField.getText();
        String password = passwordField.getText();

        login.setUsername(username);
        login.setPassword(password);

        if (Encryption.validateCredentials(login.getUsername(), login.getPassword())) {
            // si se supera el login, abre la siguiente
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/vinyes/asistencia/usuarios-screen.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Usuaris");
                stage.setScene(new Scene(root));
                stage.show();

                // cierra la pestaña de login
                ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // tira una alerta si la contraseña y el usuario esta mal
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Usuari o contrasenya incorrectes");
            alert.showAndWait();
        }
    }
}
