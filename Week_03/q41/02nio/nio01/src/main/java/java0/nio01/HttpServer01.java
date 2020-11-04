package java0.nio01;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class HttpServer01 {
    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(8801);
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                service(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static void service(Socket socket){
        try {
            Thread.sleep(20);
            InputStream in = socket.getInputStream();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            readStreamWithRecursion(output, in);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "hello,nio";
            body += output.toString(); // 传输过来的头。用于验证filter是不是成功了。
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
            printWriter.close();
            socket.close();
        } catch (IOException | InterruptedException  e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  static void readStreamWithRecursion(ByteArrayOutputStream output, InputStream inStream) throws Exception {
        long start = System.currentTimeMillis();
        while (inStream.available() == 0) {
            if ((System.currentTimeMillis() - start) > 20* 1000) {//超时退出
                throw new SocketTimeoutException("超时读取");
            }
        }
        byte[] buffer = new byte[2048];
        int read = inStream.read(buffer);
        output.write(buffer, 0, read);
        Thread.sleep(100);
        int a = inStream.available();
        if (a > 0) {
            readStreamWithRecursion(output, inStream);
        }
    }
}