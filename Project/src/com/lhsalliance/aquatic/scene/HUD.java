/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.scene;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.ui.Picture;
import com.lhsalliance.aquatic.core.HandlerPlayer;
import static com.lhsalliance.aquatic.core.HandlerPlayer.debug;
import static com.lhsalliance.aquatic.core.HandlerPlayer.distTraveled;
import static com.lhsalliance.aquatic.core.HandlerPlayer.eatCount;
import static com.lhsalliance.aquatic.core.HandlerPlayer.hideCount;
import static com.lhsalliance.aquatic.core.HandlerPlayer.level;
import com.lhsalliance.aquatic.core.Main;
import com.lhsalliance.aquatic.entities.Updatable;

/**
 * Heads Up Display (HUD)
 * @author Will
 */
public class HUD extends Updatable
{
    public enum ToastType{INFO, WARNING, OTHER}
    
    public static int toastTimer = 0;
    public static ToastType toastType = ToastType.OTHER;
    public static String toastText = "";
    public static String toastSub = "";
    
    @Override
    public void onUpdate(float tpf)
    {
        //Clear HUD
        Main.game.getGuiNode().detachAllChildren();

        //Add health text
        BitmapText healthText = new BitmapText(Main.game.getFont(), false);
        healthText.setSize(Main.game.getFont().getCharSet().getRenderedSize() * 2);
        healthText.setColor(ColorRGBA.DarkGray);
        healthText.setText("Health: " + Main.game.player.getHealth() + "/"+Main.game.player.maxHealth +
                "     Hunger: " + Main.game.playerHandler.hunger + "/" + Main.game.playerHandler.appetite +
                "Age : " + Main.game.player.age);
        healthText.setLocalTranslation(30, healthText.getLineHeight(), 0);
        Main.game.getGuiNode().attachChild(healthText);
        
        //Age Icon
        String ageIcoSrc;
        if(level == 1)
            ageIcoSrc = "Interface/fishbaby.png";
        else if(level == 2)
            ageIcoSrc = "Interface/fishadolescent.png";
        else
            ageIcoSrc = "Interface/fishadult.png";
        
        Picture ageIco = new Picture("Age Icon");
        ageIco.setImage(Main.game.getAssetManager(), ageIcoSrc, false);
        ageIco.setWidth(150);
        ageIco.setHeight(100);
        ageIco.setPosition(Main.game.getWidth() - 150, Main.game.getHeight() - 100);
        Main.game.getGuiNode().attachChild(ageIco);

        //Add objectives
        BitmapText objText = new BitmapText(Main.game.getFont(), false);
        objText.setSize(Main.game.getFont().getCharSet().getRenderedSize());
        objText.setColor(ColorRGBA.DarkGray);
        if(level == 1)
            objText.setText("Times Hidden: " + hideCount + 
                    "/15 | Times Fed = " + eatCount + "/10 " + debug);
        else if (level == 2)
            objText.setText("Distance Traveled: " + distTraveled + 
                    "/10000 | Times Fed = " + eatCount + "/15 " + debug);
        else if (level == 3)
            objText.setText("Find another Clownfish to mate with " + debug);
        else
        {
            objText.setText("YOU WON");
            Main.game.playerHandler.levelUp();
        }
        objText.setLocalTranslation(30, healthText.getLineHeight() * 2, 0);
        Main.game.getGuiNode().attachChild(objText);
        
        if(toastTimer > 0)
        {
            String toastImg;

            if (toastType == ToastType.INFO)
                toastImg = "Interface/toast-info.png";
            else if (toastType == ToastType.WARNING)
                toastImg = "Interface/toast-warn.png";
            else
                toastImg = "Interface/toast.png";

            Picture toastpic = new Picture("Toast Picture");
            toastpic.setImage(Main.game.getAssetManager(), toastImg, false);
            toastpic.setWidth(400);
            toastpic.setHeight(85);
            toastpic.setPosition(0, Main.game.getHeight() - 100);
            Main.game.getGuiNode().attachChild(toastpic);

            BitmapText hideText = new BitmapText(Main.game.getFont(), false);
            hideText.setSize(Main.game.getFont().getCharSet().getRenderedSize() * 2);
            hideText.setColor(ColorRGBA.Black);
            hideText.setText(toastText);
            hideText.setLocalTranslation(90, Main.game.getHeight() - 25, 0);
            Main.game.getGuiNode().attachChild(hideText);

            BitmapText hideText2 = new BitmapText(Main.game.getFont(), false);
            hideText2.setSize(Main.game.getFont().getCharSet().getRenderedSize());
            hideText2.setColor(ColorRGBA.DarkGray);
            hideText2.setText(toastSub);
            hideText2.setLocalTranslation(90,  Main.game.getHeight() - 70, 0);
            Main.game.getGuiNode().attachChild(hideText2);
            
            toastTimer--;
        }
        
        if(HandlerPlayer.level > 3)
        {
            Main.game.getGuiNode().detachAllChildren();
            
            Picture bgpic = new Picture("Win Picture");
            bgpic.setImage(Main.game.getAssetManager(), "Interface/endingscreen.png", false);
            bgpic.setWidth(Main.game.getWidth());
            bgpic.setHeight(Main.game.getHeight());
            bgpic.setPosition(0,0);
            Main.game.getGuiNode().attachChild(bgpic);
        }
    }
        
    public static void setToast(ToastType type, String msg, String sub, int time)
    {
        toastTimer = time;
        toastType = type;
        toastText = msg;
        toastSub = sub;
    }
}
