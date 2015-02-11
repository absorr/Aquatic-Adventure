/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.core;

import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;

/**
 *
 * @author Will
 */
public class HandlerPlayer 
{
    //define integers
    public int hunger;
    public int appetite;
    public int level = 1;
    public static int hideCount = 0;
    public static int eatCount = 0;
    
    
   
    
    public HandlerPlayer()
    {
        hunger = 10;
        appetite = 12;
    }
    
    public void update(float tpf)
    {
        if (Main.game.isInGame)
        {
            if(Main.game.player.getHealth() <= 0) //Death
            {
                Main.game.nifty.gotoScreen("fin");
                System.out.println("fin");
            }

            if(hunger <= 0 && Main.game.ticks % 300 == 0) //Starve
            {
                Main.game.player.damage(1, Main.game.player);
            }

            if(Main.game.ticks % 1000 == 0 && hunger > 0) //Decrease hunger over time
            {
                decreaseHunger(1);
            }
            
            if(hunger > appetite - 3 && Main.game.ticks % 700 == 0)
            {
                Main.game.player.addHealth(2);
            }
            Main.game.getGuiNode().detachAllChildren();
            BitmapText healthText = new BitmapText(Main.game.getFont(), false);
            healthText.setSize(Main.game.getFont().getCharSet().getRenderedSize() * 2);
            healthText.setColor(ColorRGBA.DarkGray);
            healthText.setText("Health: " + Main.game.player.getHealth() + "/"+Main.game.player.maxHealth +
                    "     Hunger: " + hunger + "/" + appetite +
                    "     Age: " + Main.game.player.age);
            healthText.setLocalTranslation(30, healthText.getLineHeight(), 0);
            Main.game.getGuiNode().attachChild(healthText);
        }
    }
    
    public void decreaseHunger(int amt)
    {
        if(hunger > 0)
        {
            hunger -= amt;
            
            //TODO Update HUD
            
            System.out.println("Hunger decreased to " + hunger);
        }
    }
    
    public void increaseHunger(int amt)
    {
        if(hunger < appetite)
        {
            hunger += amt;
            
            if(hunger > appetite)
                hunger = appetite;
            
            //TODO; Update HUD
            
            System.out.println("Hunger increased to " + hunger);
        }
    }
    public void levelUp()
    {
        level += 1;
        appetite += 3;
        hunger = appetite - 2;
        if (level == 2)
        {
            
        }
        if (level == 3)
        {
            Main.game.mate = true;
        }
        if (level > 3)
        {
            Main.game.nifty.gotoScreen("win");
        }
    } 
    
    
    
}
