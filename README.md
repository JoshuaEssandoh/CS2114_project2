Reflection:

Andersson - In our original validation table, we planned to handle empty song titles and negative durations within the SoundStack.AddSong() method. I modified the Song class to throw IllegalArgumentExeption directly inside its constructor. 












Next Steps:
We have completed all of our MVP goals, along with several stretch goals, including the recommendation engine and shuffle function. We still plan to implement the genre identifier stretch goal, which will allow users to retrieve a list of songs within a playlist that belong to a specified genre. However, we have decided to drop the user profile feature due to time constraints.

To implement the genre identifier, we will add a method to the Playlist class that returns all songs matching a specified genre. We will also write additional tests to verify that the method returns the correct songs in different scenarios. These tests will cover matching genres, genres that are not present in the playlist, empty playlists, blank or null genre inputs, and playlists containing multiple genres.

Jacob and Josh will be responsible for overseeing the implementation of the genre identifier and ensuring that its functionality meets the project requirements. Anderson will focus on working with the SoundStack class to ensure the SoundStack.AddSong() method properly wraps the Song instantiation in a try-catch block. Additionally, will implement a display formatter (either in Song.toString() or the UI) to convert the integer duration into a standard "MM:SS" format for the user interface. Colin will be testing and debugging the existing MVP features, ensuring that all public methods handle normal inputs, invalid inputs, and boundary cases correctly. Once these tasks are complete, the team will perform final integration testing to verify that all features work as expected before submission.




Attribution:
Song.java - Andersson Galvez - Worked on the class structure, fields, and getter methods. Utilized GenAI to implement and format the IllegalArgumentException validation logic within the constructor.
SongTest.java - Andersson Galvez - Worked on the test class for normal and bad-input cases. Utilized GenAI to brainstorm and implement the bad-input edge cases.

Playlist.java - Jacob Dial - Wrote and implemented the Playlist class, including playlist management, song removal, shuffle, and recommendation functionality. Used GenAI assistance for code review and implementation guidance.
PlaylistTest.java - Jacob Dial - Wrote and revised unit tests for the Playlist class, testing normal behavior, invalid inputs, and boundary cases. Used GenAI assistance to help identify test cases and improve test coverage.
