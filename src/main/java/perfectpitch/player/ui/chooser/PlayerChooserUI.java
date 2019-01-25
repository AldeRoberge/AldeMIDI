package perfectpitch.player.ui.chooser;

import alde.commons.util.window.UtilityJFrame;
import org.slf4j.LoggerFactory;
import perfectpitch.player.Player;
import perfectpitch.player.ui.EditPlayerImage;
import perfectpitch.player.ui.chooser.panels.ChooseExistingOrNewPlayerPanel;
import perfectpitch.player.ui.chooser.panels.CreateNewPlayerPanel;
import perfectpitch.player.ui.chooser.panels.SelectExistingPlayerPanel;
import perfectpitch.util.GetResource;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class PlayerChooserUI {

    private static org.slf4j.Logger log = LoggerFactory.getLogger(PlayerChooserUI.class);

    private JPanel existingOrReturning;

    private UtilityJFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PlayerChooserUI window = new PlayerChooserUI(new Consumer<Player>() {
                        @Override
                        public void accept(Player p) {
                            log.info("Received player : " + p.getName());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public PlayerChooserUI(Consumer<Player> callback) {
        frame = new UtilityJFrame();
        frame.setTitle("Player Selection");
        frame.setBounds(100, 100, 500, 275);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setIconImage(GetResource.getSoftwareIcon());

        existingOrReturning = new ChooseExistingOrNewPlayerPanel(existingPlayer -> {
            if (existingPlayer) { //Existing player
                log.info("Selecting existing player...");
                JPanel selectExistingPanel = new SelectExistingPlayerPanel(callback, this::setMainMenu);
                setView(selectExistingPanel);
            } else { //New player
                log.info("Creating new player...");
                frame.setTitle("Player Creation");
                JPanel createNewPlayerPanel = new CreateNewPlayerPanel(t -> {
                    setView(new EditPlayerImage(t, () -> callback.accept(t)));
                    callback.accept(t);
                }, this::setMainMenu);
                setView(createNewPlayerPanel);
            }
        });

        setView(existingOrReturning);
        frame.setVisible(true);
    }

    private void setMainMenu() {
        setView(existingOrReturning);
    }

    private void setView(JPanel jpanel) {

        frame.getContentPane().removeAll();
        frame.getContentPane().add(jpanel, BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();

    }

}
