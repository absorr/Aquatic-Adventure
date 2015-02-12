/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.entities;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.lhsalliance.aquatic.core.HandlerPlayer;
import com.lhsalliance.aquatic.core.Main;
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
    private int health;
    public HashSet<AI> ai = new HashSet<AI>();
    
    protected static HashSet<Animal> animals = new HashSet<Animal>();
    
    public Animal(String name, Model animalModel, int animalHealth)
    {
        this.displayName = name;
        this.age = AnimalAge.CHILD;
        this.model = animalModel;
        this.maxHealth = animalHealth;
        this.health = animalHealth;
        
        animals.add(this);
    }
    
    public Animal(Animal copy)
    {
        this.displayName = copy.displayName;
        this.age = AnimalAge.CHILD;
        this.model = new Model(copy.model.pathModel);
        this.maxHealth = copy.maxHealth;
        this.health = copy.maxHealth;
        
        animals.add(this);
    }
    
    public static Iterator getAnimals()
    {
        return animals.iterator();
    }
    
    public void update(float tpf)
    {
        for (AI a : ai) {
            a.onUpdate(tpf, this);
        }
        
        if(model.node == null)
            return;
        
        Node node = model.node;
        Node player = Main.game.player.model.node;
        
        Vector3f posAnimal = node.getLocalTranslation();
        Vector3f posPlayer = player.getLocalTranslation();
        
        float difX = posAnimal.x - posPlayer.x;
        float difZ = posAnimal.z - posPlayer.z;
        
        double dist = Math.sqrt(Math.pow(difX, 2) + Math.pow(difZ, 2));
        
        if (HandlerPlayer.level == 3 && dist < 5 && displayName.equals(Main.game.player.displayName) && difX != 0 && difZ != 0)
        {
            Main.game.mating();
        }
    }
    
    public int getHealth()
    {
            return this.health;
    }
    
    public void damage(int ammount, Animal attacker)
    {
        this.health -= ammount;
        
        if (this.health <= 0)
        {
            //TODO: Death
        }
    }
    
    public void addHealth(int ammount)
    {
        this.health += ammount;
        
        if (health > maxHealth)
            health = maxHealth;
    }
}
