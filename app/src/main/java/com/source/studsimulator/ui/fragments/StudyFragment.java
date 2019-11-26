package com.source.studsimulator.ui.fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.source.studsimulator.R;
import com.source.studsimulator.model.entity.StudentActivity;
import com.source.studsimulator.model.entity.Study;
import com.source.studsimulator.ui.fragments.adapters.ActiveButtonsAdapter;
import com.source.studsimulator.ui.fragments.adapters.OneActiveButtonAdapter;

import java.util.ArrayList;


public class StudyFragment extends Fragment {

    private RecyclerView universityRV;
    private RecyclerView extraActivityRV;
    private ArrayList<StudentActivity> university;
    private ArrayList<StudentActivity> extraActivity;
    private ArrayList<Boolean> isCourseActive = new ArrayList<>();

    private StudyFragment.OnStudyFragmentListener activityListener;
    private int activeButtonIndex = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.study_fragment_activity, null);

        initializeLists();

        universityRV = view.findViewById(R.id.universityRV);
        universityRV.setLayoutManager(new LinearLayoutManager(getContext()));
        universityRV.setHasFixedSize(true);
        OneActiveButtonAdapter universityRVAdapter = new OneActiveButtonAdapter(university);
        universityRV.setAdapter(universityRVAdapter);
        universityRVAdapter.setIndexOfActivatedButton(activeButtonIndex);

        universityRVAdapter.setAdapterListener(position -> {
            universityRVAdapter.setIndexOfActivatedButton(position);
            changeButtonActivity(position);
            universityRVAdapter.notifyDataSetChanged();
            activityListener.clickOnStudyButton((Study) university.get(position));
        });

        extraActivityRV = view.findViewById(R.id.extraActivityRV);
        extraActivityRV.setLayoutManager(new LinearLayoutManager(getContext()));
        extraActivityRV.setHasFixedSize(true);
        ActiveButtonsAdapter extraActivityRVAdapter = new ActiveButtonsAdapter(extraActivity);
        extraActivityRV.setAdapter(extraActivityRVAdapter);

        extraActivityRVAdapter.setAdapterListener(position -> {
            extraActivityRVAdapter.setButtonDisActivate(position);
            changeAccessForSideButton(position);
            extraActivityRVAdapter.notifyDataSetChanged();
            activityListener.clickOnStudyButton((Study) extraActivity.get(position));
        });

        if (isCourseActive.size() == 0) {
            for (int i = 0; i < extraActivity.size(); ++i) {
                isCourseActive.add(false);
            }
        }

        for (int i = 0; i < isCourseActive.size(); ++i) {
            if (isCourseActive.get(i)) {
                extraActivityRVAdapter.setButtonDisActivate(i);
            }
        }


        return view;
    }

    private void initializeLists() {
        university = new ArrayList<>();
        university.add(new Study(getString(R.string.noVisit), 0, 0, 0, 5, 0));
        university.add(new Study(getString(R.string.visit), 0, 15, -4, -15, 0));
        university.add(new Study(getString(R.string.cheat), 0, 2, -1, -10, 0));
        university.add(new Study(getString(R.string.workHard), 0, 20, -6, -20, 0));
        extraActivity = new ArrayList<>();
        extraActivity.add(new Study(getString(R.string.english), 5, 8, -2, -5, 0));
        extraActivity.add(new Study(getString(R.string.itransition), 0, 10, -4, -8, 6));
        extraActivity.add(new Study(getString(R.string.epam), 0, 12, -6, -10, 8));
        extraActivity.add(new Study(getString(R.string.shad), 0, 30, -12, -50, 10));
        extraActivity.add(new Study(getString(R.string.cookingCourses), 0, 20, 4, 0, 0));

    }

    private void changeButtonActivity(int position) {
        activeButtonIndex = activeButtonIndex == position ? -1 : position;
    }

    private void changeAccessForSideButton(int pos) {
        isCourseActive.set(pos, !isCourseActive.get(pos));
    }

    public interface OnStudyFragmentListener {
        void clickOnStudyButton(Study study);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StudyFragment.OnStudyFragmentListener) {
            activityListener = (StudyFragment.OnStudyFragmentListener) context;
        }
    }
}
