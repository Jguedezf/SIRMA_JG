/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: ControladorSIRMA.java
 * Descripcion: Nucleo de la logica de negocio. Actua como intermediario entre
 *              la Vista y el Modelo (Patron MVC). Gestiona las operaciones
 *              CRUD, la persistencia de datos y las reglas de negocio.
 * Fecha: Noviembre 2025
 * Version: 5.3 (Release Final)
 * -----------------------------------------------------------------------------
 */
package controlador;

import modelo.Mantenimiento;
import modelo.Propietario;
import modelo.Vehiculo;
import persistencia.GestionArchivos;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Clase ControladorSIRMA
 * Responsable de orquestar el flujo de datos y aplicar las reglas de negocio.
 */
public class ControladorSIRMA {

    // Almacenamiento en memoria de la flota de vehiculos.
    private List<Vehiculo> listaVehiculos;

    // Modulo de persistencia para guardar/cargar datos en disco.
    private GestionArchivos gestorArchivos;

    /**
     * Constructor del Controlador.
     * Inicializa el sistema cargando los datos persistentes. Si es la primera
     * ejecucion, genera datos de prueba (Seeding) para facilitar la evaluacion.
     */
    public ControladorSIRMA() {
        this.gestorArchivos = new GestionArchivos();
        this.listaVehiculos = gestorArchivos.cargarDatos();

        // Carga inicial de datos si el sistema esta vacio
        if (this.listaVehiculos.isEmpty()) {
            cargarDatosDePrueba();
            gestorArchivos.guardarDatos(this.listaVehiculos);
        }
    }

    // --- LOGICA DE NEGOCIO: GESTION DE ORDENES ---

    /**
     * Metodo: generarNuevoIdOrden
     * Genera un identificador unico y secuencial para cada orden de servicio.
     * Formato: ORD-XXX
     */
    public String generarNuevoIdOrden() {
        int totalOrdenes = 0;
        for (Vehiculo v : listaVehiculos) {
            totalOrdenes += v.getHistorialMantenimientos().size();
        }
        return String.format("ORD-%03d", totalOrdenes + 1);
    }

    /**
     * Metodo: asignarMecanicoInteligente
     * Aplica una regla de negocio simple para asignar un especialista
     * basandose en el tipo de servicio solicitado.
     */
    public String asignarMecanicoInteligente(String tipo) {
        if (tipo.contains("Aceite")) return "Pedro Perez (Fluidos)";
        if (tipo.contains("Frenos")) return "Maria Gonzalez (Seguridad)";
        if (tipo.contains("Alineacion")) return "Juan Taller (Jefe de Taller)";
        if (tipo.contains("Escaneo")) return "Ing. Roberto (Diagnostico)";
        return "Mecanico de Guardia";
    }

    /**
     * Metodo: obtenerBitacoraOrdenes
     * Procesa la informacion cruda del modelo y la transforma en una matriz
     * optimizada para ser mostrada en la JTable de la vista.
     * Incluye filtrado por placa o ID de orden.
     */
    public Object[][] obtenerBitacoraOrdenes(String filtro) {
        ArrayList<Object[]> filas = new ArrayList<>();
        String f = filtro.toUpperCase().trim();

        for (Vehiculo v : listaVehiculos) {
            for (Mantenimiento m : v.getHistorialMantenimientos()) {
                boolean coincide = f.isEmpty() ||
                        v.getPlaca().contains(f) ||
                        m.getIdOrden().contains(f);

                if (coincide) {
                    filas.add(new Object[]{
                            m.getIdOrden(),
                            m.getFechaRealizacion().toString(),
                            v.getPlaca(),
                            v.getMarca() + " " + v.getModelo(),
                            m.getTipoServicio(),
                            m.getEstado(),
                            "$" + m.getCostoTotal() // Uso de metodo calculado
                    });
                }
            }
        }

        Object[][] data = new Object[filas.size()][7];
        for(int i=0; i<filas.size(); i++) data[i] = filas.get(i);
        return data;
    }

    // --- OPERACIONES CRUD (Create, Read, Update, Delete) ---

    /**
     * CREATE: Registra un nuevo vehiculo en el sistema.
     * Valida que la placa no exista previamente.
     */
    public boolean registrarVehiculo(Vehiculo v) {
        if (buscarVehiculoPorPlaca(v.getPlaca()).isPresent()) return false;
        this.listaVehiculos.add(v);
        gestorArchivos.guardarDatos(this.listaVehiculos); // Persistencia automatica
        return true;
    }

    /**
     * READ: Busca un vehiculo por su placa unica.
     * Retorna un Optional para manejar de forma segura la ausencia de datos.
     */
    public Optional<Vehiculo> buscarVehiculoPorPlaca(String placa) {
        return listaVehiculos.stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa))
                .findFirst();
    }

    /**
     * UPDATE: Agrega un mantenimiento (orden) al historial de un vehiculo.
     */
    public boolean agregarMantenimientoAVehiculo(String placa, Mantenimiento mant) {
        Optional<Vehiculo> opt = buscarVehiculoPorPlaca(placa);
        if (opt.isPresent()) {
            opt.get().agregarMantenimiento(mant);
            gestorArchivos.guardarDatos(this.listaVehiculos);
            return true;
        }
        return false;
    }

    /**
     * DELETE: Elimina un vehiculo y todo su historial del sistema.
     */
    public boolean eliminarVehiculo(String placa) {
        boolean b = listaVehiculos.removeIf(v -> v.getPlaca().equalsIgnoreCase(placa));
        if (b) gestorArchivos.guardarDatos(this.listaVehiculos);
        return b;
    }

    /**
     * DELETE (Detalle): Elimina una orden especifica del historial.
     */
    public boolean eliminarMantenimiento(String placa, Mantenimiento m) {
        Optional<Vehiculo> opt = buscarVehiculoPorPlaca(placa);
        if (opt.isPresent() && opt.get().getHistorialMantenimientos().remove(m)) {
            gestorArchivos.guardarDatos(this.listaVehiculos);
            return true;
        }
        return false;
    }

    /**
     * Validacion de formato de placa (Reglas de Transito).
     * Acepta formato nuevo (AB123CD) y anterior (ABC123).
     */
    public boolean esFormatoPlacaValido(String p) {
        if (p == null) return false;
        String pl = p.trim().toUpperCase().replace("-","");
        return pl.matches("^[A-Z]{2}[0-9]{3}[A-Z]{2}$") || pl.matches("^[A-Z]{3}[0-9]{3}$");
    }

    public List<Vehiculo> obtenerTodosLosVehiculos() { return listaVehiculos; }

    // --- CARGA DE DATOS INICIALES (SEEDING) ---
    private void cargarDatosDePrueba() {
        Propietario p1 = new Propietario("CARLOS RODRIGUEZ", "V12345", "0414-111");
        Propietario p2 = new Propietario("ANA FLORES", "V67890", "0424-222");
        Propietario p3 = new Propietario("EMPRESA TRANSPORTE", "J-3030", "0212-333");
        Propietario pJohanna = new Propietario("JOHANNA GUEDEZ", "V14089807", "0412-999");

        Vehiculo v1 = new Vehiculo("AA123BC", "TOYOTA", "COROLLA", 2022, "GRIS", p1);
        Mantenimiento m1 = new Mantenimiento("ORD-001", "Cambio de Aceite", "Sintetico + Filtro", 40, 20, 15000, LocalDate.of(2025, 10, 5));
        m1.setEstado("Finalizado");
        v1.agregarMantenimiento(m1);

        Mantenimiento m2 = new Mantenimiento("ORD-002", "Frenos Delanteros", "Cambio de Pastillas", 30, 80, 15100, LocalDate.of(2025, 10, 20));
        m2.setEstado("Finalizado");
        v1.agregarMantenimiento(m2);

        Vehiculo v2 = new Vehiculo("XYZ987", "HYUNDAI", "ELANTRA", 2021, "ROJO", p2);
        Mantenimiento m3 = new Mantenimiento("ORD-003", "Alineacion", "Balanceo y Rotacion", 35, 0, 60000, LocalDate.of(2025, 11, 10));
        m3.setEstado("Finalizado");
        v2.agregarMantenimiento(m3);

        Vehiculo v3 = new Vehiculo("JGF140", "CHEVROLET", "SPARK", 2018, "AZUL", pJohanna);
        Mantenimiento m4 = new Mantenimiento("ORD-004", "Escaneo", "Luz de Check Engine", 30, 0, 85000, LocalDate.of(2025, 11, 26));
        m4.setEstado("En Proceso");
        v3.agregarMantenimiento(m4);

        Vehiculo v4 = new Vehiculo("BUS100", "ENCAVA", "ENT-610", 2015, "BLANCO", p3);

        this.listaVehiculos.add(v1);
        this.listaVehiculos.add(v2);
        this.listaVehiculos.add(v3);
        this.listaVehiculos.add(v4);
    }
}