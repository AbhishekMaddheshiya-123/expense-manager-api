package in.expansetrackerapi.excpetions;

public class ResourseNotFoundException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourseNotFoundException(String message) 
	{
		super(message);
	}
}
