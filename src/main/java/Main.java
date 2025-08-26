import cs6310.CommandProcessor;

public class Main 
{
    public static void main(String[] args) 
    {
        System.out.println("Welcome to the thunder dome!");
        var methodCaller = new CommandProcessor();
        methodCaller.ProcessCommands(args);
    }
}