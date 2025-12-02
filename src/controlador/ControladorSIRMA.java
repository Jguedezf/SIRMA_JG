/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación III - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      ControladorSIRMA.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Clase Controlador principal bajo el patrón arquitectónico MVC (Modelo-Vista-Controlador).
 * Centraliza toda la lógica de negocio, gestiona el estado de la aplicación,
 * coordina la comunicación con la capa de persistencia y responde a los eventos
 * provenientes de la capa de Vista.
 * ============================================================================
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Clase ControladorSIRMA que implementa la lógica de negocio del sistema.
 * Actúa como intermediario entre el Modelo (datos) y la Vista (interfaz gráfica).
 * PRINCIPIO POO: Modularidad - Descompone el sistema en módulos (MVC) para
 * facilitar el mantenimiento y la organización del código.
 */
public class ControladorSIRMA {

    // --- ATRIBUTOS ---
    // Representan la "base de datos" en memoria de la aplicación.
    private List<Vehiculo> listaVehiculos;
    private List<Mecanico> listaMecanicos;
    private final GestionArchivos gestorArchivos; // Objeto para manejar la persistencia.

    // Constantes para los nombres de archivo, evitando "magic strings".
    private static final String ARCHIVO_VEHICULOS = "sirma_vehiculos.dat";
    private static final String ARCHIVO_MECANICOS = "sirma_mecanicos.dat";

    /**
     * DTO (Data Transfer Object) para empaquetar un Vehículo y su Mantenimiento.
     * Facilita el transporte de datos relacionados entre capas.
     */
    public static class VehiculoYMantenimiento {
        public final Vehiculo vehiculo;
        public final Mantenimiento mantenimiento;
        public VehiculoYMantenimiento(Vehiculo v, Mantenimiento m) { this.vehiculo = v; this.mantenimiento = m; }
    }

    /**
     * Constructor del Controlador.
     * PROCESO:
     * 1. Instancia el gestor de persistencia.
     * 2. Intenta cargar los datos desde los archivos .dat.
     * 3. Si no hay datos (primera ejecución), llama al método de inicialización (Data Seeding).
     * PRINCIPIO POO: Persistencia - El estado de los objetos se carga al iniciar y se
     * guarda, existiendo más allá de una sola ejecución del programa.
     */
    @SuppressWarnings("unchecked")
    public ControladorSIRMA() {
        this.gestorArchivos = new GestionArchivos();
        Object datosVehiculos = gestorArchivos.cargarDatos(ARCHIVO_VEHICULOS);
        Object datosMecanicos = gestorArchivos.cargarDatos(ARCHIVO_MECANICOS);

        // Casting seguro: si la carga falla o el archivo está corrupto, se crea una lista vacía.
        this.listaVehiculos = (datosVehiculos instanceof List) ? (List<Vehiculo>) datosVehiculos : new ArrayList<>();
        this.listaMecanicos = (datosMecanicos instanceof List) ? (List<Mecanico>) datosMecanicos : new ArrayList<>();

        // Si la base de datos está vacía, se puebla con datos de prueba.
        if (this.listaVehiculos.isEmpty()) {
            cargarDatosDePrueba();
        }
    }

    // ============================================================================
    // SECCIÓN 1: LÓGICA DE BÚSQUEDA Y CONSULTAS
    // ============================================================================

    /**
     * Función 1: Búsqueda Avanzada de Órdenes de Servicio.
     * Realiza un filtrado multicriterio sobre todas las órdenes del sistema.
     * @param texto Criterio de búsqueda por Placa o ID de Orden.
     * @param estado Filtro por estado ("Pendiente", "En Proceso", "Finalizado").
     * @param fechaDesdeStr Fecha de inicio del rango de búsqueda (formato dd-MM-yyyy).
     * @param fechaHastaStr Fecha de fin del rango de búsqueda (formato dd-MM-yyyy).
     * @return Una matriz de objetos (Object[][]) formateada para ser mostrada directamente en una JTable.
     */
    public Object[][] buscarOrdenesAvanzado(String texto, String estado, String fechaDesdeStr, String fechaHastaStr) {
        // ENTRADA: Se reciben los criterios de búsqueda desde la Vista.
        String txt = (texto == null) ? "" : texto.toUpperCase().trim();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fD = null, fH = null;

        // Validación de fechas para evitar errores de parseo.
        try {
            if (fechaDesdeStr != null && !fechaDesdeStr.isEmpty()) fD = LocalDate.parse(fechaDesdeStr, fmt);
            if (fechaHastaStr != null && !fechaHastaStr.isEmpty()) fH = LocalDate.parse(fechaHastaStr, fmt);
        } catch (DateTimeParseException e) {
            // Si el formato es incorrecto, la fecha se ignora (se mantiene en null).
        }

        final LocalDate d = fD; final LocalDate h = fH;
        ArrayList<Object[]> filas = new ArrayList<>();

        // PROCESO: Se itera sobre la lista de vehículos y sus mantenimientos para aplicar los filtros.
        for (Vehiculo v : listaVehiculos) {
            for (Mantenimiento m : v.getHistorialMantenimientos()) {
                // Aplicación de condiciones lógicas para el filtrado.
                boolean cTxt = txt.isEmpty() || v.getPlaca().toUpperCase().contains(txt) || m.getIdOrden().toUpperCase().contains(txt);
                boolean cEst = estado.equals("Todos") || m.getEstado().equalsIgnoreCase(estado);
                boolean cFec = (d == null || !m.getFechaRealizacion().isBefore(d)) && (h == null || !m.getFechaRealizacion().isAfter(h));

                if (cTxt && cEst && cFec) {
                    filas.add(new Object[]{
                            m.getIdOrden(),
                            v.getPlaca(),
                            v.getMarca(),
                            m.getTipoServicio(),
                            m.getFechaRealizacion(),
                            m.getEstado(),
                            v.getPropietario().getCedula(),
                            String.format(Locale.US, "%.2f", m.getCostoTotal())
                    });
                }
            }
        }

        // SALIDA: Se convierte la lista de resultados a una matriz para la JTable.
        Object[][] data = new Object[filas.size()][8];
        for(int i=0; i<filas.size(); i++) data[i] = filas.get(i);
        return data;
    }

    // ============================================================================
    // SECCIÓN 2: OPERACIONES CRUD (Create, Read, Update, Delete)
    // ============================================================================

    // --- CRUD de Vehículos ---

    /**
     * Función 2: Registrar un nuevo vehículo.
     * @param v Objeto Vehiculo a registrar.
     * @return true si se registró, false si la placa ya existía.
     */
    public boolean registrarVehiculo(Vehiculo v) {
        // Validación: No permitir placas duplicadas.
        if (buscarVehiculoPorPlaca(v.getPlaca()).isPresent()) return false;
        listaVehiculos.add(v);
        return gestorArchivos.guardarDatos(listaVehiculos, ARCHIVO_VEHICULOS);
    }

    /**
     * Función 3: Actualizar un vehículo existente.
     * @param p Placa original del vehículo a modificar.
     * @param v Objeto Vehiculo con los nuevos datos.
     * @return true si la actualización fue exitosa.
     */
    public boolean actualizarVehiculo(String p, Vehiculo v) {
        for(int i=0; i<listaVehiculos.size(); i++) {
            if(listaVehiculos.get(i).getPlaca().equalsIgnoreCase(p)) {
                listaVehiculos.set(i, v);
                return gestorArchivos.guardarDatos(listaVehiculos, ARCHIVO_VEHICULOS);
            }
        }
        return false;
    }

    /**
     * Función 4: Eliminar un vehículo por su placa.
     * @param p Placa del vehículo a eliminar.
     * @return true si se encontró y eliminó el vehículo.
     */
    public boolean eliminarVehiculo(String p) {
        boolean eliminado = listaVehiculos.removeIf(v -> v.getPlaca().equalsIgnoreCase(p));
        if(eliminado) gestorArchivos.guardarDatos(listaVehiculos, ARCHIVO_VEHICULOS);
        return eliminado;
    }

    /**
     * Función 5: Consultar un vehículo por su placa.
     * @param p Placa a buscar.
     * @return Optional<Vehiculo> para manejar de forma segura la posible ausencia del vehículo.
     */
    public Optional<Vehiculo> buscarVehiculoPorPlaca(String p) {
        // Uso de API Stream para una búsqueda funcional y más legible.
        return listaVehiculos.stream().filter(v -> v.getPlaca().equalsIgnoreCase(p)).findFirst();
    }

    /**
     * Función 6: Consultar todos los vehículos.
     * @return Lista completa de vehículos registrados.
     */
    public List<Vehiculo> obtenerTodosLosVehiculos() { return listaVehiculos; }

    // --- CRUD de Mecánicos ---

    /**
     * Función 7: Registrar un nuevo mecánico.
     * @param m Objeto Mecanico a registrar.
     * @return true si la operación de guardado fue exitosa.
     */
    public boolean agregarMecanico(Mecanico m) {
        listaMecanicos.add(m);
        return gestorArchivos.guardarDatos(listaMecanicos, ARCHIVO_MECANICOS);
    }

    /**
     * Función 8: Actualizar un mecánico.
     * @param n Nombre original del mecánico.
     * @param m Objeto Mecanico con los datos actualizados.
     * @return true si la actualización fue exitosa.
     */
    public boolean actualizarMecanico(String n, Mecanico m) {
        for(int i=0; i<listaMecanicos.size(); i++) {
            if(listaMecanicos.get(i).getNombreCompleto().equalsIgnoreCase(n)) {
                listaMecanicos.set(i, m);
                return gestorArchivos.guardarDatos(listaMecanicos, ARCHIVO_MECANICOS);
            }
        }
        return false;
    }

    /**
     * Función 9: Eliminar un mecánico.
     * @param n Nombre del mecánico a eliminar.
     * @return true si se encontró y eliminó.
     */
    public boolean eliminarMecanico(String n) {
        boolean eliminado = listaMecanicos.removeIf(m -> m.getNombreCompleto().equalsIgnoreCase(n));
        if(eliminado) gestorArchivos.guardarDatos(listaMecanicos, ARCHIVO_MECANICOS);
        return eliminado;
    }

    /**
     * Función 10: Consultar todos los mecánicos.
     * @return Lista completa de mecánicos.
     */
    public List<Mecanico> obtenerTodosLosMecanicos() { return listaMecanicos; }

    // --- CRUD de Mantenimientos ---

    /**
     * Función 11: Agregar una nueva orden de servicio a un vehículo.
     * @param p Placa del vehículo al que pertenece la orden.
     * @param m Objeto Mantenimiento a agregar.
     * @return El objeto Mantenimiento con su ID asignado, o null si el vehículo no existe.
     */
    public Mantenimiento agregarMantenimientoAVehiculo(String p, Mantenimiento m) {
        Optional<Vehiculo> v = buscarVehiculoPorPlaca(p);
        if(v.isPresent()) {
            if(m.getIdOrden() == null) m.setIdOrden(generarNuevoIdOrden());
            v.get().agregarMantenimiento(m);
            gestorArchivos.guardarDatos(listaVehiculos, ARCHIVO_VEHICULOS);
            return m;
        }
        return null;
    }

    /**
     * Función 12: Actualizar una orden de servicio.
     * @param m Objeto Mantenimiento con los datos actualizados.
     * @return true si la actualización fue exitosa.
     */
    public boolean actualizarMantenimiento(Mantenimiento m) {
        for(Vehiculo v : listaVehiculos) {
            for(int i=0; i<v.getHistorialMantenimientos().size(); i++) {
                if(v.getHistorialMantenimientos().get(i).getIdOrden().equals(m.getIdOrden())) {
                    v.getHistorialMantenimientos().set(i, m);
                    return gestorArchivos.guardarDatos(listaVehiculos, ARCHIVO_VEHICULOS);
                }
            }
        }
        return false;
    }

    /**
     * Función 13: Eliminar una orden de servicio.
     * @param id ID de la orden a eliminar.
     * @return true si se encontró y eliminó.
     */
    public boolean eliminarMantenimiento(String id) {
        for(Vehiculo v : listaVehiculos) {
            if(v.getHistorialMantenimientos().removeIf(m -> m.getIdOrden().equals(id))) {
                return gestorArchivos.guardarDatos(listaVehiculos, ARCHIVO_VEHICULOS);
            }
        }
        return false;
    }

    /**
     * Función 14: Buscar una orden y el vehículo al que pertenece por el ID de la orden.
     * @param id ID de la orden a buscar.
     * @return Un Optional que contiene el DTO VehiculoYMantenimiento.
     */
    public Optional<VehiculoYMantenimiento> buscarVehiculoPorIdOrden(String id) {
        for(Vehiculo v : listaVehiculos)
            for(Mantenimiento m : v.getHistorialMantenimientos())
                if(m.getIdOrden().equalsIgnoreCase(id)) return Optional.of(new VehiculoYMantenimiento(v, m));
        return Optional.empty();
    }

    /**
     * Función 15: Obtener una lista plana con todas las órdenes de todos los vehículos.
     * @return Lista de todos los mantenimientos.
     */
    public List<Mantenimiento> obtenerTodasLasOrdenes() {
        return listaVehiculos.stream()
                .flatMap(v -> v.getHistorialMantenimientos().stream())
                .collect(Collectors.toList());
    }

    // ============================================================================
    // SECCIÓN 3: MÉTODOS DE LÓGICA DE NEGOCIO Y UTILITARIOS
    // ============================================================================

    /**
     * Genera un nuevo ID único para una orden de servicio.
     * @return Un String con el formato "ORD-XXX".
     */
    public String generarNuevoIdOrden() {
        return String.format("ORD-%03d", obtenerTodasLasOrdenes().size() + 1);
    }

    /**
     * Función 16: Asignación Inteligente de Mecánico.
     * Algoritmo que sugiere un mecánico basado en su especialidad.
     * @param tipoServicio El servicio a realizar.
     * @return El nombre del mecánico especialista, o "Mecanico General" si no se encuentra.
     */
    public String asignarMecanicoInteligente(String tipoServicio) {
        if(tipoServicio == null) return "General";
        for(Mecanico m : listaMecanicos) {
            // Compara si el tipo de servicio contiene la especialidad del mecánico.
            if(tipoServicio.toLowerCase().contains(m.getEspecialidad().toLowerCase())) {
                return m.getNombreCompleto();
            }
        }
        return "Mecanico General";
    }

    // ============================================================================
    // SECCIÓN 4: INICIALIZACIÓN DE DATOS DE PRUEBA (DATA SEEDING)
    // ============================================================================

    /**
     * Método privado para poblar el sistema con datos iniciales si está vacío.
     * Facilita las pruebas y la demostración de funcionalidades.
     */
    private void cargarDatosDePrueba() {
        listaMecanicos.add(new Mecanico("Pedro Perez", "Aceite y Fluidos"));
        listaMecanicos.add(new Mecanico("Carlos Villa", "Frenos"));
        listaMecanicos.add(new Mecanico("Juan Torres", "Alineacion"));
        listaMecanicos.add(new Mecanico("Roberto Castillo", "Escaneo"));

        // Se crean varios objetos de prueba para poblar la aplicación.
        Vehiculo v1 = new Vehiculo("AA123BC", "TOYOTA", "Corolla", 2022, "Gris", new Propietario("Carlos Rodriguez", "V-12345678", "0414-1112233", "Urb. Los Olivos"));
        v1.agregarMantenimiento(crearMant("ORD-001", "Cambio de Aceite", "Sintetico", 40, 25, 15000, "Finalizado", "2025-10-05"));

        Vehiculo v2 = new Vehiculo("AB456CD", "FORD", "Fiesta", 2012, "Negro", new Propietario("Ana Flores", "V-87654321", "0424-9998877", "Alta Vista Norte"));
        v2.agregarMantenimiento(crearMant("ORD-003", "Mantenimiento Aire", "Gas", 60, 70, 85000, "En Proceso", "2025-11-25"));

        Vehiculo v3 = new Vehiculo("01AA8AD", "ENCAVA", "ENT-610", 2015, "Blanco", new Propietario("Transporte Guayana", "J-303030", "0286-9990000", "Zona Industrial"));
        v3.agregarMantenimiento(crearMant("ORD-004", "Revision General", "Tren delantero", 100, 0, 120000, "Pendiente", "2025-11-28"));

        Vehiculo v4 = new Vehiculo("JGF140", "CHEVROLET", "Spark", 2018, "Rojo", new Propietario("Johanna Guedez", "V-14089807", "0412-7778899", "Unare II"));
        v4.agregarMantenimiento(crearMant("ORD-005", "Escaneo", "Check Engine", 30, 0, 45000, "Finalizado", "2025-11-01"));

        listaVehiculos.add(v1);
        listaVehiculos.add(v2);
        listaVehiculos.add(v3);
        listaVehiculos.add(v4);
        listaVehiculos.add(new Vehiculo("AE789FG", "HYUNDAI", "Tucson", 2021, "Azul", new Propietario("Diego Salazar", "V-20555666", "0416-1112233", "Villa Asia")));
        listaVehiculos.add(new Vehiculo("AD123XY", "MITSUBISHI", "Lancer", 2014, "Plata", new Propietario("Maria Gonzalez", "V-15222333", "0414-8889999", "Puerto Ordaz")));
        listaVehiculos.add(new Vehiculo("AI999ZZ", "JEEP", "Cherokee", 2010, "Verde", new Propietario("Pedro Perez", "V-5555555", "0412-1111111", "San Felix")));
        listaVehiculos.add(new Vehiculo("AK456BB", "NISSAN", "Sentra", 2019, "Blanco", new Propietario("Luisa Martinez", "V-18999000", "0424-3334444", "Core 8")));
        listaVehiculos.add(new Vehiculo("AM111CC", "FIAT", "Palio", 2008, "Rojo", new Propietario("Jose Torres", "V-11111111", "0416-2223333", "El Roble")));
        listaVehiculos.add(new Vehiculo("AO777DD", "RENAULT", "Logan", 2016, "Gris", new Propietario("Carmen Diaz", "V-9888777", "0414-5556666", "Los Mangos")));

        gestorArchivos.guardarDatos(this.listaMecanicos, ARCHIVO_MECANICOS);
        gestorArchivos.guardarDatos(this.listaVehiculos, ARCHIVO_VEHICULOS);
    }

    /**
     * Método de fábrica para crear instancias de Mantenimiento de forma rápida para las pruebas.
     */
    private Mantenimiento crearMant(String id, String serv, String desc, double mo, double re, int km, String est, String fecha) {
        Mantenimiento m = new Mantenimiento(id, serv, desc, mo, re, km, LocalDate.parse(fecha));
        m.setEstado(est);
        return m;
    }
}