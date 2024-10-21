package tcp;

import java.io.*;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPByteStreamEx {

    private static final String SERVER_ADDRESS = "203.162.10.109";
    private static final int SERVER_PORT = 2207;
    private static final int TIMEOUT = 5000;

    public static void main(String[] args) {
        String studentCode = "B21DCCN181";
        String qCode = "GkgZilQZ";
        String message = studentCode + ";" + qCode;

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT), TIMEOUT);
            socket.setSoTimeout(TIMEOUT);

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            dataOutputStream.writeUTF(message);

            int a = dataInputStream.readInt();
            int b = dataInputStream.readInt();

            int sum = a + b;
            int product = a * b;

            dataOutputStream.writeInt(sum);
            dataOutputStream.writeInt(product);

            dataInputStream.close();
            dataOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
