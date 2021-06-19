import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import java.util.Scanner;


public class TCPSocketClient {
    private static final int BufferSize=32;
    public static void main(String[] args) throws IOException {
        // 參數驗證，共有兩個，分別是server ip 與 port
        if ((args.length ==0) || (args.length > 2)) {
            throw new IllegalArgumentException("Parameter(s): <server><word> [<Port>]");
        }

        // 獲取server ip
        String server = args[0];

        byte[] buffer = new byte[BufferSize];
        // 解析端口 ，若無設定，預設為10240
        int servPort = (args.length == 2) ? Integer.parseInt(args[1]) : 10240;
        Socket client = null;
        try{
            client = new Socket(server, servPort);
        }catch (java.io.IOException e){
            System.out.println("Socket啟動出錯 !");
            System.out.println("IOException : "+ e.toString());
            System.exit(1);
        }


        System.out.println("正連線到Server ");

        // 獲取輸出輸入stream
        InputStream in = client.getInputStream();
        OutputStream out = client.getOutputStream();


        int MsgSize;
        String receData="";
        String data="";
        while(!data.equals("exit")) {
            Scanner sc = new Scanner(System.in);
            System.out.println("請輸入訊息...");
            //等待使用者輸入
            data = sc.next();

            while(data.getBytes().length>BufferSize) {
                System.out.println("輸入訊息過長，請再輸入一次...");
                data = sc.next();
            }
            out.write(data.getBytes());
            if(data.equals("exit"))
                break;
            if ((MsgSize = in.read(buffer)) != -1) {
                //接收收到訊息

                receData = new String(buffer, 0, MsgSize);
            }
            //連線中止條件
            if (receData.equals("exit") ) {
                break;
            } else {
                System.out.println("Server 說  : " + receData);
            }
        }
        System.out.println("結束連線...");
        client.close();


    }
}
