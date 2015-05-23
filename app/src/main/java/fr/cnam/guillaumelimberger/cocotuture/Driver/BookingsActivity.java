package fr.cnam.guillaumelimberger.cocotuture.driver;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import fr.cnam.guillaumelimberger.cocotuture.BuildConfig;
import fr.cnam.guillaumelimberger.cocotuture.Consts;
import fr.cnam.guillaumelimberger.cocotuture.PrefsHelper;
import fr.cnam.guillaumelimberger.cocotuture.R;
import fr.cnam.guillaumelimberger.cocotuture.entity.Subscriber;

public class BookingsActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        String destinationMsg = "Pour la destination de " + PrefsHelper.getOfferDestination(this);
        TextView textViewDestination = (TextView)findViewById(R.id.bookings_textView_destination);
        textViewDestination.setText(destinationMsg);

        Button buttonOK = (Button)findViewById(R.id.bookings_buttonOK);
        buttonOK.setOnClickListener(this);

        ListView listView = (ListView)findViewById(R.id.bookings_listView);
        ArrayList<Subscriber> arrayListPedestrian = new ArrayList<Subscriber>();
        arrayListPedestrian.add(new Subscriber("John", "Doe", "0102030405", "email@example.com", "johnd", "120872"));

        ArrayAdapter<Subscriber> arrayAdapterSubscriber =
                new ArrayAdapter<Subscriber>(this, R.layout.row_pedestrians, arrayListPedestrian);
        listView.setAdapter(arrayAdapterSubscriber);
        listView.setOnItemClickListener(this);

        Integer availableSeats = PrefsHelper.getOfferSeating(this) - listView.getAdapter().getCount();
        String seatingMsg = "Nombre de places disponibles restantes : " + availableSeats.toString();
        TextView textViewSeating = (TextView)findViewById(R.id.bookings_textView_seating);
        textViewSeating.setText(seatingMsg);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bookings_buttonOK:
                setResult(Consts.RESULT_BOOKINGS);
                finish();
                break;
        }
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p/>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Subscriber s = (Subscriber)parent.getItemAtPosition(position);
        if (BuildConfig.DEBUG) Log.d(getLocalClassName(), "onItemClick() : "+ s.toString());

        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", s.getPhone(), null));
        startActivity(intent);
    }
}
