package fr.cnam.guillaumelimberger.cocotuture.dao;

/**
 * Guillaume Limberger glim.dev@gmail.com
 */
public class ApiDAOFactory extends DAOFactory{

    private static final String API_URL = "http://ns3270404.ip-5-39-82.eu/cocotuture/api/";
    //private static final String API_URL = "http://hirsute:8888/cocotuture/";

    @Override
    public SubscriberDAO getSubscriberDAO() {
        return new ApiSubscriberDAO(this);
    }

    @Override
    public OfferDAO getOfferDAO() {
        return new ApiOfferDAO(this);
    }

    public String getUrl() {
        return API_URL;
    }
}
