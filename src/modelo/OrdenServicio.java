/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion: Modela una Orden de Servicio Automotriz.
 *              Representa la entidad central del negocio, vinculando un vehiculo
 *              con los servicios realizados, costos y tiempos.
 *              Principio POO: Abstracción y Encapsulamiento.
 * Fecha: Noviembre 2025
 * Version: 4.0 (Reingenieria)
 * -----------------------------------------------------------------------------
 */
package modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class OrdenServicio implements Serializable {

    // Atributos encapsulados (private)
    private String idOrden;
    private String estado; // "PENDIENTE", "PROCESO", "FINALIZADO"
    private String tipoServicio;
    private String descripcion;
    private String mecanicoAsignado;

    // Datos economicos
    private double costoManoObra;
    private double costoRepuestos;

    // Datos de control
    private int kilometraje;
    private LocalDate fechaIngreso;
    private LocalDate fechaSalida; // Puede ser null si no ha terminado
    private LocalDate fechaProximoServicio;

    /**
     * Constructor para una nueva orden.
     * Inicializa el estado en 'PENDIENTE' por defecto.
     */
    public OrdenServicio(String idOrden, String tipoServicio, String descripcion, int kilometraje, LocalDate fechaIngreso) {
        this.idOrden = idOrden;
        this.tipoServicio = tipoServicio;
        this.descripcion = descripcion;
        this.kilometraje = kilometraje;
        this.fechaIngreso = fechaIngreso;
        this.estado = "PENDIENTE"; // Estado inicial
        this.costoManoObra = 0.0;
        this.costoRepuestos = 0.0;
        calcularProyeccion();
    }

    /**
     * Metodo: calcularProyeccion (Logica de Negocio)
     * Determina la fecha sugerida para el proximo mantenimiento.
     */
    private void calcularProyeccion() {
        // Regla: 3 meses o 5000km
        this.fechaProximoServicio = this.fechaIngreso.plusMonths(3);
    }

    /**
     * Metodo: calcularTotal
     * Aplica polimorfismo de datos para obtener el total.
     * @return Suma de mano de obra y repuestos.
     */
    public double calcularTotal() {
        return costoManoObra + costoRepuestos;
    }

    // --- Getters y Setters con validaciones basicas ---

    public String getIdOrden() { return idOrden; }
    public String getEstado() { return estado; }

    public void setEstado(String estado) {
        this.estado = estado;
        if (estado.equals("FINALIZADO")) {
            this.fechaSalida = LocalDate.now();
        }
    }

    public String getTipoServicio() { return tipoServicio; }
    public String getDescripcion() { return descripcion; }
    public String getMecanicoAsignado() { return mecanicoAsignado; }
    public void setMecanicoAsignado(String mecanicoAsignado) { this.mecanicoAsignado = mecanicoAsignado; }

    public double getCostoManoObra() { return costoManoObra; }
    public void setCostoManoObra(double costo) { this.costoManoObra = costo; }

    public double getCostoRepuestos() { return costoRepuestos; }
    public void setCostoRepuestos(double costo) { this.costoRepuestos = costo; }

    public int getKilometraje() { return kilometraje; }
    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public LocalDate getFechaProximoServicio() { return fechaProximoServicio; }

    @Override
    public String toString() {
        return idOrden + " - " + tipoServicio + " (" + estado + ")";
    }
}