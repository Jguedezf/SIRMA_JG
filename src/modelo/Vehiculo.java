/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: Vehiculo.java
 * Descripcion: Clase Modelo que representa el activo principal del sistema.
 *              Usa el principio de Composicion (tiene un Propietario y una
 *              lista de Mantenimientos).
 * Fecha: Noviembre 2025
 * Version: 4.0
 * -----------------------------------------------------------------------------
 */
package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Vehiculo implements Serializable {
    // --- Atributos de la clase ---
    private String placa;
    private String marca;
    private String modelo;
    private int anio;
    private String color;
    private Propietario propietario; // Composicion: Un Vehiculo TIENE UN Propietario

    // Composicion: Un Vehiculo TIENE UN historial de mantenimientos.
    private final List<Mantenimiento> historialMantenimientos;

    public Vehiculo(String placa, String marca, String modelo, int anio, String color, Propietario propietario) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.color = color;
        this.propietario = propietario;
        this.historialMantenimientos = new ArrayList<>();
    }

    /**
     * Metodo: agregarMantenimiento
     * Proceso: Anade un nuevo registro de servicio al historial del vehiculo.
     * @param mantenimiento Objeto de tipo Mantenimiento a registrar.
     */
    public void agregarMantenimiento(Mantenimiento mantenimiento) {
        this.historialMantenimientos.add(mantenimiento);
    }

    // --- Getters (Metodos de acceso para obtener datos) ---
    public String getPlaca() { return placa; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public int getAnio() { return anio; }
    public String getColor() { return color; }
    public Propietario getPropietario() { return propietario; }
    public List<Mantenimiento> getHistorialMantenimientos() { return historialMantenimientos; }

    // --- Setters (Metodos para modificar datos, utiles para la edicion) ---
    public void setPlaca(String placa) { this.placa = placa; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setAnio(int anio) { this.anio = anio; }
    public void setColor(String color) { this.color = color; }
    public void setPropietario(Propietario propietario) { this.propietario = propietario; }
}