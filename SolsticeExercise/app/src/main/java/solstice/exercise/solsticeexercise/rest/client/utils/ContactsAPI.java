package solstice.exercise.solsticeexercise.rest.client.utils;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import solstice.exercise.solsticeexercise.model.Contact;
import solstice.exercise.solsticeexercise.model.ContactDetails;

/**
 * Created by romac-ubuntu on 28/10/15.
 */
public interface ContactsAPI {

    @GET("/external/contacts.json")
    public Call<List<Contact>> getContacts();

    @GET("/external/Contacts/id/{contactId}.json")
    public Call<ContactDetails> getContactInfo(@Path("contactId") String contactId);

}