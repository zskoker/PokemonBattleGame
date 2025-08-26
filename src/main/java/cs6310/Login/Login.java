package cs6310.Login;

import java.io.*;
import java.util.*;

public class Login 
{
    private static final String FILE_PATH = "src/main/java/cs6310/CSV_Data/user.csv";

    public static class User 
    {
        public String userId;
        public String username;
        public String password;
        public String role;

        public User(String userId, String username, String password, String role) 
        {
            this.userId = userId;
            this.username = username;
            this.password = password;
            this.role = role;
        }

        @Override
        public String toString() {return userId + "," + username + "," + password + "," + role;}
    }

    public static Map<String, User> readUsers() 
    {
        Map<String, User> users = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) 
        {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) 
            {
                String[] parts = line.split(",");
                if (parts.length == 4) 
                {
                    User user = new User(parts[0], parts[1], parts[2], parts[3]);
                    users.put(parts[1], user);
                }
            }
        } 
        
        catch (IOException e) {System.err.println("Error reading the file: " + e.getMessage());}
        return users;
    }

    public static User loginUser(String username, String password, Map<String, User> users) 
    {
        if (users.containsKey(username)) 
        {
            User user = users.get(username);
            if(user.password.equals(password))
            {
                return user;
            }
            return null;
        }

        return null;
    }


    public static void signUp(Map<String, User> users) 
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Sign Up for a New Account");
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();

        // not sure if this is how it should work, please review
        while (users.containsKey(username)) 
        {
            System.out.println("Username already exists. Please choose a different username.");
            username = "";
        }

        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        System.out.print("Enter your role (user/admin): ");
        String role = scanner.nextLine().toLowerCase();

        int newUserId = users.size() + 1;
        User newUser = new User(String.valueOf(newUserId), username, password, role);

        scanner.close();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) 
        {
            bw.write(newUser.toString());
            bw.newLine();
            users.put(username, newUser);
            System.out.println("Sign up successful! You can now log in.");
        } 
        
        catch (IOException e) {System.err.println("Error writing to the file: " + e.getMessage());}
    }
}
