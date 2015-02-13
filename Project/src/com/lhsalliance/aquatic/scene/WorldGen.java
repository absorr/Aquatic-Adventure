/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.scene;

import com.jme3.math.FastMath;
import com.jme3.ui.Picture;
import com.lhsalliance.aquatic.core.Main;
import com.lhsalliance.aquatic.entities.Anemone;
import com.lhsalliance.aquatic.entities.Animal;
import com.lhsalliance.aquatic.entities.AnimalRegistry;
import com.lhsalliance.aquatic.entities.Hidable;
import com.lhsalliance.aquatic.entities.KillAI;
import com.lhsalliance.aquatic.entities.MoveAI;
import com.lhsalliance.aquatic.entities.Model;
import java.util.Random;

/**
 *
 * @author Will
 */
public class WorldGen 
{
    public static void generate()
    {
        if ("Rief".equals(Main.game.biome.displayName))
        {
            Random rand = new Random(Main.game.seed);
            Object[] animals = AnimalRegistry.getAnimalList();
            
            //Randomly Generate Animals
            for (int i=1;i<500;i++)
            {
                int x = rand.nextInt(500) + 10;
                int z = rand.nextInt(500) + 10;
                
                if(rand.nextBoolean())
                    x *= -1;
                
                if(rand.nextBoolean())
                    z *= -1;
                
                int randIndx = rand.nextInt(153);
                int index = 3;
                
                if(randIndx <= 0 && randIndx < 20)
                    index = 0;
                else if(randIndx >= 20 && randIndx < 45)
                    index = 1;
                else if(randIndx >= 45 && randIndx < 53)
                    index = 2;
                else if(randIndx >= 53 && randIndx < 61)
                    index = 3;
                else if(randIndx >= 61 && randIndx < 66)
                    index = 4;
                else if(randIndx >= 66 && randIndx < 78)
                    index = 5;
                else if(randIndx >= 78 && randIndx < 93)
                    index = 6;
                else if(randIndx >= 93 && randIndx < 123)
                    index = 7;
                else if(randIndx >= 123 && randIndx < 137)
                    index = 8;
                else if(randIndx >= 137 && randIndx < 153)
                    index = 9;
                
                Animal animal = new Animal(AnimalRegistry.getAnimal((String) animals[index]));
                animal.model.loadModel();
                animal.model.node.setLocalTranslation(x, 0, z);
                
                float rotation = rand.nextInt(360)*FastMath.RAD_TO_DEG;
                
                animal.model.node.rotate(0, rotation, 0);
                
                if ("Great White Shark".equals(animal.displayName) || 
                        "Hammerhead Shark".equals(animal.displayName) || 
                        "Tiger Shark".equals(animal.displayName))
                {   
                    animal.ai.add(new KillAI(25));
                }
                else
                    animal.ai.add(new MoveAI(25));
            }
            Picture bgpic = new Picture("Background Picture");
            bgpic.setImage(Main.game.getAssetManager(), "Interface/rocks/rock1.png", false);
            bgpic.setHeight(10);
            bgpic.setWidth(10);
            bgpic.setPosition(0,0);
            Main.game.getRootNode().attachChild(bgpic);
            
            //Randomly Generate Environment
            for(int i=1;i<300;i++)
            {
                int x = rand.nextInt(500) + 6;
                int z = rand.nextInt(500) + 6;
                
                if(rand.nextBoolean())
                    x *= -1;
                
                if(rand.nextBoolean())
                    z *= -1;
                
                int type = rand.nextInt(13);
                
                if (type >= 0 && type < 5)
                {
                    Hidable hide = new Hidable(new Model("assets/Models/branching coral/branching coral.j3o"));
                    Model model = hide.model;
                    model.loadModel();
                    model.node.setLocalTranslation(x, 0, z);
                    float rotation = rand.nextInt(360)*FastMath.RAD_TO_DEG;
                    model.node.rotate(0, rotation, 0);
                }
                else if (type >= 5 && type < 7)
                {
                    Anemone anm = new Anemone();
                    anm.model.node.setLocalTranslation(x, -6f, z);
                }
                else if (type >= 7 && type < 8)
                {
                    Model model = new Model("assets/Models/sea cucumber/sea cucumber.j3o");
                    model.loadModel();
                    model.node.setLocalTranslation(x, 0, z);
                    float rotation = rand.nextInt(360)*FastMath.RAD_TO_DEG;
                    model.node.rotate(0, rotation, 0);
                }
                else if (type >= 8 && type < 9)
                {
                    Model model = new Model("assets/Models/starfish/starfish.j3o");
                    model.loadModel();
                    model.node.setLocalTranslation(x, 0, z);
                    float rotation = rand.nextInt(360)*FastMath.RAD_TO_DEG;
                    model.node.rotate(0, rotation, 0);
                }
                else if (type >= 9 && type < 13)
                {
                    Hidable hide = new Hidable(new Model("assets/Models/tube coral/tube coral.j3o"));
                    Model model = hide.model;
                    model.loadModel();
                    model.node.setLocalTranslation(x, 0, z);
                    float rotation = rand.nextInt(360)*FastMath.RAD_TO_DEG;
                    model.node.rotate(0, rotation, 0);
                }
            }
        }
    }
}
