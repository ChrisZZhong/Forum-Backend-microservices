package com.forum.userservice.service;

import com.forum.userservice.dao.UserDAO;
import com.forum.userservice.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserDAO userDao;

    @InjectMocks
    private UserService userService;
    
    @Test
    void loadAllUsers() {
        List<User> res = new ArrayList<>();
        res.add(User.builder().userId(1).build());
        Mockito.when(userDao.getAllUsers()).thenReturn(res);
        assertEquals(res, userService.loadAllUsers());
    }
    
    @Test
    void getUserByUsername() {
        Optional<User> expected = Optional.of(User.builder()
                .userId(4)
                .firstname("sss")
                .lastname("sss")
                .email("sss")
                .password("sss")
                .active(true)
                .dateJoined(LocalDateTime.of(2023,7,25, 21,50,43))
                .type("normal")
                .profileImageURL("sss").build());
        Mockito.when(userDao.getUserByEmail("sss")).thenReturn(expected);
        assertEquals(expected.get(), userService.getUserByUsername("sss"));
    }
    
    @Test
    void getUserById() {
        User expected = User.builder()
                .userId(4)
                .firstname("sss")
                .lastname("sss")
                .email("sss")
                .password("sss")
                .active(true)
                .dateJoined(LocalDateTime.of(2023,7,25, 21,50,43))
                .type("normal")
                .profileImageURL("sss").build();
        
        Mockito.when(userDao.findById(4)).thenReturn(expected);
        assertEquals(expected, userService.getUserById(4));
        System.out.println("test success");
    }
    
    @Test
    void addNewUser() {
        User expected = User.builder()
                .userId(5)
                .firstname("sss")
                .lastname("sss")
                .email("sss@xx.com")
                .password("sss")
                .active(true)
                .dateJoined(LocalDateTime.of(2023,7,25, 21,50,43))
                .type("normal")
                .profileImageURL("sss").build();
        Mockito.when(userDao.getUserByEmail("sss@xx.com"))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(expected));
        assertEquals(expected, userService.addNewUser(expected));
        System.out.println("test success");
    }
    
    @Test
    void updateUserType() {
        User expected = User.builder()
                .userId(4)
                .firstname("sss")
                .lastname("sss")
                .email("sss")
                .password("sss")
                .active(true)
                .dateJoined(LocalDateTime.of(2023,7,25, 21,50,43))
                .type("normal")
                .profileImageURL("sss").build();
        Mockito.when(userDao.getUserByEmail("sss")).thenReturn(Optional.of(expected));
        assertEquals(expected, userService.updateUserType("sss", "unverified"));
        System.out.println("test success");
    }
    
    @Test
    void changeBanStatus() {
        User expected = User.builder()
                .userId(4)
                .firstname("sss")
                .lastname("sss")
                .email("sss")
                .password("sss")
                .active(true)
                .dateJoined(LocalDateTime.of(2023,7,25, 21,50,43))
                .type("normal")
                .profileImageURL("sss").build();
        Mockito.when(userDao.getUserByEmail("sss")).thenReturn(Optional.of(expected));
        assertEquals(expected, userService.changeBanStatus("sss"));
        System.out.println("test success");
    }
    
    @Test
    void changeAdminStatus() {
        User expected = User.builder()
                .userId(4)
                .firstname("sss")
                .lastname("sss")
                .email("sss")
                .password("sss")
                .active(true)
                .dateJoined(LocalDateTime.of(2023,7,25, 21,50,43))
                .type("normal")
                .profileImageURL("sss").build();
        Mockito.when(userDao.getUserByEmail("sss")).thenReturn(Optional.of(expected));
        assertEquals(expected, userService.changeAdminStatus("sss"));
        System.out.println("test success");
    }
    
    @Test
    void updateUserEmail() {
        User expected = User.builder()
                .userId(4)
                .firstname("sss")
                .lastname("sss")
                .email("sss")
                .password("sss")
                .active(true)
                .dateJoined(LocalDateTime.of(2023,7,25, 21,50,43))
                .type("normal")
                .profileImageURL("sss").build();
        Mockito.when(userDao.getUserByEmail("sss"))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(expected));
        assertEquals(expected, userService.updateUserEmail("sss","sss"));
        System.out.println("test success");
    }
    
    @Test
    void loadUsersByIdList() {
        User expected = User.builder()
                .userId(4)
                .firstname("sss")
                .lastname("sss")
                .profileImageURL("sss").build();
        List<User> res = new ArrayList<>();
        res.add(expected);
        List<Integer> idlist = new ArrayList<>();
        idlist.add(4);
        Mockito.when(userDao.findById(4)).thenReturn(expected);
        assertEquals(res.get(0).getUserId(), userService.loadUsersByIdList(idlist).get(0).getUserId());
        System.out.println("test success");
    }
    
    @Test
    void updateUserImageURL() {
        User expected = User.builder()
                .userId(4)
                .firstname("sss")
                .lastname("sss")
                .email("sss")
                .password("sss")
                .active(true)
                .dateJoined(LocalDateTime.of(2023,7,25, 21,50,43))
                .type("normal")
                .profileImageURL("sss").build();
        Mockito.when(userDao.findById(4)).thenReturn(expected);
        assertEquals(expected, userService.updateUserImageURL(4, "xxx"));
        System.out.println("test success");
    }
    
    @Test
    void setUserDAO() {
        assertTrue(true);
    }

//    @Test
//    void test_getProductById_failed() {
//        Mockito.when(userDao.findById(-1)).thenReturn(null);
//        assertThrows(InvalidCredentialsException.class, () -> userService.getUserById(-1));
//    }
//
//    @Test
//    void test_getAllProducts_success() {
//        List<Product> expected = new ArrayList<>();
//        expected.add(new Product(1, 1, "cosmic donut", 9.99));
//        expected.add(new Product(2, 1, "rick rolls", 2.99));
//        expected.add(new Product(3, 1, "cyber cookie", 4.04));
//        Mockito.when(productDao.getAllProducts()).thenReturn(expected);
//        assertEquals(expected, productService.getAllProducts());
//    }
//
//    @Test
//    void test_getAllProducts_successWhenEmpty() {
//        List<Product> expected = new ArrayList<>();
//        Mockito.when(productDao.getAllProducts()).thenReturn(expected);
//        assertEquals(expected, productService.getAllProducts());
//    }
//
//    @Test
//    void test_saveProduct_success() throws DuplicateProductIdException, InvalidCategoryException {
//        Product expected = new Product(
//                9,
//                1,
//                "chocolate bread",
//                100.0);
//
//        // check if the product is valid
//        Mockito.when(productDao.isProductIdValid(expected.getId())).thenReturn(true);
//        Mockito.when(productDao.isProductCategoryValid(expected.getCategory())).thenReturn(true);
//
//        // add the product
//        Mockito.when(productDao.addProduct(expected)).thenReturn(expected);
//        Product actual = productService.saveProduct(expected);
//
//        // check if the product is added
//        Mockito.verify(productDao, Mockito.times(1)).addProduct(expected);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void test_saveProduct_failedOnDuplicateId() {
//        Product expected = new Product(
//                2, // duplicate id
//                1,
//                "chocolate bread",
//                100.0);
//
//        Mockito.when(productDao.isProductIdValid(expected.getId())).thenReturn(false);
//        assertThrows(DuplicateProductIdException.class, () -> productService.saveProduct(expected));
//    }
//
//    @Test
//    void test_saveProduct_failedOnInvalidCategory() {
//        Product expected = new Product(
//                9,
//                999, // invalid category
//                "chocolate bread",
//                100.0);
//
//        Mockito.when(productDao.isProductIdValid(expected.getId())).thenReturn(true);
//        Mockito.when(productDao.isProductCategoryValid(expected.getCategory())).thenReturn(false);
//        assertThrows(InvalidCategoryException.class, () -> productService.saveProduct(expected));
//    }

}
