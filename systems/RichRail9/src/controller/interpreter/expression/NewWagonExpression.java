package controller.interpreter.expression;

import logging.Logger;
import controller.AppController;
import controller.interpreter.Context;
import controller.interpreter.InvalidExpressionException;
import domain.Part;

public class NewWagonExpression extends Expression
{
	private Expression id;
	
	private Expression seats;
	
	@Override
	public void interpret(Context context) throws InvalidExpressionException
	{
		// Get the Id
		Expression expression = new IDExpression();
		expression.interpret(context);
		id = expression;
		
		// Get the controller
		AppController controller = context.getController();
		
		Part part;
		
		// Check if numseats is set in the command
		if (context.peekFirst() != null && context.peekFirst().toString().equals("numseats"))
		{
			context.pollFirst();
			expression = new NumberExpression();
			expression.interpret(context);
			seats = expression;
			
			part = new Part(id.toString(), Integer.parseInt(seats.toString()));
		} 
		else if(context.peekFirst() != null)
		{
			throw new InvalidExpressionException();
		}
		else
		{
			part = new Part(id.toString());
		}
		
		// Add part
		try {
			controller.getCompany().addPart(part);
			Logger.getInstance().log(new String("wagon " + part.getName() + " created with " + part.getNumberOfSeats() + " seats"));
		} catch(Exception e) {
			throw new InvalidExpressionException(e.getMessage());
		}

	}
	
	@Override
	public String toString()
	{
		if (id == null)
			return "wagon";
		return "wagon " + id.toString() + (seats != null ? " " + seats.toString() : "");
	}
	
}
