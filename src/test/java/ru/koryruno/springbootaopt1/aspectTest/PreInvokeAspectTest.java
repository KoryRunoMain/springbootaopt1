package ru.koryruno.springbootaopt1.aspectTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cglib.proxy.Proxy;
import ru.koryruno.springbootaopt1.annotation.PreInvoke;
import ru.koryruno.springbootaopt1.aspect.PreInvokeAspect;
import ru.koryruno.springbootaopt1.exception.ApplicationException;
import ru.koryruno.springbootaopt1.model.RoleType;
import ru.koryruno.springbootaopt1.utils.UserContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PreInvokeAspectTest {

    @InjectMocks
    private PreInvokeAspect preInvokeAspect;

    @Mock
    private UserContext userContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBefore_ValidRole() {
        UserContext.setUSERNAME("admin");

        PreInvoke preInvoke = (PreInvoke) Proxy.newProxyInstance(
                PreInvoke.class.getClassLoader(),
                new Class<?>[]{PreInvoke.class},
                (proxy, method, args) -> {
                    if ("roles".equals(method.getName())) {
                        return new RoleType[]{RoleType.USER};
                    }
                    return null;
                }
        );

        preInvokeAspect.before(preInvoke);
    }

    @Test
    void testBefore_InvalidRole() {
        UserContext.setUSERNAME("user");

        PreInvoke preInvoke = (PreInvoke) Proxy.newProxyInstance(
                PreInvoke.class.getClassLoader(),
                new Class<?>[]{PreInvoke.class},
                (proxy, method, args) -> {
                    if ("roles".equals(method.getName())) {
                        return new RoleType[]{RoleType.ADMIN}; // Неверная роль
                    }
                    return null;
                }
        );

        ApplicationException thrown = assertThrows(ApplicationException.class, () -> {
            preInvokeAspect.before(preInvoke);
        });

        assertEquals("Access Denied. Roles: [USER]", thrown.getMessage());
    }

    @Test
    void testBefore_UserNotFound() {
        UserContext.setUSERNAME("unknown");

        PreInvoke preInvoke = (PreInvoke) Proxy.newProxyInstance(
                PreInvoke.class.getClassLoader(),
                new Class<?>[]{PreInvoke.class},
                (proxy, method, args) -> {
                    if ("roles".equals(method.getName())) {
                        return new RoleType[]{RoleType.USER}; // Ожидаемая роль
                    }
                    return null;
                }
        );

        ApplicationException thrown = assertThrows(ApplicationException.class, () -> {
            preInvokeAspect.before(preInvoke);
        });

        assertEquals("User is not found: unknown", thrown.getMessage());
    }

}
