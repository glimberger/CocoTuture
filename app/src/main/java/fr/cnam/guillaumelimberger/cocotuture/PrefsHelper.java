package fr.cnam.guillaumelimberger.cocotuture;

import android.app.Activity;
import android.content.SharedPreferences;

import fr.cnam.guillaumelimberger.cocotuture.entity.Offer;

/**
 * Helper class for communications with SharedPreferences
 *
 * @author Guillaume Limberger glim.dev@gmail.com
 */
public class PrefsHelper
{
    private static final String PREFS_NAME = "prefs";

    private static SharedPreferences prefs;
    private static SharedPreferences.Editor prefsEditor;


    // SUBSCRIBER
    public static String getName(Activity activity) {
        prefs = activity.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

        return prefs.getString("subscriberFirstName", "") + " " +
                prefs.getString("subscriberLastName", "");
    }

    public static void removeSubscriber(Activity activity) {
        prefs = activity.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        prefsEditor = prefs.edit();
        prefsEditor.remove("subscriberId");
        prefsEditor.remove("subscriberLogin");
        prefsEditor.remove("subscriberFirstName");
        prefsEditor.remove("subscriberLastName");
        prefsEditor.apply();
    }

    public static void setSubscriberId(Activity activity, Integer id) {
        prefs = activity.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        prefsEditor = prefs.edit();
        prefsEditor.putInt("subscriberId", id);
        prefsEditor.apply();
    }

    public static Integer getSubscriberId(Activity activity) {
        prefs = activity.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

        return prefs.getInt("subscriberId", 0);
    }

    public static void setSubscriberLogin(Activity activity, String login) {
        prefs = activity.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        prefsEditor = prefs.edit();
        prefsEditor.putString("subscriberLogin", login);
        prefsEditor.apply();
    }

    public static String getSubscriberLogin(Activity activity) {
        prefs = activity.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

        return prefs.getString("subscriberLogin", "");
    }

    public static void setSubscriberFirstName(Activity activity, String firstName) {
        prefs = activity.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        prefsEditor = prefs.edit();
        prefsEditor.putString("subscriberFirstName", firstName);
        prefsEditor.apply();
    }

    public static String getSubscriberFirstName(Activity activity) {
        prefs = activity.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

        return prefs.getString("subscriberFirstName", "");
    }

    public static void setSubscriberLastName(Activity activity, String lastName) {
        prefs = activity.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        prefsEditor = prefs.edit();
        prefsEditor.putString("subscriberLastName", lastName);
        prefsEditor.apply();
    }

    public static String getSubscriberLastName(Activity activity) {
        prefs = activity.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

        return prefs.getString("subscriberLastName", "");
    }

    public static boolean hasSubscriber(Activity activity) {
        prefs = activity.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

        return prefs.contains("subscriberLogin");
    }

    // OFFER
    public static void setOffer(Activity activity, Offer offer) {
        prefs = activity.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        prefsEditor = prefs.edit();
        prefsEditor.putString("offerCarModel", offer.getCarModel());
        prefsEditor.putString("offerCarColor", offer.getCarColor());
        prefsEditor.putString("offerCarLicenseNumber", offer.getCarLicenseNumber());
        prefsEditor.putString("offerMeetingPlace", offer.getMeetingPlace());
        prefsEditor.putLong("offerLatitude", offer.getLatitude().longValue());
        prefsEditor.putLong("offerLatitude", offer.getLongitude().longValue());
        prefsEditor.putString("offerDestination", offer.getDestination());
        prefsEditor.putString("offerMeetingTime", offer.getMeetingTime());
        prefsEditor.putInt("offerSeating", offer.getSeating());
        prefsEditor.apply();
    }

    public static void removeOffer(Activity activity) {
        prefs = activity.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        prefsEditor = prefs.edit();
        prefsEditor.remove("offerCarModel");
        prefsEditor.remove("offerCarColor");
        prefsEditor.remove("offerCarLicenseNumber");
        prefsEditor.remove("offerMeetingPlace");
        prefsEditor.remove("offerLatitude");
        prefsEditor.remove("offerLatitude");
        prefsEditor.remove("offerDestination");
        prefsEditor.remove("offerMeetingTime");
        prefsEditor.remove("offerSeating");
        prefsEditor.apply();
    }

    public static boolean hasOffer(Activity activity) {
        // TODO : check if diver id equals subscriber id
        prefs = activity.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

        return prefs.contains("offerMeetingPlace");
    }

    public static String getOfferMeetingPlace(Activity activity) {
        prefs = activity.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

        return prefs.getString("offerMeetingPlace", "");
    }

    public static String getOfferDestination(Activity activity) {
        prefs = activity.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

        return prefs.getString("offerDestination", "");
    }

    public static Integer getOfferSeating(Activity activity) {
        prefs = activity.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

        return prefs.getInt("offerSeating", 0);
    }
}
