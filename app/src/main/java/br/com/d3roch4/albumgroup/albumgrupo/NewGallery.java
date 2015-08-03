package br.com.d3roch4.albumgroup.albumgrupo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import br.com.d3roch4.albumgroup.R;


public class NewGallery extends Activity {

    private EditText dirAlbum;
    private EditText nameAlbum;
    private String dirAlbumStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_gallery);

        dirAlbum = (EditText) findViewById(R.id.dirAlbum);
        dirAlbumStr = Environment.getExternalStorageDirectory()+"/Pictures/";

        nameAlbum = (EditText)findViewById(R.id.nameAlbum);
        nameAlbum.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                dirAlbum.setText(dirAlbumStr + nameAlbum.getText());
                return false;
            }
        });

        ((Button) findViewById(R.id.selectDir)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                        startActivityForResult(intent, 1);
                    }
        });

        ((Button)findViewById(R.id.create)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewGallery();
            }
        });

    }

    public void createNewGallery(){
        String dirGalleryFull = dirAlbumStr+nameAlbum.getText();
        File dir = new File(dirGalleryFull);
        if (!dir.exists()) {
            if (!dir.mkdirs()){
                Log.e("createNewGallery :: ", "Problem creating Gallery folder: " + dirGalleryFull);
                Toast.makeText(getApplicationContext(), R.string.not_possible_create_gallery, Toast.LENGTH_LONG).show();
                return;
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Log.d("NewGallery", String.format("Open Directory result Uri : %s", data.getData()));
            dirAlbumStr = data.getDataString()+'/';
            dirAlbum.setText(dirAlbumStr+nameAlbum.getText());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_album, menu);
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
}
