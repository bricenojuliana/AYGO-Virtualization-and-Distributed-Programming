package co.edu.escuelaing.monolith.controller;

import co.edu.escuelaing.monolith.dto.ArrivalRequest;
import co.edu.escuelaing.monolith.model.Arrival;
import co.edu.escuelaing.monolith.repository.ArrivalRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/arrivals")
public class ArrivalController {

    private final ArrivalRepository repository;

    public ArrivalController(ArrivalRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Arrival register(@RequestBody ArrivalRequest request) {
        return repository.save(new Arrival(null, request.name(), Instant.now()));
    }

    @GetMapping
    public List<Arrival> list() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, Arrival::timestamp));
    }
}
