/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.entities;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.lhsalliance.aquatic.core.Main;

/**
 *
 * @author Will
 */
public class Anemone extends Updatable 
{
    public Model model;
    
    public Anemone()
    {
        super();
        model = new Model("assets/Models/anemone/anemone.j3o");
        model.loadModel();
        model.node.scale(3f);
    }
    
    @Override
    public void onUpdate(float tpf)
    {
        Node node = model.node;
        Node player = Main.game.player.model.node;
        
        Vector3f posAnimal = node.getLocalTranslation();
        Vector3f posPlayer = player.getLocalTranslation();
        
        float difX = posAnimal.x - posPlayer.x;
        float difZ = posAnimal.z - posPlayer.z;
        
        double dist = Math.sqrt(Math.pow(difX, 2) + Math.pow(difZ, 2));
        
        if (dist < 5 && Main.game.player.displayName == "Clownfish" && Main.game.ticks % 80 == 0)
        {
            Main.game.increaseHunger(2);
        }
    }
    
}
