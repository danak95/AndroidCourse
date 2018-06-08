package com.example.kardana.androidcourse;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.SearchView;
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
import android.widget.ListView;

import com.example.kardana.androidcourse.Fragments.HomeFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Model.Room;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SearchView searchView;
    private MenuItem searchMenuItem;
    //private RoomListAdapter roomListAdapter;
    private ListView roomListView;
    //private List<Room> roomList= new ArrayList<Room>();
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
    private static final String TAG_ROOM_HISTORY = "room_history";
    private static final String TAG_TOP5 = "top5";
    private static final String TAG_WISH_LIST = "wish_list";
    private static final String TAG_MANAGE_ROOMS = "manage_rooms";
    public static String CURRENT_TAG = TAG_HOME;
    private Fragment currFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context=this;
        currFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, currFragment, CURRENT_TAG);
        fragmentTransaction.commitAllowingStateLoss();

//        roomList.add(new Room("123", "1", "1", "1", 4.1));
//        roomList.add(new Room("234", "2", "2", "2", 5.2));
//        roomList.add(new Room("fdsfsd", "3", "3", "3", 2));
//        roomList.add(new Room("deadasr", "4", "4", "4", 3.5));
//        roomList.add(new Room("dfds", "5", "5", "5", 1));
//        roomList.add(new Room("היוש", "6", "6", "6", 2.5));
//        roomList.add(new Room("כגדךכגד", "7", "7", "7", 4.5));
//        roomList.add(new Room("פליז תעבוד", "8", "8", "8", 5.5));
//
//        roomListAdapter = new RoomListAdapter(this, roomList);
//        ListView listView = (ListView) findViewById(R.id.room_list_view);
//        listView.setAdapter(roomListAdapter);
        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        NavigationView navigationViewFilter = (NavigationView) findViewById(R.id.nav_filter);
        navigationViewFilter.setNavigationItemSelectedListener(this);

        expandableList = (ExpandableListView) findViewById(R.id.navigationmenu);
        prepareListData();
        mMenuAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, expandableList);

        // setting list adapter
        expandableList.setAdapter(mMenuAdapter);

        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                CheckBox cbCheck= (CheckBox) view.findViewById(R.id.cb_submenu);
                ExpandedMenuItem child =((ExpandedMenuItem)((ExpandableListAdapter)expandableListView.getExpandableListAdapter()).getChild(i,i1));
                setChildChecked(child, cbCheck);
                return true;
            }
        });
        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                //Log.d("DEBUG", "heading clicked");
                return false;
            }
        });

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

    //private void filter(HashMap<FilterByType, List<String>> filters)
    //{
        //roomListAdapter.setConstraints(filters);
        //roomListAdapter.getFilter().filter("");
    //}

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

        ExpandedMenuHeader item1 = new ExpandedMenuHeader("קטגוריה");
        // Adding data header
        listDataHeader.add(item1);

        ExpandedMenuHeader item2 = new ExpandedMenuHeader("דירוג");
        listDataHeader.add(item2);

        ExpandedMenuHeader item3 = new ExpandedMenuHeader("מיקום");
        listDataHeader.add(item3);

        // Adding child data
        List<ExpandedMenuItem> heading1 = new ArrayList<ExpandedMenuItem>();
        heading1.add(new ExpandedMenuItem("אימה",item1));

        List<ExpandedMenuItem> heading2 = new ArrayList<ExpandedMenuItem>();
        heading2.add(new ExpandedMenuItem(getString(R.string.five_stars),item2));
        heading2.add(new ExpandedMenuItem(getString(R.string.four_stars),item2));
        heading2.add(new ExpandedMenuItem(getString(R.string.three_stars),item2));

        listDataChild.put(listDataHeader.get(0), heading1);// Header, Child data
        listDataChild.put(listDataHeader.get(1), heading2);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

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
                //roomListAdapter.setFilterType(FilterByType.NAME);
                //roomListAdapter.getFilter().filter(newText);
                return true;
            }
        });

        Button finishButton = (Button) this.findViewById(R.id.finish_button);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeFragment) currFragment).filter(getCheckedChildren());
                //filter(getCheckedChildren());
                drawer.closeDrawer(GravityCompat.END);
            }

        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_my_profile:
                return true;

            case R.id.nav_room_history:
                return true;

            case R.id.nav_top5:
                return true;

            case R.id.nav_wishlist:
                return true;

            case R.id.nav_manage_rooms:
                return true;

            case R.id.nav_settings:
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
            case R.id.nav_room_history:
                navItemIndex = 2;
                CURRENT_TAG = TAG_ROOM_HISTORY;
                break;
            case R.id.nav_top5:
                navItemIndex = 3;
                CURRENT_TAG = TAG_TOP5;
                break;
            case R.id.nav_wishlist:
                navItemIndex = 4;
                CURRENT_TAG = TAG_WISH_LIST;
                break;
            case R.id.nav_manage_rooms:
                navItemIndex = 5;
                CURRENT_TAG = TAG_MANAGE_ROOMS;
                break;
            default:
                navItemIndex = 0;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
                Fragment fragment = getFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
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
//                RoomHistoryFragment roomHistoryFragment = new RoomHistoryFragment();
//                return roomHistoryFragment;
            case 2:
//                RoomHistoryFragment roomHistoryFragment = new RoomHistoryFragment();
//                return roomHistoryFragment;
            case 3:
                // notifications fragment
                //NotificationsFragment notificationsFragment = new NotificationsFragment();
                //return notificationsFragment;

            case 4:
                // settings fragment
                //SettingsFragment settingsFragment = new SettingsFragment();
                //return settingsFragment;
            default:
                //return new HomeFragment();
        }

        return null;
    }

}
