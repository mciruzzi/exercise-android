package solstice.exercise.solsticeexercise;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import solstice.exercise.solsticeexercise.model.Contact;

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
        this.contactsListView.setAdapter( new ContactsAdapter(this.getLayoutInflater(savedInstanceState)) );

    }

    /************************Adapter for contacs************************/

    private class ContactsAdapter extends BaseAdapter {

        private final LayoutInflater layoutInflater;
        List<Contact> contacts = new ArrayList<Contact>();

        public ContactsAdapter(LayoutInflater layoutInflater) {
            this.layoutInflater  = layoutInflater;
        }

        @Override
        public int getCount() {
            return contacts.size();
        }

        @Override
        public Object getItem(int position) {
            return contacts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Not using ViewHolder Pattern
            View row = null;
            if (convertView == null ){
                row = layoutInflater.inflate(R.layout.contact_in_list ,parent ,false);
            }
            else{
                row= convertView;
            }

            Contact contact = (Contact) this.getItem(position);

            ImageView image = (ImageView) row.findViewById(R.id.contactImage);
            TextView name = (TextView) row.findViewById(R.id.contactName);
            TextView phone = (TextView) row.findViewById(R.id.contactPhone);

            //image.setImageResource();
            name.setText(contact.getName());
            phone.setText(contact.getHomePhone());

            return row;
        }
    }
}
