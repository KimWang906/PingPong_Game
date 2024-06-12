/*
 * Created by JFormDesigner on Wed Jun 12 13:08:20 KST 2024
 */

package pp_game.Screen;

import io.grpc.services.room.*;
import pp_game.Main;
import pp_game.Room.RoomClient;
import pp_game.Room.RoomUserClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * @author hyunbin
 */
public class RoomLobby extends JFrame {
    static long roomId;
    private UpdateThread updateThread;

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JButton quitRoom;
    private JButton gameSettings;
    private JButton startGame;
    private JScrollPane scrollPane1;
    private JList playerList;
    private JLabel playersLabel;
    private JLabel gameSettingsLabel;
    private JLabel label1;
    private JLabel maxScoreField;
    private JLabel label3;
    private JLabel initBallSpeedField;
    private JLabel ownerLabel;
    private JLabel ownerNameField;
    private JLabel label5;
    private JLabel paddleSpeedField;
    private DoQuitRoom doQuitRoom;
    private GoRoomSettings goRoomSettings;
    private DoStartGame doStartGame;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public RoomLobby(long id) {
        initComponents();
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        System.out.println("Request room id: " + id);
        roomId = id;
        RoomClient roomManager = new RoomClient(Main.channel);
        if (roomManager.watchRoom(id).join().getName().equals(Main.userClient.get_user().getUsername()))
            initLobby(roomManager.watchRoom(id).join());
        else {
            RoomUserClient roomUserManager = new RoomUserClient(Main.channel);
            Room currRoomData = roomManager.watchRoom(id).join();
            if ((currRoomData.getMaxSize() == MaxRoomSize.DOUBLE_PLAYERS && currRoomData.getMembers().getUsersList().size() < 2) ||
                    (currRoomData.getMaxSize() == MaxRoomSize.QUADRUPLE_PLAYERS && currRoomData.getMembers().getUsersList().size() < 4)) {
                roomUserManager.joinRoom(roomManager.watchRoom(id).join());
                initLobby(roomManager.watchRoom(id).join());
                updateThread = new UpdateThread();
            } else {
                showMessageDialog(null, "Room is full!");
                this.dispose();
                new SearchRooms();
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        quitRoom = new JButton();
        gameSettings = new JButton();
        startGame = new JButton();
        scrollPane1 = new JScrollPane();
        playerList = new JList();
        playersLabel = new JLabel();
        gameSettingsLabel = new JLabel();
        label1 = new JLabel();
        maxScoreField = new JLabel();
        label3 = new JLabel();
        initBallSpeedField = new JLabel();
        ownerLabel = new JLabel();
        ownerNameField = new JLabel();
        label5 = new JLabel();
        paddleSpeedField = new JLabel();
        doQuitRoom = new DoQuitRoom();
        goRoomSettings = new GoRoomSettings();
        doStartGame = new DoStartGame();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- quitRoom ----
        quitRoom.setFont(new Font("Arial", Font.PLAIN, 16));
        quitRoom.setAction(doQuitRoom);
        contentPane.add(quitRoom);
        quitRoom.setBounds(15, 405, 90, 30);

        //---- gameSettings ----
        gameSettings.setFont(new Font("Arial", Font.PLAIN, 16));
        gameSettings.setAction(goRoomSettings);
        contentPane.add(gameSettings);
        gameSettings.setBounds(640, 365, 140, 30);

        //---- startGame ----
        startGame.setFont(new Font("Arial", Font.PLAIN, 16));
        startGame.setAction(doStartGame);
        contentPane.add(startGame);
        startGame.setBounds(640, 405, 140, 30);

        //======== scrollPane1 ========
        {

            //---- playerList ----
            playerList.setFont(new Font("Arial", Font.PLAIN, 14));
            scrollPane1.setViewportView(playerList);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(15, 90, 365, 290);

        //---- playersLabel ----
        playersLabel.setText("Players");
        playersLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        contentPane.add(playersLabel);
        playersLabel.setBounds(new Rectangle(new Point(15, 55), playersLabel.getPreferredSize()));

        //---- gameSettingsLabel ----
        gameSettingsLabel.setText("Game Settings");
        gameSettingsLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        contentPane.add(gameSettingsLabel);
        gameSettingsLabel.setBounds(new Rectangle(new Point(400, 60), gameSettingsLabel.getPreferredSize()));

        //---- label1 ----
        label1.setText("Max Score:");
        label1.setFont(new Font("Arial", Font.PLAIN, 15));
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(420, 100), label1.getPreferredSize()));

        //---- maxScoreField ----
        maxScoreField.setText("NONE");
        maxScoreField.setFont(new Font("Arial", Font.PLAIN, 15));
        contentPane.add(maxScoreField);
        maxScoreField.setBounds(500, 100, 105, 18);

        //---- label3 ----
        label3.setText("Init Ball Speed:");
        label3.setFont(new Font("Arial", Font.PLAIN, 15));
        contentPane.add(label3);
        label3.setBounds(400, 120, 100, 18);

        //---- initBallSpeedField ----
        initBallSpeedField.setText("NONE");
        initBallSpeedField.setFont(new Font("Arial", Font.PLAIN, 15));
        contentPane.add(initBallSpeedField);
        initBallSpeedField.setBounds(500, 120, 105, 18);

        //---- ownerLabel ----
        ownerLabel.setText("Owner");
        ownerLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        contentPane.add(ownerLabel);
        ownerLabel.setBounds(new Rectangle(new Point(180, 385), ownerLabel.getPreferredSize()));

        //---- ownerNameField ----
        ownerNameField.setText("NONE");
        ownerNameField.setFont(new Font("Arial", Font.PLAIN, 18));
        contentPane.add(ownerNameField);
        ownerNameField.setBounds(260, 385, 120, 30);

        //---- label5 ----
        label5.setText("Paddle Speed:");
        label5.setFont(new Font("Arial", Font.PLAIN, 15));
        contentPane.add(label5);
        label5.setBounds(400, 140, 100, 18);

        //---- paddleSpeedField ----
        paddleSpeedField.setText("NONE");
        paddleSpeedField.setFont(new Font("Arial", Font.PLAIN, 15));
        contentPane.add(paddleSpeedField);
        paddleSpeedField.setBounds(500, 140, 105, 18);

        contentPane.setPreferredSize(new Dimension(800, 480));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    private void initLobby(Room currentRoom) {
        updateOwner(currentRoom);
        updatePlayers(currentRoom);
        updateSettings(currentRoom);
    }

    public void allUpdates() {
        RoomClient roomManager = new RoomClient(Main.channel);
        Room currRoomData = roomManager.watchRoom(roomId).join();
        updateOwner(currRoomData);
        updatePlayers(currRoomData);
        updateSettings(currRoomData);
    }

    public void updateOwner(Room room) {
        ownerNameField.setText(room.getOwner().getUsername());
    }

    public void updatePlayers(Room room) {
        RoomUserList members = room.getMembers();
        List<String> memberUsernames = members
                .getUsersList()
                .stream()
                .map(RoomUser::getUsername)
                .toList();
        SwingUtilities.invokeLater(() -> {
            DefaultListModel<String> model = new DefaultListModel<>();
            memberUsernames.forEach(model::addElement);
            playerList.setModel(model);
        });
    }

    public void updateSettings(Room room) {
        RoomSettings settings = room.getSettings();
        maxScoreField.setText(String.valueOf(settings.getMaxScore()));
        initBallSpeedField.setText(String.valueOf(settings.getBallSpped()));
        paddleSpeedField.setText(String.valueOf(settings.getPaddleSpeed()));
    }

    private static class GoRoomSettings extends AbstractAction {
        private GoRoomSettings() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            putValue(NAME, "Game Settings");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents  @formatter:on
        }

        public void actionPerformed(ActionEvent e) {
            new ChangeRoomSettings();
        }
    }

    private class UpdateThread extends Thread {
        public UpdateThread() {
            System.out.println("UpdateThread started");
            this.start();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("UpdateThread: update");
                RoomLobby.this.allUpdates();
            }
        }
    }

    private class DoQuitRoom extends AbstractAction {
        private DoQuitRoom() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            putValue(NAME, "Quit");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents  @formatter:on
        }

        public void actionPerformed(ActionEvent e) {
            String currentUsername = Main.userClient.get_user().getUsername();
            String roomOwnerName = ownerNameField.getText();

            if (currentUsername.equals(roomOwnerName)) {
                RoomClient roomManager = new RoomClient(Main.channel);
                roomManager.deleteRoom(roomId);
            } else {
                RoomUserClient roomUserManager = new RoomUserClient(Main.channel);
                roomUserManager.leaveRoom();
            }
            RoomLobby.this.dispose();
            new SearchRooms();
        }
    }

    private class DoStartGame extends AbstractAction {
        private DoStartGame() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            putValue(NAME, "Start Game");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents  @formatter:on
        }

        public void actionPerformed(ActionEvent e) {
            RoomLobby.this.setVisible(false);
            new InGame();
        }
    }
}
