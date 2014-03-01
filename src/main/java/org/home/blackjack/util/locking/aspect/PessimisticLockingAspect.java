package org.home.blackjack.util.locking.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.home.blackjack.util.locking.FinegrainedLockable;
import org.home.blackjack.util.locking.LockTemplate;
import org.home.blackjack.util.locking.VoidWriteLockingAction;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

import com.google.common.collect.Lists;

@Aspect
public class PessimisticLockingAspect implements ApplicationContextAware {

	private static Logger LOGGER = Logger.getLogger(PessimisticLockingAspect.class);

	private static final LockTemplate LOCK_TEMPLATE = new LockTemplate();

	private ApplicationContext applicationContext;

	@SuppressWarnings("unchecked")
	@Around("@annotation(org.home.blackjack.util.locking.aspect.WithPessimisticLock)")
	public Object withLock(final ProceedingJoinPoint pjp) throws Throwable {

		WithPessimisticLock withLockAnnotation = getWithLockAnnotation(pjp);
		FinegrainedLockable<Object> repository = (FinegrainedLockable<Object>) applicationContext.getBean(withLockAnnotation.repository());

		Object key = getKey(pjp);

		System.out.println("LOCK acquiring attempt with key " + key);

		final List<Object> proceedResult = Lists.newArrayList();
		LOCK_TEMPLATE.doWithLock(repository, key, new VoidWriteLockingAction<Object>() {

			@Override
			public void withWriteLock(Object key) {
				try {
					proceedResult.add(pjp.proceed());
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		
		System.out.println("LOCK released for key " + key);

		return proceedResult.get(0);
	}

	private Object getKey(final ProceedingJoinPoint pjp) throws NoSuchMethodException {
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		Class<?> clazz = methodSignature.getDeclaringType();
		Method method = clazz.getDeclaredMethod(methodSignature.getName(), methodSignature.getParameterTypes());

		int lockParameterIndex = -1;
		for (int i = 0; i < method.getParameterAnnotations()[0].length; i++) {
			Annotation ann = method.getParameterAnnotations()[0][i];

			if (LockVal.class.isInstance(ann)) {
				lockParameterIndex = i;
				break;
			}
		}

		return pjp.getArgs()[lockParameterIndex];
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

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
