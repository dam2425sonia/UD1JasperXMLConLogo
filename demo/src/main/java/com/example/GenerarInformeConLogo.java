package com.example;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

public class GenerarInformeConLogo {

    public static void main(String[] args) {

        try (
            InputStream archivoJrxml =
            GenerarInformeConLogo.class.getResourceAsStream(
                    "/biblioteca.jrxml");

            InputStream archivoXml =
                GenerarInformeConLogo.class.getResourceAsStream(
                    "/biblioteca.xml");

            InputStream imagen =
                GenerarInformeConLogo.class.getResourceAsStream(
                    "/imagenes/logoBiblioteca2.png")
        ) {

            // 1. Comprobar que existen los recursos
            if (archivoJrxml == null) {
                throw new RuntimeException(
                    "No se encuentra biblioteca.jrxml");
            }

            if (archivoXml == null) {
                throw new RuntimeException(
                    "No se encuentra biblioteca.xml");
            }

            if (imagen == null) {
                throw new RuntimeException(
                    "No se encuentra imagenes/logoBiblioteca2.png");
            }

            // 2. Compilar el informe JRXML
            JasperReport informe =
                JasperCompileManager.compileReport(
                    archivoJrxml);

            // 3. Crear la fuente de datos XML
            JRDataSource datos =
                new JRXmlDataSource(
                    archivoXml,
                    "/biblioteca/libros/libro");

            // 4. Preparar los parámetros del informe
            Map<String, Object> parametros =
                new HashMap<>();

            parametros.put("LOGO", imagen);

            // 5. Rellenar el informe
            JasperPrint informeRellenado =
                JasperFillManager.fillReport(
                    informe,
                    parametros,
                    datos);

            // 6. Exportar el informe a PDF
            JasperExportManager.exportReportToPdfFile(
                informeRellenado,
                "biblioteca.pdf");

            System.out.println(
                "Informe generado correctamente.");

        } catch (Exception e) {

            System.out.println(
                "Error al generar el informe.");

            e.printStackTrace();
        }
    }
}
