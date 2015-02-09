/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.entities;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Will
 */
public class Animal 
{
    public static enum AnimalAge
    {
        CHILD, TEEN, ADULT
    }
    
    public AnimalAge age;
    public String displayName;
    public Model model;
    public int maxHealth;
    public int health;
    public HashSet<AI> ai = new HashSet<AI>();
    
    protected static HashSet<Animal> animals = new HashSet<Animal>() {};
    
    public Animal(String name, Model animalModel, int animalHealth)
    {
        this.displayName = name;
        this.age = AnimalAge.CHILD;
        this.model = animalModel;
        this.maxHealth = animalHealth;
        this.health = animalHealth;
        
        animals.add(this);
    }
    
    public static Iterator getAnimals()
    {
        return animals.iterator();
    }
    
    public void update(float tpf)
    {
        Iterator itr = ai.iterator();
        
        while (itr.hasNext())
        {
            AI a = (AI) itr.next();
            a.onUpdate(tpf, this);
        }
    }
}
