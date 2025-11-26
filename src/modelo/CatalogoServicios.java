/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: CatalogoServicios.java
 * Descripcion: Define una lista centralizada y fija de los tipos de
 *              servicios ofrecidos.
 * Fecha: Noviembre 2025
 * Version: 1.9
 * -----------------------------------------------------------------------------
 */
package modelo;

public class CatalogoServicios {

    /**
     * Metodo estatico que retorna un arreglo con los nombres de los servicios
     * estandar que ofrece el taller.
     * @return Un arreglo de Strings con los servicios.
     */
    public static String[] getServicios() {
        return new String[] {
                "--- Seleccione un Servicio ---",
                "Cambio de Aceite y Filtro",
                "Revision de Frenos",
                "Alineacion y Balanceo",
                "Rotacion de Cauchos",
                "Escaneo Computarizado",
                "Otro Servicio (Especificar en Descripcion)"
        };
    }
}