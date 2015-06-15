package com.boris.pokemontool;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RollDieActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_die);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_roll_die, menu);
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

    public void clickRollDie(View view){
        int diceRollResult;
        int diceSideCount;

        EditText diceSides = (EditText) findViewById(R.id.editDiceSides);
        diceSideCount=Integer.parseInt(diceSides.getText().toString());
        TextView result = (TextView) findViewById(R.id.textDiceRollResult);

        Die Die = new Die(diceSideCount);
        diceRollResult = Die.roll();

        result.setText(Integer.toString(diceRollResult));

    }


}
