package train;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TCPCharacterStreamEx {
    private static final String SERVER_ADDRESS = "203.162.10.109";
    private static final int SERVER_PORT = 2208;
    private static final int TIMEOUT = 5000;

    public static void main(String[] args) {
        String studentCode = "B21DCCN181";
        String qCode = "nVTORu9j";
        String message = studentCode + ";" + qCode;

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            writer.write(message);
            writer.newLine();
            writer.flush();

            String str = reader.readLine();
            String[] arr = str.split(", ");
            List<String> edu = new ArrayList<>();

            for(String s : arr) {
                if(s.endsWith(".edu")) {
                    edu.add(s);
                }
            }

            if(!edu.isEmpty()) {
                writer.write(String.join(", ", edu));
                writer.newLine();
                writer.flush();
            }

            reader.close();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}