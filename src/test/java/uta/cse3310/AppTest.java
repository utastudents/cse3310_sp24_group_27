// package uta.cse3310;


// import junit.framework.TestCase;
// import junit.framework.TestSuite;
// import org.java_websocket.WebSocket;
// import org.java_websocket.drafts.Draft_6455;
// import org.java_websocket.handshake.ClientHandshake;

// public class AppTest extends TestCase {

//     public void testOnOpen() throws Exception {
//         // Create a WebSocket connection
//         WebSocket conn = new MockWebSocket(null, null);
//         ClientHandshake handshake = new MockClientHandshake();

//         // Create an instance of App
//         App app = new App(8080, new Draft_6455());

//         // Simulate the onOpen event
//         app.onOpen(conn, handshake);

//         // Ensure that the connection has been established and the game state is updated
//         assertNotNull(conn.getAttachment());
//         // You may add more assertions based on your specific requirements
//     }

//     public void testOnClose() throws Exception {
//         // Create a WebSocket connection
//         WebSocket conn = new MockWebSocket(null, null);

//         // Create an instance of App
//         App app = new App(8080, new Draft_6455());

//         // Simulate the onClose event
//         app.onClose(conn, 1000, "Normal closure", true);

//         // Ensure that the game state is updated accordingly (e.g., player removed from the game)
//         // You may add more assertions based on your specific requirements
//     }

//     public void testOnMessage() throws Exception {
//         // Create a WebSocket connection
//         WebSocket conn = new MockWebSocket(null, null);

//         // Create an instance of App
//         App app = new App(8080, new Draft_6455());

//         // Simulate receiving a message
//         app.onMessage(conn, "Test message");

//         // Ensure that the message is processed correctly and the game state is updated accordingly
//         // You may add more assertions based on your specific requirements
//     }

//     // You can add more test cases for other methods as needed
// }