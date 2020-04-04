package server;

import server.entity.Commodity;
import server.utility.SendList;
import server.db.DbConnect;


import java.io.*;
import java.net.*;
import java.util.List;


public class ServeOne implements Runnable {
    Socket socket;
    OutputStream os;
    DataOutputStream dos;
    InputStream ins;
    DataInputStream dis;

    String command;

    public ServeOne(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            os = socket.getOutputStream();
            dos = new DataOutputStream(os);

            ins = socket.getInputStream();
            dis = new DataInputStream(ins);

            dos.writeUTF("你已经连接到服务器！");
            command = dis.readUTF(dis);//从client获取操作

            if (command.equals("Login"))
                this.Login();
            else if(command.equals("Register"))
                this.Register();
            else if(command.equals("GetGoodsListLen"))
                this.getGoodsListLen();
            else if(command.equals("GetGoodsList"))
                this.getGoodsList();
            else if(command.equals("AddGoods"))
                this.AddGoods();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void getGoodsList() throws Exception {
        try{

            List<Commodity> list = DbConnect.getGoodsList();
            //传输对象
            SendList sl = new SendList();
            sl.setSendlist(list);

            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(sl);

        }finally {
            if (dis != null)
                dis.close();
            if (ins != null)
                ins.close();
            if (dos != null)
                dos.close();
            if (os != null)
                os.close();
        }
    }

    private void getGoodsListLen() throws Exception {
        try{
            int count = DbConnect.getGoodsListLength();
            dos.writeInt(count);


        }finally {
            if (dis != null)
                dis.close();
            if (ins != null)
                ins.close();
            if (dos != null)
                dos.close();
            if (os != null)
                os.close();
        }


    }

    private void Login(){
        try {
            //登陆操作
            String username;
            String password;

            username = dis.readUTF(dis);
            System.out.println("获取到username：" + username);

            password = dis.readUTF(dis);
            System.out.println("获取到password：" + password);

            //连接数据库查询得到结果rs
            Boolean isSuccess = DbConnect.login_select(username,password);
            if(isSuccess)
                dos.writeUTF("success");
            else
                dos.writeUTF("fail");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (dis != null)
                    dis.close();
                if (ins != null)
                    ins.close();
                if (dos != null)
                    dos.close();
                if (os != null)
                    os.close();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    private void Register() throws Exception {
        try{
            String username;
            String password;

            username = dis.readUTF(dis);
            password = dis.readUTF(dis);

            //判断用户是否已经存在
            Boolean isExist;
            isExist = DbConnect.register_select_isexist(username);
            if(isExist){
                dos.writeUTF("isExist");
            }

            //开始注册
            DbConnect.register(username,password);
            dos.writeUTF("success");
        }finally {
            if (dis != null)
                dis.close();
            if (ins != null)
                ins.close();
            if (dos != null)
                dos.close();
            if (os != null)
                os.close();
        }


    }

    private void AddGoods() throws Exception{
        try{
            String userID;
            String name;
            double price;
            int nums;
            int isAuction;

            userID=dis.readUTF(dis);
            name=dis.readUTF(dis);
            price = dis.readDouble();
            nums= dis.readInt();
            isAuction = dis.readInt();

            DbConnect.addGoods(userID,price,name,nums,isAuction);
            dos.writeInt(1);
        }finally {
            if (dis != null)
                dis.close();
            if (ins != null)
                ins.close();
            if (dos != null)
                dos.close();
            if (os != null)
                os.close();
        }
    }
}