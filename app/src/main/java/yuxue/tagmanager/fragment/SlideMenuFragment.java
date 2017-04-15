package yuxue.tagmanager.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import yuxue.tagmanager.R;
import yuxue.tagmanager.activity.SlideMenuActivity;
import yuxue.tagmanager.adapter.SlideMenuListAdapter;
import yuxue.tagmanager.util.MyApplication;

/**
 * Created by yuxue on 2017/3/5.
 */

public class SlideMenuFragment extends Fragment implements OnItemClickListener{

    private ListView lv;
    private SlideMenuListAdapter adapter;
    private String[] data = { "关于", "使用帮助", "密码设置", "数据备份", "数据同步"};
    private TextView quit;
    private SharedPreferences pref;
    private String password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_slide, container, false);
    }

    // 如何在Fragment中拿到布局中各种view控件
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        quit = (TextView) view.findViewById(R.id.tv_exit);
        lv = (ListView) view.findViewById(R.id.elv_slide_menu);// findViewById()方法是View找自己里面控件用的
        adapter = new SlideMenuListAdapter(MyApplication.getContext(),
                android.R.layout.simple_list_item_1, data);
        lv.setAdapter(adapter);
        pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        lv.setOnItemClickListener(this);
        quit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        final EditText pd = new EditText(getActivity());
        dialog.setTitle("请输入密码");
        dialog.setIcon(android.R.drawable.ic_dialog_info);
        dialog.setView(pd);
        switch (position) {
            case 0:
			/* 关于 */
                break;
            case 1:
			/* 使用帮助 */
                break;
            case 2:
                password = pref.getString("password", null);
                if (password == null) {
                    Intent intent = new Intent(getActivity(),
                            SlideMenuActivity.class);
                    intent.putExtra("flag", "setPassword");
                    startActivity(intent);
                } else {
                    dialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    String input = pd.getText().toString();
                                    if (password.equals(input)) {
                                        // 向Activity跳转并传送数据
                                        Intent intent = new Intent(getActivity(),
                                                SlideMenuActivity.class);
                                        intent.putExtra("flag", "setPassword");
                                        startActivity(intent);
                                    } else {
                                        new AlertDialog.Builder(getActivity())
                                                .setTitle("提示").setMessage("密码错误")
                                                .setPositiveButton("确定", null)
                                                .show();
                                    }
                                }
                            }).setNegativeButton("取消", null).show();
                }
                break;
            case 3:
                password = pref.getString("password", null);
                if(password == null){
                    new AlertDialog.Builder(getActivity())
                            .setTitle("提示").setMessage("没有密码")
                            .setPositiveButton("确定", null).show();
                    break;
                }
                dialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                String input = pd.getText().toString();
                                if (password.equals(input)) {
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.remove("password");
                                    editor.apply();
                                    password = null;
                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("提示").setMessage("取消密码成功，重启应用生效")
                                            .setPositiveButton("确定", null).show();
                                } else {
                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("提示").setMessage("密码错误")
                                            .setPositiveButton("确定", null).show();
                                }
                            }
                        }).setNegativeButton("取消", null).show();
                break;
            case 4:
			/* 忘记密码 */
                break;
            case 5:
			/* 备份数据
			 * 安卓的网络通信
			 * 主线程里不许访问网络*/
//                new BackUpByAsync().execute(getActivity());
//                //new Thread(new DataBackUp()).start();
        }
    }
}
