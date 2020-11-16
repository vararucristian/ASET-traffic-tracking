package Aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class Loggable {

    @Before("execution(* Handlers.HandlerFactory.createHandler(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Create Handler!");
    }

    @After("execution(* Handlers.HandlerFactory.createHandler(..))")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("Handler Created!");
    }

}
