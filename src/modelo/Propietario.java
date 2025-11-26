/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: Propietario.java (Clase del Modelo)
 * Descripcion: Representa al dueño de un vehículo con sus datos básicos.
 *              Implementa Serializable para poder guardar y cargar los datos.
 * Fecha: Noviembre 2025
 * Version: 1.1 (Sin dependencias externas)
 * -----------------------------------------------------------------------------
 */
package modelo;

import java.io.Serializable;

public class Propietario implements Serializable {
    private String nombreCompleto;
    private String cedula;
    private String telefono;

    // Este es el "Constructor". Sirve para crear un nuevo propietario dándole sus datos.
    public Propietario(String nombreCompleto, String cedula, String telefono) {
        this.nombreCompleto = nombreCompleto;
        this.cedula = cedula;
        this.telefono = telefono;
    }

    // --- Getters ---
    // Son métodos públicos para poder "leer" los datos desde otras clases.
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