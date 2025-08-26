package cs6310.Pokemon;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

import cs6310.Exceptions.BattleLostException;
import cs6310.Move.Move;
import cs6310.Move.MoveFactory;
import cs6310.enumeration.Element;
import cs6310.enumeration.Type;

public class Pokemon implements IPokemon
{
    private String name;
    private ArrayList<Element> element;
    private double maxHitPoints;
    private ArrayList<Move> attackMoves;
    private ArrayList<Move> defenseMoves;
    private Integer seedValue;
    private double currentHitPoints;
    private double defensePower = 0;
    private double lastAttackPower = 0;
    private int level = 1;
    private int battleWins = 0;
    private int tournamentWins = 0;
    private boolean poisoned = false;
    private boolean transformed = false;
    
    Random rand = new Random();

    public Pokemon(String name,
                   double maxHitPoints,
                   ArrayList<Element> element,
                   ArrayList<Move> attacks,
                   ArrayList<Move> defenses)
    {
        this.name = name;
        this.maxHitPoints = maxHitPoints;
        this.element = element;
        attackMoves = attacks;
        defenseMoves = defenses;
        currentHitPoints = this.maxHitPoints;
    }

    public Pokemon(Integer seed) {seedValue = seed;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public double getMaxHitPoints() {return maxHitPoints;}
    public void setMaxHitPoints(int maxHitPoints) {this.maxHitPoints = maxHitPoints;}

    public ArrayList<Move> getAttackMoves() {return attackMoves;}
    public void setAttackMoves(ArrayList<Move> attackMoves) {this.attackMoves = attackMoves;}

    public ArrayList<Move> getDefenseMoves() {return defenseMoves;}
    public void setDefenseMoves(ArrayList<Move> defenseMoves) {this.defenseMoves = defenseMoves;}

    public Integer getSeedValue() {return seedValue;}
    public void setSeedValue(Integer seedValue) {this.seedValue = seedValue;}
    public void removeSeed() {seedValue = null;}

    public double getCurrentHitPoints() {return currentHitPoints;}
    public void setCurrentHitPoints(double currentHitPoints) {this.currentHitPoints = currentHitPoints;}

    public double getDefensePower() {return defensePower;}
    public void setDefensePower(double defensePower) {this.defensePower = defensePower;}

    public int getLevel() {return level;}
    public void setLevel(int level) {this.level = level;}

    public int getBattleWins() {return battleWins;}
    public void setBattleWins(int battleWins) {this.battleWins = battleWins;}

    public int getTournamentWins() {return tournamentWins;}
    public void setTournamentWins(int tournamentWins) {this.tournamentWins = tournamentWins;}

    public ArrayList<Element> getElement() {return element;}
    public void setElement(ArrayList<Element> element) {this.element = element;}

    public boolean getPoisoned() {return poisoned;}
    public void setPoisoned(boolean poisoned) {this.poisoned = poisoned;}

    public boolean getTransformed() {return transformed;}
    public void setTransformed(boolean transformed) {this.transformed = transformed;}

    public double getLastAttackPower() {return lastAttackPower;}
    public void setLastAttackPower(double lastAttackPower) {this.lastAttackPower = lastAttackPower;}

    public void battle(Object callingClass, double damage)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException, BattleLostException
    {
        try {receiveDamage(damage, defensePower);}        
        catch(Exception e) {System.out.println(name + " has no more HP left.\n");}
    }

    public ArrayList<Move> determineMoveType()
    {
        double healthRatio = currentHitPoints / maxHitPoints;
        // generates int between 0 and 9
        int moveChance = rand.nextInt(10);
        if (healthRatio >= 0.7)
        {
            // 80% chance to attack
            if (moveChance < 2) {return defenseMoves;}
            return attackMoves;
        }

        else if (healthRatio >= 0.3)
        {
            // 50% chance to attack
            if (moveChance < 5) {return defenseMoves;}
            return attackMoves;
        }

        else
        {
            // 30% chance to attack
            if (moveChance < 7) {return defenseMoves;}
            return attackMoves;
        }
    }

    public void receiveDamage(double damage, double defensePower)
    {
        double poison = 0.0;

        if (defensePower > 0) {System.out.printf("%s successfully reduced the incoming damage by %.2f.%n", name, defensePower);}
        if (poisoned) {poison = 2.0;}
        
        double netDamage = Math.max(damage + poison - defensePower, 0);
        lastAttackPower = netDamage;
        setCurrentHitPoints(Math.max(currentHitPoints - netDamage, 0.0));

        String message = String.format("%s has received %.2f direct damage and %.2f damage over time, and now has %.2f HP remaining.", 
        name, netDamage, poison, currentHitPoints);
        System.out.println(message);

        if (currentHitPoints == 0) {System.out.printf("%s has lost.%n", name);}
    }

    public Move selectMove(ArrayList<Move> moves)
    {
        int select = rand.nextInt(moves.size());
        return moves.get(select);
    }

    public void evolve()
    {
        ArrayList<Move> allMoves = MoveFactory.readMoves();
        maxHitPoints *= 1.5;
        level++;

        for (Move attack: attackMoves) {attack.setPowerLevel(attack.getPowerLevel() * 1.5);}
        for (Move defense: defenseMoves) {defense.setPowerLevel(defense.getPowerLevel() * 1.5);}

        while (true)
        {
            int newMoveIndex = rand.nextInt(allMoves.size());
            Move move = allMoves.get(newMoveIndex);
            if (move.getType() == Type.DEFENSE)
            {
                if (!defenseMoves.contains(move))
                {
                    defenseMoves.add(move);
                    break;
                }
            }

            else
            {
                if (!attackMoves.contains(move))
                {
                    attackMoves.add(move);
                    break;
                }
            }
        }

        System.out.printf("%s has evolved! It is now level %d!%n", name, level);
    }
    
    public String display_info()
    {
        StringBuilder info = new StringBuilder();
        info.append(String.format("%s currently has %.2f HP.\n\n", name, currentHitPoints));
        info.append("Attack Moves:\n");
        StringBuilder attacks = new StringBuilder();
        StringBuilder defenses = new StringBuilder();
        for (Move attack: attackMoves) {attacks.append(String.format("Name: %s, Damage: %.2f\n", attack.getName(), attack.getPowerLevel()));}

        info.append(attacks);

        info.append("\nDefense Moves:\n");
        for (Move defend: defenseMoves) {defenses.append(String.format("Name: %s, Defense: %.2f\n", defend.getName(), defend.getPowerLevel()));}

        info.append(defenses);
        info.append(String.format("\nBattle wins: %d", battleWins));
        info.append(String.format("\nTournament wins: %d\n", tournamentWins));
        return info.toString();
    }
}
