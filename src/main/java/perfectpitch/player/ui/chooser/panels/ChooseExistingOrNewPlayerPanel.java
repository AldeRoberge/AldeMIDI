package perfectpitch.player.ui.chooser.panels;

import perfectpitch.player.Players;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class ChooseExistingOrNewPlayerPanel extends JPanel {

    /**
     * Create the panel.
     * <p>
     * Returns true if existing player
     * False if new player
     */
    public ChooseExistingOrNewPlayerPanel(Consumer<Boolean> callback) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel labelPanel = new JPanel();
        add(labelPanel);
        labelPanel.setLayout(new BorderLayout(0, 0));

        JLabel label = new JLabel("Who are you?");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        labelPanel.add(label, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        add(buttonPanel);

        JButton existingPlayerButton = new JButton("Existing player");
        existingPlayerButton.setFocusable(false);

        existingPlayerButton.setEnabled(Players.getPlayers().size() > 0);

        existingPlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                callback.accept(true);
            }
        });
        buttonPanel.add(existingPlayerButton);

        JButton newPlayerButton = new JButton("New player");
        newPlayerButton.requestFocus();
        newPlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                callback.accept(false);
            }
        });
        buttonPanel.add(newPlayerButton);

        JPanel bottomPanel = new JPanel();
        add(bottomPanel);
        bottomPanel.setLayout(new BorderLayout(0, 0));

    }

}
