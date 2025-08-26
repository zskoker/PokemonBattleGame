package cs6310.enumeration;

import java.util.ArrayList;
import java.util.HashMap;

public class WeatherChart 
{
    private final HashMap<Weather, ArrayList<Element>> strong = new HashMap<>();
    private final HashMap<Weather, ArrayList<Element>> weak = new HashMap<>();

    public WeatherChart()
    {
        mapStrong();
        mapWeak();
    }

    private void mapStrong()
    {
        ArrayList<Element> hail = new ArrayList<>();
        hail.add(Element.ELECTRIC);
        hail.add(Element.ICE);
        strong.put(Weather.HAIL, hail);

        ArrayList<Element> rainy = new ArrayList<>();
        rainy.add(Element.ELECTRIC);
        rainy.add(Element.GRASS);
        rainy.add(Element.WATER);
        strong.put(Weather.RAINY, rainy);

        ArrayList<Element> sandy = new ArrayList<>();
        sandy.add(Element.BUG);
        sandy.add(Element.GROUND);
        sandy.add(Element.ROCK);
        strong.put(Weather.SANDY, sandy);

        ArrayList<Element> sunny = new ArrayList<>();
        sunny.add(Element.DRAGON);
        sunny.add(Element.FIRE);
        strong.put(Weather.SUNNY, sunny);

        ArrayList<Element> windy = new ArrayList<>();
        windy.add(Element.DRAGON);
        windy.add(Element.FIRE);
        windy.add(Element.FLYING);
        strong.put(Weather.WINDY, windy);
    }

    private void mapWeak()
    {
        ArrayList<Element> hail = new ArrayList<>();
        hail.add(Element.DRAGON);
        hail.add(Element.FIRE);
        hail.add(Element.FLYING);
        weak.put(Weather.HAIL, hail);

        ArrayList<Element> rainy = new ArrayList<>();
        rainy.add(Element.BUG);
        rainy.add(Element.DRAGON);
        rainy.add(Element.FIRE);
        rainy.add(Element.FLYING);
        weak.put(Weather.RAINY, rainy);

        ArrayList<Element> sunny = new ArrayList<>();
        sunny.add(Element.DARK);
        sunny.add(Element.GRASS);
        sunny.add(Element.ICE);
        sunny.add(Element.WATER);
        weak.put(Weather.SUNNY, sunny);

        ArrayList<Element> windy = new ArrayList<>();
        windy.add(Element.BUG);
        weak.put(Weather.WINDY, windy);
    }

    public HashMap<Weather, ArrayList<Element>> getStrong() {return strong;}
    public HashMap<Weather, ArrayList<Element>> getWeak() {return weak;}   
}
