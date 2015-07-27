package com.boris.pokemontool;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.util.Log;
import android.view.Window;
import android.widget.ImageButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity
        implements OpponentFragment.OnFragmentInteractionListener{

    private final static int INTERVAL = 1000 * 30 ; //2 minutes
    Handler dbCallHandler = new Handler();

    private int poisonCount = 0;
    private int burnCount = 0;
    private int sleepCount = 0;
    private int paralyzeCount = 0;
    private int confuseCount = 0;

/*

    @Override
    public void onFragmentInteraction(Uri uri) {
        // default empty placeholder to communicate with OpponentFragment
    }

*/

    @Override
    public void onSpecialConditionRaised(CONDITION condition){
        // pass data from OpponentFragment
        switch (condition){
            case ASLEEP:
                sleepCount++;
                break;
            case CONFUSED:
                confuseCount++;
                break;
            case PARALYZED:
                paralyzeCount++;
                break;
            case BURNED:
                burnCount++;
                break;
            case POISONED:
                poisonCount++;
                break;
            default:
                break;
        }
    }


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

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (preferences.getBoolean("pref_screenRotation",false))
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        if (savedInstanceState == null) {
            int[] intArray1 = new int[] {0, 0, 0, 0, 0};
            int[] intArray2 = new int[] {0, 0, 0, 0, 0};
            boolean[] boolArray1 = new boolean[] {false, false, false, false, false};
            boolean[] boolArray2 = new boolean[] {false, false, false, false, false};
            OpponentFragment opponent1 = OpponentFragment.newInstance(false, 0, false, false, false, false, false, intArray1, false, boolArray1);
            OpponentFragment opponent2 = OpponentFragment.newInstance(true, 0, false, false, false, false, false, intArray2, false, boolArray2);
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fieldLayout, opponent2)
                    .add(R.id.fieldLayout, opponent1)
                    .commit();
        } else {
            ((ImageButton) findViewById(R.id.buttonCoin)).getDrawable().setLevel(savedInstanceState.getInt("COIN_STATE"));
        }

        startRepeatingTask();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("COIN_STATE", ((ImageButton) findViewById(R.id.buttonCoin)).getDrawable().getLevel());
    }

    @Override
    protected void onPause() {
        super.onPause();
        // persist instance here (Save Game)
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // stuff to be done before exit from the app
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
        switch(id) {
            case R.id.action_settings:
                getFragmentManager()
                        .beginTransaction()
                        .replace(android.R.id.content, new SettingsFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
        }

        return super.onOptionsItemSelected(item);
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


    private Coin coin = new Coin();

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
                pic.setLevel(coin.toss());
            }
        }, dropDelay);

        anim.start();
        //pic.setLevel(Coin.roll());

    }

    public void clickOptions(View view){
        openOptionsMenu();
    }

}
