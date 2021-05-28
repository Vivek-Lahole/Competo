package com.StartupBBSR.competo.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.StartupBBSR.competo.Adapters.EventPalUserAdapter;
import com.StartupBBSR.competo.Models.EventPalModel;
import com.StartupBBSR.competo.R;
import com.StartupBBSR.competo.Utils.Constant;
import com.StartupBBSR.competo.databinding.FragmentEventPalBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

public class EventPalFragment extends Fragment {

    public static final String TAG = "sheet";

    private FragmentEventPalBinding binding;

    //    For Skill sets
    private List<String> mSkillDataSet;

    private FirebaseFirestore firestoreDB;
    private FirebaseAuth firebaseAuth;
    private String userID;

    private Constant constant;

    private EventPalUserAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEventPalBinding.inflate(inflater, container, false);


        firestoreDB = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getUid();

        constant = new Constant();

        CollectionReference collectionReference = firestoreDB.collection(constant.getUsers());

        View view = binding.getRoot();

        SnapHelper snapHelper = new LinearSnapHelper();

//        Query query = collectionReference.orderBy("Name").whereArrayContains("Chips", "Coder");

        Query query = collectionReference.orderBy(constant.getUserIdField())
                .whereNotEqualTo(constant.getUserIdField(), userID);

        FirestoreRecyclerOptions<EventPalModel> options = new FirestoreRecyclerOptions.Builder<EventPalModel>()
                .setQuery(query, EventPalModel.class)
                .build();

        RecyclerView recyclerView = binding.eventPalRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        snapHelper.attachToRecyclerView(recyclerView);
//        EventPalUserAdapter adapter = new EventPalUserAdapter(getContext(), mUserDataSet, mSkillDataSet);
        adapter = new EventPalUserAdapter(getContext(), options);
        adapter.setOnItemClickListener(new EventPalUserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
//                Toast.makeText(getContext(), "Item Click: " + mUserDataSet.get(position).getName(), Toast.LENGTH_SHORT).show();
                TextView name = itemView.findViewById(R.id.tvEventPalUserName);
                Toast.makeText(getContext(), "Item Click: " + name.getText().toString(), Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onButtonClick(View itemView, int position) {
                TextView name = itemView.findViewById(R.id.tvEventPalUserName);
                Toast.makeText(getContext(), "Button: " + name.getText().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBottomSheetToggleClick(View itemView, int position) {

                View bottomSheet = itemView.findViewById(R.id.EventPalBottomSheet);
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
                ImageView btnBottomSheet = itemView.findViewById(R.id.btnBottomSheet);

                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {

                    Log.d(TAG, "onButtonClick: STATE_COLLAPSED");
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    itemView.findViewById(R.id.tvEventPalUserAbout).setVisibility(View.VISIBLE);
                    btnBottomSheet.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);

                } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {

                    Log.d(TAG, "onButtonClick: STATE_EXPANDED");
                    itemView.findViewById(R.id.tvEventPalUserAbout).setVisibility(View.GONE);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    btnBottomSheet.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);

                }
            }

        });
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}