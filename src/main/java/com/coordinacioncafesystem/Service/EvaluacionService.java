package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Entity.Certificacion;
import com.coordinacioncafesystem.Entity.Productores;
import com.coordinacioncafesystem.Entity.ResultadoEvaluacion;
import com.coordinacioncafesystem.Repository.CertificacionRepository;
import com.coordinacioncafesystem.Repository.ProductorRepository;
import com.coordinacioncafesystem.Repository.ResultadoEvaluacionRepository;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfWriter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.ByteArrayOutputStream;

@Service
public class EvaluacionService {

    @Autowired private ResultadoEvaluacionRepository evaluacionRepo;
    @Autowired private ProductorRepository productorRepo;
    @Autowired private CertificacionRepository certificacionRepo;

    public ResultadoEvaluacion procesarTest(Long productorId, Long certificacionId, int aciertos, int total) {
        Productores productor = productorRepo.findById(productorId).orElseThrow();
        Certificacion certificacion = certificacionRepo.findById(certificacionId).orElseThrow();

        double porcentaje = ((double) aciertos / total) * 100;

        ResultadoEvaluacion eval = new ResultadoEvaluacion();
        eval.setProductor(productor);

        // CORRECCIÓN: variable en minúscula
        eval.setCertificacionEvaluada(certificacion);
        eval.setPorcentajeCumplimiento(porcentaje);

        if(porcentaje >= 80) {
            eval.setRecomendacionesGeneradas("Estás muy cerca de la certificación. Mantén tus registros al día.");
        } else {
            eval.setRecomendacionesGeneradas("Necesitas mejorar la gestión de residuos y la trazabilidad documental.");
        }

        return evaluacionRepo.save(eval);
    }

    public byte[] generarReportePDF(Long evaluacionId) {
        ResultadoEvaluacion eval = evaluacionRepo.findById(evaluacionId).orElseThrow();

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLUE);
            Paragraph titulo = new Paragraph("Reporte de Simulación ADICAM", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Productor: " + eval.getProductor().getNombreProductor()));
            document.add(new Paragraph("Finca: " + eval.getProductor().getNombreFinca()));
            document.add(new Paragraph("Ubicación: " + eval.getProductor().getUbicacion()));
            document.add(Chunk.NEWLINE);

            // CORRECCIÓN: Llamadas correctas a la entidad
            document.add(new Paragraph("Mercado Objetivo: " + eval.getCertificacionEvaluada().getMercado()));
            document.add(new Paragraph("Certificación Evaluada: " + eval.getCertificacionEvaluada().getNombre()));
            document.add(Chunk.NEWLINE);

            Font fontResult = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.DARK_GRAY);
            document.add(new Paragraph("Nivel de Cumplimiento: " + eval.getPorcentajeCumplimiento() + "%", fontResult));


            document.add(new Paragraph("Recomendaciones: " + eval.getRecomendacionesGeneradas()));

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }
}
