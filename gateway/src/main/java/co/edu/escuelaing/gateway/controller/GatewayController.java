package co.edu.escuelaing.gateway.controller;

import co.edu.escuelaing.gateway.dto.ArrivalRequest;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@RestController
@RequestMapping("/api/arrivals")
public class GatewayController {

    private final RestClient monolith;

    public GatewayController(RestClient monolith) {
        this.monolith = monolith;
    }

    @PostMapping
    public ResponseEntity<String> register(@Valid @RequestBody ArrivalRequest request) {
        return monolith.post()
                .uri("/arrivals")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(String.class);
    }

    @GetMapping
    public ResponseEntity<String> list() {
        return monolith.get()
                .uri("/arrivals")
                .retrieve()
                .toEntity(String.class);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> invalidRequest(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().getFirst().getDefaultMessage();
        return ResponseEntity.badRequest().body(Map.of("error", message));
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<Map<String, String>> monolithUnavailable(RestClientException e) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(Map.of("error", "The backend is not available"));
    }
}
