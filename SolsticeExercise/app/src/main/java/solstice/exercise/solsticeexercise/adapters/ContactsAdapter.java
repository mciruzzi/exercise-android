package solstice.exercise.solsticeexercise.adapters;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import solstice.exercise.solsticeexercise.ContactsActivity;
import solstice.exercise.solsticeexercise.ILoadable;
import solstice.exercise.solsticeexercise.R;
import solstice.exercise.solsticeexercise.model.Contact;
import solstice.exercise.solsticeexercise.rest.client.utils.CachedImageLoader;
import solstice.exercise.solsticeexercise.rest.client.utils.Routes;

public class ContactsAdapter extends ArrayAdapter {

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private static final String TAG = "ContactsAdapter";

    List<Contact> contacts = new ArrayList<Contact>();
    private final ILoadable showProgress;

    public ContactsAdapter(ContactsActivity activity) {
        super(activity, 0);
        showProgress = activity;
        requestQueue = Volley.newRequestQueue(this.getContext());

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                showProgress.showProgress(false);
                contacts = parseJson(response);
                notifyDataSetChanged();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress.showProgress(false);
                Log.d(TAG, "Error Respuesta en JSON: " + error.getMessage());

            }
        };

        JsonArrayRequest jsArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                Routes.BASE_URL + "external/contacts.json",
                null,
                listener,
                errorListener );

        requestQueue.add(jsArrayRequest);

        imageLoader = new CachedImageLoader( requestQueue);
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

        // TODO Not using ViewHolder Pattern since there's bug setting images view to previous requested image download
        View row = null;
        //if (convertView == null) {
            row = layoutInflater.inflate(R.layout.contact_in_list, parent, false);
        //} else {
        //    row = convertView;
        //}

        final Contact contact = (Contact) this.getItem(position);

        final ImageView image = (ImageView) row.findViewById(R.id.contactImage);
        final TextView name = (TextView) row.findViewById(R.id.contactName);
        final TextView phone = (TextView) row.findViewById(R.id.contactPhone);
        image.setTag(position);

        name.setText(contact.getName());
        phone.setText(contact.getPhone().getHome());
        image.setImageResource(R.mipmap.default_profile);


        imageLoader.get(contact.getSmallImageURL(), ImageLoader.getImageListener(image,
                R.mipmap.default_profile, R.mipmap.default_profile));
        return row;
    }

    public List<Contact> parseJson(JSONArray jsonArray){
        ObjectMapper mapper = new ObjectMapper();
        try {
            Contact[] contacts = mapper.readValue( jsonArray.toString(), Contact[].class);
            List<Contact> contactList;
            contactList =  Arrays.asList(contacts);

            return contactList;

        } catch (JsonMappingException e) {
            Log.e(TAG, "Error de Mapping: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "Error de Mapping: " + e.getMessage());
        }

        return new ArrayList<>();
    }

}
