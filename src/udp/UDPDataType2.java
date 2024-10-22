package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPDataType2 {

    public static void main(String[] args) {

        String address = "203.162.10.109";
        int port = 2207;
        String studentCode = "B21DCCN181";
        String qCode = "nRHl7pA8";
        String message = ";" + studentCode + ";" + qCode;

        try {

            DatagramSocket socket = new DatagramSocket();
            InetAddress inetAddress = InetAddress.getByName(address);

            DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), inetAddress, port);
            socket.send(packet);

            byte[] bytes = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(bytes, bytes.length);
            socket.receive(receivePacket);

            String receiveString = new String(receivePacket.getData(), 0, receivePacket.getLength());
            String requestId = receiveString.split(";")[0];
            String data = receiveString.split(";")[1];

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

            int secondMax = Integer.MIN_VALUE;
            int secondMin = Integer.MAX_VALUE;

            for(String num : dataArr) {
                int number = Integer.parseInt(num);
                if(number > secondMax && number < max) {
                    secondMax = number;
                }
                if(number < secondMin && number > min) {
                    secondMin = number;
                }
            }

            String toServer = requestId + ";" + secondMax + "," + secondMin;

            DatagramPacket datagramPacket = new DatagramPacket(toServer.getBytes(), toServer.length(), inetAddress, port);
            socket.send(datagramPacket);

            socket.close();


        } catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
