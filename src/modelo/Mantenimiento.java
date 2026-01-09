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
 * IDENTIFICACIÓN DE CLASE: Mantenimiento
 * Representa una orden de servicio individual dentro del modelo de dominio.
 * * PRINCIPIO POO: Persistencia
 * Implementa la interfaz 'Serializable' para permitir que el estado de las
 * instancias (objetos) sea transformado en bytes para su almacenamiento en disco.
 */
public class Mantenimiento implements Serializable {

    // -------------------------------------------------------------------------
    // IDENTIFICACIÓN DE ATRIBUTOS
    // -------------------------------------------------------------------------
    // PRINCIPIO POO: Encapsulamiento
    // Se declaran con modificador de acceso 'private' para proteger la integridad
    // de los datos y obligar el uso de métodos de acceso (Getters/Setters).

    private String idOrden;
    private String estado;
    private String tipoServicio;
    private String descripcionDetallada;
    private double costoManoObra;
    private double costoRepuestos;
    private int kilometrajeActual;

    // Lo que se instancia: Objetos de tipo LocalDate para gestión temporal.
    private final LocalDate fechaRealizacion; // Inmutable una vez instanciada
    private LocalDate fechaProximoServicio;

    // NUEVO ATRIBUTO para la regla de negocio de los 5.000 km
    private int kilometrajeProximoServicio;

    // -------------------------------------------------------------------------
    // MÉTODOS CONSTRUCTORES
    // -------------------------------------------------------------------------
    // PRINCIPIO POO: Polimorfismo Estático (Sobrecarga)
    // Se definen múltiples constructores con diferentes firmas (parámetros) para
    // permitir la instanciación del objeto en distintos contextos (Nuevo vs Carga).

    /**
     * Constructor 1: Para crear una NUEVA orden desde la GUI.
     * * ENTRADA: Datos primitivos y Strings provenientes del formulario.
     * PROCESO: Asignación inicial de valores y cálculo automático de próxima visita.
     */
    public Mantenimiento(String tipoServicio, String descripcion, double manoObra, double repuestos, int kilometraje) {
        this.idOrden = null; // El ID será asignado posteriormente por el Controlador
        this.estado = "Pendiente"; // Valor por defecto
        this.tipoServicio = tipoServicio;
        this.descripcionDetallada = descripcion;
        this.costoManoObra = manoObra;
        this.costoRepuestos = repuestos;
        this.kilometrajeActual = kilometraje;

        // Lo que se instancia: Fecha actual del sistema
        this.fechaRealizacion = LocalDate.now();

        // PROCESO: Ejecución de regla de negocio
        calcularProximaVisita();
    }

    /**
     * Constructor 2: Para CARGAR datos existentes (Persistencia/Pruebas).
     * ENTRADA: Todos los atributos del objeto recuperado.
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

        // PROCESO: Recálculo de proyección
        calcularProximaVisita();
    }

    /**
     * Constructor 3: Sobrecarga auxiliar para datos de prueba rápidos.
     */
    public Mantenimiento(String id, String tipo, String desc, double manoObra, double repuestos, int km, LocalDate fecha) {
        this.idOrden = id;
        this.tipoServicio = tipo;
        this.descripcionDetallada = desc;
        this.costoManoObra = manoObra;
        this.costoRepuestos = repuestos;
        this.kilometrajeActual = km;
        this.fechaRealizacion = fecha;
        this.estado = "Finalizado"; // Valor por defecto

        // PROCESO: Recálculo de proyección
        calcularProximaVisita();
    }

    // -------------------------------------------------------------------------
    // MÉTODOS DE COMPORTAMIENTO Y LÓGICA DE NEGOCIO
    // -------------------------------------------------------------------------

    /**
     * MÉTODO: calcularProximaVisita
     * Aplica la regla de mantenimiento preventivo para condiciones de uso severo.
     * * VALIDACIÓN: Verifica que la fecha de realización no sea nula.
     * PROCESO:
     * 1. Suma 3 meses a la fecha actual.
     * 2. Suma 5.000 km al kilometraje actual.
     */
    private void calcularProximaVisita() {
        if (this.fechaRealizacion != null) {
            // Regla de Tiempo
            this.fechaProximoServicio = this.fechaRealizacion.plusMonths(3);
            // Regla de Kilometraje (Ajuste solicitado)
            this.kilometrajeProximoServicio = this.kilometrajeActual + 5000;
        }
    }

    /**
     * MÉTODO: getCostoTotal
     * Calcula el monto final de la orden.
     * * PROCESO: Operación aritmética de suma (Mano de Obra + Repuestos).
     * SALIDA: Retorna el valor total como double.
     */
    public double getCostoTotal() {
        return costoManoObra + costoRepuestos;
    }

    // -------------------------------------------------------------------------
    // MÉTODOS DE ACCESO (GETTERS Y SETTERS)
    // -------------------------------------------------------------------------
    // PRINCIPIO POO: Encapsulamiento
    // Interfaz pública para leer o modificar el estado interno del objeto.

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

    // Getters y Setters para el nuevo atributo de kilometraje proyectado
    public int getKilometrajeProximoServicio() { return kilometrajeProximoServicio; }
    public void setKilometrajeProximoServicio(int kilometrajeProximoServicio) {
        this.kilometrajeProximoServicio = kilometrajeProximoServicio;
    }
}