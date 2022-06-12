package hr.tvz.klobucaric.hardwareapp.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tvz.klobucaric.hardwareapp.command.HardwareCommand;
import hr.tvz.klobucaric.hardwareapp.domain.Hardware;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
class HardwareControllerTest {
    //TODO napraviti testove za ostale kontrolere i klase koje to zahtijevaju
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private final static String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY1NDc2NzU3NSwiaWF0IjoxNjU0MTYyNzc1LCJhdXRob3JpdGllcyI6IlJPTEVfQURNSU4ifQ.LSVWn8UYgR_kjhhdGhMsHLFQdPtWTC1dEAqnozqD2ROfJYp1rW0eYoewSIH0ovpDH_Bg3k5z_rjBm5EfNF5SqA";

    @Test
    void getAllHardwareComponents() throws Exception {

        this.mockMvc.perform(
                        get("/hardware")
                                .with(user("admin")
                                        .password("test")
                                        .roles("ADMIN")
                                )
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ TOKEN)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));

    }

    @Test
    void getHardwareComponentByCode() throws Exception {
        String hardwareCode = "ryz_5_3600";

        this.mockMvc.perform(
                        get("/hardware/code/"+hardwareCode)
                                .with(user("admin")
                                        .password("admin")
                                        .roles("ADMIN")
                                )
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ TOKEN)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith((MediaType.APPLICATION_JSON)));
    }

    @Transactional
    @DirtiesContext
    @Test
    void save() throws Exception {
        final String TEST_NAME = "testName";
        final String TEST_CODE = "testCode";
        final Hardware.Type TEST_TYPE = Hardware.Type.valueOf("CPU");
        final Integer TEST_STOCK = 55;
        final BigDecimal TEST_PRICE = new BigDecimal("150.55");
        final String TEST_SPECIFICATION = "testSpec";
        HardwareCommand hardwareCommand = new HardwareCommand(TEST_NAME, TEST_CODE,TEST_TYPE, TEST_STOCK, TEST_PRICE, TEST_SPECIFICATION);

        this.mockMvc.perform(
                        post("/hardware")
                                .with(user("admin")
                                        .password("admin")
                                        .roles("ADMIN")
                                )
                                .with(csrf())
                                 .header(HttpHeaders.AUTHORIZATION,"Bearer "+ TOKEN)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(objectMapper.writeValueAsString(hardwareCommand))
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith((MediaType.APPLICATION_JSON)))
                .andExpect(jsonPath("$.name").value(TEST_NAME))
                .andExpect(jsonPath("$.code").value(TEST_CODE))
                .andExpect(jsonPath("$.price").value(TEST_PRICE));
    }

    @Transactional
    @DirtiesContext
    @Test
    void deleteHardwareByCode() throws Exception {
        this.mockMvc.perform(
                        delete("/hardware/ryz_5_3600")
                                .with(user("admin").password("admin").roles("ADMIN"))
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


}