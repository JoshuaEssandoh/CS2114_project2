Reflection:

Andersson - In our original validation table, we planned to handle empty song titles and negative durations within the SoundStack.AddSong() method. I modified the Song class to throw IllegalArgumentExeption directly inside its constructor. 












Next Steps:

Andersson -  The Song class is complete and tested. My next step is to work with the SoundStack class to ensure the SoundStack.AddSong() method properly wraps the Song instantiation in a try-catch block. Additionally, I will need to implement a display formatter (either in Song.toString() or the UI) to convert the integer duration into a standard "MM:SS" format for the user interface.








Attribution:
Song.java - Andersson Galvez - Worked on the class structure, fields, and getter methods. Utilized GenAI to implement and format the IllegalArgumentException validation logic within the constructor.
SongTest.java - Andersson Galvez - Worked on the test class for normal and bad-input cases. Utilized GenAI to brainstorm and implement the bad-input edge cases.