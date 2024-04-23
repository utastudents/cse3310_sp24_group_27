package uta.cse3310;

import junit.framework.TestCase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpServerTest extends TestCase {

    private HttpServer server;
    private Thread serverThread;
    private final int testPort = 8080;
    private final String testDirName = "./test-html";

    protected void setUp() throws Exception {
        super.setUp();
        server = new HttpServer(testPort, testDirName);
        serverThread = new Thread(() -> server.start());
        serverThread.start();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        serverThread.interrupt();
        serverThread.join();
    }

    public void testFileServing() throws IOException {
        URL url = new URL("http://localhost:" + testPort + "/index.html");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int responseCode = connection.getResponseCode();
        assertEquals(200, responseCode);
    }

    public void testAPITimeEndpoint() throws IOException {
        URL url = new URL("http://localhost:" + testPort + "/api/time");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int responseCode = connection.getResponseCode();
        assertEquals(200, responseCode);

        String contentType = connection.getHeaderField("Content-Type");
        assertEquals("text/plain", contentType);

        String response = new String(connection.getInputStream().readAllBytes());
        assertTrue(response.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"));
    }
}
