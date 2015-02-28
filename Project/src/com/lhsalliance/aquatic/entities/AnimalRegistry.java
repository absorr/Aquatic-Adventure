/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.entities;

import java.util.HashMap;
import java.util.Map;

/**
 * A registry that keeps track of all of the types of animals and keeps a
 * template of that animal
 * @author Will
 */
public class AnimalRegistry 
{
    /** Registry of all of the various animals by name and class **/
    protected static Map<String, Animal> animals = new HashMap<String, Animal>();
    
    /**
     * Adds a template animal into the registry
     * @param animal 
     */
    public static void addAnimal(Animal animal)
    {
        animals.put(animal.displayName, animal);
    }
    
    /**
     * Gets the template animal by name
     * @param name Display name of the animal
     * @return Template animal for use in copying only!
     */
    public static Animal getAnimal(String name)
    {
        return animals.get(name);
    }
    
    /**
     * Get a list of animals that are registered
     * @return List of all registered display names of animals
     */
    public static Object[] getAnimalList()
    {
        return animals.keySet().toArray();
    }
}
