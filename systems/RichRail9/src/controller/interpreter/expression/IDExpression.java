package controller.interpreter.expression;

import controller.interpreter.Context;
import controller.interpreter.InvalidExpressionException;

public class IDExpression extends Expression
{
	String id;
	
	@Override
	public void interpret(Context context) throws InvalidExpressionException
	{
		id = context.pollFirst();
		
		if(id == null || !id.matches("^[a-z][a-z0-9]*$")) {
			throw new InvalidExpressionException();
		}
	}
	
	@Override
	public String toString()
	{
		return id;
	}
	
}
