/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: ControladorSIRMA.java (Cerebro del Sistema)
 * Descripcion: Gestiona la lógica de negocio, incluyendo el CRUD de vehículos
 *              y la carga de datos de prueba para la simulación.
 * Fecha: Noviembre 2025
 * Version: 1.1
 * -----------------------------------------------------------------------------
 */
package controlador;

import modelo.Mantenimiento;
import modelo.Propietario;
import modelo.Vehiculo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControladorSIRMA {

    // Almacenamiento en memoria de la lista de vehículos.
    private List<Vehiculo> listaVehiculos;

    public ControladorSIRMA() {
        this.listaVehiculos = new ArrayList<>();
        // Al instanciar el controlador, se cargan datos de prueba para la simulación.
        cargarDatosDePrueba();
    }

    /**
     * Registra un nuevo vehículo, validando que la placa no exista previamente.
     * @param v El objeto Vehiculo a registrar.
     * @return true si el registro fue exitoso, false si la placa ya existe.
     */
    public boolean registrarVehiculo(Vehiculo v) {
        if (buscarVehiculoPorPlaca(v.getPlaca()).isPresent()) {
            return false;
        }
        this.listaVehiculos.add(v);
        return true;
    }

    /**
     * Busca un vehículo por su placa (identificador único).
     * @param placa La placa a buscar.
     * @return Un Optional que contiene el Vehiculo si se encuentra.
     */
    public Optional<Vehiculo> buscarVehiculoPorPlaca(String placa) {
        return listaVehiculos.stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa))
                .findFirst();
    }

    /**
     * Agrega un registro de mantenimiento al historial de un vehículo específico.
     * @param placa La placa del vehículo a modificar.
     * @param mantenimiento El nuevo objeto Mantenimiento a agregar.
     * @return true si se agregó correctamente, false si el vehículo no fue encontrado.
     */
    public boolean agregarMantenimientoAVehiculo(String placa, Mantenimiento mantenimiento) {
        Optional<Vehiculo> vehiculoOpt = buscarVehiculoPorPlaca(placa);
        if (vehiculoOpt.isPresent()) {
            vehiculoOpt.get().agregarMantenimiento(mantenimiento);
            return true;
        }
        return false;
    }

    /**
     * Elimina un vehículo del sistema usando su placa.
     * @param placa La placa del vehículo a eliminar.
     * @return true si se encontró y eliminó el vehículo.
     */
    public boolean eliminarVehiculo(String placa) {
        return listaVehiculos.removeIf(v -> v.getPlaca().equalsIgnoreCase(placa));
    }

    /**
     * Retorna la lista completa de vehículos registrados en el sistema.
     * @return Una lista de objetos Vehiculo.
     */
    public List<Vehiculo> obtenerTodosLosVehiculos() {
        return this.listaVehiculos;
    }

    /**
     * Genera un conjunto de datos ficticios (seeding) para la demostración del sistema.
     * Esto asegura que la aplicación tenga contenido al momento de la defensa.
     */
    private void cargarDatosDePrueba() {
        Propietario prop1 = new Propietario("Carlos Rodriguez", "V12345678", "0414-1112233");
        Propietario prop2 = new Propietario("Ana Martinez", "V87654321", "0424-5556677");
        Propietario propJohanna = new Propietario("Johanna Guedez", "V14089807", "0412-9876543");

        Vehiculo vehiculo1 = new Vehiculo("AA123BC", "Toyota", "Corolla", 2022, "Gris", prop1);
        vehiculo1.agregarMantenimiento(new Mantenimiento("Cambio de Aceite", "Aceite 10W-30 Sintético", 50.0, 15000));
        vehiculo1.agregarMantenimiento(new Mantenimiento("Sistema de Frenos", "Cambio de pastillas delanteras", 120.0, 25000));

        Vehiculo vehiculo2 = new Vehiculo("AB456CD", "Ford", "Explorer", 2020, "Negro", prop2);
        vehiculo2.agregarMantenimiento(new Mantenimiento("Escaneo Computarizado", "Revisión de sensor de oxígeno", 30.0, 40000));

        Vehiculo vehiculoJohanna = new Vehiculo("JG1408", "Chevrolet", "Spark", 2018, "Azul", propJohanna);
        vehiculoJohanna.agregarMantenimiento(new Mantenimiento("Alineacion y Balanceo", "Rotación de cauchos y balanceo", 45.0, 60000));

        this.listaVehiculos.add(vehiculo1);
        this.listaVehiculos.add(vehiculo2);
        this.listaVehiculos.add(vehiculoJohanna);
    }
}