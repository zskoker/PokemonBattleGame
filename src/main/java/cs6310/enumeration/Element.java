package cs6310.enumeration;

public enum Element 
{
    BUG("Bug"),
    DARK("Dark"),
    DRAGON("Dragon"),
    ELECTRIC("Electric"),
    FAIRY("Fairy"),
    FIGHTING("Fighting"),
    FIRE("Fire"),
    FLYING("Flying"),
    GHOST("Ghost"),
    GRASS("Grass"),
    GROUND("Ground"),
    ICE("Ice"),
    NORMAL("Normal"),
    POISON("Poison"),
    PSYCHIC("Psychic"),
    ROCK("Rock"),
    STEEL("Steel"),
    WATER("Water");

    private final String label;

    Element(String label) {this.label = label;}

    public String getLabel() {return label;}
}
