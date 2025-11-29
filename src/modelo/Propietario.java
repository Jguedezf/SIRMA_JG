/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: Propietario.java
 * Descripcion: Modela la entidad Propietario, representando al duenio de un
 *              vehiculo con su informacion personal.
 * Fecha: Noviembre 2025
 * Version: 1.0
 * -----------------------------------------------------------------------------
 */
package modelo;

import java.io.Serializable;

public class Propietario implements Serializable {
    private String nombreCompleto;
    private String cedula;
    private String telefono;

    /**
     * Constructor para inicializar un objeto Propietario.
     * @param nombreCompleto Nombre del propietario.
     * @param cedula Documento de identidad.
     * @param telefono Numero de contacto.
     */
    public Propietario(String nombreCompleto, String cedula, String telefono) {
        this.nombreCompleto = nombreCompleto;
        this.cedula = cedula;
        this.telefono = telefono;
    }

    // --- Getters ---
    public String getNombreCompleto() { return nombreCompleto; }
    public String getCedula() { return cedula; }
    public String getTelefono() { return telefono; }
}