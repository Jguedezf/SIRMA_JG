/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion: Clase Modelo Vehiculo. Representa el activo principal.
 *              Usa el principio de Composicion (tiene Propietario y Mantenimientos).
 * Fecha: Noviembre 2025
 * Version: 4.0
 * -----------------------------------------------------------------------------
 */
package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Vehiculo
 * Entidad principal que agrupa datos del carro, dueño e historial.
 */
public class Vehiculo implements Serializable {
    private String placa;
    private String marca;
    private String modelo;
    private int anio;
    private String color;
    private Propietario propietario;

    // Lista de Mantenimientos (Historial)
    private List<Mantenimiento> historialMantenimientos;

    public Vehiculo(String placa, String marca, String modelo, int anio, String color, Propietario propietario) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.color = color;
        this.propietario = propietario;
        this.historialMantenimientos = new ArrayList<>();
    }

    public void agregarMantenimiento(Mantenimiento mant) {
        this.historialMantenimientos.add(mant);
    }

    // GETTERS
    public String getPlaca() { return placa; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public int getAnio() { return anio; }
    public String getColor() { return color; }
    public Propietario getPropietario() { return propietario; }

    // METODO CLAVE
    public List<Mantenimiento> getHistorialMantenimientos() { return historialMantenimientos; }
}