package com.perfumelandiaspa.envio.Model.Entity;

import lombok.Data;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity //se declara como entidad
@Data
@Table(name = "envios")
public class EnvioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEnvio;
    
    @Column(nullable = false)
    private String transportista;
    
    @Column(name = "fecha_estado", nullable = false)
    private LocalDateTime fechaEstado;
    
    @Column(nullable = false)
    private boolean estado;
    
    public EnvioEntity() {
        this.fechaEstado = LocalDateTime.now();
        this.estado = true;
    }
    
    public EnvioEntity(String transportista) {
        this();
        this.transportista = transportista;
    }
}
