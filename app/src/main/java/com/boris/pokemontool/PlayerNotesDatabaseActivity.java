package com.boris.pokemontool;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.boris.pokemontool.R;

import java.util.List;

public class PlayerNotesDatabaseActivity extends ActionBarActivity {

    public final static String PLAYER_ID_MESSAGE = "com.pokemonutil.pokemontool.PLAYERID";
    public final static String PLAYER_NAME_MESSAGE = "com.pokemonutil.pokemontool.PLAYERNAME";
    public final static String PLAYER_NOTES_MESSAGE = "com.pokemonutil.pokemontool.PLAYERNOTES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_notes_database);
        init();
    }

    public void saveMessage(View view) {
        PlayerNotesDBHandler dbHandler = new PlayerNotesDBHandler(this, null, null, 1);

        EditText editText = (EditText) findViewById(R.id.edit_message);
        String name = editText.getText().toString();

        Player player = new Player(name);

        dbHandler.addPlayer(player);

        init();
    }

    public void deletePlayer(Player player) {
        PlayerNotesDBHandler dbHandler = new PlayerNotesDBHandler(this, null, null, 1);
        dbHandler.deletePlayer(player);

        init();
    }

    public void init() {


        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        try {
            stk.removeAllViews();
        } catch (NullPointerException e) {}

        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText(" Sl.No ");
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Name ");
        tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);
        stk.addView(tbrow0);

        PlayerNotesDBHandler dbHandler = new PlayerNotesDBHandler(this, null, null, 1);
        List<Player> players = dbHandler.getPlayers();

        for (int i = 0; i < players.size(); i++) {
            final Player currentPlayer = players.get(i);
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText("" + currentPlayer.getId());
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText(currentPlayer.getName());
            t2v.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    playerNotesActivity(currentPlayer);
                    //deletePlayer(currentPlayer);
                }
            });
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            stk.addView(tbrow);
        }
    }

    public void playerNotesActivity(Player player) {
        Intent intent = new Intent(this, PlayerNotesActivity.class);
        intent.putExtra(PLAYER_ID_MESSAGE, player.getId());
        intent.putExtra(PLAYER_NAME_MESSAGE, player.getName());
        startActivity(intent);
    }
}
