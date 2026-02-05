//package com.controller;
//
//import com.dto.*;
//import com.entity.Users;
//import com.service.AuthService;
//import com.service.JwtService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.FilterType;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.security.Principal;
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.doNothing;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(
//        controllers = AuthController.class,
//        excludeFilters = @ComponentScan.Filter(
//                type = FilterType.ASSIGNABLE_TYPE,
//                classes = com.config.JwtFilter.class
//        )
//)
//@AutoConfigureMockMvc(addFilters = false)
//public class AuthControllerIT {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private AuthService authService;
//
//    @MockBean
//    private JwtService jwtService;
//
//    private Principal principalWithId(Long id) {
//        return () -> String.valueOf(id);
//    }
//
//    @Test
//    void register_success() throws Exception {
//        RegisterRequest req = new RegisterRequest();
//        req.setEmail("user@example.com");
//        req.setPassword("Password123!");
//        req.setFullName("Test User");
//        req.setMobileNumber("9876543210");
//
//        AuthResponse resp = new AuthResponse("token123", 10L, "user@example.com", "ROLE_CUSTOMER");
//        given(authService.register(any(RegisterRequest.class))).willReturn(resp);
//
//        mockMvc.perform(post("/api/v1/auth/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.accessToken").value("token123"))
//                .andExpect(jsonPath("$.userId").value(10))
//                .andExpect(jsonPath("$.email").value("user@example.com"))
//                .andExpect(jsonPath("$.role").value("ROLE_CUSTOMER"));
//    }
//
//    @Test
//    void login_success() throws Exception {
//        LoginRequest req = new LoginRequest();
//        req.setEmail("user@example.com");
//        req.setPassword("Password123!");
//        AuthResponse resp = new AuthResponse("tokenLogin", 10L, "user@example.com", "ROLE_CUSTOMER");
//        given(authService.login(any(LoginRequest.class))).willReturn(resp);
//
//        mockMvc.perform(post("/api/v1/auth/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.accessToken").value("tokenLogin"))
//                .andExpect(jsonPath("$.userId").value(10))
//                .andExpect(jsonPath("$.email").value("user@example.com"));
//    }
//
//    @Test
//    void profile_success() throws Exception {
//        Long userId = 42L;
//        Users u = new Users();
//        u.setId(userId);
//        u.setEmail("john@example.com");
//        u.setFullName("John Doe");
//        u.setMobileNumber("9123456780");
//        u.setRole("ROLE_CUSTOMER");
//        u.setIsActive(true);
//        u.setCreatedAt(LocalDateTime.now());
//        u.setUpdatedAt(LocalDateTime.now());
//        given(authService.findById(userId)).willReturn(Optional.of(u));
//
//        mockMvc.perform(get("/api/v1/auth/profile")
//                .principal(principalWithId(userId)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(userId.intValue()))
//                .andExpect(jsonPath("$.email").value("john@example.com"))
//                .andExpect(jsonPath("$.fullName").value("John Doe"))
//                .andExpect(jsonPath("$.mobileNumber").value("9123456780"))
//                .andExpect(jsonPath("$.role").value("ROLE_CUSTOMER"))
//                .andExpect(jsonPath("$.isActive").value(true));
//    }
//
//    @Test
//    void profile_unauthorized() throws Exception {
//        mockMvc.perform(get("/api/v1/auth/profile")).andExpect(status().isUnauthorized());
//    }
//
//    @Test
//    void updateProfile_success() throws Exception {
//        Long userId = 42L;
//        UpdateProfileRequest req = new UpdateProfileRequest();
//        req.setFullName("New Name");
//        req.setMobileNumber("9988776655");
//
//        Users updated = new Users();
//        updated.setId(userId);
//        updated.setEmail("john@example.com");
//        updated.setFullName("New Name");
//        updated.setMobileNumber("9988776655");
//        updated.setRole("ROLE_CUSTOMER");
//        updated.setIsActive(true);
//        updated.setCreatedAt(LocalDateTime.now());
//        updated.setUpdatedAt(LocalDateTime.now());
//        given(authService.updateProfile(eq(userId), any(UpdateProfileRequest.class))).willReturn(updated);
//
//        mockMvc.perform(put("/api/v1/auth/profile")
//                .principal(principalWithId(userId))
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.fullName").value("New Name"))
//                .andExpect(jsonPath("$.mobileNumber").value("9988776655"));
//    }
//
//    @Test
//    void changePassword_success() throws Exception {
//        Long userId = 42L;
//        ChangePasswordRequest req = new ChangePasswordRequest();
//        req.setOldPassword("OldPass123!");
//        req.setNewPassword("NewPass456!");
//        doNothing().when(authService).changePassword(eq(userId), any(ChangePasswordRequest.class));
//
//        mockMvc.perform(put("/api/v1/auth/profile/password")
//                .principal(principalWithId(userId))
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isNoContent());
//    }
//}
