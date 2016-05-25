# Don-t-Catch-The-F

Introduction:
Don't Catch The F is the final project for my CS21B class. In the class we were asked to create a game with networking using java.

In Don't Catch The F, you act as a student trying to get the highest grade possible in a set amount of time.

How to play:
1. Download the file into your computer.
2. On windows, shift + right click on the src folder to open up the terminal.
2a. On Mac, you may go to finder, and click "Finder" on the menu bar. Look for Services then System Preferences then add the "Open folder at terminal"
2b. Go back to the folder and right click the src folder. Choose the services option at the bottom and click "Open New Window at Terminal"
3. Type javac *.java then enter. Afterwards, type java LaunchGame to start.

Multiplayer:
1. Do the same steps above, but first open one terminal to act as the server. (Take note of the ip address of the terminal, and make sure the two players are on the same wifi network)
2. Run java Server to run the server code.
3. Run java LaunchGame on both computers, and click the network bottom at the menu screen.
4. Enter the ip address of the server and enjoy.

Known Bugs:
- The objects have the same x coordinate, however, do to sync issues, the objects don't fall at the same time.
- There are some lag issues.