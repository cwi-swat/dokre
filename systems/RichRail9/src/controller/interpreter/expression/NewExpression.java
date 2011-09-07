package controller.interpreter.expression;

import controller.interpreter.Context;
import controller.interpreter.InvalidExpressionException;

public class NewExpression extends Expression
{
	Expression type;
	Expression expression = null;
	
	@Override
	public void interpret(Context context) throws InvalidExpressionException
	{
		
		// Get the type
		Expression type = new TypeExpression();
		type.interpret(context);
		
		if (type.toString().equals("train"))
		{
			expression = new NewTrainExpression();
			expression.interpret(context);
		}
		else if (type.toString().equals("wagon"))
		{
			expression = new NewWagonExpression();
			expression.interpret(context);
		}
	}
	
	@Override
	public String toString()
	{
		if (type == null)
			return "new";
		return "new " + type.toString();
	}
	
}
