package cs6310.enumeration;

import java.util.HashMap;
import java.util.ArrayList;

public class ElementChart 
{
    private final HashMap<Element, ArrayList<Element>> superEffective = new HashMap<>();
    private final HashMap<Element, ArrayList<Element>> notVeryEffective = new HashMap<>();
    private final HashMap<Element, ArrayList<Element>> immune = new HashMap<>();

    public ElementChart()
    {
        mapSuperEffective();
        mapNotVeryEffective();
        mapImmune();
    }

    private void mapSuperEffective()
    {
        ArrayList<Element> bug = new ArrayList<>();
        bug.add(Element.DARK);
        bug.add(Element.GRASS);
        bug.add(Element.PSYCHIC);
        superEffective.put(Element.BUG, bug);

        ArrayList<Element> dark = new ArrayList<>();
        dark.add(Element.GHOST);
        dark.add(Element.PSYCHIC);
        superEffective.put(Element.DARK, dark);

        ArrayList<Element> dragon = new ArrayList<>();
        dragon.add(Element.DRAGON);
        superEffective.put(Element.DRAGON, dragon);

        ArrayList<Element> electric = new ArrayList<>();
        electric.add(Element.FLYING);
        electric.add(Element.WATER);
        superEffective.put(Element.ELECTRIC, electric);

        ArrayList<Element> fairy = new ArrayList<>();
        fairy.add(Element.DARK);
        fairy.add(Element.DRAGON);
        fairy.add(Element.FIGHTING);
        superEffective.put(Element.FAIRY, fairy);

        ArrayList<Element> fighting = new ArrayList<>();
        fighting.add(Element.DARK);
        fighting.add(Element.ICE);
        fighting.add(Element.NORMAL);
        fighting.add(Element.ROCK);
        fighting.add(Element.STEEL);
        superEffective.put(Element.FIGHTING, fighting);

        ArrayList<Element> fire = new ArrayList<>();
        fire.add(Element.BUG);
        fire.add(Element.GRASS);
        fire.add(Element.ICE);
        fire.add(Element.STEEL);
        superEffective.put(Element.FIRE, fire);

        ArrayList<Element> flying = new ArrayList<>();
        flying.add(Element.BUG);
        flying.add(Element.FIGHTING);
        flying.add(Element.GRASS);
        superEffective.put(Element.FLYING, flying);

        ArrayList<Element> ghost = new ArrayList<>();
        ghost.add(Element.GHOST);
        ghost.add(Element.PSYCHIC);
        superEffective.put(Element.GHOST, ghost);

        ArrayList<Element> grass = new ArrayList<>();
        grass.add(Element.GROUND);
        grass.add(Element.ROCK);
        grass.add(Element.WATER);
        superEffective.put(Element.GRASS, grass);

        ArrayList<Element> ground = new ArrayList<>();
        ground.add(Element.ELECTRIC);
        ground.add(Element.FIRE);
        ground.add(Element.ROCK);
        ground.add(Element.STEEL);
        superEffective.put(Element.GROUND, ground);

        ArrayList<Element> ice = new ArrayList<>();
        ice.add(Element.DRAGON);
        ice.add(Element.GRASS);
        ice.add(Element.GROUND);
        ice.add(Element.FLYING);
        superEffective.put(Element.ICE, ice);

        ArrayList<Element> poison = new ArrayList<>();
        poison.add(Element.FAIRY);
        poison.add(Element.GRASS);
        superEffective.put(Element.POISON, poison);

        ArrayList<Element> psychic = new ArrayList<>();
        psychic.add(Element.FIGHTING);
        psychic.add(Element.POISON);
        superEffective.put(Element.PSYCHIC, psychic);

        ArrayList<Element> rock = new ArrayList<>();
        rock.add(Element.BUG);
        rock.add(Element.FIRE);
        rock.add(Element.FLYING);
        rock.add(Element.ICE);
        superEffective.put(Element.ROCK, rock);

        ArrayList<Element> steel = new ArrayList<>();
        steel.add(Element.FAIRY);
        steel.add(Element.ICE);
        steel.add(Element.ROCK);
        superEffective.put(Element.STEEL, steel);

        ArrayList<Element> water = new ArrayList<>();
        water.add(Element.FIRE);
        water.add(Element.GROUND);
        water.add(Element.ROCK);
        superEffective.put(Element.WATER, water);
    }

    private void mapNotVeryEffective()
    {
        ArrayList<Element> bug = new ArrayList<>();
        bug.add(Element.FIGHTING);
        bug.add(Element.GRASS);
        bug.add(Element.GROUND);
        notVeryEffective.put(Element.BUG, bug);

        ArrayList<Element> dark = new ArrayList<>();
        dark.add(Element.DARK);
        dark.add(Element.GHOST);
        notVeryEffective.put(Element.DARK, dark);

        ArrayList<Element> dragon = new ArrayList<>();
        dragon.add(Element.ELECTRIC);
        dragon.add(Element.FIRE);
        dragon.add(Element.GRASS);
        dragon.add(Element.WATER);
        notVeryEffective.put(Element.DRAGON, dragon);

        ArrayList<Element> electric = new ArrayList<>();
        electric.add(Element.ELECTRIC);
        electric.add(Element.FLYING);
        electric.add(Element.STEEL);
        notVeryEffective.put(Element.ELECTRIC, electric);

        ArrayList<Element> fairy = new ArrayList<>();
        fairy.add(Element.BUG);
        fairy.add(Element.DARK);
        fairy.add(Element.FIGHTING);
        notVeryEffective.put(Element.FAIRY, fairy);

        ArrayList<Element> fighting = new ArrayList<>();
        fighting.add(Element.BUG);
        fighting.add(Element.DARK);
        fighting.add(Element.ROCK);
        notVeryEffective.put(Element.FIGHTING, fighting);

        ArrayList<Element> fire = new ArrayList<>();
        fire.add(Element.BUG);
        fire.add(Element.FAIRY);
        fire.add(Element.FIRE);
        fire.add(Element.GRASS);
        fire.add(Element.ICE);
        fire.add(Element.STEEL);
        notVeryEffective.put(Element.FIRE, fire);

        ArrayList<Element> flying = new ArrayList<>();
        flying.add(Element.BUG);
        flying.add(Element.FIGHTING);
        flying.add(Element.GRASS);
        notVeryEffective.put(Element.FLYING, flying);

        ArrayList<Element> ghost = new ArrayList<>();
        ghost.add(Element.BUG);
        notVeryEffective.put(Element.GHOST, ghost);

        ArrayList<Element> grass = new ArrayList<>();
        grass.add(Element.GRASS);
        grass.add(Element.GROUND);
        grass.add(Element.ELECTRIC);
        grass.add(Element.WATER);
        notVeryEffective.put(Element.GRASS, grass);

        ArrayList<Element> ground = new ArrayList<>();
        ground.add(Element.POISON);
        ground.add(Element.ROCK);
        notVeryEffective.put(Element.GROUND, ground);

        ArrayList<Element> ice = new ArrayList<>();
        ice.add(Element.ICE);
        notVeryEffective.put(Element.ICE, ice);

        ArrayList<Element> normal = new ArrayList<>();
        normal.add(Element.ROCK);
        normal.add(Element.STEEL);
        notVeryEffective.put(Element.NORMAL, normal);

        ArrayList<Element> poison = new ArrayList<>();
        poison.add(Element.BUG);
        poison.add(Element.FAIRY);
        poison.add(Element.FIGHTING);
        poison.add(Element.GRASS);
        poison.add(Element.POISON);
        notVeryEffective.put(Element.POISON, poison);

        ArrayList<Element> psychic = new ArrayList<>();
        psychic.add(Element.FIGHTING);
        psychic.add(Element.PSYCHIC);
        notVeryEffective.put(Element.PSYCHIC, psychic);

        ArrayList<Element> rock = new ArrayList<>();
        rock.add(Element.FIRE);
        rock.add(Element.FLYING);
        rock.add(Element.NORMAL);
        rock.add(Element.POISON);
        notVeryEffective.put(Element.ROCK, rock);

        ArrayList<Element> steel = new ArrayList<>();
        steel.add(Element.BUG);
        steel.add(Element.DRAGON);
        steel.add(Element.FLYING);
        steel.add(Element.GRASS);
        steel.add(Element.ICE);
        steel.add(Element.NORMAL);
        steel.add(Element.PSYCHIC);
        steel.add(Element.ROCK);
        steel.add(Element.STEEL);
        notVeryEffective.put(Element.STEEL, steel);

        ArrayList<Element> water = new ArrayList<>();
        water.add(Element.FIRE);
        water.add(Element.ICE);
        water.add(Element.STEEL);
        water.add(Element.WATER);
        notVeryEffective.put(Element.WATER, water);
    }

    private void mapImmune()
    {
        ArrayList<Element> dark = new ArrayList<>();
        dark.add(Element.PSYCHIC);
        immune.put(Element.DARK, dark);

        ArrayList<Element> fairy = new ArrayList<>();
        fairy.add(Element.DRAGON);
        immune.put(Element.FAIRY, fairy);

        ArrayList<Element> flying = new ArrayList<>();
        flying.add(Element.GROUND);
        immune.put(Element.FLYING, flying);

        ArrayList<Element> ghost = new ArrayList<>();
        ghost.add(Element.FIGHTING);
        ghost.add(Element.NORMAL);
        immune.put(Element.GHOST, ghost);

        ArrayList<Element> ground = new ArrayList<>();
        ground.add(Element.ELECTRIC);
        immune.put(Element.GROUND, ground);

        ArrayList<Element> normal = new ArrayList<>();
        normal.add(Element.GHOST);
        immune.put(Element.NORMAL, normal);

        ArrayList<Element> steel = new ArrayList<>();
        steel.add(Element.POISON);
        immune.put(Element.STEEL, steel);
    }

    public HashMap<Element, ArrayList<Element>> getSuperEffective() {return superEffective;}
    public HashMap<Element, ArrayList<Element>> getNotVeryEffective() {return notVeryEffective;}
    public HashMap<Element, ArrayList<Element>> getImmune() {return immune;}    
}
