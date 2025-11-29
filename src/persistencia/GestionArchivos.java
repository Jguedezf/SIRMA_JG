/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: GestionArchivos.java
 * Descripcion: Clase responsable de la persistencia de datos. Contiene los
 *              metodos para guardar y cargar listas de objetos en archivos
 *              binarios, asegurando la integridad de la informacion.
 * Fecha: Noviembre 2025
 * Version: 2.1 (Con manejo de errores mejorado)
 * -----------------------------------------------------------------------------
 */
package persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionArchivos {

    /**
     * Metodo generico para guardar una lista de objetos en un archivo.
     * La genericidad (<T>) permite que este metodo funcione para cualquier tipo
     * de lista (Vehiculos, Mecanicos, etc.) sin reescribir codigo.
     * @param lista La lista de objetos a guardar (debe ser Serializable).
     * @param nombreArchivo El nombre del archivo donde se guardaran los datos.
     * @return true si la operacion fue exitosa, false en caso de error.
     */
    public <T> boolean guardarDatos(List<T> lista, String nombreArchivo) {
        // Estructura try-with-resources: asegura que el stream se cierre
        // automaticamente, incluso si ocurre un error, evitando fugas de memoria.
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            oos.writeObject(lista);
            System.out.println("Datos guardados exitosamente en " + nombreArchivo);
            return true;
        } catch (IOException e) {
            System.err.println("Error critico al guardar los datos en " + nombreArchivo + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Metodo generico para cargar una lista de objetos desde un archivo.
     * @param nombreArchivo El nombre del archivo del cual cargar los datos.
     * @return Una Lista de objetos. Si el archivo no existe o hay un error,
     *         retorna una lista vacia para evitar NullPointerExceptions.
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> cargarDatos(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                Object objetoLeido = ois.readObject();
                System.out.println("Datos cargados exitosamente desde " + nombreArchivo);
                // Casting seguro del objeto leido a una lista del tipo generico T.
                return (List<T>) objetoLeido;
            } catch (IOException | ClassNotFoundException | ClassCastException e) {
                System.err.println("Error al cargar o convertir los datos de " + nombreArchivo + ": " + e.getMessage());
                // En caso de error, es mas seguro devolver una lista vacia.
                return new ArrayList<>();
            }
        }
        // Si el archivo no existe, no es un error, simplemente no hay datos previos.
        return new ArrayList<>();
    }
}