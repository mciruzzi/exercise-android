package solstice.exercise.solsticeexercise;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import solstice.exercise.solsticeexercise.adapters.ContactsAdapter;
import solstice.exercise.solsticeexercise.model.Contact;

public class ContactsActivityFragment extends Fragment {

    ListView contactsListView;
    private ContactsAdapter contactsAdapter;


    public ContactsActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactsAdapter = new ContactsAdapter((ContactsActivity) this.getActivity());
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
        this.contactsListView.setAdapter(contactsAdapter);

        this.contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = (Contact) contactsAdapter.getItem(position);

                Bundle b = new Bundle();
                b.putSerializable(ContactDetailsActivity.CONTACT_ID_KEY ,contact);

                Intent intent = new Intent(getActivity(), ContactDetailsActivity.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });


    }


}
