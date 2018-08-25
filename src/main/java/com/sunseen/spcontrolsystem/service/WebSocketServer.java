package com.sunseen.spcontrolsystem.service;

import com.sunseen.spcontrolsystem.utils.DataUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebSocketServer {

    private boolean isEnable;
    private final WebConfig webConfig;
    private final ExecutorService threadPool;//线程池
    private ServerSocket socket;

    private List<Socket> socketList;

    public WebSocketServer(WebConfig webConfig) {
        this.webConfig = webConfig;
        threadPool = Executors.newFixedThreadPool(webConfig.getMaxParallels());
        socketList = new ArrayList<>();
    }

    /**
     * 开启server
     */
    public void startServerAsync() {
        isEnable = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                doProcSync();
            }
        }).start();
    }

    /**
     * 关闭server
     */
    public void stopServerAsync() throws IOException {
        if (!isEnable) {
            return;
        }
        isEnable = true;
        socket.close();
        socket = null;
    }

    private void doProcSync() {
        try {
            InetSocketAddress socketAddress = new InetSocketAddress(webConfig.getPort());
            socket = new ServerSocket();
            socket.bind(socketAddress);
            while (isEnable) {
                final Socket remotePeer = socket.accept();
                socketList.add(remotePeer);
                threadPool.submit(new Runnable() {
                    @Override
                    public void run() {
//                        LLogger.e("remotePeer..............."+remotePeer.getRemoteSocketAddress().toString());
                        onAcceptRemotePeer(remotePeer);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onAcceptRemotePeer(Socket remotePeer) {
        try {
            remotePeer.getOutputStream().write("connected successful".getBytes());//告诉客户端连接成功
            // 从Socket当中得到InputStream对象
            InputStream inputStream = remotePeer.getInputStream();
            byte buffer[] = new byte[1024 * 4];
//            int temp = 0;
            // 从InputStream当中读取客户端所发送的数据
//            while ((temp = inputStream.read(buffer)) != -1) {
////                LLogger.e(new String(buffer, 0, temp,"UTF-8"));
//                remotePeer.getOutputStream().write(buffer, 0, temp);//把客户端传来的消息发送回去
//            }
            DataUtils.readData(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送数据
     * @param ip 底层设备ip地址
     * @param bytes 数据
     */
    public void onSendMsg(String ip, byte[] bytes) {
        if (socketList.size() > 0)
        {
            for (int i = 0; i < socketList.size(); i ++)
            {
                if (socketList.get(i).getInetAddress().toString().contains(ip))
                {
                    if (socketList.get(i).isConnected()) {
                        try {
                            socketList.get(i).getOutputStream().write(bytes);
                        } catch (IOException e) {
                            socketList.remove(i);
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}