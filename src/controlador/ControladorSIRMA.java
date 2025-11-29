/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: ControladorSIRMA.java
 * Descripcion: Controlador Principal con carga masiva de datos de prueba.
 * Fecha: Noviembre 2025
 * Version: 9.3
 * -----------------------------------------------------------------------------
 */
package controlador;

import modelo.Mecanico;
import modelo.Mantenimiento;
import modelo.Propietario;
import modelo.Vehiculo;
import persistencia.GestionArchivos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ControladorSIRMA {

    private final List<Vehiculo> listaVehiculos;
    private final List<Mecanico> listaMecanicos;
    private final GestionArchivos gestorArchivos;
    private static final String ARCHIVO_VEHICULOS = "sirma_vehiculos.dat";
    private static final String ARCHIVO_MECANICOS = "sirma_mecanicos.dat";

    public ControladorSIRMA() {
        this.gestorArchivos = new GestionArchivos();
        this.listaVehiculos = gestorArchivos.cargarDatos(ARCHIVO_VEHICULOS);
        this.listaMecanicos = gestorArchivos.cargarDatos(ARCHIVO_MECANICOS);

        if (this.listaVehiculos.isEmpty()) {
            cargarDatosDePrueba();
        }
    }

    public Mantenimiento agregarMantenimientoAVehiculo(String placa, Mantenimiento mant) {
        Optional<Vehiculo> vehiculoOpt = buscarVehiculoPorPlaca(placa);
        if (vehiculoOpt.isPresent()) {
            mant.setIdOrden(generarNuevoIdOrden());
            vehiculoOpt.get().agregarMantenimiento(mant);
            gestorArchivos.guardarDatos(listaVehiculos, ARCHIVO_VEHICULOS);
            return mant;
        }
        return null;
    }

    private void cargarDatosDePrueba() {
        // Mecanicos
        listaMecanicos.add(new Mecanico("Pedro Perez", "Aceite"));
        listaMecanicos.add(new Mecanico("Carlos Villa", "Frenos"));
        listaMecanicos.add(new Mecanico("Juan Torres", "Alineacion"));
        listaMecanicos.add(new Mecanico("Roberto Castillo", "Escaneo"));
        listaMecanicos.add(new Mecanico("Luis Mendez", "Aire Acondicionado"));

        // Propietarios y Vehiculos
        Propietario p1 = new Propietario("Carlos Rodriguez", "V-12.345.678", "0414-1112233");
        Propietario pJohanna = new Propietario("Johanna Guedez", "V-14.089.807", "0412-9876543");
        Propietario p3 = new Propietario("Maria Fernandez", "V-18.765.432", "0416-5554433");
        Propietario p4 = new Propietario("Diego Salazar", "V-20.123.456", "0424-8889900");

        Vehiculo v1 = new Vehiculo("AA123BC", "TOYOTA", "Corolla", 2022, "Gris", p1);
        Vehiculo v2 = new Vehiculo("JGF140", "CHEVROLET", "Spark", 2018, "Azul", pJohanna);
        Vehiculo v3 = new Vehiculo("AB456CD", "FORD", "Fiesta", 2015, "Rojo", p3);
        Vehiculo v4 = new Vehiculo("AE789FG", "HYUNDAI", "Tucson", 2021, "Blanco", p4);

        // Ordenes de Servicio
        v1.agregarMantenimiento(new Mantenimiento("ORD-001", "Cambio de Aceite y Filtro", "Aceite 10W-30", 40, 20, 15000, LocalDate.now().minusDays(30), "Finalizado"));
        v1.agregarMantenimiento(new Mantenimiento("ORD-002", "Revision de Frenos", "Pastillas delanteras", 80, 50, 18000, LocalDate.now().minusDays(15), "Finalizado"));
        v2.agregarMantenimiento(new Mantenimiento("ORD-003", "Escaneo Computarizado", "Fallo P0420", 50, 0, 85000, LocalDate.now().minusDays(5), "En Proceso"));
        v3.agregarMantenimiento(new Mantenimiento("ORD-004", "Alineacion y Balanceo", "", 30, 0, 62000, LocalDate.now().minusDays(2), "En Proceso"));
        v4.agregarMantenimiento(new Mantenimiento("ORD-005", "Mantenimiento Aire Acondicionado", "Recarga de gas", 100, 25, 40000, LocalDate.now().minusDays(1), "Pendiente"));
        v1.agregarMantenimiento(new Mantenimiento("ORD-006", "Rotacion de Cauchos", "", 15, 0, 20000, LocalDate.now(), "Pendiente"));
        v2.agregarMantenimiento(new Mantenimiento("ORD-007", "Cambio de Aceite y Filtro", "", 45, 20, 90000, LocalDate.now().minusDays(60), "Finalizado"));
        v3.agregarMantenimiento(new Mantenimiento("ORD-008", "Revision de Frenos", "Liquido de frenos bajo", 20, 5, 65000, LocalDate.now().minusDays(90), "Finalizado"));
        v4.agregarMantenimiento(new Mantenimiento("ORD-009", "Escaneo Computarizado", "Luz de motor encendida", 50, 0, 42000, LocalDate.now().minusDays(4), "En Proceso"));
        v2.agregarMantenimiento(new Mantenimiento("ORD-010", "Alineacion y Balanceo", "Vibracion en volante", 30, 0, 95000, LocalDate.now().minusDays(3), "En Proceso"));

        listaVehiculos.add(v1);
        listaVehiculos.add(v2);
        listaVehiculos.add(v3);
        listaVehiculos.add(v4);

        gestorArchivos.guardarDatos(listaMecanicos, ARCHIVO_MECANICOS);
        gestorArchivos.guardarDatos(listaVehiculos, ARCHIVO_VEHICULOS);
    }

    public boolean registrarVehiculo(Vehiculo v) { if (buscarVehiculoPorPlaca(v.getPlaca()).isPresent()) { return false; } listaVehiculos.add(v); return gestorArchivos.guardarDatos(listaVehiculos, ARCHIVO_VEHICULOS); }
    public boolean actualizarVehiculo(String p, Vehiculo v) { for (int i = 0; i < listaVehiculos.size(); i++) { if (listaVehiculos.get(i).getPlaca().equalsIgnoreCase(p)) { listaVehiculos.set(i, v); return gestorArchivos.guardarDatos(listaVehiculos, ARCHIVO_VEHICULOS); } } return false; }
    public boolean eliminarVehiculo(String p) { boolean e = listaVehiculos.removeIf(v -> v.getPlaca().equalsIgnoreCase(p)); if (e) { gestorArchivos.guardarDatos(listaVehiculos, ARCHIVO_VEHICULOS); } return e; }
    public List<Vehiculo> obtenerTodosLosVehiculos() { return listaVehiculos; }
    public Optional<Vehiculo> buscarVehiculoPorPlaca(String p) { return listaVehiculos.stream().filter(v -> v.getPlaca().equalsIgnoreCase(p)).findFirst(); }
    public boolean agregarMecanico(Mecanico m) { if (listaMecanicos.stream().anyMatch(me -> me.getNombreCompleto().equalsIgnoreCase(m.getNombreCompleto()))) { return false; } listaMecanicos.add(m); return gestorArchivos.guardarDatos(listaMecanicos, ARCHIVO_MECANICOS); }
    public boolean actualizarMecanico(String n, Mecanico m) { for (int i = 0; i < listaMecanicos.size(); i++) { if (listaMecanicos.get(i).getNombreCompleto().equalsIgnoreCase(n)) { listaMecanicos.set(i, m); return gestorArchivos.guardarDatos(listaMecanicos, ARCHIVO_MECANICOS); } } return false; }
    public boolean eliminarMecanico(String n) { boolean e = listaMecanicos.removeIf(m -> m.getNombreCompleto().equalsIgnoreCase(n)); if (e) { gestorArchivos.guardarDatos(listaMecanicos, ARCHIVO_MECANICOS); } return e; }
    public List<Mecanico> obtenerTodosLosMecanicos() { return listaMecanicos; }
    public boolean actualizarMantenimiento(Mantenimiento m) { for (Vehiculo v : listaVehiculos) { for (int i = 0; i < v.getHistorialMantenimientos().size(); i++) { if (v.getHistorialMantenimientos().get(i).getIdOrden().equals(m.getIdOrden())) { v.getHistorialMantenimientos().set(i, m); return gestorArchivos.guardarDatos(listaVehiculos, ARCHIVO_VEHICULOS); } } } return false; }
    public boolean eliminarMantenimiento(String id) { for (Vehiculo v : listaVehiculos) { boolean e = v.getHistorialMantenimientos().removeIf(m -> m.getIdOrden().equals(id)); if (e) { return gestorArchivos.guardarDatos(listaVehiculos, ARCHIVO_VEHICULOS); } } return false; }
    public List<Mantenimiento> obtenerTodasLasOrdenes() { return listaVehiculos.stream().flatMap(v -> v.getHistorialMantenimientos().stream()).collect(Collectors.toList()); }
    public Optional<VehiculoYMantenimiento> buscarVehiculoPorIdOrden(String id) { for (Vehiculo v : listaVehiculos) { for (Mantenimiento m : v.getHistorialMantenimientos()) { if (m.getIdOrden().equalsIgnoreCase(id)) { return Optional.of(new VehiculoYMantenimiento(v, m)); } } } return Optional.empty(); }
    public String asignarMecanicoInteligente(String ts) { if (ts == null || ts.contains("---")) { return "Por Asignar"; } String sn = ts.toLowerCase(); for (Mecanico m : listaMecanicos) { String en = m.getEspecialidad().toLowerCase(); if (sn.contains(en)) { return m.getNombreCompleto(); } } return "Por Asignar"; }
    public List<Mantenimiento> buscarOrdenesConFiltros(String t, String e, String ds, String hs) { DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-yyyy"); LocalDate fd = null; LocalDate fh = null; try { if (ds != null && !ds.isEmpty()) fd = LocalDate.parse(ds, f); if (hs != null && !hs.isEmpty()) fh = LocalDate.parse(hs, f); } catch (DateTimeParseException ex) { System.err.println("Fecha invalida: " + ex.getMessage()); } final LocalDate fDesde = fd; final LocalDate fHasta = fh; return obtenerTodasLasOrdenes().stream().filter(m -> { Optional<VehiculoYMantenimiento> r = buscarVehiculoPorIdOrden(m.getIdOrden()); if (!r.isPresent()) return false; String pv = r.get().vehiculo.getPlaca().toUpperCase(); String id = m.getIdOrden().toUpperCase(); String tb = t.toUpperCase(); return t.isEmpty() || pv.contains(tb) || id.contains(tb); }).filter(m -> { return e.equals("Cualquier Estado") || m.getEstado().equals(e); }).filter(m -> { return fDesde == null || !m.getFechaRealizacion().isBefore(fDesde); }).filter(m -> { return fHasta == null || !m.getFechaRealizacion().isAfter(fHasta); }).collect(Collectors.toList()); }
    private String generarNuevoIdOrden() { long t = obtenerTodasLasOrdenes().size(); return String.format("ORD-%03d", t + 1); }
    public static class VehiculoYMantenimiento { public final Vehiculo vehiculo; public final Mantenimiento mantenimiento; public VehiculoYMantenimiento(Vehiculo v, Mantenimiento m) { this.vehiculo = v; this.mantenimiento = m; } }
}