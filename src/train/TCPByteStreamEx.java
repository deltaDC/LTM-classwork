package train;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * [Mã câu hỏi (qCode): GkgZilQZ].  Một chương trình (tạm gọi là server) được triển khai tại địa chỉ 10.170.46.199 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s), yêu cầu xây dựng chương trình (tạm gọi là client) thực hiện kết nối tới server tại cổng 2207, sử dụng luồng byte dữ liệu (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
 * a.	Gửi chuỗi là mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;1D25ED92"
 * b.	Nhận lần lượt hai số nguyên a và b từ server
 * c.	Thực hiện tính toán tổng, tích và gửi lần lượt từng giá trị theo đúng thứ tự trên lên server
 * d.	Đóng kết nối và kết thúc
 */

public class TCPByteStreamEx {

    public static void main(String[] args) {
        int port = 2207;
        String address = "203.162.10.109";
        String message = "B21DCCN181;GkgZilQZ";

        try (Socket socket = new Socket()){

            socket.connect(new InetSocketAddress(address, port));

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            dataOutputStream.writeUTF(message);

            int a = dataInputStream.readInt();
            int b = dataInputStream.readInt();

            System.out.println(a);
            System.out.println(b);

            int sum = a + b;
            int prod = a * b;

            dataOutputStream.writeInt(sum);
            dataOutputStream.writeInt(prod);

            dataInputStream.close();
            dataOutputStream.close();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
