package com.forum.userservice.controller;

import com.forum.userservice.domain.User;
import com.forum.userservice.dto.response.UserResponse;
import com.forum.userservice.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.xml.ws.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;


@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test_getUserById() throws Exception {
        User expected = User.builder().userId(1).firstname("bn").lastname("z").email("123@gmail.com").active(true).build();
        Mockito.when(userService.getUserById(1)).thenReturn(expected);
        
        // 1st way - using andExpect
        mockMvc.perform(MockMvcRequestBuilders.get("/user/1"))
                .andExpect(MockMvcResultMatchers.status().isOk()); // status code 200
    }

        // 2nd way - using assertEquals
//        MvcResult result = mockMvc
//                .perform(MockMvcRequestBuilders.get("/product/{id}", "1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
//        // use Gson to convert the JSON response to a Product object
//        Gson gson = new Gson();
//        Product actual = gson.fromJson(result.getResponse().getContentAsString(), Product.class);
//        assertEquals(expected.toString(), actual.toString());

//
//    @Test
//    void test_saveProduct() throws Exception {
//        Product expected = new Product(99, 1, "cosmic donut", 9.99);
//        Mockito.when(productService.saveProduct(Mockito.isA(Product.class))).thenReturn(expected);
//
//        Gson gson = new Gson();
//        String expectedJson = gson.toJson(expected);
//        System.out.println(expectedJson);
//
//        // 1st way - using andExpect
//        mockMvc.perform(MockMvcRequestBuilders.post("/product")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(expectedJson))
//                .andExpect(MockMvcResultMatchers.status().isOk()) // status code 200
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.content().json(expectedJson));

        // 2nd way - using assertEquals
//        MvcResult result = mockMvc
//                .perform(MockMvcRequestBuilders.post("/product")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(expectedJson))
//                .andReturn();
//        // use Gson to convert the JSON response to a Product object
//        Product actual = gson.fromJson(result.getResponse().getContentAsString(), Product.class);
//        assertEquals(expected.toString(), actual.toString());
    @Test
    void test_updateUserById() throws Exception {
        User expected = User.builder().userId(1).firstname("tt").lastname("tt").email("tt@gmail.com").active(true).build();
        Mockito.when(userService.updateUserImageURL(1, "test")).thenReturn(expected);
        // 2nd way - using assertEquals
//        MvcResult result = mockMvc
//                .perform(MockMvcRequestBuilders.get("/product/{id}", "1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
//        // use Gson to convert the JSON response to a Product object
//        Gson gson = new Gson();
//        Product actual = gson.fromJson(result.getResponse().getContentAsString(), Product.class);
//        assertEquals(expected.toString(), actual.toString());
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.put("/user/1")
                        .contentType("application/json")
                        .content("testURL"))
                .andReturn();
        String actual = result.getResponse().getContentAsString();

        Gson gson = new Gson();
        ResponseEntity<UserResponse> expectedResponse = ResponseEntity.ok(UserResponse.builder()
                .status("OK")
                .message("update user type successfully")
                .user(expected).build());
        assertEquals(gson.toJson(expectedResponse.getBody()), actual);

    }
}
