package ru.rinat.tenslov;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SummaryPage extends Fragment implements Pager.PagerOnSummaryPageCallbacks {

    View rootView;
    TextView tvSummary;

    public SummaryPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_summary_page, container, false);

        tvSummary = (TextView)rootView.findViewById(R.id.tvSummary);

        return rootView;
    }


    @Override
    public void onSummaryPageSelected(Boolean check) {
        if (check) tvSummary.setText(R.string.tv_summary_success);
        if (!check) tvSummary.setText(R.string.tv_summary_bad);
    }
}
