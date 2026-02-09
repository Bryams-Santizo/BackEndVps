package com.coordinacioncafesystem.Service;

import java.io.IOException;
public interface ReporteService {
    byte[] generateTecnologicoReport(Long tecnologicoId) throws IOException;
}
