package fr.cnam.guillaumelimberger.cocotuture.dao;

import org.apache.http.NameValuePair;
import org.json.JSONException;

import java.util.List;
import java.util.jar.Attributes;

import fr.cnam.guillaumelimberger.cocotuture.entity.Offer;

/**
 * Guillaume Limberger glim.dev@gmail.com
 */
public interface  OfferDAO {

    public int create(List<NameValuePair> params) throws JSONException;

    public int remove(List<NameValuePair> params) throws JSONException;

    public Offer find(List<NameValuePair> params);
}
