package udp;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class Product implements Serializable {
    private static final long serialVersionUID = 20161107;

    private String id;
    private String code;
    private String name;
    private int quantity;

    public Product(String id, String code, String name, int quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}

public class UDPObject {

    public static void main(String[] args) {
        String address = "203.162.10.109";
        int port = 2209;
        String studentCode = "B21DCCN181";
        String qCode = "22IQaFrq";
        String message = ";" + studentCode + ";" + qCode;

        try {
            // Tạo DatagramSocket
            DatagramSocket socket = new DatagramSocket();
            InetAddress inetAddress = InetAddress.getByName(address);

            // Gửi thông điệp mã sinh viên và mã câu hỏi
            DatagramPacket sendPacket = new DatagramPacket(message.getBytes(), message.length(), inetAddress, port);
            socket.send(sendPacket);

            // Nhận dữ liệu từ server
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            // Đọc đối tượng Product từ dữ liệu nhận được
            try (ObjectInputStream objectStream = new ObjectInputStream(new ByteArrayInputStream(receiveBuffer))) {
                Product receivedProduct = (Product) objectStream.readObject();
                System.out.println("Received Product: " + receivedProduct);

                // Sửa đổi thông tin sản phẩm
                String[] nameParts = receivedProduct.getName().split(" ");
                String correctedName = nameParts[nameParts.length - 1] + " " + nameParts[1] + " " + nameParts[0];
                receivedProduct.setName(correctedName);

                // Đảo ngược số lượng
                int reversedQuantity = reverseNumber(receivedProduct.getQuantity());
                receivedProduct.setQuantity(reversedQuantity);

                // Tuần tự hóa đối tượng đã sửa đổi và gửi lại lên server
                try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                     ObjectOutputStream oos = new ObjectOutputStream(bos)) {

                    oos.writeObject(receivedProduct);

                    // Gửi lại thông điệp chứa đối tượng đã sửa
                    byte[] sendBuffer = bos.toByteArray();
                    DatagramPacket correctedPacket = new DatagramPacket(sendBuffer, sendBuffer.length, inetAddress, port);
                    socket.send(correctedPacket);
                }
            }

            // Đóng socket
            socket.close();

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    // Hàm đảo ngược số lượng
    public static int reverseNumber(int num) {
        int reversed = 0;
        while (num != 0) {
            reversed = reversed * 10 + num % 10;
            num /= 10;
        }
        return reversed;
    }
}

