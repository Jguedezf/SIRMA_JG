/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: ControladorSIRMA.java
 * Descripcion: Gestiona la logica de negocio e integra la capa de persistencia.
 * Fecha: Noviembre 2025
 * Version: 1.9
 * -----------------------------------------------------------------------------
 */
package controlador;

import modelo.Mantenimiento;
import modelo.Propietario;
import modelo.Vehiculo;
import persistencia.GestionArchivos; // <-- IMPORTANTE: Importamos la nueva clase

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControladorSIRMA {

    private List<Vehiculo> listaVehiculos;
    private GestionArchivos gestorArchivos; // <-- NUEVO: Atributo para manejar archivos

    public ControladorSIRMA() {
        this.gestorArchivos = new GestionArchivos(); // Creamos la instancia
        this.listaVehiculos = gestorArchivos.cargarDatos(); // Cargamos datos al iniciar

        // Si la lista esta vacia (primera ejecucion o archivo corrupto), cargamos datos de prueba.
        if (this.listaVehiculos.isEmpty()) {
            cargarDatosDePrueba();
            gestorArchivos.guardarDatos(this.listaVehiculos); // Guardamos los datos de prueba iniciales
        }
    }

    // --- Metodos CRUD (Ahora con guardado automatico) ---

    public boolean registrarVehiculo(Vehiculo v) {
        if (buscarVehiculoPorPlaca(v.getPlaca()).isPresent()) {
            return false;
        }
        this.listaVehiculos.add(v);
        gestorArchivos.guardarDatos(this.listaVehiculos); // Guardar despues de anadir
        return true;
    }

    public boolean agregarMantenimientoAVehiculo(String placa, Mantenimiento mantenimiento) {
        Optional<Vehiculo> vehiculoOpt = buscarVehiculoPorPlaca(placa);
        if (vehiculoOpt.isPresent()) {
            vehiculoOpt.get().agregarMantenimiento(mantenimiento);
            gestorArchivos.guardarDatos(this.listaVehiculos); // Guardar despues de actualizar
            return true;
        }
        return false;
    }

    public boolean eliminarVehiculo(String placa) {
        boolean eliminado = listaVehiculos.removeIf(v -> v.getPlaca().equalsIgnoreCase(placa));
        if (eliminado) {
            gestorArchivos.guardarDatos(this.listaVehiculos); // Guardar despues de eliminar
        }
        return eliminado;
    }

    // --- El resto de metodos no cambian ---
    public Optional<Vehiculo> buscarVehiculoPorPlaca(String placa) {
        return listaVehiculos.stream()
                .filter(vehiculo -> vehiculo.getPlaca().equalsIgnoreCase(placa))
                .findFirst();
    }

    public boolean esFormatoPlacaValido(String placa) {
        if (placa == null || placa.isEmpty()) return false;
        String placaLimpia = placa.trim().toUpperCase().replace("-", "");
        boolean esFormatoBolivariano = placaLimpia.matches("^[A-Z]{2}[0-9]{3}[A-Z]{2}$");
        boolean esFormatoGeo = placaLimpia.matches("^[A-Z]{3}[0-9]{3}$");
        return esFormatoBolivariano || esFormatoGeo;
    }

    public List<Vehiculo> obtenerTodosLosVehiculos() {
        return this.listaVehiculos;
    }

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