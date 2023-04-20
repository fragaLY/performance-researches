package by.vk.vertx.reactive.configuration.server;

public enum Server {
    CONTENT_TYPE("application/json");

    private final String value;

    Server(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
