package yuxue.tagmanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by yuxue on 2017/3/14.
 */

public class SqliteDBHelper extends SQLiteOpenHelper{

    public static final String TAG="SqliteDBHelper";//tag,给Log日志输出用的
    public static final int DATABASE_VERSON=1;
    /* 创建通讯录表 */
    public static final String CREATE_CONTACT = "create table Contact ("
            +"_id INTEGER PRIMARY KEY autoincrement,"
            +"name text not null unique,"
            +"phone1 text,"
            +"phone2 text,"
            +"mail text,"
            +"contactIcon BLOB,"
            +"description text,"
            +"pinYin text,"
            + "firstPinYin text)";

    private Context mContext;

    public SqliteDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("TAG", "create table start...");
        db.execSQL(CREATE_CONTACT);
        String tempName[]={
                "android",
                "google",
                "windows mobile",
                "microsoft",
                "symbian",
                "nokia",
                "bada",
                "sumsung",
                "IBM",
                "QQ"
        };

        //创建临时的联系人
        for(int i=0;i<10;i++){
            //String sql="insert into contacts values(?,?,'15927614509','15927614509',?,'1986-11-03','杭州','lhb@163.com',?,null,null)";
            String sql="insert into Contact (name) values(?)";
            Object[] bindArgs={tempName[i]};
            db.execSQL(sql,bindArgs);
        }
        Log.i("TAG", "create table over...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
