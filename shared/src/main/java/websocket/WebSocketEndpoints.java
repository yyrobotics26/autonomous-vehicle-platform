package websocket;

public final class WebSocketEndpoints {

    public static final String COLLISION_DESTINATION = "/collision";
    public static final String CONTROL_COMMAND_DESTINATION = "/control-command";
    public static final String OBSTACLE_DETECTED_DESTINATION = "/obstacle-detected";
    public static final String ROUTE_PROGRESS_DESTINATION = "/route-progress";
    public static final String TELEMETRY_DESTINATION = "/telemetry";

    public static final String ROVER_PREFIX = "/rover";

    private WebSocketEndpoints() {
    }
}
