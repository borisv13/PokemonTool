package com.boris.pokemontool;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.util.Log;
import android.view.Window;
import android.widget.ImageButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OpponentFragment.OnFragmentInteractionListener{

    private final static int INTERVAL = 1000 * 30 ; //2 minutes
    Handler dbCallHandler = new Handler();

    private int poisonCount = 0;
    private int burnCount = 0;
    private int sleepCount = 0;
    private int paralyzeCount = 0;
    private int confuseCount = 0;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

/*
    private enum CONDITION {ASLEEP, CONFUSED, PARALYZED, BURNED, POISONED}


    private EnumMap<CONDITION, ToggleButton> opponent1Conditions = new EnumMap<CONDITION, ToggleButton>(CONDITION.class);
    private EnumMap<CONDITION, ToggleButton> opponent2Conditions = new EnumMap<CONDITION, ToggleButton>(CONDITION.class);
*/



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

        if (savedInstanceState == null) {
            OpponentFragment opponent1 = OpponentFragment.newInstance(false, 30, false, false, false, false, false, 0, 0, 0, 0, 0, false, false, false, false, false, false);
            OpponentFragment opponent2 = OpponentFragment.newInstance(true, 30, false, false, false, false, false, 0, 0, 0, 0, 0, false, false, false, false, false, false);
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fieldLayout, opponent2)
                    .add(R.id.fieldLayout, opponent1)
                    .commit();
        }
/*
        LinearLayout fieldView = (LinearLayout) findViewById(R.id.fieldLayout);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View opponentView = inflater.inflate(R.layout.opponent_layout, fieldView, false);
        opponentView.setId(R.id.opponent2);
        opponent2Conditions = setOpponentConditions(opponentView);

        //customize top opponent's fieldView appearance == dark condition buttons, white HP text
        opponentView.setBackgroundColor(Color.parseColor("#303030"));
        opponentView.setRotation(180);

        for(EnumMap.Entry<CONDITION, ToggleButton> condition : opponent2Conditions.entrySet())
            condition.getValue().setBackground(getDrawable(R.drawable.disk_dark));

        ((TextView) opponentView.findViewById(R.id.textHitPoints)).setTextColor(Color.WHITE);
        ((TextView) opponentView.findViewById(R.id.buttonMinus)).setTextColor(Color.WHITE);
        ((TextView) opponentView.findViewById(R.id.buttonPlus)).setTextColor(Color.WHITE);
        //appearance customized
        fieldView.addView(opponentView);

        opponentView = inflater.inflate(R.layout.opponent_layout, fieldView, false);
        opponentView.setId(R.id.opponent1);
        opponent1Conditions = setOpponentConditions(opponentView);
        fieldView.addView(opponentView);
*/
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
        switch(id) {
            case R.id.action_settings:
                return true;
            case R.id.action_player_notes:
                goToPlayerNotes();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToPlayerNotes() {
        Intent intent = new Intent(this, PlayerNotesDatabaseActivity.class);
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

/*
    public EnumMap<CONDITION, ToggleButton> setOpponentConditions(View view){

        EnumMap<CONDITION, ToggleButton> conditions = new EnumMap<CONDITION, ToggleButton>(CONDITION.class);

        conditions.put(CONDITION.ASLEEP, (ToggleButton) view.findViewById(R.id.buttonAsleep));
        conditions.put(CONDITION.CONFUSED, (ToggleButton)view.findViewById(R.id.buttonConfused));
        conditions.put(CONDITION.PARALYZED, (ToggleButton)view.findViewById(R.id.buttonParalyzed));
        conditions.put(CONDITION.BURNED, (ToggleButton)view.findViewById(R.id.buttonBurned));
        conditions.put(CONDITION.POISONED, (ToggleButton)view.findViewById(R.id.buttonPoisoned));
        return conditions;
    }
*/

    public void clickAsleep(View view) {
        View grandpa = (View) view.getParent().getParent();
        ToggleButton button = (ToggleButton) view;
        if (button.isChecked()){
            sleepCount++;
/*
            if(grandpa.getId() == R.id.opponent1){
                opponent1Conditions.get(CONDITION.CONFUSED).setChecked(false);
                opponent1Conditions.get(CONDITION.PARALYZED).setChecked(false);
            } else {
                opponent2Conditions.get(CONDITION.CONFUSED).setChecked(false);
                opponent2Conditions.get(CONDITION.PARALYZED).setChecked(false);
            }
*/
        }
    }

    public void clickConfused(View view) {
        View grandpa = (View) view.getParent().getParent();
        ToggleButton button = (ToggleButton) view;
        if (button.isChecked()){
            confuseCount++;
/*
            if(grandpa.getId() == R.id.opponent1){
                opponent1Conditions.get(CONDITION.ASLEEP).setChecked(false);
                opponent1Conditions.get(CONDITION.PARALYZED).setChecked(false);
            } else {
                opponent2Conditions.get(CONDITION.ASLEEP).setChecked(false);
                opponent2Conditions.get(CONDITION.PARALYZED).setChecked(false);
            }
*/
        }
    }

    public void clickParalyzed(View view) {
        View grandpa = (View) view.getParent().getParent();
        ToggleButton button = (ToggleButton) view;
        if (button.isChecked()){
            paralyzeCount++;
/*
            if(grandpa.getId() == R.id.opponent1){
                opponent1Conditions.get(CONDITION.ASLEEP).setChecked(false);
                opponent1Conditions.get(CONDITION.CONFUSED).setChecked(false);
            } else {
                opponent2Conditions.get(CONDITION.ASLEEP).setChecked(false);
                opponent2Conditions.get(CONDITION.CONFUSED).setChecked(false);
            }
*/
        }
    }

    public void clickBurned(View view) {
        ToggleButton button = (ToggleButton) view;
        if (button.isChecked()){
            burnCount++;
        }
    }

    public void clickPoisoned(View view) {
        ToggleButton button = (ToggleButton) view;
        if (button.isChecked()){
            poisonCount++;
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

    public void clickPlus(View view){
        int number;
        int color;
        View parent = (View) view.getParent();
        TextView playerHP = (TextView) parent.findViewById(R.id.textHitPoints);
        number = Integer.parseInt(playerHP.getText().toString());
        number += 10;
        if (number > 990) {
            number = 990;
            color = Color.parseColor("#0c9f50");
        } else {
            color = ((TextView) view).getCurrentTextColor(); // +/- will have the same text color as the HP counter
        }

        playerHP.setTextColor(color);
        playerHP.setText(String.valueOf(number));
    }

    public void clickMinus(View view){
        int number;
        int color;

        View parent = (View) view.getParent();
        TextView playerHP = (TextView) parent.findViewById(R.id.textHitPoints);
        number = Integer.parseInt(playerHP.getText().toString());
        number -= 10;
        if (number <= 0) {
            number = 0;
            color = Color.parseColor("#ff0000");

            //Reset ?
        } else {
            color = ((TextView) view).getCurrentTextColor(); // +/- will have the same text color as the HP counter
        }

        playerHP.setTextColor(color);
        playerHP.setText(String.valueOf(number));
    }

    public void clickReset(View view){
        int defaultHP = 30;
        int color;

        View parent =  (View)view.getParent();
        TextView playerHP = (TextView)parent.findViewById(R.id.textHitPoints);
        color = ((TextView)parent.findViewById(R.id.buttonMinus)).getCurrentTextColor(); // +/- will have the same text color as the HP counter

        playerHP.setTextColor(color);
        playerHP.setText(String.valueOf(defaultHP));

/*
        if (parent.getId()==R.id.opponent1) {
            for(EnumMap.Entry<CONDITION, ToggleButton> condition : opponent1Conditions.entrySet())
                condition.getValue().setChecked(false);
        } else {
            for(EnumMap.Entry<CONDITION, ToggleButton> condition : opponent2Conditions.entrySet())
                condition.getValue().setChecked(false);
        }
*/

    }


    public void clickOptions(View view){
        openOptionsMenu();
    }

    public void clickBench(View view){
        View parent = (View) view.getParent();

        View benchSlider = parent.findViewById(R.id.benchLayout);
        View conditionsButtons = parent.findViewById(R.id.conditionButtonsLayout);
        View mainMinusButton = parent.findViewById(R.id.buttonMinus);
        View mainPlusButton = parent.findViewById(R.id.buttonPlus);
        View resetButton = parent.findViewById(R.id.buttonReset);

        if (benchSlider.getVisibility() == View.VISIBLE){
            benchSlider.setVisibility(View.GONE);
            conditionsButtons.setVisibility(View.VISIBLE);
            mainMinusButton.setEnabled(true);
            mainPlusButton.setEnabled(true);
            resetButton.setEnabled(true);
        }else {
            benchSlider.setVisibility(View.VISIBLE);
            conditionsButtons.setVisibility(View.INVISIBLE); //disable clicks on out-of-focus sp. conditions buttons
            mainMinusButton.setEnabled(false);
            mainPlusButton.setEnabled(false);
            resetButton.setEnabled(false);

        }
    }


}
