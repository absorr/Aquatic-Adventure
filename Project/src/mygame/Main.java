package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.math.ColorRGBA;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;

/**
 * test
 * @author Matt
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
 protected Geometry player;
  Boolean isRunning=true;
 
  @Override
  public void simpleInitApp() {
    flyCam.setEnabled(false);
    Box b = new Box(1, 1, 1);
    player = new Geometry("Player", b);
    Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat.setColor("Color", ColorRGBA.Blue);
    player.setMaterial(mat);
    rootNode.attachChild(player);
    initKeys(); // load my custom keybinding
  }
 
  /** Custom Keybinding: Map named actions to inputs. */
  private void initKeys() {
    // You can map one or several inputs to one named action
    inputManager.addMapping("Pause",  new KeyTrigger(KeyInput.KEY_P));
    inputManager.addMapping("Menu1",  new KeyTrigger(KeyInput.KEY_1));
    inputManager.addMapping("Menu2",  new KeyTrigger(KeyInput.KEY_2));
    inputManager.addMapping("Menu3",  new KeyTrigger(KeyInput.KEY_3));
    inputManager.addMapping("Left",   new KeyTrigger(KeyInput.KEY_A));
    inputManager.addMapping("Right",  new KeyTrigger(KeyInput.KEY_D));
    inputManager.addMapping("Up",     new KeyTrigger(KeyInput.KEY_W));
    inputManager.addMapping("Down",   new KeyTrigger(KeyInput.KEY_S));
    
    // Add the names to the action listener.
    inputManager.addListener(actionListener,"Pause", "Menu1", "Menu2", "Menu3");
    inputManager.addListener(analogListener,"Left", "Right", "Up", "Down");
 
  }
 
  private ActionListener actionListener = new ActionListener() {
    public void onAction(String name, boolean keyPressed, float tpf) {
      if (name.equals("Pause") && !keyPressed) {
        isRunning = !isRunning;
      }
      if (name.equals("Menu1") && !keyPressed) {
        isRunning = !isRunning;
        //Placeholder for menu code
      }
      if (name.equals("Menu2") && !keyPressed) {
        isRunning = !isRunning;
        //Placeholder for menu code
      }
      if (name.equals("Menu3") && !keyPressed) {
        isRunning = !isRunning;
        //Placeholder for menu code
      }
      /*if (name.equals("Right") && !keyPressed) {
        float j;
        for(j = (float)8.0; j >= 0; j = j - (float)0.005){
            Vector3f v = player.getLocalTranslation();
            player.setLocalTranslation(v.x + (float)1000*j*speed, v.y, v.z);
          }
      }*/
    }
  };
  
 
  private AnalogListener analogListener = new AnalogListener() {
    public void onAnalog(String name, float value, float tpf) {
      if (isRunning) {
        if (name.equals("Right")) {
          Vector3f v = player.getLocalTranslation();
          player.setLocalTranslation(v.x + value*8*speed, v.y, v.z);
        }
        if (name.equals("Left")) {
          Vector3f v = player.getLocalTranslation();
          player.setLocalTranslation(v.x - value*8*speed, v.y, v.z);
        }
        if (name.equals("Up")) {
          Vector3f v = player.getLocalTranslation();
          player.setLocalTranslation(v.x, v.y + value*8*speed, v.z);
        }
        if (name.equals("Down")) {
          Vector3f v = player.getLocalTranslation();
          player.setLocalTranslation(v.x, v.y - value*8*speed, v.z);
        }
      } else {
        System.out.println("Press P to unpause.");
      }
    }
  };
}
