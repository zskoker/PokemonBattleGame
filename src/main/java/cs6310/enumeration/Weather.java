package cs6310.enumeration;

public enum Weather
{
    HAIL("Hail"),
    RAINY("Rainy"),
    SANDY("Sandy"),
    SUNNY("Sunny"),
    WINDY("Windy");

    private final String label;

    Weather(String label) {this.label = label;}

    public String getLabel() {return label;}
}