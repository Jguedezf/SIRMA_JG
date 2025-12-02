/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación III - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      GestionArchivos.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Clase responsable de la capa de persistencia de datos. Implementa el patrón
 * de diseño DAO (Data Access Object) para encapsular la lógica de lectura y
 * escritura en archivos binarios, utilizando serialización de objetos Java.
 * ============================================================================
 */
package persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase GestionArchivos que centraliza las operaciones de I/O (Entrada/Salida) del sistema.
 * Su responsabilidad única es guardar y cargar el estado de la aplicación en archivos,
 * separando la lógica de persistencia del resto de la lógica de negocio.
 * PRINCIPIO POO: Modularidad - Se aísla la gestión de archivos en un solo módulo.
 */
public class GestionArchivos {

    /**
     * Guarda una lista de objetos serializables en un archivo binario.
     * Este método utiliza genéricos (<T>) para poder operar con cualquier tipo de lista
     * (ej. List<Vehiculo>, List<Mecanico>), promoviendo la reutilización de código.
     *
     * @param <T> El tipo de objeto contenido en la lista.
     * @param lista La lista de objetos a guardar. Todos los objetos deben implementar la interfaz Serializable.
     * @param nombreArchivo El nombre del archivo de destino (ej. "vehiculos.dat").
     * @return {@code true} si los datos se guardaron exitosamente, {@code false} en caso de error de I/O.
     */
    public <T> boolean guardarDatos(List<T> lista, String nombreArchivo) {
        // La estructura 'try-with-resources' asegura el cierre automático del stream,
        // previniendo fugas de recursos del sistema operativo. Es una buena práctica moderna en Java.
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            // PROCESO: Serializa el objeto 'lista' completo y lo escribe en el archivo.
            oos.writeObject(lista);
            System.out.println("Datos guardados exitosamente en " + nombreArchivo);
            return true; // SALIDA: Éxito.
        } catch (IOException e) {
            System.err.println("Error crítico al guardar los datos en " + nombreArchivo + ": " + e.getMessage());
            return false; // SALIDA: Fracaso.
        }
    }

    /**
     * Carga una lista de objetos desde un archivo binario.
     * Utiliza genéricos (<T>) para ser compatible con diferentes tipos de datos.
     *
     * @param <T> El tipo de objeto esperado en la lista.
     * @param nombreArchivo El nombre del archivo desde el cual se cargarán los datos.
     * @return Una {@code List<T>} con los objetos deserializados. Si el archivo no existe o
     *         ocurre un error durante la lectura, devuelve una lista vacía para evitar
     *         errores de tipo NullPointerException en el resto del programa.
     */
    @SuppressWarnings("unchecked") // Suprime la advertencia de casting inseguro, que se maneja con el catch.
    public <T> List<T> cargarDatos(String nombreArchivo) {
        // ENTRADA: Nombre del archivo a leer.
        File archivo = new File(nombreArchivo);
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                // PROCESO: Lee el objeto del archivo y realiza un casting al tipo de lista esperado.
                Object objetoLeido = ois.readObject();
                System.out.println("Datos cargados exitosamente desde " + nombreArchivo);
                return (List<T>) objetoLeido; // SALIDA: Lista con datos.
            } catch (IOException | ClassNotFoundException | ClassCastException e) {
                // Manejo de errores robusto: captura múltiples tipos de excepciones.
                System.err.println("Error al cargar o convertir los datos de " + nombreArchivo + ": " + e.getMessage());
                // En caso de error, es más seguro devolver una lista vacía que null.
                return new ArrayList<>(); // SALIDA: Lista vacía por error.
            }
        }
        // Si el archivo no existe (ej. primera ejecución), no es un error.
        return new ArrayList<>(); // SALIDA: Lista vacía por defecto.
    }
}