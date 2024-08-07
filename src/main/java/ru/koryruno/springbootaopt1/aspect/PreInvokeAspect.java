package ru.koryruno.springbootaopt1.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.koryruno.springbootaopt1.annotation.PreInvoke;
import ru.koryruno.springbootaopt1.exception.ApplicationException;
import ru.koryruno.springbootaopt1.model.RoleType;
import ru.koryruno.springbootaopt1.utils.UserContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Aspect
@Order(2)
public class PreInvokeAspect {

    private static final Map<String, List<RoleType>> USERS = new HashMap<>();

    static {
        USERS.put("admin", List.of(RoleType.ADMIN, RoleType.USER));
        USERS.put("user", List.of(RoleType.USER));
    }

    @Pointcut("@annotation(preInvoke)")
    public void checkRolePointcut(PreInvoke preInvoke) {
    }

    @Before("checkRolePointcut(preInvoke)")
    public void before(PreInvoke preInvoke) {
        String currentUser = UserContext.getUSERNAME();

        if (!USERS.containsKey(currentUser)) {
            throw new ApplicationException("User is not found: " + currentUser);
        }

        var roles = Arrays.stream(preInvoke.roles()).toList();
        var userRoles = USERS.get(currentUser);

        if (roles.stream().noneMatch(userRoles::contains)) {
            throw new ApplicationException("Access Denied. Roles: " + userRoles);
        }
    }

}
