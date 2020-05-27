package club.thewhitewall.nightjar.infra.validation.params;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.*;
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

    private Validator validator;
    private ExecutableValidator executableValidator;

    @PostConstruct
    private void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        executableValidator = validator.forExecutables();
    }

    @Before("@annotation(club.thewhitewall.nightjar.infra.validation.params.ValidParams)")
    public void advice(final JoinPoint joinPoint) {
        final Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        final Object[] args = joinPoint.getArgs();

        final Set<ConstraintViolation<?>> constraintViolations = new HashSet<>();

        final Parameter[] parameters = method.getParameters();
        if (parameters != null) {
            constraintViolations.addAll(executableValidator.validateParameters(joinPoint.getTarget(), method, args));

            for (int i = 0; i < parameters.length; i++) {
                final Parameter parameter = parameters[i];
                final Object arg = args[i];
                if (parameter.isAnnotationPresent(Valid.class) && arg != null) {
                    constraintViolations.addAll(validator.validate(arg));
                }
            }
        }

        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
