package com.coordinacioncafesystem.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "medias")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;
    @Column(columnDefinition = "text")
    private String url;
    private LocalDateTime uploadedAt;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "proyecto_id")
    private Proyectos proyecto;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario uploadedBy;

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }

    public Proyectos getProyecto() { return proyecto; }
    public void setProyecto(Proyectos proyecto) { this.proyecto = proyecto; }

    public Usuario getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(Usuario uploadedBy) { this.uploadedBy = uploadedBy; }
}
