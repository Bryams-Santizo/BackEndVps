package com.coordinacioncafesystem.Security;

import com.coordinacioncafesystem.Entity.Usuario;
import com.coordinacioncafesystem.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service("securityService")
public class SecurityService {

    private final UsuarioRepository usuarioRepo;

    public SecurityService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    /** Devuelve true si el usuario con ese correo tiene rol ADMIN */
    public boolean isAdmin(String correo) {
        Usuario u = usuarioRepo.findByCorreo(correo).orElse(null);
        return u != null
                && u.getRol() != null
                && "ADMIN".equalsIgnoreCase(u.getRol().getNombre());
    }

    /** Devuelve true si el usuario con ese correo tiene rol COORDINADOR */
    public boolean isCoordinador(String correo) {
        Usuario u = usuarioRepo.findByCorreo(correo).orElse(null);
        return u != null
                && u.getRol() != null
                && "COORDINADOR".equalsIgnoreCase(u.getRol().getNombre());
    }

    /**
     * Método pensado para usar en @PreAuthorize.
     *
     * AHORA: targetOrgId representa el ID del TECNOLÓGICO.
     *
     * - ADMIN -> siempre true
     * - COORDINADOR -> solo si su tecnologico.id == targetOrgId
     * - otro rol / sin TEC -> false
     */
    public boolean canAccessByOrg(Object principal, Long targetOrgId) {
        String correo = extractCorreoFromPrincipal(principal);
        if (correo == null || targetOrgId == null) {
            return false;
        }

        Usuario u = usuarioRepo.findByCorreo(correo).orElse(null);
        if (u == null || u.getRol() == null) {
            return false;
        }

        String rol = u.getRol().getNombre();

        // ADMIN puede acceder a cualquier Tecnológico
        if ("ADMIN".equalsIgnoreCase(rol)) {
            return true;
        }

        // COORDINADOR solo puede acceder a su propio Tecnológico
        if ("COORDINADOR".equalsIgnoreCase(rol)) {
            if (u.getTecnologico() == null || u.getTecnologico().getId() == null) {
                return false;
            }
            return u.getTecnologico().getId().equals(targetOrgId);
        }

        // Otros roles, por ahora, no tienen acceso
        return false;
    }

    /** Extrae el correo del principal en distintos casos posibles */
    private String extractCorreoFromPrincipal(Object principal) {
        if (principal == null) return null;

        if (principal instanceof String s) {
            return s;
        }

        // Si en algún lugar usas org.springframework.security.core.userdetails.User
        if (principal instanceof org.springframework.security.core.userdetails.User ud) {
            return ud.getUsername();
        }

        // Si usas directamente tu entidad Usuario como principal
        if (principal instanceof Usuario u) {
            return u.getCorreo();
        }

        return null;
    }
}
