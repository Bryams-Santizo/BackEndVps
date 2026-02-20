package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Entity.GaleriaEvidencia;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IGaleriaService {
    // Recibe el archivo y la entidad completa
    GaleriaEvidencia guardarEvidencia(MultipartFile archivo, GaleriaEvidencia evidencia) throws IOException;

    // Cambiamos a listar por Tecnológico para que sea como Eventos
    List<GaleriaEvidencia> listarPorTecnologico(Long tecnologicoId);

    List<GaleriaEvidencia> listarTodas();


    void eliminarEvidencia(Long id) throws IOException;
}
