/*
 * Created by JFormDesigner on Wed Jun 12 11:56:39 KST 2024
 */

package pp_game.Screen;

import io.grpc.services.room.SummaryRoomInfo;
import pp_game.Main;
import pp_game.Room.RoomClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * @author hyunbin
 */
public class SearchRooms extends JFrame {
    static List<SummaryRoomInfo> currentRooms;

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JScrollPane scrollRoomList;
    private JList roomList;
    private JLabel searchRoomsLabel;
    private JButton enterRoom;
    private JButton back;
    private JButton refreshRooms;
    private JButton createRoom;
    private DoBackLobby doBackLobby;
    private DoEnterRoom doEnterRoom;
    private DoRefreshRooms doRefreshRooms;
    private DoCreateRoom doCreateRoom;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public SearchRooms() {
        initComponents();
        this.setVisible(true);
        updateRooms();
        roomList.setFixedCellHeight(60);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        scrollRoomList = new JScrollPane();
        roomList = new JList();
        searchRoomsLabel = new JLabel();
        enterRoom = new JButton();
        back = new JButton();
        refreshRooms = new JButton();
        createRoom = new JButton();
        doBackLobby = new DoBackLobby();
        doEnterRoom = new DoEnterRoom();
        doRefreshRooms = new DoRefreshRooms();
        doCreateRoom = new DoCreateRoom();

        //======== this ========
        setTitle("PP Game");
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== scrollRoomList ========
        {

            //---- roomList ----
            roomList.setFont(new Font("Arial", Font.PLAIN, 14));
            scrollRoomList.setViewportView(roomList);
        }
        contentPane.add(scrollRoomList);
        scrollRoomList.setBounds(10, 50, 445, 395);

        //---- searchRoomsLabel ----
        searchRoomsLabel.setText("Search Rooms");
        searchRoomsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        contentPane.add(searchRoomsLabel);
        searchRoomsLabel.setBounds(new Rectangle(new Point(10, 15), searchRoomsLabel.getPreferredSize()));

        //---- enterRoom ----
        enterRoom.setFont(new Font("Arial", Font.PLAIN, 16));
        enterRoom.setAction(doEnterRoom);
        contentPane.add(enterRoom);
        enterRoom.setBounds(655, 405, 130, 34);

        //---- back ----
        back.setFont(new Font("Arial", Font.PLAIN, 16));
        back.setAction(doBackLobby);
        back.setPreferredSize(new Dimension(115, 25));
        contentPane.add(back);
        back.setBounds(530, 405, 115, 34);

        //---- refreshRooms ----
        refreshRooms.setFont(new Font("Arial", Font.PLAIN, 16));
        refreshRooms.setAction(doRefreshRooms);
        contentPane.add(refreshRooms);
        refreshRooms.setBounds(460, 50, 130, 34);

        //---- createRoom ----
        createRoom.setFont(new Font("Arial", Font.PLAIN, 16));
        createRoom.setAction(doCreateRoom);
        contentPane.add(createRoom);
        createRoom.setBounds(460, 90, 130, 34);

        contentPane.setPreferredSize(new Dimension(800, 480));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    void updateRooms() {
        RoomClient roomManager = new RoomClient(Main.channel);
        roomManager.listRoom().thenAccept(rooms -> {
            SwingUtilities.invokeLater(() -> {
                DefaultListModel<String> model = new DefaultListModel<>();
                SearchRooms.currentRooms = rooms;
                rooms.forEach(room -> model.addElement(
                        "<html>" +
                                "ID: " + room.getId() + "<br/>" +
                                "Room Name: " + room.getName() + "<br/>" +
                                "Room Size: " + room.getMaxSize() + "</html>"
                ));
                roomList.setModel(model);
            });
        }).exceptionally(ex -> {
            // 예외 처리
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(
                        SearchRooms.this,
                        "Failed to load rooms: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE
                );
            });
            return null;
        });
    }

    private class DoBackLobby extends AbstractAction {
        private DoBackLobby() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            putValue(NAME, "Back");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents  @formatter:on
        }

        public void actionPerformed(ActionEvent e) {
            SearchRooms.this.dispose();
            new Lobby();
        }
    }

    private class DoEnterRoom extends AbstractAction {
        private DoEnterRoom() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            putValue(NAME, "Enter Room");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents  @formatter:on
        }

        public void actionPerformed(ActionEvent e) {
            SearchRooms.this.dispose();
            int selectedIndex = roomList.getSelectedIndex();
            long roomId = currentRooms.get(selectedIndex).getId();
            new RoomLobby(roomId);
        }
    }

    private class DoRefreshRooms extends AbstractAction {
        private DoRefreshRooms() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            putValue(NAME, "Refresh");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents  @formatter:on
        }

        public void actionPerformed(ActionEvent e) {
            SearchRooms.this.updateRooms();
        }
    }

    private class DoCreateRoom extends AbstractAction {
        private DoCreateRoom() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            putValue(NAME, "Create Room");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents  @formatter:on
        }

        public void actionPerformed(ActionEvent e) {
            SearchRooms.this.dispose();
            new CreateRoomForm();
        }
    }
}
