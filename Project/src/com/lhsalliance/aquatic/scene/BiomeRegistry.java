/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.scene;

/**
 * A registry of all of the official biomes
 * @author Will
 */
public class BiomeRegistry 
{
    //TODO: add their preview models
    public static Biome RIEF = new Biome("Rief", "");
    public static Biome RIVER = new Biome("River", "");
    public static Biome DEEPOCEAN = new Biome("Deep Ocean", "");
    
    /** Official list of biomes for use in iterating **/
    public static Biome[] biomeList = {RIEF, RIVER, DEEPOCEAN};
}
