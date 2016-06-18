package ua.epamcourses.natalia_markova.classwork20.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by natalia_markova on 17.06.2016.
 */
public class ClientExample {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 7777);
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        String message = "Hello, server";
        outputStream.write(message.getBytes());
        byte[] result = new byte[1024];
        int count = inputStream.read(result);
        String received = new String(result, 0, count);
        System.out.println(received);
        inputStream.close();
        outputStream.close();
    }

}
