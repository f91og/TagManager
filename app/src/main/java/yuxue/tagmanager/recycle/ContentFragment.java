//package yuxue.tagmanager.recycle;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.view.GestureDetector;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//
//import yuxue.tagmanager.R;
//import yuxue.tagmanager.activity.MainActivity;
//import yuxue.tagmanager.fragment.ContactFragment;
//import yuxue.tagmanager.fragment.DiaryFragment;
//import yuxue.tagmanager.fragment.MemoFragment;
//import yuxue.tagmanager.fragment.TransactionFragment;
//
///**
// * Created by yuxue on 2017/3/5.
// */
//
//public class ContentFragment extends Fragment implements View.OnClickListener,RadioGroup.OnCheckedChangeListener,
//        GestureDetector.OnGestureListener {
//
//    private ImageButton leftSlideTrigger;
//    private ImageButton rightSideSearch;
//    private EditText searchEdit;
//
//    // 四个子Fragment
//    private ContactFragment contactFragment;
//    private MemoFragment memoFragment;
//    private DiaryFragment diaryFragment;
//    private TransactionFragment transactionFragment;
//    /** 四个子Fragment的Tag */
//    public static final String TAG_CONACT = "Contact";
//    public static final String TAG_MEMO = "Memo";
//    public static final String TAG_DIARY = "Diary";
//    public static final String TAG_TRANSACTION = "Transaction";
//
//    private RadioGroup rg_home_tab_menu;
//
//    private FragmentManager mFragmentManager;
//    private FragmentTransaction mFragmentTransaction;
//    private String hideTag;
//
//    // 滑动手势
//    private GestureDetector detector;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        final GestureDetector mGestureDetector = new GestureDetector(
//                getActivity(), this);
//        MainActivity.MyOnTouchListener myOnTouchListener = new MainActivity.MyOnTouchListener() {
//            @Override
//            public boolean onTouch(MotionEvent ev) {
//                boolean result = mGestureDetector.onTouchEvent(ev);
//                return result;
//            }
//        };
//
//        ((MainActivity) getActivity())
//                .registerMyOnTouchListener(myOnTouchListener);
//        return(inflater.inflate(R.layout.fragment_content,container,false));
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        initView(view);
//
//    }
//
//    void initView(View view){
//        leftSlideTrigger=(ImageButton)view.findViewById(R.id.ibtn_left);
//        rightSideSearch=(ImageButton)view.findViewById(R.id.ibtn_right);
//        searchEdit=(EditText)view.findViewById(R.id.edit_search);
//        leftSlideTrigger.setOnClickListener(this);
//        rightSideSearch.setOnClickListener(this);
//        rg_home_tab_menu = (RadioGroup) view.findViewById(R.id.rg_tab_menu);
//        // 设置第一个radiobutton为选中状态
//        RadioButton rbtn = (RadioButton) rg_home_tab_menu.getChildAt(0);
//        rbtn.setChecked(true);
//        //设置radiogroud监听
//        rg_home_tab_menu.setOnCheckedChangeListener(this);
//        contactFragment=new ContactFragment();
//        switchFragment(contactFragment,TAG_CONACT);
//
//    }
//
//
//    @Override
//    public void onClick(View v) {
//
//    }
//
//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        switch(checkedId){
//            case R.id.rbtn_one:
//                if(contactFragment==null){
//                    contactFragment=new ContactFragment();
//                }
//                switchFragment(contactFragment,TAG_CONACT);
//                break;
//            case R.id.rbtn_two:
//                if(memoFragment==null){
//                    memoFragment=new MemoFragment();
//                }
//                switchFragment(memoFragment,TAG_MEMO);
//                break;
//            case R.id.rbtn_three:
//                if(diaryFragment==null){
//                    diaryFragment=new DiaryFragment();
//                }
//                switchFragment(diaryFragment,TAG_DIARY);
//                break;
//            case R.id.rbtn_four:
//                if(transactionFragment==null){
//                    transactionFragment=new TransactionFragment();
//                }
//                switchFragment(transactionFragment, TAG_TRANSACTION);
//                break;
//        }
//    }
//
//    public void flingLeft() {//自定义方法：处理向左滑动事件
//        RadioButton rbtn;
//        switch (hideTag){
//            case TAG_CONACT:
//                rbtn = (RadioButton) rg_home_tab_menu.getChildAt(1);
//                rbtn.setChecked(true);
//                break;
//            case TAG_MEMO:
//                rbtn = (RadioButton) rg_home_tab_menu.getChildAt(2);
//                rbtn.setChecked(true);
//                break;
//            case TAG_DIARY:
//                rbtn = (RadioButton) rg_home_tab_menu.getChildAt(3);
//                rbtn.setChecked(true);
//                break;
//            case TAG_TRANSACTION:
//                rbtn = (RadioButton) rg_home_tab_menu.getChildAt(0);
//                rbtn.setChecked(true);
//                break;
//        }
//    }
//
//    public void flingRight() {//自定义方法：处理向右滑动事件
//        RadioButton rbtn;
//        switch (hideTag){
//            case TAG_CONACT:
//                rbtn = (RadioButton) rg_home_tab_menu.getChildAt(3);
//                rbtn.setChecked(true);
//                break;
//            case TAG_MEMO:
//                rbtn = (RadioButton) rg_home_tab_menu.getChildAt(0);
//                rbtn.setChecked(true);
//                break;
//            case TAG_DIARY:
//                rbtn = (RadioButton) rg_home_tab_menu.getChildAt(1);
//                rbtn.setChecked(true);
//                break;
//            case TAG_TRANSACTION:
//                rbtn = (RadioButton) rg_home_tab_menu.getChildAt(2);
//                rbtn.setChecked(true);
//                break;
//        }
//    }
//
//    @Override
//    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//        try {
//            if (e1.getX() - e2.getX() < -89) {
//                flingLeft();
//                return true;
//            } else if (e1.getX() - e2.getX() > 89) {
//                flingRight();
//                return true;
//            }
//        } catch (Exception e) {
//        }
//        return false;
//    }
//
//    @Override
//    public boolean onDown(MotionEvent e) {
//        return false;
//    }
//
//    @Override
//    public void onShowPress(MotionEvent e) {
//
//    }
//
//    @Override
//    public boolean onSingleTapUp(MotionEvent e) {
//        return false;
//    }
//
//    @Override
//    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        return false;
//    }
//
//    @Override
//    public void onLongPress(MotionEvent e) {
//
//    }
//}
