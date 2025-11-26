/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Descripcion: Modela la entidad Vehiculo y su relacion con Propietario y Mantenimiento.
 * Fecha: Noviembre 2025
 * -----------------------------------------------------------------------------
 */
package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Vehiculo
 * Representa el objeto central del sistema. Usa composicion para asociar
 * un Propietario y una lista de Mantenimientos.
 */
public class Vehiculo implements Serializable {
    private String placa;
    private String marca;
    private String modelo;
    private int anio;
    private String color;
    private Propietario propietario;
    private List<Mantenimiento> historialMantenimientos;

    /**
     * Constructor para la clase Vehiculo.
     * @param placa Identificador unico del vehiculo.
     * @param marca Fabricante del vehiculo.
     * @param modelo Modelo especifico.
     * @param anio Anio de fabricacion.
     * @param color Color del vehiculo.
     * @param propietario Objeto Propietario asociado.
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
     * Agrega un nuevo registro de mantenimiento al historial del vehiculo.
     * @param mant El objeto Mantenimiento a agregar.
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