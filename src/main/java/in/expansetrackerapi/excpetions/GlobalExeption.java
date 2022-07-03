package in.expansetrackerapi.excpetions;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import in.expansetrackerapi.entity.ErrorObject;

@ControllerAdvice
public class GlobalExeption  extends ResponseEntityExceptionHandler
{
//	Creating and Handling Custom Exception [for handle the id does not exits]
	@ExceptionHandler(ResourseNotFoundException.class)
     public ResponseEntity<ErrorObject> handelExpensesException(ResourseNotFoundException ex, WebRequest req)
     {
		ErrorObject errorObject = new ErrorObject();
	    errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
	    errorObject.setMessage(ex.getMessage());
	    errorObject.setTimestamp(new Date());
		return new  ResponseEntity<ErrorObject>(errorObject,HttpStatus.NOT_FOUND); 
     }
	
//	Creating and Handling Custom Exception for existing email
	@ExceptionHandler(ItemAlreadyExitsException.class)
     public ResponseEntity<ErrorObject> handelItemExitsException(ItemAlreadyExitsException ex, WebRequest req)
     {
		ErrorObject errorObject = new ErrorObject();
	    errorObject.setStatusCode(HttpStatus.CONFLICT.value());
	    errorObject.setMessage(ex.getMessage());
	    errorObject.setTimestamp(new Date());
		return new  ResponseEntity<ErrorObject>(errorObject,HttpStatus.CONFLICT);
    	 
     }
	
//	Handling Bad Request Exception[Type mismatch argument exception]
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorObject> handelerMismatchArgumentException(MethodArgumentTypeMismatchException ex, WebRequest req)
    {
		ErrorObject errorObject = new ErrorObject();
	    errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
	    errorObject.setMessage(ex.getMessage());
	    errorObject.setTimestamp(new Date());
		return new  ResponseEntity<ErrorObject>(errorObject,HttpStatus.BAD_REQUEST);

    }
	
//	handling Internal server Error [ that is general exception handler means unexpected exception]
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorObject> handeleGeneralException(Exception ex, WebRequest req)
    {
		ErrorObject errorObject = new ErrorObject();
	    errorObject.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	    errorObject.setMessage(ex.getMessage());
	    errorObject.setTimestamp(new Date());
		return new  ResponseEntity<ErrorObject>(errorObject,HttpStatus.INTERNAL_SERVER_ERROR);

    }
	
	
	// Customize the  validation exception through ResponseEntityExceptionHnadle Method
	 @Override
		protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
				HttpHeaders headers, HttpStatus status, WebRequest request) 
	 {
			Map<String, Object> body = new HashMap<>();
			body.put("timestamp", new Date());
			body.put("statusCode", HttpStatus.BAD_REQUEST.value());
			List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(m -> m.getDefaultMessage()).collect(Collectors.toList());
			
			body.put("message", errors);
			return new ResponseEntity<Object>(body,HttpStatus.BAD_REQUEST);
		}
	 
	 
	
}
