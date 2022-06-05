package com.henu.eltfood.DataSystem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImgOperation {
    public Context context = null;
    private File sdDir = null;
    private Bitmap query_bitmap = null;
    public ImgOperation(Context context) {
        this.context = context;
    }

    public String GetSdPath() {
        if (sdDir == null) {
            boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);// 判断sd卡是否存在
            if (sdCardExist) {
                if (Build.VERSION.SDK_INT >= 29) {
//Android10之后
                    sdDir = context.getExternalFilesDir(null);
                }
                else {
                    sdDir = Environment.getExternalStorageDirectory();// 获取SD卡根目录
                }
            }
            else {
                sdDir = Environment.getRootDirectory();// 获取跟目录
            }
        }
        return sdDir.toString();
    }
    public void WriteImg(String file_path,byte img[]){
        try {
            FileOutputStream fos = new FileOutputStream(file_path);
            fos.write(img);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap GetBitmap(String img_name) {
        String file_path = new String(GetSdPath() + "/img/" + img_name + ".jpeg");
        System.out.println(file_path);
        File file = new File(file_path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        System.out.println("开始在数据库中查询图片");

                        Connection con = MySQLConnections.getConnection();
                        String sql = "select * from img where img_name=?";
                        PreparedStatement stmt = con.prepareStatement(sql);
                        con.setAutoCommit(false);
                        stmt.setString(1, img_name);
                        stmt.addBatch();

                        con.commit();
                        ResultSet rs = stmt.executeQuery();

                        System.out.println("开始将图片缓存到本地");

                        while (rs.next()){
                            byte[] buff = rs.getBytes("img");
                            WriteImg(file_path,buff);
                            query_bitmap = BitmapFactory.decodeByteArray(buff, 0, buff.length);
                        }
                        con.close();
                        stmt.close();
                        rs.close();

                        System.out.println("数据库查询图片结束");

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            };
            thread.start();
            int cnt = 0;
            while (thread.isAlive()) cnt = cnt + 1;
        }
        else{
            System.out.println("从本地文件获取图片");

            query_bitmap = BitmapFactory.decodeFile(file_path);
        }
        return query_bitmap;
    }

    public void SaveToLocalAndSQ(String source_file_path, String file_name){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeFile(source_file_path);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte buff[] = baos.toByteArray();
        String file_path = new String(GetSdPath() + "/img/" + file_name + ".jpeg");
        WriteImg(file_path,buff);
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println("开始向数据库中插入数据");

                    Connection con = MySQLConnections.getConnection();
                    String sql = "insert into img(img_name,img,img_description) values(?,?,?)";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    // 关闭事务自动提交 ,这一行必须加上
                    con.setAutoCommit(false);
                    stmt.setString(1,file_name);
                    stmt.setBytes(2,buff);
                    stmt.setString(3,"图片描述");
                    stmt.addBatch();
                    stmt.executeBatch();
                    con.commit();
                    con.close();
                    stmt.close();

                    System.out.println("数据插入结束");

                }
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        };
        thread.start();
    }

    public void InsertDatabase(String ImgName, int Img, String ImgDescription) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), Img);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte buff[] = baos.toByteArray();
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println("开始向数据库中插入数据");

                    Connection con = MySQLConnections.getConnection();
                    String sql = "insert into img(img_name,img,img_description) values(?,?,?)";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    // 关闭事务自动提交 ,这一行必须加上
                    con.setAutoCommit(false);
                    stmt.setString(1,ImgName);
                    stmt.setBytes(2,buff);
                    stmt.setString(3,ImgDescription);
                    stmt.addBatch();
                    stmt.executeBatch();
                    con.commit();
                    con.close();
                    stmt.close();

                    System.out.println("数据插入结束");

                }
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        };
        thread.start();

        return;
    }

    public static synchronized void Update(String old_img_name,String new_img_name){
        new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println("开始更新数据");

                    Connection con = MySQLConnections.getConnection();
                    String sql = "update img set img_name=? where img_name=?";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    // 关闭事务自动提交 ,这一行必须加上
                    con.setAutoCommit(false);
                    stmt.setString(1,new_img_name);
                    stmt.setString(2,old_img_name);
                    stmt.addBatch();
                    stmt.executeBatch();
                    con.commit();
                    con.close();
                    stmt.close();

                    System.out.println("更新结束");
                }
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.start();
    }

    public static synchronized void Delete(String img_name){
        new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println("开始删除数据");

                    Connection con = MySQLConnections.getConnection();
                    String sql = "delete from img where img_name=?";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    // 关闭事务自动提交 ,这一行必须加上
                    con.setAutoCommit(false);
                    stmt.setString(1,img_name);
                    stmt.addBatch();
                    stmt.executeBatch();
                    con.commit();

                    con.close();
                    stmt.close();

                    System.out.println("删除结束");

                }
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.start();
    }
}
