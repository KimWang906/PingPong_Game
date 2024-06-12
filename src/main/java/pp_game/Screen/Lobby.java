/*
 * Created by JFormDesigner on Wed Jun 12 12:02:49 KST 2024
 */

package pp_game.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static java.lang.System.exit;

/**
 * @author hyunbin
 */
public class Lobby extends JFrame {
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel MainTitle;
    private JButton searchRooms;
    private JButton settings;
    private JButton exitGame;
    private GoSearchRooms goSearchRooms;
    private DoExitGame runExitGame;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public Lobby() {
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        MainTitle = new JLabel();
        searchRooms = new JButton();
        settings = new JButton();
        exitGame = new JButton();
        goSearchRooms = new GoSearchRooms();
        runExitGame = new DoExitGame();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- MainTitle ----
        MainTitle.setText("BSSM Ping Pong Game");
        MainTitle.setFont(new Font("Arial", Font.BOLD, 30));
        contentPane.add(MainTitle);
        MainTitle.setBounds(new Rectangle(new Point(240, 75), MainTitle.getPreferredSize()));

        //---- searchRooms ----
        searchRooms.setFont(new Font("Arial", Font.PLAIN, 16));
        searchRooms.setAction(goSearchRooms);
        contentPane.add(searchRooms);
        searchRooms.setBounds(325, 145, 160, 40);

        //---- settings ----
        settings.setText("Settings");
        settings.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPane.add(settings);
        settings.setBounds(325, 200, 160, 40);

        //---- exitGame ----
        exitGame.setFont(new Font("Arial", Font.PLAIN, 16));
        exitGame.setAction(runExitGame);
        contentPane.add(exitGame);
        exitGame.setBounds(325, 295, 160, 40);

        contentPane.setPreferredSize(new Dimension(800, 480));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    private static class DoExitGame extends AbstractAction {
        private DoExitGame() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            putValue(NAME, "Exit Game");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents  @formatter:on
        }

        public void actionPerformed(ActionEvent e) {
            exit(0);
        }
    }

    private class GoSearchRooms extends AbstractAction {
        private GoSearchRooms() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            putValue(NAME, "Search Rooms");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents  @formatter:on
        }

        public void actionPerformed(ActionEvent e) {
            Lobby.this.dispose();
            new SearchRooms();
        }
    }
}
