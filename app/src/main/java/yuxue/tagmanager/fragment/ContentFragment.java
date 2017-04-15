package yuxue.tagmanager.fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import yuxue.tagmanager.R;
import yuxue.tagmanager.activity.ContactAddActivity;
import yuxue.tagmanager.adapter.FragmentAdapter;

/**
 * Created by yuxue on 2017/3/5.
 */

public class ContentFragment extends Fragment implements View.OnClickListener,RadioGroup.OnCheckedChangeListener{

    private ImageButton leftSlideTrigger;
    private ImageButton rightSideSearch;

    // 四个子Fragment
    private ContactFragment contactFragment;
    private MemoFragment memoFragment;
    private DiaryFragment diaryFragment;
    private TransactionFragment transactionFragment;
    private List<Fragment> fragments=new ArrayList<Fragment>();

    private RadioGroup rg_home_tab_menu;
    private ViewPager mViewPager;
    FragmentAdapter adapter;

    private int currentPage;
    private DrawerLayout drawerLayout;
    private FloatingActionButton fabAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return(inflater.inflate(R.layout.fragment_content,container,false));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initView(view);
        super.onViewCreated(view, savedInstanceState);
    }

    void initView(View view){
        mViewPager=(ViewPager)view.findViewById(R.id.mViewPager);
        leftSlideTrigger=(ImageButton)view.findViewById(R.id.ibtn_left);
        rightSideSearch=(ImageButton)view.findViewById(R.id.ibtn_right);
        fabAdd=(FloatingActionButton)view.findViewById(R.id.fab_add);
        fabAdd.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.back_blue)));
        fabAdd.setOnClickListener(this);
        leftSlideTrigger.setOnClickListener(this);
        rightSideSearch.setOnClickListener(this);
        rg_home_tab_menu = (RadioGroup) view.findViewById(R.id.rg_tab_menu);
        //设置radiogroud监听
        rg_home_tab_menu.setOnCheckedChangeListener(this);
        contactFragment=new ContactFragment();
        memoFragment=new MemoFragment();
        diaryFragment=new DiaryFragment();
        transactionFragment=new TransactionFragment();
        //构造适配器
        fragments.add(contactFragment);
        fragments.add(memoFragment);
        fragments.add(diaryFragment);
        fragments.add(transactionFragment);
        adapter=new FragmentAdapter(getChildFragmentManager(),fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                rg_home_tab_menu.getChildAt(position).performClick();
                currentPage=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //checked是资源布局的id
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch(checkedId){
            case R.id.rbtn_one:
                mViewPager.setCurrentItem(0);
                currentPage=0;
                break;
            case R.id.rbtn_two:
                mViewPager.setCurrentItem(1);
                currentPage=1;
                break;
            case R.id.rbtn_three:
                mViewPager.setCurrentItem(2);
                currentPage=2;
                break;
            case R.id.rbtn_four:
                mViewPager.setCurrentItem(3);
                currentPage=3;
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_left:
                drawerLayout = (DrawerLayout) getActivity().findViewById(
                        R.id.drawer_layout);
                if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
                break;
            case R.id.ibtn_right:
                //搜索按钮单击事件
                break;
            case R.id.fab_add:
                //悬浮添加按钮单击事件
                Intent intent=new Intent(getContext(), ContactAddActivity .class);
                getContext().startActivity(intent);
                ((AppCompatActivity) getContext()).
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
        }
    }


}
