/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: VentanaPrincipal.java
 * Descripcion: Gestiona la navegacion e interaccion para el CRUD completo.
 * Fecha: Noviembre 2025
 * Version: 1.8
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
    private JPanel panelContenido; // Contenedor principal para las "pantallas"

    // Las diferentes pantallas que se mostraran en el panel de contenido
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

        // --- Configuracion de Paneles ---
        panelMenu = new PanelMenuLateral();

        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);

        // Se crean las instancias de todos los paneles que usara la aplicacion
        panelDashboard = new PanelDashboard();
        panelRegistro = new PanelRegistroVehiculo();
        panelAgregarMantenimiento = new PanelAgregarMantenimiento();

        // Se anaden los paneles al CardLayout con un nombre unico para identificarlos
        panelContenido.add(panelDashboard, "DASHBOARD");
        panelContenido.add(panelRegistro, "REGISTRO");
        panelContenido.add(panelAgregarMantenimiento, "AGREGAR_MANTENIMIENTO");

        add(panelMenu, BorderLayout.WEST);
        add(panelContenido, BorderLayout.CENTER);

        // Carga inicial de datos en la tabla del dashboard
        panelDashboard.actualizarTabla(controlador.obtenerTodosLosVehiculos());

        conectarAcciones();
    }

    /**
     * Metodo central que asigna las acciones a los botones de la interfaz.
     */
    private void conectarAcciones() {
        // --- Navegacion del Menu Lateral ---
        panelMenu.btnRegistrarVehiculo.addActionListener(e -> cardLayout.show(panelContenido, "REGISTRO"));

        panelMenu.btnBuscarVehiculo.addActionListener(e -> {
            // Antes de mostrar el panel, se actualiza la lista de vehiculos en el ComboBox
            panelAgregarMantenimiento.actualizarListaVehiculos(controlador.obtenerTodosLosVehiculos());
            cardLayout.show(panelContenido, "AGREGAR_MANTENIMIENTO");
        });

        panelMenu.btnVerHistorial.addActionListener(e -> {
            panelDashboard.actualizarTabla(controlador.obtenerTodosLosVehiculos());
            cardLayout.show(panelContenido, "DASHBOARD");
        });

        // Boton para regresar al Dashboard desde el panel de mantenimiento
        panelAgregarMantenimiento.btnVolver.addActionListener(e -> cardLayout.show(panelContenido, "DASHBOARD"));

        // --- Acciones del CRUD ---
        panelRegistro.btnGuardar.addActionListener(e -> guardarNuevoVehiculo());
        panelAgregarMantenimiento.btnGuardarMantenimiento.addActionListener(e -> guardarNuevoMantenimiento());
        panelDashboard.btnEliminarVehiculo.addActionListener(e -> eliminarVehiculoSeleccionado());
    }

    /**
     * Metodo: guardarNuevoVehiculo (CREATE)
     * Recolecta, valida y envia los datos del formulario de registro al controlador.
     */
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

        String tipoServicio = panelAgregarMantenimiento.txtTipoServicio.getText();
        if (tipoServicio.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El tipo de servicio es obligatorio.", "Campo Vacio", JOptionPane.WARNING_MESSAGE);
            return;
        }

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
            cardLayout.show(panelContenido, "DASHBOARD");
        } else {
            JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado al agregar el mantenimiento.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo: eliminarVehiculoSeleccionado (DELETE)
     * Elimina el vehiculo que este seleccionado en la tabla del dashboard.
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
            // AQUI ESTA LA CORRECCION: Asegurarse que el nombre del metodo es identico
            boolean exito = controlador.eliminarVehiculo(placa);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Vehiculo eliminado con exito.", "Eliminacion Completa", JOptionPane.INFORMATION_MESSAGE);
                panelDashboard.actualizarTabla(controlador.obtenerTodosLosVehiculos()); // Actualiza la tabla
            } else {
                // Este error teoricamente no deberia ocurrir si el vehiculo esta en la tabla
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el vehiculo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Metodo: limpiarCamposRegistro
     * Restablece los campos de texto del formulario de registro.
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
}