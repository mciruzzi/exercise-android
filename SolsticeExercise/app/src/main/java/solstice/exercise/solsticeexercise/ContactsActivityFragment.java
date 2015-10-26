package solstice.exercise.solsticeexercise;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import solstice.exercise.solsticeexercise.adapters.ContactsAdapter;

public class ContactsActivityFragment extends Fragment {

    ListView contactsListView;

    public ContactsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.contactsListView = (ListView) view.findViewById(R.id.contactsListView);
        this.contactsListView.setAdapter( new ContactsAdapter((ContactsActivity) this.getActivity()) );

    }


}
