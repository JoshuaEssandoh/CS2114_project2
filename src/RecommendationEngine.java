import java.util.Random;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

// -------------------------------------------------------------------------
/**
 * Write a one-sentence summary of your class here.
 * Follow it with additional details about its purpose, what abstraction
 * it represents, and how to use it.
 * 
 * @author galve
 * @version Oct 12, 2026
 */
public class RecommendationEngine {
    private Playlist playlist1;
    private Random random;
    private static final String THRILLER_URL =
        "https://en.wikipedia.org/wiki/Thriller_(album)";
    private static final int TIMEOUT_MILLISECONDS = 10000;

    // ----------------------------------------------------------
    /**
     * Create a new RecommendationEngine object.
     * 
     * @param playlist1
     */
    public RecommendationEngine(Playlist playlist1) {
        this(playlist1, new Random());
    }


    // ----------------------------------------------------------
    /**
     * Create a new RecommendationEngine object.
     * 
     * @param playlist1
     * @param random
     */
    public RecommendationEngine(Playlist playlist1, Random random) {
        requirePlaylist(playlist1);
        if (random == null) {
            throw new IllegalArgumentException(
                "Random generator cannot be null");
        }
        this.playlist1 = playlist1;
        this.random = random;
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * 
     * @param playlist
     * @param genre
     * @return
     */
    public LinkedChain<Song> compileGenrePlaylist(
        Playlist playlist,
        String genre) {
        requirePlaylist(playlist);
        if (genre == null || genre.trim().isEmpty()) {
            throw new IllegalArgumentException("Genre cannot be empty");
        }
        String chosenGenre = genre.trim();
        LinkedChain<Song> source = playlist.getSongs();
        Song[] songs = source.toArray(new Song[source.size()]);
        LinkedChain<Song> matches = new LinkedChain<Song>();

        for (int i = songs.length - 1; i >= 0; i--) {
            if (songs[i].getGenre().trim().equalsIgnoreCase(chosenGenre)) {
                matches.add(songs[i]);
            }
        }
        return matches;
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * 
     * @param genre
     * @param count
     * @return
     */
    public LinkedChain<Song> recommendSongs(String genre, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count is negative.");
        }

        LinkedChain<Song> matches = compileGenrePlaylist(playlist1, genre);
        if (count > matches.size()) {
            throw new IllegalArgumentException("Invalid Input");
        }
        Song[] options = matches.toArray(new Song[matches.size()]);

        for (int i = 0; i < count; i++) {
            int rndIndex = i + random.nextInt(options.length - i);
            Song temporary = options[i];
            options[i] = options[rndIndex];
            options[rndIndex] = temporary;
        }

        LinkedChain<Song> recommendations = new LinkedChain<Song>();
        for (int i = count - 1; i >= 0; i--) {
            recommendations.add(options[i]);
        }

        return recommendations;
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * 
     * @param target
     * @param genre
     * @param count
     * @return
     */
    public LinkedChain<Song> addRecommendations(
        Playlist target,
        String genre,
        int count) {
        requirePlaylist(target);
        LinkedChain<Song> recommendations = recommendSongs(genre, count);
        Song[] selected = recommendations.toArray(new Song[recommendations
            .size()]);
        for (Song song : selected) {
            target.addSong(song);
        }

        return recommendations;
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * 
     * @return
     * @throws IOException
     */
    public int loadSongsFromWebsite() throws IOException {
        Document page = Jsoup.connect(THRILLER_URL).userAgent(
            "SoundStack/1.0 (CS2114 educational album reader)").timeout(
                TIMEOUT_MILLISECONDS).get();

        // Remove footnote numbers before reading song titles.
        page.select("sup.reference").remove();
        LinkedChain<Song> loadedSongs = new LinkedChain<Song>();

        for (Element row : page.select("table.tracklist tr")) {
            // Ignore table headings and total-length rows.
            Element number = row.selectFirst("th[scope=row]");
            if (number == null || row.hasClass("tracklist-total-length")) {
                continue;
            }

            Element titleCell = row.selectFirst("td");
            if (titleCell == null) {
                throw new IOException("A track is missing its title");
            }

            String title = titleCell.text().trim();
            // Keep the quoted title, excluding notes such as (with Guest).
            if (title.startsWith("\"")) {
                int closingQuote = title.indexOf('"', 1);
                if (closingQuote > 0) {
                    title = title.substring(1, closingQuote).trim();
                }
            }
            try {
                Song song = new Song(title, "Michael Jackson", "Synth-pop");
                loadedSongs.addToEnd(song);
            }
            catch (IllegalArgumentException exception) {
                throw new IOException("Invalid song data for: " + title,
                    exception);
            }
        }

        if (loadedSongs.isEmpty()) {
            throw new IOException(
                "No tracks found. The page may have changed.");
        }

        Song[] songs = loadedSongs.toArray(new Song[loadedSongs.size()]);
        int added = 0;
        for (Song song : songs) {
            if (!playlist1.containsSong(song)) {
                playlist1.addSong(song);
                added++;
            }
        }
        return added;
    }


    private void requirePlaylist(Playlist playlist) {
        if (playlist == null) {
            throw new IllegalArgumentException("Playlist cannot be null");
        }
    }

}
