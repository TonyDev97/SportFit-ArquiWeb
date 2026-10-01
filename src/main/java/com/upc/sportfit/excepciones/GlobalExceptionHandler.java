package com.upc.sportfit.excepciones;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        log.error("Error occurred", ex);
        ErrorResponse error = new ErrorResponse(ex.getStatusCode().value(), ex.getReason());
        return new ResponseEntity<>(error, ex.getStatusCode());
    }

    // El mensaje siempre lo define la capa de servicio (ej. "Pedido con ID X no encontrado"),
    // por eso se expone tal cual al cliente.
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
        log.error("Error occurred", ex);
        String mensaje = ex.getMessage() != null ? ex.getMessage() : "Recurso no encontrado";
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), mensaje);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // La lanza Spring Data internamente, sin un mensaje curado por nosotros: se deja genérico.
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        log.error("Error occurred", ex);
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Recurso no encontrado");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // El mensaje siempre lo define la capa de servicio (ej. "Stock insuficiente para el producto X"),
    // por eso se expone tal cual al cliente.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Error occurred", ex);
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Los mensajes de cada campo los define el DTO (@NotNull, @Size, @Min, etc.);
    // ahora van estructurados en "errors" en vez de aplastados como texto en "message".
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidaciones(MethodArgumentNotValidException ex) {
        log.error("Error occurred", ex);
        List<String> errores = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .toList();

        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Error de validación", errores);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Violación de una restricción de la BD (ej. email único duplicado). El mensaje real
    // viene del driver/SQL y puede filtrar detalles internos (tabla, constraint) — nunca se expone.
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error("Error occurred", ex);
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(),
                "El registro no pudo guardarse porque entra en conflicto con datos existentes");
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // JSON de request malformado (tipos inválidos, sintaxis rota). El mensaje real viene del
    // parser de Jackson y puede exponer nombres de clases internas — nunca se expone.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Error occurred", ex);
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                "El cuerpo de la solicitud no es un JSON válido");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // El mensaje siempre lo define la capa de servicio (ej. "Producto con ID X no encontrado"
    // en ProductoService/ProveedorService), por eso se expone tal cual al cliente.
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleGeneralExceptionRuntime(RuntimeException ex) {
        log.error("Error occurred", ex);
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Última red de seguridad: cualquier excepción no controlada explícitamente (NPE, errores
    // de librerías, etc.). Nunca se definió su mensaje a propósito, así que nunca se expone.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        log.error("Error occurred", ex);
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocurrió un error inesperado");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
