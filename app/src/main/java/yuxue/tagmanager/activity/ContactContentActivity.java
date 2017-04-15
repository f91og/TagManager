package yuxue.tagmanager.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import yuxue.tagmanager.R;
import yuxue.tagmanager.model.Contact;

/**
 * Created by yuxue on 2017/3/16.
 */

public class ContactContentActivity extends AppCompatActivity implements View.OnClickListener{

    static  final String TAG="ContactContentActivity";

    private ImageButton backImb;
    private ImageButton editImb;
    private ImageView iconContactImg;
    private TextView nameTv;
    private TextView descriptionTv;
    private TextView phone1Tv;
    private TextView phone2Tv;
    private TextView mailTv;
    private Button sendMesBtn;
    private Button vedioTalBtn;

    private Contact contact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_content);
        initialView();
        initialData();
    }

    public void initialView(){
        backImb=(ImageButton)findViewById(R.id.imb_back);
        editImb=(ImageButton)findViewById(R.id.imb_edit);
        iconContactImg=(ImageView)findViewById(R.id.img_icon_contact);
        nameTv=(TextView)findViewById(R.id.tv_name);
        descriptionTv=(TextView)findViewById(R.id.tv_description);
        phone1Tv=(TextView)findViewById(R.id.tv_phone1);
        phone2Tv=(TextView)findViewById(R.id.tv_phone2);
        mailTv=(TextView)findViewById(R.id.tv_mail);
        sendMesBtn=(Button)findViewById(R.id.btn_send_mes);
        vedioTalBtn=(Button)findViewById(R.id.btn_vedio_talk);
        backImb.setOnClickListener(this);
        editImb.setOnClickListener(this);
        sendMesBtn.setOnClickListener(this);
        vedioTalBtn.setOnClickListener(this);
    }

    public void initialData(){
        Intent intent=getIntent();
        contact=(Contact)intent.getParcelableExtra("contact");
        byte[] contactIcon = contact.getContactIcon();
        if(contactIcon != null){
            /*将字节数组转为Bitmap*/
            Bitmap bitmap = BitmapFactory.decodeByteArray(contactIcon, 0, contactIcon.length);
            iconContactImg.setImageDrawable(new BitmapDrawable(bitmap));
        }
        nameTv.setText(contact.getName());
        descriptionTv.setText(contact.getComment());
        phone1Tv.setText(contact.getPhone1());
        phone2Tv.setText(contact.getPhone2());
        mailTv.setText(contact.getMail());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imb_back:
                onBackPressed();
                break;
            case R.id.imb_edit:
                //跳转到编辑界面
                Intent intent=new Intent(this,ContactAddActivity.class);
                intent.putExtra("contact", contact);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.btn_send_mes:
                Toast.makeText(this,"功能尚未完成，请期待下个版本，(○’ω’○) ",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_vedio_talk:
                Toast.makeText(this,"功能尚未完成，请期待下个版本，(○’ω’○) ",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }

}
