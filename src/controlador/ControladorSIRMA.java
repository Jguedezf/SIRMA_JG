/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Descripcion: Gestiona la logica principal de la aplicacion (CRUD) y sirve
 *              de puente entre la interfaz de usuario y los datos.
 * Fecha: Noviembre 2025
 * -----------------------------------------------------------------------------
 */
package controlador;

import modelo.Mantenimiento;
import modelo.Propietario;
import modelo.Vehiculo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Clase ControladorSIRMA (Controlador en MVC)
 * Centro de operaciones de la aplicacion. Maneja la logica de negocio.
 */
public class ControladorSIRMA {

    /**
     * Atributo: listaVehiculos
     * Almacena en memoria la coleccion de todos los objetos 'Vehiculo'.
     */
    private List<Vehiculo> listaVehiculos;

    /**
     * Constructor de la clase.
     * Inicializa la lista de vehiculos y carga datos de prueba.
     */
    public ControladorSIRMA() {
        this.listaVehiculos = new ArrayList<>();
        cargarDatosDePrueba();
    }

    // --- Metodos para el CRUD de Vehiculos ---

    /**
     * Metodo: registrarVehiculo (Operacion 'Create')
     * Anade un nuevo vehiculo a la lista, previa validacion de placa no duplicada.
     * @param v Objeto Vehiculo a registrar.
     * @return true si se registro, false si la placa ya existia.
     */
    public boolean registrarVehiculo(Vehiculo v) {
        if (buscarVehiculoPorPlaca(v.getPlaca()).isPresent()) {
            return false;
        }
        this.listaVehiculos.add(v);
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
     * Metodo: agregarMantenimientoAVehiculo (Operacion 'Update')
     * Actualiza un vehiculo anadiendo un nuevo mantenimiento a su historial.
     * @param placa Placa del vehiculo a modificar.
     * @param mantenimiento Objeto 'Mantenimiento' con la informacion del nuevo servicio.
     * @return true si la actualizacion fue exitosa.
     */
    public boolean agregarMantenimientoAVehiculo(String placa, Mantenimiento mantenimiento) {
        Optional<Vehiculo> vehiculoOpt = buscarVehiculoPorPlaca(placa);
        if (vehiculoOpt.isPresent()) {
            vehiculoOpt.get().agregarMantenimiento(mantenimiento);
            return true;
        }
        return false;
    }

    // --- Metodos Adicionales ---

    /**
     * Metodo: esFormatoPlacaValido
     * Valida que una placa cumpla con los formatos venezolanos (actual o anterior).
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
     * Genera datos iniciales para la simulacion del sistema.
     */
    private void cargarDatosDePrueba() {
        Propietario prop1 = new Propietario("Carlos Rodriguez", "V12345678", "0414-1112233");
        Propietario propJohanna = new Propietario("Johanna Guedez", "V14089807", "0412-9876543");

        Vehiculo vehiculo1 = new Vehiculo("AA123BC", "Toyota", "Corolla", 2022, "Gris", prop1);
        vehiculo1.agregarMantenimiento(new Mantenimiento("Cambio de Aceite", "Aceite 10W-30 Sintetico", 50.0, 15000));

        // CORREGIDO: La placa "JGF140" cumple con el formato "Geo".
        Vehiculo vehiculoJohanna = new Vehiculo("JGF140", "Chevrolet", "Spark", 2018, "Azul", propJohanna);

        this.listaVehiculos.add(vehiculo1);
        this.listaVehiculos.add(vehiculoJohanna);
    }
}