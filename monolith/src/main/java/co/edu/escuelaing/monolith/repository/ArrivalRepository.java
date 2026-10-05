package co.edu.escuelaing.monolith.repository;

import co.edu.escuelaing.monolith.model.Arrival;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArrivalRepository extends MongoRepository<Arrival, String> {
}
