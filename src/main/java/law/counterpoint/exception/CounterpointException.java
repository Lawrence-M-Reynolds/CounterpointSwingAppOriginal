package law.counterpoint.exception;

public abstract class CounterpointException extends Exception{
	private String errorMessage;
	
	public CounterpointException(String anErrorMessage){
		errorMessage = anErrorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
