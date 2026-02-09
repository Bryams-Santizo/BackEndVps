package com.coordinacioncafesystem;

import com.coordinacioncafesystem.Entity.Rol;
import com.coordinacioncafesystem.Entity.Tecnologicos;
import com.coordinacioncafesystem.Entity.Usuario;
import com.coordinacioncafesystem.Repository.RolRepository;
import com.coordinacioncafesystem.Repository.TecnologicoRepository;
import com.coordinacioncafesystem.Repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final RolRepository rolRepo;
    private final UsuarioRepository usuarioRepo;
    private final TecnologicoRepository tecRepo;
    private final PasswordEncoder encoder;

    public DataLoader(RolRepository rolRepo,
                      UsuarioRepository usuarioRepo,
                      TecnologicoRepository tecRepo,
                      PasswordEncoder encoder) {
        this.rolRepo = rolRepo;
        this.usuarioRepo = usuarioRepo;
        this.tecRepo = tecRepo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {

        // ============================
        // 1. ROLES
        // ============================
        Rol rolAdmin = rolRepo.findByNombre("ADMIN")
                .orElseGet(() -> rolRepo.save(createRol("ADMIN", "Administrador del sistema")));

        Rol rolCoord = rolRepo.findByNombre("COORDINADOR")
                .orElseGet(() -> rolRepo.save(createRol("COORDINADOR", "Coordinador Tecnológico")));

        // ============================
        // 2. TECNOLÓGICOS
        // ============================
        Tecnologicos tecComalapa     = saveTec("TecNM Campus Frontera Comalapa", "Chiapas", "Frontera Comalapa", "9631234567", "@fcomalapa.tecnm.mx");

        // ============================
        // 3. ADMIN
        // ============================
        if (usuarioRepo.findByCorreo("admin@coordinacioncafe.mx").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNombre("Admin CoordinacionCafe");
            admin.setCorreo("admin@coordinacioncafe.mx");
            admin.setPassword(encoder.encode("Admin1234!"));
            admin.setRol(rolAdmin);
            admin.setActivo(true);
            admin.setTecnologico(null); // Admin no está ligado a un solo Tec
            usuarioRepo.save(admin);
        }

        // ============================
        // 4. COORDINADORES
        // ============================
        // Todos con password: CafeTec2025
        createCoordinator("Coordinador Frontera Comalapa",
                "coord.comalapa@tecnm.mx", rolCoord, tecComalapa);


    }

    // ===================== HELPERS =====================

    private Rol createRol(String nombre, String desc) {
        Rol r = new Rol();
        r.setNombre(nombre);
        r.setDescripcion(desc);
        return r;
    }

    private Tecnologicos saveTec(String nombre, String estado, String ciudad,
                                 String telefono, String correo) {
        return tecRepo.findByNombre(nombre).orElseGet(() -> {
            Tecnologicos t = new Tecnologicos();
            t.setNombre(nombre);
            t.setEstado(estado);
            t.setCiudad(ciudad);
            t.setTelefono(telefono);
            t.setCorreoContacto(correo);
            return tecRepo.save(t);
        });
    }

    private void createCoordinator(String nombre, String correo,
                                   Rol rol, Tecnologicos tec) {
        if (usuarioRepo.findByCorreo(correo).isEmpty()) {
            Usuario u = new Usuario();
            u.setNombre(nombre);
            u.setCorreo(correo);
            u.setPassword(encoder.encode("CafeTec2025"));
            u.setRol(rol);
            u.setActivo(true);
            u.setTecnologico(tec); // 👈 aquí se liga al tecnológico
            usuarioRepo.save(u);
        }
    }
}
