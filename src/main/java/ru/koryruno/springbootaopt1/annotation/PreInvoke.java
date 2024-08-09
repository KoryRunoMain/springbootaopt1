package ru.koryruno.springbootaopt1.annotation;

import ru.koryruno.springbootaopt1.model.RoleType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PreInvoke {

    RoleType[] roles();

}
