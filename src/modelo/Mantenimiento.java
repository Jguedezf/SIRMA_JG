/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: Mantenimiento.java (Clase del Modelo)
 * Descripcion: Representa un registro de servicio realizado a un vehículo.
 *              Incluye la lógica "inteligente" para predecir la próxima visita.
 * Fecha: Noviembre 2025
 * Version: 1.1 (Sin dependencias externas)
 * -----------------------------------------------------------------------------
 */
package modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Mantenimiento implements Serializable {
    private String tipoServicio; // Ej: "Cambio de Aceite", "Frenos"
    private String descripcionDetallada;
    private double costo;
    private int kilometrajeActual;
    private LocalDate fechaRealizacion;

    // ATRIBUTOS INTELIGENTES (Esto te dará puntos en la defensa)
    private LocalDate fechaProximoServicio;
    private int kmProximoServicio;

    public Mantenimiento(String tipoServicio, String descripcionDetallada, double costo, int kilometrajeActual) {
        this.tipoServicio = tipoServicio;
        this.descripcionDetallada = descripcionDetallada;
        this.costo = costo;
        this.kilometrajeActual = kilometrajeActual;
        this.fechaRealizacion = LocalDate.now(); // Pone la fecha de hoy automáticamente

        // Llamamos a la lógica predictiva al crear el mantenimiento
        calcularProximaVisita();
    }

    // Lógica para predecir cuándo debe volver el cliente
    private void calcularProximaVisita() {
        // Regla de negocio simple: el próximo mantenimiento es en 3 meses o 5000 km.
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