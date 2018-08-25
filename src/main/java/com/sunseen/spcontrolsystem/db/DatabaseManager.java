package com.sunseen.spcontrolsystem.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.sunseen.spcontrolsystem.model.SystemConfig;
import com.sunseen.spcontrolsystem.model.SystemRunModel;
import com.sunseen.spcontrolsystem.model.UserInfoModel;
import com.sunseen.spcontrolsystem.utils.LogMsg;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class DatabaseManager {

    private DatabaseHelper dbHelper = null;
    private Context mContext;
    private SQLiteDatabase db = null;
    private ArrayList<UserInfoModel> listUserInfo = null;

    public DatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        this.mContext = context;
    }

    public void insertUserInfo(UserInfoModel userInfo) {
        LogMsg.printDebugMsg("insertUserInfo");
        if (db == null) {
            db = dbHelper.getWritableDatabase();
        }
        String sql = "insert into userInfo(user_name,pwd,user_id,last_login,power) values(?,?,?,?,?);";
        try {
            String user_name = new String(userInfo.getUser_name().getBytes(), "GB2312");
            db.execSQL(sql, new Object[]{user_name,
                    userInfo.getPwd(),
                    userInfo.getUser_id(),
                    userInfo.getLast_login(),
                    userInfo.getUser_power()});
        } catch (SQLException e) {
//            closeDB(db);
            updateUserInfo(userInfo);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
//            closeDB(db);
        }
    }

    public void updateUserInfo(UserInfoModel userInfo) {
        LogMsg.printDebugMsg("updateUserInfo");
        if (db == null) {
            db = dbHelper.getWritableDatabase();
        }
        String sql = "update userInfo set user_name=?,pwd=?,last_login=?,power=? where user_id='"
                + userInfo.getUser_id() + "';";
//        "update userInfo set user_name=" + "'"+ user_name +"'," +
//                "pwd=" +"'"+ userInfo.getPwd() +"'," +
//                "last_login=" + "'"+ userInfo.getLast_login() +"'," +
//                "power=" + "'"+ userInfo.getUser_power() +"'," +
//                " where user_id="
//                + userInfo.getUser_id();
        LogMsg.printDebugMsg("sql == " + sql);
        try {
            String user_name = new String(userInfo.getUser_name().getBytes(), "GB2312");
            db.execSQL(sql, new Object[]{user_name,
                    userInfo.getPwd(),
                    userInfo.getLast_login(),
                    userInfo.getUser_power()});
        } catch (SQLException e) {
            LogMsg.printErrorMsg("updateUserInfo SQLException");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
//            closeDB(db);
        }
    }

    public void deleteUserInfo(String user_id) {
        if (db == null) {
            db = dbHelper.getWritableDatabase();
        }
        String sql = "delete from userInfo where user_id='" + user_id + "';";
        try {
            db.execSQL(sql);
        } catch (SQLException e) {
            LogMsg.printErrorMsg("deleteUserInfo SQLException");
        } finally {
//            closeDB(db);
        }
    }

    public ArrayList<UserInfoModel> queryUserInfo() {
        if (db == null) {
            db = dbHelper.getWritableDatabase();
        }
        if (listUserInfo == null) {
            listUserInfo = new ArrayList<>();
        }
        String sql = "select * from userInfo;";
        try {
            Cursor mCursor = db.rawQuery(sql, null);
            while (mCursor.moveToNext()) {
//			String user_name = mCursor.getString(mCursor.getColumnIndex("user_name"));
                byte[] val = mCursor.getBlob(mCursor.getColumnIndex("user_name"));
                String user_name = new String(val, "GB2312");
                String user_id = mCursor.getString(mCursor.getColumnIndex("user_id"));
                String pwd = mCursor.getString(mCursor.getColumnIndex("pwd"));
                String last_login = mCursor.getString(mCursor.getColumnIndex("last_login"));
                int user_power = mCursor.getInt(mCursor.getColumnIndex("power"));
                if (SystemRunModel.LOGIN_USER_INFO.getUser_power() > user_power)
                {
                    UserInfoModel userInfo = new UserInfoModel(user_name, pwd, user_power, last_login, user_id);
                    listUserInfo.add(userInfo);
                }
            }
        } catch (SQLException e) {
            LogMsg.printErrorMsg("queryUserInfo SQLException" + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
//            closeDB(db);
        }
        return listUserInfo;
    }

    public UserInfoModel queryUserInfoForUserName(String name) {
        if (db == null) {
            db = dbHelper.getWritableDatabase();
        }
        String sql = "select * from userInfo where user_name='" + name + "' or user_id='"+ name +"';";
        UserInfoModel userInfo = null;
        try {
            Cursor mCursor = db.rawQuery(sql, null);
            while (mCursor.moveToNext()) {
//			String user_name = mCursor.getString(mCursor.getColumnIndex("user_name"));
                byte[] val = mCursor.getBlob(mCursor.getColumnIndex("user_name"));
                String user_name = new String(val, "GB2312");
                String user_id = mCursor.getString(mCursor.getColumnIndex("user_id"));
                String pwd = mCursor.getString(mCursor.getColumnIndex("pwd"));
                String last_login = mCursor.getString(mCursor.getColumnIndex("last_login"));
                int user_power = mCursor.getInt(mCursor.getColumnIndex("power"));
                userInfo = new UserInfoModel(user_name, pwd, user_power, last_login, user_id);
            }
        } catch (SQLException e) {
            LogMsg.printErrorMsg("queryUserInfo SQLException" + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
//            closeDB(db);
        }
        return userInfo;
    }


    public void insertSystemConfig(SystemConfig config) {
        if (db == null) {
            db = dbHelper.getWritableDatabase();
        }
        String sql = "insert into systemConfig(name,value) values(?,?);";
        try {
            String name = new String(config.getName().getBytes(), "GB2312");
            String value = new String(config.getValue().getBytes(), "GB2312");
            db.execSQL(sql, new Object[]{
                    name, value
            });
        } catch (SQLException e) {
            updateSystemConfig(config);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void updateSystemConfig(SystemConfig config) {
        if (db == null) {
            db = dbHelper.getWritableDatabase();
        }
        String sql = "update systemConfig set value=? where name='"
                + config.getName() + "';'";
        try {
            String value = new String(config.getValue().getBytes(), "GB2312");
            db.execSQL(sql, new Object[]{value});
        } catch (SQLException e) {
            LogMsg.printErrorMsg("updateUserInfo SQLException");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
//            closeDB(db);
        }
    }

    public synchronized String queryConfigValue(String name, String defult) {
        if (db == null) {
            db = dbHelper.getWritableDatabase();
        }
        if (listUserInfo == null) {
            listUserInfo = new ArrayList<>();
        }
        String sql = "select * from systemConfig where name='" + name + "';";
        String value = "-1";
        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery(sql, null);
            while (mCursor.moveToNext()) {
                value = mCursor.getString(mCursor.getColumnIndex("value"));
            }
            mCursor.close();
        } catch (SQLException e) {
            LogMsg.printErrorMsg("queryUserInfo SQLException" + e.getMessage());
        } finally {
//            closeDB(db);
            if (mCursor != null) {
                mCursor.close();
            }
        }
        return value;
    }

    public void closeDB() {
        if (dbHelper != null) {
            dbHelper.close();
        }
        if (db != null) {
            db.close();
        }
    }
}
