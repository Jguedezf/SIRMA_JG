/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion: Clase principal de la Vista. Actua como contenedor de todos los
 *              paneles y orquesta la interaccion entre el usuario, la logica
 *              de negocio (Controlador) y la generacion de reportes.
 * Fecha: Noviembre 2025
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
 * JFrame que gestiona la navegacion entre las distintas pantallas (CardLayout)
 * y centraliza los eventos de la interfaz grafica.
 */
public class VentanaPrincipal extends JFrame {

    // Componentes de Navegacion y Layout
    private CardLayout cardLayout;
    private PanelMenuLateral panelMenu;
    private JPanel panelContenido;

    // Paneles (Pantallas) del Sistema
    private PanelBienvenida panelBienvenida;
    private PanelDashboard panelDashboard;
    private PanelRegistroVehiculo panelRegistro;
    private PanelAgregarMantenimiento panelAgregarMantenimiento;
    private PanelHistorialVehiculo panelHistorial;
    private PanelReportes panelReportes;

    // Referencias a Logica y Persistencia
    private ControladorSIRMA controlador;
    private GestionReportes gestionReportes;

    /**
     * Constructor de la Ventana Principal.
     * Inicializa componentes, configura el diseno y carga los datos iniciales.
     * @param controlador Instancia del controlador principal.
     */
    public VentanaPrincipal(ControladorSIRMA controlador) {
        this.controlador = controlador;
        this.gestionReportes = new GestionReportes();

        // Configuracion basica de la ventana
        setTitle("Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA JG)");
        setSize(1200, 700);
        setLocationRelativeTo(null); // Centrar en pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Inicializacion del Menu Lateral
        panelMenu = new PanelMenuLateral();

        // Configuracion del panel de contenido dinamico (CardLayout)
        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);

        // Instanciacion de las diferentes pantallas
        panelBienvenida = new PanelBienvenida();
        panelDashboard = new PanelDashboard();
        panelRegistro = new PanelRegistroVehiculo();
        panelAgregarMantenimiento = new PanelAgregarMantenimiento();
        panelHistorial = new PanelHistorialVehiculo();
        panelReportes = new PanelReportes();

        // Agregamos las pantallas al gestor de diseño con identificadores unicos
        panelContenido.add(panelBienvenida, "BIENVENIDA");
        panelContenido.add(panelDashboard, "DASHBOARD");
        panelContenido.add(panelRegistro, "REGISTRO");
        panelContenido.add(panelAgregarMantenimiento, "AGREGAR_MANTENIMIENTO");
        panelContenido.add(panelHistorial, "HISTORIAL");
        panelContenido.add(panelReportes, "REPORTES");

        // Distribucion en la ventana (Menu a la izquierda, Contenido al centro)
        add(panelMenu, BorderLayout.WEST);
        add(panelContenido, BorderLayout.CENTER);

        // Estado Inicial: Mostrar Bienvenida
        cardLayout.show(panelContenido, "BIENVENIDA");

        // Vinculacion de logica y estilos
        conectarAcciones();
        personalizarJOptionPane();
    }

    /**
     * Metodo: conectarAcciones
     * Define y asigna los 'Listeners' para todos los botones de la aplicacion,
     * estableciendo el flujo de navegacion y las llamadas a la logica de negocio.
     */
    private void conectarAcciones() {
        // --- NAVEGACION DEL MENU LATERAL ---
        panelMenu.btnInicio.addActionListener(e -> cardLayout.show(panelContenido, "BIENVENIDA"));

        panelMenu.btnRegistrarVehiculo.addActionListener(e -> {
            limpiarCamposRegistro();
            cardLayout.show(panelContenido, "REGISTRO");
        });

        panelMenu.btnAgregarMantenimiento.addActionListener(e -> {
            panelAgregarMantenimiento.actualizarListaVehiculos(controlador.obtenerTodosLosVehiculos());
            panelAgregarMantenimiento.limpiarCampos();
            cardLayout.show(panelContenido, "AGREGAR_MANTENIMIENTO");
        });

        panelMenu.btnVerHistorial.addActionListener(e -> {
            if (panelDashboard.isShowing() && panelDashboard.tablaVehiculos.getSelectedRow() != -1) {
                mostrarHistorialDesdeTabla();
            } else {
                solicitarPlacaYMostrarHistorial();
            }
        });

        // --- ACCIONES DE LA PANTALLA DE BIENVENIDA ---
        panelBienvenida.btnVehiculos.addActionListener(e -> irAlListado());

        panelBienvenida.btnMantenimientos.addActionListener(e -> {
            limpiarCamposRegistro();
            cardLayout.show(panelContenido, "REGISTRO");
        });

        panelBienvenida.btnReportes.addActionListener(e -> {
            panelReportes.setMensajeEstado("Seleccione un formato...", Color.WHITE);
            cardLayout.show(panelContenido, "REPORTES");
        });

        // --- BOTONES DE NAVEGACION (VOLVER) ---
        panelDashboard.btnVolverInicio.addActionListener(e -> cardLayout.show(panelContenido, "BIENVENIDA"));
        panelReportes.btnVolverInicio.addActionListener(e -> cardLayout.show(panelContenido, "BIENVENIDA"));
        panelAgregarMantenimiento.btnVolver.addActionListener(e -> irAlListado());
        panelHistorial.btnVolver.addActionListener(e -> irAlListado());

        // --- LOGICA DEL PANEL DE REPORTES ---
        panelReportes.btnAbrirCarpeta.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(new File("."));
            } catch (Exception ex) {
                panelReportes.setMensajeEstado("Error al abrir carpeta.", Color.RED);
            }
        });

        panelReportes.btnGenerarHTML.addActionListener(e -> {
            String archivo = gestionReportes.generarReporteHtml(controlador.obtenerTodosLosVehiculos());
            panelReportes.setMensajeEstado("HTML generado: " + archivo, Color.GREEN);
            abrirArchivoAutomaticamente(archivo);
        });

        panelReportes.btnGenerarTXT.addActionListener(e -> {
            String archivo = gestionReportes.generarReporteTxt(controlador.obtenerTodosLosVehiculos());
            panelReportes.setMensajeEstado("TXT generado: " + archivo, Color.GREEN);
            abrirArchivoAutomaticamente(archivo);
        });

        // --- BOTONES DE ACCION CRUD ---
        panelRegistro.btnGuardar.addActionListener(e -> guardarNuevoVehiculo());
        panelAgregarMantenimiento.btnGuardarMantenimiento.addActionListener(e -> guardarNuevoMantenimiento());
        panelDashboard.btnEliminarVehiculo.addActionListener(e -> eliminarVehiculoSeleccionado());
        panelHistorial.btnEliminarRegistro.addActionListener(e -> eliminarMantenimientoSeleccionado());
    }

    // --- METODOS AUXILIARES DE NAVEGACION ---

    /**
     * Metodo: irAlListado
     * Actualiza la tabla de datos y muestra el panel Dashboard.
     */
    private void irAlListado() {
        panelDashboard.actualizarTabla(controlador.obtenerTodosLosVehiculos());
        cardLayout.show(panelContenido, "DASHBOARD");
    }

    /**
     * Metodo: abrirArchivoAutomaticamente
     * Intenta abrir el archivo generado con la aplicacion predeterminada del sistema.
     * @param nombreArchivo Ruta o nombre del archivo a abrir.
     */
    private void abrirArchivoAutomaticamente(String nombreArchivo) {
        if (nombreArchivo != null) {
            try {
                File archivo = new File(nombreArchivo);
                if (archivo.exists()) Desktop.getDesktop().open(archivo);
            } catch (Exception ex) {
                // Silencioso, el usuario ya ve el mensaje de exito en pantalla
            }
        }
    }

    /**
     * Metodo: mostrarHistorialDesdeTabla
     * Carga el historial del vehiculo actualmente seleccionado en la tabla.
     */
    private void mostrarHistorialDesdeTabla() {
        int fila = panelDashboard.tablaVehiculos.getSelectedRow();
        String placa = (String) panelDashboard.modeloTabla.getValueAt(fila, 0);
        cargarHistorialPorPlaca(placa);
    }

    /**
     * Metodo: solicitarPlacaYMostrarHistorial
     * Pide al usuario una placa manualmente y busca su historial.
     */
    private void solicitarPlacaYMostrarHistorial() {
        String placa = JOptionPane.showInputDialog(this, "Ingrese la placa del vehiculo a consultar:", "Busqueda Rapida", JOptionPane.QUESTION_MESSAGE);
        if (placa != null && !placa.trim().isEmpty()) {
            cargarHistorialPorPlaca(placa.trim());
        }
    }

    /**
     * Metodo: cargarHistorialPorPlaca
     * Busca el vehiculo en el controlador y, si existe, carga sus datos en el panel de historial.
     * @param placa Placa del vehiculo a buscar.
     */
    private void cargarHistorialPorPlaca(String placa) {
        Optional<Vehiculo> vehiculoOpt = controlador.buscarVehiculoPorPlaca(placa);
        if (vehiculoOpt.isPresent()) {
            panelHistorial.cargarHistorial(vehiculoOpt.get());
            cardLayout.show(panelContenido, "HISTORIAL");
        } else {
            JOptionPane.showMessageDialog(this, "Vehiculo no encontrado con placa: " + placa, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- METODOS DE LOGICA DE NEGOCIO (CRUD) ---

    /**
     * Metodo: guardarNuevoVehiculo (CREATE)
     * Valida los campos del formulario y delega el registro al controlador.
     */
    private void guardarNuevoVehiculo() {
        String placa = panelRegistro.txtPlaca.getText();
        String marca = panelRegistro.txtMarca.getText();

        // Validaciones de campos obligatorios
        if (placa.trim().isEmpty() || marca.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La Placa y la Marca son campos obligatorios.", "Campos Vacios", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validacion de formato de placa
        if (!controlador.esFormatoPlacaValido(placa)) {
            JOptionPane.showMessageDialog(this, "Formato de placa invalido.\nFormatos aceptados: AB123CD o ABC123", "Formato Incorrecto", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int anio;
        try {
            anio = Integer.parseInt(panelRegistro.txtAnio.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El anio debe ser un numero valido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Creacion de objetos
        Propietario p = new Propietario(
                panelRegistro.txtNombrePropietario.getText(),
                panelRegistro.txtCedulaPropietario.getText(),
                panelRegistro.txtTelefonoPropietario.getText()
        );
        Vehiculo v = new Vehiculo(placa, marca, panelRegistro.txtModelo.getText(), anio, panelRegistro.txtColor.getText(), p);

        // Registro
        if (controlador.registrarVehiculo(v)) {
            JOptionPane.showMessageDialog(this, "Vehiculo registrado con exito", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            irAlListado();
            limpiarCamposRegistro();
        } else {
            JOptionPane.showMessageDialog(this, "Error: La placa '" + placa.toUpperCase() + "' ya existe.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo: guardarNuevoMantenimiento (UPDATE)
     * Agrega un servicio al historial de un vehiculo existente.
     */
    private void guardarNuevoMantenimiento() {
        String placaSeleccionada = (String) panelAgregarMantenimiento.comboVehiculos.getSelectedItem();
        if (placaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un vehiculo.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int indiceServicio = panelAgregarMantenimiento.comboServicios.getSelectedIndex();
        if (indiceServicio == 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un tipo de servicio.", "Campo Vacio", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String tipoServicio = (String) panelAgregarMantenimiento.comboServicios.getSelectedItem();

        double costo;
        int kilometraje;
        try {
            costo = Double.parseDouble(panelAgregarMantenimiento.txtCosto.getText());
            kilometraje = Integer.parseInt(panelAgregarMantenimiento.txtKilometraje.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Costo y Kilometraje deben ser numeros validos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Mantenimiento nuevoMantenimiento = new Mantenimiento(tipoServicio, panelAgregarMantenimiento.txtDescripcion.getText(), costo, kilometraje);

        if (controlador.agregarMantenimientoAVehiculo(placaSeleccionada, nuevoMantenimiento)) {
            JOptionPane.showMessageDialog(this, "Mantenimiento agregado con exito al vehiculo " + placaSeleccionada, "Exito", JOptionPane.INFORMATION_MESSAGE);
            panelAgregarMantenimiento.limpiarCampos();
            irAlListado();
        } else {
            JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo: eliminarVehiculoSeleccionado (DELETE Vehiculo)
     * Elimina un vehiculo de la base de datos previa confirmacion.
     */
    private void eliminarVehiculoSeleccionado() {
        int filaSeleccionada = panelDashboard.tablaVehiculos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un vehiculo de la tabla para eliminar.", "Ningun Vehiculo Seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String placa = (String) panelDashboard.modeloTabla.getValueAt(filaSeleccionada, 0);

        int confirmacion = JOptionPane.showConfirmDialog(this, "Esta seguro de que desea eliminar el vehiculo con placa " + placa + "?\nEsta accion no se puede deshacer.", "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (controlador.eliminarVehiculo(placa)) {
                JOptionPane.showMessageDialog(this, "Vehiculo eliminado con exito.", "Eliminacion Completa", JOptionPane.INFORMATION_MESSAGE);
                panelDashboard.actualizarTabla(controlador.obtenerTodosLosVehiculos());
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el vehiculo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Metodo: eliminarMantenimientoSeleccionado (DELETE Mantenimiento)
     * Elimina un registro especifico del historial de un vehiculo.
     */
    private void eliminarMantenimientoSeleccionado() {
        Mantenimiento m = panelHistorial.getMantenimientoSeleccionado();
        if (m != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "Desea eliminar este registro de mantenimiento permanentemente?", "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                Vehiculo v = panelHistorial.getVehiculoActual();
                boolean exito = controlador.eliminarMantenimiento(v.getPlaca(), m);
                if (exito) {
                    panelHistorial.cargarHistorial(v);
                    JOptionPane.showMessageDialog(this, "Registro eliminado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un registro de la tabla para eliminar.", "Sin Seleccion", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Metodo: limpiarCamposRegistro
     * Helper para limpiar el formulario de registro.
     */
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

    /**
     * Metodo: personalizarJOptionPane
     * Aplica un tema oscuro (Dark Mode) a los mensajes del sistema para mantener
     * la coherencia visual.
     */
    private void personalizarJOptionPane() {
        UIManager.put("OptionPane.background", new Color(45, 50, 55));
        UIManager.put("Panel.background", new Color(45, 50, 55));
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.background", new Color(50, 55, 60));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.focus", new Color(0, 120, 215));
    }
}