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
public class CreditCard {
    /**
     * Track 1 data.
     */
    protected String track1;

    /**
     * Track 2 data.
     */
    protected String track2;

    /**
     * Account number.
     */
    protected String accountNumber;

    /**
     * Expire month (mm).
     */
    protected String expireMonth;

    /**
     * Expire year (yy).
     */
    protected String expireYear;

    /**
     * Holder name.
     */
    protected String nameOnCard;

    /**
     * Constructor when a card was swiped.
     * @param track1 String The track 1 data
     * @param track2 String The track 2 data
     */
    public CreditCard(String track1, String track2) {
        this.track1 = track1;
        this.track2 = track2;
        this.accountNumber = null;
        this.expireMonth = null;
        this.expireYear = null;
        this.nameOnCard = null;
    }

    /**
     * Constructor when a card was keyed in
     * @param accountNumber String The account number
     * @param expireMonth String The expire month in 'mm' format
     * @param expireYear String The expire year in 'yy' format
     * @param nameOnCard String The holder's name
     */
    public CreditCard(String accountNumber, String expireMonth,
                      String expireYear, String nameOnCard) {
        this.accountNumber = accountNumber;
        this.expireMonth = expireMonth;
        this.expireYear = expireYear;
        this.nameOnCard = nameOnCard;
        this.track1 = null;
        this.track2 = null;
    }

    /**
     * Constructor.
     * @param track1 String The track 1 data
     * @param track2 String The track 2 data
     * @param accountNumber String The account number
     * @param expireMonth String The expire month in 'mm' format
     * @param expireYear String The expire year in 'yy' format
     * @param nameOnCard String The holder's name
     */
    public CreditCard(String track1, String track2, String accountNumber,
                      String expireMonth,
                      String expireYear, String nameOnCard) {
        this.accountNumber = accountNumber;
        this.expireMonth = expireMonth;
        this.expireYear = expireYear;
        this.nameOnCard = nameOnCard;
        this.track1 = track1;
        this.track2 = track2;
    }

    /**
     * Gets the track 1 data.
     * @return String The track 1 data
     */
    public String getTrack1() {
        return track1;
    }

    /**
     * Gets the track 2 data.
     * @return String The track 2 data
     */
    public String getTrack2() {
        return track2;
    }

    /**
     * Gets the account number.
     * @return String The account number
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Gets the expire month.
     * @return String The expire month
     */
    public String getExpireMonth() {
        return expireMonth;
    }

    /**
     * Gets the expire year.
     * @return String The expire year
     */
    public String getExpireYear() {
        return expireYear;
    }

    /**
     * Gets the holder's name
     * @return String The holder's name
     */
    public String getNameOnCard() {
        return nameOnCard;
    }
}
