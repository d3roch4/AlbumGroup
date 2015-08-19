package br.com.d3roch4.albumgroup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.turn.ttorrent.tracker.TrackedTorrent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.d3roch4.albumgroup.torrent.TorrentManager;
import br.com.d3roch4.albumgroup.view.GridViewAdapter;
import br.com.d3roch4.albumgroup.view.ImageItem;


public class MainActivity extends ActionBarActivity {

    private TorrentManager torrent;
    private Collection<TrackedTorrent> trackedTorrentCollection;
    private GridView gridView;
    private GridViewAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            torrent = new TorrentManager(this.getApplicationContext().getFilesDir().getPath());
        } catch (IOException e) {
            Log.e("new TorrentManager :: ", "Problem para iniciar o gerenciamento do torrent: " + e.getMessage());
            Toast.makeText(getApplicationContext(), R.string.not_possible_init_torrent_manager, Toast.LENGTH_LONG).show();
        }
    }

    // Prepare some dummy data for gridview
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        for(TrackedTorrent trackedTorrent: trackedTorrentCollection){
            List<String> filesName = trackedTorrent.getFilenames();
            Bitmap bitmap = BitmapFactory.decodeFile(filesName.get(filesName.size()));
            imageItems.add(new ImageItem(bitmap, trackedTorrent.getName()));
        }
        return imageItems;
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
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.action_new_gallery:
                startActivity(new Intent(this, NewGalleryActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        torrent.stop();
    }
}
