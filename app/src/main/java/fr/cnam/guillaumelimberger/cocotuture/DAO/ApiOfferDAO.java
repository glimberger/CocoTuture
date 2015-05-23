package fr.cnam.guillaumelimberger.cocotuture.dao;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import fr.cnam.guillaumelimberger.cocotuture.entity.Offer;
import fr.cnam.guillaumelimberger.cocotuture.JSONParser;

/**
 * @author Guillaume Limberger glim.dev@gmail.com
 */
public class ApiOfferDAO implements OfferDAO {

    private ApiDAOFactory daoFactory = null;
    private JSONParser jsonParser = new JSONParser();

    public ApiOfferDAO(ApiDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public int create(List<NameValuePair> params) throws JSONException {

        String url = daoFactory.getUrl()+"offer.php";
        JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);
        int result = 0;

        int success = json.getInt(DAOUtils.TAG_SUCCESS);
        if (success == 1) {
            result = 1 ;
        }

        return result;
    }

    @Override
    public int remove(List<NameValuePair> params) throws JSONException {
        int result = 0;
        String url = daoFactory.getUrl()+"offer.php";
        JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

        int success = json.getInt(DAOUtils.TAG_SUCCESS);
        if (success == 1) {
            result = 1 ;
        }

        return result;
    }

    @Override
    public Offer find(List<NameValuePair> params) {
        return null;
    }
}
