package org.nahuelrodriguez.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerLogger {
    private static final Logger logger = LoggerFactory.getLogger(ControllerLogger.class);

    @Around("execution(public * *(..)) && within(org.nahuelrodriguez.controllers..*)")
    public Object logControllerMethodExecution(ProceedingJoinPoint pjp) {
        if (logger.isInfoEnabled())
            logger.info("Before executing {} method", pjp.getSignature());

        Object retVal = null;

        try {
            retVal = pjp.proceed();

            if (logger.isInfoEnabled())
                logger.info("After executing {} method", pjp.getSignature());
        } catch (Throwable throwable) {
            if (logger.isErrorEnabled())
                logger.error("Method {} has failed with exception message {}",
                        pjp.getSignature(), throwable.getCause());
        }
        return retVal;
    }
}
