import student.TestCase;
import java.util.Scanner;

/** Integration checks for the GenAI-assisted menu revision.
 * @author Joshua Essandoh (GenAI-assisted)
 * @version October 2026
 */
public class SoundStackRecommendationTest extends TestCase {
    public void testRecommendations() {
        SoundStack app = new SoundStack("Tester", new Scanner(
            "1\nSource\n1\nTarget\n5\n1\nTrack\nArtist\n180\nRock\n"
            + "10\n1\nRock\n1\n2\n0\n"));
        app.run();
        assertEquals(1, app.getPlaylist(0).getSize());
        assertEquals(1, app.getPlaylist(1).getSize());
        assertEquals("Track", app.getPlaylist(1).getSong(0).getTitle());
    }
    public void testTooManyRecommendations() {
        SoundStack app = new SoundStack("Tester", new Scanner(
            "1\nSource\n1\nTarget\n5\n1\nTrack\nArtist\n180\nRock\n"
            + "10\n1\nRock\n2\n2\n0\n"));
        app.run();
        assertTrue(app.getPlaylist(1).isEmpty());
    }
    public void testLoadWithoutPlaylist() {
        new SoundStack("Tester", new Scanner("8\n0\n")).run();
    }
    public void testNullScanner() {
        try {
            new SoundStack("Tester", null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }
    }
}
