package train;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPByteStreamEx {

    private static final int PORT = 2207;
    private static final int TIMEOUT = 5000;
    private static final String ADDRESS = "203.162.10.109";

    public static void main(String[] args) {

        String studentCode = "B21DCCN181";
        String qCode = "GkgZilQZ";
        String message = studentCode + ";" + qCode;


        try (Socket socket = new Socket()) {

            socket.connect(new InetSocketAddress(ADDRESS, PORT));
            socket.setSoTimeout(TIMEOUT);

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            dataOutputStream.writeUTF(message);

            int a = dataInputStream.readInt();
            int b = dataInputStream.readInt();

            dataOutputStream.writeInt(a + b);
            dataOutputStream.writeInt(a * b);

            dataInputStream.close();
            dataOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
