package fr.cnam.guillaumelimberger.cocotuture.driver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.cnam.guillaumelimberger.cocotuture.MenuActivity;
import fr.cnam.guillaumelimberger.cocotuture.R;

public class NewOfferOKActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_offer_ok);

        Button buttonOK = (Button) findViewById(R.id.offerOK_buttonOK);
        buttonOK.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.offerOK_buttonOK:
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                intent.putExtra("offer_created", true);
                startActivity(intent);
                break;
        }
    }
}
