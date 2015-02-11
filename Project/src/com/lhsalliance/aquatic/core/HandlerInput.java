/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.marine.core;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

/**
 *
 * @author Will
 */
public class HandlerInput 
{
    public static Geometry player;
    public static int speed;
    

    boolean isRunning;
    private ActionListener actionListener = new ActionListener() {
    public void onAction(String name, boolean keyPressed, float tpf) {
      if (name.equals("Pause") && !keyPressed) {
        isRunning = !isRunning;
      }
    }
  };
 
  private AnalogListener analogListener;

    public HandlerInput() {
        this.analogListener = new AnalogListener() {
    public void onAnalog(String name, float value, float tpf) {
        if (isRunning) {
    if (name.equals("Rotate")) {
    player.rotate(0, value*speed, 0);
}
    if (name.equals("Right")) {
        Vector3f v = player.getLocalTranslation();
        player.setLocalTranslation(v.x + value*speed, v.y, v.z);
}
    if (name.equals("Left")) {
        Vector3f v = player.getLocalTranslation();
        player.setLocalTranslation(v.x - value*speed, v.y, v.z);
}
} 
        else {
    System.out.println("Press P to unpause.");
        }
}
};

                }
}
