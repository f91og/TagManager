package yuxue.tagmanager.util;

import android.app.Application;
import android.content.Context;

/**
 * ȫ�ֻ��Context
 * @author yuxue
 */
public class MyApplication extends Application{
	
	private static Context context;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		context=getApplicationContext();
	}
	
	public static Context getContext(){
		return context;
	}
}
