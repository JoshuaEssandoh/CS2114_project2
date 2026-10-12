package SoundStack;

//-------------------------------------------------------------------------
/**
* Represents an individual song and stores the information about that song.
* 
* @author galve
* @version Oct 11, 2026
*/
public class Song {
 // ~ Fields ................................................................
 private String title;
 private String artist;
 private int duration;
 private String genre;

 // ~ Constructors ..........................................................
 // ----------------------------------------------------------
 /**
  * Create a new Song object.
  * 
  * @param title
  *            the title of the song
  * @param artist
  *            the artist of the song
  * @param duration
  *            the length of the song in seconds (must be positive)
  * @param genre
  *            the genre of the song
  */
 public Song(String title, String artist, int duration, String genre) {
     if (title == null || title.trim().isEmpty()) {
         throw new IllegalArgumentException("Song title cannot be empty.");
     }
     if (artist == null || artist.trim().isEmpty()) {
         throw new IllegalArgumentException("Song artist cannot be empty.");
     }
     if (duration <= 0) {
         throw new IllegalArgumentException(
             "Song duration must be greater than zero.");
     }
     if (genre == null || genre.trim().isEmpty()) {
         throw new IllegalArgumentException("Song genre cannot be empty.");
     }

     this.title = title;
     this.artist = artist;
     this.duration = duration;
     this.genre = genre;
 }
 
 public Song(String title, String artist, String genre) {
     if (title == null || title.trim().isEmpty()) {
         throw new IllegalArgumentException("Song title cannot be empty.");
     }
     if (artist == null || artist.trim().isEmpty()) {
         throw new IllegalArgumentException("Song artist cannot be empty.");
     }
     if (genre == null || genre.trim().isEmpty()) {
         throw new IllegalArgumentException("Song genre cannot be empty.");
     }

     this.title = title;
     this.artist = artist;
     this.duration = -1;
     this.genre = genre;
 }


 // ~Public Methods ........................................................
 // ----------------------------------------------------------
 /**
  * Gets the title of the song.
  * 
  * @return the title
  */
 public String getTitle() {
     return title;
 }


 // ----------------------------------------------------------
 /**
  * Gets the artist of the song.
  * 
  * @return the artist
  */
 public String getArtist() {
     return artist;
 }


 // ----------------------------------------------------------
 /**
  * Gets the length of the song in seconds.
  * 
  * @return the duration
  */
 public int getDuration() {
     return duration;
 }


 // ----------------------------------------------------------
 /**
  * Gets the genre of the song.
  * 
  * @return the genre
  */
 public String getGenre() {
     return genre;
 }


 // ----------------------------------------------------------
 /**
  * Returns a summary of the song description.
  * 
  * @return the formatted string
  */
 @Override
 public String toString() {
     return title + " by " + artist + " [" + genre + "] (" + duration + "s)";
 }


 // ----------------------------------------------------------
 /**
  * Compares the fields for an equal song.
  * 
  * @param obj
  *            the object to compare
  * @return true if equal, false otherwise
  */
 @Override
 public boolean equals(Object obj) {
     if (this == obj) {
         return true;
     }
     if (obj == null || getClass() != obj.getClass()) {
         return false;
     }
     Song otherSong = (Song)obj;
     return duration == otherSong.duration && title.equals(otherSong.title)
         && artist.equals(otherSong.artist) && genre.equals(otherSong.genre);
 }
}
