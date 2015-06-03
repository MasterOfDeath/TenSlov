package ru.rinat.tenslov;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment {

    private static TextView homeLabel;
    private static Button btnStart;

    public HomeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        homeLabel = (TextView)rootView.findViewById(R.id.home_label);
        btnStart = (Button)rootView.findViewById(R.id.btn_start);

        if (App.mDbHelper.workToday()){
            homeLabel.setText(R.string.home_label_1);
            btnStart.setText(R.string.btn_start_1);
        }
        else {
            homeLabel.setText(R.string.home_label_1);
            btnStart.setText(R.string.btn_start_1);
        }

        //Cursor cursor = App.mDbHelper.nextTenWords();

        //App.mDbHelper.completeTest(cursor);

        return rootView;
    }
}
