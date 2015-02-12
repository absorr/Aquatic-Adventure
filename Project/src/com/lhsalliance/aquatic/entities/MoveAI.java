/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.entities;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.lhsalliance.aquatic.core.Main;
import java.util.Random;

/**
 *
 * @author Emily
 */
public class MoveAI implements AI  {
    
    private float radius;
    private int wait = -1;
    public int xInt = 0; 
    public int zInt = 0;
    
    public MoveAI(float searchRadius)
    {
        this.radius = searchRadius;    
    }
    @Override
    public void onUpdate(float tpf, Animal animal)
    {
        Node node = animal.model.node;
        
        Vector3f posAnimal = node.getLocalTranslation();

        Random randomGenerator = new Random();
        int time= (2+ randomGenerator.nextInt(5))*100;
        if (posAnimal.x==xInt && posAnimal.z ==zInt)
        {
            xInt = 100 - randomGenerator.nextInt(500);
            zInt = 100 -randomGenerator.nextInt(500);
            
        }
        else if (Main.game.ticks%time == 0)
        {
            xInt = 100 - randomGenerator.nextInt(400);
            zInt = 100 -randomGenerator.nextInt(700);
            
        }
        
        float difX = posAnimal.x - xInt;
        float difZ = posAnimal.z - zInt;
        
        posAnimal.x = posAnimal.x + (difX/100000);
        posAnimal.z = posAnimal.z + (difZ/100000);
        
        double dist = Math.sqrt(Math.pow(difX, 2) + Math.pow(difZ, 2));
        
        float angle = (float)Math.atan(Math.abs(difX)/Math.abs(difZ));
        
        if(difX < 0 && difZ < 0)
        {
            angle = (float)Math.atan(Math.abs(difX)/Math.abs(difZ));
        }
        else if(difX > 0 && difZ < 0)
        {
            angle = (float)Math.atan(Math.abs(difZ)/Math.abs(difX))- 90*FastMath.DEG_TO_RAD;
        }
        else if(difX > 0 && difZ > 0)
        {
            angle = (float)Math.atan(Math.abs(difX)/Math.abs(difZ))+ 180*FastMath.DEG_TO_RAD;
        }
        else if(difX < 0 && difZ > 0)
        {
            angle = (float)Math.atan(Math.abs(difZ)/Math.abs(difX))+ 90*FastMath.DEG_TO_RAD;
        }
        
        if (difZ == 0  && difX < 0)
        {
            angle = 0;
        }
        node.setLocalRotation(new Quaternion().fromAngleAxis(angle, new Vector3f(0,1,0)));
            Vector3f forward = node.getLocalRotation().getRotationColumn(2);
            node.move(forward.divide(4));
    }
    
}
