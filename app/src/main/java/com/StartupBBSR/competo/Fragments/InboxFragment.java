package com.StartupBBSR.competo.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.StartupBBSR.competo.Adapters.InboxAdapter;
import com.StartupBBSR.competo.R;
import com.StartupBBSR.competo.databinding.FragmentInboxBinding;
import com.StartupBBSR.competo.databinding.FragmentProfileBinding;
import com.google.android.material.tabs.TabLayout;

public class InboxFragment extends Fragment {

    private FragmentInboxBinding binding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInboxBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
              // Inflate the layout for this fragment

        return view;


    }


}