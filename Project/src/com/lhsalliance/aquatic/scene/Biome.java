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
    
    public Biome(String name, String menuNodePath)
    {
        this.displayName = name;
        this.previewNode = menuNodePath;
    }
}
