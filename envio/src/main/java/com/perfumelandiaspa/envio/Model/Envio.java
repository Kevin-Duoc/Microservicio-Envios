package com.perfumelandiaspa.envio.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Envio {
    private int idEnvio;
    //private int ventaId; es foranea
    private String estado;
    private String fechaEnvio;
    private String transportista;
}
