package ru.koryruno.springbootaopt1.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.koryruno.springbootaopt1.exception.ApplicationException;
import ru.koryruno.springbootaopt1.model.OrderDetails;
import ru.koryruno.springbootaopt1.model.requestDto.NewUserDto;

import java.util.regex.Pattern;

@Component
@Aspect
@Slf4j
@Order(3)
public class ValidateAspect {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    @Pointcut("execution(public * create*(..))")
    public void addMethodPointcut() {}

    @Pointcut("args(user,..)")
    public void userArgsPointcut(NewUserDto user) {}

    @Pointcut("args(order,..)")
    public void orderArgsPointcut(OrderDetails order) {}

    @Before(value = "addMethodPointcut() && userArgsPointcut(user)", argNames = "user")
    public void validateUser(NewUserDto user) {
        log.info("Валидация пользователя перед вызовом метода");
        validateUserFields(user);
    }

    @Before(value = "addMethodPointcut() && orderArgsPointcut(order)", argNames = "order")
    public void validateOrder(OrderDetails order) {
        log.info("Валидация заказа перед вызовом метода");
        validateOrderFields(order);
    }

    private void validateUserFields(NewUserDto user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new ApplicationException("Имя пользователя не может быть пустым");
        }
        if (user.getEmail() == null || !EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new ApplicationException("Неверный формат email");
        }
    }

    private void validateOrderFields(OrderDetails order) {
        if (order.getDescription() == null || order.getDescription().isEmpty()) {
            throw new ApplicationException("Описание не может быть пустым");
        }
        if (order.getInitiator() == null) {
            throw new ApplicationException("Заказ не может быть без инициатора");
        }
    }

}
