package cs6310;

//import cs6310.Exceptions.BattleLostException;
import cs6310.Pokemon.Pokemon;
import cs6310.Pokemon.PokemonFactory;
import cs6310.battle.Battle;
import cs6310.battle.Tournament;

/*import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class CommandProcessor 
{

    private static Integer _seed = null;

    public CommandProcessor() {}
    public CommandProcessor(Integer seed) {_seed = seed;}

    public ArrayList<String> bulbaAttacks = new ArrayList<>();
    public ArrayList<String> bulbaDefenses = new ArrayList<>();
    public ArrayList<String> bulbaElement = new ArrayList<>();

    public ArrayList<String> hippoAttacks = new ArrayList<>();
    public ArrayList<String> hippoDefenses = new ArrayList<>();
    public ArrayList<String> hippoElement = new ArrayList<>();

    private boolean isPowerOfTwo(int n)
    {
        return (int)(Math.ceil((Math.log(n) / Math.log(2)))) 
            == (int)(Math.floor(((Math.log(n) / Math.log(2)))));
    }

    public void ProcessCommands(String[] args) 
    {
        PokemonFactory factory = new PokemonFactory();
        HashMap<String, Pokemon> pokemonMap = factory.getPokemonMap();

        bulbaElement.add("Grass");
        bulbaElement.add("Poison");
        bulbaAttacks.add("Vine Whip");
        bulbaDefenses.add("Protect");

        hippoElement.add("Ground");
        hippoAttacks.add("Earth Quake");
        hippoDefenses.add("Protect");

        factory.createPokemon("Bulbasaur", bulbaElement, 10.0, bulbaAttacks, bulbaDefenses);
        factory.createPokemon("Hippowdon", hippoElement, 10.0, hippoAttacks, hippoDefenses);

        var commandLineInput = new Scanner(System.in);
        var delimiter = ",";

        while (true) 
        {
            try 
            {
                var wholeInputLine = commandLineInput.nextLine();
                var tokens = wholeInputLine.split(delimiter);

                System.out.println("> " + wholeInputLine);

                if (tokens[0].startsWith("//")) 
                {
                    continue;
                }
                
                else if (tokens[0].equals("setseed")) 
                {
                    if (tokens.length < 2)
                    {
                        System.out.println("You must enter a seed.");
                        continue;
                    }
                    
                    _seed = Integer.valueOf(tokens[1]);
                } 
                
                else if (tokens[0].equals("removeseed")) 
                {
                    _seed = null;
                } 
                
                else if (tokens[0].equals("battle")) 
                {
                    if (_seed == null)
                    {
                        System.out.println("You must set a seed to battle.");
                        continue;
                    }

                    if (tokens.length < 3)
                    {
                        System.out.println("You must enter two Pokemon to battle.");
                        continue;
                    }

                    Pokemon pokemonA = pokemonMap.get(tokens[1]);
                    pokemonA.setSeedValue(_seed);

                    Pokemon pokemonB = pokemonMap.get(tokens[2]);
                    pokemonB.setSeedValue(_seed + 1);

                    Battle battle = new Battle(pokemonA, pokemonB, _seed, pokemonMap);
                    Pokemon winner = battle.encounter();

                    if (winner == null) {System.out.println("No contest.");}
                } 
                
                else if (tokens[0].equals("tournament")) 
                {
                    if (_seed == null)
                    {
                        System.out.println("You must set a seed to hold a tournament.");
                        continue;
                    }

                    if (!isPowerOfTwo(tokens.length - 1) || tokens.length - 1 < 4)
                    {
                        System.out.println("You must enter a number of Pokemon that is a power of 2 and greater than 3 to hold a tournament.");
                        continue;
                    }

                    ArrayList<String> tourneyList = new ArrayList<>();
                    Collections.addAll(tourneyList, Arrays.copyOfRange(tokens, 1, tokens.length));
                    Tournament tournament = new Tournament(tourneyList, _seed, pokemonMap);
                    Pokemon winner = tournament.tourneyEncounter();
                    if (winner == null) 
                    {
                        System.out.println("No contest.");
                    }
                } 
                
                else if (tokens[0].equals("displayinfo")) 
                { 
                    String info = pokemonMap.get(tokens[1]).display_info();
                    System.out.println(info);
                }

                else if (tokens[0].equals("stop")) 
                {
                    System.out.println("stop acknowledged");
                    break;
                }

                else 
                {
                    System.out.println("command unknown");
                    break;
                }
            } 
            
            catch (Exception e) 
            {
                e.printStackTrace();
                System.out.println();
            }
        }

        System.out.println("simulation terminated");
        commandLineInput.close();
    }
}
