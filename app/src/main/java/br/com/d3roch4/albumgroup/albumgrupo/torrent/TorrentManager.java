package br.com.d3roch4.albumgroup.albumgrupo.torrent;

import com.turn.ttorrent.common.Torrent;
import com.turn.ttorrent.tracker.TrackedTorrent;
import com.turn.ttorrent.tracker.Tracker;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by Andre on 31/07/2015.
 */
public class TorrentManager {
    private static Tracker tracker;
    private String dirFilesTorrent;
    public TorrentManager(String dirFilesTorrent) throws IOException {
        this.dirFilesTorrent = dirFilesTorrent;
        tracker = new Tracker(new InetSocketAddress(6969));
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".torrent");
            }
        };

        for (File f : new File(dirFilesTorrent).listFiles(filter)) {
            tracker.announce(TrackedTorrent.load(f));
        }
        tracker.start();
    }
    public static void create(File dir, List<File> files, String createBy) throws URISyntaxException, IOException, InterruptedException {
        Torrent.create(dir, files, tracker.getAnnounceUrl().toURI(), createBy);
    }
}
