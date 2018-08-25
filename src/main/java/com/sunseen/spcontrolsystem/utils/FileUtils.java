package com.sunseen.spcontrolsystem.utils;

import android.os.Environment;

import com.sunseen.spcontrolsystem.activity.SelfDialogActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class FileUtils {

    //    private static String SEPARATOR = System.getProperty("line.separator");
    public static final String PATH = getSDPath() + "/" + "SwimmingPoolSystemFiles/";
//    public static final String configPath = PATH + "/config/config.ini";

    /**
     * 用于保存日志
     * log需要带\n
     */
    public synchronized static void LogSava(String log, String saveFile) {
        File file = new File(saveFile);
        OutputStream outputStream = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            log = log + File.separator;
            outputStream = new FileOutputStream(file, true);
            outputStream.write(log.getBytes("utf-8"));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param path
     * @return 删除path中的所有文件
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * @param fileName
     * @return 删除fileName文件
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            Boolean succeedDelete = file.delete();
            if (succeedDelete) {
                LogMsg.printDebugMsg("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                LogMsg.printDebugMsg("删除单个文件" + fileName + "失败！");
                return true;
            }
        } else {
            LogMsg.printDebugMsg("删除单个文件" + fileName + "失败！");
            return false;
        }
    }

    /**
     * 删除文件夹
     *
     * @param folderPath 文件夹完整绝对路径
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param path
     * @return 获取path目录下的文件名集合
     */
    public static ArrayList<String> getFileName(String path) {
        File file = new File(path);
        if (!file.exists()) {
            LogMsg.printDebugMsg(path + " not exists");
            return null;
        }
        ArrayList<String> list = new ArrayList<>();
        File[] files = file.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File fs = files[i];
                if (fs.isDirectory()) {
                    System.out.println(fs.getName() + " [isDirectory]");
                } else {
                    list.add(fs.getName());
                }
            }
        } else {
            LogMsg.printDebugMsg("listFiles is null");
        }
        return list;
    }

    /**
     * @return 获取SDCard路径
     */
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

    public static void createFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

