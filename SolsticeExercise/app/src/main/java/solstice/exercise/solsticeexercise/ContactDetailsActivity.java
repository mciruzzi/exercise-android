package solstice.exercise.solsticeexercise;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import solstice.exercise.solsticeexercise.model.Contact;

public class ContactDetailsActivity extends ActionBarActivity {

    public final static String CONTACT_ID_KEY = "ContactID-Key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();
        Contact contact = (Contact) b.getSerializable(CONTACT_ID_KEY);
    }

}
