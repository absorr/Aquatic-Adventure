/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.scene;

/**
 *
 * @author Will
 */
public class BiomeRegistry 
{
    public static Biome RIEF = new Biome("Rief", "");
    public static Biome RIVER = new Biome("River", "");
    public static Biome DEEPOCEAN = new Biome("Deep Ocean", "");
    
    public static Biome[] biomeList = {RIEF, RIVER, DEEPOCEAN};
}
