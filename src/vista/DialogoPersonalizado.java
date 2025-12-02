/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación III - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      DialogoPersonalizado.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Componente de UI que crea una ventana de diálogo modal personalizada.
 * Aplica Herencia al extender JDialog y Composición al anidar paneles para
 * lograr una estética consistente con el tema oscuro de la aplicación,
 * reemplazando el JOptionPane estándar.
 * ============================================================================
 */
package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Clase DialogoPersonalizado que genera una ventana modal de alerta o información.
 * PRINCIPIO POO: Herencia - Extiende de {@link JDialog} para obtener el comportamiento
 * de una ventana de diálogo (modal, bloqueante).
 * PRINCIPIO POO: Composición - Construye su interfaz anidando componentes de Swing
 * como JPanel, JLabel y JButton.
 */
public class DialogoPersonalizado extends JDialog {

    /**
     * Constructor privado para construir la interfaz del diálogo.
     * Se hace privado para fomentar su uso a través del método estático `mostrar`.
     *
     * @param owner El componente padre sobre el cual se centrará el diálogo.
     * @param titulo El texto que aparecerá en la barra de título (si la tuviera).
     * @param mensaje El texto principal del mensaje, soporta saltos de línea (\n).
     * @param tipoMensaje El tipo de ícono a mostrar (ej. JOptionPane.ERROR_MESSAGE).
     */
    private DialogoPersonalizado(Frame owner, String titulo, String mensaje, int tipoMensaje) {
        super(owner, titulo, true); // `true` lo hace modal (bloquea la ventana padre).

        // --- CONFIGURACIÓN DE LA VENTANA ---
        setUndecorated(true); // Elimina los bordes y la barra de título del sistema operativo.
        setLayout(new BorderLayout());

        // --- PANEL DE FONDO ---
        JPanel panelFondo = new JPanel(new BorderLayout(10, 10));
        panelFondo.setBackground(new Color(45, 50, 55));
        panelFondo.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));

        // --- ÍCONO (IZQUIERDA) ---
        // Se obtiene un ícono estándar de Swing basado en el tipo de mensaje.
        JLabel lblIcono = new JLabel(getIconoPorTipo(tipoMensaje));
        lblIcono.setBorder(new EmptyBorder(20, 20, 20, 10));
        panelFondo.add(lblIcono, BorderLayout.WEST);

        // --- MENSAJE DE TEXTO (CENTRO) ---
        // Se utiliza HTML simple dentro del JLabel para permitir el ajuste de línea automático.
        String mensajeHtml = "<html><div style='width: 250px;'>" + mensaje.replace("\n", "<br>") + "</div></html>";
        JLabel lblMensaje = new JLabel(mensajeHtml);
        lblMensaje.setForeground(Color.WHITE);
        lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelFondo.add(lblMensaje, BorderLayout.CENTER);

        // --- BOTÓN DE CIERRE (ABAJO) ---
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        panelBoton.setOpaque(false); // Transparente para heredar el fondo oscuro.
        JButton btnOk = new JButton("OK");

        // Estilización del botón para que coincida con el tema.
        btnOk.setBackground(new Color(80, 85, 90));
        btnOk.setForeground(Color.WHITE);
        btnOk.setFocusPainted(false);
        btnOk.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(120, 125, 130)),
                BorderFactory.createEmptyBorder(5, 25, 5, 25)
        ));
        // Evento de acción: cierra el diálogo al hacer clic.
        btnOk.addActionListener(e -> dispose());
        panelBoton.add(btnOk);

        panelFondo.add(panelBoton, BorderLayout.SOUTH);
        add(panelFondo, BorderLayout.CENTER);

        pack(); // Ajusta el tamaño de la ventana al contenido.
        setLocationRelativeTo(owner); // Centra la ventana respecto a su "dueño".
    }

    /**
     * Método de fábrica estático para mostrar el diálogo.
     * Esta es la forma recomendada de invocar el diálogo desde otras clases.
     *
     * @param owner Componente padre.
     * @param titulo Título de la ventana.
     * @param mensaje Texto a mostrar.
     * @param tipoMensaje Constante de JOptionPane para el ícono.
     */
    public static void mostrar(Component owner, String titulo, String mensaje, int tipoMensaje) {
        Frame frameOwner = (owner instanceof Frame) ? (Frame) owner : (Frame) SwingUtilities.getWindowAncestor(owner);
        DialogoPersonalizado dialogo = new DialogoPersonalizado(frameOwner, titulo, mensaje, tipoMensaje);
        dialogo.setVisible(true);
    }

    /**
     * Devuelve un ícono de Swing estándar según el tipo de mensaje.
     * @param tipoMensaje Una de las constantes de JOptionPane (ERROR_MESSAGE, etc.).
     * @return El {@link Icon} correspondiente.
     */
    private Icon getIconoPorTipo(int tipoMensaje) {
        switch (tipoMensaje) {
            case JOptionPane.ERROR_MESSAGE:
                return UIManager.getIcon("OptionPane.errorIcon");
            case JOptionPane.WARNING_MESSAGE:
                return UIManager.getIcon("OptionPane.warningIcon");
            case JOptionPane.INFORMATION_MESSAGE:
            default: // Por defecto, se muestra el de información.
                return UIManager.getIcon("OptionPane.informationIcon");
        }
    }
}