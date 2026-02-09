package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Entity.Proyectos;
import com.coordinacioncafesystem.Entity.Tecnologicos;
import com.coordinacioncafesystem.Repository.ProyectosRepository;
import com.coordinacioncafesystem.Repository.TecnologicoRepository;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ReporteServiceImpl implements ReporteService {

    private final TecnologicoRepository tecRepo;
    private final ProyectosRepository proyectoRepo;

    public ReporteServiceImpl(TecnologicoRepository tecRepo, ProyectosRepository proyectoRepo){
        this.tecRepo = tecRepo; this.proyectoRepo = proyectoRepo;
    }

    @Override
    public byte[] generateTecnologicoReport(Long tecnologicoId) throws IOException {
        Tecnologicos t = tecRepo.findById(tecnologicoId).orElseThrow();
        List<Proyectos> proyectos = proyectoRepo.findByTecnologicoId(tecnologicoId);

        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph p = doc.createParagraph();
        XWPFRun r = p.createRun();
        r.setBold(true); r.setFontSize(14); r.setText("REPORTE - " + t.getNombre());
        r.addBreak();

        for (Proyectos pr : proyectos) {
            XWPFParagraph ph = doc.createParagraph();
            XWPFRun rh = ph.createRun();
            rh.setText("Estado: " + t.getEstado()); rh.addBreak();
            rh.setText("Nombre proyecto: " + pr.getNombre()); rh.addBreak();
            rh.setText("Actividad: " + pr.getActividad()); rh.addBreak();
            rh.setText("Necesidad: " + pr.getNecesidad()); rh.addBreak();
            rh.setText("Empresa vinculada: " + pr.getEmpresaVinculada()); rh.addBreak();
            rh.addBreak();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        doc.write(out); doc.close();
        return out.toByteArray();
    }
}
