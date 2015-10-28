package solstice.exercise.solsticeexercise.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import solstice.exercise.solsticeexercise.ContactsActivity;
import solstice.exercise.solsticeexercise.ILoadable;
import solstice.exercise.solsticeexercise.R;
import solstice.exercise.solsticeexercise.model.Contact;
import solstice.exercise.solsticeexercise.rest.client.utils.ContactsAPI;
import solstice.exercise.solsticeexercise.rest.client.utils.Routes;

public class ContactsAdapter extends ArrayAdapter {

    private static final String TAG = "ContactsAdapter";

    private List<Contact> contacts = new ArrayList<Contact>();
    private final ILoadable showProgress;

    public ContactsAdapter(ContactsActivity activity) {
        super(activity, 0);
        showProgress = activity;

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Routes.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ContactsAPI contactsAPI = retrofit.create(ContactsAPI.class);

        contactsAPI.getContacts().enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(retrofit.Response<List<Contact>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    showProgress.showProgress(false);
                    contacts = response.body();
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                showProgress.showProgress(false);
                Log.d(TAG, "Error Respuesta en JSON: " + t.getMessage());
            }
        });

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

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View row;
        if (convertView == null) {
            row = layoutInflater.inflate(R.layout.contact_in_list, parent, false);
        } else {
            row = convertView;
        }

        final Contact contact = (Contact) this.getItem(position);

        // TODO Implement View Holder Pattern
        final ImageView image = (ImageView) row.findViewById(R.id.contactImage);
        final TextView name = (TextView) row.findViewById(R.id.contactName);
        final TextView phone = (TextView) row.findViewById(R.id.contactPhone);
        image.setTag(position);

        name.setText(contact.getName());
        phone.setText(contact.getPhone().getHome());
        image.setImageResource(R.mipmap.default_profile);

        Picasso.with(this.getContext()).load(contact.getSmallImageURL()).placeholder(R.mipmap.default_profile).into(image);

        return row;
    }

}
