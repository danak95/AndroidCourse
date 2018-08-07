package com.example.kardana.androidcourse;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kardana.androidcourse.Fragments.HomeFragment;
import com.example.kardana.androidcourse.Fragments.MemberProfileFragment;
import com.example.kardana.androidcourse.Fragments.RoomManagmentFragment;
import com.example.kardana.androidcourse.Model.Model;
import com.example.kardana.androidcourse.Model.User;
import com.example.kardana.androidcourse.Model.UserViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public SearchView searchView;
    private MenuItem searchMenuItem;
    private ListView roomListView;
    private MenuItem filterMenuItem;
    private DrawerLayout drawer;
    ExpandableListAdapter mMenuAdapter;
    List<ExpandedMenuHeader> listDataHeader;
    HashMap<ExpandedMenuHeader, List<ExpandedMenuItem>> listDataChild;
    ExpandableListView expandableList;
    private Handler mHandler;
    public static Context context;
    public static int navItemIndex = 0;
    private static final String TAG_HOME = "home";
    private static final String TAG_MY_PROFILE = "my_profile";
    private static final String TAG_MANAGE_ROOMS = "manage_rooms";
    private static final String TAG_LOGOUT = "logout";
    public static String CURRENT_TAG = TAG_HOME;
    private Fragment currFragment;

    private String UserId;
    private User   currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Model.getInstance().getCurrentUser(new Model.IGetCurrentUserCallback() {
            @Override
            public void onComplete(User user) {
                currUser = user;
                UserId = user.getUserid();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context=this;
        currFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, currFragment, CURRENT_TAG);
        fragmentTransaction.commitAllowingStateLoss();

        mHandler = new Handler();

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        NavigationView navigationViewFilter = findViewById(R.id.nav_filter);
        navigationViewFilter.setNavigationItemSelectedListener(this);

        expandableList = findViewById(R.id.navigationmenu);
        prepareListData();
        mMenuAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, expandableList);

        // setting list adapter
        expandableList.setAdapter(mMenuAdapter);

        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                CheckBox cbCheck= view.findViewById(R.id.cb_submenu);
                ExpandedMenuItem child =((ExpandedMenuItem) expandableListView.getExpandableListAdapter().getChild(i,i1));
                setChildChecked(child, cbCheck);
                return true;
            }
        });
        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return false;
            }
        });

    }
    private void showMainToolbar()
    {

    }

    private void setChildChecked(ExpandedMenuItem child, CheckBox cbCheck)
    {
        if (child.getIsChecked())
        {
            child.setIsChecked(false);
            cbCheck.setChecked(false);
        }
        else
        {
            child.setIsChecked(true);
            cbCheck.setChecked(true);
        }
    }

    private HashMap<FilterByType, List<String>> getCheckedChildren()
    {
        int groupCount= expandableList.getExpandableListAdapter().getGroupCount();
        int childCount;
        HashMap<FilterByType, List<String>> filters = new HashMap<FilterByType, List<String>>();

        for(int i=0; i < groupCount; i++) {
            childCount = expandableList.getExpandableListAdapter().getChildrenCount(i);
            for (int j = 0; j < childCount; j++) {
                ExpandedMenuItem child = ((ExpandedMenuItem) expandableList.getExpandableListAdapter().getChild(i, j));

                if (child.getIsChecked()) {
                    FilterByType type = FilterByType.getTypeByName(child.getGroup().getName());
                    List<String> constraints = new ArrayList<String>();
                    constraints.add(child.getName());

                    if (filters.containsKey(type))
                    {
                        constraints.addAll(filters.get(type));
                    }
                    filters.put(type,constraints);
                }
            }
        }

        return filters;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<ExpandedMenuHeader>();
        listDataChild = new HashMap<ExpandedMenuHeader, List<ExpandedMenuItem>>();

        ExpandedMenuHeader category = new ExpandedMenuHeader("קטגוריה");
        // Adding data header
        listDataHeader.add(category);

        ExpandedMenuHeader rank = new ExpandedMenuHeader("דירוג");
        listDataHeader.add(rank);


        // Adding child data
        List<ExpandedMenuItem> categories = new ArrayList<ExpandedMenuItem>();
        List<ExpandedMenuItem> ranks = new ArrayList<ExpandedMenuItem>();

        for(RoomType currType : RoomType.values()) {
            categories.add(new ExpandedMenuItem(currType.getName(), category));
        }

        for(FilterByType.RankFilter currRank : FilterByType.RankFilter.values())
        {
            ranks.add(new ExpandedMenuItem(currRank.getName(),rank));
        }

        listDataChild.put(listDataHeader.get(0), categories);// Header, Child data
        listDataChild.put(listDataHeader.get(1), ranks);

    }

    private void clearCheckedChildren() {
        expandableList.setAdapter(mMenuAdapter);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START) || drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawers();
        }
        else {
            int count = getSupportFragmentManager().getBackStackEntryCount();
            if (CURRENT_TAG == TAG_HOME && getCheckedChildren().size() > 0) {
                ((HomeFragment) currFragment).onBackPressed();
                clearCheckedChildren();
            } else {
                if (count == 0) {
                    super.onBackPressed();
                } else {
                    Toolbar toolbar = findViewById(R.id.toolbar);
                    ActionBar actionBar = getSupportActionBar();
                    actionBar.setDisplayShowCustomEnabled(false);
                    actionBar.setDisplayShowTitleEnabled(true);
                    DrawerLayout drawer = findViewById(R.id.drawer_layout);
                    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                            MainActivity.this, drawer, toolbar, R.string.navigation_drawer_open,
                            R.string.navigation_drawer_close);
                    toggle.syncState();
                    filterMenuItem.setVisible(true);
                    searchMenuItem.setVisible(true);
                    getSupportFragmentManager().popBackStack();
                }
            }
        }
        // logout
        //Model.getInstance().signOut();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        filterMenuItem = menu.findItem(R.id.action_filter);
        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return true;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                if (CURRENT_TAG == TAG_HOME)
                {
                    ((HomeFragment) currFragment).setRoomListAdapterFilter(FilterByType.NAME);
                }
                CursorAdapter selectedView = searchView.getSuggestionsAdapter();
                Cursor cursor = (Cursor) selectedView.getItem(position);
                int index = cursor.getColumnIndexOrThrow(SearchManager.SUGGEST_COLUMN_TEXT_1);
                searchView.setQuery(cursor.getString(index), true);
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                SearchRecentSuggestions suggestions = new SearchRecentSuggestions(getApplicationContext(),
                        MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
                suggestions.saveRecentQuery(query, null);
                searchView.clearFocus();
                intent.setAction(Intent.ACTION_SEARCH);
                intent.putExtra("query", query);
                startActivity(intent);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ((HomeFragment)currFragment).onQueryTextChanged(newText);
                return true;
            }
        });

        Button finishButton = this.findViewById(R.id.finish_button);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeFragment) currFragment).filter(getCheckedChildren());
                drawer.closeDrawer(GravityCompat.END);
            }

        });

        // Update navigation side bar- image, name and email

        // Get current user
//        Model.getInstance().getCurrentUser(new Model.IGetCurrentUserCallback() {
//            @Override
//            public void onComplete(User user) {
//                currUser = user;
//            }
//        });
        UserViewModel dataModel = ViewModelProviders.of(this).get(UserViewModel.class);
        dataModel.getData().observe(this, new Observer<List<User>>() {
                    @Override
                    public void onChanged(@Nullable List<User> users) {
                        String userid = Model.getInstance().getCurrentUserId();

                        for (User user :  users)
                        {
                            if (user.getUserid().equals(userid))
                            {
                                currUser = user;
                                break;
                            }
                        }
                    }
                });
                // Get the fields
        final ImageView currUserImage = this.findViewById(R.id.nav_imageView);
        final TextView userNameView = this.findViewById(R.id.nav_nameView);
        final TextView userEmailView = this.findViewById(R.id.nav_emailView);
        Model.getInstance().getImage(currUser.getImagePath(), new Model.GetImageListener() {
            @Override
            public void onDone(Bitmap imageBitmap) {
                // Update Data
                currUserImage.setImageBitmap(imageBitmap);
                userNameView.setText(currUser.getName());
                userEmailView.setText(currUser.getEmail());
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_my_profile:
                return true;

            case R.id.nav_manage_rooms:
                return true;

            case R.id.nav_logout:
                return true;

            case R.id.action_filter:
                drawer.openDrawer(GravityCompat.END);

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId())
        {
            case R.id.home:
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                break;
            case R.id.nav_my_profile:
                navItemIndex = 1;
                CURRENT_TAG = TAG_MY_PROFILE;
                break;
            case R.id.nav_manage_rooms:
                navItemIndex = 2;
                CURRENT_TAG = TAG_MANAGE_ROOMS;
                break;
            case R.id.nav_logout:
                navItemIndex = 3;
                CURRENT_TAG = TAG_LOGOUT;
                break;
            default:
                navItemIndex = 0;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        loadFragment();
        return true;
    }

    private void loadFragment()
    {
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                searchMenuItem.setVisible(false);
                filterMenuItem.setVisible(false);
                Fragment fragment = getFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                if (CURRENT_TAG == TAG_LOGOUT)
                {
                    Model.getInstance().signOut();
                    Intent entrance = new Intent(getApplicationContext(), ESCEntranceActivity.class);
                    entrance.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(entrance);
                }
                else
                {
                    fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG).addToBackStack(null).commit();
                }
                fragmentManager.executePendingTransactions();

                //fragmentTransaction.commitAllowingStateLoss();
            }
        };

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        drawer.closeDrawers();
        invalidateOptionsMenu();
    }
    private Fragment getFragment() {
        switch (navItemIndex) {
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                currFragment = homeFragment;
                return homeFragment;
            case 1:
                MemberProfileFragment profileFragment = MemberProfileFragment.newInstance(UserId);
                currFragment = profileFragment;
                return profileFragment;
            case 2:
                RoomManagmentFragment roomManagmentFragment = new RoomManagmentFragment();
                currFragment = roomManagmentFragment;
                return roomManagmentFragment;
            default:
        }

        return null;
    }

    public void showActionBar(int titleID) {
        // get the ToolBar from Main Activity
        final Toolbar toolbar = findViewById(R.id.toolbar);
        // get the ActionBar from Main Activity
        final ActionBar actionBar = getSupportActionBar();
        // inflate the customized Action Bar View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_actionbar, null);
        final TextView title = view.findViewById(R.id.frag_title);
        title.setText(titleID);

        if (actionBar != null) {
            // enable the customized view and disable title
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);

            actionBar.setCustomView(view);
            // remove Burger Icon
            toolbar.setNavigationIcon(null);

            // add click listener to the back arrow icon
            view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // reverse back the show
//                    actionBar.setDisplayShowCustomEnabled(false);
//                    actionBar.setDisplayShowTitleEnabled(true);
//                    //get the Drawer and DrawerToggle from Main Activity
//                    // set them back as normal
//                    DrawerLayout drawer = findViewById(R.id.drawer_layout);
//                    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                            MainActivity.this, drawer, toolbar, R.string.navigation_drawer_open,
//                            R.string.navigation_drawer_close);
//                    // All that to re-synchronize the Drawer State
//                    toggle.syncState();
                    // Implement Back Arrow Icon
                    // so it goes back to previous Fragment
                    onBackPressed();
                }
            });
        }
    }
}
