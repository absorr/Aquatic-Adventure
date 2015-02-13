package com.lhsalliance.aquatic.core;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.export.binary.BinaryImporter;
import com.jme3.font.BitmapFont;
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
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
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
import com.lhsalliance.aquatic.entities.Anemone;
import com.lhsalliance.aquatic.entities.Animal;
import com.lhsalliance.aquatic.entities.AnimalRegistry;
import com.lhsalliance.aquatic.entities.KillAI;
import com.lhsalliance.aquatic.entities.Model;
import com.lhsalliance.aquatic.entities.Updatable;
import com.lhsalliance.aquatic.scene.Biome;
import com.lhsalliance.aquatic.scene.BiomeRegistry;
import com.lhsalliance.aquatic.scene.WorldGen;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
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
    public HandlerPlayer playerHandler = new HandlerPlayer();
    
     //define age control bools
    public boolean mate = false;
    public Model heart;
    public static boolean hide = false;
    
    public boolean isRunning=true;
    public boolean isRight=false;
    public boolean isLeft=false;
    public boolean isUp=false;
    public boolean isDown=false;
    public boolean isInGame = false;
    
    private AnimChannel channel;
    private AnimControl control;
    
    public long seed = 1209345223;
    public Biome biome = BiomeRegistry.RIEF;
    
    public CameraNode camNode;
    public Node movementNode = new Node();
    public ViewPort bgvp;
    public Picture bgpic;
    
    public int ticks = 0;
    
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
        AnimalRegistry.addAnimal(new Animal("Great White Shark", 
                new Model("assets/Models/GreatWhiteShark/GreatWhiteShark.j3o"),
                50));
        AnimalRegistry.addAnimal(new Animal("Parrot Fish", 
                new Model("assets/Models/ParrotFish/ParrotFish.j3o"),
                50));
        AnimalRegistry.addAnimal(new Animal("Angelfish", 
                new Model("assets/Models/AngelFishRigged/AngelFishRigged.j3o"),
                50));
        AnimalRegistry.addAnimal(new Animal("Tiger Shark", 
                new Model("assets/Models/tiger shark/tiger shark.j3o"),
                50));
        AnimalRegistry.addAnimal(new Animal("Hammerhead Shark", 
                new Model("assets/Models/hammerhead shark/hammerhead shark.j3o"),
                50));
        AnimalRegistry.addAnimal(new Animal("Octopus", 
                new Model("assets/Models/octopus/octopus.j3o"),
                50));
        AnimalRegistry.addAnimal(new Animal("Sea Turtle", 
                new Model("assets/Models/Sea turtle/Sea turtle.j3o"),
                50));
        AnimalRegistry.addAnimal(new Animal("Moray Eel", 
                new Model("assets/Models/Moray eel/Moray eel.j3o"),
                50));
        AnimalRegistry.addAnimal(new Animal("Manta Ray", 
                new Model("assets/Models/MantaRay/MantaRay.j3o"),
                50));
        
        //menuMain();
        btn_Start();
        
        initKeys(); // load my custom keybinding
        inputManager.setCursorVisible(true);
        flyCam.setEnabled(true);
    }

    @Override
    public void simpleUpdate(float tpf) {
        
        if (isInGame)
        {
            bgpic.updateLogicalState(1);
            bgpic.updateGeometricState();
            hide = false;
            for (Updatable obj : Updatable.objects) {
                obj.onUpdate(tpf);
            }
            
            Iterator itr = Animal.getAnimals();
            while(itr.hasNext())
            {
                Animal ani = (Animal) itr.next();
                ani.update(tpf);
            }
        }
        
        playerHandler.update(tpf);
        
        ticks++;
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    /** Custom Keybinding: Map named actions to inputs. */
    private void initKeys() {
      // You can map one or several inputs to one named action
      inputManager.addMapping("Pause",  new KeyTrigger(KeyInput.KEY_ESCAPE));
      inputManager.addMapping("Space", new KeyTrigger(KeyInput.KEY_SPACE));
      inputManager.addMapping("Left",   new KeyTrigger(KeyInput.KEY_D));
    inputManager.addMapping("Right",  new KeyTrigger(KeyInput.KEY_A));
    inputManager.addMapping("Up",     new KeyTrigger(KeyInput.KEY_W));
    inputManager.addMapping("Down",   new KeyTrigger(KeyInput.KEY_S));
    
    // Add the names to the action listener.
    inputManager.addListener(analogListener,"Left", "Right", "Up", "Down", "Space");

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
            if (isInGame)
            {
                if (name.equals("Right")) {
                    isRight = true;
                    Vector3f v = player.model.node.getLocalTranslation();
                    Vector3f c = camNode.getLocalTranslation();
                    player.model.node.setLocalTranslation(v.x + value*8*speed, v.y, v.z);
                    camNode.setLocalTranslation(c.x + value*8*speed, c.y, c.z);
                    player.model.node.setLocalRotation(new Quaternion().fromAngleAxis(90*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
                  }
                  if (name.equals("Left")) {
                    isLeft = true;
                    Vector3f v = player.model.node.getLocalTranslation();
                    Vector3f c = camNode.getLocalTranslation();
                    player.model.node.setLocalTranslation(v.x - value*8*speed, v.y, v.z);
                    camNode.setLocalTranslation(c.x - value*8*speed, c.y, c.z);
                    player.model.node.setLocalRotation(new Quaternion().fromAngleAxis(270*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
                  }
                  if (name.equals("Up")) {
                      if(isRight){
                          player.model.node.setLocalRotation(new Quaternion().fromAngleAxis(45*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
                      }
                      if(isLeft){
                          player.model.node.setLocalRotation(new Quaternion().fromAngleAxis(315*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
                      }
                    isUp = true;
                    Vector3f v = player.model.node.getLocalTranslation();
                    Vector3f c = camNode.getLocalTranslation();
                    player.model.node.setLocalTranslation(v.x, v.y, v.z + value*8*speed);
                    camNode.setLocalTranslation(c.x, c.y, c.z + value*8*speed);
                    player.model.node.setLocalRotation(new Quaternion().fromAngleAxis(0*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
                  }
                  if (name.equals("Down")) {
                      if(isRight){
                          player.model.node.setLocalRotation(new Quaternion().fromAngleAxis(135*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
                      }
                      if(isLeft){
                          player.model.node.setLocalRotation(new Quaternion().fromAngleAxis(225*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
                      }
                    isDown = true;
                    Vector3f v = player.model.node.getLocalTranslation();
                    Vector3f c = camNode.getLocalTranslation();
                    player.model.node.setLocalTranslation(v.x, v.y, v.z - value*8*speed);
                    camNode.setLocalTranslation(c.x, c.y, c.z - value*8*speed);
                    player.model.node.setLocalRotation(new Quaternion().fromAngleAxis(180*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
                  }
                  
                  if(isLeft || isRight || isUp || isDown)
                      HandlerPlayer.distTraveled++;
            }
            else
            {
                if (name.equals("Space")){
                    //openBiomeScreen();
                    btn_Start();
                }
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
    
    public NiftyJmeDisplay niftyDisplay;
    public Nifty nifty;
    
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
    public void openTutorialScreen() {

       nifty.addXml("Interface/Screen.xml");

       nifty.gotoScreen("tutorial");

    }
    public void openBiomeScreen() {

       nifty.addXml("Interface/Screen.xml");

       nifty.gotoScreen("biome");
       

    }
    public void openAnimalScreen() {

       nifty.addXml("Interface/Screen.xml");

       nifty.gotoScreen("animal");
    }
    public void openFinScreen() {

       nifty.addXml("Interface/Screen.xml");

       nifty.gotoScreen("fin");
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
        nifty.exit();
        
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
        
        //Make the player
        player = AnimalRegistry.getAnimal("Clownfish");
        player.model.loadModel();
        
        // You must add a light to make the model visible
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.8f, -0.7f, 2.0f));
        rootNode.addLight(sun);
        
        //create the camera Node
        camNode = new CameraNode("Camera Node", cam);
        //This mode means that camera copies the movements of the target:
        camNode.setControlDir(ControlDirection.SpatialToCamera);
        //Attach the camNode to the target:
        rootNode.attachChild(movementNode);
        movementNode.attachChild(camNode);
        movementNode.attachChild(player.model.node);
        //Move camNode, e.g. behind and above the target:
        camNode.setLocalTranslation(new Vector3f(0, 50, -50));
        //Rotate the camNode to look at the target:
        camNode.lookAt(player.model.node.getLocalTranslation(), Vector3f.UNIT_Y);
        
        WorldGen.generate();
        
        isInGame = true;
    }
    
    public BitmapFont getFont()
    {
        return this.guiFont;
    }
    
       //age level control methods
    
    public void mating()
    {
        if(!mate)
        {
            heart = new Model("assets/Models/heart/heart.j3o");
            heart.loadModel();
            heart.node.setLocalRotation(new Quaternion().fromAngleAxis(90*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
        }
        heart.node.setLocalTranslation(player.model.node.getLocalTranslation().x, 
                player.model.node.getLocalTranslation().y + 4, 
                player.model.node.getLocalTranslation().z);
        this.mate = true;
    }
    public void hiding()
    {
        hide = true;
    }
    public boolean isHiding()
    {
        boolean retVal;
        if (hide == true)
            retVal = true;
        else
            retVal = false;
        return retVal;
    }
    
}
