/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.core;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author Will
 */
public class InteractionHandler
{
  private Node shootables;
  private Geometry mark;
  private Main game;
  
  public InteractionHandler(Main main)
  {
      this.game = main;
      initKeys();
  }
 
  /** Declaring the "Shoot" action and mapping to its triggers. */
  private void initKeys() {
    game.getInputManager().addMapping("Shoot",
      new KeyTrigger(KeyInput.KEY_SPACE), // trigger 1: spacebar
      new MouseButtonTrigger(MouseInput.BUTTON_LEFT)); // trigger 2: left-button click
    game.getInputManager().addListener(analogListener, "Shoot");
  } /** Map one or more inputs to an action */

 private AnalogListener analogListener = new AnalogListener() {
    public void onAnalog(String name, float intensity, float tpf) {
      if (name.equals("Shoot")) {
        
        // Reset results list.
        CollisionResults results = new CollisionResults();
        
        if (results.getClosestCollision().getGeometry() instanceof IInteractable)
        {
            IInteractable obj = (IInteractable) results.getClosestCollision().getGeometry();
            obj.click();
        }
        /*// Convert screen click to 3d position
        Vector2f click2d = inputManager.getCursorPosition();
        Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
        Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
        // Aim the ray from the clicked spot forwards.
        Ray ray = new Ray(click3d, dir);
        // Collect intersections between ray and all nodes in results list.
        rootNode.collideWith(ray, results);
        // (Print the results so we see what is going on:)
        System.out.println(results.getClosestCollision().getGeometry() instanceof IInteractable);
        for (int i = 0; i < results.size(); i++) {
          // (For each “hit”, we know distance, impact point, geometry.)
          float dist = results.getCollision(i).getDistance();
          Vector3f pt = results.getCollision(i).getContactPoint();
          String target = results.getCollision(i).getGeometry().getName();
          System.out.println("Selection #" + i + ": " + target + " at " + pt + ", " + dist + " WU away.");
        }
        // Use the results -- we rotate the selected geometry.
        if (results.size() > 0) {
          // The closest result is the target that the player picked:
          Geometry target = results.getClosestCollision().getGeometry();
          // Here comes the action:
          if (target.getName().equals("Red Box")) {
            target.rotate(0, -intensity, 0);
          } else if (target.getName().equals("Blue Box")) {
            target.rotate(0, intensity, 0);
          }
        }*/
      } // else if ...
    }
  };
}
