package tcp;

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
            socket.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT), TIMEOUT);
            socket.setSoTimeout(TIMEOUT);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer.write(message);
            writer.newLine();
            writer.flush();

            String domainList = reader.readLine();
            System.out.println("Received domain list: " + domainList);

            String[] domains = domainList.split(", ");
            List<String> eduDomains = new ArrayList<>();

            for (String domain : domains) {
                if (domain.endsWith(".edu")) {
                    eduDomains.add(domain);
                }
            }

            if (!eduDomains.isEmpty()) {
                String eduDomainList = String.join(", ", eduDomains);
                writer.write(eduDomainList);
                writer.newLine();
                writer.flush();
                System.out.println("Sent .edu domains to server: " + eduDomainList);
            } else {
                System.out.println("No .edu domains found.");
            }

            writer.close();
            reader.close();
            socket.close();
            System.out.println("Connection closed.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}