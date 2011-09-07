package richrail.exceptions;

/**
 *
 */
public class WagonNotFoundException extends NullPointerException {

	 /**
		*
		*/
	 public WagonNotFoundException() {
			super();
	 }

	 /**
		*
		* @param s
		*/
	 public WagonNotFoundException(String s) {
			super(s);
	 }

}
