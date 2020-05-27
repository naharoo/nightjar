package club.thewhitewall.nightjar.infra.logging;

import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Aspect
@Component
@Order(HIGHEST_PRECEDENCE)
public class LoggerAspect {

    private static final String STARTING_MESSAGE = "Starting %s method's execution with following args:'%s'";
    private static final String FINISHING_MESSAGE = "Finished %s method's execution with following result:'%s' in %dms.";
    private static final String FINISHING_MESSAGE_SHORT = "Finished %s methods execution in %dms.";

    private final Map<Class<?>, Logger> loggerMap = new ConcurrentHashMap<>();

    private static void log(final Logger logger, final LogLevel level, final String message) {
        switch (level) {
            case TRACE: {
                logger.trace(message);
                break;
            }
            case DEBUG: {
                logger.debug(message);
                break;
            }
            case INFO: {
                logger.info(message);
                break;
            }
            case WARN: {
                logger.warn(message);
                break;
            }
            case ERROR: {
                logger.error(message);
                break;
            }
            case FATAL:
            case OFF: {
                break;
            }
        }
    }

    @Around("@annotation(club.thewhitewall.nightjar.infra.logging.Loggable)")
    public Object advice(final ProceedingJoinPoint joinPoint) throws Throwable {
        final Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        final String methodName = method.getName();
        final Class<?> declaringClass = method.getDeclaringClass();

        final Logger logger = loggerMap.computeIfAbsent(
                declaringClass,
                classFullQualifiedName -> LoggerFactory.getLogger(declaringClass)
        );

        final Loggable loggable = method.getAnnotation(Loggable.class);

        final StopWatch stopWatch = StopWatch.createStarted();
        log(
                logger,
                loggable.before(),
                String.format(STARTING_MESSAGE, methodName, Arrays.toString(joinPoint.getArgs()))
        );

        final Object result = joinPoint.proceed();

        stopWatch.stop();
        final LogLevel after = loggable.after();
        final String finishingMessage =
                LogLevel.INFO.equals(after)
                ? String.format(FINISHING_MESSAGE_SHORT, methodName, stopWatch.getTime())
                : String.format(FINISHING_MESSAGE, methodName, result, stopWatch.getTime());
        log(logger, after, finishingMessage);

        return result;
    }
}
