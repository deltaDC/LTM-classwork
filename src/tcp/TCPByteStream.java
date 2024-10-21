package tcp;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPByteStream {

    private static final int PORT = 2206;
    private static final int TIMEOUT = 5000;
    private static final String ADDRESS = "203.162.10.109";

    public static void main(String[] args) {

        String studentCode = "B21DCCN181";
        String qCode = "5Hps1CqB";
        String message = studentCode + ";" + qCode;

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ADDRESS, PORT), TIMEOUT);
            socket.setSoTimeout(TIMEOUT);

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            outputStream.write(message.getBytes());
            outputStream.flush();

            byte[] bytes = new byte[1024];
            int bytesRead = inputStream.read(bytes);
            String receiveData = new String(bytes, 0, bytesRead);


            String[] numbers = receiveData.split("\\|");
            int sum = 0;
            for(String number : numbers) {
                sum += Integer.parseInt(number);
            }

            String sumString = Integer.toString(sum);
            outputStream.write(sumString.getBytes());
            outputStream.flush();

            inputStream.close();
            outputStream.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
