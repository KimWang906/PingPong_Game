package pp_game;

import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import pp_game.Auth.MetadataToken;
import pp_game.Screen.MainApp;
import pp_game.User.UserClient;

public class Main {
    final static String host = "localhost:50051";
    public static ManagedChannel channel = Grpc.
            newChannelBuilder(
                    host,
                    InsecureChannelCredentials
                            .create()
            ).intercept(new MetadataToken()).build();
    public static UserClient userClient;

    public static void main(String[] args) {
        userClient = new UserClient(channel);
        new MainApp();
    }
}