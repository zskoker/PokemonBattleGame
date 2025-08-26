package cs6310.battle;

import java.util.ArrayList;
import java.util.HashMap;
import cs6310.Pokemon.Pokemon;

public class Tournament 
{
    private ArrayList<String> tourneyList;
    private Integer seed;
    private final HashMap<String, Pokemon> pokemonMap;
    private double rounds;

    // Method to split an ArrayList into smaller ArrayLists
    private <T> ArrayList<ArrayList<T>> splitArrayList(ArrayList<T> original, int size) 
    {
        ArrayList<ArrayList<T>> subLists = new ArrayList<>();

        for (int i = 0; i < original.size(); i += size) 
        {
            int end = Math.min(i + size, original.size());
            subLists.add(new ArrayList<>(original.subList(i, end)));
        }

        return subLists;
    }
    
    public Tournament(ArrayList<String> tourneyList, Integer seed, HashMap<String, Pokemon> pokemonMap)
    {
        this.tourneyList = tourneyList;
        this.seed = seed;
        this.pokemonMap = pokemonMap;
        rounds = Math.log(tourneyList.size()) / Math.log(2);
    }

    public ArrayList<String> getTourneyList() {return tourneyList;}
    public void setTourneyList(ArrayList<String> tourneyList) {this.tourneyList = tourneyList;}

    public Integer getSeed() {return seed;}
    public void setSeed(Integer seed) {this.seed = seed;}

    public Pokemon tourneyEncounter()
    {
        for (String s : tourneyList)
        {
            if (!pokemonMap.containsKey(s))
            {
                System.out.println("Invalid roster.");
                return null;
            }
        }

        ArrayList<ArrayList<String>> tourneyBrackets = splitArrayList(tourneyList, 2); 
        ArrayList<String> temp = new ArrayList<>();
        
        while (rounds > 0)
        {
            for (ArrayList<String> tourneyBracket : tourneyBrackets)
            {
                Pokemon pokemon1 = pokemonMap.get(tourneyBracket.get(0));
                pokemon1.setSeedValue(seed);

                Pokemon pokemon2 = pokemonMap.get(tourneyBracket.get(1));
                pokemon2.setSeedValue(seed + 1);

                Battle battle = new Battle(pokemon1, pokemon2, seed, pokemonMap);

                String winner = battle.encounter().getName();
                temp.add(winner);
            }

            if (tourneyBrackets.size() > 1)
            {
                tourneyBrackets = splitArrayList(temp, 2);
                temp.clear();
            }

            rounds--;
        }

        Pokemon winner = pokemonMap.get(temp.getFirst());
        System.out.printf("%s has won the tournament!%n", winner.getName());
        winner.setTournamentWins(winner.getTournamentWins() + 1);
        winner.evolve();
        return winner;
    }
}
