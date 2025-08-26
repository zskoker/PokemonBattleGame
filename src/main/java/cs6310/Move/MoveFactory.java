package cs6310.Move;

import cs6310.enumeration.Element;
import cs6310.enumeration.Type;

import java.io.*;

import java.util.ArrayList;

public class MoveFactory
{
    private static ArrayList<Move> allMoves = new ArrayList<>();
    private static final String filePath = "src/main/java/cs6310/CSV_Data/Move.csv";

    public static ArrayList<Move> readMoves()
    {
        allMoves.clear();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null)
            {
                String[] parts = line.split(",");
                if (parts.length == 6)
                {
                    String name = parts[1];
                    double powerLevel = Double.parseDouble(parts[2]);
                    Type type = Type.valueOf(parts[3].toUpperCase());
                    Element element = Element.valueOf(parts[4].toUpperCase());
                    String effect = parts[5];

                    Move move = new Move(name, powerLevel, type, element, effect);
                    allMoves.add(move);
                }
            }
        }

        catch (IOException e) {System.err.println("Error reading the file: " + e.getMessage());}
        catch (IllegalArgumentException e) {System.err.println("Error parsing the file: Invalid Type or Element. " + e.getMessage());}

        return allMoves;
    }

    public void createMove(String moveName, double powerLevel, Type type, Element element, String effect)
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true)))
        {
            int newMoveId = allMoves.size() + 1;
            String newMoveLine = newMoveId + "," + moveName + "," + powerLevel + "," + type + "," + element + "," + effect;
            bw.write(newMoveLine);
            bw.newLine();
            allMoves = readMoves();
            System.out.println("Move added successfully: " + newMoveLine);
        }

        catch (IOException e) {System.err.println("Error writing to the file: " + e.getMessage());}
    }

    public MoveFactory() {}
}
