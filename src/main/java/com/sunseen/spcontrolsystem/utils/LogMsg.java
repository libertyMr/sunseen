package com.sunseen.spcontrolsystem.utils;

import android.util.Log;

public class LogMsg {
	private static final String TAG = "sunseen_spControlSystem";
	public static void printDebugMsg(String msg)
	{
		Log.d(TAG, msg);
	}
	public static void printErrorMsg(String msg)
	{
		Log.e(TAG, msg);
	}
	public static void printInfoMsg(String msg)
	{
		Log.i(TAG, msg);
	}
}
