package com.perfumelandiaspa.envio.Service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfumelandiaspa.envio.Model.EnvioDTO;
import com.perfumelandiaspa.envio.Model.Entity.EnvioEntity;
import com.perfumelandiaspa.envio.Repository.EnvioRepository;

import jakarta.transaction.Transactional;


//aca van las reglas del negocio, se conecta con las otras clases para interactuar
//es la capa intermedia del servicio entre controller y repository
@Service
public class EnvioService {
    @Autowired //una anotacion
    private EnvioRepository envioRepository;
    
    @Transactional
    public EnvioDTO crearEnvio(String transportista) {
        validarTransportista(transportista);
        
        EnvioEntity nuevoEnvio = new EnvioEntity(transportista);
        EnvioEntity envioGuardado = envioRepository.save(nuevoEnvio);
        
        return EnvioDTO.fromEntity(envioGuardado);
    }
    
    public EnvioDTO obtenerEnvioPorId(Long id) {
        EnvioEntity envio = envioRepository.findById(id).orElseThrow(() -> new RuntimeException("Envío no encontrado con ID: " + id));
            
        return EnvioDTO.fromEntity(envio);
    }
    
    private void validarTransportista(String transportista) {
        if (transportista == null || transportista.trim().isEmpty()) {
            throw new IllegalArgumentException("El transportista no puede estar vacío");
        }
    }

    @Transactional
    public void eliminarEnvio(Long idEnvio) {
        if (!envioRepository.existsById(idEnvio)) {
            throw new NoSuchElementException("Envío no encontrado");
        }
        envioRepository.deleteById(idEnvio);
    }
    // @Transactional  
    // public void eliminarEnvio(Long idEnvio) {  
    //     if (!envioRepository.existsById(idEnvio)) {  
    //         throw new NoSuchElementException("Envío no encontrado con ID: " + idEnvio);  
    //     }  
    //     envioRepository.deleteById(idEnvio);  
    // }  
    // @Transactional
    // public void eliminarEnvio(Long id) {
    //     // Primero verificamos si existe
    //     if (!envioRepository.existsById(id)) {
    //         throw new NoSuchElementException("No se encontró el envío con ID: " + id);
    //     }
        
    //     // Luego eliminamos
    //     envioRepository.deleteById(id);
        
    //     // Opcional: Podrías registrar la eliminación
    //     log.info("Envío con ID {} eliminado correctamente", id);
    // }
    
    // @Transactional
    // public EnvioDTO crearEnvio(String transportista)
    // {
    //     if (transportista == null || transportista.trim().isEmpty()) {
    //         throw new IllegalArgumentException("El transportista es requerido");
    //     }

    //     EnvioEntity nuevoEnvio = new EnvioEntity(transportista);
    //     EnvioEntity envioGuardado = envioRepository.save(nuevoEnvio);

    //     return new EnvioDTO(
    //         envioGuardado.getTransportista(),
    //         envioGuardado.getFechaEstado(),
    //         envioGuardado.isEstado()
    //     );
    // }

    // public EnvioDTO obtenerEnvioPorId(int id)
    // {
    //     return envioRepository.findById(id).map(envio -> new EnvioDTO(
    //         envio.getTransportista(),
    //         envio.getFechaEstado(),
    //         envio.isEstado()
    //     )).orElseThrow(() -> new RuntimeException("Envio no encontrado por su ID: "+ id));
    // }

    //metodo creado para agregarlos
    // public String generarEnvio(EnvioDTO env)
    // {
    //     try {
    //         Boolean estado = envioRepository.existsByIdEnvio(env.getIdEnvio());

    //         if (!estado) {
    //             EnvioEntity envioNuevo = new EnvioEntity();
    //                 envioNuevo.setIdEnvio(env.getIdEnvio());
    //                 envioNuevo.setEstado(env.getEstado());
    //                 envioNuevo.setFechaEstado(env.getFechaEnvio());
    //                 envioNuevo.setTransportista(env.getTransportista());
    //                 envioRepository.save(envioNuevo);
    //                 return "Envio generado correctamente";
    //             }
    //             return "El envio ya existe";
    //         } catch (Exception e) {
    //             return "Error al generar el envio";
    //         }
    // }

    // //metodo creado para encontrar/obtener envios
    // public EnvioDTO obtenerEnvio(int idEnvio)
    // {
    //     try {
    //         EnvioEntity envio = envioRepository.findByIdEnvio(idEnvio);

    //         if (envio != null) {
    //             EnvioDTO env = new EnvioDTO(
    //                 envio.getIdEnvio(),
    //                 envio.getFechaEstado(),
    //                 envio.getEstado(),
    //                 envio.getTransportista()
    //             );
    //             return env;
    //         }
    //         return null;
    //     } catch (Exception e) {
    //         return null;
    //     }
    // }


}
