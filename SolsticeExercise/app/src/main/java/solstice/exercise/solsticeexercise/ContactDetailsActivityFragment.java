package solstice.exercise.solsticeexercise;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import solstice.exercise.solsticeexercise.model.Contact;
import solstice.exercise.solsticeexercise.model.ContactDetails;
import solstice.exercise.solsticeexercise.rest.client.utils.ContactsAPI;
import solstice.exercise.solsticeexercise.rest.client.utils.Routes;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContactDetailsActivityFragment extends Fragment {

    private final String TAG = "ContactDetailsFragment";
    private Contact contact;
    private View contactDetailsView;

    public ContactDetailsActivityFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contact = (Contact) this.getActivity().getIntent().getSerializableExtra( ContactDetailsActivity.CONTACT_ID_KEY);
        queryContactDetails();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_contact_details, container, false);
    }

    private void queryContactDetails() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Routes.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ContactsAPI contactsAPI = retrofit.create(ContactsAPI.class);

        contactsAPI.getContactInfo(contact.getEmployeeId()).enqueue(new Callback<ContactDetails>() {
            @Override
            public void onResponse(retrofit.Response<ContactDetails> response, Retrofit retrofit) {
                if (response.isSuccess())
                    contact.setDetails( response.body());
                    populateContactInfo( contactDetailsView);
            }

            @Override
            public void onFailure(Throwable t) {
                    Log.d(TAG, "Error Respuesta en JSON: " + t.getMessage());
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.contactDetailsView = view;
        populateContactInfo(view);
    }

    private void populateContactInfo(View view) {
        // TODO Implement View Holder Pattern
        TextView name = (TextView) view.findViewById(R.id.contactName);
        TextView company = (TextView) view.findViewById(R.id.contactCompany);
        TextView phone = (TextView) view.findViewById(R.id.contactPhone);
        TextView address = (TextView) view.findViewById(R.id.contactAddress);
        TextView birthday = (TextView) view.findViewById(R.id.contactBirthday);
        TextView email = (TextView) view.findViewById(R.id.contactEmail);
        ImageView favorite = (ImageView) view.findViewById(R.id.contactFavorite);
        ImageView largeImage = (ImageView) view.findViewById(R.id.contactImage);

        name.setText(contact.getName());
        company.setText(contact.getCompany());
        phone.setText(contact.getPhone().toString());

        Date birthDate = new Date( contact.getBirthdate() );
        birthday.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(birthDate));

        String emailString = contact.getDetails() != null ? contact.getDetails().getEmail() : "";
        email.setText(emailString);
        String addressString = contact.getDetails() != null ? contact.getDetails().getAddress().toString() : "";
        address.setText(addressString);

        boolean isFavorite = contact.getDetails() != null ? contact.getDetails().isFavorite() : false;
        if (isFavorite)
            favorite.setVisibility(View.VISIBLE);
        else
            favorite.setVisibility(View.INVISIBLE);

        loadImage(largeImage);
    }

    private void loadImage(ImageView imageView) {
        Picasso.with(this.getActivity()).load(contact.getSmallImageURL()).noFade().placeholder(R.mipmap.default_profile).into(imageView);
        if ( contact.getDetails() != null && contact.getDetails().getLargeImageURL() !=null ) {
            String largeImageURL = contact.getDetails().getLargeImageURL();
            Picasso.with(this.getActivity()).load(largeImageURL).noFade().into(imageView);
        }

    }
}
