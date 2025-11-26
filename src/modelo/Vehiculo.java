/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: Vehiculo.java (Clase del Modelo)
 * Descripcion: Modela la entidad Vehículo, que contiene sus datos, propietario
 *              y el historial de mantenimientos asociados.
 * Fecha: Noviembre 2025
 * Version: 1.1
 * -----------------------------------------------------------------------------
 */
package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Vehiculo implements Serializable {
    private String placa;
    private String marca;
    private String modelo;
    private int anio;
    private String color;
    private Propietario propietario; // Asociación con Propietario

    // Lista para almacenar el historial de servicios del vehículo
    private List<Mantenimiento> historialMantenimientos;

    /**
     * Constructor de la clase Vehiculo.
     * @param placa Identificador único del vehículo.
     * @param marca Fabricante del vehículo.
     * @param modelo Modelo específico.
     * @param anio Año de fabricación.
     * @param color Color del vehículo.
     * @param propietario Objeto Propietario asociado a este vehículo.
     */
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
     * Agrega un nuevo registro de mantenimiento al historial del vehículo.
     * @param mant Objeto Mantenimiento a agregar.
     */
    public void agregarMantenimiento(Mantenimiento mant) {
        this.historialMantenimientos.add(mant);
    }

    // --- Getters ---
    public String getPlaca() { return placa; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public int getAnio() { return anio; }
    public String getColor() { return color; }
    public Propietario getPropietario() { return propietario; }
    public List<Mantenimiento> getHistorialMantenimientos() { return historialMantenimientos; }
}