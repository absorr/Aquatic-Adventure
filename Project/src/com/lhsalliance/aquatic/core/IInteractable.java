package com.lhsalliance.aquatic.core;

/**
 *
 * @author Will
 */
public interface IInteractable 
{
    String[] getInteractions();
    void click();
    void interact(String type, IInteractable sender);
}
