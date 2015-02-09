/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.scene;

/**
 *
 * @author Will
 */
public class Biome 
{
    public String displayName;
    public String previewNode;
    public String seed = "18a03ef6cddf91b"; //15 char hex string
    
    public Biome(String name, String menuNodePath)
    {
        this.displayName = name;
        this.previewNode = menuNodePath;
    }
    
    public void generateLand(float radius)
    {
        
    }
}
