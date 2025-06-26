package org.vinyes.asistencia.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.vinyes.asistencia.Database.RegistroDAO;
import org.vinyes.asistencia.Entities.Usuario;

import java.io.IOException;

public class GestionUsuarioController {

    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, String> colUUID;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colDepartamento;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDepartamento;
    @FXML private Button btnVerRegistros;
    @FXML private Button btnEliminar;
    @FXML private Button btnModificar;

    private ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colUUID.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUuid()));
        colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombreCompleto()));
        colDepartamento.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDepartamento()));

        cargarUsuarios();

        // tenemos que ir escuchando que usuario se ha seleccionado para modificar
        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtNombre.setText(newSelection.getNombreCompleto());
                txtDepartamento.setText(newSelection.getDepartamento());
                btnVerRegistros.setDisable(false);  // habilitar
            } else {
                btnVerRegistros.setDisable(true);  // deshabilitar
            }
        });
    }

    private void cargarUsuarios() {
        listaUsuarios.setAll(RegistroDAO.obtenerTodosLosUsuarios());
        tablaUsuarios.setItems(listaUsuarios);
    }

    @FXML
    public void handleEliminarUsuario() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION, "Esteu segurs que voleu eliminar a " + seleccionado.getNombreCompleto() + "?", ButtonType.YES, ButtonType.NO);
            confirmacion.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    RegistroDAO.eliminarUsuario(seleccionado.getUuid());
                    listaUsuarios.remove(seleccionado);
                    txtNombre.clear();
                    txtDepartamento.clear();
                    System.out.println("Usuari eliminat: " + seleccionado.getNombreCompleto());
                }
            });
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING, "Selecciona un usuari per eliminar.");
            alerta.show();
        }
    }

    @FXML
    public void handleModificarUsuario(ActionEvent actionEvent) {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            String nuevoNombre = txtNombre.getText().trim();
            String nuevoDepartamento = txtDepartamento.getText().trim();

            if (!nuevoNombre.isEmpty() && !nuevoDepartamento.isEmpty()) {
                RegistroDAO.actualizarUsuario(seleccionado.getUuid(), nuevoNombre, nuevoDepartamento);
                seleccionado.setNombreCompleto(nuevoNombre);
                seleccionado.setDepartamento(nuevoDepartamento);
                tablaUsuarios.refresh();
                System.out.println("Usuari actualitzat: " + nuevoNombre);
            } else {
                Alert alerta = new Alert(Alert.AlertType.WARNING, "Siusplau, introduiu un nom i departament.");
                alerta.show();
            }
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING, "Seleccioneu un usuari per modificar.");
            alerta.show();
        }
    }

    public void handleVerUsuario(ActionEvent actionEvent) {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/vinyes/asistencia/m-registros-screen.fxml"));
            Parent root = loader.load();

            MRegistrosController controller = loader.getController();
            controller.cargarRegistrosUsuario(seleccionado.getUuid());

            Stage stage = new Stage();
            stage.setTitle("Registres de " + seleccionado.getNombreCompleto());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
