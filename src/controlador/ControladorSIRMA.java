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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
 * PRINCIPIO POO: Modularidad - Descompone el sistema en módulos (MVC).
 */
public class ControladorSIRMA {

    // --- ATRIBUTOS ---
    private List<Vehiculo> listaVehiculos;
    private List<Mecanico> listaMecanicos;
    private final GestionArchivos gestorArchivos;

    // Constantes para los nombres de archivo
    private static final String ARCHIVO_VEHICULOS = "sirma_vehiculos.dat";
    private static final String ARCHIVO_MECANICOS = "sirma_mecanicos.dat";

    /**
     * DTO (Data Transfer Object) para empaquetar un Vehículo y su Mantenimiento.
     */
    public static class VehiculoYMantenimiento {
        public final Vehiculo vehiculo;
        public final Mantenimiento mantenimiento;
        public VehiculoYMantenimiento(Vehiculo v, Mantenimiento m) { this.vehiculo = v; this.mantenimiento = m; }
    }

    /**
     * Constructor del Controlador.
     */
    @SuppressWarnings("unchecked")
    public ControladorSIRMA() {
        this.gestorArchivos = new GestionArchivos();
        Object datosVehiculos = gestorArchivos.cargarDatos(ARCHIVO_VEHICULOS);
        Object datosMecanicos = gestorArchivos.cargarDatos(ARCHIVO_MECANICOS);

        // Casting seguro
        this.listaVehiculos = (datosVehiculos instanceof List) ? (List<Vehiculo>) datosVehiculos : new ArrayList<>();
        this.listaMecanicos = (datosMecanicos instanceof List) ? (List<Mecanico>) datosMecanicos : new ArrayList<>();

        // Si la base de datos está vacía, se puebla con datos de prueba (10 vehículos).
        if (this.listaVehiculos.isEmpty()) {
            cargarDatosDePrueba();
        }
    }

    // ============================================================================
    // SECCIÓN 1: LÓGICA DE BÚSQUEDA Y CONSULTAS
    // ============================================================================

    public Object[][] buscarOrdenesAvanzado(String texto, String estado, String fechaDesdeStr, String fechaHastaStr) {
        String txt = (texto == null) ? "" : texto.toUpperCase().trim();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fD = null, fH = null;

        try {
            if (fechaDesdeStr != null && !fechaDesdeStr.isEmpty()) fD = LocalDate.parse(fechaDesdeStr, fmt);
            if (fechaHastaStr != null && !fechaHastaStr.isEmpty()) fH = LocalDate.parse(fechaHastaStr, fmt);
        } catch (DateTimeParseException e) { }

        final LocalDate d = fD; final LocalDate h = fH;
        ArrayList<Object[]> filas = new ArrayList<>();

        for (Vehiculo v : listaVehiculos) {
            for (Mantenimiento m : v.getHistorialMantenimientos()) {
                boolean cTxt = txt.isEmpty() || v.getPlaca().toUpperCase().contains(txt) || m.getIdOrden().toUpperCase().contains(txt);
                boolean cEst = estado.equals("Todos") || m.getEstado().equalsIgnoreCase(estado);
                boolean cFec = (d == null || !m.getFechaRealizacion().isBefore(d)) && (h == null || !m.getFechaRealizacion().isAfter(h));

                if (cTxt && cEst && cFec) {
                    filas.add(new Object[]{
                            m.getIdOrden(),
                            m.getFechaRealizacion().format(fmt),
                            v.getPlaca(),
                            v.getMarca(),
                            m.getTipoServicio(),
                            m.getEstado(),
                            String.format(Locale.US, "%.2f", m.getCostoTotal())
                    });
                }
            }
        }

        Object[][] data = new Object[filas.size()][7];
        for(int i=0; i<filas.size(); i++) data[i] = filas.get(i);
        return data;
    }

    // --- CRUD de Vehículos ---
    public boolean registrarVehiculo(Vehiculo v) {
        if (buscarVehiculoPorPlaca(v.getPlaca()).isPresent()) return false;
        listaVehiculos.add(v);
        return gestorArchivos.guardarDatos(listaVehiculos, ARCHIVO_VEHICULOS);
    }
    public boolean actualizarVehiculo(String p, Vehiculo v) {
        for(int i=0; i<listaVehiculos.size(); i++) {
            if(listaVehiculos.get(i).getPlaca().equalsIgnoreCase(p)) {
                listaVehiculos.set(i, v);
                return gestorArchivos.guardarDatos(listaVehiculos, ARCHIVO_VEHICULOS);
            }
        }
        return false;
    }
    public boolean eliminarVehiculo(String p) {
        boolean eliminado = listaVehiculos.removeIf(v -> v.getPlaca().equalsIgnoreCase(p));
        if(eliminado) gestorArchivos.guardarDatos(listaVehiculos, ARCHIVO_VEHICULOS);
        return eliminado;
    }
    public Optional<Vehiculo> buscarVehiculoPorPlaca(String p) {
        return listaVehiculos.stream().filter(v -> v.getPlaca().equalsIgnoreCase(p)).findFirst();
    }
    public List<Vehiculo> obtenerTodosLosVehiculos() { return listaVehiculos; }

    // --- CRUD de Mecánicos ---
    public boolean agregarMecanico(Mecanico m) {
        listaMecanicos.add(m);
        return gestorArchivos.guardarDatos(listaMecanicos, ARCHIVO_MECANICOS);
    }
    public boolean actualizarMecanico(String n, Mecanico m) {
        for(int i=0; i<listaMecanicos.size(); i++) {
            if(listaMecanicos.get(i).getNombreCompleto().equalsIgnoreCase(n)) {
                listaMecanicos.set(i, m);
                return gestorArchivos.guardarDatos(listaMecanicos, ARCHIVO_MECANICOS);
            }
        }
        return false;
    }
    public boolean eliminarMecanico(String n) {
        boolean eliminado = listaMecanicos.removeIf(m -> m.getNombreCompleto().equalsIgnoreCase(n));
        if(eliminado) gestorArchivos.guardarDatos(listaMecanicos, ARCHIVO_MECANICOS);
        return eliminado;
    }
    public List<Mecanico> obtenerTodosLosMecanicos() { return listaMecanicos; }

    // --- CRUD de Mantenimientos ---
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
    public boolean eliminarMantenimiento(String id) {
        for(Vehiculo v : listaVehiculos) {
            if(v.getHistorialMantenimientos().removeIf(m -> m.getIdOrden().equals(id))) {
                return gestorArchivos.guardarDatos(listaVehiculos, ARCHIVO_VEHICULOS);
            }
        }
        return false;
    }
    public Optional<VehiculoYMantenimiento> buscarVehiculoPorIdOrden(String id) {
        for(Vehiculo v : listaVehiculos)
            for(Mantenimiento m : v.getHistorialMantenimientos())
                if(m.getIdOrden().equalsIgnoreCase(id)) return Optional.of(new VehiculoYMantenimiento(v, m));
        return Optional.empty();
    }
    public List<Mantenimiento> obtenerTodasLasOrdenes() {
        return listaVehiculos.stream()
                .flatMap(v -> v.getHistorialMantenimientos().stream())
                .collect(Collectors.toList());
    }
    public String generarNuevoIdOrden() {
        return String.format("ORD-%03d", obtenerTodasLasOrdenes().size() + 1);
    }
    public String asignarMecanicoInteligente(String tipoServicio) {
        if(tipoServicio == null) return "General";
        for(Mecanico m : listaMecanicos) {
            if(tipoServicio.toLowerCase().contains(m.getEspecialidad().toLowerCase())) {
                return m.getNombreCompleto();
            }
        }
        return "Mecanico General";
    }

    // ============================================================================
    // --- SECCIÓN DE REPORTES (CSS COMPACTO SIN ESPACIOS GRANDES) ---
    // ============================================================================

    public String generarReporteHTML(String tituloReporte) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>\n<html lang=\"es\">\n<head>\n");
        html.append("  <meta charset=\"UTF-8\">\n");
        html.append("  <title>").append(tituloReporte).append("</title>\n");
        html.append("  <style>\n");

        // AJUSTE: Margen del body reducido a 10px para subir todo
        html.append("    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #333; color: #f0f0f0; margin: 10px 20px; }\n");

        // AJUSTE: Encabezado compacto
        html.append("    .header-container { text-align: center; margin-bottom: 5px; }\n");
        html.append("    .logo { max-width: 150px; height: auto; display: block; margin: 0 auto 0 auto; }\n"); // Margen 0 abajo
        html.append("    h1 { color: #ffc107; text-transform: uppercase; letter-spacing: 2px; margin: 0; padding: 5px 0; font-size: 24px; }\n");

        // Tabla pegadita arriba
        html.append("    table { width: 100%; border-collapse: collapse; box-shadow: 0 5px 15px rgba(0,0,0,0.5); background-color: #444; margin-top: 5px; }\n");
        html.append("    th, td { padding: 10px 15px; border: 1px solid #555; font-size: 13px; }\n");
        html.append("    thead { background-color: #ffc107; color: #000; font-weight: bold; }\n");

        // Alineación
        html.append("    .text-center { text-align: center; }\n");
        html.append("    .text-right { text-align: right; }\n");
        html.append("    .text-left { text-align: left; }\n");

        html.append("    tbody tr:nth-child(even) { background-color: #3d3d3d; }\n");
        html.append("    tbody tr:hover { background-color: #555; transition: 0.3s; }\n");

        html.append("    .estado-finalizado { color: #28a745; font-weight: bold; }\n");
        html.append("    .estado-en-proceso { color: #ffc107; font-weight: bold; }\n");
        html.append("    .estado-pendiente { color: #dc3545; font-weight: bold; }\n");
        html.append("  </style>\n</head>\n<body>\n");

        // Contenido Header
        html.append("  <div class='header-container'>\n");
        html.append("    <img src='fondo/sirma.png' alt='Logo SIRMA' class='logo'>\n");
        html.append("    <h1>").append(tituloReporte).append("</h1>\n");
        html.append("  </div>\n");

        // Tabla
        html.append("  <table>\n");
        html.append("    <thead>\n<tr>\n");
        html.append("        <th class='text-center'>ID Orden</th>");
        html.append("        <th class='text-center'>Fecha</th>");
        html.append("        <th class='text-left'>Placa</th>");
        html.append("        <th class='text-left'>Marca</th>");
        html.append("        <th class='text-left'>Servicio</th>");
        html.append("        <th class='text-center'>Estado</th>");
        html.append("        <th class='text-right'>Total ($)</th>\n");
        html.append("</tr>\n</thead>\n");

        html.append("    <tbody>\n");
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (Vehiculo v : listaVehiculos) {
            for (Mantenimiento m : v.getHistorialMantenimientos()) {
                String claseEstado = "";
                switch (m.getEstado().toLowerCase()) {
                    case "finalizado": claseEstado = "estado-finalizado"; break;
                    case "en proceso": claseEstado = "estado-en-proceso"; break;
                    case "pendiente": claseEstado = "estado-pendiente"; break;
                }

                html.append("      <tr>\n")
                        .append("        <td class='text-center'>").append(m.getIdOrden()).append("</td>\n")
                        .append("        <td class='text-center'>").append(m.getFechaRealizacion().format(fmt)).append("</td>\n")
                        .append("        <td class='text-left'>").append(v.getPlaca()).append("</td>\n")
                        .append("        <td class='text-left'>").append(v.getMarca()).append("</td>\n")
                        .append("        <td class='text-left'>").append(m.getTipoServicio()).append("</td>\n")
                        .append("        <td class='text-center ").append(claseEstado).append("'>").append(m.getEstado()).append("</td>\n")
                        .append("        <td class='text-right'>").append(String.format(Locale.US, "%.2f", m.getCostoTotal())).append("</td>\n")
                        .append("      </tr>\n");
            }
        }
        html.append("    </tbody>\n</table>\n");

        html.append("<p style='text-align: center; margin-top: 10px; color: #777; font-size: 11px;'>Generado por Sistema SIRMA_JG</p>");
        html.append("</body>\n</html>\n");
        return html.toString();
    }

    public void guardarReporteEnArchivo(String nombreArchivo) {
        String reporte = generarReporteHTML("Reporte General de Órdenes de Servicio");
        try {
            Files.write(Paths.get(nombreArchivo), reporte.getBytes());
            System.out.println("Reporte generado exitosamente: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar el reporte: " + e.getMessage());
        }
    }

    // ============================================================================
    // --- DATOS DE PRUEBA (10 VEHÍCULOS CON MANTENIMIENTOS) ---
    // ============================================================================

    private void cargarDatosDePrueba() {
        listaMecanicos.add(new Mecanico("Pedro Perez", "Aceite y Fluidos"));
        listaMecanicos.add(new Mecanico("Carlos Villa", "Frenos"));
        listaMecanicos.add(new Mecanico("Juan Torres", "Alineacion"));
        listaMecanicos.add(new Mecanico("Roberto Castillo", "Escaneo"));

        // 1. Toyota
        Vehiculo v1 = new Vehiculo("AA123BC", "TOYOTA", "Corolla", 2022, "Gris", new Propietario("Carlos Rodriguez", "V-12345678", "0414-1112233", "Urb. Los Olivos"));
        v1.agregarMantenimiento(crearMant("ORD-001", "Cambio de Aceite", "Sintetico", 40, 25, 15000, "Finalizado", "2025-10-05"));
        listaVehiculos.add(v1);

        // 2. Ford
        Vehiculo v2 = new Vehiculo("AB456CD", "FORD", "Fiesta", 2012, "Negro", new Propietario("Ana Flores", "V-87654321", "0424-9998877", "Alta Vista Norte"));
        v2.agregarMantenimiento(crearMant("ORD-003", "Mantenimiento Aire", "Gas", 60, 70, 85000, "En Proceso", "2025-11-25"));
        listaVehiculos.add(v2);

        // 3. Encava
        Vehiculo v3 = new Vehiculo("01AA8AD", "ENCAVA", "ENT-610", 2015, "Blanco", new Propietario("Transporte Guayana", "J-303030", "0286-9990000", "Zona Industrial"));
        v3.agregarMantenimiento(crearMant("ORD-004", "Revision General", "Tren delantero", 100, 0, 120000, "Pendiente", "2025-11-28"));
        listaVehiculos.add(v3);

        // 4. Chevrolet
        Vehiculo v4 = new Vehiculo("JGF140", "CHEVROLET", "Spark", 2018, "Rojo", new Propietario("Johanna Guedez", "V-14089807", "0412-7778899", "Unare II"));
        v4.agregarMantenimiento(crearMant("ORD-005", "Escaneo", "Check Engine", 30, 0, 45000, "Finalizado", "2025-11-01"));
        listaVehiculos.add(v4);

        // 5. Hyundai
        Vehiculo v5 = new Vehiculo("AE789FG", "HYUNDAI", "Tucson", 2021, "Azul", new Propietario("Diego Salazar", "V-20555666", "0416-1112233", "Villa Asia"));
        v5.agregarMantenimiento(crearMant("ORD-006", "Alineacion 3D", "Balanceo completo", 45, 10, 20000, "Finalizado", "2025-11-30"));
        listaVehiculos.add(v5);

        // 6. Mitsubishi
        Vehiculo v6 = new Vehiculo("AD123XY", "MITSUBISHI", "Lancer", 2014, "Plata", new Propietario("Maria Gonzalez", "V-15222333", "0414-8889999", "Puerto Ordaz"));
        v6.agregarMantenimiento(crearMant("ORD-007", "Frenos", "Cambio de pastillas", 50, 80, 95000, "Pendiente", "2025-12-01"));
        listaVehiculos.add(v6);

        // 7. Jeep
        Vehiculo v7 = new Vehiculo("AI999ZZ", "JEEP", "Cherokee", 2010, "Verde", new Propietario("Pedro Perez", "V-5555555", "0412-1111111", "San Felix"));
        v7.agregarMantenimiento(crearMant("ORD-008", "Caja", "Cambio aceite caja", 120, 150, 180000, "En Proceso", "2025-12-02"));
        listaVehiculos.add(v7);

        // 8. Nissan
        Vehiculo v8 = new Vehiculo("AK456BB", "NISSAN", "Sentra", 2019, "Blanco", new Propietario("Luisa Martinez", "V-18999000", "0424-3334444", "Core 8"));
        v8.agregarMantenimiento(crearMant("ORD-009", "Electrico", "Luces LED", 25, 30, 40000, "Finalizado", "2025-11-15"));
        listaVehiculos.add(v8);

        // 9. Fiat
        Vehiculo v9 = new Vehiculo("AM111CC", "FIAT", "Palio", 2008, "Rojo", new Propietario("Jose Torres", "V-11111111", "0416-2223333", "El Roble"));
        v9.agregarMantenimiento(crearMant("ORD-010", "Motor", "Correa tiempo", 80, 40, 210000, "Pendiente", "2025-12-03"));
        listaVehiculos.add(v9);

        // 10. Renault
        Vehiculo v10 = new Vehiculo("AO777DD", "RENAULT", "Logan", 2016, "Gris", new Propietario("Carmen Diaz", "V-9888777", "0414-5556666", "Los Mangos"));
        v10.agregarMantenimiento(crearMant("ORD-011", "Cauchos", "Rotacion", 20, 0, 75000, "Finalizado", "2025-10-20"));
        listaVehiculos.add(v10);

        gestorArchivos.guardarDatos(this.listaMecanicos, ARCHIVO_MECANICOS);
        gestorArchivos.guardarDatos(this.listaVehiculos, ARCHIVO_VEHICULOS);
    }

    private Mantenimiento crearMant(String id, String serv, String desc, double mo, double re, int km, String est, String fecha) {
        Mantenimiento m = new Mantenimiento(id, serv, desc, mo, re, km, LocalDate.parse(fecha));
        m.setEstado(est);
        return m;
    }
}