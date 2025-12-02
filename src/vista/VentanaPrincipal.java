/*
 * ============================================================================
 * PROYECTO: Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA JG)
 * INSTITUCIÓN: Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA: Técnicas de Programación III - Sección 3
 * * AUTOR: Johanna Guédez
 * CÉDULA: V-14.089.807
 * DOCENTE: Ing. Dubraska Roca
 * * ARCHIVO: VentanaPrincipal.java
 * FECHA: Diciembre 2025
 * * DESCRIPCIÓN TÉCNICA:
 * Clase Principal de la Capa de Vista (View) en la arquitectura MVC.
 * Actúa como el contenedor raíz (Root Container) y orquestador de la navegación.
 *
 * PRINCIPIOS DE PROGRAMACIÓN ORIENTADA A OBJETOS (POO):
 * 1. HERENCIA: Extiende de 'javax.swing.JFrame' para heredar comportamiento de ventana.
 * 2. COMPOSICIÓN: La ventana "está compuesta por" (HAS-A) múltiples paneles hijos.
 * 3. ENCAPSULAMIENTO: Atributos 'private final' para proteger la estructura de la UI.
 *
 * PATRONES DE DISEÑO:
 * - MVC: Separa la interfaz gráfica de la lógica de negocio (Controlador).
 * - Observer: Implementado mediante Listeners para capturar eventos de usuario.
 * - Mediator: Centraliza la comunicación entre los paneles hijos y el controlador.
 * ============================================================================
 */
package vista;

// --- IMPORTACIÓN DE LA CAPA DE CONTROL Y MODELO (MVC) ---
import controlador.ControladorSIRMA;
import controlador.ControladorSIRMA.VehiculoYMantenimiento;
import modelo.Mecanico;
import modelo.Mantenimiento;
import modelo.Propietario;
import modelo.Vehiculo;
import persistencia.GestionReportes;

// --- IMPORTACIÓN DE LIBRERÍAS GRÁFICAS Y UTILITARIOS ---
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Locale;

/**
 * Clase: VentanaPrincipal
 * Responsabilidad: Gestionar el ciclo de vida de la aplicación gráfica, la inyección
 * de dependencias y la navegación entre módulos (CardLayout).
 */
public class VentanaPrincipal extends JFrame {

    // =========================================================================
    // 1. ATRIBUTOS Y DEPENDENCIAS (ESTADO DEL OBJETO)
    // =========================================================================

    // INYECCIÓN DE DEPENDENCIA: Referencia a la capa de Lógica de Negocio.
    // Se declara 'final' para asegurar la integridad de la referencia.
    private final ControladorSIRMA controlador;

    // GESTIÓN DE ESTADO (MEMORIA DE NAVEGACIÓN)
    // Variable auxiliar para determinar el flujo de retorno (Botón Volver).
    private String vistaAnterior = "DASHBOARD";

    // COMPONENTES DE DISEÑO (LAYOUT MANAGERS)
    private final CardLayout cardLayout;   // Gestor tipo "Pila" para intercambio de vistas.
    private final JPanel panelContenido;   // Contenedor donde se renderizan los paneles.

    // COMPOSICIÓN (VISTAS HIJAS)
    // La ventana instancia y administra los siguientes paneles:
    private final PanelMenuLateral panelMenu;
    private final PanelBienvenida panelBienvenida;
    private final PanelGestionVehiculos panelGestionVehiculos;
    private final PanelDashboard panelDashboard;
    private final PanelGestionMantenimientos panelFormularioMantenimiento;
    private final PanelReportes panelReportes;
    private final PanelGestionMecanicos panelGestionMecanicos;
    private final PanelBusquedaInteligente panelBusquedaInteligente;

    /**
     * CONSTRUCTOR: VentanaPrincipal
     * OBJETIVO: Inicializar el entorno gráfico y establecer relaciones entre objetos.
     *
     * ALGORITMO DE INICIALIZACIÓN:
     * 1. Inyección de Dependencia (Controlador).
     * 2. Configuración del Window Manager (Título, Tamaño, Cierre).
     * 3. Instanciación de Vistas Hijas (Paneles).
     * 4. Construcción del Grafo de Escena (Agregar paneles al Layout).
     * 5. Binding de Eventos (Conectar acciones).
     *
     * @param controlador Instancia del ControladorSIRMA (Capa Lógica).
     */
    public VentanaPrincipal(ControladorSIRMA controlador) {
        this.controlador = controlador;

        // 1. Configuración de Propiedades del Frame
        setTitle("SIRMA JG - Sistema Inteligente de Registro de Mantenimiento Automotriz");
        setSize(1280, 720); // Resolución HD Ergonómica
        setLocationRelativeTo(null); // Centrado automático en pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Finalizar JVM al cerrar
        setLayout(new BorderLayout()); // Layout Raíz

        // 2. Instanciación de Componentes Estructurales
        panelMenu = new PanelMenuLateral();
        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);

        // 3. Instanciación de Módulos (Vistas Específicas)
        panelBienvenida = new PanelBienvenida();
        panelGestionVehiculos = new PanelGestionVehiculos();
        panelDashboard = new PanelDashboard();
        panelFormularioMantenimiento = new PanelGestionMantenimientos();
        panelReportes = new PanelReportes();
        panelGestionMecanicos = new PanelGestionMecanicos();

        // Inyección del controlador al panel de búsqueda (Comunicación directa Vista-Controlador)
        panelBusquedaInteligente = new PanelBusquedaInteligente(controlador);

        // 4. Registro de Vistas en el CardLayout (Mapeo Clave-Valor)
        panelContenido.add(panelBienvenida, "BIENVENIDA");
        panelContenido.add(panelGestionVehiculos, "VEHICULOS");
        panelContenido.add(panelDashboard, "DASHBOARD");
        panelContenido.add(panelFormularioMantenimiento, "FORMULARIO_MANTENIMIENTO");
        panelContenido.add(panelReportes, "REPORTES");
        panelContenido.add(panelGestionMecanicos, "MECANICOS");
        panelContenido.add(panelBusquedaInteligente, "BUSQUEDA");

        // 5. Composición Final
        add(panelMenu, BorderLayout.WEST);
        add(panelContenido, BorderLayout.CENTER);

        // Renderizado Inicial
        cardLayout.show(panelContenido, "BIENVENIDA");

        // 6. Activación de la Lógica de Control (Event Binding)
        conectarAcciones();
    }

    /**
     * MÉTODO: conectarAcciones
     * DESCRIPCIÓN TÉCNICA:
     * Implementa el patrón OBSERVER. Registra esta clase (mediante Lambdas) como
     * 'Listeners' de los componentes de la UI.
     * Centraliza el despacho de eventos (Event Dispatching) hacia la lógica de negocio.
     */
    private void conectarAcciones() {

        // --- 1. SUBSISTEMA DE NAVEGACIÓN (CONTROL DE FLUJO) ---
        panelMenu.btnInicio.addActionListener(e -> cardLayout.show(panelContenido, "BIENVENIDA"));
        panelMenu.btnGestionVehiculos.addActionListener(e -> irAGestionVehiculos());
        panelMenu.btnOrdenesServicio.addActionListener(e -> irAlDashboard());
        panelMenu.btnBusquedaInteligente.addActionListener(e -> irABusquedaInteligente());
        panelMenu.btnGestionMecanicos.addActionListener(e -> irAGestionMecanicos());
        panelMenu.btnCentroReportes.addActionListener(e -> irACentroReportes());
        panelMenu.btnCerrarSesion.addActionListener(e -> System.exit(0)); // Syscall de salida

        // Accesos rápidos desde Bienvenida
        panelBienvenida.btnGestionFlota.addActionListener(e -> irAGestionVehiculos());
        panelBienvenida.btnGestionServicios.addActionListener(e -> irAlDashboard());
        panelBienvenida.btnReportesCierre.addActionListener(e -> irACentroReportes());

        // --- 2. SUBSISTEMA DE GESTIÓN DE VEHÍCULOS (CRUD) ---
        panelGestionVehiculos.btnGuardar.addActionListener(e -> guardarOActualizarVehiculo());
        panelGestionVehiculos.btnLimpiar.addActionListener(e -> panelGestionVehiculos.limpiarFormulario());
        panelGestionVehiculos.btnEliminar.addActionListener(e -> eliminarVehiculoSeleccionado());

        // Listener de JTable: Data Binding (Modelo -> Vista al seleccionar fila)
        panelGestionVehiculos.tablaVehiculos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarVehiculoSeleccionadoEnFormulario();
                // Habilitación contextual del botón eliminar
                panelGestionVehiculos.btnEliminar.setEnabled(panelGestionVehiculos.tablaVehiculos.getSelectedRow() != -1);
            }
        });

        // --- 3. SUBSISTEMA DASHBOARD (BITÁCORA OPERATIVA) ---
        panelDashboard.btnAgregarOrden.addActionListener(e -> {
            vistaAnterior = "DASHBOARD"; // Actualización de estado para navegación
            panelFormularioMantenimiento.prepararParaNuevaOrden(controlador.obtenerTodosLosVehiculos());
            cardLayout.show(panelContenido, "FORMULARIO_MANTENIMIENTO");
        });
        panelDashboard.btnEditarOrden.addActionListener(e -> editarOrdenSeleccionadaDesdeDashboard());
        panelDashboard.btnEliminarOrden.addActionListener(e -> eliminarOrdenSeleccionada());

        // Lógica de habilitación contextual de botones (UX)
        panelDashboard.tablaOrdenes.getSelectionModel().addListSelectionListener(e -> {
            boolean sel = panelDashboard.tablaOrdenes.getSelectedRow() != -1;
            panelDashboard.btnEditarOrden.setEnabled(sel);
            panelDashboard.btnEliminarOrden.setEnabled(sel);
        });

        // --- 4. SUBSISTEMA FORMULARIO MANTENIMIENTO (TRANSACCIONAL) ---

        // Lógica de "Volver Inteligente": Evalúa el historial de navegación
        panelFormularioMantenimiento.btnVolver.addActionListener(e -> {
            if (vistaAnterior.equals("BUSQUEDA")) {
                cardLayout.show(panelContenido, "BUSQUEDA"); // Retorno a Búsqueda
            } else {
                irAlDashboard(); // Retorno por defecto
            }
        });

        panelFormularioMantenimiento.btnLimpiar.addActionListener(e -> panelFormularioMantenimiento.prepararParaNuevaOrden(controlador.obtenerTodosLosVehiculos()));
        panelFormularioMantenimiento.btnGuardar.addActionListener(e -> guardarOActualizarMantenimiento());
        panelFormularioMantenimiento.btnImprimirSolicitud.addActionListener(e -> imprimirSolicitudDeServicio());

        // Algoritmos Auxiliares: Autocompletado y Asignación Automática
        panelFormularioMantenimiento.comboVehiculos.addActionListener(e -> autocompletarKilometraje());
        panelFormularioMantenimiento.comboServicios.addActionListener(e -> {
            String tipo = (String) panelFormularioMantenimiento.comboServicios.getSelectedItem();
            // Invocación a lógica de negocio en el Controlador
            String mec = controlador.asignarMecanicoInteligente(tipo);
            panelFormularioMantenimiento.campoMecanicoAsignado.setText(mec);
        });

        // --- 5. SUBSISTEMA GESTIÓN MECÁNICOS ---
        panelGestionMecanicos.btnGuardar.addActionListener(e -> guardarOActualizarMecanico());
        panelGestionMecanicos.btnLimpiar.addActionListener(e -> panelGestionMecanicos.limpiarFormulario());
        panelGestionMecanicos.btnEliminar.addActionListener(e -> eliminarMecanicoSeleccionado());
        panelGestionMecanicos.tablaMecanicos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean sel = panelGestionMecanicos.tablaMecanicos.getSelectedRow() != -1;
                panelGestionMecanicos.btnEliminar.setEnabled(sel);
                if (sel) cargarMecanicoEnFormulario();
                else panelGestionMecanicos.limpiarFormulario();
            }
        });

        // --- 6. SUBSISTEMA BÚSQUEDA INTELIGENTE ---
        panelBusquedaInteligente.btnBuscar.addActionListener(e -> realizarBusquedaInteligente());
        panelBusquedaInteligente.btnLimpiar.addActionListener(e -> limpiarFiltrosBusqueda());

        // Acciones Contextuales (Desde resultados de búsqueda)
        panelBusquedaInteligente.btnAccionEditar.addActionListener(e -> editarOrdenDesdeBusqueda());
        panelBusquedaInteligente.btnAccionEliminar.addActionListener(e -> eliminarOrdenDesdeBusqueda());
        panelBusquedaInteligente.btnAccionNuevaOrden.addActionListener(e -> crearNuevaOrdenDesdeBusqueda());

        // --- 7. SUBSISTEMA REPORTES (I/O) ---
        panelReportes.btnGenerarHTML.addActionListener(e -> generarYAbriReporte("html"));
        panelReportes.btnGenerarTXT.addActionListener(e -> generarYAbriReporte("txt"));
        panelReportes.btnAbrirCarpeta.addActionListener(e -> abrirCarpetaReportes());
        panelReportes.btnVolverInicio.addActionListener(e -> cardLayout.show(panelContenido, "BIENVENIDA"));
    }

    // =========================================================================
    // SECCIÓN 1: MÉTODOS DE NAVEGACIÓN Y CARGA (VIEW CONTROLLER)
    // =========================================================================

    private void irAlDashboard() {
        // Sincronización Modelo -> Vista (Refresh de tabla)
        actualizarTablaDeOrdenes(controlador.obtenerTodasLasOrdenes());
        cardLayout.show(panelContenido, "DASHBOARD");
    }

    private void irAGestionVehiculos() {
        actualizarTablaDeVehiculos();
        panelGestionVehiculos.limpiarFormulario();
        cardLayout.show(panelContenido, "VEHICULOS");
    }

    private void irAGestionMecanicos() {
        actualizarTablaMecanicos();
        panelGestionMecanicos.limpiarFormulario();
        cardLayout.show(panelContenido, "MECANICOS");
    }

    private void irABusquedaInteligente() {
        cardLayout.show(panelContenido, "BUSQUEDA");
    }

    private void irACentroReportes() {
        panelReportes.setMensajeEstado(" ", false);
        cardLayout.show(panelContenido, "REPORTES");
    }

    public void establecerUsuarioLogueado(String n) {
        panelMenu.setNombreUsuario(n);
    }

    // =========================================================================
    // SECCIÓN 2: ACTUALIZACIÓN DE TABLAS (READ - DATA BINDING)
    // =========================================================================

    /**
     * MÉTODO: actualizarTablaDeVehiculos
     * PROCESO: Obtiene la lista desde el Controlador e itera para renderizar filas.
     */
    private void actualizarTablaDeVehiculos() {
        panelGestionVehiculos.modeloTabla.setRowCount(0);
        for (Vehiculo v : controlador.obtenerTodosLosVehiculos()) {
            panelGestionVehiculos.modeloTabla.addRow(new Object[]{
                    v.getPlaca(), v.getMarca(), v.getModelo(), v.getAnio(), v.getPropietario().getNombreCompleto()
            });
        }
        panelGestionVehiculos.tablaVehiculos.clearSelection();
        panelGestionVehiculos.btnEliminar.setEnabled(false);
    }

    private void actualizarTablaDeOrdenes(List<Mantenimiento> lista) {
        panelDashboard.modeloTabla.setRowCount(0);
        for (Mantenimiento m : lista) {
            // Búsqueda relacional: Obtener Vehículo asociado al ID de Orden
            controlador.buscarVehiculoPorIdOrden(m.getIdOrden()).ifPresent(r ->
                    panelDashboard.modeloTabla.addRow(new Object[]{
                            m.getIdOrden(), r.vehiculo.getPlaca(), r.vehiculo.getMarca(),
                            m.getTipoServicio(), m.getFechaRealizacion(), m.getEstado(),
                            r.vehiculo.getPropietario().getCedula(), String.format(Locale.US, "%.2f", m.getCostoTotal())
                    })
            );
        }
        panelDashboard.tablaOrdenes.clearSelection();
        panelDashboard.btnEditarOrden.setEnabled(false);
        panelDashboard.btnEliminarOrden.setEnabled(false);
    }

    private void actualizarTablaMecanicos() {
        panelGestionMecanicos.modeloTabla.setRowCount(0);
        for (Mecanico m : controlador.obtenerTodosLosMecanicos()) {
            panelGestionMecanicos.modeloTabla.addRow(new Object[]{m.getNombreCompleto(), m.getEspecialidad()});
        }
        panelGestionMecanicos.tablaMecanicos.clearSelection();
        panelGestionMecanicos.btnEliminar.setEnabled(false);
    }

    // =========================================================================
    // SECCIÓN 3: LÓGICA DE NEGOCIO CRUD (CREATE, UPDATE, DELETE)
    // =========================================================================

    /**
     * PROCESO: Persistencia de Entidad Vehículo
     * PASO 1: Captura de datos desde GUI.
     * PASO 2: Validación de integridad (Campos obligatorios y tipos).
     * PASO 3: Delegación al Controlador para persistencia.
     */
    private void guardarOActualizarVehiculo() {
        // 1. CAPTURA
        String p = panelGestionVehiculos.txtPlaca.getText().trim();
        String m = panelGestionVehiculos.txtMarca.getText().trim();
        String mo = panelGestionVehiculos.txtModelo.getText().trim();
        String a = panelGestionVehiculos.txtAnio.getText().trim();
        String n = panelGestionVehiculos.txtNombrePropietario.getText().trim();
        String c = panelGestionVehiculos.txtCedulaPropietario.getText().trim();
        String d = panelGestionVehiculos.txtDireccion.getText().trim();

        // 2. VALIDACIÓN
        if (p.isEmpty() || m.isEmpty() || mo.isEmpty() || a.isEmpty() || n.isEmpty() || c.isEmpty() || d.isEmpty()) {
            JOptionPane.showMessageDialog(panelContenido, "Complete todos los campos obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int anio;
        try { anio = Integer.parseInt(a); } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panelContenido, "El campo Año debe ser numérico.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. INSTANCIACIÓN (MODELO)
        Propietario prop = new Propietario(n, c, panelGestionVehiculos.txtTelefonoPropietario.getText().trim(), d);
        Vehiculo v = new Vehiculo(p, m, mo, anio, panelGestionVehiculos.txtColor.getText().trim(), prop);

        // 4. DELEGACIÓN (CONTROLADOR)
        boolean exito;
        String msj;

        if (panelGestionVehiculos.vehiculoEnEdicion == null) {
            exito = controlador.registrarVehiculo(v); // Operación CREATE
            msj = exito ? "Vehículo registrado exitosamente." : "Error: La placa ya existe.";
        } else {
            exito = controlador.actualizarVehiculo(panelGestionVehiculos.vehiculoEnEdicion.getPlaca(), v); // Operación UPDATE
            msj = exito ? "Vehículo actualizado exitosamente." : "Error al actualizar.";
        }

        JOptionPane.showMessageDialog(panelContenido, msj, "Gestión de Vehículos", exito ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
        if (exito) {
            panelGestionVehiculos.limpiarFormulario();
            actualizarTablaDeVehiculos();
        }
    }

    private void guardarOActualizarMecanico() {
        String n = panelGestionMecanicos.txtNombre.getText().trim();
        String e = panelGestionMecanicos.txtEspecialidad.getText().trim();

        if (n.isEmpty() || e.isEmpty()) {
            JOptionPane.showMessageDialog(panelContenido, "Campos obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Mecanico m = new Mecanico(n, e);
        boolean exito;
        String msj;

        if (panelGestionMecanicos.mecanicoEnEdicion == null) {
            exito = controlador.agregarMecanico(m);
            msj = exito ? "Mecánico registrado." : "Error: Nombre duplicado.";
        } else {
            exito = controlador.actualizarMecanico(panelGestionMecanicos.mecanicoEnEdicion.getNombreCompleto(), m);
            msj = exito ? "Mecánico actualizado." : "Error al actualizar.";
        }

        JOptionPane.showMessageDialog(panelContenido, msj, "Gestión de Mecánicos", exito ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
        if (exito) { panelGestionMecanicos.limpiarFormulario(); actualizarTablaMecanicos(); }
    }

    /**
     * PROCESO: Gestión de Orden de Mantenimiento
     * Coordina la creación o edición de una orden, validando relaciones y cálculos financieros.
     */
    private void guardarOActualizarMantenimiento() {
        String p = panelFormularioMantenimiento.getPlacaSeleccionada();
        if (p == null && panelFormularioMantenimiento.getMantenimientoEnEdicion() == null) {
            JOptionPane.showMessageDialog(panelContenido, "Seleccione un vehículo.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String ts = (String) panelFormularioMantenimiento.comboServicios.getSelectedItem();
        if (ts == null || ts.contains("---")) {
            JOptionPane.showMessageDialog(panelContenido, "Seleccione un tipo de servicio.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int km;
        double mo, cr;
        try {
            km = Integer.parseInt(panelFormularioMantenimiento.campoKilometraje.getText().trim());
            // Normalización de separadores decimales (soporte internacional)
            mo = Double.parseDouble(panelFormularioMantenimiento.campoCostoManoObra.getText().trim().replace(",", "."));
            cr = Double.parseDouble(panelFormularioMantenimiento.campoCostoRepuestos.getText().trim().replace(",", "."));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panelContenido, "Kilometraje y Costos deben ser numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String d = panelFormularioMantenimiento.areaDescripcion.getText().trim();
        String e = (String) panelFormularioMantenimiento.comboEstado.getSelectedItem();
        boolean exito;
        String msj;

        if (panelFormularioMantenimiento.getMantenimientoEnEdicion() == null) {
            Mantenimiento n = new Mantenimiento(ts, d, mo, cr, km);
            n.setEstado(e);
            // RELACIÓN AGREGACIÓN: Vehículo -> Mantenimiento
            Mantenimiento guardada = controlador.agregarMantenimientoAVehiculo(p, n);
            exito = guardada != null;
            msj = exito ? "Orden registrada." : "Error al registrar.";
            if (exito) panelFormularioMantenimiento.prepararParaEditarOrden(guardada, controlador.buscarVehiculoPorPlaca(p).get());
        } else {
            Mantenimiento act = panelFormularioMantenimiento.getMantenimientoEnEdicion();
            act.setTipoServicio(ts); act.setDescripcionDetallada(d); act.setCostoManoObra(mo); act.setCostoRepuestos(cr); act.setKilometrajeActual(km); act.setEstado(e);
            exito = controlador.actualizarMantenimiento(act);
            msj = exito ? "Orden actualizada." : "Error al actualizar.";
        }

        JOptionPane.showMessageDialog(panelContenido, msj, "Gestión de Mantenimiento", exito ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
        if (exito) {
            panelFormularioMantenimiento.btnImprimirSolicitud.setEnabled(true);
            actualizarTablaDeOrdenes(controlador.obtenerTodasLasOrdenes());
        }
    }

    // =========================================================================
    // SECCIÓN 4: ACCIONES AUXILIARES (CARGA Y GESTIÓN VISUAL)
    // =========================================================================

    private void cargarVehiculoSeleccionadoEnFormulario() {
        int f = panelGestionVehiculos.tablaVehiculos.getSelectedRow();
        if (f != -1) {
            String p = (String) panelGestionVehiculos.modeloTabla.getValueAt(f, 0);
            controlador.buscarVehiculoPorPlaca(p).ifPresent(v -> {
                panelGestionVehiculos.vehiculoEnEdicion = v;
                panelGestionVehiculos.txtPlaca.setText(v.getPlaca());
                panelGestionVehiculos.txtPlaca.setEditable(false); // ID no editable
                panelGestionVehiculos.txtMarca.setText(v.getMarca());
                panelGestionVehiculos.txtModelo.setText(v.getModelo());
                panelGestionVehiculos.txtAnio.setText(String.valueOf(v.getAnio()));
                panelGestionVehiculos.txtColor.setText(v.getColor());
                panelGestionVehiculos.txtNombrePropietario.setText(v.getPropietario().getNombreCompleto());
                panelGestionVehiculos.txtCedulaPropietario.setText(v.getPropietario().getCedula());
                panelGestionVehiculos.txtTelefonoPropietario.setText(v.getPropietario().getTelefono());
                panelGestionVehiculos.txtDireccion.setText(v.getPropietario().getDireccion());
                panelGestionVehiculos.btnGuardar.setText("Actualizar Datos");
            });
        }
    }

    private void cargarMecanicoEnFormulario() {
        int f = panelGestionMecanicos.tablaMecanicos.getSelectedRow();
        if (f != -1) {
            String n = (String) panelGestionMecanicos.modeloTabla.getValueAt(f, 0);
            String e = (String) panelGestionMecanicos.modeloTabla.getValueAt(f, 1);
            Mecanico m = new Mecanico(n, e);
            panelGestionMecanicos.mecanicoEnEdicion = m;
            panelGestionMecanicos.txtNombre.setText(m.getNombreCompleto());
            panelGestionMecanicos.txtNombre.setEditable(false);
            panelGestionMecanicos.txtEspecialidad.setText(m.getEspecialidad());
            panelGestionMecanicos.btnGuardar.setText("Actualizar");
        }
    }

    private void eliminarVehiculoSeleccionado() {
        int f = panelGestionVehiculos.tablaVehiculos.getSelectedRow();
        if (f != -1) {
            String p = (String) panelGestionVehiculos.modeloTabla.getValueAt(f, 0);
            if (JOptionPane.showConfirmDialog(panelContenido, "¿Eliminar vehículo " + p + "?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                if (controlador.eliminarVehiculo(p)) {
                    JOptionPane.showMessageDialog(panelContenido, "Vehículo eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    panelGestionVehiculos.limpiarFormulario();
                    actualizarTablaDeVehiculos();
                } else {
                    JOptionPane.showMessageDialog(panelContenido, "Error al eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void eliminarMecanicoSeleccionado() {
        int f = panelGestionMecanicos.tablaMecanicos.getSelectedRow();
        if (f != -1) {
            String n = (String) panelGestionMecanicos.modeloTabla.getValueAt(f, 0);
            if (JOptionPane.showConfirmDialog(panelContenido, "¿Eliminar mecánico?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                if (controlador.eliminarMecanico(n)) {
                    JOptionPane.showMessageDialog(panelContenido, "Mecánico eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    panelGestionMecanicos.limpiarFormulario();
                    actualizarTablaMecanicos();
                } else {
                    JOptionPane.showMessageDialog(panelContenido, "Error al eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void eliminarOrdenSeleccionada() {
        int f = panelDashboard.tablaOrdenes.getSelectedRow();
        if (f != -1) {
            String id = (String) panelDashboard.modeloTabla.getValueAt(f, 0);
            if (JOptionPane.showConfirmDialog(panelContenido, "¿Eliminar orden?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                if (controlador.eliminarMantenimiento(id)) {
                    JOptionPane.showMessageDialog(panelContenido, "Orden eliminada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    actualizarTablaDeOrdenes(controlador.obtenerTodasLasOrdenes());
                } else {
                    JOptionPane.showMessageDialog(panelContenido, "Error al eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void editarOrdenSeleccionadaDesdeDashboard() {
        int f = panelDashboard.tablaOrdenes.getSelectedRow();
        if (f != -1) {
            String id = (String) panelDashboard.modeloTabla.getValueAt(f, 0);
            controlador.buscarVehiculoPorIdOrden(id).ifPresent(r -> {
                vistaAnterior = "DASHBOARD";
                panelFormularioMantenimiento.prepararParaEditarOrden(r.mantenimiento, r.vehiculo);
                cardLayout.show(panelContenido, "FORMULARIO_MANTENIMIENTO");
            });
        }
    }

    private void imprimirSolicitudDeServicio() {
        Mantenimiento m = panelFormularioMantenimiento.getMantenimientoEnEdicion();
        if (m == null) {
            JOptionPane.showMessageDialog(panelContenido, "Guarde la orden primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Optional<VehiculoYMantenimiento> r = controlador.buscarVehiculoPorIdOrden(m.getIdOrden());
        if (r.isPresent()) {
            new DialogoSolicitudServicio(this, r.get().mantenimiento, r.get().vehiculo).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(panelContenido, "Error interno.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================================================================
    // SECCIÓN 5: ALGORITMOS DE BÚSQUEDA
    // =========================================================================

    private void realizarBusquedaInteligente() {
        String texto = panelBusquedaInteligente.txtBusqueda.getText();
        String estado = (String) panelBusquedaInteligente.cmbEstado.getSelectedItem();
        String fechaDesde = panelBusquedaInteligente.getFechaDesdeStr();
        String fechaHasta = panelBusquedaInteligente.getFechaHastaStr();

        Object[][] datos = controlador.buscarOrdenesAvanzado(texto, estado, fechaDesde, fechaHasta);
        DefaultTableModel modelo = panelBusquedaInteligente.modeloTabla;
        modelo.setRowCount(0);

        for (Object[] fila : datos) modelo.addRow(fila);

        if (datos.length > 0) {
            panelBusquedaInteligente.lblTotalResultados.setText(datos.length + " registro(s) encontrado(s).");
        } else {
            panelBusquedaInteligente.lblTotalResultados.setText("Sin coincidencias.");
            JOptionPane.showMessageDialog(this, "No se encontraron resultados.");
        }
    }

    private void limpiarFiltrosBusqueda() {
        panelBusquedaInteligente.limpiarFormulario();
    }

    // --- Acciones Contextuales ---

    private void editarOrdenDesdeBusqueda() {
        int f = panelBusquedaInteligente.tablaResultados.getSelectedRow();
        if (f != -1) {
            String id = (String) panelBusquedaInteligente.modeloTabla.getValueAt(f, 0);
            controlador.buscarVehiculoPorIdOrden(id).ifPresent(r -> {
                vistaAnterior = "BUSQUEDA";
                panelFormularioMantenimiento.prepararParaEditarOrden(r.mantenimiento, r.vehiculo);
                cardLayout.show(panelContenido, "FORMULARIO_MANTENIMIENTO");
            });
        }
    }

    private void eliminarOrdenDesdeBusqueda() {
        int f = panelBusquedaInteligente.tablaResultados.getSelectedRow();
        if (f != -1) {
            String id = (String) panelBusquedaInteligente.modeloTabla.getValueAt(f, 0);
            if (JOptionPane.showConfirmDialog(this, "¿Eliminar orden?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (controlador.eliminarMantenimiento(id)) {
                    JOptionPane.showMessageDialog(this, "Orden eliminada.");
                    realizarBusquedaInteligente();
                }
            }
        }
    }

    private void crearNuevaOrdenDesdeBusqueda() {
        int f = panelBusquedaInteligente.tablaResultados.getSelectedRow();
        if (f != -1) {
            String placa = (String) panelBusquedaInteligente.modeloTabla.getValueAt(f, 2);
            vistaAnterior = "BUSQUEDA";
            panelFormularioMantenimiento.prepararParaNuevaOrden(controlador.obtenerTodosLosVehiculos());
            panelFormularioMantenimiento.comboVehiculos.setSelectedItem(placa);
            cardLayout.show(panelContenido, "FORMULARIO_MANTENIMIENTO");
            JOptionPane.showMessageDialog(this, "Vehículo " + placa + " seleccionado.", "Asistente", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void autocompletarKilometraje() {
        String p = panelFormularioMantenimiento.getPlacaSeleccionada();
        if (p != null) {
            controlador.buscarVehiculoPorPlaca(p).ifPresent(v -> {
                Optional<Mantenimiento> um = v.getHistorialMantenimientos().stream().max(Comparator.comparing(Mantenimiento::getFechaRealizacion));
                String km = um.map(m -> String.valueOf(m.getKilometrajeActual())).orElse("");
                panelFormularioMantenimiento.campoKilometraje.setText(km);
            });
        } else {
            panelFormularioMantenimiento.campoKilometraje.setText("");
        }
    }

    // =========================================================================
    // SECCIÓN 6: REPORTES (I/O Y GESTIÓN DE ARCHIVOS)
    // =========================================================================

    private void generarYAbriReporte(String tipo) {
        GestionReportes gr = new GestionReportes();
        String n = null;
        if ("html".equalsIgnoreCase(tipo)) n = gr.generarReporteHtml(controlador.obtenerTodosLosVehiculos());
        else if ("txt".equalsIgnoreCase(tipo)) n = gr.generarReporteTxt(controlador.obtenerTodosLosVehiculos());

        if (n != null) {
            panelReportes.setMensajeEstado("Reporte generado.", false);
            Object[] opciones = {"Sí, abrir ahora", "No, gracias"};
            int sel = JOptionPane.showOptionDialog(panelContenido,
                    "<html><center><b>Proceso Completado.</b><br>Reporte generado.<br>¿Desea visualizarlo?</center></html>",
                    "Confirmación de Reporte", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
            if (sel == JOptionPane.YES_OPTION) abrirArchivo(n);
        } else {
            panelReportes.setMensajeEstado("Error al generar.", true);
            JOptionPane.showMessageDialog(panelContenido, "Error de escritura I/O.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirArchivo(String n) {
        try { Desktop.getDesktop().open(new File(n)); } catch (IOException ex) {
            JOptionPane.showMessageDialog(panelContenido, "Error al abrir archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirCarpetaReportes() {
        try { Desktop.getDesktop().open(new File(".")); } catch (IOException ex) {
            JOptionPane.showMessageDialog(panelContenido, "Error al abrir carpeta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}