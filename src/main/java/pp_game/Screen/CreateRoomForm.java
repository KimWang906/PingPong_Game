/*
 * Created by JFormDesigner on Wed Jun 12 13:29:29 KST 2024
 */

package pp_game.Screen;

import io.grpc.Status;
import io.grpc.services.room.MaxRoomSize;
import pp_game.Main;
import pp_game.Room.RoomClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @author hyunbin
 */
public class CreateRoomForm extends JFrame {
    private final Logger logger = Logger.getLogger(CreateRoomForm.class.getName());
    public JTextField roomName;
    public JComboBox maxSize;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel newRoomLabel;
    private JLabel roomNameLabel;
    private JLabel maxSizeLabel;
    private JButton createRoom;
    private JButton back;
    private ExecuteNewRoom excuteNewRoom;
    private DoBackSearchRooms doBackSearchRooms;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public CreateRoomForm() {
        initComponents();
        this.setVisible(true);
        maxSize.addItem(makeStringObj("default"));
        maxSize.addItem(makeStringObj("2"));
        maxSize.addItem(makeStringObj("4"));
    }

    private Object makeStringObj(final String item) {
        return new Object() {
            public String toString() {
                return item;
            }
        };
    }

    public JTextField getRoomName() {
        return roomName;
    }

    public MaxRoomSize getRoomMaxSize() {
        if (Objects.equals(Objects.requireNonNull(maxSize.getSelectedItem()).toString(), "4")) {
            return MaxRoomSize.QUADRUPLE_PLAYERS;
        } else {
            return MaxRoomSize.DOUBLE_PLAYERS;
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        newRoomLabel = new JLabel();
        roomNameLabel = new JLabel();
        maxSizeLabel = new JLabel();
        roomName = new JTextField();
        maxSize = new JComboBox();
        createRoom = new JButton();
        back = new JButton();
        excuteNewRoom = new ExecuteNewRoom();
        doBackSearchRooms = new DoBackSearchRooms();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- newRoomLabel ----
        newRoomLabel.setText("New Room");
        newRoomLabel.setFont(new Font("Arial", Font.BOLD, 20));
        contentPane.add(newRoomLabel);
        newRoomLabel.setBounds(195, 40, 105, 24);

        //---- roomNameLabel ----
        roomNameLabel.setText("Room Name");
        roomNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        contentPane.add(roomNameLabel);
        roomNameLabel.setBounds(85, 105, 119, 24);

        //---- maxSizeLabel ----
        maxSizeLabel.setText("Max Size");
        maxSizeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        contentPane.add(maxSizeLabel);
        maxSizeLabel.setBounds(115, 145, 89, 24);

        //---- roomName ----
        roomName.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPane.add(roomName);
        roomName.setBounds(205, 100, 180, 34);

        //---- maxSize ----
        maxSize.setFont(new Font("Arial", Font.PLAIN, 16));
        maxSize.setSelectedIndex(-1);
        contentPane.add(maxSize);
        maxSize.setBounds(205, 140, 180, 34);

        //---- createRoom ----
        createRoom.setFont(new Font("Arial", Font.PLAIN, 16));
        createRoom.setAction(excuteNewRoom);
        contentPane.add(createRoom);
        createRoom.setBounds(270, 230, 160, 34);

        //---- back ----
        back.setFont(new Font("Arial", Font.PLAIN, 16));
        back.setAction(doBackSearchRooms);
        contentPane.add(back);
        back.setBounds(95, 230, 160, 34);

        contentPane.setPreferredSize(new Dimension(500, 360));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    private class ExecuteNewRoom extends AbstractAction {
        private ExecuteNewRoom() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            putValue(NAME, "Create new Room");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents  @formatter:on
        }

        public void actionPerformed(ActionEvent e) {
            RoomClient roomManager = new RoomClient(Main.channel);

            System.out.println("Request room name: " + roomName.getText());
            System.out.println("Request room max size: " + Objects.requireNonNull(maxSize.getSelectedItem()).toString());
            System.out.println("Request Username: " + Main.userClient.get_user().getUsername());

            roomManager.createRoom(
                    CreateRoomForm.this.getRoomName().getText(),
                    CreateRoomForm.this.getRoomMaxSize(),
                    Main.userClient.get_user().getUsername()
            ).thenAccept(roomRes -> {
                CreateRoomForm.this.dispose();
                new RoomLobby(roomRes.getId());
            }).exceptionally(ex -> {
                logger.info("Error creating room: " + Status.fromThrowable(ex));
                return null;
            });
        }
    }

    private class DoBackSearchRooms extends AbstractAction {
        private DoBackSearchRooms() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            putValue(NAME, "Back");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents  @formatter:on
        }

        public void actionPerformed(ActionEvent e) {
            CreateRoomForm.this.dispose();
            new SearchRooms();
        }
    }
}
