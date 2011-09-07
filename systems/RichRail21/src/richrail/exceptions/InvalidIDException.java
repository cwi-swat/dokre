package richrail.exceptions;

/**
 *
 */
public class InvalidIDException extends IllegalArgumentException {

	 /**
		*
		*/
	 public InvalidIDException() {
			super();
	 }

	 /**
		*
		* @param s
		*/
	 public InvalidIDException(String s) {
			super(s);
	 }

}
