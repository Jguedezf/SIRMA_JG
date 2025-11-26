/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: ControladorSIRMA.java (Cerebro del Sistema)
 * Descripcion: Gestiona toda la lógica de negocio (CRUD, listas, cálculos).
 *              No contiene código de interfaz gráfica, solo la inteligencia.
 * Fecha: Noviembre 2025
 * Version: 1.1 (Sin dependencias externas)
 * -----------------------------------------------------------------------------
 */
package controlador;

import modelo.Mantenimiento;
import modelo.Propietario;
import modelo.Vehiculo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControladorSIRMA {

    // Esta es la "Base de Datos" en memoria. Aquí vivirá toda la información.
    private List<Vehiculo> listaVehiculos;

    public ControladorSIRMA() {
        this.listaVehiculos = new ArrayList<>();
        // ¡Magia! Al iniciar, cargamos datos de prueba para la defensa.
        cargarDatosDePrueba();
    }

    /**
     * FUNCIÓN 1 (CREATE): Registra un nuevo vehículo en el sistema.
     * Valida que la placa no esté ya registrada.
     * @param v El vehículo a registrar.
     * @return true si se registró con éxito, false si la placa ya existía.
     */
    public boolean registrarVehiculo(Vehiculo v) {
        if (buscarVehiculoPorPlaca(v.getPlaca()).isPresent()) {
            System.out.println("Error: La placa " + v.getPlaca() + " ya está registrada.");
            return false;
        }
        this.listaVehiculos.add(v);
        return true;
    }

    /**
     * FUNCIÓN 2 (READ): Busca un vehículo por su placa.
     * @param placa La placa a buscar.
     * @return Un Optional que contiene el Vehiculo si se encuentra, o vacío si no.
     */
    public Optional<Vehiculo> buscarVehiculoPorPlaca(String placa) {
        return listaVehiculos.stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa))
                .findFirst();
    }

    /**
     * FUNCIÓN 3 (UPDATE): Agrega un nuevo registro de mantenimiento a un vehículo existente.
     * @param placa La placa del vehículo.
     * @param mantenimiento El nuevo mantenimiento a agregar.
     * @return true si se agregó, false si el vehículo no se encontró.
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
     * FUNCIÓN 4 (DELETE): Elimina un vehículo del sistema.
     * @param placa La placa del vehículo a eliminar.
     * @return true si se eliminó, false si no se encontró.
     */
    public boolean eliminarVehiculo(String placa) {
        return listaVehiculos.removeIf(v -> v.getPlaca().equalsIgnoreCase(placa));
    }

    /**
     * Método auxiliar para que la interfaz gráfica pueda mostrar todos los vehículos.
     * @return La lista completa de vehículos.
     */
    public List<Vehiculo> obtenerTodosLosVehiculos() {
        return this.listaVehiculos;
    }

    /**
     * MÉTODO CLAVE PARA LA DEFENSA: Carga de Datos Ficticios ("Seeding").
     * Esto inyecta "vida artificial" al programa para que no se vea vacío.
     */
    private void cargarDatosDePrueba() {
        System.out.println("Cargando datos de prueba para la simulación...");

        // Propietarios de prueba
        Propietario prop1 = new Propietario("Carlos Rodriguez", "V12345678", "0414-1112233");
        Propietario prop2 = new Propietario("Ana Martinez", "V87654321", "0424-5556677");
        Propietario propJohanna = new Propietario("Johanna Guedez", "V14089807", "0412-9876543");

        // Vehículos de prueba
        Vehiculo vehiculo1 = new Vehiculo("AA123BC", "Toyota", "Corolla", 2022, "Gris", prop1);
        Vehiculo vehiculo2 = new Vehiculo("AB456CD", "Ford", "Explorer", 2020, "Negro", prop2);
        Vehiculo vehiculoJohanna = new Vehiculo("JG1408", "Chevrolet", "Spark", 2018, "Azul", propJohanna);

        // Mantenimientos para el Vehículo 1
        vehiculo1.agregarMantenimiento(new Mantenimiento("Cambio de Aceite", "Aceite 10W-30 Sintético", 50.0, 15000));
        vehiculo1.agregarMantenimiento(new Mantenimiento("Sistema de Frenos", "Cambio de pastillas delanteras", 120.0, 25000));

        // Mantenimientos para el Vehículo 2
        vehiculo2.agregarMantenimiento(new Mantenimiento("Escaneo Computarizado", "Revisión de sensor de oxígeno", 30.0, 40000));

        // Mantenimientos para tu vehículo
        vehiculoJohanna.agregarMantenimiento(new Mantenimiento("Alineacion y Balanceo", "Rotación de cauchos y balanceo", 45.0, 60000));

        // Agregamos los vehículos al sistema
        this.listaVehiculos.add(vehiculo1);
        this.listaVehiculos.add(vehiculo2);
        this.listaVehiculos.add(vehiculoJohanna);

        System.out.println("¡Datos cargados! Sistema listo para la demostración.");
    }
}
