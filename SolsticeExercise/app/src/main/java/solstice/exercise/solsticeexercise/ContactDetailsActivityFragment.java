package solstice.exercise.solsticeexercise;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import solstice.exercise.solsticeexercise.model.Contact;
import solstice.exercise.solsticeexercise.model.ContactDetails;
import solstice.exercise.solsticeexercise.rest.client.utils.CachedImageLoader;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContactDetailsActivityFragment extends Fragment {

    private final String TAG = "ContactDetailsFragment";
    private Contact contact;
    private RequestQueue requestQueue; // TODO Should make singleton pattern to have just 1 requestQueue
    private View contactDetailsView;

    public ContactDetailsActivityFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contact = (Contact) this.getActivity().getIntent().getSerializableExtra( ContactDetailsActivity.CONTACT_ID_KEY);
        requestQueue = Volley.newRequestQueue(this.getActivity());
        queryContactDetails();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_contact_details, container, false);
    }

    private void queryContactDetails() {

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                contact.setDetails(parseJson(response));
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        populateContactInfo(contactDetailsView);
                    }
                });
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error JSON response: " + error.getMessage());
            }
        };

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                contact.getDetailsURL(),
                null,
                listener,
                errorListener );

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.contactDetailsView = view;
        populateContactInfo(view);
    }

    private void populateContactInfo(View view) {
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
        birthday.setText(new SimpleDateFormat("yyyy-MM-dd").format(birthDate));

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
        String imageURL = contact.getDetails() != null ? contact.getDetails().getLargeImageURL() : contact.getSmallImageURL();
        CachedImageLoader cachedImageLoader = new CachedImageLoader(requestQueue);

        cachedImageLoader.get(imageURL, ImageLoader.getImageListener(imageView,
                R.mipmap.default_profile, R.mipmap.default_profile));

    }

    public ContactDetails parseJson(JSONObject jsonObject){
        ObjectMapper mapper = new ObjectMapper();
        try {
            ContactDetails contactDetails = mapper.readValue( jsonObject.toString(), ContactDetails.class);
            return contactDetails;

        } catch (JsonMappingException e) {
            Log.e(TAG, "Mapping Error in ContactDetails: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "Mapping Error in ContactDetails: " + e.getMessage());
        }

        return null;
    }
}
