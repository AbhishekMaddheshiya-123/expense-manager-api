package in.expansetrackerapi.excpetions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value  = HttpStatus.CONFLICT)
public class ItemAlreadyExitsException  extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ItemAlreadyExitsException(String message) 
	{
		super(message);
	}
    
}
