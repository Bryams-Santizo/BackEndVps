package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Entity.Certificacion;
import com.coordinacioncafesystem.Repository.CertificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificacionService {
    @Autowired
    private CertificacionRepository repo;
    public List<Certificacion> obtenerPorMercado(String mercado) { return repo.findByMercado(mercado); }
}
