package com.boris.pokemontool;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.util.Log;
import android.widget.ImageButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import android.os.Handler;


public class MainActivity extends ActionBarActivity {

    private final static int INTERVAL = 1000 * 30 ; //2 minutes
    Handler dbCallHandler = new Handler();

    Runnable dbCallHandlerTask = new Runnable() {
        @Override
        public void run() {
            new UpdateDB().execute();
            dbCallHandler.postDelayed(dbCallHandlerTask, INTERVAL);
        }
    };

    void startRepeatingTask() {
        dbCallHandlerTask.run();
    }

    void stopRepeatingTask() {
        dbCallHandler.removeCallbacks(dbCallHandlerTask);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startRepeatingTask();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToPlayerNotes(View view) {
        Intent intent = new Intent(this, PlayerNotesDatabaseActivity.class);
        startActivity(intent);
    }


    public void goToRollDie(View view) {
        Intent intent = new Intent(this, RollDieActivity.class);
        startActivity(intent);
    }

    private final String URL_NEW_PREDICTION = "http://10.0.3.2:8080/scripts/update.php";
    private final String POISON = "POISON";
    private final String BURN = "BURN";
    private final String PARALYZE = "PARALYZE";
    private final String SLEEP = "SLEEP";
    private final String CONFUSE = "CONFUSE";


    public void addDB(View view) {
        new UpdateDB().execute();
    }

    private class UpdateDB extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(String... arg) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(POISON, "1"));
            params.add(new BasicNameValuePair(BURN, "2"));
            params.add(new BasicNameValuePair(SLEEP, "3"));
            params.add(new BasicNameValuePair(PARALYZE, "4"));
            params.add(new BasicNameValuePair(CONFUSE, "5"));

            ServiceHandler serviceClient = new ServiceHandler();

            String json = serviceClient.makeServiceCall(URL_NEW_PREDICTION,
                    ServiceHandler.POST, params);

            Log.d("DB", "Sent update request.");

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

    private Toggle Asleep = new Toggle(false);

    public void clickAsleep(View view) {
        //ImageButton button = (ImageButton)findViewById(R.id.buttonAsleep);
        ImageButton button = (ImageButton) view;
        //Drawable background = button.getBackground();
        //Drawable face = button.getDrawable();

        if (Asleep.toggle()) {
            button.setImageDrawable(getResources().getDrawable(R.drawable.asleep_on, getTheme()));
            //((Animatable) face).start();
        } else {
            button.setImageDrawable(getResources().getDrawable(R.drawable.asleep_off, getTheme()));
        }
    }

    private Toggle Poisoned = new Toggle(false);

    public void clickPoisoned(View view) {
        ImageButton button = (ImageButton) view;

        if (Poisoned.toggle()) {
            button.setImageDrawable(getResources().getDrawable(R.drawable.poisoned_on, getTheme()));
        } else {
            button.setImageDrawable(getResources().getDrawable(R.drawable.poisoned_off, getTheme()));
        }
    }

    private Toggle Paralyzed = new Toggle(false);

    public void clickParalyzed(View view) {
        ImageButton button = (ImageButton) view;
        LevelListDrawable pic = (LevelListDrawable) button.getDrawable();

        if (Paralyzed.toggle()) {
            pic.setLevel(1);
            //Asleep.off(); // this isn't going to be pretty without checkable buttons
        } else {
            pic.setLevel(0);
        }
    }

    private Toggle Burned = new Toggle(false);

    public void clickBurned(View view) {
        ImageButton button = (ImageButton) view;

        if (Burned.toggle()) {
            button.setImageDrawable(getResources().getDrawable(R.drawable.burned_on, getTheme()));
            //Asleep.off(); // this isn't going to be pretty without checkable buttons
        } else {
            button.setImageDrawable(getResources().getDrawable(R.drawable.burned_off, getTheme()));
        }
    }

    private Toggle Confused = new Toggle(false);

    public void clickConfused(View view) {
        ImageButton button = (ImageButton) view;

        if (Confused.toggle()) {
            button.setImageDrawable(getResources().getDrawable(R.drawable.confused_on, getTheme()));
            //Asleep.off(); // this isn't going to be pretty without checkable buttons
        } else {
            button.setImageDrawable(getResources().getDrawable(R.drawable.confused_off, getTheme()));
        }
    }

    public void clickCoin(View view){

        ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.flip);
        anim.setTarget(view);
        anim.setDuration(1000);
        anim.start();
    }
}
