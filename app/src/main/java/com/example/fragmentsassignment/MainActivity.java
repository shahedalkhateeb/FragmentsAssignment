package com.example.fragmentsassignment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private static final int HOME_ID = R.id.nav_gallery;
    private long lastBackTime = 0L;

    private BottomNavigationView bottomNav;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        bottomNav = findViewById(R.id.bottom_nav);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View headerView = navigationView.getHeaderView(0);
        TextView profileName = headerView.findViewById(R.id.profile_name);
        TextView profileEmail = headerView.findViewById(R.id.profile_email);
        ImageView profileImage = headerView.findViewById(R.id.profile_image);

        profileName.setText("User Name");
        profileEmail.setText("user@example.com");
        profileImage.setImageResource(R.drawable.profile_pic); // ضع صورتك هنا


        if (savedInstanceState == null) {
            bottomNav.setSelectedItemId(HOME_ID);
            replaceMainFragment(new GalleryFragment(), "Gallery");
            navigationView.setCheckedItem(R.id.drawer_home);
        }


        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_gallery) {
                replaceMainFragment(new GalleryFragment(), "Gallery");
                navigationView.setCheckedItem(R.id.drawer_home);
                return true;
            } else if (itemId == R.id.nav_music) {
                replaceMainFragment(new VideosFragment(), "Music");
                navigationView.setCheckedItem(R.id.drawer_home);
                return true;
            } else if (itemId == R.id.nav_places) {
                replaceMainFragment(new PlacesFragment(), "Places");
                navigationView.setCheckedItem(R.id.drawer_home);
                return true;
            } else if (itemId == R.id.nav_profile) {
                openProfile();
                navigationView.setCheckedItem(R.id.drawer_profile);
                return true;
            }
            return false;
        });


        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawers();
            int id = item.getItemId();
            if (id == R.id.drawer_home) {
                bottomNav.setSelectedItemId(HOME_ID);
                return true;
            } else if (id == R.id.drawer_profile) {
                openProfile();
                bottomNav.setSelectedItemId(R.id.nav_profile);
                return true;
            } else if (id == R.id.drawer_settings) {
                Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
                item.setChecked(true);
                return true;
            }
            return false;
        });


        getOnBackPressedDispatcher().addCallback(this, new androidx.activity.OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Fragment current = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

                if (current instanceof GalleryFragment) {
                    GalleryFragment gf = (GalleryFragment) current;
                    if (gf.getCurrentTabIndex() != 0) {
                        gf.setCurrentTabIndex(0);
                        return;
                    }
                }

                if (bottomNav.getSelectedItemId() != HOME_ID) {
                    bottomNav.setSelectedItemId(HOME_ID);
                    return;
                }

                long now = System.currentTimeMillis();
                if (now - lastBackTime < 2000) {
                    finish();
                } else {
                    lastBackTime = now;
                    Toast.makeText(MainActivity.this, "اضغط رجوع مرة أخرى للخروج", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void openProfile() {
        replaceMainFragment(new ProfileFragment(), "Profile");
    }

    private void replaceMainFragment(@NonNull Fragment fragment, @NonNull String title) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(title);
    }
}

