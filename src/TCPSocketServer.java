import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class TCPSocketServer {
    private static final int BufferSize=32;

    public static void main(String[] args) throws IOException {
        //參數驗證，一個參數port
        if (args.length != 1)
            throw new IllegalArgumentException();


        int port = Integer.parseInt(args[0]);

        ServerSocket server= new ServerSocket(port);
        byte[] buffer = new byte[BufferSize];
        while(true){
            System.out.println("等待Clinet連線...");
            Socket client = server.accept();
            SocketAddress ClientAdd = client
                    .getRemoteSocketAddress();
            System.out.println("連結到 Client 位置為 : " + ClientAdd);
            // 獲取輸出輸入stream
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();
            int MsgSize;
            String data = "",receData = "";
            while(!data.equals("exit") ) {
                if ((MsgSize = in.read(buffer)) !=-1) {
                    //接收收到訊息
                    receData = new String(buffer, 0, MsgSize);
                }
                //連線中止條件
                if (receData.equals("exit")) {
                    break;
                } else {
                    System.out.println("Client 說  : " + receData);
                }
                Scanner sc = new Scanner(System.in);
                System.out.println("請輸入訊息...");
                //等待使用者輸入
                data = sc.next();
                while(data.getBytes().length>BufferSize) {
                    System.out.println("輸入訊息過長，請再輸入一次...");
                    data = sc.next();
                }
                out.write(data.getBytes());
                if(data.equals("exit") )
                    break;
            }

            client.close();
        }

    }
}
