/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Descripcion: Gestiona la navegacion e interaccion para el CRUD completo.
 * Fecha: Noviembre 2025
 * -----------------------------------------------------------------------------
 */
package vista;

import controlador.ControladorSIRMA;
import modelo.Mantenimiento;
import modelo.Propietario;
import modelo.Vehiculo;
import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class VentanaPrincipal extends JFrame {

    private CardLayout cardLayout;
    private PanelMenuLateral panelMenu;
    private JPanel panelContenido;

    private PanelDashboard panelDashboard;
    private PanelRegistroVehiculo panelRegistro;
    private PanelAgregarMantenimiento panelAgregarMantenimiento;

    private ControladorSIRMA controlador;

    public VentanaPrincipal(ControladorSIRMA controlador) {
        this.controlador = controlador;

        setTitle("SIRMA-JG: Sistema de Registro de Mantenimiento de Vehiculos");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        panelMenu = new PanelMenuLateral();

        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);

        panelDashboard = new PanelDashboard();
        panelRegistro = new PanelRegistroVehiculo();
        panelAgregarMantenimiento = new PanelAgregarMantenimiento();

        panelContenido.add(panelDashboard, "DASHBOARD");
        panelContenido.add(panelRegistro, "REGISTRO");
        panelContenido.add(panelAgregarMantenimiento, "AGREGAR_MANTENIMIENTO");

        add(panelMenu, BorderLayout.WEST);
        add(panelContenido, BorderLayout.CENTER);

        panelDashboard.actualizarTabla(controlador.obtenerTodosLosVehiculos());

        conectarAcciones();
        personalizarJOptionPane(); // Aplicar estilo al iniciar
    }

    private void conectarAcciones() {
        panelMenu.btnRegistrarVehiculo.addActionListener(e -> cardLayout.show(panelContenido, "REGISTRO"));

        panelMenu.btnBuscarVehiculo.addActionListener(e -> {
            panelAgregarMantenimiento.actualizarListaVehiculos(controlador.obtenerTodosLosVehiculos());
            cardLayout.show(panelContenido, "AGREGAR_MANTENIMIENTO");
        });

        panelMenu.btnVerHistorial.addActionListener(e -> {
            panelDashboard.actualizarTabla(controlador.obtenerTodosLosVehiculos());
            cardLayout.show(panelContenido, "DASHBOARD");
        });

        panelAgregarMantenimiento.btnVolver.addActionListener(e -> cardLayout.show(panelContenido, "DASHBOARD"));

        panelRegistro.btnGuardar.addActionListener(e -> guardarNuevoVehiculo());
        panelAgregarMantenimiento.btnGuardarMantenimiento.addActionListener(e -> guardarNuevoMantenimiento());
        panelDashboard.btnEliminarVehiculo.addActionListener(e -> eliminarVehiculoSeleccionado());
    }

    private void guardarNuevoVehiculo() {
        String placa = panelRegistro.txtPlaca.getText();
        String marca = panelRegistro.txtMarca.getText();

        if (placa.trim().isEmpty() || marca.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La Placa y la Marca son campos obligatorios.", "Campos Vacios", JOptionPane.WARNING_MESSAGE);
            return;
        }

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

        Propietario p = new Propietario(panelRegistro.txtNombrePropietario.getText(), panelRegistro.txtCedulaPropietario.getText(), panelRegistro.txtTelefonoPropietario.getText());
        Vehiculo v = new Vehiculo(placa, marca, panelRegistro.txtModelo.getText(), anio, panelRegistro.txtColor.getText(), p);

        if (controlador.registrarVehiculo(v)) {
            JOptionPane.showMessageDialog(this, "Vehiculo registrado con exito", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            panelDashboard.actualizarTabla(controlador.obtenerTodosLosVehiculos());
            cardLayout.show(panelContenido, "DASHBOARD");
            limpiarCamposRegistro();
        } else {
            JOptionPane.showMessageDialog(this, "Error: La placa '" + placa.toUpperCase() + "' ya existe.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo: guardarNuevoMantenimiento (UPDATE)
     * Recolecta los datos del formulario de mantenimiento y los envia al controlador.
     */
    private void guardarNuevoMantenimiento() {
        String placaSeleccionada = (String) panelAgregarMantenimiento.comboVehiculos.getSelectedItem();
        if (placaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un vehiculo.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // NUEVO: Obtener el servicio del ComboBox
        int indiceServicio = panelAgregarMantenimiento.comboServicios.getSelectedIndex();
        if (indiceServicio == 0) { // El indice 0 es "--- Seleccione un Servicio ---"
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

        String descripcion = panelAgregarMantenimiento.txtDescripcion.getText();

        Mantenimiento nuevoMantenimiento = new Mantenimiento(tipoServicio, descripcion, costo, kilometraje);

        if (controlador.agregarMantenimientoAVehiculo(placaSeleccionada, nuevoMantenimiento)) {
            JOptionPane.showMessageDialog(this, "Mantenimiento agregado con exito al vehiculo " + placaSeleccionada, "Exito", JOptionPane.INFORMATION_MESSAGE);
            panelAgregarMantenimiento.limpiarCampos(); // Limpia el formulario
            cardLayout.show(panelContenido, "DASHBOARD");
        } else {
            JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado al agregar el mantenimiento.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

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

    private void personalizarJOptionPane() {
        UIManager.put("OptionPane.background", new Color(45, 50, 55));
        UIManager.put("Panel.background", new Color(45, 50, 55));
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.background", new Color(50, 55, 60));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.focus", new Color(0, 120, 215));
    }
}