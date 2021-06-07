package com.example.heal.customerui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.heal.Adapter.CustomGuideAdapter;
import com.example.heal.GuideController;
import com.example.heal.GuideStepsActivity;
import com.example.heal.R;

import java.util.ArrayList;

public class GuideFragment extends Fragment {
    ArrayList<Integer> problemsImages;
    ArrayList<String> problemsText;
    ListView medicalListView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guide, null, false);
        medicalListView = (ListView) view.findViewById(R.id.listOfProblems);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fillProblemsImages();
                fillProblemsText();
                CustomGuideAdapter customGuideAdapter = new CustomGuideAdapter(getActivity(), problemsImages, problemsText);
                medicalListView.setAdapter(customGuideAdapter);
            }
        });
        medicalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listItemSelector(i);
            }
        });
        return view;
    }
    void listItemSelector(int i) {

        GuideController.index = (byte) i;
        Intent intent = new Intent(getActivity(), GuideStepsActivity.class);
        startActivity(intent);
    }

    /**
     * fillProblemsImages method
     * to fill medical problem images
     * */
    void fillProblemsImages() {
        problemsImages = new ArrayList();
        problemsImages.add(R.drawable.heart);
        problemsImages.add(R.drawable.fracture);
        problemsImages.add(R.drawable.burn);
        problemsImages.add(R.drawable.cuts);
        problemsImages.add(R.drawable.heat);
        problemsImages.add(R.drawable.elec);
        problemsImages.add(R.drawable.bite);
        problemsImages.add(R.drawable.choking);
        problemsImages.add(R.drawable.seizure);
        problemsImages.add(R.drawable.eye);
    }

    /**
     * fillProblemsText to load problem names
     * from resources
     * */
    void fillProblemsText() {
        problemsText = new ArrayList();
        String[] problems = getResources().getStringArray(R.array.medicalProblems);

        for(int i=0; i<problems.length; i++)
            problemsText.add(problems[i]);
    }
}