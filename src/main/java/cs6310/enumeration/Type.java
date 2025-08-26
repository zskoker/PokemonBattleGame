package cs6310.enumeration;

public enum Type 
{
    ATTACK("attack"),
    DEFENSE("defense");

    private final String label;

    Type(String label) {this.label = label;}

    public String getLabel() {return label;}
}
