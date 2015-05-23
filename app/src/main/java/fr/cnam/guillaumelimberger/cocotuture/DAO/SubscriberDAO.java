package fr.cnam.guillaumelimberger.cocotuture.dao;

import org.apache.http.NameValuePair;
import java.util.ArrayList;
import java.util.List;

import fr.cnam.guillaumelimberger.cocotuture.entity.Subscriber;

/**
 * @author Guillaume Limberger glim.dev@gmail.com
 */
public interface SubscriberDAO
{
    public int create(List<NameValuePair> params);

    public ArrayList<Object> find(List<NameValuePair> params);

    public ArrayList<Subscriber> findAll();

    public Subscriber check(List<NameValuePair> params);
}
