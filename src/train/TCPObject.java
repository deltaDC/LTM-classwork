package train;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;

class Student implements Serializable {
    private static final long serialVersionUID = 20151107;

    private int id;
    private String code;
    private float gpa;
    private String gpaLetter;

    public Student(int id, String code, float gpa, String gpaLetter) {
        this.id = id;
        this.code = code;
        this.gpa = gpa;
        this.gpaLetter = gpaLetter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    public String getGpaLetter() {
        if(this.gpa >= 3.7 && this.gpa <= 4.0) {
            return "A";
        }
        return "B";
    }

    public void setGpaLetter(String gpaLetter) {
        this.gpaLetter = gpaLetter;
    }
}

public class TCPObject {

    private static final String SERVER_ADDRESS = "203.162.10.109";
    private static final int SERVER_PORT = 2208;
    private static final int TIMEOUT = 5000;

    public static void main(String[] args) {

        String studentCode = "B21DCCN181";
        String qCode = "nVTORu9j";
        String message = studentCode + ";" + qCode;

        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)){

            socket.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));
            socket.setSoTimeout(TIMEOUT);

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            objectOutputStream.writeObject(message);
            objectOutputStream.flush();

            Student student = (Student) objectInputStream.readObject();
            student.setGpaLetter(student.getGpaLetter());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
