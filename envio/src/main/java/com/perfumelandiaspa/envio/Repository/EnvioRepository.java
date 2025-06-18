package com.perfumelandiaspa.envio.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.perfumelandiaspa.envio.Model.Entity.EnvioEntity;

//aca el usuario interactua con la entidad a traves de el repositorio
@Repository
public interface EnvioRepository extends JpaRepository<EnvioEntity, Long> {
    // Métodos personalizados
    boolean existsByTransportista(String transportista);
    
    // Consulta personalizada
    @Query("SELECT e FROM EnvioEntity e WHERE e.transportista = :transportista")
    List<EnvioEntity> findByTransportista(String transportista);
}

