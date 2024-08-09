package ru.koryruno.springbootaopt1.aspectTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import ru.koryruno.springbootaopt1.aspect.ValidateAspect;
import ru.koryruno.springbootaopt1.exception.ApplicationException;
import ru.koryruno.springbootaopt1.model.User;
import ru.koryruno.springbootaopt1.model.requestDto.NewOrderDto;
import ru.koryruno.springbootaopt1.model.requestDto.NewUserDto;
import ru.koryruno.springbootaopt1.model.requestDto.UpdateOrderDto;
import ru.koryruno.springbootaopt1.model.requestDto.UpdateUserDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidateAspectTest {

    @InjectMocks
    private ValidateAspect validateAspect;

    // Valid
    private final NewUserDto newUser = NewUserDto.builder().name("user").email("user@user.ru").build();
    private final UpdateUserDto updateUser  = UpdateUserDto.builder().name("updatedName").build();
    private final NewOrderDto newOrder = NewOrderDto.builder().description("orderDescription").build();
    private final NewOrderDto newOrderWithInvalidDescription = NewOrderDto.builder().description(null).build();
    private final UpdateOrderDto updateOrder = UpdateOrderDto.builder().description("updatedOrderDescription").status("PROCESSING").build();

    // Invalid
    private final NewUserDto newUserWithInvalidName = NewUserDto.builder().name(null).email("user@user.ru").build();
    private final NewUserDto newUserWithInvalidEmail = NewUserDto.builder().name("user").email("@uuuuuuu").build();
    private final UpdateUserDto updateUserWithInvalidName  = UpdateUserDto.builder().name(null).build();
    private final UpdateOrderDto updateOrderWithInvalidDescription = UpdateOrderDto.builder().description(null).status("PROCESSING").build();
    private final UpdateOrderDto updateUpdateOrderWithInvalidStatus = UpdateOrderDto.builder().description("updatedOrderDescription").status(null).build();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateCreateUser_withValidFields_Successfully() {
        validateAspect.validateUser(newUser);
    }

    @Test
    void validateCreateUser_withInvalidName_Throws() {
        ApplicationException thrown = assertThrows(ApplicationException.class, () -> {
            validateAspect.validateUser(newUserWithInvalidName);
        });
        assertEquals("Username cannot be empty", thrown.getMessage());
    }

    @Test
    void validateCreateUser_withInvalidEmail_Throws() {
        ApplicationException thrown = assertThrows(ApplicationException.class, () -> {
            validateAspect.validateUser(newUserWithInvalidEmail);
        });
        assertEquals("Invalid email format", thrown.getMessage());
    }

    @Test
    void validateUpdateUser_withValidFields_Successfully() {
        validateAspect.validateUpdateUser(updateUser);
    }

    @Test
    void validateUpdateUser_withInvalidName_Throws() {
        ApplicationException thrown = assertThrows(ApplicationException.class, () -> {
            validateAspect.validateUpdateUser(updateUserWithInvalidName);
        });
        assertEquals("Username cannot be empty", thrown.getMessage());
    }

    @Test
    void validateCreateOrder_withValidFields_Successfully() {
        validateAspect.validateOrder(newOrder);
    }

    @Test
    void validateCreateOrder_withInvalidDescription_Throws() {
        ApplicationException thrown = assertThrows(ApplicationException.class, () -> {
            validateAspect.validateOrder(newOrderWithInvalidDescription);
        });
        assertEquals("Description cannot be empty", thrown.getMessage());
    }

    @Test
    void validateUpdateOrder_withValidFields_Successfully() {
        validateAspect.validateUpdateOrder(updateOrder);
    }

    @Test
    void validateUpdateOrder_withInvalidStatus_Throws() {
        ApplicationException thrown = assertThrows(ApplicationException.class, () -> {
            validateAspect.validateUpdateOrder(updateUpdateOrderWithInvalidStatus);
        });
        assertEquals("Order status cannot be empty", thrown.getMessage());
    }

    @Test
    void validateUpdateOrder_withInvalidDescription_Throws() {
        ApplicationException thrown = assertThrows(ApplicationException.class, () -> {
            validateAspect.validateUpdateOrder(updateOrderWithInvalidDescription);
        });
        assertEquals("Description cannot be empty", thrown.getMessage());
    }

}
