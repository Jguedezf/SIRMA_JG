/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación III - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      Vehiculo.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Clase de entidad principal que modela un vehículo. Esta clase es el núcleo
 * del dominio, aplicando principios clave de POO como Abstracción, Encapsulamiento
 * y Composición para representar de forma robusta los activos del taller.
 * ============================================================================
 */
package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Vehiculo que representa un automóvil dentro del sistema.
 * Contiene sus características físicas, la información de su propietario y su
 * historial completo de mantenimientos.
 * PRINCIPIO POO: Abstracción - La clase modela un vehículo real, pero solo incluye
 * los atributos y comportamientos relevantes para el contexto del sistema de mantenimiento.
 * PRINCIPIO POO: Persistencia - Implementa Serializable para poder ser guardado en disco.
 */
public class Vehiculo implements Serializable {

    // --- ATRIBUTOS ---
    // PRINCIPIO POO: Encapsulamiento - Todos los atributos son privados para proteger
    // la integridad del estado del objeto. El acceso se controla a través de métodos públicos.
    private String placa;      // Identificador único (Primary Key lógica)
    private String marca;
    private String modelo;
    private int anio;
    private String color;

    // PRINCIPIO POO: Composición - Un Vehículo "tiene-un" Propietario.
    // Esta es una relación fuerte; un vehículo no puede existir en el sistema sin un propietario asociado.
    private Propietario propietario;

    // PRINCIPIO POO: Agregación - Un Vehículo "tiene-un" historial (lista) de Mantenimientos.
    // Esta es una relación de "uno a muchos".
    private List<Mantenimiento> historialMantenimientos;

    /**
     * Constructor para crear una nueva instancia de Vehiculo.
     * Inicializa la lista de mantenimientos para prevenir errores de tipo NullPointerException.
     *
     * @param placa Matrícula del vehículo (identificador único).
     * @param marca Fabricante del vehículo (ej. Toyota, Ford).
     * @param modelo Modelo específico del vehículo (ej. Corolla, Fiesta).
     * @param anio Año de fabricación.
     * @param color Color del vehículo.
     * @param propietario Objeto Propietario asociado a este vehículo.
     */
    public Vehiculo(String placa, String marca, String modelo, int anio, String color, Propietario propietario) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.color = color;
        this.propietario = propietario;
        // Instanciación temprana para asegurar que la lista nunca sea nula.
        this.historialMantenimientos = new ArrayList<>();
    }

    // --- MÉTODOS DE COMPORTAMIENTO ---

    /**
     * Agrega una nueva orden de servicio al historial de mantenimientos del vehículo.
     * @param mant La instancia de Mantenimiento a agregar.
     */
    public void agregarMantenimiento(Mantenimiento mant) {
        if (mant != null) {
            this.historialMantenimientos.add(mant);
        }
    }

    // --- GETTERS (MÉTODOS DE ACCESO) ---

    public String getPlaca() { return placa; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public int getAnio() { return anio; }
    public String getColor() { return color; }
    public Propietario getPropietario() { return propietario; }
    public List<Mantenimiento> getHistorialMantenimientos() { return historialMantenimientos; }

    // --- SETTERS (MÉTODOS DE MODIFICACIÓN) ---

    public void setPlaca(String placa) { this.placa = placa; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setAnio(int anio) { this.anio = anio; }
    public void setColor(String color) { this.color = color; }
    public void setPropietario(Propietario propietario) { this.propietario = propietario; }
    public void setHistorialMantenimientos(List<Mantenimiento> historialMantenimientos) { this.historialMantenimientos = historialMantenimientos; }
}