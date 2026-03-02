package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Entity.Certificacion;
import com.coordinacioncafesystem.Entity.Productores;
import com.coordinacioncafesystem.Repository.CertificacionRepository;
import com.coordinacioncafesystem.Repository.ProductorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductorService {

    @Autowired
    private ProductorRepository repo;
    public Productores guardar(Productores p) { return repo.save(p); }
}
