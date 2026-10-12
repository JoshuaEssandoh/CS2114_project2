import student.TestCase;

/**
 * Tests for the SoundStack class.
 *
 * @author Colin Smith
 * @version October 2026
 */
public class SoundStackTest extends TestCase {
    private SoundStack stack;
    private Song songA;
    private Song songB;
    private SoundStack lastApp; // app used by the last runWithInput call

    /**
     * Sets up the test fixtures before each test.
     */
    public void setUp() {
        stack = new SoundStack();
        songA = new Song("Song A", "Artist A", 180, "Rock");
        songB = new Song("Song B", "Artist B", 200, "Pop");
    }


    /**
     * Runs the menu with simulated keyboard input and returns everything
     * that was printed.
     *
     * @param userInput
     *            the simulated keystrokes (use \n for Enter)
     * @return all console output produced by the run
     */
    private String runWithInput(String userInput) {
        systemOut().clearHistory();
        lastApp = new SoundStack("Tester", new java.util.Scanner(userInput));
        lastApp.run();
        return systemOut().getHistory();
    }


    /**
     * Default constructor uses the default name and starts empty.
     */
    public void testDefaultConstructor() {
        assertEquals("User", stack.getUsername());
        assertEquals(0, stack.getPlaylistCount());
    }


    /**
     * Username is trimmed.
     */
    public void testUsernameConstructorTrimsName() {
        SoundStack named = new SoundStack("  Alex  ");
        assertEquals("Alex", named.getUsername());
        assertEquals(0, named.getPlaylistCount());
    }


    /**
     * Null username is rejected.
     */
    public void testUsernameConstructorNull() {
        try {
            new SoundStack(null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // expected
        }
    }


    /**
     * Empty username is rejected.
     */
    public void testUsernameConstructorEmpty() {
        try {
            new SoundStack("");
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // expected
        }
    }


    /**
     * Whitespace-only username is rejected.
     */
    public void testUsernameConstructorWhitespace() {
        try {
            new SoundStack("   ");
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // expected
        }
    }


    /**
     * A valid playlist is created and stored.
     */
    public void testCreatePlaylist() {
        Playlist p = stack.createPlaylist("Workout");
        assertEquals("Workout", p.getName());
        assertEquals(1, stack.getPlaylistCount());
        assertSame(p, stack.getPlaylist(0));
    }


    /**
     * Playlists are added to the end of the chain.
     */
    public void testCreatePlaylistAddsToEnd() {
        Playlist first = stack.createPlaylist("First");
        Playlist second = stack.createPlaylist("Second");
        assertSame(first, stack.getPlaylist(0));
        assertSame(second, stack.getPlaylist(1));
    }


    /**
     * Playlist names are trimmed.
     */
    public void testCreatePlaylistTrimsName() {
        Playlist p = stack.createPlaylist("  Chill  ");
        assertEquals("Chill", p.getName());
    }


    /**
     * Null name is rejected and nothing is added.
     */
    public void testCreatePlaylistNull() {
        try {
            stack.createPlaylist(null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // expected
        }
        assertEquals(0, stack.getPlaylistCount());
    }


    /**
     * Empty name is rejected and nothing is added.
     */
    public void testCreatePlaylistEmpty() {
        try {
            stack.createPlaylist("");
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // expected
        }
        assertEquals(0, stack.getPlaylistCount());
    }


    /**
     * Whitespace-only name is rejected and nothing is added.
     */
    public void testCreatePlaylistWhitespace() {
        try {
            stack.createPlaylist("    ");
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // expected
        }
        assertEquals(0, stack.getPlaylistCount());
    }


    /**
     * Each valid index returns the right playlist.
     */
    public void testGetPlaylistValidIndexes() {
        Playlist a = stack.createPlaylist("A");
        Playlist b = stack.createPlaylist("B");
        Playlist c = stack.createPlaylist("C");
        assertSame(a, stack.getPlaylist(0));
        assertSame(b, stack.getPlaylist(1));
        assertSame(c, stack.getPlaylist(2));
    }


    /**
     * Negative index is rejected.
     */
    public void testGetPlaylistNegativeIndex() {
        stack.createPlaylist("A");
        try {
            stack.getPlaylist(-1);
            fail("Expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e) {
            // expected
        }
    }


    /**
     * Index equal to the size is rejected.
     */
    public void testGetPlaylistIndexTooLarge() {
        stack.createPlaylist("A");
        try {
            stack.getPlaylist(1);
            fail("Expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e) {
            // expected
        }
    }


    /**
     * Getting a playlist when there are none is rejected.
     */
    public void testGetPlaylistWhenNoPlaylists() {
        try {
            stack.getPlaylist(0);
            fail("Expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e) {
            // expected
        }
    }


    /**
     * Removing the first playlist.
     */
    public void testRemovePlaylistFirst() {
        Playlist a = stack.createPlaylist("A");
        Playlist b = stack.createPlaylist("B");
        assertSame(a, stack.removePlaylist(0));
        assertEquals(1, stack.getPlaylistCount());
        assertSame(b, stack.getPlaylist(0));
    }


    /**
     * Removing a middle playlist.
     */
    public void testRemovePlaylistMiddle() {
        Playlist a = stack.createPlaylist("A");
        Playlist b = stack.createPlaylist("B");
        Playlist c = stack.createPlaylist("C");
        assertSame(b, stack.removePlaylist(1));
        assertEquals(2, stack.getPlaylistCount());
        assertSame(a, stack.getPlaylist(0));
        assertSame(c, stack.getPlaylist(1));
    }


    /**
     * Removing the last playlist.
     */
    public void testRemovePlaylistLast() {
        Playlist a = stack.createPlaylist("A");
        Playlist b = stack.createPlaylist("B");
        assertSame(b, stack.removePlaylist(1));
        assertEquals(1, stack.getPlaylistCount());
        assertSame(a, stack.getPlaylist(0));
    }


    /**
     * Two playlists with the same name are treated as different playlists.
     */
    public void testRemovePlaylistSameNames() {
        Playlist first = stack.createPlaylist("Mix");
        Playlist second = stack.createPlaylist("Mix");
        assertSame(second, stack.removePlaylist(1));
        assertEquals(1, stack.getPlaylistCount());
        assertSame(first, stack.getPlaylist(0));
    }


    /**
     * Invalid indexes are rejected and nothing is removed.
     */
    public void testRemovePlaylistInvalidIndex() {
        stack.createPlaylist("A");
        try {
            stack.removePlaylist(-1);
            fail("Expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            stack.removePlaylist(1);
            fail("Expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e) {
            // expected
        }
        assertEquals(1, stack.getPlaylistCount());
    }


    /**
     * Removing when there are no playlists is rejected.
     */
    public void testRemovePlaylistWhenEmpty() {
        try {
            stack.removePlaylist(0);
            fail("Expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e) {
            // expected
        }
    }


    /**
     * Finds a playlist by exact name.
     */
    public void testFindPlaylistFound() {
        stack.createPlaylist("Road Trip");
        Playlist b = stack.createPlaylist("Study");
        assertSame(b, stack.findPlaylist("Study"));
    }


    /**
     * Search ignores case and surrounding spaces.
     */
    public void testFindPlaylistIgnoresCaseAndSpaces() {
        Playlist p = stack.createPlaylist("Road Trip");
        assertSame(p, stack.findPlaylist("  road trip "));
    }


    /**
     * Returns null when no playlist matches.
     */
    public void testFindPlaylistNotFound() {
        stack.createPlaylist("Road Trip");
        assertNull(stack.findPlaylist("Gym"));
    }


    /**
     * Returns null for a null name.
     */
    public void testFindPlaylistNullName() {
        stack.createPlaylist("Road Trip");
        assertNull(stack.findPlaylist(null));
    }


    /**
     * Returns null when there are no playlists.
     */
    public void testFindPlaylistWhenEmpty() {
        assertNull(stack.findPlaylist("Anything"));
    }


    /**
     * A song is added to a playlist.
     */
    public void testAddSong() {
        stack.createPlaylist("A");
        stack.addSong(0, songA);
        assertEquals(1, stack.getPlaylist(0).getSize());
        assertEquals(songA, stack.getPlaylist(0).getSong(0));
    }


    /**
     * Songs keep the order in which they were added.
     */
    public void testAddSongKeepsOrder() {
        stack.createPlaylist("A");
        stack.addSong(0, songA);
        stack.addSong(0, songB);
        assertEquals(songA, stack.getPlaylist(0).getSong(0));
        assertEquals(songB, stack.getPlaylist(0).getSong(1));
    }


    /**
     * The song goes only into the chosen playlist.
     */
    public void testAddSongToCorrectPlaylist() {
        stack.createPlaylist("A");
        stack.createPlaylist("B");
        stack.addSong(1, songA);
        assertTrue(stack.getPlaylist(0).isEmpty());
        assertEquals(1, stack.getPlaylist(1).getSize());
    }


    /**
     * Duplicate songs are allowed.
     */
    public void testAddSongAllowsDuplicates() {
        stack.createPlaylist("A");
        stack.addSong(0, songA);
        stack.addSong(0, songA);
        assertEquals(2, stack.getPlaylist(0).getSize());
    }


    /**
     * A null song is rejected.
     */
    public void testAddSongNull() {
        stack.createPlaylist("A");
        try {
            stack.addSong(0, null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // expected
        }
        assertTrue(stack.getPlaylist(0).isEmpty());
    }


    /**
     * Invalid playlist indexes are rejected.
     */
    public void testAddSongInvalidPlaylistIndex() {
        try {
            stack.addSong(0, songA);
            fail("Expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e) {
            // expected
        }
        stack.createPlaylist("A");
        try {
            stack.addSong(-1, songA);
            fail("Expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            stack.addSong(1, songA);
            fail("Expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e) {
            // expected
        }
    }


    /**
     * Removes the first song and returns it.
     */
    public void testRemoveSong() {
        stack.createPlaylist("A");
        stack.addSong(0, songA);
        stack.addSong(0, songB);
        assertEquals(songA, stack.removeSong(0, 0));
        assertEquals(1, stack.getPlaylist(0).getSize());
        assertEquals(songB, stack.getPlaylist(0).getSong(0));
    }


    /**
     * Removes the last song and returns it.
     */
    public void testRemoveSongLast() {
        stack.createPlaylist("A");
        stack.addSong(0, songA);
        stack.addSong(0, songB);
        assertEquals(songB, stack.removeSong(0, 1));
        assertEquals(1, stack.getPlaylist(0).getSize());
    }


    /**
     * Invalid song indexes are rejected and nothing is removed.
     */
    public void testRemoveSongInvalidSongIndex() {
        stack.createPlaylist("A");
        stack.addSong(0, songA);
        try {
            stack.removeSong(0, -1);
            fail("Expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            stack.removeSong(0, 1);
            fail("Expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e) {
            // expected
        }
        assertEquals(1, stack.getPlaylist(0).getSize());
    }


    /**
     * Removing from an empty playlist is rejected.
     */
    public void testRemoveSongFromEmptyPlaylist() {
        stack.createPlaylist("A");
        try {
            stack.removeSong(0, 0);
            fail("Expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e) {
            // expected
        }
    }


    /**
     * Removing when the playlist does not exist is rejected.
     */
    public void testRemoveSongInvalidPlaylistIndex() {
        try {
            stack.removeSong(0, 0);
            fail("Expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e) {
            // expected
        }
    }


    /**
     * A menu number that does not exist.
     */
    public void testRunInvalidMenuNumber() {
        String out = runWithInput("9\n0\n");
        assertTrue(out.contains("Invalid option. Please try again."));
    }


    /**
     * Text instead of a number at the menu.
     */
    public void testRunNonNumberMenuChoice() {
        String out = runWithInput("abc\n0\n");
        assertTrue(out.contains("Please enter a whole number."));
        assertTrue(out.contains("Goodbye!"));
    }


    /**
     * Creating a playlist from the menu.
     */
    public void testRunCreatePlaylist() {
        String out = runWithInput("1\nRoad Trip\n0\n");
        assertTrue(out.contains("Created playlist \"Road Trip\"."));
        assertEquals(1, lastApp.getPlaylistCount());
    }


    /**
     * A blank playlist name makes the menu ask again.
     */
    public void testRunCreatePlaylistRePromptsOnBlankName() {
        String out = runWithInput("1\n   \nRoad Trip\n0\n");
        assertTrue(out.contains("Invalid name"));
        assertTrue(out.contains("Created playlist \"Road Trip\"."));
        assertEquals(1, lastApp.getPlaylistCount());
    }


    /**
     * Viewing playlists when there are none.
     */
    public void testRunViewPlaylistsWhenNone() {
        String out = runWithInput("3\n0\n");
        assertTrue(out.contains("You have no playlists yet."));
    }


    /**
     * Viewing playlists shows names and song counts.
     */
    public void testRunViewPlaylistsShowsNamesAndSizes() {
        String out = runWithInput("1\nMix\n3\n0\n");
        assertTrue(out.contains("Your playlists:"));
        assertTrue(out.contains("1. Mix (0 songs)"));
    }


    /**
     * Viewing an empty playlist.
     */
    public void testRunViewEmptyPlaylist() {
        String out = runWithInput("1\nMix\n4\n1\n0\n");
        assertTrue(out.contains("is currently empty."));
    }


    /**
     * Viewing a playlist's songs when no playlists exist.
     */
    public void testRunViewPlaylistWithNoPlaylists() {
        String out = runWithInput("4\n0\n");
        assertTrue(out.contains("You have no playlists yet."));
    }


    /**
     * Choosing a playlist number that does not exist.
     */
    public void testRunChoosePlaylistThatDoesNotExist() {
        String out = runWithInput("1\nMix\n4\n5\n0\n");
        assertTrue(out.contains("Error: that playlist does not exist."));
    }


    /**
     * Adding a song and then viewing the playlist.
     */
    public void testRunAddSong() {
        String out = runWithInput(
            "1\nMix\n5\n1\nSong\nArtist\n200\nRock\n4\n1\n0\n");
        assertTrue(out.contains("Added: Song by Artist [Rock] (200s)"));
        assertEquals(1, lastApp.getPlaylist(0).getSize());
    }


    /**
     * Bad song input makes the menu ask again.
     */
    public void testRunAddSongRePromptsOnBadInput() {
        String out = runWithInput("1\nMix\n5\n1\n   \nSong\n\nArtist\n"
            + "-5\n0\nabc\n200\nRock\n0\n");
        assertTrue(out.contains("Input cannot be empty."));
        assertTrue(out.contains("Value must be greater than zero."));
        assertTrue(out.contains("Please enter a whole number."));
        assertEquals(1, lastApp.getPlaylist(0).getSize());
        Song added = lastApp.getPlaylist(0).getSong(0);
        assertEquals("Song", added.getTitle());
        assertEquals("Artist", added.getArtist());
        assertEquals(200, added.getDuration());
    }


    /**
     * Adding a song when no playlists exist.
     */
    public void testRunAddSongWithNoPlaylists() {
        String out = runWithInput("5\n0\n");
        assertTrue(out.contains("You have no playlists yet."));
    }


    /**
     * Removing a song from an empty playlist.
     */
    public void testRunRemoveSongFromEmptyPlaylist() {
        String out = runWithInput("1\nMix\n6\n1\n0\n");
        assertTrue(out.contains("is empty."));
    }


    /**
     * Removing a song from the menu.
     */
    public void testRunRemoveSong() {
        String out = runWithInput(
            "1\nMix\n5\n1\nSong\nArtist\n200\nRock\n6\n1\n1\n0\n");
        assertTrue(out.contains("Removed: Song by Artist [Rock] (200s)"));
        assertTrue(lastApp.getPlaylist(0).isEmpty());
    }


    /**
     * Removing a song number that does not exist.
     */
    public void testRunRemoveSongNotFound() {
        String out = runWithInput(
            "1\nMix\n5\n1\nSong\nArtist\n200\nRock\n6\n1\n7\n0\n");
        assertTrue(out.contains("Error: that song was not found."));
        assertEquals(1, lastApp.getPlaylist(0).getSize());
    }


    /**
     * Removing a playlist from the menu.
     */
    public void testRunRemovePlaylist() {
        String out = runWithInput("1\nMix\n2\n1\n0\n");
        assertTrue(out.contains("Removed playlist \"Mix\"."));
        assertEquals(0, lastApp.getPlaylistCount());
    }


    /**
     * Removing a playlist number that does not exist.
     */
    public void testRunRemovePlaylistInvalidNumber() {
        String out = runWithInput("1\nMix\n2\n5\n0\n");
        assertTrue(out.contains("Error: that playlist does not exist."));
        assertEquals(1, lastApp.getPlaylistCount());
    }


    /**
     * Removing a playlist when none exist.
     */
    public void testRunRemovePlaylistWithNoPlaylists() {
        String out = runWithInput("2\n0\n");
        assertTrue(out.contains("You have no playlists yet."));
    }


    /**
     * Quitting right away.
     */
    public void testRunQuitImmediately() {
        String out = runWithInput("0\n");
        assertTrue(out.contains("Welcome to SoundStack, Tester!"));
        assertTrue(out.contains("Goodbye!"));
    }
}
