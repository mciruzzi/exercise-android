package solstice.exercise.solsticeexercise;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.HttpException;

import java.util.List;

import solstice.exercise.solsticeexercise.background.GetContactsTask;
import solstice.exercise.solsticeexercise.model.Contact;

public class ContactsActivity extends ActionBarActivity implements ILoadable {

    private ProgressBar progressBar;
    private TextView getContactsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        progressBar = (ProgressBar) findViewById(R.id.login_progess_bar);
        getContactsText = (TextView) findViewById(R.id.login_status_message);

        this.showProgress(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /*
    private void loadContacts() {
        showProgress(true);
        GetContactsTask backgroundTask = new GetContactsTask(this);
        backgroundTask.execute();
    }
    */

    @Override
    public void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        progressBar.setVisibility(View.VISIBLE);
        getContactsText.setVisibility(View.VISIBLE);
        progressBar.animate().setDuration(shortAnimTime)
                .alpha(show ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                        getContactsText.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                });
    }

    /*
    public void onError(Exception error) {

    }

    public void updateConctacts(List<Contact> contacts) {

    }
    */
}
