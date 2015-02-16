/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.core;

import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.ui.Picture;
import com.lhsalliance.aquatic.entities.Animal;
import com.lhsalliance.aquatic.scene.HUD;
import com.lhsalliance.aquatic.scene.HUD;

/**
 *
 * @author Will
 */
public class HandlerPlayer 
{
    //define integers
    public int hunger;
    public int appetite;
    public static int level = 1;
    public static int hideCount = 0;
    public static int eatCount = 0;
    public static int distTraveled = 0;
    
    public static String debug = "";
    
    public HandlerPlayer()
    {
        hunger = 10;
        appetite = 12;
    }
    
    public void update(float tpf)
    {
        if (Main.game.isInGame)
        {
            if(level == 1 && hideCount >= 15 && eatCount >= 10)
                levelUp();
            else if(level == 2 && distTraveled >= 10000 && eatCount >= 15)
                levelUp();
            else if(level == 3 && Main.game.mate)
                levelUp();
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
            
            eatCount++;
            
            System.out.println(eatCount + " " + hideCount + "Hunger increased to " + hunger);
        }
    }
    public void levelUp()
    {
        level += 1;
        appetite += 3;
        hunger = appetite - 2;
        if (level == 2)
        {
            Main.game.player.age = Animal.AnimalAge.TEEN;
            HUD.setToast(HUD.ToastType.OTHER, "Aged up", "You are now a teen", 100);
        }
        if (level == 3)
        {
            Main.game.mate = false;
            Main.game.player.age = Animal.AnimalAge.ADULT;
            HUD.setToast(HUD.ToastType.OTHER, "Aged up", "You are now an adult", 100);
        }
        hideCount = 0;
        eatCount = 0;
    } 
    
    
    
}
