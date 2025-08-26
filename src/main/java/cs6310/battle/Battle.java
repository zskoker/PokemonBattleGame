package cs6310.battle;

import cs6310.Move.Move;
import cs6310.Pokemon.Pokemon;
import cs6310.enumeration.Type;
import cs6310.enumeration.Weather;
import cs6310.enumeration.WeatherChart;
import cs6310.enumeration.Element;
import cs6310.enumeration.ElementChart;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;

public class Battle 
{
    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private Integer seed;
    private HashMap<String, Pokemon> pokemonMap;
    private Weather weather;

    private final ElementChart elementChart = new ElementChart();
    private final HashMap<Element, ArrayList<Element>> superEffective = elementChart.getSuperEffective();
    private final HashMap<Element, ArrayList<Element>> notVeryEffective = elementChart.getNotVeryEffective();
    private final HashMap<Element, ArrayList<Element>> immune = elementChart.getImmune();

    private final WeatherChart weatherChart = new WeatherChart();
    private final HashMap<Weather, ArrayList<Element>> strong = weatherChart.getStrong();
    private final HashMap<Weather, ArrayList<Element>> weak = weatherChart.getWeak();

    public Battle(Pokemon pokemon1, Pokemon pokemon2, Integer seed, HashMap<String, Pokemon> pokemonMap)
    {
        this.pokemon1 = pokemon1;
        this.pokemon2 = pokemon2;
        this.seed = seed;
        this.pokemonMap = pokemonMap;
        weatherGenerator();
    }

    public Pokemon getPokemonOne() {return pokemon1;}
    public void setPokemonOne(Pokemon pokemon1) {this.pokemon1 = pokemon1;}

    public Pokemon getPokemonTwo() {return pokemon2;}
    public void setPokemonTwo(Pokemon pokemon2) {this.pokemon2 = pokemon2;}

    public Integer getSeed() {return seed;}
    public void setSeed(Integer seed) {this.seed = seed;}

    public HashMap<String, Pokemon> getPokemonMap() {return pokemonMap;}
    public void setPokemonMap(HashMap<String, Pokemon> pokemonMap) {this.pokemonMap = pokemonMap;}

    public Weather getWeather() {return weather;}
    public void setWeather(Weather weather) {this.weather = weather;}

    private void weatherGenerator()
    {
        Random rand = new Random();
        int weatherIndex = rand.nextInt(5);

        if (weatherIndex == 0) {this.weather = Weather.HAIL;}
        else if (weatherIndex == 1) {this.weather = Weather.RAINY;}
        else if (weatherIndex == 2) {this.weather = Weather.SANDY;}
        else if (weatherIndex == 3) {this.weather = Weather.SUNNY;}
        else {this.weather = Weather.WINDY;}
    }

    public double elementCalc(Move move, Pokemon pokemon)
    {
        Element moveElement = move.getElement();
        ArrayList<Element> pokemonElement = pokemon.getElement();
        double modifier = 1.0;

        for (Element element: pokemonElement)
        {
            if (immune.containsKey(element))
            {
                ArrayList<Element> immunes = immune.get(element);
                if (immunes.contains(moveElement)) {modifier *= 0.0;}
            }
            
            ArrayList<Element> notVeryEffectives = notVeryEffective.get(moveElement);
            if (notVeryEffectives.contains(element)) {modifier *= 0.5;}

            if (superEffective.containsKey(moveElement))
            {
                ArrayList<Element> superEffectives = superEffective.get(moveElement);
                if (superEffectives.contains(element)) {modifier *= 2.0;}
            }
        }

        return modifier;
    }

    public double weatherCalc(Move move, Weather weather)
    {
        double modifier = 1.0;
        if (strong.get(weather).contains(move.getElement())) {modifier = 1.5;}

        else 
        {
            if (weak.get(weather) == null) {modifier *= 1.0;}
            if (weak.get(weather).contains(move.getElement())) {modifier = 0.5;}
        }

        return modifier;
    }

    public double weatherCalc(Pokemon pokemon, Weather weather)
    {
        double modifier = 1.0;
        for (Element element: pokemon.getElement())
        {
            if (strong.get(weather).contains(element)) {modifier *= 1.5;}
            else
            {
                if (weak.get(weather) == null) {modifier *= 1.0;}
                if (weak.get(weather).contains(element)) {modifier *= 0.5;}
            }
        }

        return modifier;
    }

    public void turn(Pokemon pokemon1, Pokemon pokemon2, Weather weather)
    {
        Move move = pokemon1.selectMove(pokemon1.determineMoveType());

        // modifiers
        double sameElementBonus = 1.0;
        if (pokemon1.getElement().contains(move.getElement())) {sameElementBonus *= 1.5;}

        double weatherMoveModifier = weatherCalc(move, weather);

        if (move.getType() == Type.DEFENSE) 
        {
            if (move.getEffect().equals("heal"))
            {
                pokemon1.setCurrentHitPoints(pokemon1.getCurrentHitPoints() + 4);
                System.out.printf("%s heals itself for 4.0 HP. It is now at %.2f HP.%n", pokemon1.getName(), pokemon1.getCurrentHitPoints());
            }

            else 
            {
                System.out.printf("%s is attempting to defend with %s.%n", pokemon1.getName(), move.getName());
                pokemon1.setDefensePower(move.getPowerLevel() * sameElementBonus * weatherMoveModifier);
            }
        }

        else 
        {
            try 
            {
                if (pokemon1.getTransformed())
                {
                    move.setName("Attack");
                    move.setPowerLevel(pokemon1.getLastAttackPower());
                    move.setEffect("standard");
                }

                String message = "";
                double elementModifier = elementCalc(move, pokemon2);
                if (elementModifier == 0.0) {message = String.format("It doesn't affect %s...",pokemon2.getName());}
                else if (elementModifier <= 0.5) {message = "It's not very effective...";}
                else if (elementModifier >= 2.0) {message = "It's super effective!";}

                if (!message.isEmpty()) {System.out.println(message);}
                System.out.printf("%s is attacking with %s for %.2f damage to %s.%n",
                                                        pokemon1.getName(),
                                                        move.getName(),
                                                        move.getPowerLevel() * sameElementBonus * elementModifier * weatherMoveModifier,
                                                        pokemon2.getName());

                if (move.getEffect().equals("dot")) {pokemon2.setPoisoned(true);}
                if (move.getEffect().equals("transform")) {pokemon1.setTransformed(true);}

                pokemon1.setDefensePower(0);
                pokemon2.battle(this, move.getPowerLevel() * sameElementBonus * elementModifier * weatherMoveModifier);
            }

            catch (Exception e) {System.out.println(e.getMessage());}
        }
    }

    public Pokemon encounter()
    {
        if (!pokemonMap.containsKey(pokemon1.getName()) || !pokemonMap.containsKey(pokemon2.getName()))
        {
            System.out.println("There is an invalid Pokemon.");
            return null;
        } 

        Pokemon winner;

        System.out.printf("It is %s this battle.%n", weather.getLabel());

        double weatherModifier1 = weatherCalc(pokemon1, weather);
        double weatherModifier2 = weatherCalc(pokemon2, weather);

        pokemon1.setCurrentHitPoints(pokemon1.getCurrentHitPoints() * weatherModifier1);
        pokemon2.setCurrentHitPoints(pokemon2.getCurrentHitPoints() * weatherModifier2);

        while (pokemon1.getCurrentHitPoints() > 0 && pokemon2.getCurrentHitPoints() > 0)
        {
            if (pokemon1.getSeedValue() < pokemon2.getSeedValue())
            {
                turn(pokemon1, pokemon2, weather);
                pokemon1.setSeedValue(seed + 1);
                pokemon2.setSeedValue(seed);
            }

            else
            {
                turn(pokemon2, pokemon1, weather);
                pokemon2.setSeedValue(seed + 1);
                pokemon1.setSeedValue(seed);
            }
        }

        if (pokemon1.getCurrentHitPoints() > 0)
        {
            pokemon1.setBattleWins(pokemon1.getBattleWins() + 1);
            if (pokemon1.getBattleWins() % 5 == 0) {pokemon1.evolve();}
            winner = pokemon1;
        }
        
        else
        {
            pokemon2.setBattleWins(pokemon2.getBattleWins() + 1);
            if (pokemon2.getBattleWins() % 5 == 0) {pokemon2.evolve();}
            winner = pokemon2;
        }

        System.out.printf("%s has won the battle!\n%n", winner.getName());

        pokemon1.setCurrentHitPoints(pokemon1.getMaxHitPoints());
        pokemon2.setCurrentHitPoints(pokemon2.getMaxHitPoints());

        return winner;
    }
}
