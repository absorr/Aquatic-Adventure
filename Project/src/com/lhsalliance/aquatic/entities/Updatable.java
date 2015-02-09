/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.entities;

import java.util.HashSet;

/**
 *
 * @author Will
 */
public abstract class Updatable 
{
    public static HashSet<Updatable> objects = new HashSet<Updatable>();
    
    public Updatable()
    {
        objects.add(this);
    }
    
    public abstract void onUpdate(float tpf);
}
