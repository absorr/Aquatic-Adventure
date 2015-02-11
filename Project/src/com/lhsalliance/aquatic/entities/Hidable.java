/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.entities;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.lhsalliance.aquatic.core.Main;
import static com.lhsalliance.aquatic.entities.Updatable.objects;

/**
 *
 * @author Emily
 */
public class Hidable extends Updatable
{
    public boolean playHide = false;
    public Model model;
    
    public Hidable(Model safety)
    {
        super();
        model = safety;
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
        
        if (dist < 5 && !playHide)
        {
            Main.game.hiding();
            playHide = true;
        }
        
        if(dist < 5)
        {
            Main.hide = true;
        }
        else
        {
            playHide = false;
        }
    }
    
}
