package com.examly.springapp.exception;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.examly.springapp.pojo.ErrorResponse;
@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> method1(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});
		ErrorResponse response = new ErrorResponse(errors.toString());
		return ResponseEntity.status(404).body(response);
	}

	@ExceptionHandler(LoanNotFoundException.class)
	public ResponseEntity<?> method2(LoanNotFoundException e) {
		return ResponseEntity.status(409).body(e.getMessage());
	}

	@ExceptionHandler(UserNotFound.class)
	public ResponseEntity<?> method3(UserNotFound e) {
		return ResponseEntity.status(409).body(e.getMessage());
	}

	@ExceptionHandler(FeedbackNotExist.class)
	public ResponseEntity<?> method4(FeedbackNotExist e) {
		return ResponseEntity.status(401).body(e.getMessage());
	}

	@ExceptionHandler(ApplicationStatusCancelledException.class)
	public ResponseEntity<?> method5(ApplicationStatusCancelledException e) {
		return ResponseEntity.status(401).body(e.getMessage());
	}

	@ExceptionHandler(LoanApplicationNotFoundException.class)
	public ResponseEntity<?> method6(LoanApplicationNotFoundException e) {
		return ResponseEntity.status(401).body(e.getMessage());
	}
}