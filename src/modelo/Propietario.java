/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez
 * Profesora: Ing. Dubraska Roca
 * Descripcion: Modela la entidad Propietario.
 * Fecha: Noviembre 2025
  * -----------------------------------------------------------------------------
 */
package modelo;

import java.io.Serializable;

/**
 * Clase Propietario
 * Representa al dueño de un vehiculo con su informacion personal.
 * Implementa Serializable para permitir la persistencia de datos.
 */
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