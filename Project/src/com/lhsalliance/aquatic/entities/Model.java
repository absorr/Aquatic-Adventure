/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.entities;
import com.jme3.export.binary.BinaryImporter;
import com.jme3.scene.Node;
import com.lhsalliance.aquatic.core.Main;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Will
 */
public class Model 
{
    public String pathModel;
    public Node node;
    
    public Model(String pathToModel)
    {
        this.pathModel = pathToModel;
    }
    
    public void loadModel()
    {
        /** Load a Node from a .j3o file */
        BinaryImporter importer = BinaryImporter.getInstance();
        importer.setAssetManager(Main.game.getAssetManager());
        File file = new File(pathModel);
        try {
          node = (Node)importer.load(file);
          node.setName("loaded node");
          Main.game.getRootNode().attachChild(node);
        } catch (IOException ex) {
          Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "No saved node loaded.", ex);
          node = null;
        } 
    }
}
