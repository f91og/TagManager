package yuxue.tagmanager.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yuxue on 2017/3/9.
 */

/*安卓中的Parcelable序列化接口如何使用？
* java中的Comparable如何使用？*/
public class Contact implements Parcelable, Comparable<Contact> {

    private String name;
    private String phone1;
    private String phone2;
    private String mail;
    private byte[] contactIcon;
    private String description;//描述
    public String pinYin;
    public String firstPinYin;

    public Contact() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phone1);
        dest.writeString(phone2);
        dest.writeString(mail);
        dest.writeByteArray(contactIcon);
        dest.writeString(description);
        dest.writeString(pinYin);
        dest.writeString(firstPinYin);
    }

    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        public Contact createFromParcel(Parcel in) {
            Contact contact=new Contact();
            contact.name = in.readString();
            contact.phone1 = in.readString();
            contact.phone2 = in.readString();
            contact.mail = in.readString();
            contact.contactIcon = in.createByteArray();
            contact.description = in.readString();
            contact.pinYin = in.readString();
            contact.firstPinYin = in.readString();
            return contact;
        }

        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public Contact(String name, String phone1, String phone2, String comment) {
        this.name = name;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.description = comment;
    }

    public Contact(String name, String phone1, String phone2, String mail, byte[] contactIcon, String description) {
        this.name = name;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.mail = mail;
        this.contactIcon = contactIcon;
        this.description = description;
    }

    public Contact(String name, String phone1, String phone2, String mail, byte[] contactIcon, String description, String pinYin, String firstPinYin) {
        this.name = name;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.mail = mail;
        this.contactIcon = contactIcon;
        this.description = description;
        this.pinYin = pinYin;
        this.firstPinYin = firstPinYin;
    }

    public byte[] getContactIcon() {
        return contactIcon;
    }

    public void setContactIcon(byte[] contactIcon) {
        this.contactIcon = contactIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getComment() {
        return description;
    }

    public void setComment(String comment) {
        this.description = comment;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public int compareTo(Contact another) {
        return firstPinYin.toUpperCase().compareTo(another.firstPinYin.toUpperCase());
    }
}
