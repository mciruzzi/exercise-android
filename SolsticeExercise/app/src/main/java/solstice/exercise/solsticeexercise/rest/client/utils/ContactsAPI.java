package solstice.exercise.solsticeexercise.rest.client.utils;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import solstice.exercise.solsticeexercise.model.Contact;

/**
 * Created by romac-ubuntu on 28/10/15.
 */
public interface ContactsAPI {

    @GET("/external/contacts.json")
    public Call<List<Contact>> getContacts();


}