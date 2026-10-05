package co.edu.escuelaing.gateway.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestClient;

class GatewayControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Nothing listens on port 1, which simulates the monolith being down.
        mockMvc = MockMvcBuilders
                .standaloneSetup(new GatewayController(RestClient.create("http://localhost:1")))
                .build();
    }

    @Test
    void rejectsBlankName() throws Exception {
        mockMvc.perform(post("/api/arrivals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"   \"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Name is required"));
    }

    @Test
    void rejectsTooLongName() throws Exception {
        String name = "a".repeat(101);
        mockMvc.perform(post("/api/arrivals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"" + name + "\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void returnsBadGatewayWhenMonolithIsDown() throws Exception {
        mockMvc.perform(get("/api/arrivals"))
                .andExpect(status().isBadGateway());
    }
}
