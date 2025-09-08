package com.example.fragmentsassignment;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class GalleryPagerAdapter extends FragmentStateAdapter {

    public GalleryPagerAdapter(@NonNull Fragment host) {
        super(host);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new Tab1Fragment();
            case 1: return new Tab2Fragment();
            default: return new Tab3Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
