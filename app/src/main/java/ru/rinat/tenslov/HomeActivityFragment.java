package ru.rinat.tenslov;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class HomeActivityFragment extends Fragment {

    View rootView;

    private View.OnClickListener btnStartClick = new View.OnClickListener() {
        public void onClick(View v) {
            Pager fragPager = new Pager();
            Bundle args = new Bundle();
            fragPager.setArguments(args);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, fragPager, "FragPager");
            transaction.addToBackStack(null);

            transaction.commit();
        }
    };

    public HomeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        TextView homeLabel = (TextView) rootView.findViewById(R.id.home_label);
        Button btnStart = (Button) rootView.findViewById(R.id.btn_start);
        btnStart.setOnClickListener(btnStartClick);

        if (App.mDbHelper.workToday()){
            homeLabel.setText(R.string.home_label_1);
            btnStart.setText(R.string.btn_start_1);
        }
        else {
            homeLabel.setText(R.string.home_label_1);
            btnStart.setText(R.string.btn_start_1);
        }

        //App.mDbHelper.cursorTen = App.mDbHelper.nextTenWords();

        //App.mDbHelper.completeTest(cursor);

        return rootView;
    }

}
