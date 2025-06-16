package com.drs.drs_enhanced.backend;

import com.drs.drs_enhanced.model.Request;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocketHelper {
    
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8888;

    /**
     * Sends a request to the server and receives a response.
     * 
     * @param action The action to be done by server.
     * @param data The request object to send.
     * @return The response object received from the server.
     */
    public static Object sendRequest(String action, Object data) {
    try (
        Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
    ) {
        Request request = new Request(action, data);
        out.writeObject(request);
        out.flush();

        Object response = in.readObject();
        return response;

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
        return null;
    }
}
    
}
