/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: VentanaPrincipal.java
 * Descripcion: Clase principal de la Vista (JFrame).
 * Fecha: Noviembre 2025
 * Version: 11.0 (Version final estable con estilos globales)
 * -----------------------------------------------------------------------------
 */
package vista;

import controlador.ControladorSIRMA;
import controlador.ControladorSIRMA.VehiculoYMantenimiento;
import modelo.Mecanico;
import modelo.Mantenimiento;
import modelo.Propietario;
import modelo.Vehiculo;
import persistencia.GestionReportes;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class VentanaPrincipal extends JFrame {

    private final ControladorSIRMA controlador;
    private final CardLayout cardLayout;
    private final JPanel panelContenido;
    private final PanelMenuLateral panelMenu;
    private final PanelBienvenida panelBienvenida;
    private final PanelGestionVehiculos panelGestionVehiculos;
    private final PanelDashboard panelDashboard;
    private final PanelGestionMantenimientos panelFormularioMantenimiento;
    private final PanelReportes panelReportes;
    private final PanelGestionMecanicos panelGestionMecanicos;
    private final PanelBusquedaInteligente panelBusquedaInteligente;

    public VentanaPrincipal(ControladorSIRMA controlador) {
        this.controlador = controlador;
        setTitle("SIRMA JG - Sistema Inteligente de Registro de Mantenimiento Automotriz");
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        panelMenu = new PanelMenuLateral();
        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);
        panelBienvenida = new PanelBienvenida();
        panelGestionVehiculos = new PanelGestionVehiculos();
        panelDashboard = new PanelDashboard();
        panelFormularioMantenimiento = new PanelGestionMantenimientos();
        panelReportes = new PanelReportes();
        panelGestionMecanicos = new PanelGestionMecanicos();
        panelBusquedaInteligente = new PanelBusquedaInteligente();

        panelContenido.add(panelBienvenida, "BIENVENIDA");
        panelContenido.add(panelGestionVehiculos, "VEHICULOS");
        panelContenido.add(panelDashboard, "DASHBOARD");
        panelContenido.add(panelFormularioMantenimiento, "FORMULARIO_MANTENIMIENTO");
        panelContenido.add(panelReportes, "REPORTES");
        panelContenido.add(panelGestionMecanicos, "MECANICOS");
        panelContenido.add(panelBusquedaInteligente, "BUSQUEDA");

        add(panelMenu, BorderLayout.WEST);
        add(panelContenido, BorderLayout.CENTER);
        cardLayout.show(panelContenido, "BIENVENIDA");

        conectarAcciones();
    }

    private void conectarAcciones() {
        panelFormularioMantenimiento.btnLimpiar.addActionListener(e -> panelFormularioMantenimiento.prepararParaNuevaOrden(controlador.obtenerTodosLosVehiculos()));
        panelMenu.btnInicio.addActionListener(e -> cardLayout.show(panelContenido, "BIENVENIDA"));
        panelMenu.btnGestionVehiculos.addActionListener(e -> irAGestionVehiculos());
        panelMenu.btnOrdenesServicio.addActionListener(e -> irAlDashboard());
        panelMenu.btnBusquedaInteligente.addActionListener(e -> irABusquedaInteligente());
        panelMenu.btnGestionMecanicos.addActionListener(e -> irAGestionMecanicos());
        panelMenu.btnCentroReportes.addActionListener(e -> irACentroReportes());
        panelMenu.btnCerrarSesion.addActionListener(e -> System.exit(0));
        panelBienvenida.btnGestionFlota.addActionListener(e -> irAGestionVehiculos());
        panelBienvenida.btnGestionServicios.addActionListener(e -> irAlDashboard());
        panelBienvenida.btnReportesCierre.addActionListener(e -> irACentroReportes());
        panelGestionVehiculos.btnGuardar.addActionListener(e -> guardarOActualizarVehiculo());
        panelGestionVehiculos.btnLimpiar.addActionListener(e -> panelGestionVehiculos.limpiarFormulario());
        panelGestionVehiculos.btnEliminar.addActionListener(e -> eliminarVehiculoSeleccionado());
        panelGestionVehiculos.tablaVehiculos.getSelectionModel().addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) { cargarVehiculoSeleccionadoEnFormulario(); panelGestionVehiculos.btnEliminar.setEnabled(panelGestionVehiculos.tablaVehiculos.getSelectedRow() != -1); } });
        panelDashboard.tablaOrdenes.getSelectionModel().addListSelectionListener(e -> { boolean sel = panelDashboard.tablaOrdenes.getSelectedRow() != -1; panelDashboard.btnEditarOrden.setEnabled(sel); panelDashboard.btnEliminarOrden.setEnabled(sel); });
        panelDashboard.btnAgregarOrden.addActionListener(e -> { panelFormularioMantenimiento.prepararParaNuevaOrden(controlador.obtenerTodosLosVehiculos()); cardLayout.show(panelContenido, "FORMULARIO_MANTENIMIENTO"); });
        panelDashboard.btnEditarOrden.addActionListener(e -> editarOrdenSeleccionadaDesdeDashboard());
        panelDashboard.btnEliminarOrden.addActionListener(e -> eliminarOrdenSeleccionada());
        panelFormularioMantenimiento.btnVolver.addActionListener(e -> irAlDashboard());
        panelFormularioMantenimiento.btnGuardar.addActionListener(e -> guardarOActualizarMantenimiento());
        panelFormularioMantenimiento.btnImprimirSolicitud.addActionListener(e -> imprimirSolicitudDeServicio());
        panelFormularioMantenimiento.comboServicios.addActionListener(e -> { String tipo = (String) panelFormularioMantenimiento.comboServicios.getSelectedItem(); String mec = controlador.asignarMecanicoInteligente(tipo); panelFormularioMantenimiento.campoMecanicoAsignado.setText(mec); });
        panelGestionMecanicos.btnGuardar.addActionListener(e -> guardarOActualizarMecanico());
        panelGestionMecanicos.btnLimpiar.addActionListener(e -> panelGestionMecanicos.limpiarFormulario());
        panelGestionMecanicos.btnEliminar.addActionListener(e -> eliminarMecanicoSeleccionado());
        panelGestionMecanicos.tablaMecanicos.getSelectionModel().addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) { boolean sel = panelGestionMecanicos.tablaMecanicos.getSelectedRow() != -1; panelGestionMecanicos.btnEliminar.setEnabled(sel); if (sel) { cargarMecanicoEnFormulario(); } else { panelGestionMecanicos.limpiarFormulario(); } } });
        panelBusquedaInteligente.btnRealizarBusqueda.addActionListener(e -> realizarBusquedaInteligente());
        panelBusquedaInteligente.btnLimpiarBusqueda.addActionListener(e -> limpiarFiltrosBusqueda());
        panelReportes.btnGenerarHTML.addActionListener(e -> generarYAbriReporte("html"));
        panelReportes.btnGenerarTXT.addActionListener(e -> generarYAbriReporte("txt"));
        panelReportes.btnAbrirCarpeta.addActionListener(e -> abrirCarpetaReportes());
        panelReportes.btnVolverInicio.addActionListener(e -> cardLayout.show(panelContenido, "BIENVENIDA"));
    }

    public void establecerUsuarioLogueado(String n) { panelMenu.setNombreUsuario(n); }
    private void guardarOActualizarMantenimiento() { String p = panelFormularioMantenimiento.getPlacaSeleccionada(); if (p == null && panelFormularioMantenimiento.getMantenimientoEnEdicion() == null) { JOptionPane.showMessageDialog(panelContenido, "Debe seleccionar un vehículo.", "Dato Requerido", JOptionPane.WARNING_MESSAGE); return; } String ts = (String) panelFormularioMantenimiento.comboServicios.getSelectedItem(); if (ts == null || ts.contains("---")) { JOptionPane.showMessageDialog(panelContenido, "Debe seleccionar un tipo de servicio.", "Dato Requerido", JOptionPane.WARNING_MESSAGE); return; } int km; double mo, cr; try { km = Integer.parseInt(panelFormularioMantenimiento.campoKilometraje.getText().trim()); mo = Double.parseDouble(panelFormularioMantenimiento.campoCostoManoObra.getText().trim().replace(",",".")); cr = Double.parseDouble(panelFormularioMantenimiento.campoCostoRepuestos.getText().trim().replace(",",".")); } catch (NumberFormatException e) { JOptionPane.showMessageDialog(panelContenido, "Kilometraje y Costos deben ser números.", "Error de Formato", JOptionPane.ERROR_MESSAGE); return; } String d = panelFormularioMantenimiento.areaDescripcion.getText().trim(); String e = (String) panelFormularioMantenimiento.comboEstado.getSelectedItem(); boolean exito; String msj; if (panelFormularioMantenimiento.getMantenimientoEnEdicion() == null) { Mantenimiento n = new Mantenimiento(ts, d, mo, cr, km); n.setEstado(e); Mantenimiento guardada = controlador.agregarMantenimientoAVehiculo(p, n); exito = guardada != null; msj = exito ? "Orden registrada exitosamente.\nAhora puede imprimir la solicitud." : "Error al registrar la orden."; if(exito) { panelFormularioMantenimiento.prepararParaEditarOrden(guardada, controlador.buscarVehiculoPorPlaca(p).get()); } } else { Mantenimiento act = panelFormularioMantenimiento.getMantenimientoEnEdicion(); act.setTipoServicio(ts); act.setDescripcionDetallada(d); act.setCostoManoObra(mo); act.setCostoRepuestos(cr); act.setKilometrajeActual(km); act.setEstado(e); exito = controlador.actualizarMantenimiento(act); msj = exito ? "Orden actualizada exitosamente." : "Error al actualizar la orden."; } JOptionPane.showMessageDialog(panelContenido, msj, "Gestión de Órdenes", exito ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE); if (exito) { panelFormularioMantenimiento.btnImprimirSolicitud.setEnabled(true); actualizarTablaDeOrdenes(controlador.obtenerTodasLasOrdenes()); } }
    private void imprimirSolicitudDeServicio() { Mantenimiento m = panelFormularioMantenimiento.getMantenimientoEnEdicion(); if (m == null) { JOptionPane.showMessageDialog(panelContenido, "Guarde la orden primero.", "Advertencia", JOptionPane.WARNING_MESSAGE); return; } Optional<VehiculoYMantenimiento> r = controlador.buscarVehiculoPorIdOrden(m.getIdOrden()); if (r.isPresent()) { DialogoSolicitudServicio d = new DialogoSolicitudServicio(this, r.get().mantenimiento, r.get().vehiculo); d.setLocationRelativeTo(panelContenido); d.setVisible(true); } else { JOptionPane.showMessageDialog(panelContenido, "Error: No se encontró el vehículo asociado.", "Error Interno", JOptionPane.ERROR_MESSAGE); } }
    private void irAlDashboard() { actualizarTablaDeOrdenes(controlador.obtenerTodasLasOrdenes()); panelBusquedaInteligente.limpiarFiltros(); cardLayout.show(panelContenido, "DASHBOARD"); }
    private void irAGestionVehiculos() { actualizarTablaDeVehiculos(); panelGestionVehiculos.limpiarFormulario(); cardLayout.show(panelContenido, "VEHICULOS"); }
    private void irAGestionMecanicos() { actualizarTablaMecanicos(); panelGestionMecanicos.limpiarFormulario(); cardLayout.show(panelContenido, "MECANICOS"); }
    private void irABusquedaInteligente() { panelBusquedaInteligente.limpiarFiltros(); cardLayout.show(panelContenido, "BUSQUEDA"); }
    private void irACentroReportes() { panelReportes.setMensajeEstado(" ", false); cardLayout.show(panelContenido, "REPORTES"); }
    private void actualizarTablaDeVehiculos() { panelGestionVehiculos.modeloTabla.setRowCount(0); for (Vehiculo v : controlador.obtenerTodosLosVehiculos()) { panelGestionVehiculos.modeloTabla.addRow(new Object[]{v.getPlaca(), v.getMarca(), v.getModelo(), v.getAnio(), v.getPropietario().getNombreCompleto()}); } panelGestionVehiculos.tablaVehiculos.clearSelection(); panelGestionVehiculos.btnEliminar.setEnabled(false); }
    private void actualizarTablaDeOrdenes(List<Mantenimiento> lista) { panelDashboard.modeloTabla.setRowCount(0); for (Mantenimiento m : lista) { controlador.buscarVehiculoPorIdOrden(m.getIdOrden()).ifPresent(r -> panelDashboard.modeloTabla.addRow(new Object[]{m.getIdOrden(), r.vehiculo.getPlaca(), r.vehiculo.getMarca(), m.getTipoServicio(), m.getFechaRealizacion(), m.getEstado(), r.vehiculo.getPropietario().getCedula(), "$" + String.format("%.2f", m.getCostoTotal())})); } panelDashboard.tablaOrdenes.clearSelection(); panelDashboard.btnEditarOrden.setEnabled(false); panelDashboard.btnEliminarOrden.setEnabled(false); }
    private void actualizarTablaMecanicos() { panelGestionMecanicos.modeloTabla.setRowCount(0); for (Mecanico m : controlador.obtenerTodosLosMecanicos()) { panelGestionMecanicos.modeloTabla.addRow(new Object[]{m.getNombreCompleto(), m.getEspecialidad()}); } panelGestionMecanicos.tablaMecanicos.clearSelection(); panelGestionMecanicos.btnEliminar.setEnabled(false); }
    private void guardarOActualizarVehiculo() { String p = panelGestionVehiculos.txtPlaca.getText().trim(), m = panelGestionVehiculos.txtMarca.getText().trim(), mo = panelGestionVehiculos.txtModelo.getText().trim(), a = panelGestionVehiculos.txtAnio.getText().trim(), n = panelGestionVehiculos.txtNombrePropietario.getText().trim(), c = panelGestionVehiculos.txtCedulaPropietario.getText().trim(); if (p.isEmpty() || m.isEmpty() || mo.isEmpty() || a.isEmpty() || n.isEmpty() || c.isEmpty()) { JOptionPane.showMessageDialog(panelContenido, "Complete todos los campos.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE); return; } int anio; try { anio = Integer.parseInt(a); } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(panelContenido, "Año debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE); return; } Propietario prop = new Propietario(n, c, panelGestionVehiculos.txtTelefonoPropietario.getText().trim()); Vehiculo v = new Vehiculo(p, m, mo, anio, panelGestionVehiculos.txtColor.getText().trim(), prop); boolean exito; String msj; if (panelGestionVehiculos.vehiculoEnEdicion == null) { exito = controlador.registrarVehiculo(v); msj = exito ? "Vehículo registrado." : "Error: Placa ya existe."; } else { exito = controlador.actualizarVehiculo(panelGestionVehiculos.vehiculoEnEdicion.getPlaca(), v); msj = exito ? "Vehículo actualizado." : "Error al actualizar."; } JOptionPane.showMessageDialog(panelContenido, msj, "Gestión de Vehículos", exito ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE); if (exito) { panelGestionVehiculos.limpiarFormulario(); actualizarTablaDeVehiculos(); } }
    private void cargarVehiculoSeleccionadoEnFormulario() { int f = panelGestionVehiculos.tablaVehiculos.getSelectedRow(); if (f != -1) { String p = (String) panelGestionVehiculos.modeloTabla.getValueAt(f, 0); controlador.buscarVehiculoPorPlaca(p).ifPresent(v -> { panelGestionVehiculos.vehiculoEnEdicion = v; panelGestionVehiculos.txtPlaca.setText(v.getPlaca()); panelGestionVehiculos.txtPlaca.setEditable(false); panelGestionVehiculos.txtMarca.setText(v.getMarca()); panelGestionVehiculos.txtModelo.setText(v.getModelo()); panelGestionVehiculos.txtAnio.setText(String.valueOf(v.getAnio())); panelGestionVehiculos.txtColor.setText(v.getColor()); panelGestionVehiculos.txtNombrePropietario.setText(v.getPropietario().getNombreCompleto()); panelGestionVehiculos.txtCedulaPropietario.setText(v.getPropietario().getCedula()); panelGestionVehiculos.txtTelefonoPropietario.setText(v.getPropietario().getTelefono()); panelGestionVehiculos.btnGuardar.setText("Actualizar"); }); } }
    private void eliminarVehiculoSeleccionado() { int f = panelGestionVehiculos.tablaVehiculos.getSelectedRow(); if (f != -1) { String p = (String) panelGestionVehiculos.modeloTabla.getValueAt(f, 0); if (JOptionPane.showConfirmDialog(panelContenido, "¿Eliminar vehículo " + p + "?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) { if (controlador.eliminarVehiculo(p)) { JOptionPane.showMessageDialog(panelContenido, "Vehículo eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE); panelGestionVehiculos.limpiarFormulario(); actualizarTablaDeVehiculos(); } else { JOptionPane.showMessageDialog(panelContenido, "Error al eliminar.", "Error", JOptionPane.ERROR_MESSAGE); } } } }
    private void editarOrdenSeleccionadaDesdeDashboard() { int f = panelDashboard.tablaOrdenes.getSelectedRow(); if (f != -1) { String id = (String) panelDashboard.modeloTabla.getValueAt(f, 0); controlador.buscarVehiculoPorIdOrden(id).ifPresent(r -> { panelFormularioMantenimiento.prepararParaEditarOrden(r.mantenimiento, r.vehiculo); cardLayout.show(panelContenido, "FORMULARIO_MANTENIMIENTO"); }); } }
    private void eliminarOrdenSeleccionada() { int f = panelDashboard.tablaOrdenes.getSelectedRow(); if (f != -1) { String id = (String) panelDashboard.modeloTabla.getValueAt(f, 0); if (JOptionPane.showConfirmDialog(panelContenido, "¿Eliminar orden " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) { if (controlador.eliminarMantenimiento(id)) { JOptionPane.showMessageDialog(panelContenido, "Orden eliminada.", "Éxito", JOptionPane.INFORMATION_MESSAGE); actualizarTablaDeOrdenes(controlador.obtenerTodasLasOrdenes()); } else { JOptionPane.showMessageDialog(panelContenido, "Error al eliminar.", "Error", JOptionPane.ERROR_MESSAGE); } } } }
    private void guardarOActualizarMecanico() { String n = panelGestionMecanicos.txtNombre.getText().trim(), e = panelGestionMecanicos.txtEspecialidad.getText().trim(); if (n.isEmpty() || e.isEmpty()) { JOptionPane.showMessageDialog(panelContenido, "Nombre y especialidad son obligatorios.", "Incompletos", JOptionPane.WARNING_MESSAGE); return; } Mecanico m = new Mecanico(n, e); boolean exito; String msj; if (panelGestionMecanicos.mecanicoEnEdicion == null) { exito = controlador.agregarMecanico(m); msj = exito ? "Mecánico registrado." : "Error: El nombre ya existe."; } else { exito = controlador.actualizarMecanico(panelGestionMecanicos.mecanicoEnEdicion.getNombreCompleto(), m); msj = exito ? "Mecánico actualizado." : "Error al actualizar."; } JOptionPane.showMessageDialog(panelContenido, msj, "Gestión de Mecánicos", exito ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE); if (exito) { panelGestionMecanicos.limpiarFormulario(); actualizarTablaMecanicos(); } }
    private void cargarMecanicoEnFormulario() { int f = panelGestionMecanicos.tablaMecanicos.getSelectedRow(); if (f != -1) { String n = (String) panelGestionMecanicos.modeloTabla.getValueAt(f, 0), e = (String) panelGestionMecanicos.modeloTabla.getValueAt(f, 1); Mecanico m = new Mecanico(n, e); panelGestionMecanicos.mecanicoEnEdicion = m; panelGestionMecanicos.txtNombre.setText(m.getNombreCompleto()); panelGestionMecanicos.txtNombre.setEditable(false); panelGestionMecanicos.txtEspecialidad.setText(m.getEspecialidad()); panelGestionMecanicos.btnGuardar.setText("Actualizar"); } }
    private void eliminarMecanicoSeleccionado() { int f = panelGestionMecanicos.tablaMecanicos.getSelectedRow(); if (f != -1) { String n = (String) panelGestionMecanicos.modeloTabla.getValueAt(f, 0); if (JOptionPane.showConfirmDialog(panelContenido, "¿Eliminar a " + n + "?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) { if (controlador.eliminarMecanico(n)) { JOptionPane.showMessageDialog(panelContenido, "Mecánico eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE); panelGestionMecanicos.limpiarFormulario(); actualizarTablaMecanicos(); } else { JOptionPane.showMessageDialog(panelContenido, "Error al eliminar.", "Error", JOptionPane.ERROR_MESSAGE); } } } }
    private void realizarBusquedaInteligente() { String t = panelBusquedaInteligente.txtBusquedaGeneral.getText().trim(), e = (String) panelBusquedaInteligente.comboEstado.getSelectedItem(), d = panelBusquedaInteligente.txtFechaDesde.getText().trim(), h = panelBusquedaInteligente.txtFechaHasta.getText().trim(); try { DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-yyyy"); if (!d.isEmpty()) LocalDate.parse(d, f); if (!h.isEmpty()) LocalDate.parse(h, f); } catch (java.time.format.DateTimeParseException ex) { JOptionPane.showMessageDialog(panelContenido, "Formato de fecha debe ser DD-MM-AAAA.", "Error", JOptionPane.ERROR_MESSAGE); return; } List<Mantenimiento> res = controlador.buscarOrdenesConFiltros(t, e, d, h); actualizarTablaDeOrdenes(res); cardLayout.show(panelContenido, "DASHBOARD"); }
    private void limpiarFiltrosBusqueda() { panelBusquedaInteligente.limpiarFiltros(); actualizarTablaDeOrdenes(controlador.obtenerTodasLasOrdenes()); }
    private void generarYAbriReporte(String tipo) { GestionReportes gr = new GestionReportes(); String n = null; if ("html".equalsIgnoreCase(tipo)) { n = gr.generarReporteHtml(controlador.obtenerTodosLosVehiculos()); } else if ("txt".equalsIgnoreCase(tipo)) { n = gr.generarReporteTxt(controlador.obtenerTodosLosVehiculos()); } if (n != null) { panelReportes.setMensajeEstado("Reporte '" + n + "' generado.", false); if (JOptionPane.showConfirmDialog(panelContenido, "Reporte generado.\n¿Desea abrirlo?", "Éxito", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) { abrirArchivo(n); } } else { panelReportes.setMensajeEstado("Error al generar reporte.", true); JOptionPane.showMessageDialog(panelContenido, "Error al generar reporte.", "Error", JOptionPane.ERROR_MESSAGE); } }
    private void abrirArchivo(String n) { try { Desktop.getDesktop().open(new File(n)); } catch (IOException ex) { JOptionPane.showMessageDialog(panelContenido, "No se pudo abrir el archivo.", "Error", JOptionPane.ERROR_MESSAGE); } }
    private void abrirCarpetaReportes() { try { Desktop.getDesktop().open(new File(".")); } catch (IOException ex) { JOptionPane.showMessageDialog(panelContenido, "No se pudo abrir la carpeta.", "Error", JOptionPane.ERROR_MESSAGE); } }
}