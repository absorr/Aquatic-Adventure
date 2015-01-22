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
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
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
    protected Node player;
    protected Spatial teapot;
    private AnimChannel channel;
    private AnimControl control;
    private boolean isRunning = true;
    public CameraNode camNode;
    
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
        try {
          // Branding: window icon
          cfg.setIcons(new BufferedImage[]{ImageIO.read(new File("assets/Interface/icon.gif"))});
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
        //menuMain();
        btn_Start();
 
//        teapot = assetManager.loadModel("Models/Teapot/Teapot.obj");
//        Material mat_default = new Material( 
//            assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
//        teapot.setMaterial(mat_default);
//        rootNode.attachChild(teapot);
// 
//        // Create a wall with a simple texture from test_data
//        Box box = new Box(2.5f,2.5f,1.0f);
//        Spatial wall = new Geometry("Box", box );
//        Material mat_brick = new Material( 
//            assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//        mat_brick.setTexture("ColorMap", 
//            assetManager.loadTexture("Textures/Terrain/BrickWall/BrickWall.jpg"));
//        wall.setMaterial(mat_brick);
//        wall.setLocalTranslation(2.0f,-2.5f,0.0f);
//        rootNode.attachChild(wall);
// 
//        // Display a line of text with a default font
//        guiNode.detachAllChildren();
//        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
//        BitmapText helloText = new BitmapText(guiFont, false);
//        helloText.setSize(guiFont.getCharSet().getRenderedSize());
//        helloText.setText("Hello World");
//        helloText.setLocalTranslation(300, helloText.getLineHeight(), 0);
//        guiNode.attachChild(helloText);
// 
//        // Load a model from test_data (OgreXML + material + texture)
//        Spatial ninja = assetManager.loadModel("Models/Ninja/Ninja.mesh.xml");
//        ninja.scale(0.05f, 0.05f, 0.05f);
//        ninja.rotate(0.0f, -3.0f, 0.0f);
//        ninja.setLocalTranslation(0.0f, -5.0f, -2.0f);
//        rootNode.attachChild(ninja);
//        // You must add a light to make the model visible
//        DirectionalLight sun = new DirectionalLight();
//        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
//        rootNode.addLight(sun);
//        
//        //Box of rotations
//        /*Box b = new Box(1, 1, 1);
//        player = new Geometry("blue cube", b);
//        Material mat = new Material(assetManager,
//          "Common/MatDefs/Misc/Unshaded.j3md");
//        mat.setColor("Color", ColorRGBA.Pink);
//        player.setMaterial(mat);
//        player.setLocalTranslation(0.0f, 7.0f, -2.0f);
//        rootNode.attachChild(player);*/
//        
//        //Person of animations
//        player = (Node) assetManager.loadModel("Models/Oto/Oto.mesh.xml");
//        player.setLocalScale(0.5f);
//        player.setLocalTranslation(0.0f, 7.0f, -2.0f);
//        rootNode.attachChild(player);
//        control = player.getControl(AnimControl.class);
//        control.addListener(this);
//        channel = control.createChannel();
//        channel.setAnim("stand");
        
        initKeys(); // load my custom keybinding
        inputManager.setCursorVisible(true);
        flyCam.setEnabled(false);
    }

    @Override
    public void simpleUpdate(float tpf) {
        // make the player rotate:
        //teapot.rotate(0, 2*tpf, 0); 
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    public Spatial loadModel(Model m)
    {
        /** Load a model. Uses model and texture from jme3-test-data library! */ 
        Spatial teapot = Main.game.assetManager.loadModel(m.pathModel);
        Material defaultMat = new Material( assetManager, m.pathMaterial);
        teapot.setMaterial(defaultMat);
        rootNode.attachChild(teapot);
        
        return teapot;
    }
    
    /** Custom Keybinding: Map named actions to inputs. */
    private void initKeys() {
      // You can map one or several inputs to one named action
      inputManager.addMapping("Pause",  new KeyTrigger(KeyInput.KEY_P));
      inputManager.addMapping("Left",   new KeyTrigger(KeyInput.KEY_J));
      inputManager.addMapping("Right",  new KeyTrigger(KeyInput.KEY_K));
      inputManager.addMapping("Walk", new KeyTrigger(KeyInput.KEY_SPACE));
      // Add the names to the action listener.
      inputManager.addListener(actionListener,"Pause", "Walk");
      inputManager.addListener(analogListener,"Left", "Right", "Rotate");

    }

    private ActionListener actionListener = new ActionListener() {
      public void onAction(String name, boolean keyPressed, float tpf) {
        if (name.equals("Pause") && !keyPressed) {
          isRunning = !isRunning;
        }
        else if (name.equals("Walk") && !keyPressed) {
          if (!channel.getAnimationName().equals("Walk")) {
            channel.setAnim("Walk", 0.50f);
            channel.setLoopMode(LoopMode.Loop);
          }
        }
      }
    };

    private AnalogListener analogListener = new AnalogListener() {
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
 
        
        guiViewPort.addProcessor(niftyDisplay);
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onStartScreen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        
        //System.out.println("deeeeeeeebuggery!!!!");
        
        /** Load a Node from a .j3o file */
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
        // You must add a light to make the model visible
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.8f, -0.7f, -1.0f));
        rootNode.addLight(sun);
        
        //create the camera Node
        camNode = new CameraNode("Camera Node", cam);
        //This mode means that camera copies the movements of the target:
        camNode.setControlDir(ControlDirection.SpatialToCamera);
        //Attach the camNode to the target:
        player.attachChild(camNode);
        //Move camNode, e.g. behind and above the target:
        camNode.setLocalTranslation(new Vector3f(0, 70, -20));
        //Rotate the camNode to look at the target:
        camNode.lookAt(player.getLocalTranslation(), Vector3f.UNIT_Y);
    }
}
