/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez
 * Profesora: Ing. Dubraska Roca
 * Descripcion: Gestiona la logica principal de la aplicacion (CRUD) y sirve
 *              de puente entre la interfaz de usuario y los datos.
 * Fecha: Noviembre 2025
 * -----------------------------------------------------------------------------
 */
package controlador;

import modelo.Mantenimiento;
import modelo.Propietario;
import modelo.Vehiculo;
import persistencia.GestionArchivos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Clase ControladorSIRMA (Controlador en MVC)
 * Centro de operaciones de la aplicacion. Maneja la logica de negocio y la persistencia.
 */
public class ControladorSIRMA {

    /**
     * Atributo: listaVehiculos
     * Almacena en memoria la coleccion de todos los objetos 'Vehiculo'.
     */
    private List<Vehiculo> listaVehiculos;

    /**
     * Atributo: gestorArchivos
     * Encargado de las operaciones de lectura y escritura en disco.
     */
    private GestionArchivos gestorArchivos;

    /**
     * Constructor de la clase.
     * Inicializa la lista, carga los datos persistentes o genera datos de prueba.
     */
    public ControladorSIRMA() {
        this.gestorArchivos = new GestionArchivos();
        this.listaVehiculos = gestorArchivos.cargarDatos();

        if (this.listaVehiculos.isEmpty()) {
            cargarDatosDePrueba();
            gestorArchivos.guardarDatos(this.listaVehiculos);
        }
    }

    // --- Metodos para el CRUD de Vehiculos ---

    /**
     * Metodo: registrarVehiculo (Operacion 'Create')
     * Anade un nuevo vehiculo a la lista y guarda los cambios.
     * @param v Objeto Vehiculo a registrar.
     * @return true si se registro, false si la placa ya existia.
     */
    public boolean registrarVehiculo(Vehiculo v) {
        if (buscarVehiculoPorPlaca(v.getPlaca()).isPresent()) {
            return false;
        }
        this.listaVehiculos.add(v);
        gestorArchivos.guardarDatos(this.listaVehiculos);
        return true;
    }

    /**
     * Metodo: buscarVehiculoPorPlaca (Operacion 'Read')
     * Busca un vehiculo especifico usando su placa.
     * @param placa String con la placa a buscar.
     * @return Un Optional que contendra el vehiculo si es encontrado.
     */
    public Optional<Vehiculo> buscarVehiculoPorPlaca(String placa) {
        return listaVehiculos.stream()
                .filter(vehiculo -> vehiculo.getPlaca().equalsIgnoreCase(placa))
                .findFirst();
    }

    /**
     * Metodo: agregarMantenimientoAVehiculo (Operacion 'Update' - Vehiculo)
     * Actualiza un vehiculo anadiendo un nuevo mantenimiento a su historial.
     * @param placa Placa del vehiculo a modificar.
     * @param mantenimiento Objeto 'Mantenimiento' a anadir.
     * @return true si la actualizacion fue exitosa.
     */
    public boolean agregarMantenimientoAVehiculo(String placa, Mantenimiento mantenimiento) {
        Optional<Vehiculo> vehiculoOpt = buscarVehiculoPorPlaca(placa);
        if (vehiculoOpt.isPresent()) {
            vehiculoOpt.get().agregarMantenimiento(mantenimiento);
            gestorArchivos.guardarDatos(this.listaVehiculos);
            return true;
        }
        return false;
    }

    /**
     * Metodo: eliminarVehiculo (Operacion 'Delete' - Vehiculo)
     * Elimina un vehiculo de la lista usando su placa.
     * @param placa La placa del vehiculo a eliminar.
     * @return true si se encontro y elimino el vehiculo.
     */
    public boolean eliminarVehiculo(String placa) {
        boolean eliminado = listaVehiculos.removeIf(v -> v.getPlaca().equalsIgnoreCase(placa));
        if (eliminado) {
            gestorArchivos.guardarDatos(this.listaVehiculos);
        }
        return eliminado;
    }

    /**
     * Metodo: eliminarMantenimiento (Operacion 'Delete' - Mantenimiento)
     * Elimina un registro especifico del historial de un vehiculo.
     * @param placa Placa del vehiculo propietario del registro.
     * @param mantenimiento Objeto mantenimiento a eliminar.
     * @return true si se elimino correctamente.
     */
    public boolean eliminarMantenimiento(String placa, Mantenimiento mantenimiento) {
        Optional<Vehiculo> vehiculoOpt = buscarVehiculoPorPlaca(placa);
        if (vehiculoOpt.isPresent()) {
            Vehiculo v = vehiculoOpt.get();
            boolean eliminado = v.getHistorialMantenimientos().remove(mantenimiento);
            if (eliminado) {
                gestorArchivos.guardarDatos(this.listaVehiculos);
                return true;
            }
        }
        return false;
    }

    // --- Metodos Adicionales ---

    /**
     * Metodo: esFormatoPlacaValido
     * Valida que una placa cumpla con los formatos venezolanos.
     * @param placa La placa a validar.
     * @return true si el formato es correcto.
     */
    public boolean esFormatoPlacaValido(String placa) {
        if (placa == null || placa.isEmpty()) return false;
        String placaLimpia = placa.trim().toUpperCase().replace("-", "");
        boolean esFormatoBolivariano = placaLimpia.matches("^[A-Z]{2}[0-9]{3}[A-Z]{2}$");
        boolean esFormatoGeo = placaLimpia.matches("^[A-Z]{3}[0-9]{3}$");
        return esFormatoBolivariano || esFormatoGeo;
    }

    /**
     * Metodo: obtenerTodosLosVehiculos
     * Proporciona a la Vista acceso a la lista completa de vehiculos.
     * @return La lista de todos los objetos 'Vehiculo'.
     */
    public List<Vehiculo> obtenerTodosLosVehiculos() {
        return this.listaVehiculos;
    }

    /**
     * Metodo: cargarDatosDePrueba
     * Genera datos iniciales para la simulacion del sistema si no hay archivo previo.
     */
    private void cargarDatosDePrueba() {
        Propietario prop1 = new Propietario("Carlos Rodriguez", "V12345678", "0414-1112233");
        Propietario prop2 = new Propietario("Ana Martinez", "V87654321", "0424-5556677");
        Propietario propJohanna = new Propietario("Johanna Guedez", "V14089807", "0412-9876543");

        Vehiculo vehiculo1 = new Vehiculo("AA123BC", "Toyota", "Corolla", 2022, "Gris", prop1);
        vehiculo1.agregarMantenimiento(new Mantenimiento("Cambio de Aceite", "Aceite 10W-30 Sintetico", 50.0, 15000));

        Vehiculo vehiculo2 = new Vehiculo("DEF456", "Ford", "Explorer", 2020, "Negro", prop2);

        Vehiculo vehiculoJohanna = new Vehiculo("JGF140", "Chevrolet", "Spark", 2018, "Azul", propJohanna);

        this.listaVehiculos.add(vehiculo1);
        this.listaVehiculos.add(vehiculo2);
        this.listaVehiculos.add(vehiculoJohanna);
    }
}