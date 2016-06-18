package ua.epamcourses.natalia_markova.classwork20.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by natalia_markova on 17.06.2016.
 */


class ClientHandler extends  Thread {
    Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            byte[] message = new byte[1024];
            int count = inputStream.read(message);
            String str = new String(message, 0, count);
            str+= " from server";
            outputStream.write(str.getBytes());
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

    }
}

public class ServerExample {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(7777, 0, InetAddress.getByName("127.0.0.1")); // 0 - количество клиентов в очередь на подключениеж, 0 == неограниченное количество

        while (true) {
            Socket socket = server.accept();
            new ClientHandler(socket).start();
        }

    }

}
