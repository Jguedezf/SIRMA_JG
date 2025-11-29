/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: Mecanico.java
 * Descripcion: Modela la entidad 'Mecanico', representando al personal del taller.
 * Fecha: Noviembre 2025
 * Version: 1.1
 * -----------------------------------------------------------------------------
 */
package modelo;

import java.io.Serializable;
import java.util.Objects;

public class Mecanico implements Serializable {
    private String nombreCompleto;
    private String especialidad;

    public Mecanico(String nombre, String especialidad) {
        this.nombreCompleto = nombre;
        this.especialidad = especialidad;
    }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nuevoNombre) { this.nombreCompleto = nuevoNombre; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String nuevaEspecialidad) { this.especialidad = nuevaEspecialidad; }

    @Override
    public String toString() {
        return nombreCompleto + " (" + especialidad + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mecanico mecanico = (Mecanico) o;
        return Objects.equals(nombreCompleto, mecanico.nombreCompleto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreCompleto);
    }
}