/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación III - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      Mecanico.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Clase de entidad que modela al personal técnico del taller. Implementa métodos
 * clave como equals() y hashCode() para una correcta gestión en colecciones
 * y sobrescribe toString() para una representación textual legible.
 * ============================================================================
 */
package modelo;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clase Mecanico que representa a un miembro del personal técnico del taller.
 * Implementa 'Serializable' para permitir que sus objetos sean guardados en archivos.
 * PRINCIPIO POO: Persistencia - Permite que el estado de los objetos Mecanico se
 * mantenga entre ejecuciones del programa.
 */
public class Mecanico implements Serializable {

    // --- ATRIBUTOS ---
    // PRINCIPIO POO: Encapsulamiento - Los atributos son privados, protegiendo
    // el estado interno del objeto. El acceso se realiza mediante métodos públicos.
    private String nombreCompleto;
    private String especialidad;

    /**
     * Constructor para crear una nueva instancia de Mecanico.
     * @param nombre El nombre completo del mecánico.
     * @param especialidad El área de experticia del mecánico (ej. "Frenos").
     */
    public Mecanico(String nombre, String especialidad) {
        // ENTRADA: Datos para la creación de un nuevo mecánico.
        this.nombreCompleto = nombre;
        this.especialidad = especialidad;
    }

    // --- GETTERS Y SETTERS ---
    // Métodos de acceso que permiten leer y modificar los atributos de forma controlada.

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nuevoNombre) { this.nombreCompleto = nuevoNombre; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String nuevaEspecialidad) { this.especialidad = nuevaEspecialidad; }


    // --- MÉTODOS SOBRESCRITOS DE LA CLASE OBJECT ---

    /**
     * Sobrescribe el método toString() para proporcionar una representación
     * textual significativa del objeto Mecanico.
     * PRINCIPIO POO: Polimorfismo (Sobrescritura) - Se redefine el comportamiento
     * del método heredado de la superclase Object.
     * @return Un String formateado con el nombre y la especialidad.
     */
    @Override
    public String toString() {
        return nombreCompleto + " (" + especialidad + ")";
    }

    /**
     * Compara este objeto Mecanico con otro para determinar si son iguales.
     * La igualdad se basa únicamente en el 'nombreCompleto', que se considera
     * el identificador único del mecánico en este sistema.
     * Es crucial para el correcto funcionamiento de métodos como List.contains().
     * @param o El objeto a comparar.
     * @return true si los nombres completos son iguales, ignorando mayúsculas/minúsculas.
     */
    @Override
    public boolean equals(Object o) {
        // Validación de identidad y tipo.
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        // Comparación de estado.
        Mecanico mecanico = (Mecanico) o;
        return Objects.equals(nombreCompleto, mecanico.nombreCompleto);
    }

    /**
     * Genera un código hash para el objeto, basado en el 'nombreCompleto'.
     * Es obligatorio sobrescribir este método si se sobrescribe `equals()`.
     * Asegura que objetos "iguales" tengan el mismo código hash, lo que es
     * fundamental para estructuras de datos como HashMap o HashSet.
     * @return El código hash generado a partir del nombre completo.
     */
    @Override
    public int hashCode() {
        return Objects.hash(nombreCompleto);
    }
}