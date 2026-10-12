package SoundStack;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

/**
 * Console controller for SoundStack. Stores playlists in memory and delegates
 * song storage and recommendations to Playlist and RecommendationEngine.
 * Uses the supplied default-package classes. Requires Jsoup on the build path.
 */
public class SoundStack {
    private final LinkedChain<Playlist> playlists;
    private final Playlist catalog;
    private final RecommendationEngine engine;
    private final Scanner input;
    private final Random random;
    private Playlist selectedPlaylist;

    /** Creates the application's shared objects, without downloading anything. */
    public SoundStack() {
        playlists = new LinkedChain<Playlist>();
        catalog = new Playlist("Recommendation Catalog");
        engine = new RecommendationEngine(catalog);
        input = new Scanner(System.in);
        random = new Random();
        selectedPlaylist = null;
    }

    /** @param args unused command-line arguments */
    public static void main(String[] args) {
        SoundStack app = new SoundStack();
        app.run();
    }

    /**
     * Creates and selects a playlist. Duplicate names are allowed; the menu
     * selects playlists by number so each remains accessible.
     * @param name playlist name
     * @return the newly created playlist
     */
    public Playlist createPlaylist(String name) {
        Playlist playlist = new Playlist(name);
        playlists.addToEnd(playlist);
        selectedPlaylist = playlist;
        return playlist;
    }

    /**
     * Finds the first playlist with the supplied name, ignoring case.
     * @param name name to find
     * @return first match, or null if not found
     */
    public Playlist findPlaylist(String name) {
        if (name == null) {
            return null;
        }
        Playlist[] all = playlists.toArray(new Playlist[playlists.size()]);
        for (Playlist playlist : all) {
            if (playlist.getName().equalsIgnoreCase(name.trim())) {
                return playlist;
            }
        }
        return null;
    }

    /**
     * Removes one playlist; clears selection if that playlist was selected.
     * @param playlist playlist to remove
     * @throws IllegalArgumentException if it is null or not stored
     */
    public void removePlaylist(Playlist playlist) {
        if (playlist == null || !playlists.remove(playlist)) {
            throw new IllegalArgumentException("Playlist not found.");
        }
        if (selectedPlaylist == playlist) {
            selectedPlaylist = null;
        }
    }

    /** Runs until the user chooses Exit or input ends. */
    public void run() {
        boolean running = true;
        while (running) {
            showMenu();
            try {
                int choice = readInt("Choose an option: ");
                switch (choice) {
                    case 0:
                        running = false;
                        break;
                    case 1:
                        createPlaylist(readLine("Playlist name: "));
                        System.out.println("Playlist created and selected.");
                        break;
                    case 2:
                        selectPlaylist();
                        break;
                    case 3:
                        showPlaylists();
                        break;
                    case 4:
                        addSong();
                        break;
                    case 5:
                        removeSong();
                        break;
                    case 6:
                        displaySongs(requireSelected().getSongs());
                        break;
                    case 7:
                        removePlaylist(requireSelected());
                        System.out.println("Playlist removed.");
                        break;
                    case 8:
                        Playlist playlist = requireSelected();
                        String genre = readLine("Genre: ");
                        displaySongs(engine.compileGenrePlaylist(playlist, genre));
                        break;
                    case 9:
                        int loaded = engine.loadSongsFromWebsite();
                        System.out.println("New catalog songs: " + loaded);
                        break;
                    case 10:
                        addRecommendations();
                        break;
                    case 11:
                        Playlist toShuffle = requireSelected();
                        toShuffle.shuffle(random);
                        displaySongs(toShuffle.getSongs());
                        break;
                    case 12:
                        System.out.println(requireSelected().getRandomSong(random));
                        break;
                    case 13:
                        displaySongs(catalog.getSongs());
                        break;
                    default:
                        System.out.println("Choose a number from 0 to 13.");
                }
            }
            catch (IOException exception) {
                System.out.println("Website loading failed: " + exception.getMessage());
            }
            catch (NumberFormatException exception) {
                System.out.println("Please enter a whole number.");
            }
            catch (IllegalArgumentException | IllegalStateException
                | IndexOutOfBoundsException exception) {
                System.out.println(exception.getMessage());
            }
            catch (NoSuchElementException exception) {
                running = false;
            }
        }
        System.out.println("Goodbye! Playlists are stored only for this session.");
    }

    /** Displays the available actions and current selection. */
    private void showMenu() {
        String selectedName = selectedPlaylist == null
            ? "none" : selectedPlaylist.getName();
        System.out.println("\nSoundStack | Selected: " + selectedName);
        System.out.println("1 Create playlist    2 Select playlist    3 List playlists");
        System.out.println("4 Add song           5 Remove song        6 View songs");
        System.out.println("7 Delete playlist    8 Filter by genre    9 Load Thriller catalog");
        System.out.println("10 Add recommendations   11 Shuffle   12 Pick random song");
        System.out.println("13 View catalog      0 Exit");
    }

    /** Displays playlist numbers, names, and sizes. */
    private void showPlaylists() {
        Playlist[] all = playlists.toArray(new Playlist[playlists.size()]);
        if (all.length == 0) {
            System.out.println("No playlists yet.");
        }
        for (int i = 0; i < all.length; i++) {
            System.out.println((i + 1) + ". " + all[i].getName()
                + " (" + all[i].getSize() + " songs)");
        }
    }

    /** Selects an existing playlist using its displayed number. */
    private void selectPlaylist() {
        if (playlists.isEmpty()) {
            throw new IllegalStateException("Create a playlist first.");
        }
        showPlaylists();
        int number = readInt("Playlist number: ");
        if (number < 1 || number > playlists.size()) {
            throw new IllegalArgumentException("Invalid playlist number.");
        }
        selectedPlaylist = playlists.get(number - 1);
    }

    /** Adds a manual song to the selected playlist and makes it recommendable. */
    private void addSong() {
        Playlist target = requireSelected();
        String title = readLine("Title: ");
        String artist = readLine("Artist: ");
        String genre = readLine("Genre: ");
        String duration = readLine("Duration in seconds (blank if unknown): ");
        Song song;
        if (duration.isEmpty()) {
            song = new Song(title, artist, genre);
        }
        else {
            song = new Song(title, artist, Integer.parseInt(duration), genre);
        }
        target.addSong(song);
        if (!catalog.containsSong(song)) {
            catalog.addSong(song);
        }
        System.out.println("Song added; also available in the recommendation catalog.");
    }

    /** Removes a specific song position, allowing duplicates to be distinguished. */
    private void removeSong() {
        Playlist target = requireSelected();
        if (target.isEmpty()) {
            throw new IllegalStateException("The selected playlist is empty.");
        }
        displaySongs(target.getSongs());
        int number = readInt("Song number to remove: ");
        if (number < 1 || number > target.getSize()) {
            throw new IllegalArgumentException("Invalid song number.");
        }
        System.out.println("Removed: " + target.removeSongAt(number - 1));
    }

    /** Requests recommendations and adds them through the engine. */
    private void addRecommendations() {
        Playlist target = requireSelected();
        if (catalog.isEmpty()) {
            throw new IllegalStateException(
                "Load the catalog with option 9 or add a song with option 4 first.");
        }
        String genre = readLine("Genre (Thriller songs use Synth-pop): ");
        int count = readInt("How many songs? ");
        LinkedChain<Song> added = engine.addRecommendations(target, genre, count);
        System.out.println("Added " + added.size() + " recommendations:");
        displaySongs(added);
    }

    /** @return selected playlist, or throws if none is selected */
    private Playlist requireSelected() {
        if (selectedPlaylist == null) {
            throw new IllegalStateException("Create or select a playlist first.");
        }
        return selectedPlaylist;
    }

    /** @param songs entries to display in their current order */
    private void displaySongs(LinkedChain<Song> songs) {
        Song[] entries = songs.toArray(new Song[songs.size()]);
        if (entries.length == 0) {
            System.out.println("No songs to display.");
        }
        for (int i = 0; i < entries.length; i++) {
            System.out.println((i + 1) + ". " + entries[i]);
        }
    }

    /** @param prompt text shown before reading @return trimmed input line */
    private String readLine(String prompt) {
        System.out.print(prompt);
        return input.nextLine().trim();
    }

    /** @param prompt text shown before reading @return parsed integer */
    private int readInt(String prompt) {
        return Integer.parseInt(readLine(prompt));
    }
}

