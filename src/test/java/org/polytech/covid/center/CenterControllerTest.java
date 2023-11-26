package org.polytech.covid.center;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.polytech.covid.user.AuthRequest;
import org.polytech.covid.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest//(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CenterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    String superadminToken;
    Center center1 = new Center();
    Center center2 = new Center();

    @BeforeEach
    public void setup() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("superAdmin@superAdmin.com");
        authRequest.setPassword("plokij");
        this.superadminToken = this.userService.authenticate(authRequest).getToken();

        center1.setId(1);
        center1.setName("Centre 1");
        center1.setCity("Nancy");
        center1.setCity_code(54000);
        center1.setAddress("1 rue du Centre");

        center2.setId(2);
        center2.setName("Centre 2");
        center2.setCity("Lille");
        center2.setCity_code(59000);
        center2.setAddress("1 rue du Centre");
    }

    @Test
    void createCenter() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/superadmin/center")
                        .content(mapper.writeValueAsString(center1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + this.superadminToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(center1)))
        ;
    }

    @Test
    @Sql(statements = "INSERT INTO center (id, name, city, city_code, address) VALUES (1, 'Centre 1', 'Nancy', 54000, '1 rue du Centre')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO center (id, name, city, city_code, address) VALUES (2, 'Centre 2', 'Lille', 59000, '1 rue du Centre')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM center WHERE id=1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM center WHERE id=2", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllByCity() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/public/center/city/nancy")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(List.of(center1))));
    }

    @Test
    @Sql(statements = "INSERT INTO center (id, name, city, city_code, address) VALUES (1, 'Centre 1', 'Nancy', 54000, '1 rue du Centre')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO center (id, name, city, city_code, address) VALUES (2, 'Centre 2', 'Lille', 59000, '1 rue du Centre')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM center WHERE id=1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM center WHERE id=2", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllByName() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/public/center/name/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(List.of(center2))));
    }

    @Test
    @Sql(statements = "INSERT INTO center (id, name, city, city_code, address) VALUES (1, 'Centre 1', 'Nancy', 54000, '1 rue du Centre')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO center (id, name, city, city_code, address) VALUES (2, 'Centre 2', 'Lille', 59000, '1 rue du Centre')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM center WHERE id=1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM center WHERE id=2", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getOneById() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/public/center/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(center1)));
    }

    @Test
    @Sql(statements = "INSERT INTO center (id, name, city, city_code, address) VALUES (1, 'Centre 1', 'Nancy', 54000, '1 rue du Centre')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO center (id, name, city, city_code, address) VALUES (2, 'Centre 2', 'Lille', 59000, '1 rue du Centre')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM center WHERE id=1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM center WHERE id=2", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAll() throws Exception{
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/public/center")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(List.of(center1, center2))));
    }


    @Test
    @Sql(statements = "INSERT INTO center (id, name, city, city_code, address) VALUES (1, 'Centre 1', 'Nancy', 54000, '1 rue du Centre')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM center WHERE id=1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void updateCenter() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Center newCenter1 = center1;
        newCenter1.setName("new name");

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/superadmin/center/1")
                        .content(mapper.writeValueAsString(newCenter1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + this.superadminToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(newCenter1)));
    }

    @Test
    @Sql(statements = "INSERT INTO center (id, name, city, city_code, address) VALUES (1, 'Centre 1', 'Nancy', 54000, '1 rue du Centre')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deleteCenter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/superadmin/center/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + this.superadminToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}