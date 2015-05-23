package fr.cnam.guillaumelimberger.cocotuture.driver;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.cnam.guillaumelimberger.cocotuture.BuildConfig;
import fr.cnam.guillaumelimberger.cocotuture.dao.ApiDAOFactory;
import fr.cnam.guillaumelimberger.cocotuture.dao.DAOFactory;
import fr.cnam.guillaumelimberger.cocotuture.dao.DAOUtils;
import fr.cnam.guillaumelimberger.cocotuture.dao.OfferDAO;
import fr.cnam.guillaumelimberger.cocotuture.EditTextValidator;
import fr.cnam.guillaumelimberger.cocotuture.entity.Offer;
import fr.cnam.guillaumelimberger.cocotuture.PrefsHelper;
import fr.cnam.guillaumelimberger.cocotuture.R;


public class NewOfferActivity extends Activity implements View.OnClickListener, View.OnFocusChangeListener, AdapterView.OnItemSelectedListener
{
    String errorMsg;

    private TextView textViewDriver;
    private EditText editTextModel;
    private EditText editTextLicense;
    private EditText editTextMeetingPlace;
    private EditText editTextMeetingTime;

    private Spinner colorSpinner;
    private Spinner destinationSpinner;
    private Spinner seatingSpinner;

    // buttons
    private Button buttonSubmit;
    private Button buttonCancel;

    private Offer offer = new Offer();
    private ApiDAOFactory apiDAOFactory;
    private OfferDAO offerDAO;

    /**
     * Called when the activity is starting.  This is where most initialization
     * should go: calling {@link #setContentView(int)} to inflate the
     * activity's UI, using {@link #findViewById} to programmatically interact
     * with widgets in the UI, calling
     * {@link #managedQuery(android.net.Uri, String[], String, String[], String)} to retrieve
     * cursors for data being displayed, etc.
     * <p/>
     * <p>You can call {@link #finish} from within this function, in
     * which case onDestroy() will be immediately called without any of the rest
     * of the activity lifecycle ({@link #onStart}, {@link #onResume},
     * {@link #onPause}, etc) executing.
     * <p/>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     * @see #onStart
     * @see #onSaveInstanceState
     * @see #onRestoreInstanceState
     * @see #onPostCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) Log.d(getLocalClassName(), "NewOfferActivity.onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_offer);

        apiDAOFactory = (ApiDAOFactory) DAOFactory.getDAOFactory(DAOFactory.API);

        // views
        textViewDriver = (TextView)findViewById(R.id.newOffer_textView_driver);
        editTextModel = (EditText)findViewById(R.id.new_offer_editText_model);
        editTextLicense = (EditText)findViewById(R.id.new_offer_editText_license);
        editTextMeetingPlace = (EditText)findViewById(R.id.new_offer_editText_meetingPlace);
        editTextMeetingTime = (EditText)findViewById(R.id.new_offer_editText_meetingTime);

        textViewDriver.setText(PrefsHelper.getSubscriberFirstName(this)+" "+PrefsHelper.getSubscriberLastName(this));

        // buttons
        buttonSubmit = (Button)findViewById(R.id.newOffer_buttonSubmit);
        buttonCancel = (Button)findViewById(R.id.newOffer_buttonCancel);

        // spinners
        colorSpinner = (Spinner)findViewById(R.id.newOffer_spinner_color);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.car_colors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapter);

        destinationSpinner = (Spinner)findViewById(R.id.newOffer_spinner_destination);
        ArrayAdapter<CharSequence> destinationAdapter =
                ArrayAdapter.createFromResource(this, R.array.destinations, android.R.layout.simple_spinner_item);
        destinationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destinationSpinner.setAdapter(destinationAdapter);

        seatingSpinner = (Spinner)findViewById(R.id.newOffer_spinner_seating);
        ArrayAdapter<CharSequence> seatingAdapter =
                ArrayAdapter.createFromResource(this, R.array.seating, android.R.layout.simple_spinner_item);
        seatingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seatingSpinner.setAdapter(seatingAdapter);

        // listeners
        buttonSubmit.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
        colorSpinner.setOnItemSelectedListener(this);
        destinationSpinner.setOnItemSelectedListener(this);
        seatingSpinner.setOnItemSelectedListener(this);
        editTextMeetingPlace.setOnFocusChangeListener(this);

//        // build the query
//        String[] mProjection =
//                {
//                        SubscriberProvider.SUBSCRIBER_FIRST_NAME,
//                        SubscriberProvider.SUBSCRIBER_LAST_NAME
//                };
//        String mSelectionClause = SubscriberProvider.SUBSCRIBER_LOGIN + " = ?";
//        String[] mSelectionArgs = { name };
//        Cursor mCursor = getContentResolver().query(
//                SubscriberProvider.CONTENT_URI,     // The content URI of the subscriber table
//                mProjection,                        // The columns to return for each row
//                mSelectionClause,                   // Selection criteria
//                mSelectionArgs,                     // Selection criteria
//                null);
//
//        if (mCursor != null) {
//            if (mCursor.getCount() > 0) { // at least one record
//                int mFirstNameIndex = mCursor.getColumnIndex(SubscriberProvider.SUBSCRIBER_FIRST_NAME);
//                int mLastNameIndex = mCursor.getColumnIndex(SubscriberProvider.SUBSCRIBER_LAST_NAME);
//                mCursor.moveToFirst();
//                String firstName = mCursor.getString(mFirstNameIndex);
//                String lastName = mCursor.getString(mLastNameIndex);
//                mCursor.close();
//                editTextFirstName.setText(firstName);
//                editTextLastName.setText(lastName);
//            }
//        }
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

        offerDAO = apiDAOFactory.getOfferDAO();
    }

    /**
     * <p>Callback method to be invoked when an item in this view has been
     * selected. This callback is invoked only when the newly selected
     * position is different from the previously selected position or if
     * there was no selected item.</p>
     * <p/>
     * Impelmenters can call getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param parent   The AdapterView where the selection happened
     * @param view     The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id       The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (BuildConfig.DEBUG) Log.d("NewOfferActivity", "onItemSelected() : color = "+parent.getItemAtPosition(position).toString());

        switch (view.getId()) {
            case R.id.newOffer_spinner_color:
                if (BuildConfig.DEBUG) Log.d("NewOfferActivity", "onItemSelected() : color = "+parent.getItemAtPosition(position).toString());

                offer.setCarColor(parent.getItemAtPosition(position).toString());
                break;

            case R.id.newOffer_spinner_destination:
                if (BuildConfig.DEBUG) Log.d("NewOfferActivity", "onItemSelected() : destination = "+parent.getItemAtPosition(position).toString());

                offer.setDestination(parent.getItemAtPosition(position).toString());
                break;

            case R.id.newOffer_spinner_seating:
                if (BuildConfig.DEBUG) Log.d("NewOfferActivity", "onItemSelected() : color = "+parent.getItemAtPosition(position).toString());

                offer.setSeating(Integer.parseInt(parent.getItemAtPosition(position).toString()));
                break;
        }

    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newOffer_buttonSubmit:
                if (BuildConfig.DEBUG) Log.d("NewOfferActivity", "buttonSubmit onClick()");

                // form validation
                boolean isFormValid = true;

                if (EditTextValidator.hasTextValidation(this, editTextModel)) {
                    if (BuildConfig.DEBUG) Log.d("NewOfferActivity", "model validation : "+editTextModel.getText().toString());

                    offer.setCarModel(editTextModel.getText().toString());
                } else {
                    isFormValid = false;
                }
                if (EditTextValidator.hasTextValidation(this, editTextLicense)) {
                    if (BuildConfig.DEBUG) Log.d("NewOfferActivity", "license validation : "+editTextLicense.getText().toString());

                    offer.setCarLicenseNumber(editTextLicense.getText().toString());
                } else {
                    isFormValid = false;
                }
                if (EditTextValidator.hasTextValidation(this, editTextMeetingPlace)) {
                    if (BuildConfig.DEBUG) Log.d("NewOfferActivity", "meeting place validation : "+editTextMeetingPlace.getText().toString());

                    offer.setMeetingPlace(editTextMeetingPlace.getText().toString());
                } else {
                    isFormValid = false;
                }
                if (EditTextValidator.hasTimeValidation(this, editTextMeetingTime)) {
                    if (BuildConfig.DEBUG) Log.d("NewOfferActivity", "meeting time validation : "+editTextMeetingPlace.getText().toString());

                    offer.setMeetingTime(editTextMeetingTime.getText().toString());
                }
                if (BuildConfig.DEBUG) Log.d("NewOfferActivity", "color validation : "+colorSpinner.getSelectedItem().toString());
                if (colorSpinner.getSelectedItem().toString().equals("")) {
                    isFormValid = false;
                } else {
                    offer.setCarColor(colorSpinner.getSelectedItem().toString());
                }
                if (BuildConfig.DEBUG) Log.d("NewOfferActivity", "destination validation : "+destinationSpinner.getSelectedItem().toString());
                if (destinationSpinner.getSelectedItem().toString().equals("")) {
                    isFormValid = false;
                } else {
                    offer.setDestination(destinationSpinner.getSelectedItem().toString());
                }
                if (BuildConfig.DEBUG) Log.d("NewOfferActivity", "seating validation : "+seatingSpinner.getSelectedItem().toString());
                if (seatingSpinner.getSelectedItem().toString().equals("")) {
                    isFormValid = false;
                } else {
                    offer.setSeating(Integer.parseInt(seatingSpinner.getSelectedItem().toString()));
                }

                if (isFormValid) {
                    if (BuildConfig.DEBUG) Log.d("NewOfferActivity", "Form is valid");

                    offer.setDriverId(PrefsHelper.getSubscriberId(this));
                    if (BuildConfig.DEBUG) Log.d("NewOfferActivity", offer.toString());
                    new AsyncCreateOffer().execute();
                }
                break;

            case R.id.newOffer_buttonCancel:
                setResult(RESULT_CANCELED);
                finish();
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
        switch (v.getId()) {

            case R.id.new_offer_editText_meetingPlace:
                if (hasFocus) {
                    editTextMeetingPlace.setTypeface(null, Typeface.NORMAL);
                } else {
                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocationName(editTextMeetingPlace.getText().toString(), 1);
                    } catch(IOException ioException) {
                        errorMsg = getString(R.string.error_service_not_available);
                        Log.e(getLocalClassName(), errorMsg, ioException);
                    }

                    if (addresses != null && addresses.size() > 0) {
                        double latitude= addresses.get(0).getLatitude();
                        double longitude= addresses.get(0).getLongitude();
                        String formattedAddress = addresses.get(0).getAddressLine(0)+" "+
                                addresses.get(0).getPostalCode()+" "+
                                addresses.get(0).getLocality();

                        editTextMeetingPlace.setTypeface(null, Typeface.ITALIC);
                        editTextMeetingPlace.setText(formattedAddress);

                        offer.setLatitude(latitude);
                        offer.setLongitude(longitude);

                        Log.d(getLocalClassName(), "getFromLocationName() address = "+addresses.get(0).toString());
                    } else {
                        editTextMeetingPlace.setError(getResources().getString(R.string.error_address));
                        errorMsg = getString(R.string.error_address);
                        Log.e(getLocalClassName(), errorMsg);
                    }
                }
                break;
        }
    }

    private class AsyncCreateOffer extends AsyncTask<String, String, String> {

        private final String SUCCESS_MESSAGE = NewOfferActivity.this.getString(R.string.success_create_offer);
        private final String FAILURE_MESSAGE = NewOfferActivity.this.getString(R.string.failure_create_offer);

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

            progressDialog = new ProgressDialog(NewOfferActivity.this);
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

            // Building Parameters
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("dao_action", DAOUtils.ACTION_CREATE));
            parameters.add(new BasicNameValuePair("driver_id", offer.getDriverId().toString()));
            parameters.add(new BasicNameValuePair("model", offer.getCarModel()));
            parameters.add(new BasicNameValuePair("color", offer.getCarColor()));
            parameters.add(new BasicNameValuePair("license", offer.getCarLicenseNumber()));
            parameters.add(new BasicNameValuePair("meeting_place", offer.getMeetingPlace()));
            parameters.add(new BasicNameValuePair("latitude", offer.getLatitude().toString()));
            parameters.add(new BasicNameValuePair("longitude", offer.getLongitude().toString()));
            parameters.add(new BasicNameValuePair("destination", offer.getDestination()));
            parameters.add(new BasicNameValuePair("meeting_time", offer.getMeetingTime()));
            parameters.add(new BasicNameValuePair("seating", offer.getSeating().toString()));

            Log.d("NewOfferActivity", "AsyncCreateOffer : request starting");
            int result = 0;

            try {
                result = offerDAO.create(parameters);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (result == 1) {
                Log.d("NewOfferActivity", "AsyncCreateOffer : Success! ");

                PrefsHelper.setOffer(NewOfferActivity.this, offer);
                startActivity(new Intent(getApplicationContext(), NewOfferOKActivity.class));

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
                Toast toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 400);
                toast.show();
            }
        }
    }
}