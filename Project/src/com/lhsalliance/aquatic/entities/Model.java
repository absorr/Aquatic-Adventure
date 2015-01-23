/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.entities;
import com.jme3.scene.Spatial;
import com.lhsalliance.aquatic.core.Main;

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
