/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Descripcion: Organiza los paneles y gestiona la navegacion e interaccion
 *              entre la vista y el controlador.
 * Fecha: Noviembre 2025
 * -----------------------------------------------------------------------------
 */
package vista;

import controlador.ControladorSIRMA;
import modelo.Propietario;
import modelo.Vehiculo;
import javax.swing.*;
import java.awt.*;
import java.util.Optional;

/**
 * Clase VentanaPrincipal
 * Contenedor principal (JFrame) de la interfaz grafica.
 */
public class VentanaPrincipal extends JFrame {

    private CardLayout cardLayout;
    private PanelMenuLateral panelMenu;
    private PanelContenidoPrincipal panelContenido;
    private PanelDashboard panelDashboard;
    private PanelRegistroVehiculo panelRegistro;
    private ControladorSIRMA controlador;

    /**
     * Constructor de la ventana.
     * @param controlador La instancia del controlador principal de la aplicacion.
     */
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
        panelContenido = new PanelContenidoPrincipal();
        panelContenido.setLayout(cardLayout);

        panelDashboard = new PanelDashboard();
        panelRegistro = new PanelRegistroVehiculo();

        panelContenido.add(panelDashboard, "DASHBOARD");
        panelContenido.add(panelRegistro, "REGISTRO");

        add(panelMenu, BorderLayout.WEST);
        add(panelContenido, BorderLayout.CENTER);

        panelDashboard.actualizarTabla(controlador.obtenerTodosLosVehiculos());

        // --- Vinculacion de Eventos ---
        conectarAcciones();
    }

    /**
     * Metodo: conectarAcciones
     * Asigna los 'ActionListeners' a los botones para manejar los eventos del usuario.
     */
    private void conectarAcciones() {
        panelMenu.btnRegistrarVehiculo.addActionListener(e -> {
            cardLayout.show(panelContenido, "REGISTRO");
        });

        panelMenu.btnBuscarVehiculo.addActionListener(e -> {
            buscarVehiculo();
        });

        panelMenu.btnVerHistorial.addActionListener(e -> {
            panelDashboard.actualizarTabla(controlador.obtenerTodosLosVehiculos());
            cardLayout.show(panelContenido, "DASHBOARD");
        });

        panelRegistro.btnGuardar.addActionListener(e -> {
            guardarNuevoVehiculo();
        });
    }

    /**
     * Metodo: guardarNuevoVehiculo
     * Recolecta datos del formulario, los valida y llama al controlador para registrar.
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

        Propietario nuevoPropietario = new Propietario(
                panelRegistro.txtNombrePropietario.getText(),
                panelRegistro.txtCedulaPropietario.getText(),
                panelRegistro.txtTelefonoPropietario.getText()
        );

        Vehiculo nuevoVehiculo = new Vehiculo(
                placa, marca, panelRegistro.txtModelo.getText(), anio,
                panelRegistro.txtColor.getText(), nuevoPropietario
        );

        boolean exito = controlador.registrarVehiculo(nuevoVehiculo);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Vehiculo registrado con exito", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            panelDashboard.actualizarTabla(controlador.obtenerTodosLosVehiculos());
            cardLayout.show(panelContenido, "DASHBOARD");
            limpiarCamposRegistro();
        } else {
            JOptionPane.showMessageDialog(this, "Error: La placa '" + placa.toUpperCase() + "' ya existe.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo: buscarVehiculo
     * Solicita una placa al usuario y muestra la informacion del vehiculo si existe.
     */
    private void buscarVehiculo() {
        String placa = JOptionPane.showInputDialog(this, "Ingrese la placa del vehiculo a buscar:", "Busqueda de Vehiculo", JOptionPane.QUESTION_MESSAGE);

        if (placa != null && !placa.trim().isEmpty()) {
            Optional<Vehiculo> vehiculoEncontrado = controlador.buscarVehiculoPorPlaca(placa.trim());

            if (vehiculoEncontrado.isPresent()) {
                Vehiculo v = vehiculoEncontrado.get();
                StringBuilder detalles = new StringBuilder();
                detalles.append("--- Datos del Vehiculo ---\n");
                detalles.append("Placa: ").append(v.getPlaca().toUpperCase()).append("\n");
                detalles.append("Marca: ").append(v.getMarca()).append("\n");
                detalles.append("Modelo: ").append(v.getModelo()).append("\n");
                detalles.append("Anio: ").append(v.getAnio()).append("\n\n");
                detalles.append("--- Datos del Propietario ---\n");
                detalles.append("Nombre: ").append(v.getPropietario().getNombreCompleto()).append("\n");
                detalles.append("Cedula: ").append(v.getPropietario().getCedula()).append("\n");

                JOptionPane.showMessageDialog(this, detalles.toString(), "Vehiculo Encontrado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontro ningun vehiculo con la placa: " + placa, "Busqueda Fallida", JOptionPane.WARNING_MESSAGE);
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