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


/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment {

    private View.OnClickListener btnStartClick = new View.OnClickListener() {
        public void onClick(View v) {
            StudyPage newFragment = new StudyPage();
            Bundle args = new Bundle();
            //args.putInt(StudyPage.ARG_POSITION, position);
            newFragment.setArguments(args);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

// Commit the transaction
            transaction.commit();
        }
    };

    public HomeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

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

        //Cursor cursor = App.mDbHelper.nextTenWords();

        //App.mDbHelper.completeTest(cursor);

        return rootView;
    }

}
