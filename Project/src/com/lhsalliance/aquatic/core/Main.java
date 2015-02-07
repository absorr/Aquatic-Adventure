package com.lhsalliance.aquatic.core;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.export.binary.BinaryImporter;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;
import com.lhsalliance.aquatic.entities.Animal;
import com.lhsalliance.aquatic.entities.AnimalRegistry;
import com.lhsalliance.aquatic.entities.Model;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication implements AnimEventListener, ScreenController {

    public static Main game;
    public Animal player;
    protected Spatial teapot;
    private AnimChannel channel;
    private AnimControl control;
    private boolean isRunning = true;
    public CameraNode camNode;
    public ViewPort bgvp;
    public Picture bgpic;
    
    public static void main(String[] args) {
        Main app = new Main();
        AppSettings cfg = new AppSettings(true);
        cfg.setFrameRate(60); // set to less than or equal screen refresh rate
        cfg.setVSync(true);   // prevents page tearing
        cfg.setFrequency(60); // set to screen refresh rate
        cfg.setResolution(1024, 768);   
        cfg.setFullscreen(false); 
        cfg.setSamples(2);    // anti-aliasing
        cfg.setTitle("Aquatic Adventure"); // branding: window name
        cfg.setSettingsDialogImage("Interface/dialog.png");
        try {
          // Branding: window icon
          cfg.setIcons(new BufferedImage[]{ImageIO.read(new File("assets/Interface/icon.png"))});
        } catch (IOException ex) {
          Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Icon missing.", ex);
        }
        // branding: load splashscreen from assets
        //cfg.setSettingsDialogImage("Interface/MySplashscreen.png"); 
        //app.setShowSettings(false); // or don't display splashscreen
        app.setSettings(cfg);
        app.start();
        
        game = app;
    }

    @Override
    public void simpleInitApp() {
        AnimalRegistry.addAnimal(new Animal("Clownfish", 
                new Model("assets/Models/clownfish/clownfish.j3o"),
                10));
        
        //menuMain();
        btn_Start();
        
        initKeys(); // load my custom keybinding
        inputManager.setCursorVisible(true);
        flyCam.setEnabled(true);
        
        bgpic = new Picture("Background Picture");
        bgpic.setImage(assetManager, "Interface/newbg1.png", false);
        bgpic.setWidth(settings.getWidth());
        bgpic.setHeight(settings.getHeight());
        bgpic.setPosition(0,0);
        bgpic.updateGeometricState();
        
        bgvp = renderManager.createPreView("background", cam);
        bgvp.setClearFlags(true, true, true);
        bgvp.attachScene(bgpic);
        viewPort.setClearFlags(false, true, true);
    }

    @Override
    public void simpleUpdate(float tpf) {
        bgpic.updateLogicalState(1);
        bgpic.updateGeometricState();
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    /** Custom Keybinding: Map named actions to inputs. */
    private void initKeys() {
      // You can map one or several inputs to one named action
      inputManager.addMapping("Pause",  new KeyTrigger(KeyInput.KEY_P));
      inputManager.addMapping("Left",   new KeyTrigger(KeyInput.KEY_J));
      inputManager.addMapping("Right",  new KeyTrigger(KeyInput.KEY_K));
      inputManager.addMapping("Space", new KeyTrigger(KeyInput.KEY_SPACE));
      // Add the names to the action listener.
      inputManager.addListener(actionListener,"Pause", "Walk");
      inputManager.addListener(analogListener,"Left", "Right", "Rotate");

    }

    private ActionListener actionListener = new ActionListener() {
      public void onAction(String name, boolean keyPressed, float tpf) {
        if (name.equals("Pause") && !keyPressed) {
          isRunning = !isRunning;
        }
        
      }
    };

    private AnalogListener analogListener = new AnalogListener() {
      public void onAnalog(String name, float value, float tpf) {
        if (isRunning) {
          if (name.equals("Rotate")) {
            player.model.node.rotate(0, value*speed, 0);
          }
          if (name.equals("Right")) {
            Vector3f v = player.model.node.getLocalTranslation();
            player.model.node.setLocalTranslation(v.x + value*speed, v.y, v.z);
          }
          if (name.equals("Left")) {
            Vector3f v = player.model.node.getLocalTranslation();
            player.model.node.setLocalTranslation(v.x - value*speed, v.y, v.z);
          }
          if (name.equals("Space")){
              openBiomeScreen();
          }
        } else {
          System.out.println("Press P to unpause.");
        }
      }
    };
    
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
      if (animName.equals("Walk")) {
        channel.setAnim("stand", 0.50f);
        channel.setLoopMode(LoopMode.DontLoop);
        channel.setSpeed(1f);
      }
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
      // unused
    }
    
    private NiftyJmeDisplay niftyDisplay;
    private Nifty nifty;
    
    public void menuMain()
    {
        flyCam.setEnabled(false);
        niftyDisplay = new NiftyJmeDisplay(assetManager,
                                                          inputManager,
                                                          audioRenderer,
                                                          guiViewPort);
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/Screen.xml", "start", this);
        
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onStartScreen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void openBiomeScreen() {

       nifty.addXml("Interface/Screen.xml");

       nifty.gotoScreen("biome");

    }
    public void openAnimalScreen() {

       nifty.addXml("Interface/Screen.xml");

       nifty.gotoScreen("animal");
    }
    @Override
    public void onEndScreen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /*
     * Button Functions
     */
    
    public void btn_Start()
    {
        //Unload main menu
        //nifty.exit();
        
        player = AnimalRegistry.getAnimal("Clownfish");
        player.model.loadModel();
        
        // You must add a light to make the model visible
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.8f, -0.7f, -1.0f));
        rootNode.addLight(sun);
        
        //create the camera Node
        camNode = new CameraNode("Camera Node", cam);
        //This mode means that camera copies the movements of the target:
        camNode.setControlDir(ControlDirection.SpatialToCamera);
        //Attach the camNode to the target:
        player.model.node.attachChild(camNode);
        //Move camNode, e.g. behind and above the target:
        camNode.setLocalTranslation(new Vector3f(0, 70, -20));
        //Rotate the camNode to look at the target:
        camNode.lookAt(player.model.node.getLocalTranslation(), Vector3f.UNIT_Y);
    }
}
