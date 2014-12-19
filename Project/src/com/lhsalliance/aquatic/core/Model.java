/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.core;
import com.jme3.scene.Spatial;

/**
 *
 * @author Will
 */
public class Model 
{
    public String pathModel;
    public String pathMaterial;
    public Spatial render;
    
    public Model(String pathToModel, String pathToMaterial)
    {
        this.pathModel = pathToModel;
        this.pathMaterial = pathToMaterial;
    }
    
    public void loadModel()
    {
        this.render = Main.game.loadModel(this);
    }
}
