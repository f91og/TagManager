package yuxue.tagmanager.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import yuxue.tagmanager.R;
import yuxue.tagmanager.adapter.ContactAdapter;
import yuxue.tagmanager.db.SqliteDBHelper;
import yuxue.tagmanager.model.Contact;
import yuxue.tagmanager.util.MyApplication;
import yuxue.tagmanager.util.TinyPY;

/**
 * Created by yuxue on 2017/3/5.
 */

public class ContactFragment extends Fragment {

    public static final String TAG="ContactFragment";
    private SqliteDBHelper dbHelper = new SqliteDBHelper(
            MyApplication.getContext(), "TagManager.db", null, 1);
    private SQLiteDatabase db = dbHelper.getWritableDatabase();

    private List<Contact> contacts=new ArrayList<>();
    private ArrayList<String> firstList = new ArrayList<>();//字母索引集合
    private HashSet<String> set = new HashSet<>();//中间临时集合
    private ContactAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return(inflater.inflate(R.layout.fragment_contact,container,false));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_contact);
        //设置布局
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //初始化数据
        //避免ViewPager滚动而带来的重复赋值问题
        if(!(contacts.size()>0)){
            initData();
        }
        adapter = new ContactAdapter(contacts);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        adapter.notifyDataSetChanged();
    }

    private void initData(){
        contacts.clear();
        if (contacts == null)
            contacts = new ArrayList<>();
        //是否有非字母数据(拼音首字符不在26个字母范围当中)
        boolean hasIncognizance = false;
        //装载非字母数据的结合
        ArrayList<Contact> incognizanceList = new ArrayList<>();
        //从数据库取出记录,
        Cursor cursor = db.query("Contact", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(1);
                String phone1 = cursor.getString(2);
                String phone2=cursor.getString(3);
                String mail=cursor.getString(4);
                byte[] contactIcon=cursor.getBlob(5);
                String description=cursor.getString(6);
                Contact contact=new Contact(name,phone1,phone2,mail,contactIcon,description);
                contact.pinYin = TinyPY.toPinYin(contact.getName());
                contact.firstPinYin = TinyPY.firstPinYin(contact.pinYin);
                if (!TextUtils.isEmpty(contact.firstPinYin)) {
                    char first = contact.firstPinYin.charAt(0);
                    //A(65), Z(90), a(97), z(122) 根据数据的类型分开装进集合
                    if (first < 'A' || (first > 'Z' && first < 'a') || first > 'z') {
                        //非字母
                        contact.firstPinYin = "#";
                        //标记含有#集合
                        hasIncognizance = true;
                        //添加数据到#集合
                        incognizanceList.add(contact);
                    } else {
                        //字母索引(set可以去重复)
                        set.add(contact.firstPinYin);
                        //添加数据到字母a-z集合
                        contacts.add(contact);
                    }
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        //对contact集合数据排序
        Collections.sort(contacts);

        //把排序后的字母顺序装进字母索引集合
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            firstList.add(iterator.next());
        }
        Collections.sort(firstList);

        //最后加上#
        if (hasIncognizance) {
            //把#装进索引集合
            firstList.add("#");
            //把非字母的contact数据装进数据集合
            contacts.addAll(incognizanceList);
        }
        //清空中间缓存集合
        incognizanceList.clear();
        set.clear();
    }
}
