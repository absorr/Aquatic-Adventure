package com.lhsalliance.aquatic.core;

/**
 *
 * @author Will
 */
public interface IInteractable 
{
    String[] getInteractions();
    void interact(String type, IInteractable sender);

    public void click();
}
