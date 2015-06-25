package com.boris.pokemontool;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.util.Log;
import android.view.Window;
import android.widget.ImageButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import android.os.Handler;

public class MainActivity extends Activity {

    private final static int INTERVAL = 1000 * 30 ; //2 minutes
    Handler dbCallHandler = new Handler();

    private int poisonCount = 0;
    private int burnCount = 0;
    private int sleepCount = 0;
    private int paralyzeCount = 0;
    private int confuseCount = 0;

    Runnable dbCallHandlerTask = new Runnable() {
        @Override
        public void run() {
            if(determineIfDBCallNeeded()) {
                new UpdateDB().execute();
            }
            dbCallHandler.postDelayed(dbCallHandlerTask, INTERVAL);
        }
    };

    void startRepeatingTask() {
        dbCallHandlerTask.run();
    }

    void stopRepeatingTask() {
        dbCallHandler.removeCallbacks(dbCallHandlerTask);
    }


    private boolean determineIfDBCallNeeded() {
        if(poisonCount > 0)
            return true;
        else if(burnCount > 0)
            return true;
        else if(poisonCount > 0)
            return true;
        else if(paralyzeCount > 0)
            return true;
        else if(confuseCount > 0)
            return true;
        else
            return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
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

    private void resetCounters() {
        poisonCount = 0;
        burnCount = 0;
        sleepCount = 0;
        paralyzeCount = 0;
        confuseCount = 0;
    }

    private class UpdateDB extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(String... arg) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(POISON, Integer.toString(poisonCount)));
            params.add(new BasicNameValuePair(BURN, Integer.toString(burnCount)));
            params.add(new BasicNameValuePair(SLEEP, Integer.toString(sleepCount)));
            params.add(new BasicNameValuePair(PARALYZE, Integer.toString(paralyzeCount)));
            params.add(new BasicNameValuePair(CONFUSE, Integer.toString(confuseCount)));

            ServiceHandler serviceClient = new ServiceHandler();

            String json = serviceClient.makeServiceCall(URL_NEW_PREDICTION,
                    ServiceHandler.POST, params);

            resetCounters();

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
        ImageButton button = (ImageButton) view;
        LevelListDrawable pic = (LevelListDrawable) button.getDrawable();

        if (Asleep.toggle()) {
            pic.setLevel(1);
            sleepCount++;
        } else {
            pic.setLevel(0);
            //button.setImageDrawable(getResources().getDrawable(R.drawable.asleep_off, getTheme()));
        }
    }

    private Toggle Confused = new Toggle(false);
    public void clickConfused(View view) {
        ImageButton button = (ImageButton) view;
        LevelListDrawable pic = (LevelListDrawable) button.getDrawable();

        if (Confused.toggle()) {
            pic.setLevel(1);
            confuseCount++;
        } else {
            pic.setLevel(0);
        }
    }

    private Toggle Paralyzed = new Toggle(false);
    public void clickParalyzed(View view) {
        ImageButton button = (ImageButton) view;
        LevelListDrawable pic = (LevelListDrawable) button.getDrawable();

        if (Paralyzed.toggle()) {
            pic.setLevel(1);
            paralyzeCount++;
        } else {
            pic.setLevel(0);
        }
    }

    private Toggle Burned = new Toggle(false);
    public void clickBurned(View view) {
        ImageButton button = (ImageButton) view;
        LevelListDrawable pic = (LevelListDrawable) button.getDrawable();

        if (Burned.toggle()) {
            pic.setLevel(1);
            burnCount++;
        } else {
            pic.setLevel(0);
        }
    }

    private Toggle Poisoned = new Toggle(false);
    public void clickPoisoned(View view) {
        ImageButton button = (ImageButton) view;
        LevelListDrawable pic = (LevelListDrawable) button.getDrawable();

        if (Poisoned.toggle()) {
            pic.setLevel(1);
            poisonCount++;
        } else {
            pic.setLevel(0);
        }
    }

    private Die Coin = new Die(2);

    public void clickCoin(View view){
        long flipDuration;
        long dropDelay;
        flipDuration = 1000;
        dropDelay = flipDuration - (long)((float)flipDuration * 0.12);
        ImageButton button = (ImageButton) view;
        final LevelListDrawable pic = (LevelListDrawable) button.getDrawable();

        ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.toss);
        anim.setTarget(view);
        anim.setDuration(flipDuration);

        // delay image change until the animation is (almost) complete
        // otherwise it looks as if the coin drops before it flips

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pic.setLevel(Coin.roll());
            }
        }, dropDelay);

        anim.start();
        //pic.setLevel(Coin.roll());

    }
}
