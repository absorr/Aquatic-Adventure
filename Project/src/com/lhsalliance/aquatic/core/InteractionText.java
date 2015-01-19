/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.core;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import jme3test.helloworld.HelloAssets;

/**
 *
 * @author Will
 */
public class InteractionText extends BitmapText implements IInteractable {
    public HelloAssets game;
    public InteractionText (BitmapFont font, boolean bool, HelloAssets ass)
    {
        super(font, bool);
        this.game = ass;
    }

    @Override
    public String[] getInteractions() {
        String[] actions = {};
        return actions;
    }

    @Override
    public void interact(String type, IInteractable sender) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void click() {
        System.out.println("debuggery 1");
        if ("RETURN TO GAME".equals(this.getText()))
        {
           System.out.println("debuggery");
            game.stop();
        }
    }
}