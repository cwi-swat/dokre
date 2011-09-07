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
public interface CreditCardGateway {
    /**
     * Charges a credit card.
     * @param amount double The amount to charge
     * @param orderId String The order id
     * @param card CreditCard The credit card to charge
     * @return PaymentTransaction The transaction data
     * @throws PaymentException
     */
    public PaymentTransaction chargeCreditCard(double amount,
                                               String orderId,
                                               CreditCard card) throws
            PaymentException;

    /**
     * Refunds a credit card charge.
     * @param amount double The amount to be refunded
     * @param orderId String The original order id
     * @param accountNumber String The account number of the charged credit card
     * @param transactionId String The original transaction id
     * @return PaymentTransaction The transaction data
     * @throws PaymentException
     */
    public PaymentTransaction refundCreditCard(double amount,
                                               String orderId,
                                               String accountNumber,
                                               String transactionId) throws
            PaymentException;
}
