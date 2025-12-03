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
 * plano (TXT) y de hipertexto (HTML) con formato profesional.
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
 * Clase GestionReportes que encapsula la lógica para crear reportes.
 * PRINCIPIO POO: Modularidad - Esta clase se encarga SOLO de escribir archivos.
 */
public class GestionReportes {

    /**
     * Genera un reporte HTML estilizado, con logo y columnas alineadas.
     * @param listaVehiculos La lista completa de vehículos.
     * @return El nombre del archivo generado o null si falla.
     */
    public String generarReporteHtml(List<Vehiculo> listaVehiculos) {
        String nombreArchivo = "Reporte_SIRMA_" + LocalDate.now() + ".html";
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try (FileWriter fw = new FileWriter(nombreArchivo); PrintWriter pw = new PrintWriter(fw)) {

            // 1. INICIO DEL HTML Y CSS (ESTILOS COMPACTOS)
            pw.println("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'>");
            pw.println("<title>Reporte de Flota - SIRMA JG</title>");
            pw.println("<style>");

            // AJUSTE: Márgenes del cuerpo mínimos
            pw.println("body { font-family: 'Segoe UI', sans-serif; background-color: #1e1e1e; color: #f0f0f0; margin: 10px 20px; }");

            // AJUSTE: Encabezado ULTRA COMPACTO (Sin márgenes extra)
            pw.println(".header { text-align: center; margin-bottom: 5px; border-bottom: 2px solid #ffc107; padding-bottom: 2px; }");

            // Logo sin margen abajo
            pw.println("img { margin-bottom: 0px; display: block; margin-left: auto; margin-right: auto; }");

            // Título pegado al logo
            pw.println("h1 { color: #ffc107; margin: 0; text-transform: uppercase; letter-spacing: 2px; font-size: 22px; line-height: 1.1; }");

            // Estilos de Tablas (Pegadas al título del vehículo)
            pw.println("table { width: 100%; border-collapse: collapse; background-color: #2c2c2c; margin-bottom: 10px; margin-top: 2px; box-shadow: 0 4px 8px rgba(0,0,0,0.3); }");
            pw.println("th { background-color: #ffc107; color: #000; padding: 6px; font-weight: bold; font-size: 13px; }");
            pw.println("td { padding: 5px 8px; border-bottom: 1px solid #444; font-size: 12px; }");

            // Alineación
            pw.println(".text-center { text-align: center; }");
            pw.println(".text-right { text-align: right; }");
            pw.println(".text-left { text-align: left; }");

            // Títulos de Vehículos (Pegaditos a la tabla)
            pw.println(".vehiculo-title { color: #ffc107; font-size: 15px; margin-top: 10px; margin-bottom: 2px; border-left: 4px solid #ffc107; padding-left: 8px; font-weight: bold; }");

            // Colores de Estado
            pw.println(".est-fin { color: #28a745; font-weight: bold; }"); // Verde
            pw.println(".est-pro { color: #ffc107; font-weight: bold; }"); // Amarillo
            pw.println(".est-pen { color: #dc3545; font-weight: bold; }"); // Rojo

            pw.println("</style></head><body>");

            // 2. CONTENIDO DEL REPORTE
            pw.println("<div class='header'>");
            pw.println("  <img src='fondo/sirma.png' width='120' alt='Logo SIRMA'>"); // Logo un poco más pequeño para ahorrar espacio
            pw.println("  <h1>Reporte General de Flota</h1>");
            pw.println("  <p style='color: #aaa; font-size: 10px; margin: 2px 0;'>Fecha de Emisión: " + LocalDate.now().format(formatoFecha) + "</p>");
            pw.println("</div>");

            // 3. ITERACIÓN POR VEHÍCULO
            boolean hayDatos = false;
            for (Vehiculo v : listaVehiculos) {
                if (!v.getHistorialMantenimientos().isEmpty()) {
                    hayDatos = true;
                    // Título del carro
                    pw.println("<div class='vehiculo-title'>Vehículo: " + v.getPlaca() + " - " + v.getMarca() + "</div>");

                    // Tabla
                    pw.println("<table>");
                    pw.println("<thead><tr>");
                    pw.println("  <th class='text-center' width='10%'>ID</th>");
                    pw.println("  <th class='text-center' width='15%'>Fecha</th>");
                    pw.println("  <th class='text-left'>Servicio / Descripción</th>");
                    pw.println("  <th class='text-center' width='15%'>Estado</th>");
                    pw.println("  <th class='text-right' width='15%'>Total ($)</th>");
                    pw.println("</tr></thead><tbody>");

                    for (modelo.Mantenimiento m : v.getHistorialMantenimientos()) {
                        String claseEstado = "est-pen";
                        if(m.getEstado().equalsIgnoreCase("Finalizado")) claseEstado = "est-fin";
                        if(m.getEstado().equalsIgnoreCase("En Proceso")) claseEstado = "est-pro";

                        pw.println("<tr>");
                        pw.println("  <td class='text-center'>" + m.getIdOrden() + "</td>");
                        pw.println("  <td class='text-center'>" + m.getFechaRealizacion().format(formatoFecha) + "</td>");
                        pw.println("  <td class='text-left'>" + m.getTipoServicio() + "</td>");
                        pw.println("  <td class='text-center " + claseEstado + "'>" + m.getEstado() + "</td>");
                        pw.println("  <td class='text-right'>" + String.format(Locale.US, "%.2f", m.getCostoTotal()) + "</td>");
                        pw.println("</tr>");
                    }
                    pw.println("</tbody></table>");
                }
            }

            if(!hayDatos) {
                pw.println("<h3 style='text-align:center; color:#777; margin-top:30px;'>No hay mantenimientos registrados en el sistema.</h3>");
            }

            pw.println("</body></html>");
            return nombreArchivo; // Retorna el nombre si todo salió bien

        } catch (IOException e) {
            e.printStackTrace();
            return null; // Retorna null si hubo error
        }
    }

    /**
     * Genera un reporte TXT plano.
     */
    public String generarReporteTxt(List<Vehiculo> listaVehiculos) {
        String nombreArchivo = "Reporte_SIRMA_" + LocalDate.now() + ".txt";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try (FileWriter fw = new FileWriter(nombreArchivo); PrintWriter pw = new PrintWriter(fw)) {
            pw.println("=============================================");
            pw.println("      REPORTE DE FLOTA - SIRMA JG");
            pw.println("=============================================");
            pw.println("Fecha: " + LocalDate.now().format(fmt));

            for (Vehiculo v : listaVehiculos) {
                pw.println("\n---------------------------------------------");
                pw.println("VEHICULO: " + v.getPlaca() + " (" + v.getMarca() + ")");
                pw.println("---------------------------------------------");
                if (v.getHistorialMantenimientos().isEmpty()) {
                    pw.println("  >> Sin historial.");
                } else {
                    for (modelo.Mantenimiento m : v.getHistorialMantenimientos()) {
                        pw.println(String.format(Locale.US, " > %s | %s | %-20s | %s | $%.2f",
                                m.getIdOrden(),
                                m.getFechaRealizacion().format(fmt),
                                m.getTipoServicio(),
                                m.getEstado(),
                                m.getCostoTotal()));
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