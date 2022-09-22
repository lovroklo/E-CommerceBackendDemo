package hr.klobucaric.webshop.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import hr.klobucaric.webshop.product.ProductCommand;
import hr.klobucaric.webshop.product.ProductDto;
import hr.klobucaric.webshop.product.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private ProductService productService;


    @Test
    public void getAllProducts_whenNotAuthenticated_200() throws Exception {
        ProductDto obj1 = new ProductDto(1L, "test", "test_desc", "test_image", "1");
        Pageable pageable = PageRequest.of(0, 12);
        Page<ProductDto> productDtos = new PageImpl<>(List.of(obj1), pageable, 0);
        Mockito.when(productService.findAllByPagination(0, 12)).thenReturn(productDtos);

        this.mockMvc
                .perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPage").value(productDtos.getNumber()))
                .andExpect(jsonPath("$.totalItems").value(productDtos.getTotalElements()))
                .andExpect(jsonPath("$.totalPages").value(productDtos.getTotalPages()));

        verify(this.productService, times(1)).findAllByPagination(anyInt(), anyInt());
        verifyNoMoreInteractions(this.productService);

    }

    @Test
    @WithMockUser(authorities = "USER")
    public void getAllProducts_when1Product_EmptyCollection() throws Exception {
        ProductDto obj1 = new ProductDto(1L, "test", "test_desc", "test_image", "1");
        Pageable pageable = PageRequest.of(0, 12);
        Page<ProductDto> productDtos = new PageImpl<>(List.of(obj1), pageable, 0);
        Mockito.when(productService.findAllByPagination(0, 12)).thenReturn(productDtos);

        this.mockMvc
                .perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPage").value(productDtos.getNumber()))
                .andExpect(jsonPath("$.totalItems").value(productDtos.getTotalElements()))
                .andExpect(jsonPath("$.totalPages").value(productDtos.getTotalPages()));

        verify(this.productService, times(1)).findAllByPagination(anyInt(), anyInt());
        verifyNoMoreInteractions(this.productService);

    }

    @Test
    @WithMockUser(authorities = "USER")
    public void getAllProducts_when1Product_WithCategory() throws Exception {
        ProductDto obj1 = new ProductDto(1L, "test", "test_desc", "test_image", "1");
        Pageable pageable = PageRequest.of(0, 12);
        Page<ProductDto> productDtos = new PageImpl<>(List.of(obj1), pageable, 0);
        Mockito.when(productService.findByCategoryId(1L, 0, 12)).thenReturn(productDtos);
        this.mockMvc
                .perform(get("/api/products?ctg=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPage").value(productDtos.getNumber()))
                .andExpect(jsonPath("$.totalItems").value(productDtos.getTotalElements()))
                .andExpect(jsonPath("$.totalPages").value(productDtos.getTotalPages()));

        verify(this.productService, times(1)).findByCategoryId(anyLong(), anyInt(), anyInt());
        verifyNoMoreInteractions(this.productService);

    }


    @Test
    public void getProductById_whenNotAuthenticated_200() throws Exception {
        ProductDto obj1 = new ProductDto(1L, "test", "test_desc", "test_image", "1");
        Mockito.when(productService.findById(1L)).thenReturn(obj1);

        this.mockMvc
                .perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(this.productService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(this.productService);

    }

    @Test
    @WithMockUser(authorities = "USER")
    public void getProductById() throws Exception {
        ProductDto obj1 = new ProductDto(1L, "test", "test_desc", "test_image", "1");
        Mockito.when(productService.findById(1L)).thenReturn(obj1);

        this.mockMvc
                .perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(this.productService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(this.productService);

    }

    @Test
    public void save_whenRoleUser_403() throws Exception {
        ProductCommand testCommand = new ProductCommand("test", "test",
                "https://cdn.shopify.com/s/files/1/1094/4892/products/art-zidne-slike-plitvice---ap011art-zidne-slikelife-decorlife-decor-15090663_1654x1103.jpg?v=1578840014",
                1L);

        this.mockMvc
                .perform(post("/api/products")
                        .with(user("user")
                                .password("test")
                                .roles("USER")
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testCommand))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void save_whenRoleAdmin_201() throws Exception {
        ProductCommand testCommand = new ProductCommand("test", "test",
                "https://cdn.shopify.com/s/files/1/1094/4892/products/art-zidne-slike-plitvice---ap011art-zidne-slikelife-decorlife-decor-15090663_1654x1103.jpg?v=1578840014",
                1L);

        this.mockMvc
                .perform(post("/api/products")
                        .with(user("admin")
                                .password("test")
                                .roles("ADMIN")
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testCommand))
                )
                .andExpect(status().isCreated());
    }


    @Test
    public void update_whenRoleAdmin_201() throws Exception {
        ProductCommand testCommand = new ProductCommand("test", "test",
                "https://cdn.shopify.com/s/files/1/1094/4892/products/art-zidne-slike-plitvice---ap011art-zidne-slikelife-decorlife-decor-15090663_1654x1103.jpg?v=1578840014",
                1L);

        this.mockMvc
                .perform(put("/api/products/1")
                        .with(user("admin")
                                .password("test")
                                .roles("ADMIN")
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testCommand))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void update_whenRoleUser_403() throws Exception {
        ProductCommand testCommand = new ProductCommand("test", "test",
                "https://cdn.shopify.com/s/files/1/1094/4892/products/art-zidne-slike-plitvice---ap011art-zidne-slikelife-decorlife-decor-15090663_1654x1103.jpg?v=1578840014",
                1L);

        this.mockMvc
                .perform(put("/api/products/1")
                        .with(user("user")
                                .password("test")
                                .roles("USER")
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testCommand))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void delete_whenRoleUser_403() throws Exception {
        this.mockMvc
                .perform(delete("/api/products/1")
                        .with(user("user")
                                .password("test")
                                .roles("USER")
                        )
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void delete_whenRoleAdmin_204() throws Exception {
        this.mockMvc
                .perform(delete("/api/products/1")
                        .with(user("adminko")
                                .password("test")
                                .roles("ADMIN")
                        )
                )
                .andExpect(status().isNoContent());
    }


}