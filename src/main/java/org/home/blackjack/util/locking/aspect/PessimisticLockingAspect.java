package org.home.blackjack.util.locking.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.home.blackjack.util.locking.FinegrainedLockable;
import org.home.blackjack.util.locking.LockTemplate;
import org.home.blackjack.util.locking.VoidWriteLockingAction;
import org.junit.Assert;
import org.springframework.core.annotation.AnnotationUtils;

@Aspect
public class PessimisticLockingAspect {

    private static Logger LOGGER = Logger.getLogger(PessimisticLockingAspect.class);
    
    private static final LockTemplate LOCK_TEMPLATE = new LockTemplate();

    @Around("@annotation(org.home.blackjack.util.locking.optimistic.WithLock)")
    public Object withLock(final ProceedingJoinPoint pjp) throws Throwable {
        WithPessimisticLock withLockAnnotation = getWithLockAnnotation(pjp);
        
        FinegrainedLockable<Object> repository = getRepository(withLockAnnotation.repository());
        Object key = pjp.getArgs()[0];
        LOCK_TEMPLATE.doWithLock(repository, key, new VoidWriteLockingAction<Object>() {

			public void withWriteLock(Object key) {
				try {
					pjp.proceed();
				} catch (Throwable e) {
					throw new RuntimeException(e);
				}
			}
		});
        
        return (withLockAnnotation != null) ? proceed(pjp, withLockAnnotation) : proceed(pjp);
    }

    private FinegrainedLockable<Object> getRepository(String repositoryBeanName) {
		// TODO Auto-generated method stub
		return null;
	}

	private WithPessimisticLock getWithLockAnnotation(ProceedingJoinPoint pjp) throws NoSuchMethodException {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        WithPessimisticLock withLockAnnotation = AnnotationUtils.findAnnotation(method, WithPessimisticLock.class);

        if (withLockAnnotation != null) {
            return withLockAnnotation;
        }

        Class[] argClasses = new Class[pjp.getArgs().length];
        for (int i = 0; i < pjp.getArgs().length; i++) {
            argClasses[i] = pjp.getArgs()[i].getClass();
        }
        method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), argClasses);
        return AnnotationUtils.findAnnotation(method, WithPessimisticLock.class);
    }

    private Object proceed(ProceedingJoinPoint pjp) throws Throwable {

        return pjp.proceed();

    }

    private Object proceed(ProceedingJoinPoint pjp, WithPessimisticLock withLockAnnotation) throws Throwable {

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
