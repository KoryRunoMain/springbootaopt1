package ru.koryruno.springbootaopt1.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.koryruno.springbootaopt1.exception.ApplicationException;
import ru.koryruno.springbootaopt1.model.requestDto.NewOrderDto;
import ru.koryruno.springbootaopt1.model.requestDto.NewUserDto;
import ru.koryruno.springbootaopt1.model.requestDto.UpdateOrderDto;
import ru.koryruno.springbootaopt1.model.requestDto.UpdateUserDto;

import java.util.regex.Pattern;

@Component
@Aspect
@Slf4j
@Order(3)
public class ValidateAspect {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    @Pointcut("execution(public * ru.koryruno.springbootaopt1.controller.*.create*(..)) || " +
            "execution(public * ru.koryruno.springbootaopt1.controller.*.update*(..))")
    public void addAndUpdateMethodPointcut() {}

    @Pointcut("args(user,..)")
    public void userArgsPointcut(NewUserDto user) {}

    @Pointcut("args(updateUser,..)")
    public void updateUserArgsPointcut(UpdateUserDto updateUser) {}

    @Pointcut("args(order,..)")
    public void orderArgsPointcut(NewOrderDto order) {}

    @Pointcut("args(updateOrder,..)")
    public void updateOrderArgsPointcut(UpdateOrderDto updateOrder) {}

    @Before(value = "addAndUpdateMethodPointcut() && userArgsPointcut(user)", argNames = "user")
    public void validateUser(NewUserDto user) {
        log.info("Validate user before calling method");
        validateUserFields(user);
    }

    @Before(value = "addAndUpdateMethodPointcut() && updateUserArgsPointcut(updateUser)", argNames = "updateUser")
    public void validateUpdateUser(UpdateUserDto updateUser) {
        log.info("Validate user update before calling method");
        validateUpdateUserFields(updateUser);
    }

    @Before(value = "addAndUpdateMethodPointcut() && orderArgsPointcut(order)", argNames = "order")
    public void validateOrder(NewOrderDto order) {
        log.info("Validate order before calling method");
        validateOrderFields(order);
    }

    @Before(value = "addAndUpdateMethodPointcut() && updateOrderArgsPointcut(updateOrder)", argNames = "updateOrder")
    public void validateUpdateOrder(UpdateOrderDto updateOrder) {
        log.info("Validate order update before calling method");
        validateUpdateOrderFields(updateOrder);
    }

    private void validateUserFields(NewUserDto user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new ApplicationException("Username cannot be empty");
        }
        if (user.getEmail() == null || !EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new ApplicationException("Invalid email format");
        }
    }

    private void validateUpdateUserFields(UpdateUserDto updateUser) {
        if (updateUser.getName() == null || updateUser.getName().isEmpty()) {
            throw new ApplicationException("Username cannot be empty");
        }
    }

    private void validateOrderFields(NewOrderDto order) {
        if (order.getDescription() == null || order.getDescription().isEmpty()) {
            throw new ApplicationException("Description cannot be empty");
        }
    }

    private void validateUpdateOrderFields(UpdateOrderDto updateOrder) {
        if (updateOrder.getDescription() == null || updateOrder.getDescription().isEmpty()) {
            throw new ApplicationException("Description cannot be empty");
        }
        if (updateOrder.getStatus() == null || updateOrder.getStatus().isEmpty()) {
            throw new ApplicationException("Order status cannot be empty");
        }
    }

}
