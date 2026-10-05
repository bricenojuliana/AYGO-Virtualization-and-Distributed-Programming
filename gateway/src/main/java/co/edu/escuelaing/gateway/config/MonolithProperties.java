package co.edu.escuelaing.gateway.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("monolith")
public record MonolithProperties(String url, Duration connectTimeout, Duration readTimeout) {
}
