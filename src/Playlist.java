
import java.util.Random;

public class Playlist {
    private String name;
    private LinkedChain<Song> songs;

    public Playlist(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Must have a playlist name");
        }

        this.name = name.trim();
        this.songs = new LinkedChain<Song>();

    }


    public String getName() {
        return name;
    }


    public void addSong(Song song) {
        if (song == null) {
            throw new IllegalArgumentException("Cannot add a null song");
        }
        songs.addToEnd(song);
    }


    public void removeSong(Song song) {
        if (song == null) {
            throw new IllegalArgumentException("Song cannot be null");
        }
        if (!songs.remove(song)) {
            throw new IllegalArgumentException("Song is not in playlist");
        }
    }


    public Song removeSongAt(int index) {
        checkIndex(index);

        Song removedSong = songs.get(index);
        LinkedChain<Song> updatedSongs = new LinkedChain<Song>();

        for (int i = 0; i < songs.size(); i++) {
            if (i != index) {
                updatedSongs.addToEnd(songs.get(i));
            }
        }

        songs = updatedSongs;

        return removedSong;
    }


    public Song getSong(int index) {
        checkIndex(index);
        return songs.get(index);
    }


    public boolean containsSong(Song song) {
        if (song == null) {
            return false;
        }
        return songs.contains(song);
    }


    public int getSize() {
        return songs.size();
    }


    public boolean isEmpty() {
        return songs.isEmpty();
    }


    public LinkedChain<Song> getSongs() {
        return songs;
    }


    public Song getRandomSong(Random random) {
        if (random == null) {
            throw new IllegalArgumentException(
                "Random generator cannot be null");
        }
        if (songs.isEmpty()) {
            throw new IllegalStateException(
                "Cannot select a song from an empty playlist");
        }

        int index = random.nextInt(songs.size());
        return songs.get(index);
    }


    public void shuffle(Random random) {
    if (random == null) {
        throw new IllegalArgumentException(
            "Random generator can't be null");
    }

    Song[] shuffledSongs =
        songs.toArray(new Song[songs.size()]);

    // Fisher-Yates: swap each song with a random earlier position.
    for (int i = shuffledSongs.length - 1; i > 0; i--) {
        int randomIndex = random.nextInt(i + 1);

        Song temp = shuffledSongs[i];
        shuffledSongs[i] = shuffledSongs[randomIndex];
        shuffledSongs[randomIndex] = temp;
    }

    // Add to the front in reverse order to preserve the shuffled order.
    LinkedChain<Song> shuffledChain = new LinkedChain<Song>();
    for (int i = shuffledSongs.length - 1; i >= 0; i--) {
        shuffledChain.add(shuffledSongs[i]);
    }

    songs = shuffledChain;
}


    public LinkedChain<Song> getQueue() {
        LinkedChain<Song> queue = new LinkedChain<Song>();

        for (int i = 0; i < songs.size(); i++) {
            queue.addToEnd(songs.get(i));
        }

        return queue;
    }


    private void checkIndex(int index) {
        if (index < 0 || index >= songs.size()) {
            throw new IndexOutOfBoundsException("Song index " + index
                + " is out of bounds for playlist size " + songs.size());
        }
    }
    
    public LinkedChain<Song> getSongsByGenre(String genre) {
        if (genre == null || genre.trim().isEmpty()) {
            throw new IllegalArgumentException("Genre cannot be empty");
        }

        LinkedChain<Song> matchingSongs = new LinkedChain<Song>();

        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);

            if (song.getGenre().equalsIgnoreCase(genre.trim())) {
                matchingSongs.addToEnd(song);
            }
        }

        return matchingSongs;
    }

}
