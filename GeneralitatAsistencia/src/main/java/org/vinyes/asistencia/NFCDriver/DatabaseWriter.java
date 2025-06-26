package org.vinyes.asistencia.NFCDriver;

import org.vinyes.asistencia.Database.RegistroDAO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// Simulando la carga de fichajes anteriores en la base de datos
public class DatabaseWriter {

    public DatabaseWriter() {
    }

    public void escribirNuevaLinea(String csv) {
        String[] csvList = csv.split(",");
        String uid = csvList[0];
        String nombre = csvList[1];
        String fecha = csvList[2];
        String tipo = csvList[3].equals("true") ? "entrat" : "sortit";

        // Escribir en archivo de texto
        escribirEnArchivo("fichaje.txt", "Professor: " + nombre + " ha " + tipo + " a les " + fecha + ", amb identificador: " + uid);

        // Escribir en CSV
        escribirEnArchivo("database.csv", String.join(",", uid, nombre, fecha, tipo));

        System.out.println("Registro cosas");

        // Insertar en la base de datos
        RegistroDAO.insertarRegistro(uid, fecha, tipo);
    }

    private void escribirEnArchivo(String ruta, String contenido) {
        File file = new File(ruta);
        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(contenido + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir en " + ruta, e);
        }
    }
}