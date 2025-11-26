/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: Main.java (Punto de Entrada)
 * Descripcion: Clase principal que inicia la aplicación. Por ahora,
 *              se usa para probar la lógica del controlador en la consola.
 * Fecha: Noviembre 2025
 * Version: 1.1 (Sin dependencias externas)
 * -----------------------------------------------------------------------------
 */

import controlador.ControladorSIRMA;
import modelo.Vehiculo;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- INICIANDO SISTEMA SIRMA JG (PRUEBAS DE CONSOLA) ---");

        // 1. Creamos una instancia del cerebro del sistema.
        // Al hacer esto, el constructor del controlador llamará a cargarDatosDePrueba()
        ControladorSIRMA controlador = new ControladorSIRMA();

        System.out.println("\n--- PRUEBA 1: VERIFICAR DATOS DE PRUEBA ---");
        System.out.println("Total de vehículos registrados: " + controlador.obtenerTodosLosVehiculos().size());
        for (Vehiculo v : controlador.obtenerTodosLosVehiculos()) {
            System.out.println("-> Placa: " + v.getPlaca() + ", Propietario: " + v.getPropietario().getNombreCompleto());
        }

        System.out.println("\n--- PRUEBA 2: BUSCAR UN VEHÍCULO ESPECÍFICO (TU CARRO) ---");
        String placaABuscar = "JG1408";
        Optional<Vehiculo> vehiculoEncontrado = controlador.buscarVehiculoPorPlaca(placaABuscar);

        if (vehiculoEncontrado.isPresent()) {
            Vehiculo tuVehiculo = vehiculoEncontrado.get();
            System.out.println("Vehículo encontrado: " + tuVehiculo.getMarca() + " " + tuVehiculo.getModelo());
            System.out.println("Historial de mantenimientos:");
            // Mostramos el historial usando los getters que definimos
            tuVehiculo.getHistorialMantenimientos().forEach(m -> {
                System.out.println("   - Servicio: " + m.getTipoServicio() +
                        ", Fecha: " + m.getFechaRealizacion() +
                        ", Próximo: " + m.getFechaProximoServicio());
            });
        } else {
            System.out.println("No se encontró el vehículo con placa " + placaABuscar);
        }

        System.out.println("\n--- PRUEBA 4: ELIMINAR UN VEHÍCULO ---");
        String placaAEliminar = "AB456CD";
        System.out.println("Intentando eliminar vehículo con placa: " + placaAEliminar);
        boolean eliminado = controlador.eliminarVehiculo(placaAEliminar);
        if (eliminado) {
            System.out.println("¡Vehículo eliminado con éxito!");
        } else {
            System.out.println("No se pudo eliminar el vehículo.");
        }
        System.out.println("Total de vehículos ahora: " + controlador.obtenerTodosLosVehiculos().size());

    }
}