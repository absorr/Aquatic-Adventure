/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.core;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;

/**
 *
 * @author Will
 */
public class HandlerInput 
{
    /**Use ActionListener to respond to pressed/released inputs (key presses, mouse clicks) */ 
    private ActionListener actionListener = new ActionListener(){
        public void onAction(String name, boolean pressed, float tpf){
            System.out.println(name + " = " + pressed);
        }
    };
    /** Use AnalogListener to respond to continuous inputs (key presses, mouse clicks) */
    private AnalogListener analogListener = new AnalogListener() {
       public void onAnalog(String name, float value, float tpf) {
           System.out.println(name + " = " + value);
       }
   };
}
