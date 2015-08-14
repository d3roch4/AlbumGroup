package br.com.d3roch4.albumgroup.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import br.com.d3roch4.albumgroup.torrent.TorrentManager;

/**
 * Created by Andre on 13/08/2015.
 */
public class GalleryModel {

    private String userName;

    public GalleryModel(String userName){
        this.userName = userName;
    }

    public boolean create(String dirGalleryFull) throws IOException, InterruptedException, URISyntaxException {
        File dir = new File(dirGalleryFull);
        if (!dir.exists()) {
            if (!dir.mkdirs()){
                return false;
            }
        }

        File[] listFiles = dir.listFiles();
        if(listFiles.length<1) {
            PrintWriter fileInit = new PrintWriter(dir.getAbsolutePath()+"/init.db");
            fileInit.write("testando um doi tres\n");
            fileInit.close();
        }
        TorrentManager.create(dir, listFiles, userName);

        return true;
    }
}
