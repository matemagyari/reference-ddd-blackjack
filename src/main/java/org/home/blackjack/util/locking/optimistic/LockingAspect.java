package org.home.blackjack.util.locking.optimistic;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.Assert;
import org.springframework.core.annotation.AnnotationUtils;

@Aspect
public class LockingAspect {

    private static Logger LOGGER = Logger.getLogger(LockingAspect.class);

    @Around("@annotation(org.home.blackjack.util.locking.optimistic.WithLock)")
    public Object withLock(ProceedingJoinPoint pjp) throws Throwable {
        WithLock withLockAnnotation = getWithLockAnnotation(pjp);
        return (withLockAnnotation != null) ? proceed(pjp, withLockAnnotation) : proceed(pjp);
    }

    private WithLock getWithLockAnnotation(ProceedingJoinPoint pjp) throws NoSuchMethodException {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        WithLock withLockAnnotation = AnnotationUtils.findAnnotation(method, WithLock.class);

        if (withLockAnnotation != null) {
            return withLockAnnotation;
        }

        Class[] argClasses = new Class[pjp.getArgs().length];
        for (int i = 0; i < pjp.getArgs().length; i++) {
            argClasses[i] = pjp.getArgs()[i].getClass();
        }
        method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), argClasses);
        return AnnotationUtils.findAnnotation(method, WithLock.class);
    }

    private Object proceed(ProceedingJoinPoint pjp) throws Throwable {

        return pjp.proceed();

    }

    private Object proceed(ProceedingJoinPoint pjp, WithLock withLockAnnotation) throws Throwable {

        int times = withLockAnnotation.times();

        Class<? extends Throwable>[] withLockOn = withLockAnnotation.on();

        Assert.assertTrue("@WithLock{times} should be greater than 0!",times > 0);

        Assert.assertTrue("@WithLock{on} should have at least one Throwable!", withLockOn.length > 0 );

        LOGGER.info("Proceed with "+times+" retries on " + Arrays.toString(withLockOn) );

        return tryProceeding(pjp, times, withLockOn);

    }

    private Object tryProceeding(ProceedingJoinPoint pjp, int times, Class<? extends Throwable>[] withLockOn) throws Throwable {

        try {

            return proceed(pjp);

        } catch (Throwable throwable) {

            if (isWithLockThrowable(throwable, withLockOn) && times-- > 0) {

                LOGGER.info("Optimistic locking detected, "+times+" remaining retries on " + Arrays.toString(withLockOn) );

                tryProceeding(pjp, times, withLockOn);

            }

            throw throwable;

        }

    }

    private boolean isWithLockThrowable(Throwable throwable, Class<? extends Throwable>[] withLockOn) {

        Throwable[] causes = ExceptionUtils.getThrowables(throwable);
        for (Throwable cause : causes) {
            for (Class<? extends Throwable> withLockThrowable : withLockOn) {
                if (withLockThrowable.isAssignableFrom(cause.getClass())) {
                    return true;
                }
            }
        }
        return false;

    }

}
