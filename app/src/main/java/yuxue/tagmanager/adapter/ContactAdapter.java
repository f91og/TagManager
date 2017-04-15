package yuxue.tagmanager.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yuxue.tagmanager.R;
import yuxue.tagmanager.activity.ContactAddActivity;
import yuxue.tagmanager.activity.ContactContentActivity;
import yuxue.tagmanager.model.Contact;
import yuxue.tagmanager.util.MyApplication;

/**
 * Created by yuxue on 2017/3/9.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    public static final int SHOW_HEADER_VIEW = 1;   //显示header
    public static final int DISMISS_HEADER_VIEW = 2;//隐藏header

    private List<Contact> contacts;

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView header;
        ImageView pic;
        TextView name;

        public ContactViewHolder(View view) {
            super(view);
            itemView = view;
            header = (TextView) view.findViewById(R.id.tv_header);
            pic = (ImageView) view.findViewById(R.id.tv_pic);
            name = (TextView) view.findViewById(R.id.tv_name);
        }
    }

    public ContactAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public void updateData(ArrayList<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    /*用于创建ViewHolder实例*/
    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        final ContactViewHolder contactViewHolder = new ContactViewHolder(view);

        //RecyclerView的点击事件是注册给子项具体的View的
        //注册List里item的点击事件
        contactViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ContactContentActivity.class);
                //如何在这里通过点击事件将contact传给另一个activity,intent里如何传一个对象呢？
                Contact contact=contacts.get(contactViewHolder.getAdapterPosition());
                intent.putExtra("contact", contact);
                v.getContext().startActivity(intent);
                //利用代码来控制activity的切入切出动画
                ((AppCompatActivity) v.getContext()).
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        contactViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                View view=View.inflate(v.getContext(),R.layout.dialog_del_edit,null);
                final AlertDialog dialog = builder.create();
                TextView editTv=(TextView)view.findViewById(R.id.tv_edit);
                TextView delTv=(TextView)view.findViewById(R.id.tv_del);
                editTv.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(v.getContext(),ContactAddActivity.class);
                        intent.putExtra("contact", contacts.get(contactViewHolder.getAdapterPosition()));
                        v.getContext().startActivity(intent);
                        ((Activity) MyApplication.getContext()).
                                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    }
                });
                delTv.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                    }
                });
                dialog.setView(view);
                dialog.show();
                return true;
            }
        });

        return contactViewHolder;
    }

    /*在每个子项被滚动到屏幕内时执行*/
    @Override
    public void onBindViewHolder(final ContactViewHolder holder, final int position) {
        if (contacts == null || contacts.size() <= 0)
            return;
        final Contact contact = contacts.get(position);
        holder.header.setText(contact.firstPinYin);
        holder.name.setText(contact.getName());
        //如果从数据源得到的联系人头像是空的话则显示默认联系人头像
        byte[] contactIcon = contact.getContactIcon();
        if (contactIcon == null) {
            holder.pic.setImageResource(R.drawable.icon_contact);
        }
        //如果不为空的话则需将byte[]数组转化为drawable
        else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(contactIcon, 0, contactIcon.length);
            holder.pic.setImageDrawable(new BitmapDrawable(bitmap));
        }
        if (position == 0) {
            holder.header.setText(contact.firstPinYin);
            holder.header.setVisibility(View.VISIBLE);
        } else {
            if (!TextUtils.equals(contact.firstPinYin, contacts.get(position - 1).firstPinYin)) {
                holder.header.setVisibility(View.VISIBLE);
                holder.header.setText(contact.firstPinYin);
                holder.itemView.setTag(SHOW_HEADER_VIEW);
            } else {
                holder.header.setVisibility(View.GONE);
                holder.itemView.setTag(DISMISS_HEADER_VIEW);
            }
        }
        holder.itemView.setContentDescription(contact.firstPinYin);
    }

    @Override
    public int getItemCount() {
        return (contacts == null || contacts.size() <= 0) ? 0 : contacts.size();
    }

}
