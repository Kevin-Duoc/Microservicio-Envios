package com.perfumelandiaspa.envio.Controller;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.perfumelandiaspa.envio.Model.EnvioDTO;
import com.perfumelandiaspa.envio.Model.Entity.EnvioEntity;
import com.perfumelandiaspa.envio.Service.EnvioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;

@RestController //el controlador trabaja con un REST
@RequestMapping("/api/v1/envios")
public class EnvioController { //el controller es el que interactua con el cliente
    @Autowired
    private EnvioService envioService;
    
    @Operation(
        summary = "Crea un nuevo envio",
        description = "Este EndPoint crea un nuevo envio y lo almacena en la base de datos",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Envio creado exitosamente",
                content = @Content(schema = @Schema(implementation = String.class))//Devuelve un cuerpo tipo Json
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Solicitud invalida",
                content = @Content()//no hay cuerpo en la respuesta por ende vacio
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Recurso no fue encontrado",
                content = @Content()//no hay cuerpo en la respuesta por ende vacio
            )
        }
    )
    //agrega repartidor solamente, la hora se asigna local
    @PostMapping
    public ResponseEntity<?> crearEnvio(@RequestBody EnvioDTO envioDTO) {
        try {
            EnvioDTO respuesta = envioService.crearEnvio(envioDTO.getTransportista());
            return ResponseEntity.ok(respuesta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al crear envío: " + e.getMessage());
        }
    }

    @Operation(
        summary = "Buscar Envio",
        description = "Este EndPoint se encarga de buscar un envio almacenado en la DB través de su ID",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Envio encontrado exitosamente",
                content = @Content(schema = @Schema(implementation = EnvioEntity.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Recurso no fue encontrado",
                content = @Content()
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno en el servidor",
                content = @Content()
            )
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerEnvio(@PathVariable Long id) {
        try {
            EnvioDTO respuesta = envioService.obtenerEnvioPorId(id);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error al obtener envío: " + e.getMessage());
        }
    }
    
    @Operation(
        summary = "Eliminar Envio",
        description = "Elimina un envio que se encuentra en la DB por su ID",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description =  "Vendedor encontrado y eliminado correctamente",
                content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Solicitud invalida",
                content = @Content()
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Recurso no fue encontrado",
                content = @Content()
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno en el servidor",
                content = @Content()
            )
        }
    )
    @PostMapping("/eliminar")
    public ResponseEntity<?> eliminarEnvio(@RequestBody Map<String, Long> request) {
        try {
            Long idEnvio = request.get("idEnvio");
            envioService.eliminarEnvio(idEnvio);
            return ResponseEntity.ok("Envío eliminado correctamente");
            
        } catch (NoSuchElementException e) {// SI retorna 404, sale mensaje personalizado
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el envío con ID: " + request.get("idEnvio"));
                    
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error interno al eliminar: " + e.getMessage());
        }
    }
}