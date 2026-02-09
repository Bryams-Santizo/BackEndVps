package com.coordinacioncafesystem.Security;

import com.coordinacioncafesystem.Entity.Rol;
import com.coordinacioncafesystem.Entity.Tecnologicos;
import com.coordinacioncafesystem.Entity.Usuario;
import com.coordinacioncafesystem.Repository.RolRepository;
import com.coordinacioncafesystem.Repository.TecnologicoRepository;
import com.coordinacioncafesystem.Repository.UsuarioRepository;
import com.coordinacioncafesystem.Security.Dto.AuthResponse;
import com.coordinacioncafesystem.Security.Dto.LoginRequest;
import com.coordinacioncafesystem.Security.Dto.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;
    private final TecnologicoRepository tecnologicoRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authManager,
                          UsuarioRepository usuarioRepo,
                          RolRepository rolRepo,
                          TecnologicoRepository tecnologicoRepo,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {

        this.authManager = authManager;
        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
        this.tecnologicoRepo = tecnologicoRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ============= LOGIN =============
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getCorreo(),
                        req.getPassword()
                )
        );

        Optional<Usuario> userOpt = usuarioRepo.findByCorreo(req.getCorreo());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Usuario no encontrado");
        }

        Usuario u = userOpt.get();

        // Puedes usar el propio Usuario (UserDetails) o un User de Spring
        User springUser = new User(
                u.getCorreo(),
                u.getPassword(),
                u.getAuthorities() // viene de Usuario implements UserDetails
        );

        String token = jwtUtil.generateToken(springUser);

        AuthResponse r = new AuthResponse();
        r.setToken(token);
        r.setCorreo(u.getCorreo());
        r.setNombre(u.getNombre());
        r.setRol(u.getRol() != null ? u.getRol().getNombre() : null);

        return ResponseEntity.ok(r);
    }

    // ============= REGISTER =============
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {

        // 1) Validar que no exista correo
        if (usuarioRepo.existsByCorreo(req.getCorreo())) {
            return ResponseEntity.badRequest().body("Correo ya registrado");
        }

        // 2) Buscar rol por nombre (ADMIN / COORDINADOR)
        Rol rol = rolRepo.findByNombre(req.getRol())
                .orElse(null);

        if (rol == null) {
            return ResponseEntity.badRequest().body("Rol inválido");
        }

        // 3) Crear usuario
        Usuario u = new Usuario();
        u.setNombre(req.getNombre());
        u.setCorreo(req.getCorreo());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setRol(rol);
        u.setActivo(true);

        // 4) Si viene un tecnologicoId (por ejemplo para COORDINADOR), lo asociamos
        if (req.getTecnologicoId() != null) {
            Tecnologicos tec = tecnologicoRepo.findById(req.getTecnologicoId())
                    .orElse(null);
            if (tec == null) {
                return ResponseEntity.badRequest().body("Tecnológico no encontrado");
            }
            u.setTecnologico(tec);
        }

        usuarioRepo.save(u);
        return ResponseEntity.ok("Usuario creado");
    }
}
