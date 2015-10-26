package solstice.exercise.solsticeexercise;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import solstice.exercise.solsticeexercise.model.Contact;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContactDetailsActivityFragment extends Fragment {

    public ContactDetailsActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Contact contact = (Contact) this.getActivity().getIntent().getSerializableExtra( ContactDetailsActivity.CONTACT_ID_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_details, container, false);
    }
}
