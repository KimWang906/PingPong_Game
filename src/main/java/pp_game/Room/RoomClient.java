package pp_game.Room;

import com.google.protobuf.Empty;
import io.grpc.Channel;
import io.grpc.Status;
import io.grpc.services.room.*;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomClient {
    private static final Logger logger = Logger.getLogger(RoomClient.class.getName());

    private final RoomManagerGrpc.RoomManagerBlockingStub blockingStub;
    private final RoomManagerGrpc.RoomManagerStub asyncStub;

    public RoomClient(Channel channel) {
        blockingStub = RoomManagerGrpc.newBlockingStub(channel);
        asyncStub = RoomManagerGrpc.newStub(channel);
    }

    public CompletableFuture<List<SummaryRoomInfo>> listRoom() {
        Empty request = Empty.getDefaultInstance();

        CompletableFuture<List<SummaryRoomInfo>> completableFuture = new CompletableFuture<List<SummaryRoomInfo>>();
        List<SummaryRoomInfo> rooms = new ArrayList<>();
        asyncStub.listRooms(request, new StreamObserver<SummaryRoomInfo>() {
            @Override
            public void onNext(SummaryRoomInfo room) {
//                logger.info("Got room:" + room.getName());
                rooms.add(room);
            }

            @Override
            public void onError(Throwable t) {
                Status status = Status.fromThrowable(t);
                logger.log(Level.WARNING, "Get rooms failed {0}", t);
                completableFuture.completeExceptionally(t);
            }

            @Override
            public void onCompleted() {
                completableFuture.complete(rooms);
            }
        });

        return completableFuture;
    }

    public CompletableFuture<CreatedRoom> createRoom(String roomName, MaxRoomSize size, String ownerName) {
        CreateRoomRequest request = CreateRoomRequest
                .newBuilder()
                .setRoomName(roomName)
                .setMaxSize(size)
                .setOwnerName(ownerName)
                .build();

        CompletableFuture<CreatedRoom> completableFuture = new CompletableFuture<>();

        asyncStub.createRoom(request, new StreamObserver<>() {
            @Override
            public void onNext(CreatedRoom room) {
                logger.log(Level.INFO, "New Room: " + roomName);
                completableFuture.complete(room);
            }

            @Override
            public void onError(Throwable t) {
                Status status = Status.fromThrowable(t);
                logger.log(Level.WARNING, "Get rooms failed " + status);
            }

            @Override
            public void onCompleted() {
                logger.log(Level.INFO, "Room created");
            }
        });

        return completableFuture;
    }

    public void deleteRoom(long id) {
        DeleteRoomRequest request = DeleteRoomRequest
                .newBuilder()
                .setId(id)
                .build();

        blockingStub.deleteRoom(request);
    }

    public CompletableFuture<Room> watchRoom(long id) {
        RoomInfoRequest request = RoomInfoRequest
                .newBuilder()
                .setId(id)
                .build();

        CompletableFuture<Room> completableFuture = new CompletableFuture<>();

        asyncStub.watchRoomInfo(request, new StreamObserver<Room>() {
            @Override
            public void onNext(Room room) {
                logger.log(Level.INFO, "Room info");
                logger.info("Room id: " + room.getId());
                logger.info("Room name: " + room.getName());
                logger.info("Room owner: " + room.getOwner());
                logger.info("Room member: " + room.getMembers());
                completableFuture.complete(room);
            }

            @Override
            public void onError(Throwable t) {
                logger.log(Level.WARNING, "Get rooms failed " + t);
            }

            @Override
            public void onCompleted() {
                logger.log(Level.INFO, "Room watched");
            }
        });

        return completableFuture;
    }

    public void changeRoomSettings(long id, RoomSettings roomSettings) {
        ChangeRoomSettingsRequest request = ChangeRoomSettingsRequest
                .newBuilder()
                .setId(id)
                .setSettings(roomSettings)
                .build();

        asyncStub.changeRoomSettings(request, new StreamObserver<>() {
            @Override
            public void onNext(Empty empty) {
            }

            @Override
            public void onError(Throwable t) {
                logger.log(Level.WARNING, "Get rooms failed " + t);
            }

            @Override
            public void onCompleted() {
                logger.log(Level.INFO, "Room settings changed");
            }
        });
    }
}
