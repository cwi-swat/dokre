package controller.command;

public class CommandResult {
	public Object object = null;
	public String message;

	/**
	 * Sets the message that should appear in the output log.
	 * 
	 * @param message The message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Return the message that is set.
	 * 
	 * @return The message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Return an object. This could be the Train or Wagon object.
	 * 
	 * @return Either Train or Wagon.
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * Set the object. This could be the Train or Wagon object.
	 * 
	 * @param object Train or Wagon object.
	 */
	public void setObject(Object object) {
		this.object = object;
	}
}
