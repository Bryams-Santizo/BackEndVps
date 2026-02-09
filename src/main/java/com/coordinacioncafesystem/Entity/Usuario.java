package com.coordinacioncafesystem.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "usuarios")
@JsonIgnoreProperties({"authorities"}) // por si Jackson mira el campo por reflexión
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(nullable = false, unique = true)
    private String correo;   // usuario

    @Column(nullable = false)
    private String password; // contraseña

    private Boolean activo = true;

    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    // 👇 Aquí ligamos al Tecnológico
    @ManyToOne
    @JoinColumn(name = "tecnologico_id")
    private Tecnologicos tecnologico;

    public Usuario() {}

    // =================== Getters / setters ===================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public void setPassword(String password) { this.password = password; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public Tecnologicos getTecnologico() { return tecnologico; }
    public void setTecnologico(Tecnologicos tecnologico) { this.tecnologico = tecnologico; }

    // =================== UserDetails ===================

    // 👇 No exponer authorities en JSON (aquí se arma el GrantedAuthority)
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (rol == null) return Collections.emptyList();
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rol.getNombre()));
    }

    @Override
    public String getUsername() {
        return this.correo;
    }

    // 👇 No exponer la password en las respuestas JSON
    @Override
    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() { return true; }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() { return true; }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    @JsonIgnore
    public boolean isEnabled() { return this.activo != null ? this.activo : false; }
}
