package cs6310.Pokemon;

import java.lang.reflect.InvocationTargetException;
import cs6310.Exceptions.BattleLostException;

public interface IPokemon 
{
    void battle(Object pokemon, double damage) throws ClassNotFoundException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException, BattleLostException;

    String display_info();
}