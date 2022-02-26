package backend.core.global.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* backend.core.post..*(..))")
    public void allPost() {
    }

    @Pointcut("execution(* backend.core.member..*(..))")
    public void allMember() {
    }

    @Pointcut("execution(* backend.core..*Controller.*(..))")
    public void allController() {
    }

    @Pointcut("execution(* backend.core..*Service.*(..))")
    public void allService() {
    }

    @Pointcut("execution(* backend.core..*Repository.*(..))")
    public void allRepository() {
    }

    @Pointcut("allService() || allController() || allRepository()")
    public void all() {
    }

    @Pointcut("allPost() || allMember()")
    public void allPostOrMember() {
    }
}
