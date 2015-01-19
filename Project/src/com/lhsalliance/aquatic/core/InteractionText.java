/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.core;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;

/**
 *
 * @author Will
 */
public class InteractionText extends BitmapText implements IInteractable {
    public InteractionText (BitmapFont font, boolean bool)
    {
        super(font, bool);
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
        if (this.getText() == "Hello World")
        {
            
        }
    }
}
