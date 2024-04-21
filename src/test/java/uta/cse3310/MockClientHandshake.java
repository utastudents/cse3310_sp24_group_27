package uta.cse3310;

import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import org.java_websocket.handshake.ClientHandshake;

public class MockClientHandshake implements ClientHandshake {

    private Map<String, String> fields = new HashMap<>();
    private String resourceDescriptor = "/";

    @Override
    public Iterator<String> iterateHttpFields() {
        return fields.keySet().iterator();
    }

    @Override
    public String getFieldValue(String name) {
        return fields.getOrDefault(name, null);
    }

    @Override
    public boolean hasFieldValue(String name) {
        return fields.containsKey(name);
    }

    @Override
    public byte[] getContent() {
        return new byte[0];
    }

    @Override
    public String getResourceDescriptor() {
        return resourceDescriptor;
    }

    public void setFieldValue(String name, String value) {
        fields.put(name, value);
    }

    public void setResourceDescriptor(String resourceDescriptor) {
        this.resourceDescriptor = resourceDescriptor;
    }
}
