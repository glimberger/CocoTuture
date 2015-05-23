package fr.cnam.guillaumelimberger.cocotuture.dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import fr.cnam.guillaumelimberger.cocotuture.entity.Offer;
import fr.cnam.guillaumelimberger.cocotuture.entity.Subscriber;


/**
 * Guillaume Limberger glim.dev@gmail.com
 */
public class DAOUtils {

    public static final String TAG_SUCCESS      = "success";
    public static final String TAG_MESSAGE      = "message";

    public static final String TAG_SUBSCRIBER   = "subscriber";
    public static final String TAG_SUBSCRIBERS  = "subscribers";

    public static final String TAG_OFFER        = "offer";

    // actions
    public static final String ACTION_CREATE    = "create";
    public static final String ACTION_REMOVE    = "remove";

    public static Object map(JSONObject json, String tag) throws ParseException {

        try {
            if (tag.equals(TAG_SUBSCRIBER) && json.has(TAG_SUBSCRIBER)) {
                JSONObject jsonSubscriber = json.getJSONObject(TAG_SUBSCRIBER);

                Subscriber subscriber = new Subscriber();
                if (jsonSubscriber.has(Subscriber.ID)) {
                    subscriber.setId(jsonSubscriber.getInt(Subscriber.ID));
                }
                if (jsonSubscriber.has(Subscriber.FIRSTNAME)) {
                    subscriber.setFirstName(jsonSubscriber.getString(Subscriber.FIRSTNAME));
                }
                if (jsonSubscriber.has(Subscriber.LASTNAME)) {
                    subscriber.setLastName(jsonSubscriber.getString(Subscriber.LASTNAME));
                }
                if (jsonSubscriber.has(Subscriber.PHONENUMBER)) {
                    subscriber.setPhone(jsonSubscriber.getString(Subscriber.PHONENUMBER));
                }
                if (jsonSubscriber.has(Subscriber.EMAIL)) {
                    subscriber.setEmail(jsonSubscriber.getString(Subscriber.EMAIL));
                }
                if (jsonSubscriber.has(Subscriber.LOGIN)) {
                    subscriber.setLogin(jsonSubscriber.getString(Subscriber.LOGIN));
                }
                if (jsonSubscriber.has(Subscriber.PASSWORD)) {
                    subscriber.setPassword(jsonSubscriber.getString(Subscriber.PASSWORD));
                }

                return subscriber;
            }
            if (tag.equals(TAG_SUBSCRIBERS) && json.has(TAG_SUBSCRIBERS)) {
                JSONArray jsonArray = json.getJSONArray(TAG_SUBSCRIBERS);

                List<Subscriber> subscribers = new ArrayList<Subscriber>();
                for (int i = 0; i < jsonArray.length(); i++ ) {
                    JSONObject jsonSubscriber = jsonArray.getJSONObject(i);

                    Subscriber subscriber = new Subscriber();
                    subscriber.setId(jsonSubscriber.getInt(Subscriber.ID));
                    subscriber.setFirstName(jsonSubscriber.getString(Subscriber.FIRSTNAME));
                    subscriber.setLastName(jsonSubscriber.getString(Subscriber.LASTNAME));
                    subscriber.setPhone(jsonSubscriber.getString(Subscriber.PHONENUMBER));
                    subscriber.setEmail(jsonSubscriber.getString(Subscriber.EMAIL));
                    subscriber.setLogin(jsonSubscriber.getString(Subscriber.LOGIN));
                    subscriber.setPassword(jsonSubscriber.getString(Subscriber.PASSWORD));
                    subscribers.add(subscriber);
                }

                return subscribers;
            }
            if (tag.equals(TAG_OFFER) && json.has(TAG_OFFER)) {
                JSONObject jsonOffer = json.getJSONObject(TAG_OFFER);

                Offer offer = new Offer();
                offer.setId(jsonOffer.getInt(Offer.ID));
                offer.setDriverId(jsonOffer.getInt(Offer.DRIVER_ID));
                offer.setCarModel(jsonOffer.getString(Offer.CAR_MODEL));
                offer.setCarColor(jsonOffer.getString(Offer.CAR_COLOR));
                offer.setCarLicenseNumber(jsonOffer.getString(Offer.CAR_LICENSE_NUMBER));
                offer.setMeetingPlace(jsonOffer.getString(Offer.MEETING_PLACE));
                offer.setDestination(jsonOffer.getString(Offer.DESTINATION));
                offer.setMeetingTime(jsonOffer.getString(Offer.MEETING_TIME));
                offer.setSeating(jsonOffer.getInt(Offer.SEATING));

                return offer;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
