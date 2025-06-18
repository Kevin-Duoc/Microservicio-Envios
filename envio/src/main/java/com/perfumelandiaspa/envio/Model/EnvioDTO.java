package com.perfumelandiaspa.envio.Model;

import java.time.LocalDateTime;

import com.perfumelandiaspa.envio.Model.Entity.EnvioEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvioDTO {
    private String transportista;
    private LocalDateTime fechaEstado;
    private boolean estado;
    
    // Método para convertir de Entity a DTO
    public static EnvioDTO fromEntity(EnvioEntity entity) {
        return new EnvioDTO(
            entity.getTransportista(),
            entity.getFechaEstado(),
            entity.isEstado()
        );
    }
}
