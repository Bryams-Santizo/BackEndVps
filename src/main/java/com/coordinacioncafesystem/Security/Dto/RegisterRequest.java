package com.coordinacioncafesystem.Security.Dto;

public class RegisterRequest {
        private String nombre;
        private String correo;
        private String password;
        private String telefono;
        private String rol;            // "ADMIN" o "COORDINADOR"
        private Long tecnologicoId;    // opcional: solo para coordinadores

        // getters / setters

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getCorreo() { return correo; }
        public void setCorreo(String correo) { this.correo = correo; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }

        public String getRol() { return rol; }
        public void setRol(String rol) { this.rol = rol; }

        public Long getTecnologicoId() { return tecnologicoId; }
        public void setTecnologicoId(Long tecnologicoId) { this.tecnologicoId = tecnologicoId; }
    }
