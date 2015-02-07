/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.entities;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Will
 */
public class AnimalRegistry 
{
    protected static Map<String, Animal> animals = new HashMap<String, Animal>();
    
    public static void addAnimal(Animal animal)
    {
        animals.put(animal.displayName, animal);
    }
    
    public static Animal getAnimal(String name)
    {
        return animals.get(name);
    }
    
    public static String[] getAnimalList()
    {
        return (String[]) animals.keySet().toArray();
    }
}
