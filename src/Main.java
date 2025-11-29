/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: Main.java
 * Descripcion: Punto de entrada del sistema SIRMA JG.
 *              Configura el diseño visual (Look & Feel) global de la aplicacion
 *              para asegurar una apariencia consistente en modo oscuro.
 * Fecha: Noviembre 2025
 * Version: 5.0 (Implementacion final de estilos globales)
 * -----------------------------------------------------------------------------
 */
import vista.Login;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        // --- 1. DEFINICION DE LA PALETA DE COLORES ---
        Color colorFondoOscuro = new Color(45, 50, 55);   // Gris plomo (Fondo general)
        Color colorBotonOscuro = new Color(80, 85, 90);   // Gris para botones de dialogo
        Color colorTextoBlanco = Color.WHITE;             // Letra para fondo oscuro

        Color colorCampoBlanco = Color.WHITE;             // Fondo para campos de texto
        Color colorTextoNegro  = Color.BLACK;             // Letra para campos de texto

        // --- 2. APLICACION DE ESTILOS GLOBALES (UIManager) ---

        // A. Paneles y Ventanas de Dialogo (Modo Oscuro)
        UIManager.put("Panel.background", colorFondoOscuro);
        UIManager.put("OptionPane.background", colorFondoOscuro);
        UIManager.put("OptionPane.messageForeground", colorTextoBlanco); // Letra de los mensajes en dialogos

        // B. Botones de las Alertas (JOptionPane)
        // Esto arregla que el boton "OK", "Cerrar", etc., se vea bien.
        UIManager.put("Button.background", colorBotonOscuro);
        UIManager.put("Button.foreground", colorTextoBlanco);
        UIManager.put("Button.select", new Color(100, 105, 110)); // Color al presionar
        UIManager.put("Button.focus", new Color(0,0,0,0)); // Elimina el borde de foco
        UIManager.put("Button.border", BorderFactory.createLineBorder(new Color(120, 125, 130)));

        // C. Campos de Texto Normales (JTextField) - SOLUCION AL PROBLEMA DE LEGIBILIDAD
        UIManager.put("TextField.background", colorCampoBlanco);     // Fondo Blanco
        UIManager.put("TextField.foreground", colorTextoNegro);      // Letra Negra
        UIManager.put("TextField.caretForeground", colorTextoNegro); // Cursor (Barrita) Negro

        // D. Campos de Contraseña (JPasswordField)
        UIManager.put("PasswordField.background", colorCampoBlanco);
        UIManager.put("PasswordField.foreground", colorTextoNegro);
        UIManager.put("PasswordField.caretForeground", colorTextoNegro);

        // --- 3. INICIO DE LA APLICACION ---
        // Se invoca en el hilo de despacho de eventos de Swing, la forma segura de iniciar GUIs.
        SwingUtilities.invokeLater(() -> {
            // Instanciamos y mostramos la ventana de Login
            Login login = new Login();
            login.setVisible(true);
        });
    }
}