/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.scene;

import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.ui.Picture;
import static com.lhsalliance.aquatic.core.HandlerPlayer.debug;
import static com.lhsalliance.aquatic.core.HandlerPlayer.distTraveled;
import static com.lhsalliance.aquatic.core.HandlerPlayer.eatCount;
import static com.lhsalliance.aquatic.core.HandlerPlayer.hideCount;
import static com.lhsalliance.aquatic.core.HandlerPlayer.level;
import com.lhsalliance.aquatic.core.Main;
import com.lhsalliance.aquatic.entities.Updatable;

/**
 *
 * @author Will
 */
public class ToastAlert extends Updatable
{
    public enum ToastType{
        INFO, WARNING, OTHER
    }
    
    public String message;
    public String subtitle;
    public ToastType type;
    public Picture bgpic;
    private int time = 0;
    
    public ToastAlert(String msg, String sub, ToastType typ)
    {
        super();
        this.message = msg;
        this.subtitle = sub;
        this.type = typ;
    }
    
    //Renders the alert
    public void show(int length)
    {
        this.time = length;
    }
    
    @Override
    public void onUpdate(float tpf) 
    {
        if (time != 0)
        {
            bgpic = new Picture("Background Picture");
            bgpic.setImage(Main.game.getAssetManager(), "Interface/newbg1.png", false);
            bgpic.setWidth(600);
            bgpic.setHeight(128);
            bgpic.setPosition(0,0);
            Main.game.getGuiNode().attachChild(bgpic);
            
            BitmapText objText = new BitmapText(Main.game.getFont(), false);
            objText.setSize(Main.game.getFont().getCharSet().getRenderedSize() * 1.5f);
            objText.setColor(ColorRGBA.White);
            objText.setText(message);
            objText.setLocalTranslation(140, 30, 0);
            Main.game.getGuiNode().attachChild(objText);
            
            time--;
        }
    }
}
