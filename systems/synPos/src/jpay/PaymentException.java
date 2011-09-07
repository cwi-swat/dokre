package jpay;

/**
 * <p>Title: JPay</p>
 *
 * <p>This package defines the interface for all the payment modules of synPOS,
 * a desktop POS (Point Of Sale) client for online ERP/eCommerce systems.</p>
 *
 * <p>Released under the GNU General Public License.
 * Absolutely no warranty. Use at your own risk.</p>
 *
 * <p>Copyright: Copyright (c) 2006 synPOS.com</p>
 *
 * <p>Website: www.synpos.com</p>
 *
 * @author H.Q.
 * @version 0.9.3
 */
public class PaymentException extends Exception {
    /**
     * Exception when a transaction falied.
     * @param message String The error message
     */
    public PaymentException(String message) {
        super(message);
    }
}
