package fr.cnam.guillaumelimberger.cocotuture;

/**
 * @author Guillaume Limberger glim.dev@gmail.com
 */
public class Consts {

    // request codes
    public static final int REQUEST_LOGIN           = 1;
    public static final int REQUEST_REGISTER        = 2;
    public static final int REQUEST_NEW_OFFER       = 11;
    public static final int REQUEST_CANCEL_OFFER    = 12;
    public static final int REQUEST_BOOKINGS        = 13;


    // result codes
    public static final int RESULT_LOGIN            = 1;
    public static final int RESULT_LOGOUT           = 99;
    public static final int RESULT_NEW_OFFER        = 101;
    public static final int RESULT_CANCEL_OFFER     = 102;
    public static final int RESULT_BOOKINGS         = 103;
    public static final int RESULT_SEARCH_OFFER     = 201;
    public static final int RESULT_CANCEL_BOOKING   = 202;
    public static final int RESULT_OFFERS           = 203;


    public static final int NOT_FOUND = -1;

    private Consts(){
        //this prevents even the native class from calling this constructor
        throw new AssertionError();
    }
}
