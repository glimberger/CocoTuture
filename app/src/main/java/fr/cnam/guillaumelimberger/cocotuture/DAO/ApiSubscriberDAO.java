package fr.cnam.guillaumelimberger.cocotuture.dao;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import fr.cnam.guillaumelimberger.cocotuture.entity.Subscriber;
import fr.cnam.guillaumelimberger.cocotuture.JSONParser;

/**
 * @author Guillaume Limberger glim.dev@gmail.com
 */
public class ApiSubscriberDAO implements SubscriberDAO {

    private ApiDAOFactory daoFactory;
    private JSONParser jsonParser;

    public ApiSubscriberDAO(ApiDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.jsonParser = new JSONParser();
    }

    @Override
    public int create(List<NameValuePair> params) {
        String url = daoFactory.getUrl()+"register.php";
        JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);
        int result = 0;

        try {
            int success = json.getInt(DAOUtils.TAG_SUCCESS);
            if (success == 1) {
                result = 1 ;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public ArrayList<Object> find(List<NameValuePair> params) {
        String url = daoFactory.getUrl()+"subscriber.php";
        JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);
        ArrayList<Object> arrayList = new ArrayList<Object>();

        try {
            int success = json.getInt(DAOUtils.TAG_SUCCESS);
            if (success == 1) {
                arrayList.add(0, DAOUtils.map(json, DAOUtils.TAG_SUBSCRIBER));
                arrayList.add(1, DAOUtils.map(json, DAOUtils.TAG_OFFER));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

    @Override
    public ArrayList<Subscriber> findAll() {
        String url = daoFactory.getUrl()+"subscriber.php";
        JSONObject json = jsonParser.getJSONFromUrl(url);
        ArrayList<Subscriber> subscribers = new ArrayList<Subscriber>();

//        try {
//            int success = json.getInt(DAOUtils.TAG_SUCCESS);
//            if (success == 1) {
//                subscribers = (ArrayList<Subscriber>)DAOUtils.map(json.getJSONObject(DAOUtils.TAG_SUBSCRIBERS));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        return subscribers;
    }

    public Subscriber check(List<NameValuePair> params) {
        String url = daoFactory.getUrl()+"login.php";
        JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);
        Subscriber subscriber = new Subscriber();

        try {
            int success = json.getInt(DAOUtils.TAG_SUCCESS);
            if (success == 1) {
                subscriber =  (Subscriber)DAOUtils.map(json, DAOUtils.TAG_SUBSCRIBER);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return subscriber;
    }
}
