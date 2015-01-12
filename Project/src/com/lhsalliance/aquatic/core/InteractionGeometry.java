/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.core;

import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Will
 */
public class InteractionGeometry extends Geometry implements IInteractable {

    public InteractionGeometry(String name, Box box)
    {
        super(name, box);
    }
    
    @Override
    public String[] getInteractions() {
        String[] var1 = {"Eat", "Pee on", "Observe"};
        return var1;
    }

    @Override
    public void interact(String type, IInteractable sender) {
        switch(type)
        {
            case "Eat":
                System.out.println("Omm nom!");
                break;
            case "Pee on":
                System.out.println("Why?");
                break;
            case "Observe":
                System.out.println("It is a thing.");
                break;
            default:
                System.out.println("WARNING: Invalid interaction!");
                break;
        }
    }

    @Override
    public void click() {
        //TODO: Stuff
    }
    
}
