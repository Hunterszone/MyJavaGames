package util;

import java.awt.Frame;

public class FrameUtils {

  private final static int VIBRATION_LENGTH = 1;
  private final static int VIBRATION_VELOCITY = 5;
  
  private FrameUtils() { }
  
  public static void vibrate(Frame frame) { 
    try { 
      final int originalX = frame.getLocationOnScreen().x; 
      final int originalY = frame.getLocationOnScreen().y; 
      for(int i = 0; i < VIBRATION_LENGTH; i++) { 
//        Thread.sleep(1); 
        frame.setLocation(originalX, originalY + VIBRATION_VELOCITY); 
//        Thread.sleep(1); 
        frame.setLocation(originalX, originalY - VIBRATION_VELOCITY);
//        Thread.sleep(1); 
        frame.setLocation(originalX + VIBRATION_VELOCITY, originalY);
//        Thread.sleep(1); 
        frame.setLocation(originalX, originalY);
      } 
    } 
    catch (Exception err) { 
      err.printStackTrace(); 
    } 
  }
}