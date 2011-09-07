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
public class PaymentResponse {
    /**
     * Response code.
     */
    protected String responseCode;

    /**
     * Constructor.
     * @param responseCode String The response code
     */
    public PaymentResponse(String responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * Gets the response code.
     * @return String The response code
     */
    public String getResponseCode() {
        return responseCode;
    }
}
