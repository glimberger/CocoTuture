package fr.cnam.guillaumelimberger.cocotuture;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import fr.cnam.guillaumelimberger.cocotuture.dao.ApiDAOFactory;
import fr.cnam.guillaumelimberger.cocotuture.dao.DAOFactory;
import fr.cnam.guillaumelimberger.cocotuture.dao.DAOUtils;
import fr.cnam.guillaumelimberger.cocotuture.dao.OfferDAO;
import fr.cnam.guillaumelimberger.cocotuture.driver.NewOfferActivity;
import fr.cnam.guillaumelimberger.cocotuture.driver.BookingsActivity;


public class MenuActivity extends Activity implements View.OnClickListener {

    private final String CLASSNAME = getClass().getSimpleName();
    private final boolean D = BuildConfig.DEBUG;

    // buttons
    Button buttonNewOffer;
    Button buttonCancelOffer;
    Button buttonBookings;
    Button buttonSearchOffer;
    Button buttonCancelBooking;
    Button buttonOffers;
    Button buttonLogout;

    private ApiDAOFactory apiDAOFactory;
    private OfferDAO offerDAO;

    /**
     * Called when the activity is starting.  This is where most initialization
     * should go: calling {@link #setContentView(int)} to inflate the
     * activity's UI, using {@link #findViewById} to programmatically interact
     * with widgets in the UI, calling
     * {@link #managedQuery(android.net.Uri , String[], String, String[], String)} to retrieve
     * cursors for data being displayed, etc.
     *
     * <p>You can call {@link #finish} from within this function, in
     * which case onDestroy() will be immediately called without any of the rest
     * of the activity lifecycle ({@link #onStart}, {@link #onResume},
     * {@link #onPause}, etc) executing.
     *
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     * @see #onStart
     * @see #onSaveInstanceState
     * @see #onRestoreInstanceState
     * @see #onPostCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (D) Log.i(CLASSNAME, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        apiDAOFactory = (ApiDAOFactory) DAOFactory.getDAOFactory(DAOFactory.API);

        // buttons
        buttonNewOffer = (Button)findViewById(R.id.menu_buttonDriverNewOffer);
        buttonCancelOffer = (Button)findViewById(R.id.menu_buttonDriverCancelOffer);
        buttonBookings = (Button)findViewById(R.id.menu_buttonDriverBookings);
        buttonSearchOffer = (Button)findViewById(R.id.menu_buttonPedestrianSearchOffer);
        buttonCancelBooking = (Button)findViewById(R.id.menu_buttonPedestrianCancelBooking);
        buttonOffers = (Button)findViewById(R.id.menu_buttonPedestrianOffers);
        buttonLogout = (Button)findViewById(R.id.menu_buttonLogout);

        // listeners
        buttonNewOffer.setOnClickListener(this);
        buttonCancelOffer.setOnClickListener(this);
        buttonBookings.setOnClickListener(this);
        buttonSearchOffer.setOnClickListener(this);
        buttonCancelBooking.setOnClickListener(this);
        buttonOffers.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);

        // check if user logged in
        if (!PrefsHelper.hasSubscriber(this)) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }

    /**
     * Called after {@link #onRestoreInstanceState}, {@link #onRestart}, or
     * {@link #onPause}, for your activity to start interacting with the user.
     * This is a good place to begin animations, open exclusive-access devices
     * (such as the camera), etc.
     * <p/>
     * <p>Keep in mind that onResume is not the best indicator that your activity
     * is visible to the user; a system window such as the keyguard may be in
     * front.  Use {@link #onWindowFocusChanged} to know for certain that your
     * activity is visible to the user (for example, to resume a game).
     * <p/>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @see #onRestoreInstanceState
     * @see #onRestart
     * @see #onPostResume
     * @see #onPause
     */
    @Override
    protected void onResume() {
        if (D) Log.i(CLASSNAME, "onResume");

        super.onResume();

        if (PrefsHelper.hasOffer(this)) {
            buttonNewOffer.setEnabled(false);
            buttonCancelOffer.setEnabled(true);
            buttonBookings.setEnabled(true);
        } else {
            buttonNewOffer.setEnabled(true);
            buttonCancelOffer.setEnabled(false);
            buttonBookings.setEnabled(false);
        }
    }

    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (D) Log.i(CLASSNAME, "onActivityResult");
        if (D) Log.i(CLASSNAME, "requestCode = " + requestCode + " | resultCode : " + resultCode);

        super.onActivityResult(requestCode, resultCode, data);

        // for all requestCode
        switch (resultCode) {
            case RESULT_CANCELED:
                Toast toast = Toast.makeText(
                        getApplicationContext(),
                        getString(R.string.cancel_create_offer),
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 70);
                toast.show();
                break;
        }

        switch (requestCode) {
            case Consts.REQUEST_LOGIN:
                Toast toast = Toast.makeText(
                        getApplicationContext(),
                        "Login OK",
                        Toast.LENGTH_SHORT
                );
                toast.show();
                break;

            case Consts.REQUEST_NEW_OFFER:
                break;

            case Consts.REQUEST_BOOKINGS:
                break;
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (D) Log.i(CLASSNAME, "onClick : " + v.toString());

        switch (v.getId()) {
            case R.id.menu_buttonDriverNewOffer:
                startActivityForResult(
                        new Intent(getApplicationContext(), NewOfferActivity.class),
                        Consts.REQUEST_NEW_OFFER
                );
                break;

            case R.id.menu_buttonDriverCancelOffer:
                // run async task to handle offer cancellation
                new AsyncCancelOffer().execute();
                break;

            case R.id.menu_buttonDriverBookings:
                startActivityForResult(
                        new Intent(getApplicationContext(), BookingsActivity.class),
                        Consts.REQUEST_BOOKINGS
                );
                break;

            case R.id.menu_buttonLogout:
                PrefsHelper.removeSubscriber(this);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
        }

    }

    private class AsyncCancelOffer extends AsyncTask<String, String, String> {

        private final String INNER_CLASSNAME = getClass().getSimpleName();
        private final String SUCCESS_MESSAGE = MenuActivity.this.getString(R.string.success_cancel_offer);
        private final String FAILURE_MESSAGE = MenuActivity.this.getString(R.string.failure_cancel_offer);

        ProgressDialog progressDialog;

        /**
         * Runs on the UI thread before {@link #doInBackground}.
         *
         * @see #onPostExecute
         * @see #doInBackground
         */
        @Override
        protected void onPreExecute() {
            if (D) Log.i(INNER_CLASSNAME, "onPreExecute");
            super.onPreExecute();

            progressDialog = new ProgressDialog(MenuActivity.this);
            progressDialog.setMessage(getResources().getString(R.string.attempt_create_offer));
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected String doInBackground(String... params) {
            if (D) Log.i(INNER_CLASSNAME, "doInBackground");

            // Building Parameters
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("dao_action", DAOUtils.ACTION_REMOVE));
            parameters.add(new BasicNameValuePair(
                    "driver_id",
                    PrefsHelper.getSubscriberId(MenuActivity.this).toString()
            ));

            int result = 0;
            offerDAO = apiDAOFactory.getOfferDAO();
            try {
                result = offerDAO.remove(parameters);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (result == 1) {
                PrefsHelper.removeOffer(MenuActivity.this);

                startActivity(new Intent(getApplicationContext(), MenuActivity.class));

                return SUCCESS_MESSAGE;
            } else {
                return FAILURE_MESSAGE;
            }
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p/>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param s The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(String s) {
            if (D) Log.i(INNER_CLASSNAME, "onPostExecute");
            super.onPostExecute(s);

            progressDialog.dismiss();
            if (s != null){
                Toast toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 400);
                toast.show();
            }
        }
    }
}
