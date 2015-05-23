package fr.cnam.guillaumelimberger.cocotuture;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import java.util.List;
import fr.cnam.guillaumelimberger.cocotuture.dao.ApiDAOFactory;
import fr.cnam.guillaumelimberger.cocotuture.dao.DAOFactory;
import fr.cnam.guillaumelimberger.cocotuture.dao.SubscriberDAO;
import fr.cnam.guillaumelimberger.cocotuture.entity.Offer;
import fr.cnam.guillaumelimberger.cocotuture.entity.Subscriber;


/**
 * Activité gérant la connexion de l'utilisateur
 *
 * @author Guillaume Limberger <glim.dev@gmail.com>
 */
public class LoginActivity extends Activity implements OnClickListener, OnTouchListener
{
    private final String CLASSNAME = getClass().getSimpleName();
    private final boolean D = BuildConfig.DEBUG;

    // DAO Factory
    private ApiDAOFactory apiDAOFactory;

    // views
    private EditText editTextLogin;
    private EditText editTextPassword;
    private TextView textViewMessage;

    private ProgressDialog pDialog;

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
        setContentView(R.layout.activity_login);

        // views
        editTextLogin = (EditText) findViewById(R.id.editTextLogin);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewMessage = (TextView)findViewById(R.id.textViewMessage);

        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        Button buttonPwd = (Button) findViewById(R.id.buttonSignIn);

        // listeners
        buttonLogin.setOnClickListener(this);
        buttonPwd.setOnClickListener(this);
        editTextLogin.setOnTouchListener(this);
        editTextPassword.setOnTouchListener(this);


        // message
        textViewMessage.setText(getResources().getString(R.string.info_welcome));
        textViewMessage.setTextColor(getResources().getColor(R.color.color_alert_success_text));
        textViewMessage.setBackground(getResources().getDrawable(R.drawable.alert_success_shape));
        textViewMessage.setVisibility(View.VISIBLE);

        apiDAOFactory = (ApiDAOFactory)DAOFactory.getDAOFactory(DAOFactory.API);
    }

    /**
     * Called when you are no longer visible to the user.  You will next
     * receive either {@link #onRestart}, {@link #onDestroy}, or nothing,
     * depending on later user activity.
     * <p/>
     * <p>Note that this method may never be called, in low memory situations
     * where the system does not have enough memory to keep your activity's
     * process running after its {@link #onPause} method is called.
     * <p/>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @see #onRestart
     * @see #onResume
     * @see #onSaveInstanceState
     * @see #onDestroy
     */
    @Override
    protected void onStop() {
        if (D) Log.i(CLASSNAME, "onStop");
        super.onStop();

        // hide textView message
        textViewMessage.setVisibility(View.INVISIBLE);

        // reset editText views
        editTextLogin.setText("");
        editTextPassword.setText("");
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

        switch (requestCode) {
            case Consts.REQUEST_REGISTER:
                switch (resultCode) {
                    case RESULT_OK:
                        // success message
                        String msg = getResources().getString(R.string.register_ok_message1) +
                                PrefsHelper.getSubscriberFirstName(this) +
                                getResources().getString(R.string.register_ok_message2);
                        textViewMessage.setText(msg);
                        textViewMessage.setTextColor(getResources().getColor(R.color.color_alert_success_text));
                        textViewMessage.setBackground(getResources().getDrawable(R.drawable.alert_success_shape));
                        textViewMessage.setVisibility(View.VISIBLE);
//                        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
//                        toast.setGravity(Gravity.CENTER, 0, 0);
//                        toast.show();
                        break;

                    case RESULT_CANCELED:
                        textViewMessage.setText(getString(R.string.cancel_register));
                        textViewMessage.setTextColor(getResources().getColor(R.color.color_alert_info_text));
                        textViewMessage.setBackground(getResources().getDrawable(R.drawable.alert_info_shape));
                        textViewMessage.setVisibility(View.VISIBLE);
                        break;


                }
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
        if (D) Log.i(CLASSNAME, "onCLick : " + v.toString());

        switch (v.getId()) {
            case R.id.buttonLogin:
                if (BuildConfig.DEBUG) Log.d("LoginActivity", "onClickLogin()");

                // form validation
                if (EditTextValidator.hasTextValidation(this, editTextLogin) &&
                        EditTextValidator.hasTextValidation(this, editTextLogin)) {
                    if (BuildConfig.DEBUG) {
                        Log.d("LoginActivity", "Credentials OK");
                        Log.d("LoginActivity", "Attempt to login on remote database");
                    }
                    new AttemptLogin().execute();

                } else {
                    if (BuildConfig.DEBUG) Log.d("LoginActivity", "Credentials NOT VALID");
                    textViewMessage.setText(getString(R.string.error_empty_login_pwd));
                    textViewMessage.setTextColor(getResources().getColor(R.color.color_alert_danger_text));
                    textViewMessage.setBackground(getResources().getDrawable(R.drawable.alert_danger_shape));
                    textViewMessage.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.buttonSignIn:
                if (BuildConfig.DEBUG)
                    Log.d("LoginActivity", "onClickSignIn()");

                startActivityForResult(
                        new Intent(getApplicationContext(), RegisterActivity.class),
                        Consts.REQUEST_REGISTER);
                break;
        }
    }

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (D) Log.i(CLASSNAME, "onTouch : " + v.toString());

        switch (v.getId()) {
            case R.id.editTextLogin:
            case R.id.editTextPassword:
                textViewMessage.setVisibility(View.INVISIBLE);
                break;
        }
        return false;
    }

//    private boolean isAuthentificationOK(String login, String password, TextView msg)
//    {
//        // build the query
//        String[] mProjection =
//                {
//                        SubscriberProvider.SUBSCRIBER_ID,
//                        SubscriberProvider.SUBSCRIBER_FIRST_NAME,
//                        SubscriberProvider.SUBSCRIBER_LAST_NAME
//                };
//        String mSelectionClause = SubscriberProvider.SUBSCRIBER_LOGIN + " = ? AND " + SubscriberProvider.SUBSCRIBER_PASSWORD + " = ?";
//        String[] mSelectionArgs = { login, password };
//        Cursor mCursor = getContentResolver().query(
//                SubscriberProvider.CONTENT_URI,     // The content URI of the subscriber table
//                mProjection,                        // The columns to return for each row
//                mSelectionClause,                   // Selection criteria
//                mSelectionArgs,                     // Selection criteria
//                null);
//
//        if (mCursor != null)
//        {
//            if (mCursor.getCount() > 0) { // at least one record
//                int mFirstNameIndex = mCursor.getColumnIndex(SubscriberProvider.SUBSCRIBER_FIRST_NAME);
//                int mLastNameIndex  = mCursor.getColumnIndex(SubscriberProvider.SUBSCRIBER_LAST_NAME);
//                mCursor.moveToFirst();
//                String mFirstName = mCursor.getString(mFirstNameIndex);
//                String rmLastName  = mCursor.getString(mLastNameIndex);
//                mCursor.close();
//                msg.setText("");
//
//                Toast toast = Toast.makeText(
//                        this,
//                        getResources().getString(R.string.login_message),
//                        Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.TOP, 0, 70);
//                toast.show();
//
//                return true;
//            }
//
//            mCursor.close();
//            msg.setText("Login ou mot de passe erroné.");
//            msg.setTextColor(getResources().getColor(R.color.color_alert_danger_text));
//            msg.setBackground(getResources().getDrawable(R.drawable.alert_danger_shape));
//            msg.setVisibility(View.VISIBLE);
//        }
//
//        return false;
//    }

    /**
     * Async task that manages the login request attempt
     */
    class AttemptLogin extends AsyncTask<String, String, String> {

        private final String INNER_CLASSNAME = getClass().getSimpleName();

        private final String SUCCESS_MESSAGE = LoginActivity.this.getString(R.string.success_auth);
        private final String FAILURE_MESSAGE = LoginActivity.this.getString(R.string.failure_auth);

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

            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage(getResources().getString(R.string.attempt_login));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
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

            // credentials to test
            String login = editTextLogin.getText().toString();
            String password = editTextPassword.getText().toString();

            // Building Parameters
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("login", login));
            parameters.add(new BasicNameValuePair("password", password));

            SubscriberDAO subscriberDAO = apiDAOFactory.getSubscriberDAO();
            ArrayList<Object> arrayList = subscriberDAO.find(parameters);
            Subscriber subscriber = (Subscriber)arrayList.get(0);
            Offer offer = (Offer)arrayList.get(1);
            if (subscriber != null && subscriber.getLogin() != null) {
                // subscriber is authenticated
                // set Prefs
                PrefsHelper.setSubscriberId(LoginActivity.this, subscriber.getId());
                PrefsHelper.setSubscriberLogin(LoginActivity.this, subscriber.getLogin());
                PrefsHelper.setSubscriberFirstName(LoginActivity.this, subscriber.getFirstName());
                PrefsHelper.setSubscriberLastName(LoginActivity.this, subscriber.getLastName());

                PrefsHelper.setOffer(LoginActivity.this, offer);

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
         * @param message The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(String message) {
            if (D) Log.i(INNER_CLASSNAME, "onPostExecute");
            super.onPostExecute(message);

            pDialog.dismiss();
            if (message != null){
                Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }
}
