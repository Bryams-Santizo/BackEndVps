package com.coordinacioncafesystem.Dto;

import java.util.List;
public class TecnologicoPerfilDTO {
    private String estado;
    private String nombreTecnologico;
    private String actividad;
    private String necesidad;
    private String responsable;
    private String contacto;
    private String empresaVinculada;
    private List<String> etapas;
    // getters & setters...
    public String getEstado(){return estado;} public void setEstado(String estado){this.estado=estado;}
    public String getNombreTecnologico(){return nombreTecnologico;} public void setNombreTecnologico(String nombreTecnologico){this.nombreTecnologico=nombreTecnologico;}
    public String getActividad(){return actividad;} public void setActividad(String actividad){this.actividad=actividad;}
    public String getNecesidad(){return necesidad;} public void setNecesidad(String necesidad){this.necesidad=necesidad;}
    public String getResponsable(){return responsable;} public void setResponsable(String responsable){this.responsable=responsable;}
    public String getContacto(){return contacto;} public void setContacto(String contacto){this.contacto=contacto;}
    public String getEmpresaVinculada(){return empresaVinculada;} public void setEmpresaVinculada(String empresaVinculada){this.empresaVinculada=empresaVinculada;}
    public List<String> getEtapas(){return etapas;} public void setEtapas(List<String> etapas){this.etapas=etapas;}
}
