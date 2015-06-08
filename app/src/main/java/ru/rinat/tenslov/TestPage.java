package ru.rinat.tenslov;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class TestPage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PAGE_NUM = "page_num";

    // TODO: Rename and change types of parameters
    private int mPageNum;

    View rootView;
    TextView wordLabel;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Test.
     */
    // TODO: Rename and change types and number of parameters
    public static TestPage newInstance(int pagaNum) {
        TestPage fragment = new TestPage();
        Bundle args = new Bundle();
        args.putInt(PAGE_NUM, pagaNum);
        fragment.setArguments(args);
        return fragment;
    }

    public TestPage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPageNum = getArguments().getInt(PAGE_NUM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_test_page, container, false);

        wordLabel = (TextView)rootView.findViewById(R.id.word_label);

        String[] str = App.mDbHelper.getTestForWord(mPageNum);

        for (int i = 0; i < 5; i++) {
            Log.d("dvdf", str[i]);
        }

        return rootView;
    }


}
