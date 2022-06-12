package hr.tvz.klobucaric.hardwareapp.controller;

import hr.tvz.klobucaric.hardwareapp.dto.ReviewDto;
import hr.tvz.klobucaric.hardwareapp.services.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ReviewControllerTest {

    private final static String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY1NDc2NzU3NSwiaWF0IjoxNjU0MTYyNzc1LCJhdXRob3JpdGllcyI6IlJPTEVfQURNSU4ifQ.LSVWn8UYgR_kjhhdGhMsHLFQdPtWTC1dEAqnozqD2ROfJYp1rW0eYoewSIH0ovpDH_Bg3k5z_rjBm5EfNF5SqA";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Test
    void getAllReviews() throws Exception {
        this.mockMvc.perform(
                        get("/review")
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

    @Test
    void getReviewsByHardwareCode() throws Exception {
        String hardwareCode = "ryz_5_3600";


        this.mockMvc.perform(
                        get("/review?code="+hardwareCode)
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



    @Test
    void getReviewById() throws Exception {
        long reviewID = 1;

        when(reviewService.findReviewById(reviewID))
                .thenReturn(Optional.of(new ReviewDto(reviewID, "testTitle","testText",5, "user", "ryz_5_2600", LocalDateTime.now())));

        this.mockMvc.perform(
                        get("/review/id/"+reviewID)
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
}