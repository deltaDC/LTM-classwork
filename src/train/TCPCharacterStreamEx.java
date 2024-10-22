package train;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * [Mã câu hỏi (qCode): nVTORu9j].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208
 * (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với
 * server sử dụng các luồng byte (BufferedWriter/BufferedReader) theo kịch bản sau:
 * a.	Gửi một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;EC4F899B"
 * b.	Nhận một chuỗi ngẫu nhiên là danh sách các một số tên miền từ server
 * Ví dụ: giHgWHwkLf0Rd0.io, I7jpjuRw13D.io, wXf6GP3KP.vn, MdpIzhxDVtTFTF.edu, TUHuMfn25chmw.vn, HHjE9.com, 4hJld2m2yiweto.vn, y2L4SQwH.vn, s2aUrZGdzS.com, 4hXfJe9giAA.edu
 * c.	Tìm kiếm các tên miền .edu và gửi lên server
 * Ví dụ: MdpIzhxDVtTFTF.edu, 4hXfJe9giAA.edu
 * d.	Đóng kết nối và kết thúc chương trình.
 */

public class TCPCharacterStreamEx {

    public static void main(String[] args) {
        int port = 2208;
        String address = "203.162.10.109";
        String message = "B21DCCN181;nVTORu9j";

        try (Socket socket = new Socket()){

            socket.connect(new InetSocketAddress(address, port));

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();


            String data = bufferedReader.readLine();
            System.out.println(data);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
