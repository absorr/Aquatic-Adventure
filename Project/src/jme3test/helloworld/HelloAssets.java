package jme3test.helloworld;
 
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.ui.Picture;

public class HelloAssets extends SimpleApplication {
 
    public static void main(String[] args) {
        HelloAssets app = new HelloAssets();
        app.start();
    }
 
    @Override
    public void simpleInitApp() {
        inputManager.setCursorVisible(true);
        flyCam.setEnabled(false);
 
        Spatial teapot = assetManager.loadModel("Models/Teapot/Teapot.obj");
        Material mat_default = new Material( 
            assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        teapot.setMaterial(mat_default);
        rootNode.attachChild(teapot);
 
        Box box = new Box(2.5f,2.5f,1.0f);
        Spatial wall = new Geometry("Box", box );
        Material mat_brick = new Material( 
            assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_brick.setTexture("ColorMap", 
            assetManager.loadTexture("Textures/Terrain/BrickWall/BrickWall.jpg"));
        wall.setMaterial(mat_brick);
        wall.setLocalTranslation(2.0f,-2.5f,0.0f);
        rootNode.attachChild(wall);
 
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText helloText = new BitmapText(guiFont, false);
        helloText.setSize(guiFont.getCharSet().getRenderedSize());
        helloText.setText("Hello World");
        helloText.setLocalTranslation(300, helloText.getLineHeight(), 0);
        guiNode.attachChild(helloText);
 
        Spatial ninja = assetManager.loadModel("Models/Ninja/Ninja.mesh.xml");
        ninja.scale(0.05f, 0.05f, 0.05f);
        ninja.rotate(0.0f, -3.0f, 0.0f);
        ninja.setLocalTranslation(0.0f, -5.0f, -2.0f);
        rootNode.attachChild(ninja);
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        rootNode.addLight(sun);
        HelloAsset();
    }
    public void HelloAsset(){     
        Picture pic = new Picture("HUD Picture");
        pic.setImage(assetManager, "sunset.jpg", true);
        pic.setWidth(settings.getWidth()/2);
        pic.setHeight(settings.getHeight()/2);
        pic.setPosition(settings.getWidth()/4, settings.getHeight()/4);
        guiNode.attachChild(pic);

        BitmapText hudText;
        BitmapFont myFont = assetManager.loadFont("Interface/Fonts/myfont.fnt");
        hudText = new BitmapText(myFont, true);
        hudText.setSize(myFont.getCharSet().getRenderedSize());     
        hudText.setColor(ColorRGBA.Blue);                             
        hudText.setText("MAIN MENU");                               
        hudText.setLocalTranslation(250,300, 0); 
        guiNode.attachChild(hudText);
        CollisionResults results = new CollisionResults();
        Vector2f click2d = inputManager.getCursorPosition();
        Vector3f click3d = cam.getWorldCoordinates(
        new Vector2f(click2d.x, click2d.y), 0f).clone();
        Vector3f dir = cam.getWorldCoordinates(
        new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
        Ray ray = new Ray(click3d, dir);    
        
        hudText = new BitmapText(myFont, true);
        hudText.setSize(myFont.getCharSet().getRenderedSize());     
        hudText.setColor(ColorRGBA.Blue);                             
        hudText.setText("SETTINGS");                               
        hudText.setLocalTranslation(250,250, 0); 
        guiNode.attachChild(hudText); 
        
        hudText = new BitmapText(myFont, true);
        hudText.setSize(myFont.getCharSet().getRenderedSize());     
        hudText.setColor(ColorRGBA.Blue);                             
        hudText.setText("SAVE");                               
        hudText.setLocalTranslation(250,200, 0); 
        guiNode.attachChild(hudText);
        
        hudText = new BitmapText(myFont, true);
        hudText.setSize(myFont.getCharSet().getRenderedSize());     
        hudText.setColor(ColorRGBA.Blue);                   
        hudText.setText("RETURN TO GAME");                               
        hudText.setLocalTranslation(250,350, 0); 
        guiNode.attachChild(hudText);
        
        hudText = new BitmapText(myFont, true);
        hudText.setSize(myFont.getCharSet().getRenderedSize());     
        hudText.setColor(ColorRGBA.Blue);                   
        hudText.setText("PAUSE MENU");                               
        hudText.setLocalTranslation(320,450, 0); 
        guiNode.attachChild(hudText);
    }
}