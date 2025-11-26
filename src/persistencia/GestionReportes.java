/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion: Modulo encargado de la generacion de salidas del sistema.
 *              Soporta formatos HTML (Visual) y TXT (Texto Plano).
 * Fecha: Noviembre 2025
 * -----------------------------------------------------------------------------
 */
package persistencia;

import modelo.Vehiculo;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

/**
 * Clase GestionReportes
 * Facilita la exportacion de datos a archivos externos.
 */
public class GestionReportes {

    /**
     * Metodo: generarReporteHtml
     * Crea un archivo HTML con tablas y estilos para visualizar en navegador.
     * @param listaVehiculos Datos a exportar.
     * @return String con el nombre del archivo generado.
     */
    public String generarReporteHtml(List<Vehiculo> listaVehiculos) {
        String nombreArchivo = "Reporte_SIRMA_" + LocalDate.now() + ".html";

        try (FileWriter fw = new FileWriter(nombreArchivo); PrintWriter pw = new PrintWriter(fw)) {
            pw.println("<html><head><title>Reporte SIRMA JG</title>");
            pw.println("<style>");
            pw.println("body { font-family: sans-serif; background-color: #f4f4f4; }");
            pw.println("table { width: 100%; border-collapse: collapse; background: white; }");
            pw.println("th, td { padding: 8px; border: 1px solid #ddd; text-align: left; }");
            pw.println("th { background-color: #2c3e50; color: white; }");
            pw.println("h1 { color: #2c3e50; text-align: center; }");
            pw.println("</style></head><body>");

            pw.println("<h1>Reporte General de Mantenimientos - SIRMA JG</h1>");
            pw.println("<p align='center'>Fecha de corte: " + LocalDate.now() + "</p>");

            pw.println("<table>");
            pw.println("<tr><th>Placa</th><th>Marca</th><th>Modelo</th><th>Propietario</th><th>Total Servicios</th></tr>");

            for (Vehiculo v : listaVehiculos) {
                pw.println("<tr>");
                pw.println("<td>" + v.getPlaca() + "</td>");
                pw.println("<td>" + v.getMarca() + "</td>");
                pw.println("<td>" + v.getModelo() + "</td>");
                pw.println("<td>" + v.getPropietario().getNombreCompleto() + "</td>");
                pw.println("<td>" + v.getHistorialMantenimientos().size() + "</td>");
                pw.println("</tr>");
            }
            pw.println("</table></body></html>");
            return nombreArchivo;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Metodo: generarReporteTxt
     * Crea un archivo de texto plano ideal para registros simples o impresion rapida.
     * @param listaVehiculos Datos a exportar.
     * @return String con el nombre del archivo generado.
     */
    public String generarReporteTxt(List<Vehiculo> listaVehiculos) {
        String nombreArchivo = "Reporte_SIRMA_" + LocalDate.now() + ".txt";

        try (FileWriter fw = new FileWriter(nombreArchivo); PrintWriter pw = new PrintWriter(fw)) {
            pw.println("====================================================");
            pw.println("      SISTEMA DE REGISTRO DE MANTENIMIENTO (SIRMA)");
            pw.println("             REPORTE GENERAL DE FLOTA");
            pw.println("====================================================");
            pw.println("Fecha: " + LocalDate.now());
            pw.println("Total Vehiculos: " + listaVehiculos.size());
            pw.println("====================================================\n");

            for (Vehiculo v : listaVehiculos) {
                pw.println("VEHICULO: " + v.getPlaca() + " - " + v.getMarca() + " " + v.getModelo());
                pw.println("PROPIETARIO: " + v.getPropietario().getNombreCompleto());
                pw.println("SERVICIOS REGISTRADOS: " + v.getHistorialMantenimientos().size());
                pw.println("----------------------------------------------------");
            }
            pw.println("\nFin del reporte.");
            return nombreArchivo;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}