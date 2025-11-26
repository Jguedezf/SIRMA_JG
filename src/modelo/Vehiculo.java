/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: Vehiculo.java (Clase del Modelo)
 * Descripcion: Representa el objeto central del sistema. Contiene sus datos,
 *              su propietario y su historial de mantenimientos.
 * Fecha: Noviembre 2025
 * Version: 1.1 (Sin dependencias externas)
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
    private Propietario propietario; // Un Vehículo TIENE UN Propietario (Composición)

    // Un Vehículo TIENE UNA LISTA de Mantenimientos (Su historial)
    private List<Mantenimiento> historialMantenimientos;

    public Vehiculo(String placa, String marca, String modelo, int anio, String color, Propietario propietario) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.color = color;
        this.propietario = propietario;
        this.historialMantenimientos = new ArrayList<>(); // Se crea la lista vacía al nacer el vehículo
    }

    // Método para añadir un nuevo registro al historial
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