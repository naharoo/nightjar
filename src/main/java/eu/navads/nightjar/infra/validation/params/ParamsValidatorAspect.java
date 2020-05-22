package eu.navads.nightjar.infra.validation.params;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Aspect
@Component
@Order(HIGHEST_PRECEDENCE + 10)
public class ParamsValidatorAspect {

    private ExecutableValidator executableValidator;

    @PostConstruct
    private void init() {
        executableValidator = Validation.buildDefaultValidatorFactory().getValidator().forExecutables();
    }

    @Before("@annotation(eu.navads.nightjar.infra.validation.params.ValidParams)")
    public void advice(final JoinPoint joinPoint) {
        final Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        final Object[] args = joinPoint.getArgs();

        final Set<ConstraintViolation<?>> constraintViolations = new HashSet<>();

        final Parameter[] parameters = method.getParameters();
        if (parameters != null) {
            constraintViolations.addAll(executableValidator.validateParameters(joinPoint.getTarget(), method, args));
        }

        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
