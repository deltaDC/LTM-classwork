package udp;

import java.net.*;

public class UDPDataType {

    public static void main(String[] args) {

        String address = "203.162.10.109";
        int port = 2207;
        String studentCode = "B21DCCN181";
        String qCode = "nRHl7pA8";
        String message = ";" + studentCode + ";" + qCode;

        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress inetAddress = InetAddress.getByName(address);
            socket.connect(inetAddress, port);

            DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), inetAddress, port);
            socket.send(packet);

            byte[] bytes = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(bytes, bytes.length);
            socket.receive(receivePacket);

            String receiveString = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println(receiveString);
            String[] str = receiveString.split(";");
            String requestId = str[0];
            String data = str[1];

            String[] dataArr = data.split(",");

            int max = Integer.parseInt(dataArr[0]);
            int min = Integer.parseInt(dataArr[0]);

            for(String num : dataArr) {
                int number = Integer.parseInt(num);
                if(number > max) {
                    max = number;
                }
                if(number < min) {
                    min = number;
                }
            }

            String toServer = requestId + ";" + max + "," + min;

            DatagramPacket packetToServer = new DatagramPacket(toServer.getBytes(), 0, toServer.length(), inetAddress, port);
            socket.send(packetToServer);

            socket.close();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
