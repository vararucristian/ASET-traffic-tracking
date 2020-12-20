package Aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class Loggable {

    @Before("execution(* Handlers.GetTrafficLightsByIntersectionIdHandler.handle())")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("handle running");
        System.out.println(joinPoint.getSignature().getName());
    }

    @After("execution(* Handlers.GetTrafficLightsByIntersectionIdHandler.handle())")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("handle finished");
        System.out.println(joinPoint.getSignature().getName());
    }

}
