package com.example.javatest.socket;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ChatClient {

    public static void main(String[] args) {

        Socket socket = null;

        try {
            socket = new Socket();
            System.out.println("[연결 요청]");
            socket.connect(new InetSocketAddress("localhost", 5001));
            System.out.println("[연결 성공]");

            byte[] bytes = null;
            String message = null;

//            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            message = "Hello Server, I'm Client.";
            bw.write(message);
            bw.newLine();
            bw.write(0x04); // EOT control character
            bw.newLine(); // This is needed for BufferedReader/Writer - even if we've used a EOT
//            bw.newLine();
//            bytes = message.getBytes("UTF-8");

            bw.flush();
//            os.write(bytes);
//            os.flush();
            System.out.println("[데이터 보내기 성공]");

            InputStream is = socket.getInputStream();
            bytes = new byte[100];
            int readByteCount = is.read(bytes);
            message = new String(bytes,0,readByteCount,"UTF-8");
            System.out.println("[데이터 받기 성공] " + message);


            bw.close();
//            os.close();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!socket.isClosed()) {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
