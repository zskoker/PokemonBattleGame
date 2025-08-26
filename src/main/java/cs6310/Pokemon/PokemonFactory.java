package cs6310.Pokemon;

import cs6310.Move.Move;
import cs6310.Move.MoveFactory;
import cs6310.enumeration.Element;

import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PokemonFactory
{
    private HashMap<String, Pokemon> pokemonMap = new HashMap<>();
    private static final String filePath = "src/main/java/cs6310/CSV_Data/Pokemon.csv";

    private ArrayList<Move> parseMoveList(String moveListStr, List<Move> allMoves)
    {
        ArrayList<Move> moves = new ArrayList<>();
        moveListStr = moveListStr.replace("[", "").replace("]", "").replace("'", "").replace("\"", "").trim();
        String[] moveNames = moveListStr.split(",");
        for (String moveName : moveNames)
        {
            for (Move move : allMoves)
            {
                if (move.getName().equalsIgnoreCase(moveName))
                {
                    moves.add(move);
                    break;
                }
            }
        }

        return moves;
    }

    private ArrayList<Element> parseElementList(String elementListStr)
    {
        ArrayList<Element> elements = new ArrayList<>();
        elementListStr = elementListStr
                        .replace("[", "")
                        .replace("]", "")
                        .replace("'", "")
                        .replace("\"", "")
                        .trim();
        String[] elementNames = elementListStr.split(",");
        for (String elementName : elementNames)
        {
            String element = elementName.replaceAll("\"", "").toUpperCase();
            elements.add(Element.valueOf(element));
        }

        return elements;
    }

    private void populateMap()
    {
        ArrayList<Move> allMoves = MoveFactory.readMoves();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null)
            {
                line = line.replace("\"", "");
                String[] parts = line.split(", ");
                if (!pokemonMap.containsKey(parts[0]))
                {
                    String name = parts[0];
                    ArrayList<Element> elements = parseElementList(parts[1]);
                    double hitPoints = Double.parseDouble(parts[2]);
                    ArrayList<Move> attackMoves = parseMoveList(parts[3], allMoves);
                    ArrayList<Move> defenseMoves = parseMoveList(parts[4], allMoves);

                    Pokemon pokemon = new Pokemon(name, hitPoints, elements, attackMoves, defenseMoves);
                    pokemonMap.put(name, pokemon);
                }

                else {System.out.println("This pokemon already exists.");}
            }
        }

        catch (IOException e) {System.out.println("File not found.");}
    }

    public void createPokemon(String name,
                              ArrayList<String> elements,
                              double maxHitPoints,
                              ArrayList<String> attackMoves,
                              ArrayList<String> defenseMoves)
    {
        if (!pokemonMap.containsKey(name))
        {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true)))
            {
                String elementListStr = elements.toString();
                String attackListStr = attackMoves.toString();
                String defenseListStr = defenseMoves.toString();

                String newPokemonLine = name + ", " +
                        elementListStr + ", " +
                        maxHitPoints + ", " +
                        attackListStr + ", " +
                        defenseListStr + ", " +
                        "1, " +
                        "0, " +
                        "0";
                bw.write(newPokemonLine);
                bw.newLine();
                populateMap();
            }

            catch (IOException e) {System.err.println("Error writing to the file: " + e.getMessage());}
        }
    }

    public PokemonFactory() {populateMap();}
    public HashMap<String, Pokemon> getPokemonMap() {return pokemonMap;}
}
