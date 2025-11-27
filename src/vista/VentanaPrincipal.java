/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: VentanaPrincipal.java
 * Descripcion: Clase principal de la Vista (JFrame). Actua como el contenedor
 *              central de la interfaz y orquesta la comunicacion entre el
 *              usuario, el controlador (logica de negocio) y los reportes.
 *              Gestiona el flujo de pantallas mediante CardLayout.
 * Fecha: Noviembre 2025
 * Version: 5.3 (Final Release con Gestion de Costos)
 * -----------------------------------------------------------------------------
 */
package vista;

import controlador.ControladorSIRMA;
import modelo.Mantenimiento;
import modelo.Propietario;
import modelo.Vehiculo;
import persistencia.GestionReportes;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Optional;
import java.util.List;

/**
 * Clase VentanaPrincipal
 * Gestiona el ciclo de vida de la interfaz grafica, la navegacion entre paneles
 * y la captura de eventos para las operaciones CRUD completas.
 */
public class VentanaPrincipal extends JFrame {

    // --- Componentes de Diseno y Navegacion ---
    private CardLayout cardLayout;
    private PanelMenuLateral panelMenu;
    private JPanel panelContenido;

    // --- Paneles (Vistas) del Sistema ---
    private PanelBienvenida panelBienvenida;
    private PanelDashboard panelDashboard;      // Bitacora General
    private PanelRegistroVehiculo panelRegistro;
    private PanelGestionMantenimientos panelGestion; // Gestion Centralizada (Súper Pantalla)
    private PanelHistorialVehiculo panelHistorial;   // Historial (Consulta)
    private PanelReportes panelReportes;

    // --- Referencias a la Logica (Controlador) y Persistencia ---
    private ControladorSIRMA controlador;
    private GestionReportes gestionReportes;

    /**
     * Constructor de la Ventana Principal.
     * Inicializa componentes, configura el diseno y carga las dependencias.
     * @param controlador Instancia del controlador de negocio.
     */
    public VentanaPrincipal(ControladorSIRMA controlador) {
        this.controlador = controlador;
        this.gestionReportes = new GestionReportes();

        // Configuracion del Frame Principal
        setTitle("Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA JG)");
        setSize(1280, 760); // Tamaño optimizado para mostrar tablas completas
        setLocationRelativeTo(null); // Centrar en pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Inicializacion de componentes de navegacion
        panelMenu = new PanelMenuLateral();
        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);

        // Instanciacion de todos los paneles funcionales
        panelBienvenida = new PanelBienvenida();
        panelDashboard = new PanelDashboard();
        panelRegistro = new PanelRegistroVehiculo();
        panelGestion = new PanelGestionMantenimientos();
        panelHistorial = new PanelHistorialVehiculo();
        panelReportes = new PanelReportes();

        // Agregar paneles al gestor de diseño con identificadores unicos
        panelContenido.add(panelBienvenida, "BIENVENIDA");
        panelContenido.add(panelDashboard, "DASHBOARD");
        panelContenido.add(panelRegistro, "REGISTRO");
        panelContenido.add(panelGestion, "GESTION");
        panelContenido.add(panelHistorial, "HISTORIAL");
        panelContenido.add(panelReportes, "REPORTES");

        // Distribucion en la ventana: Menu a la izquierda, Contenido al centro
        add(panelMenu, BorderLayout.WEST);
        add(panelContenido, BorderLayout.CENTER);

        // Estado inicial: Mostrar pantalla de Bienvenida
        cardLayout.show(panelContenido, "BIENVENIDA");

        // Configuracion de eventos y estilos visuales
        conectarAcciones();
        personalizarJOptionPane();
    }

    /**
     * Metodo: establecerUsuarioLogueado
     * Recibe el nombre del usuario autenticado desde el Login y actualiza la interfaz.
     * @param nombre Nombre del usuario (ej. "Admin").
     */
    public void establecerUsuarioLogueado(String nombre) {
        panelMenu.setNombreUsuario(nombre);
    }

    /**
     * Metodo: conectarAcciones
     * Centraliza la definicion de los 'ActionListeners' para todos los botones
     * del sistema, definiendo el flujo de la aplicacion.
     */
    private void conectarAcciones() {

        // ----------------------------------------------------------
        // 1. NAVEGACION DEL MENU LATERAL
        // ----------------------------------------------------------

        panelMenu.btnInicio.addActionListener(e -> cardLayout.show(panelContenido, "BIENVENIDA"));

        panelMenu.btnRegistrarVehiculo.addActionListener(e -> {
            limpiarCamposRegistro();
            cardLayout.show(panelContenido, "REGISTRO");
        });

        // Boton "Gestion Mantenimiento": Abre la pantalla unificada
        panelMenu.btnAgregarMantenimiento.addActionListener(e -> irAGestionMantenimiento());

        // Boton "Bitacora General": Abre el Dashboard
        panelMenu.btnVerHistorial.addActionListener(e -> irABitacora());

        // ----------------------------------------------------------
        // 2. ACCIONES DE LA PANTALLA DE BIENVENIDA (TARJETAS)
        // ----------------------------------------------------------

        panelBienvenida.btnVehiculos.addActionListener(e -> irABitacora());

        panelBienvenida.btnMantenimientos.addActionListener(e -> irAGestionMantenimiento());

        panelBienvenida.btnReportes.addActionListener(e -> {
            panelReportes.setMensajeEstado("Seleccione un formato...", Color.WHITE);
            cardLayout.show(panelContenido, "REPORTES");
        });

        // ----------------------------------------------------------
        // 3. BOTONES DE RETORNO Y NAVEGACION INTERNA
        // ----------------------------------------------------------

        panelDashboard.btnVolverInicio.addActionListener(e -> cardLayout.show(panelContenido, "BIENVENIDA"));
        panelReportes.btnVolverInicio.addActionListener(e -> cardLayout.show(panelContenido, "BIENVENIDA"));
        panelGestion.btnVolver.addActionListener(e -> cardLayout.show(panelContenido, "BIENVENIDA"));
        panelHistorial.btnVolver.addActionListener(e -> irABitacora());

        // Refrescar Bitacora manualmente
        panelDashboard.btnRefrescar.addActionListener(e -> irABitacora());

        // ----------------------------------------------------------
        // 4. GESTION DE REPORTES Y ARCHIVOS
        // ----------------------------------------------------------

        panelReportes.btnAbrirCarpeta.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(new File("."));
            } catch (Exception ex) {
                panelReportes.setMensajeEstado("Error al abrir carpeta.", Color.RED);
            }
        });

        panelReportes.btnGenerarHTML.addActionListener(e -> {
            String archivo = gestionReportes.generarReporteHtml(controlador.obtenerTodosLosVehiculos());
            panelReportes.setMensajeEstado("HTML Generado: " + archivo, Color.GREEN);
            abrirArchivoAutomaticamente(archivo);
        });

        panelReportes.btnGenerarTXT.addActionListener(e -> {
            String archivo = gestionReportes.generarReporteTxt(controlador.obtenerTodosLosVehiculos());
            panelReportes.setMensajeEstado("TXT Generado: " + archivo, Color.GREEN);
            abrirArchivoAutomaticamente(archivo);
        });

        // ----------------------------------------------------------
        // 5. LOGICA DE LA PANTALLA DE GESTION (CRUD UNIFICADO)
        // ----------------------------------------------------------

        // Al seleccionar vehiculo del combo, cargar su historial en la tabla inferior
        panelGestion.comboVehiculos.addActionListener(e -> {
            String placa = (String) panelGestion.comboVehiculos.getSelectedItem();
            if (placa != null) cargarTablaMantenimientoEnGestion(placa);
        });

        panelGestion.btnGuardar.addActionListener(e -> guardarOrdenServicio());

        panelGestion.btnLimpiar.addActionListener(e -> panelGestion.limpiarCampos());

        panelGestion.btnImprimir.addActionListener(e -> imprimirOrdenSeleccionada());

        panelGestion.btnEliminar.addActionListener(e -> eliminarOrdenSeleccionadaEnGestion());

        // ----------------------------------------------------------
        // 6. OTROS CRUD (Registro Vehiculo y Eliminacion Historica)
        // ----------------------------------------------------------

        panelRegistro.btnGuardar.addActionListener(e -> guardarNuevoVehiculo());
        panelHistorial.btnEliminarRegistro.addActionListener(e -> eliminarMantenimientoSeleccionado());
    }

    // --- METODOS DE FLUJO Y NAVEGACION ---

    /**
     * Metodo: irABitacora
     * Actualiza la tabla principal con los datos mas recientes y muestra el panel.
     */
    private void irABitacora() {
        String filtro = panelDashboard.txtBuscador.getText();
        // Obtenemos la matriz de datos plana para la tabla de ordenes
        Object[][] datos = controlador.obtenerBitacoraOrdenes(filtro);
        panelDashboard.llenarTablaOrdenes(datos);
        cardLayout.show(panelContenido, "DASHBOARD");
    }

    /**
     * Metodo: irAGestionMantenimiento
     * Prepara el panel de gestion cargando la lista de vehiculos actualizada.
     */
    private void irAGestionMantenimiento() {
        panelGestion.cargarVehiculos(controlador.obtenerTodosLosVehiculos());
        panelGestion.limpiarCampos();
        cardLayout.show(panelContenido, "GESTION");
    }

    // --- LOGICA DE NEGOCIO: GESTION DE MANTENIMIENTO (CORE) ---

    /**
     * Metodo: cargarTablaMantenimientoEnGestion
     * Busca el historial de un vehiculo especifico y lo muestra en la tabla de gestion.
     * @param placa Identificador del vehiculo.
     */
    private void cargarTablaMantenimientoEnGestion(String placa) {
        Optional<Vehiculo> opt = controlador.buscarVehiculoPorPlaca(placa);
        if (opt.isPresent()) {
            Vehiculo v = opt.get();
            panelGestion.modeloTabla.setRowCount(0);
            for (Mantenimiento m : v.getHistorialMantenimientos()) {
                // Mostramos ID, Fecha, Servicio, Estado y Total
                panelGestion.modeloTabla.addRow(new Object[]{
                        m.getIdOrden(), m.getFechaRealizacion(), m.getTipoServicio(), m.getEstado(), "$" + m.getCostoTotal()
                });
            }
        }
    }

    /**
     * Metodo: guardarOrdenServicio (CREATE/UPDATE ORDEN)
     * Captura los datos del formulario, valida costos, crea la orden y genera la impresion.
     */
    private void guardarOrdenServicio() {
        String placa = (String) panelGestion.comboVehiculos.getSelectedItem();
        if (placa == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un vehiculo.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Captura de datos con validacion numerica
            double manoObra = Double.parseDouble(panelGestion.txtCostoManoObra.getText());
            double repuestos = Double.parseDouble(panelGestion.txtCostoRepuestos.getText());
            int km = Integer.parseInt(panelGestion.txtKilometraje.getText());

            String tipo = (String) panelGestion.comboServicios.getSelectedItem();
            String desc = panelGestion.txtDescripcion.getText();
            String estado = (String) panelGestion.comboEstado.getSelectedItem();

            // Logica Inteligente: Generacion automatica de ID y Asignacion
            String idOrden = controlador.generarNuevoIdOrden();
            String mecanico = controlador.asignarMecanicoInteligente(tipo);

            // Creacion del objeto Mantenimiento (Modelo)
            Mantenimiento m = new Mantenimiento(idOrden, tipo, desc, manoObra, repuestos, km, java.time.LocalDate.now());
            m.setEstado(estado);

            // Persistencia mediante el Controlador
            if (controlador.agregarMantenimientoAVehiculo(placa, m)) {
                JOptionPane.showMessageDialog(this, "Orden Guardada Exitosamente");
                cargarTablaMantenimientoEnGestion(placa); // Refrescar tabla en tiempo real
                mostrarHojaImpresion(m, placa, mecanico); // Simular impresion
            }
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Verifique los campos numericos (Costo/Km).", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo: imprimirOrdenSeleccionada
     * Busca la orden seleccionada en la tabla y muestra su vista previa.
     */
    private void imprimirOrdenSeleccionada() {
        int fila = panelGestion.tablaHistorial.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una orden de la tabla de abajo.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idOrden = (String) panelGestion.modeloTabla.getValueAt(fila, 0);
        String placa = (String) panelGestion.comboVehiculos.getSelectedItem();

        // Busqueda del objeto real en memoria
        Optional<Vehiculo> opt = controlador.buscarVehiculoPorPlaca(placa);
        if (opt.isPresent()) {
            for (Mantenimiento m : opt.get().getHistorialMantenimientos()) {
                if (m.getIdOrden().equals(idOrden)) {
                    String mecanico = controlador.asignarMecanicoInteligente(m.getTipoServicio());
                    mostrarHojaImpresion(m, placa, mecanico);
                    return;
                }
            }
        }
    }

    /**
     * Metodo: eliminarOrdenSeleccionadaEnGestion
     * Elimina una orden especifica directamente desde la pantalla de gestion.
     */
    private void eliminarOrdenSeleccionadaEnGestion() {
        int fila = panelGestion.tablaHistorial.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una orden para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idOrden = (String) panelGestion.modeloTabla.getValueAt(fila, 0);
        String placa = (String) panelGestion.comboVehiculos.getSelectedItem();

        if (JOptionPane.showConfirmDialog(this, "¿Eliminar orden permanentemente?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            Optional<Vehiculo> opt = controlador.buscarVehiculoPorPlaca(placa);
            if (opt.isPresent()) {
                Vehiculo v = opt.get();
                // Uso de Streams para buscar el objeto a borrar
                Mantenimiento aBorrar = v.getHistorialMantenimientos().stream()
                        .filter(m -> m.getIdOrden().equals(idOrden)).findFirst().orElse(null);

                if(aBorrar != null && controlador.eliminarMantenimiento(placa, aBorrar)) {
                    cargarTablaMantenimientoEnGestion(placa); // Actualizar visualmente
                    JOptionPane.showMessageDialog(this, "Orden eliminada.");
                }
            }
        }
    }

    /**
     * Metodo: mostrarHojaImpresion
     * Genera un panel HTML visual que simula una hoja de papel impresa.
     */
    private void mostrarHojaImpresion(Mantenimiento m, String placa, String mecanico) {
        String ordenImpresa =
                "<html><body style='width: 350px; font-family: monospace; color: #000; background-color: #fff; padding: 20px;'>" +
                        "<h2 style='text-align: center; border-bottom: 2px solid #000;'>SOLICITUD DE MANTENIMIENTO</h2>" +
                        "<p><b>ORDEN:</b> " + m.getIdOrden() + " &nbsp;&nbsp; <b>FECHA:</b> " + m.getFechaRealizacion() + "</p>" +
                        "<table width='100%'><tr><td><b>PLACA:</b> " + placa + "</td><td><b>KM:</b> " + m.getKilometrajeActual() + "</td></tr></table>" +
                        "<hr>" +
                        "<p><b>SERVICIO:</b> " + m.getTipoServicio().toUpperCase() + "</p>" +
                        "<p><b>DETALLE:</b> " + m.getDescripcionDetallada() + "</p>" +
                        "<p><b>ASIGNADO A:</b> " + mecanico + "</p>" +
                        "<p><b>ESTADO:</b> " + m.getEstado().toUpperCase() + "</p>" +
                        "<hr>" +
                        "<table width='100%'>" +
                        "<tr><td>Mano de Obra:</td><td align='right'>$" + m.getCostoManoObra() + "</td></tr>" +
                        "<tr><td>Repuestos:</td><td align='right'>$" + m.getCostoRepuestos() + "</td></tr>" +
                        "<tr><td><b>TOTAL:</b></td><td align='right'><b>$" + m.getCostoTotal() + "</b></td></tr>" +
                        "</table>" +
                        "<br><br><br>" +
                        "<p style='text-align: center; border-top: 1px solid #000;'>FIRMA DEL CLIENTE</p>" +
                        "</body></html>";

        JLabel hoja = new JLabel(ordenImpresa);
        hoja.setOpaque(true);
        hoja.setBackground(Color.WHITE);
        hoja.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Borde sutil

        JOptionPane.showMessageDialog(this, hoja, "Vista Previa de Impresion", JOptionPane.PLAIN_MESSAGE);
    }

    // --- LOGICA DE REGISTRO DE VEHICULO ---

    /**
     * Metodo: guardarNuevoVehiculo
     * Procesa el formulario de registro de un nuevo activo.
     */
    private void guardarNuevoVehiculo() {
        String placa = panelRegistro.txtPlaca.getText().toUpperCase().trim();
        if (placa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Campos obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE); return;
        }
        if (!controlador.esFormatoPlacaValido(placa)) {
            JOptionPane.showMessageDialog(this, "Placa invalida.", "Error", JOptionPane.ERROR_MESSAGE); return;
        }
        try {
            int anio = Integer.parseInt(panelRegistro.txtAnio.getText());
            Propietario p = new Propietario(panelRegistro.txtNombrePropietario.getText().toUpperCase(), panelRegistro.txtCedulaPropietario.getText(), panelRegistro.txtTelefonoPropietario.getText());
            Vehiculo v = new Vehiculo(placa, panelRegistro.txtMarca.getText().toUpperCase(), panelRegistro.txtModelo.getText().toUpperCase(), anio, panelRegistro.txtColor.getText().toUpperCase(), p);

            if(controlador.registrarVehiculo(v)) {
                JOptionPane.showMessageDialog(this, "Vehiculo registrado correctamente.");
                irABitacora();
                limpiarCamposRegistro();
            } else {
                JOptionPane.showMessageDialog(this, "La placa ya existe.");
            }
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Revise los datos."); }
    }

    // --- METODOS AUXILIARES ---

    private void eliminarMantenimientoSeleccionado() {
        Mantenimiento m = panelHistorial.getMantenimientoSeleccionado();
        if (m != null) {
            if (JOptionPane.showConfirmDialog(this, "¿Eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                controlador.eliminarMantenimiento(panelHistorial.getVehiculoActual().getPlaca(), m);
                panelHistorial.cargarHistorial(panelHistorial.getVehiculoActual());
            }
        }
    }

    private void limpiarCamposRegistro() {
        panelRegistro.txtPlaca.setText("");
        panelRegistro.txtMarca.setText("");
        panelRegistro.txtModelo.setText("");
        panelRegistro.txtAnio.setText("");
        panelRegistro.txtColor.setText("");
        panelRegistro.txtNombrePropietario.setText("");
        panelRegistro.txtCedulaPropietario.setText("");
        panelRegistro.txtTelefonoPropietario.setText("");
    }

    private void abrirArchivoAutomaticamente(String nombreArchivo) {
        if (nombreArchivo != null) {
            try {
                File archivo = new File(nombreArchivo);
                if (archivo.exists()) Desktop.getDesktop().open(archivo);
            } catch (Exception ex) { /* Ignorar error de apertura */ }
        }
    }

    /**
     * Metodo: personalizarJOptionPane
     * Aplica estilos Dark Mode globales a los dialogos.
     */
    private void personalizarJOptionPane() {
        UIManager.put("OptionPane.background", new Color(45, 50, 55));
        UIManager.put("Panel.background", new Color(45, 50, 55));
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.background", new Color(50, 55, 60));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.focus", new Color(0, 120, 215));
    }

    // Metodos auxiliares para mantener compatibilidad con panelHistorial
    private void mostrarHistorialDesdeTabla() {
        int fila = panelDashboard.tablaOrdenes.getSelectedRow();
        if (fila != -1) {
            String placa = (String) panelDashboard.modeloTabla.getValueAt(fila, 2);
            cargarHistorialPorPlaca(placa);
        }
    }
    private void solicitarPlacaYMostrarHistorial() {
        String placa = JOptionPane.showInputDialog(this, "Placa:", "Busqueda", JOptionPane.QUESTION_MESSAGE);
        if(placa != null && !placa.isEmpty()) cargarHistorialPorPlaca(placa);
    }
    private void cargarHistorialPorPlaca(String placa) {
        Optional<Vehiculo> opt = controlador.buscarVehiculoPorPlaca(placa);
        if(opt.isPresent()) { panelHistorial.cargarHistorial(opt.get()); cardLayout.show(panelContenido, "HISTORIAL"); }
        else { JOptionPane.showMessageDialog(this, "No encontrado"); }
    }
}