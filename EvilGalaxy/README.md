# README - check it out

### Main techniques: moving sprites, collision detection, three levels of difficulty, multiple levels, AI algorithms

## DEVELOPER NOTES:
To build the game, the following libraries (.jar) must be available for the project (currently provided in the **pom.xml**):
- voice-cmu-slt-hsmm-5.2
- marytts-lang-en-5.2
- marytts-runtime-5.2-jar-with-dependencies
- marytts-client-5.2-jar-with-dependencies  

Menu buttons are created by using https://www.photopea.com/  

## HOW TO LAUNCH, USING LAUNCHER:

Create an empty folder and download the Launcher.jnlp file into it, using the [![button](https://java.com/js/webstart.png)](https://me4gaming.com/LauncherEG/Launcher.jnlp) button or download the **EGinit.jar** into an empty folder from [here](https://github.com/Hunterszone/MyJavaGames/blob/master/EvilGalaxy/EGInit.jar?raw=true).  

After executing the file it will automatically download all the required stuff inside the dir 
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

## HOW TO LAUNCH MANUALLY:

### *For WINDOWS users:*   
Launch the game from the EvilGalaxy.exe WITHIN its folder or from a shortcut on your desktop.  
IMPORTANT: If you remove the .exe file outside the folder, the game resources won't be found, which will result in a failure to run the game.

### *For NON-WINDOWS users:*   
Launch the game from the EvilGalaxy.jar WITHIN its folder or from a shortcut on your desktop.  
IMPORTANT: If you remove the .jar file outside the folder, the game resources won't be found, which will result in a failure to run the game.



## MANUAL: 

Use the ARROWS to navigate your ship.   
Use [SPACE] and [CTRL] to fire, depends on which weapon is unlocked.  
Use [S] to mute or [A] to enable the background music.  
Use [P] to pause the game.  
Use [R] to restart the game when in running state.  
Use [E] to restart/resume if not in running state, or switch to E A S Y.  
Use [M] to restart/resume if not in running state, or switch to M E D I U M.  
Use [H] to restart/resume if not in running state, or switch to H A R D.  
Use [O] to open the game manual.  
Use [G] to turn ON or OFF G O D M O D E.  
Use keys [1] to [4] to switch the level.  
Use Alt+Z to instantly save the game state, Alt+X to enable auto-saving and Alt+C to load already saved game.   
Use [ESC] to quit the game.  

NB: Beware the electromagnetic field the EvilHead creates on level 4 

## AUTHOR: 

Konstantin Drenski


*ENJOY RESPONSIBLY! :)*
