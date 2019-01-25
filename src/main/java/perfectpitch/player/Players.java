package perfectpitch.player;

import alde.commons.util.file.ObjectSerializer;
import org.slf4j.LoggerFactory;
import perfectpitch.player.password.PasswordStorage;
import perfectpitch.player.password.PasswordStorage.CannotPerformOperationException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Players {

    private static org.slf4j.Logger log = LoggerFactory.getLogger(Players.class);

    private static final String PLAYERS_FOLDER_PATH = "players/";

    public static void main(String[] args) {

        // Jon or bingo

        List<Player> players = getPlayers();

        log.info("Players : " + players.size());

        for (Player p : players) {
            log.info(p.toString());
        }

        Player p;
        try {
            p = new Player("Big Nibba", PasswordStorage.createHash("Jesus"));
            log.info("Saving to file new player : " + p);
            saveToFile(p);
        } catch (CannotPerformOperationException e) {
            e.printStackTrace();
            log.info("Error creating player!");
        }

    }

    public static List<Player> getPlayers() {

        List<Player> players = new ArrayList<>();

        File folder = new File(PLAYERS_FOLDER_PATH);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles == null) {
            log.info("No players folder found. Creation of a new folder at '" + folder.getAbsolutePath() + "'. Success : " + folder.mkdirs() + ".");

        } else {
            for (File listOfFile : listOfFiles) {
                players.add(loadFromFile(listOfFile));
            }
        }

        return players;

    }

    private static List<ObjectSerializer<Player>> serializedPlayers = new ArrayList<>();

    private static Player loadFromFile(File file) {

        if (file.exists() && !(file.length() == 0)) {
            for (ObjectSerializer<Player> p : serializedPlayers) {
                if (p.getFile().equals(file)) {

                    log.info("Returning!");

                    return p.get();
                }
            }

            ObjectSerializer<Player> p = new ObjectSerializer<Player>(file);
            serializedPlayers.add(p);
            return p.get();

        } else {
            log.error("File " + file.getAbsolutePath() + " is empty or does not exist!");
        }

        return null;

    }

    public static void saveToFile(Player player) {

        String fileName = player.getName() + ".serialized";

        File file = new File(PLAYERS_FOLDER_PATH + fileName);

        for (ObjectSerializer<Player> p : serializedPlayers) {
            if (p.getFile().equals(file)) {
                p.save();
            }
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            log.error("Could not create file " + file.getAbsolutePath() + "!");
            e.printStackTrace();
        }

        ObjectSerializer<Player> p = new ObjectSerializer<Player>(file, player);
        p.save();

        serializedPlayers.add(p);

    }

}
