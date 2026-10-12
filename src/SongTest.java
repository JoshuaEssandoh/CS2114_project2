import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Tests the Song class.
 * 
 * @author galve
 * @version Oct 11, 2026
 */
public class SongTest extends TestCase {
    // ~ Fields ................................................................

    private Song validSong;

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................

    // ----------------------------------------------------------
    /**
     * Sets up the test fixture.
     */
    public void setUp() {
        validSong = new Song("Bohemian Rhapsody", "Queen", 355, "Rock");
    }

    // ----------------------------------------------------------
    /**
     * Tests that a valid song stores its fields correctly.
     */
    public void testValidSongCreation() {
        assertEquals("Bohemian Rhapsody", validSong.getTitle());
        assertEquals("Queen", validSong.getArtist());
        assertEquals(354, validSong.getDuration());
        assertEquals("Rock", validSong.getGenre());
    }

    // ----------------------------------------------------------
    /**
     * Tests the bad input case of providing an empty title.
     */
    public void testEmptyTitleSong() {
        try {
            validSong = new Song("", "Queen", 355, "Rock");
            fail("Expected an IllegalArgumentException to be thrown for empty titles.");
        }
        catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("empty"));
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests the bad input case of providing an empty artist.
     */
    public void testEmptyArtistSong() {
        try {
            validSong = new Song("Bohemian Rhapsody", "   ", 355, "Rock");
            fail("Expected an IllegalArgumentException to be thrown for empty artists.");
        }
        catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("empty"));
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests the bad input case of providing a non-positive duration.
     */
    public void testInvalidDurationSong() {
        try {
            validSong = new Song("Bohemian Rhapsody", "Queen", 0, "Rock");
            fail("Expected an IllegalArgumentException to be thrown for zero duration.");
        }
        catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("greater than zero"));
        }
        
        try {
            validSong = new Song("Bohemian Rhapsody", "Queen", -10, "Rock");
            fail("Expected an IllegalArgumentException to be thrown for negative duration.");
        }
        catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("greater than zero"));
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests the bad input case of providing an empty genre.
     */
    public void testEmptyGenreSong() {
        try {
            validSong = new Song("Bohemian Rhapsody", "Queen", 355, "");
            fail("Expected an IllegalArgumentException to be thrown for empty genres.");
        }
        catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("empty"));
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests the toString method.
     */
    public void testToString() {
        String output = validSong.toString();
        assertTrue(output.contains("Bohemian Rhapsody"));
        assertTrue(output.contains("Queen"));
        assertTrue(output.contains("355"));
        assertTrue(output.contains("Rock"));
    }

    // ----------------------------------------------------------
    /**
     * Tests the equals method with matching and non-matching objects.
     */
    public void testEquals() {
        Song duplicateSong = new Song("Bohemian Rhapsody", "Queen", 355, "Rock");
        Song differentSong = new Song("Numb", "Linkin Park", 187, "Rock");
        String notASong = "This is a string, not a song";
        
        assertTrue(validSong.equals(duplicateSong));
        assertFalse(validSong.equals(differentSong));
        assertFalse(validSong.equals(null));
        assertFalse(validSong.equals(notASong));
    }
}