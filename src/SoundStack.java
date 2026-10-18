import java.util.Scanner;

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

    /**
     * Creates a SoundStack with a default username and no playlists.
     */
    public SoundStack() {
        this("User");
    }


    /**
     * Creates a SoundStack profile with the given username.
     *
     * @param username
     *            the user's name; must not be null or blank
     * @throws IllegalArgumentException
     *             if username is null or blank
     */
    public SoundStack(String username) {
        this(username, new Scanner(System.in));
    }


    public SoundStack(String username, Scanner input) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        this.username = username.trim();
        this.playlists = new LinkedChain<Playlist>();
        this.input = input;
    }


    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }


    /**
     * Gets the number of playlists.
     *
     * @return the number of playlists
     */
    public int getPlaylistCount() {
        return playlists.size();
    }


    /**
     * Creates a new playlist and adds it to the end of the playlist chain.
     *
     * @param name
     *            the playlist name; must not be null or blank
     * @return the new playlist
     * @throws IllegalArgumentException
     *             if the name is null or blank
     */
    public Playlist createPlaylist(String name) {
        Playlist newPlaylist = new Playlist(name);
        playlists.addToEnd(newPlaylist);
        return newPlaylist;
    }


    /**
     * Removes the playlist at the given index.
     *
     * @param index
     *            the index of the playlist
     * @return the removed playlist
     * @throws IndexOutOfBoundsException
     *             if the index is invalid
     */
    public Playlist removePlaylist(int index) {
        Playlist toRemove = playlists.get(index);
        playlists.remove(toRemove);
        return toRemove;
    }


    /**
     * Gets the playlist at the given index.
     *
     * @param index
     *            the index of the playlist
     * @return the playlist
     * @throws IndexOutOfBoundsException
     *             if the index is invalid
     */
    public Playlist getPlaylist(int index) {
        return playlists.get(index);
    }


    /**
     * Finds a playlist by name, ignoring case.
     *
     * @param name
     *            the name to look for
     * @return the matching playlist, or null if none is found
     */
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


    /**
     * Adds a song to the playlist at the given index.
     *
     * @param playlistIndex
     *            the zero-based index of the playlist
     * @param song
     *            the song to add
     */
    public void addSong(int playlistIndex, Song song) {
        getPlaylist(playlistIndex).addSong(song);
    }


    /**
     * Removes a song from the playlist at the given index.
     *
     * @param playlistIndex
     *            the zero-based index of the playlist
     * @param songIndex
     *            the zero-based index of the song
     * @return the removed song
     */
    public Song removeSong(int playlistIndex, int songIndex) {
        return getPlaylist(playlistIndex).removeSongAt(songIndex);
    }


    /**
     * Runs the main menu loop until the user quits.
     */
    public void run() {
        System.out.println("Welcome to SoundStack, " + username + "!");
        boolean running = true;

        while (running) {
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
        System.out.println("0. Quit");
    }


    // Re-prompt until the name is valid
    private void handleCreatePlaylist() {
        while (true) {
            System.out.print("Enter a playlist name: ");
            String name = input.nextLine();
            try {
                createPlaylist(name);
                System.out.println("Created playlist \"" + name.trim() + "\".");
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
        System.out.println("Removed playlist \"" + removed.getName() + "\".");
    }


    private void viewPlaylists() {
        if (playlists.isEmpty()) {
            System.out.println("You have no playlists yet.");
            return;
        }
        System.out.println("Your playlists:");
        for (int i = 0; i < playlists.size(); i++) {
            Playlist p = playlists.get(i);
            System.out.println((i + 1) + ". " + p.getName() + " (" + p.getSize()
                + " songs)");
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

        LinkedChain<Song> matchingSongs = selected.getSongsByGenre(genre);

        if (matchingSongs.isEmpty()) {
            System.out.println("No songs found in the " + genre + " genre.");
            return;
        }

        System.out.println("Songs in the " + genre + " genre:");

        for (int i = 0; i < matchingSongs.size(); i++) {
            System.out.println((i + 1) + ". " + matchingSongs.get(i));
        }
    }


    /**
     * Shows the playlists and asks the user to pick one.
     *
     * @return the zero-based index of the chosen playlist, or -1 if there
     *         are no playlists or the choice was invalid
     */
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


    /**
     * Reads an integer, re-prompting until the user enters a valid one.
     */
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


    /**
     * Reads an integer greater than zero, re-prompting until valid.
     */
    private int readPositiveInt(String prompt) {
        int value = readInt(prompt);
        while (value <= 0) {
            System.out.println("Value must be greater than zero.");
            value = readInt(prompt);
        }
        return value;
    }


    /**
     * Reads text that is not empty or whitespace only, re-prompting until
     * valid.
     */
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


    /**
     * Starts the SoundStack application.
     *
     * @param args
     *            command line arguments (not used)
     */
    public static void main(String[] args) {
        SoundStack app = new SoundStack();
        app.run();
    }
}
