/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación III - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      Propietario.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Clase de entidad que modela los datos de un cliente o propietario de vehículo.
 * Es una clase fundamental del dominio, utilizada en composición por la clase Vehiculo.
 * Aplica el principio de encapsulamiento para proteger la integridad de los datos.
 * ============================================================================
 */
package modelo;

import java.io.Serializable;

/**
 * Clase Propietario que representa al dueño de un vehículo.
 * Implementa 'Serializable' para permitir que sus objetos sean guardados en archivos
 * como parte del estado de un objeto Vehiculo.
 * PRINCIPIO POO: Persistencia - Permite que los datos del propietario se conserven
 * junto con los datos del vehículo al guardar y cargar.
 */
public class Propietario implements Serializable {

    // --- ATRIBUTOS ---
    // PRINCIPIO POO: Encapsulamiento - Los atributos son privados, lo que significa
    // que solo pueden ser accedidos y modificados a través de los métodos públicos
    // (getters y setters) de esta clase.
    private String nombreCompleto;
    private String cedula;
    private String telefono;
    private String direccion;

    /**
     * Constructor para crear una nueva instancia de Propietario.
     * @param nombreCompleto El nombre y apellido del propietario.
     * @param cedula El número de cédula de identidad.
     * @param telefono El número de teléfono de contacto.
     * @param direccion La dirección de residencia del propietario.
     */
    public Propietario(String nombreCompleto, String cedula, String telefono, String direccion) {
        this.nombreCompleto = nombreCompleto;
        this.cedula = cedula;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // --- GETTERS (MÉTODOS DE ACCESO) ---
    // Permiten obtener el valor de los atributos privados desde fuera de la clase.

    /**
     * @return El nombre completo del propietario.
     */
    public String getNombreCompleto() { return nombreCompleto; }

    /**
     * @return La cédula de identidad del propietario.
     */
    public String getCedula() { return cedula; }

    /**
     * @return El teléfono de contacto del propietario.
     */
    public String getTelefono() { return telefono; }

    /**
     * @return La dirección del propietario.
     */
    public String getDireccion() { return direccion; }


    // --- SETTERS (MÉTODOS DE MODIFICACIÓN) ---
    // Permiten cambiar el valor de los atributos privados desde fuera de la clase.

    /**
     * @param nombreCompleto El nuevo nombre para el propietario.
     */
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    /**
     * @param cedula La nueva cédula para el propietario.
     */
    public void setCedula(String cedula) { this.cedula = cedula; }

    /**
     * @param telefono El nuevo teléfono para el propietario.
     */
    public void setTelefono(String telefono) { this.telefono = telefono; }

    /**
     * @param direccion La nueva dirección para el propietario.
     */
    public void setDireccion(String direccion) { this.direccion = direccion; }
}