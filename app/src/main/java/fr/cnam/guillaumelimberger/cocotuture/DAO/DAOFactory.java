package fr.cnam.guillaumelimberger.cocotuture.dao;

/**
 * Guillaume Limberger glim.dev@gmail.com
 */
public abstract class DAOFactory {

    public static final int API = 1;
    public static final int SQLITE = 2;

    public abstract SubscriberDAO getSubscriberDAO();

    public abstract OfferDAO getOfferDAO();

    public static DAOFactory getDAOFactory(int whichFactory) {

        switch (whichFactory) {
            case API:
                return new ApiDAOFactory();

            case SQLITE:
                return new SQliteFactoryDAO();

            default:
                return null;
        }
    }
}
