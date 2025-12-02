/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación III - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      GestionReportes.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Clase de utilidad responsable de generar los reportes de salida del sistema.
 * Transforma la lista de objetos del modelo de dominio en archivos de texto
 * plano (TXT) y de hipertexto (HTML) para su consulta externa.
 * ============================================================================
 */
package persistencia;

import modelo.Vehiculo;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * Clase GestionReportes que encapsula la lógica para crear reportes en diferentes formatos.
 * PRINCIPIO POO: Modularidad - Aísla la responsabilidad de la generación de reportes
 * en una clase específica, separándola de la persistencia de datos y la lógica de negocio.
 */
public class GestionReportes {

    /**
     * Genera un reporte completo de la flota en formato HTML con estilos CSS incrustados.
     * El reporte agrupa los mantenimientos por vehículo y presenta los datos en tablas.
     *
     * @param listaVehiculos La lista completa de vehículos con su historial de mantenimientos.
     * @return El nombre del archivo HTML generado (ej. "Reporte_SIRMA_2025-12-05.html"),
     *         o {@code null} si ocurre un error de escritura.
     */
    public String generarReporteHtml(List<Vehiculo> listaVehiculos) {
        // Genera un nombre de archivo único basado en la fecha actual.
        String nombreArchivo = "Reporte_SIRMA_" + LocalDate.now() + ".html";

        // Define el formato de fecha estándar para el reporte (DD-MM-YYYY).
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Utiliza try-with-resources para garantizar el cierre seguro del archivo.
        try (FileWriter fw = new FileWriter(nombreArchivo); PrintWriter pw = new PrintWriter(fw)) {

            // PROCESO: Escritura del contenido HTML línea por línea.

            // 1. Cabecera HTML y estilos CSS inyectados para un diseño autocontenido.
            pw.println("<html><head><title>Reporte de Flota - SIRMA JG</title><style>");
            pw.println("body { font-family: sans-serif; background-color: #121212; color: #e0e0e0; margin: 40px; }");
            pw.println("h1 { color: #FFD700; text-align: center; border-bottom: 2px solid #FFD700; padding-bottom: 10px; }");
            pw.println("h2 { color: #FFD700; margin-top: 40px; }");
            pw.println("table { width: 100%; border-collapse: collapse; background-color: #1e1e1e; margin-top: 15px; }");
            pw.println("th { background-color: #FFD700; color: #000000; padding: 12px; text-align: center; }");
            pw.println("td { padding: 10px; border-bottom: 1px solid #333333; text-align: center; }");
            pw.println("td:last-child { text-align: right; padding-right: 20px; }");
            pw.println("</style></head><body>");

            // 2. Título y fecha del reporte.
            pw.println("<h1>REPORTE GENERAL DE FLOTA - SIRMA JG</h1>");
            pw.println("<p style='text-align:center'>Fecha de Corte: " + LocalDate.now().format(formatoFecha) + "</p>");

            // 3. Itera sobre cada vehículo para generar su sección en el reporte.
            for (Vehiculo v : listaVehiculos) {
                if (!v.getHistorialMantenimientos().isEmpty()) {
                    pw.println("<h2>Vehículo: " + v.getPlaca() + " (" + v.getMarca() + ")</h2>");
                    pw.println("<table><tr><th>ID</th><th>Fecha</th><th>Servicio</th><th>Estado</th><th>Total ($)</th></tr>");

                    // Itera sobre el historial de mantenimientos de cada vehículo.
                    for (modelo.Mantenimiento m : v.getHistorialMantenimientos()) {
                        pw.println("<tr>");
                        pw.println("  <td>" + m.getIdOrden() + "</td>");
                        pw.println("  <td>" + m.getFechaRealizacion().format(formatoFecha) + "</td>");
                        pw.println("  <td>" + m.getTipoServicio() + "</td>");

                        // Lógica de formato condicional para el color del estado.
                        String color;
                        switch (m.getEstado()) {
                            case "Finalizado": color = "#4CAF50"; break; // Verde
                            case "En Proceso": color = "#FFC107"; break; // Amarillo
                            case "Pendiente":  color = "#F44336"; break; // Rojo
                            default:           color = "#FFFFFF"; break; // Blanco
                        }
                        pw.println("  <td style='color:" + color + "; font-weight:bold;'>" + m.getEstado() + "</td>");
                        pw.println("  <td>" + String.format(Locale.US, "%.2f", m.getCostoTotal()) + "</td>");
                        pw.println("</tr>");
                    }
                    pw.println("</table>");
                }
            }
            pw.println("</body></html>");

            // SALIDA: Retorna el nombre del archivo si la operación fue exitosa.
            return nombreArchivo;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Genera un reporte de la flota en formato de texto plano (TXT).
     * Este formato es ideal para logs o procesamiento por otros sistemas.
     *
     * @param listaVehiculos La lista completa de vehículos con su historial.
     * @return El nombre del archivo TXT generado (ej. "Reporte_SIRMA_2025-12-05.txt"),
     *         o {@code null} si ocurre un error de escritura.
     */
    public String generarReporteTxt(List<Vehiculo> listaVehiculos) {
        String nombreArchivo = "Reporte_SIRMA_" + LocalDate.now() + ".txt";
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try (FileWriter fw = new FileWriter(nombreArchivo); PrintWriter pw = new PrintWriter(fw)) {
            pw.println("=============================================");
            pw.println("      REPORTE DE FLOTA - SIRMA JG");
            pw.println("=============================================");
            pw.println("Fecha de Corte: " + LocalDate.now().format(formatoFecha));

            for (Vehiculo v : listaVehiculos) {
                pw.println("\n---------------------------------------------");
                pw.println("VEHICULO: " + v.getPlaca() + " (" + v.getMarca() + " " + v.getModelo() + ")");
                pw.println("---------------------------------------------");
                if (v.getHistorialMantenimientos().isEmpty()) {
                    pw.println("  >> Sin historial de mantenimiento registrado.");
                } else {
                    for (modelo.Mantenimiento m : v.getHistorialMantenimientos()) {
                        // Se utiliza String.format para crear una línea de texto bien estructurada.
                        pw.println(String.format(Locale.US, " > ID: %s | Fecha: %s | %s (%s) | Total: $%.2f",
                                m.getIdOrden(), m.getFechaRealizacion().format(formatoFecha),
                                m.getTipoServicio(), m.getEstado(), m.getCostoTotal()));
                    }
                }
            }
            return nombreArchivo;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}