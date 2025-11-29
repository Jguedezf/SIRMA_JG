/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: CatalogoServicios.java
 * Descripcion: Define una lista centralizada y estandarizada de los tipos de
 *              servicios que ofrece el taller. Su uso previene errores de
 *              tipeo y facilita futuras actualizaciones.
 * Fecha: Noviembre 2025
 * Version: 1.0
 * -----------------------------------------------------------------------------
 */
package modelo;

public class CatalogoServicios {

    /**
     * Metodo estatico getServicios.
     * Un metodo estatico pertenece a la clase, no a un objeto. Se puede llamar
     * directamente como: CatalogoServicios.getServicios();
     * @return String[] - Un arreglo con los nombres de los servicios estandar.
     */
    public static String[] getServicios() {
        return new String[]{
                "--- Seleccione un Servicio ---",
                "Cambio de Aceite y Filtro",
                "Revision de Frenos",
                "Alineacion y Balanceo",
                "Rotacion de Cauchos",
                "Escaneo Computarizado",
                "Mantenimiento Aire Acondicionado",
                "Otro Servicio (Especificar)"
        };
    }
}