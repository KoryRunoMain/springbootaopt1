package ru.koryruno.springbootaopt1.controllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.koryruno.springbootaopt1.controller.UserController;
import ru.koryruno.springbootaopt1.exception.ErrorHandler;
import ru.koryruno.springbootaopt1.model.requestDto.NewUserDto;
import ru.koryruno.springbootaopt1.model.requestDto.UpdateUserDto;
import ru.koryruno.springbootaopt1.model.responseDto.UserFullDto;
import ru.koryruno.springbootaopt1.service.UserService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class UserControllerTest {

    @Mock
    private UserService userService;
    @InjectMocks
    private UserController controller;

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Long USER_ID = 1L;
    private final NewUserDto newUserDto = new NewUserDto("user", "user@user.user");
    private final UserFullDto userFullDto = new UserFullDto(USER_ID, "user", "user@user.user");
    private final UpdateUserDto updatedUserDto = new UpdateUserDto("updatedUser");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ErrorHandler())
                .build();
    }

    @Test
    void testCreateUser_Success() throws Exception {
        when(userService.createUser(any(NewUserDto.class))).thenReturn(userFullDto);
        mvc.perform(post("/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateUser_Success() throws Exception {
        when(userService.updateUser(eq(USER_ID), any(UpdateUserDto.class))).thenReturn(userFullDto);
        mvc.perform(patch("/users/{userId}", USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUserDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUser_Success() throws Exception {
        when(userService.getUser(eq(USER_ID))).thenReturn(userFullDto);
        mvc.perform(get("/users/{userId}", USER_ID))
                .andExpect(status().isOk());

    }

    @Test
    void testGetAllUsers_Success() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(userFullDto));
        mvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUser_Success() throws Exception {
        mvc.perform(delete("/admin/users/{userId}", USER_ID))
                .andExpect(status().isNoContent());
    }

}
