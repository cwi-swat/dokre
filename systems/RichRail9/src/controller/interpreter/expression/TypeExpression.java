package controller.interpreter.expression;

import controller.interpreter.Context;
import controller.interpreter.InvalidExpressionException;

public class TypeExpression extends Expression
{
	String type;
	
	@Override
	public void interpret(Context context) throws InvalidExpressionException
	{
		type = context.pollFirst();
		
		if(type == null || (!type.equals("train") && !type.equals("wagon"))) {
			throw new InvalidExpressionException("invalid type");
		}
	}
	
	@Override
	public String toString()
	{
		return type;
	}
	
}
