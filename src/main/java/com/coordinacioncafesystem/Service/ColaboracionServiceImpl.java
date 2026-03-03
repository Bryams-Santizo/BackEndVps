package com.coordinacioncafesystem.Service;


import com.coordinacioncafesystem.Entity.Colaboracion;
import com.coordinacioncafesystem.Repository.ColaboracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ColaboracionServiceImpl implements ColaboracionService {

    @Autowired
    private ColaboracionRepository repository;

    @Override
    @Transactional
    public Colaboracion guardar(Colaboracion colaboracion) {
        // Aquí podrías agregar validaciones antes de guardar
        return repository.save(colaboracion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Colaboracion> listarTodo() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Colaboracion obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Colaboración no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
