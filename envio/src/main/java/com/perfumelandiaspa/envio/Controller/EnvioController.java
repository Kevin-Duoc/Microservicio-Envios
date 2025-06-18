package com.perfumelandiaspa.envio.Controller;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.perfumelandiaspa.envio.Model.EnvioDTO;
import com.perfumelandiaspa.envio.Service.EnvioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;



@RestController //el controlador trabaja con un REST
@RequestMapping("/envios")
public class EnvioController { //el controller es el que interactua con el cliente
    @Autowired
    private EnvioService envioService;
    
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

    //busca por la id
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
    // @PostMapping("/eliminar/{id}")  
    // public ResponseEntity<?> eliminarEnvio(@PathVariable Long id) {  
    //     try {  
    //         envioService.eliminarEnvio(id);  
    //         return ResponseEntity.ok("Envío eliminado correctamente");  
    //     } catch (NoSuchElementException e) {  
    //         return ResponseEntity.notFound().build();  
    //     } catch (Exception e) {  
    //         return ResponseEntity.internalServerError().body("Error al eliminar: " + e.getMessage());  
    //     }  
    // }
}
    // @PostMapping("/envios")
    // //ResonseEntity <-- responder segun accion o resultado
    // //404 --> no se encuentra el recurso
    // //200 --> ok
    // public ResponseEntity<String> obtenerEnvio(@RequestBody EnvioDTO envio) //con el RequestBody se le da el cuerpo del Json
    // {   
    //     return ResponseEntity.ok(envioService.generarEnvio(envio));
    // }


    // @GetMapping("/obtenerEnvio/{idEnvio}")
    // public ResponseEntity<EnvioDTO> obtenerEnvioEntity(@PathVariable int idEnvio)
    // {
    //     EnvioDTO envio = envioService.obtenerEnvio(idEnvio);
    //     if (envio != null) {
    //         return ResponseEntity.ok(envio);
    //     }
    //     return ResponseEntity.notFound().build(); //notFound si manda un parametro que no corresponde, sigue funcionando pero manda 404
    // }
    
