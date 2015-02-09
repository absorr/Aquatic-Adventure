/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.core;

/**
 *
 * @author Will
 */
public class HandlerPlayer 
{
    public int hunger;
    public int appetite;
    public int level = 1;
    public boolean mate = false;
    
    public HandlerPlayer()
    {
        hunger = 10;
        appetite = 12;
    }
    
    public void update(int tpf)
    {
        if (Main.game.isInGame)
        {
            if(Main.game.player.getHealth() <= 0) //Death
            {
                Main.game.nifty.gotoScreen("fin");
            }

            if(hunger <= 0 && Main.game.ticks % 300 == 0) //Starve
            {
                Main.game.player.damage(1, Main.game.player);
            }

            if(Main.game.ticks % 1000 == 0 && hunger > 0) //Decrease hunger over time
            {
                decreaseHunger(1);
            }
            
            if(hunger > appetite - 3)
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
            
            //TODO; Update HUD
            
            System.out.println("Hunger increased to " + hunger);
        }
    }
    public void levelUp(int a)
    {
        level += 1;
        appetite += 3;
        hunger = appetite - 2;
        if (level == 2)
        {
            
        }
        if (level == 3)
        {
            mate = true;
        }
        if (level > 3)
        {
            Main.game.nifty.gotoScreen("win");
        }
    }
    
    public void mating()
    {
        //TODO code mating here
    }
}
