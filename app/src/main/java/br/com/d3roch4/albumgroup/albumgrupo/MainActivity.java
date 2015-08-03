package br.com.d3roch4.albumgroup.albumgrupo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;

import br.com.d3roch4.albumgroup.R;
import br.com.d3roch4.albumgroup.albumgrupo.torrent.TorrentManager;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            TorrentManager torrent = new TorrentManager(this.getApplicationContext().getFilesDir().getPath());
        } catch (IOException e) {
            Log.e("new TorrentManager :: ", "Problem para iniciar o gerenciamento do torrent: " + e.getMessage());
            Toast.makeText(getApplicationContext(), R.string.not_possible_init_torrent_manager, Toast.LENGTH_LONG).show();
        }

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

        switch (id)
        {
            case R.id.action_settings:
                return true;
            case R.id.action_new_gallery:
                startActivity(new Intent(this, NewGallery.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
