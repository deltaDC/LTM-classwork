package train;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * [Mã câu hỏi (qCode): 5Hps1CqB].  Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s).
 * Yêu cầu xây dựng chương trình client thực hiện kết nối tới server trên sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
 * a.	Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;C64967DD"
 * b.	Nhận dữ liệu từ server là một chuỗi gồm các giá trị nguyên được phân tách với nhau bằng  "|"
 * Ex: 2|5|9|11
 * c.	Thực hiện tìm giá trị tổng của các số nguyên trong chuỗi và gửi lên server
 * Ex: 27
 * d.	Đóng kết nối và kết thúc
 */

public class TCPByteStream {

    public static void main(String[] args) {
        int port = 2206;
        String address = "203.162.10.109";
        String message = "B21DCCN181;5Hps1CqB";

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(address, port));

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            outputStream.write(message.getBytes());

            byte[] b = new byte[1024];
            int byteLength = inputStream.read(b);
            String receiveData = new String(b, 0, byteLength);
            System.out.println(byteLength);
            System.out.println(receiveData);

            String[] numbers = receiveData.split("\\|");
            int sum = 0;
            for(String num : numbers) {
                System.out.println(num);
                sum += Integer.parseInt(num);
            }

            String sumString = Integer.toString(sum);
            outputStream.write(sumString.getBytes());

            inputStream.close();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
