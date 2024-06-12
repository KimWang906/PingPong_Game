package pp_game.Room;

import com.google.protobuf.Empty;
import io.grpc.Channel;
import io.grpc.Status;
import io.grpc.services.room.JoinRoomRequest;
import io.grpc.services.room.Room;
import io.grpc.services.room.RoomUserManagerGrpc;
import io.grpc.stub.StreamObserver;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomUserClient {
    private static final Logger logger = Logger.getLogger(RoomUserClient.class.getName());

    private final RoomUserManagerGrpc.RoomUserManagerBlockingStub blockingStub;
    private final RoomUserManagerGrpc.RoomUserManagerStub asyncStub;

    public RoomUserClient(Channel channel) {
        blockingStub = RoomUserManagerGrpc.newBlockingStub(channel);
        asyncStub = RoomUserManagerGrpc.newStub(channel);
    }

    public void joinRoom(Room room) {
        JoinRoomRequest request = JoinRoomRequest
                .newBuilder()
                .setId(room.getId())
                .build();

        asyncStub.joinRoom(request, new StreamObserver<>() {
            @Override
            public void onNext(Empty empty) {
                logger.info("room id: " + room.getId());
            }

            @Override
            public void onError(Throwable t) {
                Status status = Status.fromThrowable(t);
                logger.log(Level.WARNING, "Get rooms failed " + status);
            }

            @Override
            public void onCompleted() {
                logger.info("Room joined");
            }
        });
    }

    public void leaveRoom() {
        Empty request = Empty.getDefaultInstance();

        asyncStub.leaveRoom(request, new StreamObserver<>() {
            @Override
            public void onNext(Empty empty) {
            }

            @Override
            public void onError(Throwable t) {
                logger.log(
                        Level.WARNING,
                        "Leaving rooms failed " + Status.fromThrowable(t)
                );
            }

            @Override
            public void onCompleted() {
                logger.info("Room leaved");
            }
        });
    }
}
