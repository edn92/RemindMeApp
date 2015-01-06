package com.example.remindme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_new:
                Intent i = new Intent(this, NewReminder.class);
                startActivity(i);
                return true;
            case R.id.action_delete:
                showToast("Deleting all marked memos.");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
