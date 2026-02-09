package com.coordinacioncafesystem.Security.Dto;

public class AuthResponse {
    private String token;
    private String correo;
    private String nombre;
    private String rol;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}

