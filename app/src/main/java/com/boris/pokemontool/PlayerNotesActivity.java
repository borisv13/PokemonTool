package com.boris.pokemontool;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class PlayerNotesActivity extends ActionBarActivity {

    Player player = new Player();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_notes);
        Intent intent = getIntent();
        int id = intent.getIntExtra(PlayerNotesDatabaseActivity.PLAYER_ID_MESSAGE, -1);
        player.setId(id);
        String name = intent.getStringExtra(PlayerNotesDatabaseActivity.PLAYER_NAME_MESSAGE);
        player.setName(name);
        TextView nameTextView = (TextView) findViewById(R.id.player_name);
        nameTextView.setText(name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player_notes, menu);
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

    public void deletePlayer(View view) {
        PlayerNotesDBHandler dbHandler = new PlayerNotesDBHandler(this, null, null, 1);
        dbHandler.deletePlayer(player);

        Intent intent = new Intent(this, PlayerNotesDatabaseActivity.class);
        startActivity(intent);
    }
}
