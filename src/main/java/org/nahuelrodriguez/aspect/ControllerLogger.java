package org.nahuelrodriguez.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
class ControllerLogger {
    private static final Logger log = LoggerFactory.getLogger(ControllerLogger.class);

    @Around("execution(public * *(..)) && within(org.nahuelrodriguez.controller.*)")
    private Object logControllerMethodExecution(final ProceedingJoinPoint pjp) throws Throwable {
        log.debug("Executing method {}", pjp.getSignature());
        final Object retVal;

        try {
            retVal = pjp.proceed();
            log.debug("Method {} executed correctly", pjp.getSignature());
        } catch (final Throwable throwable) {
            log.error("Method {} has failed with exception message {}",
                    pjp.getSignature(),
                    throwable.getMessage());
            throw throwable;
        }
        return retVal;
    }
}

