/*
 * Archivo: Main.java
 * Descripcion: Punto de entrada. Inicia la capa de seguridad (Login).
 */
import vista.Login;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Estilo global oscuro para dialogos
        UIManager.put("OptionPane.background", new Color(45, 50, 55));
        UIManager.put("Panel.background", new Color(45, 50, 55));
        UIManager.put("OptionPane.messageForeground", Color.WHITE);

        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.setVisible(true);
        });
    }
}