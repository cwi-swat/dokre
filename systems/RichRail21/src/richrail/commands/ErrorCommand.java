package richrail.commands;

/**
 * Bedoeld voor het afhandelen van foutmeldingen
 */
public class ErrorCommand extends Command {

	 private String message;

	 /**
	  * Constructor
	  */
	 public ErrorCommand() {
			this("");
	 }

	 /**
	  * Instellen van een foutmelding
	  * @param message string
	  */
	 public ErrorCommand(String message) {
			this.message = message;
	 }

	 /**
	  * Haalt foutbericht op.
	  * @return string
	  */
	 public String getMessage() {
			return message;
	 }

	 /**
	  * Het uitvoeren van de error commando.
	  * @see richrail.commands.Command#execute()
	  */
	 @Override
	 protected void todo() {
	 }
}
