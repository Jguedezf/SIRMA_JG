/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: GestionReportes.java
 * Descripcion: Modulo encargado de la generacion de salidas del sistema.
 *              Soporta formatos HTML (Visual) y TXT (Texto Plano) para
 *              presentar la informacion de la flota y sus servicios.
 * Fecha: Noviembre 2025
 * Version: 2.0
 * -----------------------------------------------------------------------------
 */
package persistencia;

import modelo.Mantenimiento;
import modelo.Vehiculo;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

public class GestionReportes {

    /**
     * Metodo: generarReporteHtml
     * Crea un archivo HTML con tablas y estilos para una visualizacion clara
     * y profesional de los datos en cualquier navegador web.
     * @param listaVehiculos Datos completos de la flota a exportar.
     * @return String con el nombre del archivo generado o null si falla.
     */
    public String generarReporteHtml(List<Vehiculo> listaVehiculos) {
        String nombreArchivo = "Reporte_SIRMA_" + LocalDate.now() + ".html";

        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
            // --- Encabezado y Estilos CSS ---
            pw.println("<!DOCTYPE html><html lang='es'><head><title>Reporte SIRMA JG</title>");
            pw.println("<style>");
            pw.println("body { font-family: 'Segoe UI', sans-serif; background-color: #2c3e50; color: #ecf0f1; margin: 20px; }");
            pw.println(".container { max-width: 900px; margin: auto; background-color: #34495e; padding: 20px; border-radius: 8px; }");
            pw.println("h1, h2 { color: #1abc9c; text-align: center; border-bottom: 2px solid #16a085; padding-bottom: 10px; }");
            pw.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
            pw.println("th, td { padding: 12px; border: 1px solid #2c3e50; text-align: left; }");
            pw.println("th { background-color: #16a085; color: white; }");
            pw.println("tr:nth-child(even) { background-color: #2c3e50; }");
            pw.println("p { text-align: center; font-style: italic; }");
            pw.println("</style></head><body><div class='container'>");

            // --- Contenido del Reporte ---
            pw.println("<h1>Reporte General de Mantenimientos - SIRMA JG</h1>");
            pw.println("<p>Fecha de generacion: " + LocalDate.now() + "</p>");

            for (Vehiculo vehiculo : listaVehiculos) {
                pw.println("<h2>Vehículo: " + vehiculo.getPlaca() + " (" + vehiculo.getMarca() + " " + vehiculo.getModelo() + ")</h2>");
                pw.println("<table>");
                pw.println("<tr><th>ID Orden</th><th>Fecha</th><th>Servicio</th><th>Estado</th><th>Total ($)</th></tr>");
                for (Mantenimiento mantenimiento : vehiculo.getHistorialMantenimientos()) {
                    pw.println("<tr>");
                    pw.println("<td>" + mantenimiento.getIdOrden() + "</td>");
                    pw.println("<td>" + mantenimiento.getFechaRealizacion() + "</td>");
                    pw.println("<td>" + mantenimiento.getTipoServicio() + "</td>");
                    pw.println("<td>" + mantenimiento.getEstado() + "</td>");
                    pw.println("<td>" + String.format("%.2f", mantenimiento.getCostoTotal()) + "</td>");
                    pw.println("</tr>");
                }
                pw.println("</table>");
            }
            pw.println("</div></body></html>");
            return nombreArchivo;
        } catch (IOException e) {
            System.err.println("Error al generar el reporte HTML: " + e.getMessage());
            return null;
        }
    }

    /**
     * Metodo: generarReporteTxt
     * Crea un archivo de texto plano, ideal para registros simples, impresion
     * rapida o para sistemas que no soportan HTML.
     * @param listaVehiculos Datos de la flota a exportar.
     * @return String con el nombre del archivo generado o null si falla.
     */
    public String generarReporteTxt(List<Vehiculo> listaVehiculos) {
        String nombreArchivo = "Reporte_SIRMA_" + LocalDate.now() + ".txt";

        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
            pw.println("=====================================================");
            pw.println("    SISTEMA DE REGISTRO DE MANTENIMIENTO (SIRMA JG)");
            pw.println("              REPORTE GENERAL DE FLOTA");
            pw.println("=====================================================");
            pw.println("Fecha: " + LocalDate.now());
            pw.println("Total Vehiculos Registrados: " + listaVehiculos.size());
            pw.println("=====================================================\n");

            for (Vehiculo v : listaVehiculos) {
                pw.println("VEHICULO: " + v.getPlaca() + " | " + v.getMarca() + " " + v.getModelo() + " | Anio: " + v.getAnio());
                pw.println("PROPIETARIO: " + v.getPropietario().getNombreCompleto() + " (CI: " + v.getPropietario().getCedula() + ")");
                pw.println("--- HISTORIAL DE SERVICIOS ---");
                if (v.getHistorialMantenimientos().isEmpty()) {
                    pw.println("  >> Sin servicios registrados.");
                } else {
                    for (Mantenimiento m : v.getHistorialMantenimientos()) {
                        pw.println("  - ID: " + m.getIdOrden() + " | Fecha: " + m.getFechaRealizacion() + " | Servicio: " + m.getTipoServicio() + " | Costo: $" + String.format("%.2f", m.getCostoTotal()));
                    }
                }
                pw.println("-----------------------------------------------------\n");
            }
            pw.println("Fin del reporte.");
            return nombreArchivo;
        } catch (IOException e) {
            System.err.println("Error al generar el reporte TXT: " + e.getMessage());
            return null;
        }
    }
}