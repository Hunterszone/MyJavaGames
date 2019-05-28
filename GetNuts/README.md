# README - check it out!

### Load unlimited number of new levels and backgrounds dynamically:
- create your own levels and put them into the "levels"-folder, as you keep the level pattern
- create your own backgrounds and put them into the "backgrounds"-folder

### Generate random levels on the fly:
- double-press H to generate a random level and then press R to load it on the board
- this cool feature gives you an unlimited number of levels a.k.a. endless game state
- some levels will be impossible (really!); in this case just press C and move in random direction to cut some trees

## HOW TO LAUNCH, USING LAUNCHER:

Create an empty folder and download the Launcher.jnlp file into it, using the [![button](https://java.com/js/webstart.png)](http://me4gaming.com/LauncherGN/Launcher.jnlp) button.  
After executing the file, it will automatically download all the required stuff inside the dir 
and will launch the game immediately after that.  
It will also compare the game version and will update it, if needed.

NB.: Make sure you add http://me4gaming.com and https://me4gaming.com to the Java Exception Site List, using this article: https://www.java.com/en/download/faq/exception_sitelist.xml .  
And if you later struggle to run the JNLP application, make sure you've granted all permission within your 

```java
<jre location>\lib\security\java.policy
```
file like this:
  
```java
grant {
  permission java.security.AllPermission;
};
```

Otherwise just download the Launcher.jar into an empty folder from [here](https://github.com/Hunterszone/GetNuts/blob/master/Launcher.jar?raw=true).

## HOW TO INSTALL/LAUNCH (manually):

### *For WINDOWS users:*   
Run the setup.exe file, choose installation folder and install the game. Launch the game from the GetNuts.exe or from the shortcut on your desktop.

### *For NON-WINDOWS users:*   
Run the GetNuts.jar file WITHIN its folder. If you remove the .jar file outside the folder, the game resources won't be found, which will result in a failure to load the game.

## MANUAL: 

Move the nuts to their holes, as you have just a limited number of moves. Beware the water!

Use the ARROWS to move and to switch the level, once completed.  
Use 'S' to mute or 'A' to enable the background music.  
Use 'R' to restart the game.  
Use 'ESC' to quit the game.  

### *NB: Switching to a previous level is not allowed!*



## AUTHOR: 

Konstantin Drenski


*ENJOY RESPONSIBLY! :)*
