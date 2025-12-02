/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación III - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      CatalogoServicios.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Clase de utilidad (Utility Class) que centraliza el catálogo de servicios.
 * Utiliza un método estático para proveer los datos de forma global sin
 * necesidad de crear una instancia, promoviendo la consistencia de datos en el sistema.
 * ============================================================================
 */
package modelo;

/**
 * Clase CatalogoServicios que actúa como una fuente de datos centralizada y estática.
 * Su propósito es estandarizar los nombres de los servicios ofrecidos por el taller,
 * evitando el uso de "cadenas mágicas" (magic strings) en el código y facilitando
 * el mantenimiento futuro.
 */
public class CatalogoServicios {

    /**
     * Constructor privado para prevenir la instanciación de esta clase de utilidad.
     * PRINCIPIO POO: Encapsulamiento - Se oculta el constructor para reforzar
     * el hecho de que esta clase no debe ser tratada como un objeto, sino como
     * un proveedor de funcionalidades estáticas.
     */
    private CatalogoServicios() {
        // Esta clase no debe ser instanciada.
    }

    /**
     * Provee una lista estandarizada de los tipos de servicios disponibles.
     * Al ser un método estático, pertenece a la clase misma y no a una instancia,
     * permitiendo su acceso global mediante `CatalogoServicios.getServicios()`.
     *
     * @return Un arreglo de Strings con los nombres de los servicios. El primer
     *         elemento es una instrucción para la interfaz de usuario.
     */
    public static String[] getServicios() {
        // SALIDA: Retorna un arreglo predefinido de servicios.
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