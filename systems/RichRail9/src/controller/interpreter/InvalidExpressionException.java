package controller.interpreter;

public class InvalidExpressionException extends Exception
{
	private static final long serialVersionUID = -6645250082608833255L;

	public InvalidExpressionException() {
		super("command not correct");
	}
	
	public InvalidExpressionException(String message) {
		super(message);
	}
}
