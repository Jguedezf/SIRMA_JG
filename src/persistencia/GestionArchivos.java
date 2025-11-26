/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion: Clase responsable de la persistencia de datos. Contiene los
 *              metodos para guardar y cargar la lista de vehiculos en un archivo.
 * Fecha: Noviembre 2025
 * -----------------------------------------------------------------------------
 */
package persistencia;

import modelo.Vehiculo;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionArchivos {

    private final String NOMBRE_ARCHIVO = "sirma_data.dat"; // Nombre del archivo de datos

    /**
     * Metodo: guardarDatos
     * Guarda la lista completa de vehiculos en un archivo binario.
     * @param lista La lista de objetos Vehiculo a guardar.
     */
    public void guardarDatos(List<Vehiculo> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOMBRE_ARCHIVO))) {
            oos.writeObject(lista);
            System.out.println("Datos guardados exitosamente en " + NOMBRE_ARCHIVO);
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    /**
     * Metodo: cargarDatos
     * Carga la lista de vehiculos desde un archivo binario.
     * Si el archivo no existe, retorna una lista vacia.
     * @return Una lista de objetos Vehiculo.
     */
    public List<Vehiculo> cargarDatos() {
        File archivo = new File(NOMBRE_ARCHIVO);
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                List<Vehiculo> lista = (List<Vehiculo>) ois.readObject();
                System.out.println("Datos cargados exitosamente desde " + NOMBRE_ARCHIVO);
                return lista;
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al cargar los datos: " + e.getMessage());
                // Si hay un error, es mas seguro empezar con una lista vacia
                return new ArrayList<>();
            }
        }
        // Si el archivo no existe, es la primera vez que se ejecuta el programa.
        return new ArrayList<>();
    }
}