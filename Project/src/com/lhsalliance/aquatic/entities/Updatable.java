/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.entities;

import java.util.HashSet;

/**
 * An abstract class that can be used to make objects preform tasks every frame
 * @author Will
 */
public abstract class Updatable 
{
    /** All of the instances that need regular updating **/
    public static HashSet<Updatable> objects = new HashSet<Updatable>();
    
    /** Constructor. Also adds the instance to the set **/
    public Updatable()
    {
        objects.add(this);
    }
    
    /** Method called every frame for updating **/
    public abstract void onUpdate(float tpf);
}
