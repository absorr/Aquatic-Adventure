/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.marine.animals;

import com.lhsalliance.marine.core.Model;

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
    
    public static enum AnimalType
    {
        MAMMAL, FISH
    }
    
    private AnimalAge age;
    private AnimalType type;
    private String displayName;
    private Model model;
    
    private static Animal animals[];
    
    public Animal(String name, AnimalType animalType, Model animalModel)
    {
        this.displayName = name;
        this.age = AnimalAge.CHILD;
        this.type = animalType;
        this.model = animalModel;
        
        animals[animals.length] = this;
    }
}
