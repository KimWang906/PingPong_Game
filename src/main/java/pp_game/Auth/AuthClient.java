package pp_game.Auth;

import io.grpc.Channel;
import io.grpc.Status;
import io.grpc.services.auth.AuthGrpc;
import io.grpc.services.auth.LoginRequest;
import io.grpc.services.auth.RegisterRequest;
import io.grpc.services.auth.Token;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthClient {
    private static final Logger logger = Logger.getLogger(AuthClient.class.getName());

    private final AuthGrpc.AuthBlockingStub blockingStub;
    private final AuthGrpc.AuthStub asyncStub;

    public AuthClient(Channel channel) {
        blockingStub = AuthGrpc.newBlockingStub(channel);
        asyncStub = AuthGrpc.newStub(channel);
    }

    public CompletableFuture<Token> register(String username, String student_id, String password) {
        RegisterRequest request = RegisterRequest
                .newBuilder()
                .setUsername(username)
                .setStudentId(student_id)
                .setPassword(password)
                .build();

        CompletableFuture<Token> completableFuture = new CompletableFuture<>();

        StreamObserver<Token> responseObserver = new StreamObserver<Token>() {
            @Override
            public void onNext(Token token) {
                logger.info("Received token, " + token);
                completableFuture.complete(token);
            }

            @Override
            public void onError(Throwable t) {
                Status status = Status.fromThrowable(t);
                logger.log(Level.WARNING, "Register Failed {0}", status);
                completableFuture.completeExceptionally(t);
            }

            @Override
            public void onCompleted() {
                logger.info("Register completed");
            }
        };

        asyncStub.register(request, responseObserver);

        return completableFuture;
    }

    public CompletableFuture<Token> login(String username, String password) {
        LoginRequest request = LoginRequest
                .newBuilder()
                .setUsername(username)
                .setPassword(password)
                .build();

        CompletableFuture<Token> completableFuture = new CompletableFuture<>();

        StreamObserver<Token> responseObserver = new StreamObserver<Token>() {
            @Override
            public void onNext(Token token) {
                logger.info("Received token, " + token);
                completableFuture.complete(token);
            }

            @Override
            public void onError(Throwable t) {
                Status status = Status.fromThrowable(t);
                logger.log(Level.WARNING, "Login Failed {0}", status);
                completableFuture.completeExceptionally(t);
            }

            @Override
            public void onCompleted() {
                logger.info("Login completed");
            }
        };

        asyncStub.login(request, responseObserver);

        return completableFuture;
    }
}
