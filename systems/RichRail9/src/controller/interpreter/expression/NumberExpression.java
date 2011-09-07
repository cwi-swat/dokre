package controller.interpreter.expression;

import controller.interpreter.Context;
import controller.interpreter.InvalidExpressionException;

public class NumberExpression extends Expression
{
	private Number number;
	
	@Override
	public void interpret(Context context) throws InvalidExpressionException
	{
		try {
			number = new Integer(context.pollFirst());
		} catch(NumberFormatException e) {
			throw new InvalidExpressionException();
		}
		if(!number.toString().matches("[0-9]+")) {
			throw new InvalidExpressionException();
		}
	}
	
	@Override
	public String toString()
	{
		return number == null ? "null" : number.toString();
	}
	
}
