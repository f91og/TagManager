package yuxue.tagmanager.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;

import yuxue.tagmanager.R;
import yuxue.tagmanager.db.SqliteDBHelper;
import yuxue.tagmanager.model.Contact;
import yuxue.tagmanager.util.MyApplication;

public class ContactAddActivity extends AppCompatActivity implements View.OnClickListener {

    static final String TAG="ContactAddActivity";
    private ScrollView scrollView;
    private Button cancBtn;
    private Button confBtn;
    private ImageView porimg;

    private EditText nameEt;
    private EditText descriptionEt;
    private EditText phone1Et;
    private EditText phone2Et;
    private EditText mailEt;
    private TextView titleTv;

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    public static final int AFTER_CROP = 3;

    private SqliteDBHelper dbHelper = new SqliteDBHelper(
            MyApplication.getContext(), "TagManager.db", null, 1);
    private SQLiteDatabase db = dbHelper.getWritableDatabase();

    Uri imageUri;
    Bitmap head;
    private Contact contact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add_edit);
        initView();
    }

    private void initView() {
        //去掉ScrollView的滚动条，使得输入法弹出时只有中间部分上移
        scrollView = (ScrollView) findViewById(R.id.scroll_content);
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setHorizontalScrollBarEnabled(false);
        cancBtn = (Button) findViewById(R.id.btn_cancel);
        cancBtn.setOnClickListener(this);
        confBtn = (Button) findViewById(R.id.btn_confirm);
        confBtn.setOnClickListener(this);
        porimg = (ImageView) findViewById(R.id.img_portrait);
        porimg.setOnClickListener(this);
        nameEt=(EditText)findViewById(R.id.et_name);
        descriptionEt=(EditText)findViewById(R.id.et_description);
        phone1Et=(EditText)findViewById(R.id.et_phone1);
        phone2Et=(EditText)findViewById(R.id.et_phone2);
        mailEt=(EditText)findViewById(R.id.et_mail);
        titleTv=(TextView)findViewById(R.id.tv_title);
        Intent intent=getIntent();
        contact=intent.getParcelableExtra("contact");
        if(contact!=null){
            byte[] contactIcon=contact.getContactIcon();
            if(contactIcon!=null){
                Bitmap bitmap= BitmapFactory.decodeByteArray(contactIcon,0,contactIcon.length);
                porimg.setImageDrawable(new BitmapDrawable(bitmap));
            }
            nameEt.setText(contact.getName());
            phone1Et.setText(contact.getPhone1());
            phone2Et.setText(contact.getPhone2());
            descriptionEt.setText(contact.getComment());
            mailEt.setText(contact.getMail());
        }
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("提示").setPositiveButton("确定",null).setMessage("保存成功");
        switch (v.getId()) {
            case R.id.btn_cancel:
                onBackPressed();
                break;
            case R.id.btn_confirm:
                if(!nameEt.getText().toString().equals("")){
                    ContentValues values=new ContentValues();
                    values.put("name",nameEt.getText().toString());
                    values.put("phone1",phone1Et.getText().toString());
                    values.put("phone2",phone2Et.getText().toString());
                    values.put("mail",mailEt.getText().toString());
                    values.put("description",descriptionEt.getText().toString());
                    if(head!=null){
                        /*如何想数据库存入图片*/
                        ByteArrayOutputStream baos=new ByteArrayOutputStream();
                        head.compress(Bitmap.CompressFormat.JPEG,85,baos);
                        byte[] bytes=baos.toByteArray();
                        values.put("contactIcon",bytes);
                    }
                    if(db.insert("Contact", null, values)>0){
                        nameEt.setText("");
                        phone1Et.setText("");
                        phone2Et.setText("");
                        mailEt.setText("");
                        descriptionEt.setText("");
                    }else{
                        dialog.setMessage("添加通讯录失败，可能添加了重复的通讯人");
                    }
                }else{
                    dialog.setMessage("没有输入通讯人姓名");
                }
                dialog.show();
                break;
            case R.id.img_portrait:
                //选择头像图片逻辑，从相册中选择图片或者拍照，涉及到动态权限处理,无论是从相册选取还是拍照首先要获得是图片的uri
                showTypeDialog();
                break;
        }
    }

    private void showTypeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        //将Dialog的点击事件分发到Dialog绑定的视图上
        View view = View.inflate(this, R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检查是否具有运行时权限
                if (ContextCompat.checkSelfPermission(ContactAddActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ContactAddActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    //打开相册
                    openAlbum();
                }
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ContactAddActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ContactAddActivity.this,
                            new String[]{Manifest.permission.CAMERA}, 2);
                } else {
                    openCamera();
                }
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    private void openAlbum() {
        //打开相册intent
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        //访问系统图库,CHOOSE_PHOTO是请求码.startActivityForResult()方法之后执行的是onActivityResult()方法
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    private void openCamera() {
        //File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        File outputImage=new File(Environment.getExternalStorageDirectory(),"head.jpg");
        imageUri = Uri.fromFile(outputImage);
        //启动相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        //设置将拍下的照片的uri给imageUri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length > 0) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "读取相册权限被拒绝", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length > 0) {
                    openCamera();
                }else{
                    Toast.makeText(this, "打开摄像头被拒绝", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;

        }
    }

    /*
    requestCode 请求码，用这个来判断是哪个activity发送的请求
    resultCode 结果码，判断有没有请求成功
    data 请求返回的结果数据，竟然是个Intent类型....
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                //终于搞明白哪里错了，日狗了这是，把resultCode写成了requestCode
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图
                }
                break;
            //如果是打开相册的的intent的请求返回来的并且成功的话则将返回结果发送给裁剪程序
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());
                }
                break;
            case AFTER_CROP:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        porimg.setImageBitmap(head);
                    }
                }
            default:
                break;
        }
    }

    //调用图片剪辑程序
    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, AFTER_CROP);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
}
