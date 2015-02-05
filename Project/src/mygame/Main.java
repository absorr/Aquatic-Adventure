package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.math.ColorRGBA;
import com.jme3.input.KeyInput;
import com.jme3.export.binary.BinaryImporter;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.font.BitmapText;
import com.jme3.light.DirectionalLight;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


/**
 * test
 * @author Matt
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
 protected Node player;
  Boolean isRunning=true;
  Boolean isRight=false;
  Boolean isLeft=false;
  Boolean isUp=false;
  Boolean isDown=false;
 
  @Override
  public void simpleInitApp() {
    flyCam.setEnabled(false); /** Load a Node from a .j3o file */
    String userHome = System.getProperty("user.home");
    BinaryImporter importer = BinaryImporter.getInstance();
        importer.setAssetManager(assetManager);
        File file = new File("assets/Models/clownfish/clownfish.j3o");
        try {
          player = (Node)importer.load(file);
          player.setName("loaded node");
          rootNode.attachChild(player);
        } catch (IOException ex) {
          Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "No saved node loaded.", ex);
          player = null;
        } 
    rootNode.attachChild(player);
    DirectionalLight sun = new DirectionalLight();
    sun.setDirection(new Vector3f(-0.8f, -0.7f, -1.0f));
    rootNode.addLight(sun);
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
    inputManager.addListener(actionListener,"Pause", "Menu1", "Menu2", "Menu3", "Right", "Left", "Up", "Down");
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
      /*
      if (isRight && !keyPressed) {
        float j;
        for(j = (float)1.0; j <= 10; j = j + (float).05){
            Vector3f v = player.getLocalTranslation();
            player.setLocalTranslation(v.x + (float).01*(speed/j), v.y, v.z);
          }
        isRight=false;
      }
      if (isLeft && !keyPressed) {
        float j;
        for(j = (float)1.0; j <= 10; j = j + (float).05){
            Vector3f v = player.getLocalTranslation();
            player.setLocalTranslation(v.x - (float).01*(speed/j), v.y, v.z);
          }
        isLeft=false;
      }
      if (isUp && !keyPressed) {
        float j;
        for(j = (float)1.0; j <= 10; j = j + (float).05){
            Vector3f v = player.getLocalTranslation();
            player.setLocalTranslation(v.x, v.y + (float).01*(speed/j), v.z);
          }
        isUp=false;
      }
      if (isDown && !keyPressed) {
        float j;
        for(j = (float)1.0; j <= 10; j = j + (float).05){
            Vector3f v = player.getLocalTranslation();
            player.setLocalTranslation(v.x, v.y - (float).01*(speed/j), v.z);
          }
        isDown=false;
      }*/
    }
  };
  
 
  private AnalogListener analogListener = new AnalogListener() {
    public void onAnalog(String name, float value, float tpf) {
      if (isRunning) {
        if (name.equals("Right")) {
          isRight = true;
          Vector3f v = player.getLocalTranslation();
          player.setLocalTranslation(v.x + value*8*speed, v.y, v.z);
          player.setLocalRotation(new Quaternion().fromAngleAxis(90*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
        }
        if (name.equals("Left")) {
          isLeft = true;
          Vector3f v = player.getLocalTranslation();
          player.setLocalTranslation(v.x - value*8*speed, v.y, v.z);
          player.setLocalRotation(new Quaternion().fromAngleAxis(270*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
        }
        if (name.equals("Up")) {
            if(isRight){
                player.setLocalRotation(new Quaternion().fromAngleAxis(45*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
            }
            if(isLeft){
                player.setLocalRotation(new Quaternion().fromAngleAxis(315*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
            }
          isUp = true;
          Vector3f v = player.getLocalTranslation();
          player.setLocalTranslation(v.x, v.y, v.z + value*8*speed);
          player.setLocalRotation(new Quaternion().fromAngleAxis(0*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
        }
        if (name.equals("Down")) {
            if(isRight){
                player.setLocalRotation(new Quaternion().fromAngleAxis(135*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
            }
            if(isLeft){
                player.setLocalRotation(new Quaternion().fromAngleAxis(225*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
            }
          isDown = true;
          Vector3f v = player.getLocalTranslation();
          player.setLocalTranslation(v.x, v.y, v.z - value*8*speed);
          player.setLocalRotation(new Quaternion().fromAngleAxis(180*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
        }
      } else {
        System.out.println("Press P to unpause.");
      }
    }
  };
}
