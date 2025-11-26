/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Descripcion: Modela un registro de servicio y calcula el proximo mantenimiento.
 * Fecha: Noviembre 2025
 * -----------------------------------------------------------------------------
 */
package modelo;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Clase Mantenimiento
 * Representa un evento de mantenimiento realizado a un vehiculo.
 */
public class Mantenimiento implements Serializable {
    private String tipoServicio;
    private String descripcionDetallada;
    private double costo;
    private int kilometrajeActual;
    private LocalDate fechaRealizacion;
    private LocalDate fechaProximoServicio;
    private int kmProximoServicio;

    /**
     * Constructor para un nuevo registro de mantenimiento.
     * @param tipoServicio Nombre del servicio realizado.
     * @param descripcionDetallada Detalles del trabajo.
     * @param costo Costo del servicio.
     * @param kilometrajeActual Kilometraje del vehiculo al momento del servicio.
     */
    public Mantenimiento(String tipoServicio, String descripcionDetallada, double costo, int kilometrajeActual) {
        this.tipoServicio = tipoServicio;
        this.descripcionDetallada = descripcionDetallada;
        this.costo = costo;
        this.kilometrajeActual = kilometrajeActual;
        this.fechaRealizacion = LocalDate.now();
        calcularProximaVisita();
    }

    /**
     * Metodo interno que calcula la fecha y kilometraje del proximo servicio.
     */
    private void calcularProximaVisita() {
        this.fechaProximoServicio = this.fechaRealizacion.plusMonths(3);
        this.kmProximoServicio = this.kilometrajeActual + 5000;
    }

    // --- Getters ---
    public String getTipoServicio() { return tipoServicio; }
    public String getDescripcionDetallada() { return descripcionDetallada; }
    public double getCosto() { return costo; }
    public int getKilometrajeActual() { return kilometrajeActual; }
    public LocalDate getFechaRealizacion() { return fechaRealizacion; }
    public LocalDate getFechaProximoServicio() { return fechaProximoServicio; }
    public int getKmProximoServicio() { return kmProximoServicio; }
}