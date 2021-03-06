package server.chat.model;/*
是服务器和某个客户端的通讯线程
 */


import common.chat.Message;
import common.chat.MessageType;
import common.entity.User;
import server.db.DbConnect;
import server.db.DbHandle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ServerConClientThread extends Thread {
    Socket s;
    User user;

    public ServerConClientThread(Socket s, User user) {
        //把服务器与该客户端的连接赋给s
        this.s = s;
        this.user = user;
    }

    //让该线程通知其他用户
    public void notifyOther(String iAm) {
        //得到所有在线的人的线程
        HashMap<String, ServerConClientThread> hm = ManageClientThread.hm;
        Iterator it = hm.keySet().iterator();
        while (it.hasNext()) {
            Message message = new Message();
            message.setCon(iAm);
            message.setMessageType(MessageType.message_ret_onLineFriend);

            //取出在线人的ID
            try {
                String onLineUserID = it.next().toString();
                ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.getClientThread(onLineUserID).s.getOutputStream());
                message.setGetter(onLineUserID);
                oos.writeObject(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        try {
            List<Message> messageList = new DbHandle(new DbConnect().getConnection()).getAndDeleteMessage(user.getUserID());
            if (messageList.size() != 0) {
                ServerConClientThread sc = ManageClientThread.getClientThread(user.getUserID());
                for (Message message : messageList) {
                    ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());
                    oos.writeObject(message);
                }
            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        try {
            while (true) {
                //这里该线程可以接受客户端的信息
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Message m = (Message) ois.readObject();
                System.out.println(m.getSender() + "给" + m.getGetter() + "说：" + m.getCon());

                //对从客户端取得消息进行类型判断，然后做相应的处理
                if (m.getMessageType().equals(MessageType.message_comm_mes)) {
                    //下面完成转发
                    //取得接收人的通讯线程
                    ServerConClientThread sc = ManageClientThread.getClientThread(m.getGetter());
                    if (sc != null) {
                        ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());
                        oos.writeObject(m);
                    } else {
                        System.out.println("对方离线，存入数据库");
                        new DbHandle(new DbConnect().getConnection()).addToMessage(m);
                    }
                } else if (m.getMessageType().equals(MessageType.message_get_onLineFriend)) {
                    //把在服务器的好友给客户端返回
                    String res = ManageClientThread.getAllOnLineUserID();
                    Message message = new Message();
                    message.setCon(res);
                    message.setGetter(m.getSender());
                    message.setMessageType(MessageType.message_ret_onLineFriend);

                    ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                    oos.writeObject(message);
                }
            }

        } catch (SocketException se) {
            ManageClientThread.deleteClientThread(user.getUserID());//哈希表中删除这个线程

            //得到所有在线的人的线程
            HashMap<String, ServerConClientThread> hm = ManageClientThread.hm;
            Iterator it = hm.keySet().iterator();
            while (it.hasNext()) {
                Message message = new Message();
                message.setCon(user.getUserID());
                message.setMessageType(MessageType.message_ret_offLineFriend);

                //取出在线人的ID
                try {
                    String onLineUserID = it.next().toString();
                    ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.getClientThread(onLineUserID).s.getOutputStream());
                    message.setGetter(onLineUserID);
                    oos.writeObject(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            System.out.println("聊天已退出");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
