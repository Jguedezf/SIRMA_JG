/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez
 * Profesora: Ing. Dubraska Roca
 * Descripcion: Define una lista centralizada y estandarizada de los tipos de
 *              servicios que ofrece el taller.
 * Fecha: Noviembre 2025
 * -----------------------------------------------------------------------------
 */
package modelo;

/**
 * Clase CatalogoServicios
 * Provee los datos maestros para los tipos de servicios disponibles.
 */
public class CatalogoServicios {

    /**
     * Metodo: getServicios
     * Retorna un arreglo con los nombres de los servicios estandar.
     * @return String[] con la lista de servicios.
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