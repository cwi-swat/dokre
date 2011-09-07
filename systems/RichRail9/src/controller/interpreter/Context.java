package controller.interpreter;

import java.util.LinkedList;

import controller.AppController;
import controller.interpreter.expression.AddExpression;
import controller.interpreter.expression.DelExpression;
import controller.interpreter.expression.Expression;
import controller.interpreter.expression.GetNumSeatsExpression;
import controller.interpreter.expression.NewExpression;
import controller.interpreter.expression.RemExpression;

public class Context extends LinkedList<String>
{
	private static final long serialVersionUID = -3014921352501828305L;
	private AppController controller;
	private String expression;
	
	public Context(AppController controller, String expression)
	{
		this.controller = controller;
		
		if(expression.endsWith(";"))
		{
			expression = expression.substring(0, expression.length() - 1);
		}
		
		this.expression = expression.toLowerCase();
		
		parseExpression(expression);
	}
	
	/**
	 * @return controller
	 */
	public AppController getController() 
	{
		return controller;
	}
	
	/**
	 * @return the expression
	 */
	public String getExpression()
	{
		return expression;
	}
	
	/**
	 * @param expression the expression to set
	 */
	public void setExpression(String expression)
	{
		this.expression = expression;
		
		parseExpression(expression);
	}
	
	private void parseExpression(String expression)
	{
		String[] expressionArray = expression.split("\\s+");
		
		for (String expressionPart: expressionArray)
		{
			add(expressionPart);
		}
	}
	
	public Expression Evaluate() throws InvalidExpressionException
	{
		String command = pollFirst();
		if (command == null)
		{
			throw new InvalidExpressionException();
		}
		
		command = command.toLowerCase();
		
		Expression expression;
		if (command.equals("new"))
		{
			expression = new NewExpression();
			expression.interpret(this);
		}
		else if (command.equals("add"))
		{
			expression = new AddExpression();
			expression.interpret(this);
		}
		else if (command.equals("getnumseats"))
		{
			expression = new GetNumSeatsExpression();
			expression.interpret(this);
		}
		else if (command.equals("delete"))
		{
			expression = new DelExpression();
			expression.interpret(this);
		}
		else if (command.equals("remove"))
		{
			expression = new RemExpression();
			expression.interpret(this);
		}
		else
		{
			throw new InvalidExpressionException();
		}
		
		return expression;
	}
}
