package com.yuxingxin.yxtx.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yuxingxin.yxtx.R;
import com.yuxingxin.yxtx.layout.ScrimInsetsFrameLayout;
import com.yuxingxin.yxtx.ui.fragment.AboutFragment;
import com.yuxingxin.yxtx.ui.fragment.ArchiveFragment;
import com.yuxingxin.yxtx.ui.fragment.CategoryFragment;
import com.yuxingxin.yxtx.ui.fragment.HomeFragment;
import com.yuxingxin.yxtx.ui.fragment.TagFragment;
import com.yuxingxin.yxtx.utils.AttrUtils;
import com.yuxingxin.yxtx.utils.DeviceUtils;
import com.yuxingxin.yxtx.utils.ManagerTypeface;

/**
 * main
 * Created by Sean on 15/7/22.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener{

    private final static double NAVIGATION_DRAWER_ACCOUNT_SECTION_ASPECT_RATIO = 9d/16d;

    private DrawerLayout mDrawerLayout;

    private FrameLayout mFrameLayout_AccountView;
    private FrameLayout mFrameLayout_Home, mFrameLayout_Category,mFrameLayout_Tag,mFrameLayout_Archive,
            mFrameLayout_Email,mFrameLayout_About;

    private LinearLayout mNavDrawerEntriesRootView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ScrimInsetsFrameLayout mScrimInsetsFrameLayout;

    private TextView mTextView_AccountDisplayName, mTextView_AccountProfile;
    private TextView mTextView_Home, mTextView_Category,mTextView_Tag,mTextView_Archive,
            mTextView_Email, mTextView_About;

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    //init views
    private void init(){
        initToolBar();
        //resource
        mFrameLayout_AccountView = (FrameLayout) findViewById(R.id.navigation_drawer_account_view);
        mFrameLayout_Home = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_home);
        mFrameLayout_Category = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_category);
        mFrameLayout_Tag = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_tag);
        mFrameLayout_Archive = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_archive);
        mFrameLayout_Email = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_email);
        mFrameLayout_About = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_about);
        mNavDrawerEntriesRootView = (LinearLayout)findViewById(R.id.navigation_drawer_linearLayout_entries_root_view);

        mTextView_AccountDisplayName = (TextView) findViewById(R.id.navigation_drawer_account_information_display_name);
        mTextView_AccountProfile = (TextView) findViewById(R.id.navigation_drawer_account_information_profile);

        mTextView_Home = (TextView) findViewById(R.id.navigation_drawer_items_textView_home);
        mTextView_Category = (TextView) findViewById(R.id.navigation_drawer_items_textView_category);
        mTextView_Tag = (TextView) findViewById(R.id.navigation_drawer_items_textView_tag);
        mTextView_Archive = (TextView) findViewById(R.id.navigation_drawer_items_textView_archive);
        mTextView_Email = (TextView) findViewById(R.id.navigation_drawer_items_textView_email);
        mTextView_About = (TextView) findViewById(R.id.navigation_drawer_items_textView_about);

        // Typefaces
        mTextView_AccountDisplayName.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_medium));
        mTextView_AccountProfile.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_regular));
        mTextView_Home.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_medium));
        mTextView_Category.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_medium));
        mTextView_Tag.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_medium));
        mTextView_Archive.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_medium));
        mTextView_Email.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_medium));
        mTextView_About.setTypeface(ManagerTypeface.getTypeface(this, R.string.typeface_roboto_medium));

        mDrawerLayout = (DrawerLayout)findViewById(R.id.main_activity_DrawerLayout);
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.primary_dark));
        mScrimInsetsFrameLayout = (ScrimInsetsFrameLayout) findViewById(R.id.main_activity_navigation_drawer_rootLayout);

        mActionBarDrawerToggle = new ActionBarDrawerToggle
                (
                        this,
                        mDrawerLayout,mToolbar,
                        R.string.navigation_drawer_opened,
                        R.string.navigation_drawer_closed
                ){
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);


        mActionBarDrawerToggle.syncState();

        // Navigation Drawer layout width
        int possibleMinDrawerWidth = DeviceUtils.getScreenWidth(this) -
                AttrUtils.getThemeAttributeDimensionSize(this, android.R.attr.actionBarSize);

        int maxDrawerWidth = getResources().getDimensionPixelSize(R.dimen.navigation_drawer_max_width);
        mScrimInsetsFrameLayout.getLayoutParams().width = Math.min(possibleMinDrawerWidth, maxDrawerWidth);

        // Account section height
        mFrameLayout_AccountView.getLayoutParams().height = (int) (mScrimInsetsFrameLayout.getLayoutParams().width
                * NAVIGATION_DRAWER_ACCOUNT_SECTION_ASPECT_RATIO);

        // Nav Drawer item click listener
        mFrameLayout_AccountView.setOnClickListener(this);
        mFrameLayout_Home.setOnClickListener(this);
        mFrameLayout_Category.setOnClickListener(this);
        mFrameLayout_Tag.setOnClickListener(this);
        mFrameLayout_Archive.setOnClickListener(this);
        mFrameLayout_Email.setOnClickListener(this);
        mFrameLayout_About.setOnClickListener(this);

        // Set the first item as selected for the first time
        getSupportActionBar().setTitle(R.string.toolbar_title_home);
        mFrameLayout_Home.setSelected(true);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_activity_content_frame, HomeFragment.newInstance())
                .commit();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.navigation_drawer_account_view){
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }else{
            if (!v.isSelected()){
                onRowPressed((FrameLayout) v);
                switch (v.getId()){
                    case R.id.navigation_drawer_items_list_linearLayout_home: {
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle(getString(R.string.toolbar_title_home));
                        }
                        v.setSelected(true);

                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_activity_content_frame, HomeFragment.newInstance())
                                .commit();
                        break;
                    }
                    case R.id.navigation_drawer_items_list_linearLayout_category: {
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle(getString(R.string.toolbar_title_category));
                        }
                        v.setSelected(true);

                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_activity_content_frame, CategoryFragment.newInstance())
                                .commit();
                        break;
                    }
                    case R.id.navigation_drawer_items_list_linearLayout_tag: {
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle(getString(R.string.toolbar_title_tag));
                        }
                        v.setSelected(true);

                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_activity_content_frame, TagFragment.newInstance())
                                .commit();
                        break;
                    }
                    case R.id.navigation_drawer_items_list_linearLayout_archive: {
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle(getString(R.string.toolbar_title_archive));
                        }
                        v.setSelected(true);

                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_activity_content_frame, ArchiveFragment.newInstance())
                                .commit();
                        break;
                    }
                    case R.id.navigation_drawer_items_list_linearLayout_about: {
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle(getString(R.string.toolbar_title_about));
                        }
                        v.setSelected(true);

                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_activity_content_frame, AboutFragment.newInstance())
                                .commit();
                        break;
                    }
                    case R.id.navigation_drawer_items_list_linearLayout_email:
                        // Start intent to send an email
                        Intent data=new Intent(Intent.ACTION_SENDTO);
                        data.setData(Uri.parse("mailto:lx8909@gmail.com"));
                        startActivity(data);
                        break;

                    default:
                        break;
                }
            }else{
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        }

    }

    /**
     * Set up the rows when any is pressed
     * @param pressedRow is the pressed row in the drawer
     */
    private void onRowPressed(FrameLayout pressedRow){
        if (pressedRow.getTag() != getResources().getString(R.string.tag_nav_drawer_special_entry)){
            for (int i = 0; i < mNavDrawerEntriesRootView.getChildCount(); i++){
                View currentView = mNavDrawerEntriesRootView.getChildAt(i);

                boolean currentViewIsMainEntry = currentView.getTag() ==
                        getResources().getString(R.string.tag_nav_drawer_main_entry);

                if (currentViewIsMainEntry){
                    if (currentView == pressedRow){
                        currentView.setSelected(true);
                    }else{
                        currentView.setSelected(false);
                    }
                }
            }
        }

        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
