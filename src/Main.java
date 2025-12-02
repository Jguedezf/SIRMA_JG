/*
 * ============================================================================
 * PROYECTO: Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA JG)
 * INSTITUCIÓN: Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA: Técnicas de Programación III - Sección 3
 * * AUTOR: Johanna Guédez
 * CÉDULA: V-14.089.807
 * DOCENTE: Ing. Dubraska Roca
 * * ARCHIVO: Main.java
 * FECHA: Diciembre 2025
 * * DESCRIPCIÓN TÉCNICA:
 * Punto de entrada (Entry Point) de la aplicación.
 * Responsabilidades:
 * 1. Configurar el "Look & Feel" global para garantizar un modo oscuro consistente.
 * 2. Iniciar el ciclo de vida de la aplicación manejando la concurrencia segura.
 * ============================================================================
 */
import vista.Login;
import javax.swing.*;
import java.awt.*;

/**
 * Clase Principal.
 * Contiene el método main y la configuración de estilos estáticos.
 */
public class Main {

    /**
     * Método principal de ejecución.
     * @param args Argumentos de línea de comandos (No utilizados en esta versión).
     */
    public static void main(String[] args) {

        // ========================================================================
        // 1. DEFINICIÓN DE LA PALETA DE COLORES (ESTRATEGIA UX/UI)
        // ========================================================================
        // Se definen constantes locales para mantener la coherencia visual en todos
        // los componentes nativos de Swing (JDialog, JOptionPane, JPanel).

        Color colorFondoOscuro = new Color(45, 50, 55);   // Gris Plomo (Base)
        Color colorBotonOscuro = new Color(80, 85, 90);   // Gris Medio (Elementos interactivos)
        Color colorTextoBlanco = Color.WHITE;             // Alto Contraste

        Color colorCampoBlanco = Color.WHITE;             // Fondo de Inputs (Ergonomía visual)
        Color colorTextoNegro  = Color.BLACK;             // Texto de Inputs

        // ========================================================================
        // 2. INYECCIÓN DE ESTILOS GLOBALES (UIManager)
        // ========================================================================
        // Se sobrescriben las propiedades por defecto de Swing antes de crear cualquier ventana.

        // A. Paneles y Ventanas de Diálogo (Modo Oscuro)
        UIManager.put("Panel.background", colorFondoOscuro);
        UIManager.put("OptionPane.background", colorFondoOscuro);
        UIManager.put("OptionPane.messageForeground", colorTextoBlanco);

        // B. Botones de Alertas (JOptionPane)
        // Personalización para eliminar el estilo "Windows 95" por defecto.
        UIManager.put("Button.background", colorBotonOscuro);
        UIManager.put("Button.foreground", colorTextoBlanco);
        UIManager.put("Button.select", new Color(100, 105, 110)); // Feedback visual (Click)
        UIManager.put("Button.focus", new Color(0,0,0,0));        // Elimina borde de foco azul
        UIManager.put("Button.border", BorderFactory.createLineBorder(new Color(120, 125, 130)));

        // C. Campos de Texto (JTextField) - CORRECCIÓN DE LEGIBILIDAD
        // Se fuerza un fondo blanco con letras negras para evitar fatiga visual en la entrada de datos.
        UIManager.put("TextField.background", colorCampoBlanco);
        UIManager.put("TextField.foreground", colorTextoNegro);
        UIManager.put("TextField.caretForeground", colorTextoNegro); // Cursor negro

        // D. Campos de Contraseña (JPasswordField)
        UIManager.put("PasswordField.background", colorCampoBlanco);
        UIManager.put("PasswordField.foreground", colorTextoNegro);
        UIManager.put("PasswordField.caretForeground", colorTextoNegro);

        // ========================================================================
        // 3. INICIO DE LA APLICACIÓN (CONCURRENCIA)
        // ========================================================================
        /*
         * [cite_start]PRINCIPIO POO: CONCURRENCIA [cite: 1030-1046]
         * Swing no es "Thread-Safe" (Seguro para hilos). Cualquier manipulación de la interfaz
         * gráfica debe realizarse dentro del "Event Dispatch Thread" (EDT).
         * * 'invokeLater' recibe un objeto Runnable (implementado aquí como Lambda)
         * y lo encola para su ejecución segura, evitando condiciones de carrera.
         */
        SwingUtilities.invokeLater(() -> {
            // Instanciación del primer objeto de la Vista (Login)
            Login login = new Login();
            login.setVisible(true);
        });
    }
}