How to Compile and Run:
### Setup
1. Download Jsoup in your browser and transfer it into a lib folder within your project.
2. Initialize all Jsoup imports in your Java library.
3. Clone this Git repository and transfer it to your local repository.
4. Run SoundStack.java as a Java Application

Reflection:

Changes from the Specification

In our original validation table, we planned to handle empty song titles and negative durations within the SoundStack.AddSong() method. However, we changed our approach by placing this validation directly in the Song class constructor. The constructor now throws an IllegalArgumentException when a song is created with invalid information. This approach keeps validation close to the data being validated and prevents invalid Song objects from being created. We also added another constructor that does not require a duration to support the recommendation engine.

Changes to LinkedChain

We used the provided LinkedChain class unchanged. Its existing methods were sufficient to support our application’s playlist functionality, so we did not need to modify or extend its implementation.

Resolving Unknowns

One of the main unknowns identified in our original scope was how to implement Jsoup. We figured out how to incorporate Jsoup into our project, helping us address that uncertainty and better understand how to use the library in our application.

Current Limitations

At this stage, all of our MVP features are working as intended. We have not identified any outstanding issues that prevent the MVP from functioning. However, additional testing and future stretch-goal development may reveal edge cases or areas for improvement.










Next Steps:
We have completed all of our MVP goals, along with several stretch goals, including the recommendation engine and shuffle function. We still plan to implement the genre identifier stretch goal, which will allow users to retrieve a list of songs within a playlist that belong to a specified genre. However, we have decided to drop the user profile feature due to time constraints.

To implement the genre identifier, we will add a method to the Playlist class that returns all songs matching a specified genre. We will also write additional tests to verify that the method returns the correct songs in different scenarios. These tests will cover matching genres, genres that are not present in the playlist, empty playlists, blank or null genre inputs, and playlists containing multiple genres.

Jacob and Josh will be responsible for overseeing the implementation of the genre identifier and ensuring that its functionality meets the project requirements. Anderson will focus on working with the SoundStack class to ensure the SoundStack.AddSong() method properly wraps the Song instantiation in a try-catch block. Additionally, will implement a display formatter (either in Song.toString() or the UI) to convert the integer duration into a standard "MM:SS" format for the user interface. Colin will be testing and debugging the existing MVP features, ensuring that all public methods handle normal inputs, invalid inputs, and boundary cases correctly. Once these tasks are complete, the team will perform final integration testing to verify that all features work as expected before submission.




Attribution:
Song.java - Andersson Galvez - Worked on the class structure, fields, and getter methods. Utilized GenAI to implement and format the IllegalArgumentException validation logic within the constructor.
SongTest.java - Andersson Galvez - Worked on the test class for normal and bad-input cases. Utilized GenAI to brainstorm and implement the bad-input edge cases.

Playlist.java - Jacob Dial - Wrote and implemented the Playlist class, including playlist management, song removal, shuffle, and recommendation functionality. Used GenAI assistance for code review and implementation guidance.
PlaylistTest.java - Jacob Dial - Wrote and revised unit tests for the Playlist class, testing normal behavior, invalid inputs, and boundary cases. Used GenAI assistance to help identify test cases and improve test coverage.

RecommendationEngine.java - Joshua Essandoh - Worked on the recommendation system and class structure. Based it on the Wikipedia page of Michael Jackson's album Thriller. Uses Jsoup to parse data from the internet and import Songs from a scraped playlist to the user's playlist. For our original idea for the RecommendationEngine class, it was supposed to take in a string input of a genre and output a song to be added into your created playlist. In practice, the process of getting a random song of a genre was very complicated; to simplify, one album was used as the library to obtain songs. In this case, the album was Michael Jackson's Thriller. Additionally, the Song.java class was edited to contain a constructor without the duration so the parsing process with Jsoup is easier.
