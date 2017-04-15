package yuxue.tagmanager.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

public class SlideMenuListAdapter extends ArrayAdapter<String>{

	private int resourceId;
	/**
	 * @param context:当前上下文
	 * @param resource:ListView子项布局的id
	 * @param objects:要适配的数据
	 */
	public SlideMenuListAdapter(Context context, int resource, String[] objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}
	//getView()方法在每个子项被滚动到屏幕内的时候会被调用
//	public View getView(int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		
//	}
//	
}
