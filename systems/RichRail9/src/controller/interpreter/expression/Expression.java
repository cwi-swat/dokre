package controller.interpreter.expression;

import controller.interpreter.Context;
import controller.interpreter.InvalidExpressionException;

public abstract class Expression
{
	public abstract void interpret(Context context) throws InvalidExpressionException;
}