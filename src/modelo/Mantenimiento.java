/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: Mantenimiento.java (Clase del Modelo)
 * Descripcion: Modela un registro de servicio. Incluye lógica para el cálculo
 *              predictivo del próximo mantenimiento.
 * Fecha: Noviembre 2025
 * Version: 1.1
 * -----------------------------------------------------------------------------
 */
package modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Mantenimiento implements Serializable {
    private String tipoServicio;
    private String descripcionDetallada;
    private double costo;
    private int kilometrajeActual;
    private LocalDate fechaRealizacion;

    // Atributos para la gestión predictiva
    private LocalDate fechaProximoServicio;
    private int kmProximoServicio;

    /**
     * Constructor para la clase Mantenimiento.
     * @param tipoServicio Nombre del servicio realizado (ej. "Cambio de Aceite").
     * @param descripcionDetallada Detalles específicos del trabajo.
     * @param costo Costo del servicio.
     * @param kilometrajeActual Kilometraje del vehículo al momento del servicio.
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
     * Calcula la fecha y kilometraje del próximo servicio basado en reglas de negocio.
     * Regla: 3 meses o 5000 km, lo que ocurra primero.
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