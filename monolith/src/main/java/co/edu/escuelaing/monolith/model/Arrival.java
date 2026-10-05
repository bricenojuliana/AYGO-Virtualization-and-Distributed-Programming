package co.edu.escuelaing.monolith.model;

import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("arrivals")
public record Arrival(@Id String id, String name, Instant timestamp) {
}
