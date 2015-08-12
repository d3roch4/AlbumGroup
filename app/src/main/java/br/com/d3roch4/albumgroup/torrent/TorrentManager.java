package br.com.d3roch4.albumgroup.torrent;

import com.turn.ttorrent.common.Torrent;
import com.turn.ttorrent.tracker.TrackedTorrent;
import com.turn.ttorrent.tracker.Tracker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * Created by Andre on 31/07/2015.
 */
public class TorrentManager {
    private static Tracker tracker;
    private static String dirFilesTorrent;

    public TorrentManager(String dirFilesTorrent) throws IOException {
        this.dirFilesTorrent = dirFilesTorrent;
        tracker = new Tracker(new InetSocketAddress(6969));
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".torrent");
            }
        };

        for (File f : new File(dirFilesTorrent).listFiles(filter)) {
            getTracker().announce(TrackedTorrent.load(f));
        }
        getTracker().start();
    }


    public static void create(File dir, File[] files, String createBy) throws URISyntaxException, IOException, InterruptedException {
        Torrent torrent = Torrent.create(dir, Arrays.asList(files), getTracker().getAnnounceUrl().toURI(), createBy);
        FileOutputStream fos = new FileOutputStream(dirFilesTorrent+'/'+dir.getName()+".torrent");
        torrent.save(fos);
        TrackedTorrent trackedTorrent = new TrackedTorrent(torrent);
        getTracker().announce(trackedTorrent);
    }

    public static Tracker getTracker() {
        return tracker;
    }

    public void stop(){
        getTracker().stop();
    }
}
