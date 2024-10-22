package udp;

import javax.xml.crypto.Data;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;



public class UDPString {

    public static String normalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    public static void main(String[] args) {

        String address = "203.162.10.109";
        int port = 2208;
        String studentCode = "B21DCCN181";
        String qCode = "nRHl7pA8";
        String message = ";" + studentCode + ";" + qCode;

        try {

            DatagramSocket socket = new DatagramSocket();
            InetAddress inetAddress = InetAddress.getByName(address);

            DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), inetAddress, port);
            socket.send(packet);

            byte[] buffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(receivePacket);
            String receiveData = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println(receiveData);

            String[] data = receiveData.split(";");
            int requestId = Integer.parseInt(data[0]);
            String str = data[1];
            String[] words = str.split(" ");
            StringBuilder result = new StringBuilder();

            for(String word : words) {
                result.append(normalize(word)).append(" ");
            }

            String sendToServer = requestId + ";" + result.toString().trim();
            DatagramPacket sendPacket = new DatagramPacket(sendToServer.getBytes(), sendToServer.length(), inetAddress, port);
            socket.send(sendPacket);

            socket.close();

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
