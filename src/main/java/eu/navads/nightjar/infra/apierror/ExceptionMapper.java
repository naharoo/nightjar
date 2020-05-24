package eu.navads.nightjar.infra.apierror;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.navads.nightjar.exception.ResourceAlreadyExistsException;
import eu.navads.nightjar.exception.ResourceNotFoundException;
import eu.navads.nightjar.exception.ResourceNotViableException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@ControllerAdvice
public class ExceptionMapper {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorDto> handleConstraintViolationException(final ConstraintViolationException e) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        final ApiErrorDto dto = new ApiErrorDto(status.value(), List.of(e.getMessage()));
        log.info(dto.toString());
        return new ResponseEntity<>(dto, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDto> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;

        final Stream<String> firstSource = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format("%s: %s", error.getField(), error.getDefaultMessage()));
        final Stream<String> secondSource = exception
                .getBindingResult()
                .getGlobalErrors()
                .stream()
                .map(error -> String.format("%s: %s", error.getObjectName(), error.getDefaultMessage()));

        final List<String> errors = Stream
                .concat(firstSource, secondSource)
                .collect(Collectors.toList());

        final ApiErrorDto dto = new ApiErrorDto(status.value(), errors);
        log.info(dto.toString());
        return new ResponseEntity<>(dto, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handleResourceNotFoundException(final ResourceNotFoundException e) {
        final HttpStatus status = HttpStatus.NOT_FOUND;
        final ApiErrorDto dto = new ApiErrorDto(status.value(), List.of(e.getMessage()));
        log.info(dto.toString());
        return new ResponseEntity<>(dto, status);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiErrorDto> handleResourceAlreadyExistsException(final ResourceAlreadyExistsException e) {
        final HttpStatus status = HttpStatus.CONFLICT;
        final ApiErrorDto dto = new ApiErrorDto(status.value(), List.of(e.getMessage()));
        log.info(dto.toString());
        return new ResponseEntity<>(dto, status);
    }

    @ExceptionHandler(ResourceNotViableException.class)
    public ResponseEntity<ApiErrorDto> handleResourceNotViableException(final ResourceNotViableException e) {
        final HttpStatus status = HttpStatus.GONE;
        final ApiErrorDto dto = new ApiErrorDto(status.value(), List.of(e.getMessage()));
        log.info(dto.toString());
        return new ResponseEntity<>(dto, status);
    }

    @Data
    public static class ApiErrorDto {

        private final int status;
        private final List<String> messages;

        @JsonCreator
        public ApiErrorDto(
                @JsonProperty("status") final int status,
                @JsonProperty("messages") final List<String> messages
        ) {
            this.status = status;
            this.messages = messages;
        }
    }

}
