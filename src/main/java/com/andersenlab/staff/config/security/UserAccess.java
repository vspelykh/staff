package com.andersenlab.staff.config.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("""
    hasAnyRole(
        T(com.andersenlab.staff.config.security.UserRole).USER.name()
    )
    """)
public @interface UserAccess {

}
