package pp_game.User;

import com.google.protobuf.Empty;
import io.grpc.Channel;
import io.grpc.Status;
import io.grpc.services.User.User;
import io.grpc.services.User.UserManagerGrpc;
import io.grpc.services.auth.Token;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserClient {
    public static User user = User.getDefaultInstance();
    public static Token token = Token.getDefaultInstance();
    private final Logger logger = Logger.getLogger(UserClient.class.getName());
    private final UserManagerGrpc.UserManagerBlockingStub blockingStub;
    private final UserManagerGrpc.UserManagerStub asyncStub;

    public UserClient(Channel channel) {
        blockingStub = UserManagerGrpc.newBlockingStub(channel);
        asyncStub = UserManagerGrpc.newStub(channel);
    }

    public Token get_token() {
        return token;
    }

    public void set_token(Token token) {
        UserClient.token = token;
    }

    public User get_user() {
        return user;
    }

    public void set_user(User user) {
        UserClient.user = user;
    }

    public CompletableFuture<User> getUserData() {
        Empty request = Empty.getDefaultInstance();

        CompletableFuture<User> completableFuture = new CompletableFuture<>();
        asyncStub.getUser(request, new StreamObserver<User>() {
            @Override
            public void onNext(User user) {
                logger.info("Found User");
                logger.info("student_id: " + user.getStudentId());
                logger.info("username: " + user.getUsername());
                completableFuture.complete(user);
            }

            @Override
            public void onError(Throwable t) {
                Status status = Status.fromThrowable(t);
                logger.log(Level.WARNING, "Could not get User, Error: " + t);
                completableFuture.completeExceptionally(t);
            }

            @Override
            public void onCompleted() {
                logger.info("User has been successfully retrieved");
            }
        });
        return completableFuture;
    }
}
