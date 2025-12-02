/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación III - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      Mantenimiento.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Clase de entidad que modela una "Orden de Servicio". Contiene todos los
 * atributos y comportamientos relacionados con un trabajo de mantenimiento
 * realizado a un vehículo, incluyendo costos, fechas y estado.
 * ============================================================================
 */
package modelo;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Clase Mantenimiento que representa una orden de servicio individual.
 * Implementa la interfaz 'Serializable' para permitir que sus instancias
 * sean guardadas en disco.
 * PRINCIPIO POO: Persistencia - Al ser Serializable, el estado de un objeto
 * Mantenimiento puede ser almacenado en un archivo y recuperado posteriormente.
 */
public class Mantenimiento implements Serializable {

    // --- ATRIBUTOS ---
    // PRINCIPIO POO: Encapsulamiento - Todos los atributos son privados para proteger
    // el estado interno del objeto. El acceso se controla a través de getters y setters.
    private String idOrden;
    private String estado;
    private String tipoServicio;
    private String descripcionDetallada;
    private double costoManoObra;
    private double costoRepuestos;
    private int kilometrajeActual;
    private final LocalDate fechaRealizacion; // Inmutable una vez creada la orden
    private LocalDate fechaProximoServicio;

    /**
     * Constructor para crear una nueva orden de servicio desde la interfaz gráfica.
     * Inicializa la orden con estado "Pendiente" y la fecha actual.
     * PRINCIPIO POO: Polimorfismo Estático (Sobrecarga) - Múltiples constructores
     * con diferentes parámetros para distintos escenarios de creación.
     *
     * @param tipoServicio El tipo de servicio a realizar.
     * @param descripcion Descripción detallada del trabajo.
     * @param manoObra Costo de la mano de obra.
     * @param repuestos Costo de los repuestos.
     * @param kilometraje Kilometraje del vehículo al momento del servicio.
     */
    public Mantenimiento(String tipoServicio, String descripcion, double manoObra, double repuestos, int kilometraje) {
        // ENTRADA: Datos del formulario de nueva orden.
        this.idOrden = null; // El ID será asignado por el Controlador.
        this.estado = "Pendiente";
        this.tipoServicio = tipoServicio;
        this.descripcionDetallada = descripcion;
        this.costoManoObra = manoObra;
        this.costoRepuestos = repuestos;
        this.kilometrajeActual = kilometraje;
        this.fechaRealizacion = LocalDate.now(); // Se asigna la fecha del sistema.
        // PROCESO:
        calcularProximaVisita();
    }

    /**
     * Constructor sobrecargado para cargar datos existentes desde persistencia
     * o para la creación de datos de prueba.
     *
     * @param id ID único de la orden.
     * @param tipo Tipo de servicio.
     * @param desc Descripción del servicio.
     * @param manoObra Costo de mano de obra.
     * @param repuestos Costo de repuestos.
     * @param km Kilometraje del vehículo.
     * @param fecha Fecha en que se realizó o registró el servicio.
     * @param estado Estado actual de la orden.
     */
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

    /**
     * Constructor sobrecargado, principalmente para la carga de datos de prueba,
     * asumiendo un estado "Finalizado" por defecto si no se especifica.
     */
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

    /**
     * Calcula la fecha estimada del próximo servicio, sumando 3 meses a la fecha actual.
     */
    private void calcularProximaVisita() {
        if (this.fechaRealizacion != null) {
            this.fechaProximoServicio = this.fechaRealizacion.plusMonths(3);
        }
    }

    /**
     * Calcula y devuelve el costo total de la orden de servicio.
     * @return La suma del costo de mano de obra y el costo de repuestos.
     */
    public double getCostoTotal() {
        // PROCESO: Suma de los costos parciales.
        // SALIDA: Costo total como un double.
        return costoManoObra + costoRepuestos;
    }

    // --- SECCIÓN DE GETTERS Y SETTERS ---
    // Métodos públicos que permiten un acceso controlado a los atributos privados,
    // cumpliendo con el principio de Encapsulamiento.

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