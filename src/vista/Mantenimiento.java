/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion: Modela la entidad 'Orden de Servicio'. Incluye generacion de ID,
 *              gestion de estados y desglose de costos (Mano de Obra + Repuestos).
 * Fecha: Noviembre 2025
 * Version: 5.3 (Modelo Final con Costos Separados)
 * -----------------------------------------------------------------------------
 */
package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Random;

public class Mantenimiento implements Serializable {

    private String idOrden;
    private String estado;  // Pendiente, En Proceso, Finalizado
    private String tipoServicio;
    private String descripcionDetallada;

    // Desglose de Costos (NUEVO)
    private double costoManoObra;
    private double costoRepuestos;

    private int kilometrajeActual;
    private LocalDate fechaRealizacion;
    private LocalDate fechaProximoServicio;
    private int kmProximoServicio;

    /**
     * Constructor Completo (Para cargar datos de prueba con fechas especificas).
     */
    public Mantenimiento(String idOrden, String tipoServicio, String descripcionDetallada, double costoManoObra, double costoRepuestos, int kilometrajeActual, LocalDate fecha) {
        this.idOrden = idOrden;
        this.estado = "Finalizado";
        this.tipoServicio = tipoServicio;
        this.descripcionDetallada = descripcionDetallada;
        this.costoManoObra = costoManoObra;
        this.costoRepuestos = costoRepuestos;
        this.kilometrajeActual = kilometrajeActual;
        this.fechaRealizacion = fecha;
        calcularProximaVisita();
    }

    /**
     * Constructor Estandar (Para registros nuevos desde la interfaz).
     * Genera ID aleatorio si no se provee logica externa y usa fecha actual.
     */
    public Mantenimiento(String tipoServicio, String descripcionDetallada, double costoManoObra, double costoRepuestos, int kilometrajeActual) {
        this.idOrden = "ORD-" + (1000 + new Random().nextInt(9000));
        this.estado = "Pendiente";
        this.tipoServicio = tipoServicio;
        this.descripcionDetallada = descripcionDetallada;
        this.costoManoObra = costoManoObra;
        this.costoRepuestos = costoRepuestos;
        this.kilometrajeActual = kilometrajeActual;
        this.fechaRealizacion = LocalDate.now();
        calcularProximaVisita();
    }

    // Constructor de compatibilidad (si alguna parte antigua lo llama con un solo costo)
    public Mantenimiento(String tipoServicio, String descripcionDetallada, double costoTotal, int kilometrajeActual) {
        this(tipoServicio, descripcionDetallada, costoTotal, 0.0, kilometrajeActual);
    }

    private void calcularProximaVisita() {
        this.fechaProximoServicio = this.fechaRealizacion.plusMonths(3);
        this.kmProximoServicio = this.kilometrajeActual + 5000;
    }

    // --- Metodos Clave para la Vista ---

    public double getCostoTotal() {
        return costoManoObra + costoRepuestos;
    }

    // --- Getters y Setters ---
    public String getIdOrden() { return idOrden; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getTipoServicio() { return tipoServicio; }
    public String getDescripcionDetallada() { return descripcionDetallada; }

    public double getCostoManoObra() { return costoManoObra; }
    public double getCostoRepuestos() { return costoRepuestos; }

    public int getKilometrajeActual() { return kilometrajeActual; }
    public LocalDate getFechaRealizacion() { return fechaRealizacion; }
    public LocalDate getFechaProximoServicio() { return fechaProximoServicio; }
    public int getKmProximoServicio() { return kmProximoServicio; }
}