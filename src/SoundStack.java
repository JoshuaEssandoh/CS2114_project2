import java.util.Scanner;
import java.io.IOException;

/**
 * Controls the overall SoundStack application and manages the user's
 * playlists.
 *
 * @author Colin Smith
 * @version October 2026
 */
public class SoundStack {
    private String username;
    private LinkedChain<Playlist> playlists;
    private Scanner input;

    public SoundStack() {
        this("User");
    }

    public SoundStack(String username) {
        this(username, new Scanner(System.in));
    }

    public SoundStack(String username, Scanner input) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (input == null) {
            throw new IllegalArgumentException("Scanner cannot be null");
        }

        this.username = username.trim();
        this.playlists = new LinkedChain<Playlist>();
        this.input = input;
    }

    public String getUsername() {
        return username;
    }

    public int getPlaylistCount() {
        return playlists.size();
    }

    public Playlist createPlaylist(String name) {
        Playlist newPlaylist = new Playlist(name);
        playlists.addToEnd(newPlaylist);
        return newPlaylist;
    }

    public Playlist removePlaylist(int index) {
        Playlist toRemove = playlists.get(index);
        playlists.remove(toRemove);
        return toRemove;
    }

    public Playlist getPlaylist(int index) {
        return playlists.get(index);
    }

    public Playlist findPlaylist(String name) {
        if (name == null) {
            return null;
        }

        for (int i = 0; i < playlists.size(); i++) {
            Playlist current = playlists.get(i);
            if (current.getName().equalsIgnoreCase(name.trim())) {
                return current;
            }
        }
        return null;
    }

    public void addSong(int playlistIndex, Song song) {
        getPlaylist(playlistIndex).addSong(song);
    }

    public Song removeSong(int playlistIndex, int songIndex) {
        return getPlaylist(playlistIndex).removeSongAt(songIndex);
    }

    public void run() {
        System.out.println("Welcome to SoundStack, " + username + "!");
        boolean running = true;

        while (running && input.hasNextLine()) {
            printMenu();
            int choice = readInt("Choose an option: ");

            if (choice == 1) {
                handleCreatePlaylist();
            }
            else if (choice == 2) {
                handleRemovePlaylist();
            }
            else if (choice == 3) {
                viewPlaylists();
            }
            else if (choice == 4) {
                handleViewPlaylist();
            }
            else if (choice == 5) {
                handleAddSong();
            }
            else if (choice == 6) {
                handleRemoveSong();
            }
            else if (choice == 7) {
                handleFindSongsByGenre();
            }
            else if (choice == 8) {
                handleLoadCatalog();
            }
            else if (choice == 10) {
                handleRecommendations();
            }
            else if (choice == 0) {
                running = false;
                System.out.println("Goodbye!");
            }
            else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("===== SoundStack Menu =====");
        System.out.println("1. Create a playlist");
        System.out.println("2. Remove a playlist");
        System.out.println("3. View all playlists");
        System.out.println("4. View a playlist's songs");
        System.out.println("5. Add a song to a playlist");
        System.out.println("6. Remove a song from a playlist");
        System.out.println("7. Find songs by genre");
        System.out.println("8. Load Thriller catalog");
        System.out.println("10. Add genre recommendations");
        System.out.println("0. Quit");
    }

    private void handleCreatePlaylist() {
        while (true) {
            System.out.print("Enter a playlist name: ");
            String name = input.nextLine();

            try {
                createPlaylist(name);
                System.out.println(
                    "Created playlist \"" + name.trim() + "\".");
                return;
            }
            catch (IllegalArgumentException e) {
                System.out.println("Invalid name: " + e.getMessage()
                    + ". Please try again.");
            }
        }
    }

    private void handleRemovePlaylist() {
        int index = choosePlaylist();
        if (index == -1) {
            return;
        }

        Playlist removed = removePlaylist(index);
        System.out.println(
            "Removed playlist \"" + removed.getName() + "\".");
    }

    private void viewPlaylists() {
        if (playlists.isEmpty()) {
            System.out.println("You have no playlists yet.");
            return;
        }

        System.out.println("Your playlists:");
        for (int i = 0; i < playlists.size(); i++) {
            Playlist playlist = playlists.get(i);
            System.out.println((i + 1) + ". " + playlist.getName()
                + " (" + playlist.getSize() + " songs)");
        }
    }

    private void handleViewPlaylist() {
        int index = choosePlaylist();
        if (index == -1) {
            return;
        }

        Playlist selected = getPlaylist(index);
        if (selected.isEmpty()) {
            System.out.println("Playlist \"" + selected.getName()
                + "\" is currently empty.");
            return;
        }

        System.out.println("Songs in \"" + selected.getName() + "\":");
        for (int i = 0; i < selected.getSize(); i++) {
            System.out.println((i + 1) + ". " + selected.getSong(i));
        }
    }

    private void handleAddSong() {
        int index = choosePlaylist();
        if (index == -1) {
            return;
        }

        String title = readNonEmpty("Enter song title: ");
        String artist = readNonEmpty("Enter song artist: ");
        int duration = readPositiveInt("Enter duration in seconds: ");
        String genre = readNonEmpty("Enter song genre: ");

        Song song = new Song(title, artist, duration, genre);
        addSong(index, song);
        System.out.println("Added: " + song);
    }

    private void handleRemoveSong() {
        int index = choosePlaylist();
        if (index == -1) {
            return;
        }

        Playlist selected = getPlaylist(index);
        if (selected.isEmpty()) {
            System.out.println("Error: playlist \"" + selected.getName()
                + "\" is empty.");
            return;
        }

        for (int i = 0; i < selected.getSize(); i++) {
            System.out.println((i + 1) + ". " + selected.getSong(i));
        }

        int songNumber = readInt("Enter the song number to remove: ");
        try {
            Song removed = removeSong(index, songNumber - 1);
            System.out.println("Removed: " + removed);
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("Error: that song was not found.");
        }
    }

    private void handleFindSongsByGenre() {
        int index = choosePlaylist();
        if (index == -1) {
            return;
        }

        Playlist selected = getPlaylist(index);
        if (selected.isEmpty()) {
            System.out.println("This playlist is empty.");
            return;
        }

        String genre = readNonEmpty("Enter the genre to search for: ");
        RecommendationEngine engine = new RecommendationEngine(selected);
        LinkedChain<Song> matchingSongs =
            engine.compileGenrePlaylist(selected, genre);

        if (matchingSongs.isEmpty()) {
            System.out.println(
                "No songs found in the " + genre + " genre.");
            return;
        }

        System.out.println("Songs in the " + genre + " genre:");
        for (int i = 0; i < matchingSongs.size(); i++) {
            System.out.println((i + 1) + ". " + matchingSongs.get(i));
        }
    }

    private void handleLoadCatalog() {
        int index = choosePlaylist();
        if (index == -1) {
            return;
        }

        try {
            RecommendationEngine engine =
                new RecommendationEngine(getPlaylist(index));
            int added = engine.loadSongsFromWebsite();

            System.out.println(
                "Loaded " + added + " new songs. Genre: Synth-pop.");
        }
        catch (IOException exception) {
            System.out.println(
                "Could not load catalog: " + exception);
        }
    }

    private void handleRecommendations() {
        System.out.println("Choose source playlist:");
        int source = choosePlaylist();
        if (source == -1) {
            return;
        }

        if (getPlaylist(source).isEmpty()) {
            System.out.println("This playlist is empty.");
            return;
        }

        String genre = readNonEmpty("Enter genre: ");
        int count = readPositiveInt("How many songs? ");

        System.out.println("Choose target playlist:");
        int target = choosePlaylist();
        if (target == -1) {
            return;
        }

        try {
            RecommendationEngine engine =
                new RecommendationEngine(getPlaylist(source));

            LinkedChain<Song> selected = engine.addRecommendations(
                getPlaylist(target), genre, count);

            System.out.println(
                "Added " + selected.size() + " recommendations.");

            for (int i = 0; i < selected.size(); i++) {
                System.out.println(selected.get(i));
            }
        }
        catch (IllegalArgumentException exception) {
            System.out.println(
                "Not enough songs in that genre for this count.");
        }
    }

    private int choosePlaylist() {
        if (playlists.isEmpty()) {
            System.out.println("You have no playlists yet.");
            return -1;
        }

        viewPlaylists();
        int number = readInt("Enter the playlist number: ");
        int index = number - 1;

        if (index < 0 || index >= playlists.size()) {
            System.out.println("Error: that playlist does not exist.");
            return -1;
        }

        return index;
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = input.nextLine().trim();

            try {
                return Integer.parseInt(line);
            }
            catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private int readPositiveInt(String prompt) {
        int value = readInt(prompt);

        while (value <= 0) {
            System.out.println("Value must be greater than zero.");
            value = readInt(prompt);
        }

        return value;
    }

    private String readNonEmpty(String prompt) {
        System.out.print(prompt);
        String line = input.nextLine();

        while (line.trim().isEmpty()) {
            System.out.println("Input cannot be empty.");
            System.out.print(prompt);
            line = input.nextLine();
        }

        return line.trim();
    }

    public static void main(String[] args) {
        SoundStack app = new SoundStack();
        app.run();
    }
}
