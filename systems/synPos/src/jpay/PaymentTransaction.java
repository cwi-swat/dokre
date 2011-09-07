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
@version 0.9.3 * @author H.Q.
 * @version 1.0.0
 */
public class PaymentTransaction extends PaymentResponse {
    /**
     * Authorization code.
     */
    protected String authCode;

    /**
     * Transaction id.
     */
    protected String transactionId;

    /**
     * Reference number
     */
    protected String referenceNumber;

    /**
     * Constructor.
     * @param responseCode String The response code
     * @param authCode String The auth code
     * @param transactionId String The transaction id
     * @param referenceNumber String The reference number
     */
    public PaymentTransaction(String responseCode, String authCode,
                              String transactionId,
                              String referenceNumber) {
        super(responseCode);
        this.authCode = authCode;
        this.transactionId = transactionId;
        this.referenceNumber = referenceNumber;
    }

    /**
     * Gets the auth code.
     * @return String The auth code
     */
    public String getAuthCode() {
        return authCode;
    }

    /**
     * Gets the transaction id.
     * @return String The transaction id
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Gets the reference number
     * @return String The reference number
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }
}
