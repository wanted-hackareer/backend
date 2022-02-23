package backend.core.controller.security.auth;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithAuthMemberSecurityContextFactory.class)
public @interface WithAuthMember {
    String email() default "test@gmail.com";

    String name() default "test";

    String password() default "password";
}
