package cs6310.Move;

import cs6310.enumeration.Element;
import cs6310.enumeration.Type;

public class Move
{
    private Type type;
    private String name;
    private double powerLevel;
    private Element element;
    private String effect;

    public Move(String name, double powerLevel, Type type, Element element, String effect)
    {
        this.name = name;
        this.powerLevel = powerLevel;
        this.type = type;
        this.element = element;
        this.effect = effect;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public double getPowerLevel() {return powerLevel;}
    public void setPowerLevel(double powerLevel) {this.powerLevel = powerLevel;}

    public Type getType() {return type;}
    public void setType(Type type) {this.type = type;}

    public Element getElement() {return element;}
    public void setElement(Element element) {this.element = element;}

    public String getEffect() {return effect;}
    public void setEffect(String effect) {this.effect = effect;}
}
