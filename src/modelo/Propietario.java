/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: Propietario.java (Clase del Modelo)
 * Descripcion: Modela la entidad Propietario, conteniendo su información personal.
 *              Implementa Serializable para permitir la persistencia de objetos.
 * Fecha: Noviembre 2025
 * Version: 1.1
 * -----------------------------------------------------------------------------
 */
package modelo;

import java.io.Serializable;

public class Propietario implements Serializable {
    private String nombreCompleto;
    private String cedula;
    private String telefono;

    /**
     * Constructor para la clase Propietario.
     * @param nombreCompleto Nombre y apellido del propietario.
     * @param cedula Cédula de identidad.
     * @param telefono Número de contacto.
     */
    public Propietario(String nombreCompleto, String cedula, String telefono) {
        this.nombreCompleto = nombreCompleto;
        this.cedula = cedula;
        this.telefono = telefono;
    }

    // --- Getters ---
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    public String getCedula() {
        return cedula;
    }
    public String getTelefono() {
        return telefono;
    }
}