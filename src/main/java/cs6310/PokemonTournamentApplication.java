package cs6310;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import cs6310.enumeration.Element;
import cs6310.enumeration.Type;
import cs6310.Move.Move;
import cs6310.Move.MoveFactory;
import cs6310.Pokemon.Pokemon;
import cs6310.Pokemon.PokemonFactory;
import cs6310.battle.Battle;
import cs6310.battle.Tournament;
import cs6310.Login.Login;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.util.Map;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PokemonTournamentApplication {

    private PokemonFactory pokemonFactory = new PokemonFactory();
    private HashMap<String, Pokemon> pokemonMap = pokemonFactory.getPokemonMap();

    public static void main(String[] args) {
        SpringApplication.run(PokemonTournamentApplication.class, args);
    }

    static class CreatePokemonRequest {
        private String name;
        private double hitPoints;  
        private List<String> elements; 
        private List<String> attackMoves;
        private List<String> defenseMoves;

        // Getters
        public String getName() { return name; }
        public double getHitPoints() { return hitPoints; }
        public List<String> getElements() { return elements; }
        public List<String> getAttackMoves() { return attackMoves; }
        public List<String> getDefenseMoves() { return defenseMoves; }
    }

    @PostMapping("/pokemon/create")
    public ResponseEntity<?> createPokemon(@RequestBody CreatePokemonRequest request) {
        try {
            // Create Pokemon using the factory
            pokemonFactory.createPokemon(
                request.getName(),
                new ArrayList<>(request.getElements()),
                request.getHitPoints(),
                new ArrayList<>(request.getAttackMoves()),
                new ArrayList<>(request.getDefenseMoves())
            );

            return ResponseEntity.ok("Successfully created Pokemon: " + request.getName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating Pokemon: " + e.getMessage());
        }
    }

    @GetMapping("/pokemon/list")
    public List<Pokemon> getAllPokemonList() {
        return new ArrayList<>(pokemonMap.values());
    }
    

    static class RequestBattle {
        private String pokemonAName;
        private String pokemonBName;
        private Integer seed = 1; // Default seed value

        public String getPokemonAName() {
            return pokemonAName;
        }

        public String getPokemonBName() {
            return pokemonBName;
        }

        public Integer getSeed() {
            return seed;
        }
    }

    static class BattleResult {
        private Pokemon winner;
        private List<String> battleLog;
        private Pokemon loser;

        public BattleResult(Pokemon winner, Battle battle, List<String> battleLog) {
            this.winner = winner;
            this.battleLog = battleLog;
            this.loser = (winner == battle.getPokemonOne()) ? battle.getPokemonTwo() : battle.getPokemonOne();

        }

        public Pokemon getWinner() {
            return winner;
        }

        public List<String> getBattleLog() {
            return battleLog;
        }

        public Pokemon getLoser() {
            return loser;
        }
    }

    static class RequestTournament {
        private List<String> pokemonNames;
        private Integer seed = 1; // Default seed value

        public List<String> getPokemonNames() {
            return pokemonNames;
        }

        public Integer getSeed() {
            return seed;
        }
    }

    static class TournamentResult {
        private Pokemon winner;
        private List<String> tournamentLog;
        private List<String> tourneyList;

        public TournamentResult(Pokemon winner, List<String> tournamentLog, List<String> tourneyList) {
            this.winner = winner;
            this.tournamentLog = tournamentLog;
            this.tourneyList = tourneyList;
        }

        public Pokemon getWinner() {
            return winner;
        }

        public List<String> getTournamentLog() {
            return tournamentLog;
        }

        public List<String> getParticipants() {
            return tourneyList;
        }
    }

    @PostMapping("/battle")
    public ResponseEntity<?> startBattle(@RequestBody RequestBattle request) {
        try {
            // logs
            ByteArrayOutputStream bOutputs = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(bOutputs);
            System.setOut(ps);


            // Pokemon
            Pokemon pokemonA = pokemonMap.get(request.getPokemonAName());
            Pokemon pokemonB = pokemonMap.get(request.getPokemonBName());

            int seedValue = request.getSeed();
            pokemonA.setSeedValue(seedValue);
            pokemonB.setSeedValue(seedValue + 1);

            Battle battle = new Battle(pokemonA, pokemonB, seedValue, pokemonMap);
            Pokemon winner = battle.encounter();

            String battleLog = bOutputs.toString();
            List<String> logLines = Arrays.asList(battleLog.split("\n"));

            return ResponseEntity.ok(new BattleResult(winner, battle, logLines));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Battle error " + e.getMessage());
        }
    }

    @PostMapping("/tournament")
    public ResponseEntity<?> startTournament(@RequestBody RequestTournament request) {
        try {
            // Capture console output
            ByteArrayOutputStream bOutputs = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(bOutputs);
            PrintStream oldOut = System.out;
            System.setOut(ps);

            List<String> tourneyList = request.getPokemonNames();
            Tournament tournament = new Tournament(new ArrayList<>(tourneyList), request.getSeed(),pokemonMap);
            Pokemon winner = tournament.tourneyEncounter();
            System.setOut(oldOut);
            String tournamentLog = bOutputs.toString();
            List<String> logLines = Arrays.asList(tournamentLog.split("\n"));

            if (winner == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Tournament could not be completed.");
            }

            return ResponseEntity.ok(new TournamentResult(winner, logLines, tourneyList));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Tournament errors " + e.getMessage());
        }
    }

    // Request class to handle incoming login data
    static class RequestLogin {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RequestLogin request) {
        try {
            Map<String, Login.User> users = Login.readUsers();
            Login.User user = Login.loginUser(request.getUsername(), request.getPassword(), users);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HashMap<>());
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during login: " + e.getMessage());
        }
    }

    static class CreateMoveRequest {
        private String moveName;
        private double powerLevel;
        private Type type;
        private Element element;
        private String effect;

        // Getters
        public String getMoveName() {
            return moveName;
        }

        public double getPowerLevel() {
            return powerLevel;
        }

        public Type getType() {
            return type;
        }

        public Element getElement() {
            return element;
        }

        public String getEffect() {
            return effect;
        }
    }

    // Add these new endpoints

    @GetMapping("/moves/list")
    public ArrayList<Move> getAllMoves() {
        return MoveFactory.readMoves();
    }


    @PostMapping("/moves/create")
    public ResponseEntity<?> createMove(@RequestBody CreateMoveRequest request) {
        try {
            MoveFactory moveFactory = new MoveFactory();
            moveFactory.createMove(
                    request.getMoveName(),
                    request.getPowerLevel(),
                    request.getType(),
                    request.getElement(),
                    request.getEffect());
            return ResponseEntity.ok("Successfully created the move");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Create move error " + e.getMessage());
        }
    }
}