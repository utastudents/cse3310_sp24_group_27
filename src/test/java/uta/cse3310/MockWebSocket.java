package uta.cse3310;

import org.java_websocket.WebSocketImpl;
import org.java_websocket.WebSocketListener;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ClientHandshake;

public class MockWebSocket extends WebSocketImpl {

    private String Message;
    private int CloseCode;
    private boolean Open;

    public MockWebSocket(WebSocketListener listener, Draft draft) {
        super(listener, draft);
    }

    public void onOpen(ClientHandshake handshake) {
        Open = true;
        System.out.println("WebSocket opened");
    }

    public void onClose(int code, String reason, boolean remote) {
        Open = false;
        CloseCode = code;
        System.out.println("WebSocket closed with code: " + code);
    }

    public void onMessage(String message) {
        Message = message;
        System.out.println("Message received: " + message);
    }

    public void onError(Exception ex) {
        System.out.println("Error ocurred: " + ex.getMessage());
    }

    public void close(int code) {
        super.close(code);
        System.out.println("Closed with code: " + code);
    }

    public String getMessage() {
        return Message;
    }

    public int getLastCloseCode() {
        return CloseCode;
    }

    public boolean Open() {
        return Open;
    }
    
}
