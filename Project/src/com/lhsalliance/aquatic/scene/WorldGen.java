/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.scene;

import com.jme3.math.FastMath;
import com.lhsalliance.aquatic.core.Main;
import com.lhsalliance.aquatic.entities.Animal;
import com.lhsalliance.aquatic.entities.AnimalRegistry;
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
            
            for (int i=1;i<50;i++)
            {
                int randX = rand.nextInt(100);
                int randZ = rand.nextInt(100);
                
                if (randX > 50)
                {
                    randX -= 50;
                    randX *= -1;
                }
                if (randZ > 50)
                {
                    randZ -= 50;
                    randZ *= -1;
                }
                
                int x = (int) (randX * i * 0.5);
                int z = (int) (randZ * i * 0.5);
                
                int randIndx = rand.nextInt(153);
                int index = 3;
                
                if(randIndx <= 0 && randIndx < 20)
                    index = 0;
                else if(randIndx <= 20 && randIndx < 45)
                    index = 1;
                else if(randIndx <= 45 && randIndx < 53)
                    index = 2;
                else if(randIndx <= 53 && randIndx < 61)
                    index = 3;
                else if(randIndx <= 61 && randIndx < 66)
                    index = 4;
                else if(randIndx <= 66 && randIndx < 78)
                    index = 5;
                else if(randIndx <= 78 && randIndx < 93)
                    index = 6;
                else if(randIndx <= 93 && randIndx < 123)
                    index = 7;
                else if(randIndx <= 123 && randIndx < 137)
                    index = 8;
                else if(randIndx <= 137 && randIndx < 153)
                    index = 9;
                
                Animal animal = new Animal(AnimalRegistry.getAnimal((String) animals[index]));
                animal.model.loadModel();
                animal.model.node.setLocalTranslation(x + 10f, 0, z + 10f);
                
                float rotation = rand.nextInt(360)*FastMath.RAD_TO_DEG;
                
                animal.model.node.rotate(0, rotation, 0);
            }
        }
    }
}
