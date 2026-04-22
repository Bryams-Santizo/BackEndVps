// --- ResultadoEvaluacion.java CORREGIDO ---
package com.coordinacioncafesystem.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "evaluacion")
public class ResultadoEvaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "productor_id")
    @JsonBackReference // Evita bucles infinitos en el JSON
    private Productores productor;

    // CORRECCIÓN: Relación con Certificación, no un String
    @ManyToOne
    @JoinColumn(name = "certificacion_id")
    private Certificacion certificacionEvaluada;

    private Double porcentajeCumplimiento;

    @Column(columnDefinition = "TEXT")
    private String recomendacionesGeneradas; // Tú lo llamaste así, lo mantendremos

    private LocalDate fechaEvaluacion = LocalDate.now(); // Mejor inicializarlo

    // Getters y Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Productores getProductor() { return productor; }
    public void setProductor(Productores productor) { this.productor = productor; }

    // CORRECCIÓN de Getter/Setter
    public Certificacion getCertificacionEvaluada() { return certificacionEvaluada; }
    public void setCertificacionEvaluada(Certificacion certificacionEvaluada) { this.certificacionEvaluada = certificacionEvaluada; }

    public Double getPorcentajeCumplimiento() { return porcentajeCumplimiento; }
    public void setPorcentajeCumplimiento(Double porcentajeCumplimiento) { this.porcentajeCumplimiento = porcentajeCumplimiento; }
    public String getRecomendacionesGeneradas() { return recomendacionesGeneradas; }
    public void setRecomendacionesGeneradas(String recomendacionesGeneradas) { this.recomendacionesGeneradas = recomendacionesGeneradas; }
    public LocalDate getFechaEvaluacion() { return fechaEvaluacion; }
    public void setFechaEvaluacion(LocalDate fechaEvaluacion) { this.fechaEvaluacion = fechaEvaluacion; }


}
