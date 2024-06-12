package pp_game.Auth;

import io.grpc.*;
import pp_game.Main;

public class MetadataToken implements ClientInterceptor {
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                headers.put(
                        Metadata.Key.of(
                                "x-authorization",
                                Metadata.ASCII_STRING_MARSHALLER
                        ), Main.userClient.get_token().getAccessToken());

                super.start(responseListener, headers);
            }
        };
    }
}
