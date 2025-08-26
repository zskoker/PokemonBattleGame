package cs6310.Exceptions;

public class BattleLostException extends Exception 
{
    private String LosingPokemonName;

    public BattleLostException(String errorMessage) {super(errorMessage);}

    public BattleLostException(String errorMessage, String pokemonName) {LosingPokemonName = pokemonName;}

    public String get_losing_pokemon_name() {return LosingPokemonName;}
}
