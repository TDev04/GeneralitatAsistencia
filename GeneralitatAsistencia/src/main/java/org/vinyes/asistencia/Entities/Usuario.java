package org.vinyes.asistencia.Entities;

import org.vinyes.asistencia.Database.RegistroDAO;

public class Usuario {
    private String nombreCompleto;
    private String uuid;
    private String departamento;
    private boolean fichado;

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public boolean isFichado() {
        return fichado;
    }

    public void setFichado(boolean fichado) {
        this.fichado = fichado;
    }

    // Constructor actualizado para incluir el departamento
    public Usuario(String uuid, String nombreCompleto, String departamento) {
        this.nombreCompleto = nombreCompleto;
        this.uuid = uuid;
        this.departamento = departamento;
        this.fichado = RegistroDAO.obtenerEstadoFichaje(uuid); // Obtiene el estado de fichaje
    }
}
