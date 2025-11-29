/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: Mantenimiento.java
 * Descripcion: Modela la entidad 'Orden de Servicio'.
 * Fecha: Noviembre 2025
 * Version: 7.2
 * -----------------------------------------------------------------------------
 */
package modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Mantenimiento implements Serializable {

    private String idOrden;
    private String estado;
    private String tipoServicio;
    private String descripcionDetallada;
    private double costoManoObra;
    private double costoRepuestos;
    private int kilometrajeActual;
    private final LocalDate fechaRealizacion;
    private LocalDate fechaProximoServicio;

    public Mantenimiento(String tipoServicio, String descripcion, double manoObra, double repuestos, int kilometraje) {
        this.idOrden = null;
        this.estado = "Pendiente";
        this.tipoServicio = tipoServicio;
        this.descripcionDetallada = descripcion;
        this.costoManoObra = manoObra;
        this.costoRepuestos = repuestos;
        this.kilometrajeActual = kilometraje;
        this.fechaRealizacion = LocalDate.now();
        calcularProximaVisita();
    }

    public Mantenimiento(String id, String tipo, String desc, double manoObra, double repuestos, int km, LocalDate fecha, String estado) {
        this.idOrden = id;
        this.tipoServicio = tipo;
        this.descripcionDetallada = desc;
        this.costoManoObra = manoObra;
        this.costoRepuestos = repuestos;
        this.kilometrajeActual = km;
        this.fechaRealizacion = fecha;
        this.estado = estado;
        calcularProximaVisita();
    }

    public Mantenimiento(String id, String tipo, String desc, double manoObra, double repuestos, int km, LocalDate fecha) {
        this.idOrden = id;
        this.tipoServicio = tipo;
        this.descripcionDetallada = desc;
        this.costoManoObra = manoObra;
        this.costoRepuestos = repuestos;
        this.kilometrajeActual = km;
        this.fechaRealizacion = fecha;
        this.estado = "Finalizado";
        calcularProximaVisita();
    }

    private void calcularProximaVisita() {
        if (this.fechaRealizacion != null) {
            this.fechaProximoServicio = this.fechaRealizacion.plusMonths(3);
        }
    }

    public double getCostoTotal() {
        return costoManoObra + costoRepuestos;
    }

    public String getIdOrden() { return idOrden; }
    public void setIdOrden(String idOrden) { this.idOrden = idOrden; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(String tipoServicio) { this.tipoServicio = tipoServicio; }
    public String getDescripcionDetallada() { return descripcionDetallada; }
    public void setDescripcionDetallada(String descripcion) { this.descripcionDetallada = descripcion; }
    public double getCostoManoObra() { return costoManoObra; }
    public void setCostoManoObra(double costo) { this.costoManoObra = costo; }
    public double getCostoRepuestos() { return costoRepuestos; }
    public void setCostoRepuestos(double costo) { this.costoRepuestos = costo; }
    public int getKilometrajeActual() { return kilometrajeActual; }
    public void setKilometrajeActual(int kilometraje) { this.kilometrajeActual = kilometraje; }
    public LocalDate getFechaRealizacion() { return fechaRealizacion; }
    public LocalDate getFechaProximoServicio() { return fechaProximoServicio; }
}