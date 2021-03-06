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
import com.lhsalliance.aquatic.scene.HUD;
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
 * The root class. Includes initialization, game state, input handling,
 * and other essentials.
 * 
 * @author normenhansen & Will
 */
public class Main extends SimpleApplication implements AnimEventListener, ScreenController {

    public static Main game; //Instance of the game for use in other classes
    
    public Animal player; //The player
    public HandlerPlayer playerHandler = new HandlerPlayer(); //Handler for the player
    
    public HUD hud = new HUD(); //Handler for the heads up display
    
    public static int worldRadius = 500; //Radius of the world generated
    
    //State variables for the state of the game
    public boolean mate = false;
    public Model heart;
    public static boolean hide = false;
    public boolean tutorial = false;
    public boolean isRunning=true;
    public boolean isRight=false;
    public boolean isLeft=false;
    public boolean isUp=false;
    public boolean isDown=false;
    public boolean isInGame = false;
    public boolean isLoading = false;
    public boolean isLoaded = false;
    public boolean isStarted = false;
    
    //Animation handlers
    private AnimChannel channel;
    private AnimControl control;
    
    //The biome and seed for world gen
    public long seed = 1209345223;
    public Biome biome = BiomeRegistry.RIEF;
    
    //More handlers for the view
    public CameraNode camNode;
    public Node movementNode = new Node();
    public ViewPort bgvp;
    public Picture bgpic;
    public BitmapText loadText;
    
    //Ticks count for use in timing
    public int ticks = 0;
    
    /**
     * Called when the app starts
     * @param args arguments given to the application
     */
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

    /**
     * Called duting app initializer. First thing to be called when the game
     * is launched.
     */
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
        
        menuMain();
        
        
        initKeys(); // load my custom keybinding
        inputManager.setCursorVisible(true);
        flyCam.setEnabled(true);
    }

    /**
     * Called on every frame to update things.
     * @param tpf ticks per frame
     */
    @Override
    public void simpleUpdate(float tpf) {
        if (tutorial && isLoading)
        {
            loadGame();
            loadText.setText("Press SPACE to continue.");
            isLoading = false;
        }
        else if (tutorial)
        {
            isLoading = true;
        }
        
        if (isInGame && player != null)
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
            playerHandler.update(tpf);
        }
        
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
      inputManager.addMapping("Left",   new KeyTrigger(KeyInput.KEY_D), new KeyTrigger(KeyInput.KEY_RIGHT));
      inputManager.addMapping("Right",  new KeyTrigger(KeyInput.KEY_A), new KeyTrigger(KeyInput.KEY_LEFT));
      inputManager.addMapping("Up",     new KeyTrigger(KeyInput.KEY_W), new KeyTrigger(KeyInput.KEY_UP));
      inputManager.addMapping("Down",   new KeyTrigger(KeyInput.KEY_S), new KeyTrigger(KeyInput.KEY_DOWN));
    
    // Add the names to the action listener.
   inputManager.addListener(analogListener,"Left", "Right", "Up", "Down");
   inputManager.addListener(actionListener,"Space");

    }

    private ActionListener actionListener = new ActionListener() {
      public void onAction(String name, boolean keyPressed, float tpf) {
        if (name.equals("Pause") && !keyPressed) {
          isRunning = !isRunning;
        }
        
        if (name.equals("Space") && !keyPressed){
            System.out.println(tutorial + " " + isLoaded);
            if (!tutorial && !isLoaded)
            {
                Picture tutpic = new Picture("Tutorial Picture");
                tutpic.setImage(Main.game.getAssetManager(), "Interface/Instruction Screen.png", false);
                tutpic.setWidth(Main.game.getWidth());
                tutpic.setHeight(Main.game.getHeight());
                tutpic.setPosition(0,0);
                Main.game.getGuiNode().attachChild(tutpic);
                
                //Add loaded text
                loadText = new BitmapText(Main.game.getFont(), false);
                loadText.setSize(Main.game.getFont().getCharSet().getRenderedSize() * 2);
                loadText.setColor(new ColorRGBA(120f, 74f, 158f, 1f));
                loadText.setText("Loading...");
                loadText.setLocalTranslation(30, loadText.getLineHeight() + 30, 0);
                Main.game.getGuiNode().attachChild(loadText);
                
                tutorial = true;
            }
            
            else if(tutorial && isLoaded && !isStarted)
            {
                Main.game.startGame();
                isStarted = true;
            }
            else if(tutorial && isLoaded && isStarted)
                System.out.println("Emily is right");
            
        }
        
      }
    };

    private AnalogListener analogListener = new AnalogListener() {
      public void onAnalog(String name, float value, float tpf) {
        if (isRunning) {
            if (isInGame)
            {
                Vector3f v = player.model.node.getLocalTranslation();
                if (name.equals("Right") && Math.abs(v.x + value*10*speed) < worldRadius - 50) {
                    isRight = true;
                    Vector3f c = camNode.getLocalTranslation();
                    player.model.node.setLocalTranslation(v.x + value*10*speed, v.y, v.z);
                    camNode.setLocalTranslation(c.x + value*10*speed, c.y, c.z);
                    player.model.node.setLocalRotation(new Quaternion().fromAngleAxis(90*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
                    //channel.setAnim("Walk", 0.50f);
                    //channel.setLoopMode(LoopMode.Loop);
                  }
                  if (name.equals("Left") && Math.abs(v.x - value*10*speed) < worldRadius - 50) {
                    isLeft = true;
                    Vector3f c = camNode.getLocalTranslation();
                    player.model.node.setLocalTranslation(v.x - value*10*speed, v.y, v.z);
                    camNode.setLocalTranslation(c.x - value*10*speed, c.y, c.z);
                    player.model.node.setLocalRotation(new Quaternion().fromAngleAxis(270*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
                  }
                  if (name.equals("Up") && Math.abs(v.z + value*10*speed) < worldRadius - 50) {
                    isUp = true;
                    if (isRight) {
                          player.model.node.setLocalRotation(new Quaternion().fromAngleAxis(45*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
                      }
                    if (isLeft) {
                          player.model.node.setLocalRotation(new Quaternion().fromAngleAxis(315*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
                      }
                    Vector3f c = camNode.getLocalTranslation();
                    player.model.node.setLocalTranslation(v.x, v.y, v.z + value*10*speed);
                    camNode.setLocalTranslation(c.x, c.y, c.z + value*10*speed);
                    player.model.node.setLocalRotation(new Quaternion().fromAngleAxis(0*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
                  }
                  if (name.equals("Down") && Math.abs(v.z - value*10*speed) < worldRadius - 50) {
                    isDown = true;
                    if (isRight) {
                          player.model.node.setLocalRotation(new Quaternion().fromAngleAxis(135*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
                      }
                    if (isLeft) {
                          player.model.node.setLocalRotation(new Quaternion().fromAngleAxis(225*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
                      }
                    Vector3f c = camNode.getLocalTranslation();
                    player.model.node.setLocalTranslation(v.x, v.y, v.z - value*10*speed);
                    camNode.setLocalTranslation(c.x, c.y, c.z - value*10*speed);
                    player.model.node.setLocalRotation(new Quaternion().fromAngleAxis(180*FastMath.DEG_TO_RAD, new Vector3f(0,1,0)));
                  }
                  if (!name.equals("Right")) {
                    isRight = false;
                  }
                  if (!name.equals("Left")) {
                    isLeft = false;
                  }
                  if (!name.equals("Up")) {
                    isUp = false;
                  }
                  if (!name.equals("Down")) {
                    isDown = false;
                  }
                  if(isLeft || isRight || isUp || isDown)
                      HandlerPlayer.distTraveled++;
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
    
    //State variables for the various screens
    public NiftyJmeDisplay niftyDisplay;
    public Nifty nifty;
    
    /**
     * Called on to start up the main menu screen
     */
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
       tutorial = true;

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
    
    /**
     * Called on to load the game when the player presses start (after the menus)
     */
    public void loadGame()
    {
        if (!isLoaded)
        {
            //Make the player
            player = AnimalRegistry.getAnimal("Clownfish");
            player.model.loadModel();
            //control = player.model.node.getControl(AnimControl.class);
            //control.addListener(this);
            //channel = control.createChannel();
            //channel.setAnim("ArmatureAction");

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

            isLoaded = true;
        }
    }
    
    /**
     * Called on to initialize the game processes after everything is loaded
     * and the player presses space to continue
     */
    public void startGame()
    {
        //Unload main menu
        nifty.exit();
        Main.game.getGuiNode().detachAllChildren();
        
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
        
        isInGame = true;
    }
    
    public BitmapFont getFont()
    {
        return this.guiFont;
    }
    
    public int getWidth()
    {
        return this.settings.getWidth();
    }
    
    public int getHeight()
    {
        return this.settings.getHeight();
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
        HUD.setToast(HUD.ToastType.INFO, "You are hiding", "Enemies cannot see you now", 100);
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
