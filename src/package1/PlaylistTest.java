package package1;
import student.TestCase;
import java.util.Random;

public class PlaylistTest
    extends TestCase
{

    private Playlist playlist;
    private Song song1;
    private Song song2;
    private Song song3;

    public void setUp()
    {
        playlist = new Playlist("My Playlist");

        song1 = new Song("Blinding Lights", "The Weeknd", 200, "Pop");
        song2 = new Song("Numb", "Linkin Park", 185, "Rock");
        song3 = new Song("Lose Yourself", "Eminem", 326, "Hip Hop");
    }


    public void testConstructor()
    {
        assertEquals("My Playlist", playlist.getName());
        assertTrue(playlist.isEmpty());
        assertEquals(0, playlist.getSize());
    }


    public void testContructorNullName()
    {
        Exception exception = null;

        try
        {
            new Playlist(null);
        }
        catch (IllegalArgumentException e)
        {
            exception = e;
        }

        assertNotNull(exception);
    }


    public void testConstructorEmptyName()
    {
        Exception exception = null;

        try
        {
            new Playlist("   ");
        }
        catch (IllegalArgumentException e)
        {
            exception = e;
        }

        assertNotNull(exception);
    }


    public void testConstructorTrimsName()
    {
        Playlist trimmed = new Playlist("  Favorites  ");

        assertEquals("Favorites", trimmed.getName());
    }


    public void testGetName()
    {
        assertEquals("My Playlist", playlist.getName());
    }


    public void testAddSong()
    {
        playlist.addSong(song1);

        assertEquals(1, playlist.getSize());
        assertFalse(playlist.isEmpty());
        assertEquals(song1, playlist.getSong(0));
    }


    public void testAddNullSong()
    {
        Exception exception = null;

        try
        {
            playlist.addSong(null);
        }
        catch (IllegalArgumentException e)
        {
            exception = e;
        }
        assertNotNull(exception);
        assertTrue(playlist.isEmpty());
    }


    public void testAddMultipleSongs()
    {

        playlist.addSong(song1);
        playlist.addSong(song2);
        playlist.addSong(song3);

        assertEquals(3, playlist.getSize());
        assertEquals(song1, playlist.getSong(0));
        assertEquals(song2, playlist.getSong(1));
        assertEquals(song3, playlist.getSong(2));
    }


    public void testRemoveSong()
    {
        playlist.addSong(song1);
        playlist.addSong(song2);

        playlist.removeSong(song1);

        assertEquals(1, playlist.getSize());
        assertFalse(playlist.containsSong(song1));
        assertTrue(playlist.containsSong(song2));
    }


    public void testRemoveNullSong()
    {
        Exception exception = null;

        try
        {
            playlist.removeSong(null);
        }
        catch (IllegalArgumentException e)
        {
            exception = e;
        }

        assertNotNull(exception);
    }


    public void testRemoveMissingSong()
    {
        playlist.addSong(song1);

        Exception exception = null;

        try
        {
            playlist.removeSong(song2);
        }
        catch (IllegalArgumentException e)
        {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals(1, playlist.getSize());
    }


    public void testRemoveSongAt()
    {
        playlist.addSong(song1);
        playlist.addSong(song2);
        playlist.addSong(song3);

        Song removed = playlist.removeSongAt(1);

        assertEquals(song2, removed);
        assertEquals(2, playlist.getSize());
        assertEquals(song1, playlist.getSong(0));
        assertEquals(song3, playlist.getSong(1));
    }


    public void testRemoveSongAtNegativeIndex()
    {
        playlist.addSong(song1);

        Exception exception = null;

        try
        {
            playlist.removeSongAt(-1);
        }
        catch (IndexOutOfBoundsException e)
        {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals(1, playlist.getSize());
    }


    public void testGetSong()
    {
        playlist.addSong(song1);
        playlist.addSong(song2);

        assertEquals(song1, playlist.getSong(0));
        assertEquals(song2, playlist.getSong(1));
    }


    public void testGetSongNegativeIndex()
    {
        playlist.addSong(song1);

        Exception exception = null;

        try
        {
            playlist.getSong(-1);
        }
        catch (IndexOutOfBoundsException e)
        {
            exception = e;
        }

        assertNotNull(exception);
    }


    public void testContainsSong()
    {
        playlist.addSong(song1);

        assertTrue(playlist.containsSong(song1));
        assertFalse(playlist.containsSong(song2));
        assertFalse(playlist.containsSong(null));
    }


    public void testGetSize()
    {
        assertEquals(0, playlist.getSize());

        playlist.addSong(song1);
        playlist.addSong(song2);

        assertEquals(2, playlist.getSize());
    }


    public void testIsEmpty()
    {
        assertTrue(playlist.isEmpty());

        playlist.addSong(song1);

        assertFalse(playlist.isEmpty());
    }


    public void testGetSongs()
    {
        playlist.addSong(song1);
        playlist.addSong(song2);

        LinkedChain<Song> songs = playlist.getSongs();

        assertEquals(2, songs.size());
        assertEquals(song1, songs.get(0));
        assertEquals(song2, songs.get(1));
    }


    public void testGetRandomSong()
    {
        playlist.addSong(song1);
        playlist.addSong(song2);
        playlist.addSong(song3);

        Song randomSong = playlist.getRandomSong(new Random(10));

        assertTrue(playlist.containsSong(randomSong));
    }


    public void testGetRandomSongNull()
    {
        playlist.addSong(song1);

        Exception exception = null;

        try
        {
            playlist.getRandomSong(null);
        }
        catch (IllegalArgumentException e)
        {
            exception = e;
        }

        assertNotNull(exception);
    }


    public void testGetRandomSongEmptyPlaylist()
    {
        Exception exception = null;

        try
        {
            playlist.getRandomSong(new Random(10));
        }
        catch (IllegalStateException e)
        {
            exception = e;
        }

        assertNotNull(exception);
    }


    public void testShuffle()
    {
        playlist.addSong(song1);
        playlist.addSong(song2);
        playlist.addSong(song3);

        playlist.shuffle(new Random(10));

        assertEquals(3, playlist.getSize());
        assertTrue(playlist.containsSong(song1));
        assertTrue(playlist.containsSong(song2));
        assertTrue(playlist.containsSong(song3));
    }


    public void testShuffleNullRandom()
    {
        playlist.addSong(song1);

        Exception exception = null;

        try
        {
            playlist.shuffle(null);
        }
        catch (IllegalArgumentException e)
        {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals(1, playlist.getSize());
    }


    public void testShuffleEmptyPlaylist()
    {
        playlist.shuffle(new Random(10));

        assertTrue(playlist.isEmpty());
        assertEquals(0, playlist.getSize());
    }


    public void testGetQueue()
    {
        playlist.addSong(song1);
        playlist.addSong(song2);

        LinkedChain<Song> queue = playlist.getQueue();

        assertEquals(2, queue.size());
        assertEquals(song1, queue.get(0));
        assertEquals(song2, queue.get(1));

        queue.remove();

        assertEquals(2, playlist.getSize());
        assertEquals(1, queue.size());
    }


    public void testGetQueueEmpty()
    {
        LinkedChain<Song> queue = playlist.getQueue();

        assertNotNull(queue);
        assertTrue(queue.isEmpty());
        assertTrue(playlist.isEmpty());

    }

}
