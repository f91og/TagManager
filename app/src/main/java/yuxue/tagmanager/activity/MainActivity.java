package yuxue.tagmanager.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import yuxue.tagmanager.R;
import yuxue.tagmanager.fragment.ContentFragment;

/**
 * Created by yuxue on 2017/3/5.
 */

public class MainActivity extends AppCompatActivity {

    private ContentFragment contentFragment;
    private DrawerLayout drawerLayout;
    private long touchTime = 0;
    /* 退出的间隔时间 */
    private static final long EXIT_INTERVAL_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        // 加载首页fragment
        contentFragment = new ContentFragment();
        replaceFragment(contentFragment);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // 关闭DrawerLayout的手势滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
		/* 如何在Activity中获得Fragment中的控件？ */
		/* DrawerLayout打开时启用手势滑动，使其能滑动关闭 */
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerStateChanged(int arg0) {
            }

            @Override
            public void onDrawerSlide(View arg0, float arg1) {
            }

            @Override
            public void onDrawerOpened(View arg0) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }

            @Override
            public void onDrawerClosed(View arg0) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        /**
         * 将Fragment动态加入到Activity中的步骤(静态加入的话直接在Activity的布局文件中加入Fragment控件即可)
         * 这里使用的Fragment是support下的，所以相应的FragmnetManager和FragmentTransaction都应该使用
         * support下的
         */
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - touchTime) >= EXIT_INTERVAL_TIME) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                touchTime = currentTime;
            } else {
                finish();
                System.exit(0);
            }
        }

    }

}
