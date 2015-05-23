package fr.cnam.guillaumelimberger.cocotuture;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import java.util.List;
import fr.cnam.guillaumelimberger.cocotuture.dao.ApiDAOFactory;
import fr.cnam.guillaumelimberger.cocotuture.dao.DAOFactory;
import fr.cnam.guillaumelimberger.cocotuture.dao.SubscriberDAO;
import fr.cnam.guillaumelimberger.cocotuture.entity.Subscriber;

/**
 * @author <a href="mailto:glim.dev@gmail.com">Guillaume Limberger</a>
 */
public class RegisterActivity extends Activity implements OnClickListener, View.OnFocusChangeListener
{
    // editText
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextPhoneNumber;
    private EditText editTextEmail;
    private EditText editTextLogin;
    private EditText editTextPassword1;
    private EditText editTextPassword2;

    Subscriber subscriber = new Subscriber();

    private ApiDAOFactory apiDAOFactory;
    private SubscriberDAO subscriberDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) Log.d("LoginActivity", "RegisterActivity.onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // views
        Button buttonSubmit = (Button) findViewById(R.id.register_buttonSubmit);
        Button buttonCancel = (Button) findViewById(R.id.register_buttonCancel);
        editTextFirstName = (EditText)findViewById(R.id.register_editText_firstName);
        editTextLastName = (EditText)findViewById(R.id.register_editText_lastName);
        editTextPhoneNumber = (EditText)findViewById(R.id.register_editText_phone);
        editTextEmail = (EditText)findViewById(R.id.register_editText_email);
        editTextLogin = (EditText)findViewById(R.id.register_editText_login);
        editTextPassword1 = (EditText)findViewById(R.id.register_editText_password);
        editTextPassword2 = (EditText)findViewById(R.id.register_editText_password2);

        // set listeners
        buttonSubmit.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
        editTextLogin.setOnFocusChangeListener(this);

        apiDAOFactory = (ApiDAOFactory) DAOFactory.getDAOFactory(DAOFactory.API);
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
        super.onResume();

        subscriberDAO = apiDAOFactory.getSubscriberDAO();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_buttonCancel:
                if (BuildConfig.DEBUG) Log.d("RegisterActivity", "buttonCancel onClick()");
                setResult(RESULT_CANCELED);
                finish();
//                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;

            case R.id.register_buttonSubmit:
                if (BuildConfig.DEBUG) Log.d("RegisterActivity", "buttonSubmit onClick()");

                // form validation
                boolean isFormValid = true;
                if (EditTextValidator.hasTextValidation(this, editTextFirstName)) {
                    subscriber.setFirstName(editTextFirstName.getText().toString());
                } else {
                    isFormValid = false;
                }
                if (EditTextValidator.hasTextValidation(this, editTextLastName)) {
                    subscriber.setLastName(editTextLastName.getText().toString());
                } else {
                    isFormValid = false;
                }
                if (EditTextValidator.hasPhoneValidation(this, editTextPhoneNumber)) {
                    subscriber.setPhone(editTextPhoneNumber.getText().toString());
                } else {
                    isFormValid = false;
                }
                if (EditTextValidator.hasEmailValidation(this, editTextEmail)) {
                    subscriber.setEmail(editTextEmail.getText().toString());
                } else {
                    isFormValid = false;
                }
                if (EditTextValidator.hasLoginValidation(this, editTextLogin)) {
                    subscriber.setLogin(editTextLogin.getText().toString());
                } else {
                    isFormValid = false;
                }
                if (EditTextValidator.hasPasswordValidation(this, editTextPassword1, editTextPassword2)) {
                    subscriber.setPassword(editTextPassword1.getText().toString());
                } else {
                    isFormValid = false;
                }

                if (isFormValid) {
                    if (BuildConfig.DEBUG) Log.d("RegisterActivity", "Form is valid");

                    new AsyncCreateSubscriber().execute();

//                    // persist subscriber
//                    ContentResolver resolver = getContentResolver();
//                    Uri uri = SubscriberProvider.CONTENT_URI;
//                    ContentValues cv = new ContentValues();
//                    cv.put(SubscriberProvider.SUBSCRIBER_FIRST_NAME, subscriber.getFirstName());
//                    cv.put(SubscriberProvider.SUBSCRIBER_LAST_NAME, subscriber.getLastName());
//                    cv.put(SubscriberProvider.SUBSCRIBER_PHONE_NUMBER, subscriber.getPhone());
//                    cv.put(SubscriberProvider.SUBSCRIBER_EMAIL, subscriber.getEmail());
//                    cv.put(SubscriberProvider.SUBSCRIBER_LOGIN, subscriber.getLogin());
//                    cv.put(SubscriberProvider.SUBSCRIBER_PASSWORD, subscriber.getPassword());
//                    Uri uri2 = resolver.insert(uri, cv);
//                    if (BuildConfig.DEBUG)
//                        Log.d("RegisterActivity", "Add subscriber to database : "+subscriber.toString());
//
                }
                break;
        }
    }

    /**
     * Called when the focus state of a view has changed.
     *
     * @param v        The view whose state has changed.
     * @param hasFocus The new focus state of v.
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (BuildConfig.DEBUG) Log.d("RegisterActivity", "onFocusChange() : focus changed");
        if (editTextLogin.getText().toString().length() >= 5) {
//            v.requestFocus();
            new AsyncCheckLogin().execute();
        }
    }


    private class AsyncCreateSubscriber extends AsyncTask<String, String, String> {

        private final String SUCCESS_MESSAGE = RegisterActivity.this.getString(R.string.success_register);
        private final String FAILURE_MESSAGE = RegisterActivity.this.getString(R.string.failure_register);

        ProgressDialog progressDialog;


        /**
         * Runs on the UI thread before {@link #doInBackground}.
         *
         * @see #onPostExecute
         * @see #doInBackground
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage(getResources().getString(R.string.attempt_create_subscriber));
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

            // Building Parameters
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("firstname", subscriber.getFirstName()));
            parameters.add(new BasicNameValuePair("lastname", subscriber.getLastName()));
            parameters.add(new BasicNameValuePair("phone", subscriber.getPhone()));
            parameters.add(new BasicNameValuePair("email", subscriber.getEmail()));
            parameters.add(new BasicNameValuePair("login", subscriber.getLogin()));
            parameters.add(new BasicNameValuePair("password", subscriber.getPassword()));

            Log.d("RegisterActivity", "AsyncCreateSubscriber : request! starting");
            int result = subscriberDAO.create(parameters);
            if (result == 1) {
                Log.d("RegisterActivity", "AsyncCreateSubscriber : Success! ");

                // set Prefs
                PrefsHelper.setSubscriberId(RegisterActivity.this, subscriber.getId());
                PrefsHelper.setSubscriberLogin(RegisterActivity.this, subscriber.getLogin());
                PrefsHelper.setSubscriberFirstName(RegisterActivity.this, subscriber.getFirstName());
                PrefsHelper.setSubscriberLastName(RegisterActivity.this, subscriber.getLastName());

//                Intent intent = new Intent();
//                intent.putExtra("login", subscriber.getLogin());
//                intent.putExtra("password", subscriber.getPassword());
//                setResult(RESULT_OK, intent);
                setResult(RESULT_OK);
                finish();

                return SUCCESS_MESSAGE;
            } else {
                Log.d("RegisterActivity", "AsyncCreateSubscriber : Failure! ");

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
            super.onPostExecute(s);

            progressDialog.dismiss();
            if (s != null){
                Toast toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 400);
                toast.show();
            }
        }
    }

    private class AsyncCheckLogin extends AsyncTask<String, String, String> {

        private final String ERROR_MESSAGE = RegisterActivity.this.getString(R.string.error_login_uniqueness);

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
            // check login
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("login", editTextLogin.getText().toString()));
            Subscriber subscriberCheck = subscriberDAO.check(parameters);
            if (subscriberCheck.getLogin() != null) {
               return ERROR_MESSAGE;
            }
            return null;
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
            super.onPostExecute(s);

            if (s != null) {
                editTextLogin.setError(s);
            }
        }
    }
}
