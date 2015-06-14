package ru.rinat.tenslov;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;


public class TestPage extends Fragment {
    private static final String PAGE_NUM = "page_num";

    private int mPageNum;
    private TestValue mTestValue;

    private TestPageCallbacks mCallbacks;

    View rootView;
    TextView wordLabel;
    RadioButton rb1, rb2, rb3, rb4;


    public static TestPage newInstance(int pagaNum) {
        TestPage fragment = new TestPage();
        Bundle args = new Bundle();
        args.putInt(PAGE_NUM, pagaNum);
        //args.putParcelable("TestValue", testValue);
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
            //testValue = getArguments().getParcelable("TestValue");
        }

        mTestValue = App.mDbHelper.getTestForWord(mPageNum);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_test_page, container, false);

        wordLabel = (TextView)rootView.findViewById(R.id.word_label);
        rb1 = (RadioButton)rootView.findViewById(R.id.rb1);
        rb2 = (RadioButton)rootView.findViewById(R.id.rb2);
        rb3 = (RadioButton)rootView.findViewById(R.id.rb3);
        rb4 = (RadioButton)rootView.findViewById(R.id.rb4);

        rb1.setOnClickListener(radioListener);
        rb2.setOnClickListener(radioListener);
        rb3.setOnClickListener(radioListener);
        rb4.setOnClickListener(radioListener);


        wordLabel.setText(mTestValue.word);
        rb1.setText(mTestValue.val1);
        rb2.setText(mTestValue.val2);
        rb3.setText(mTestValue.val3);
        rb4.setText(mTestValue.val4);


        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Pager pagerFrag = (Pager) getParentFragment();

        try {
            mCallbacks = (TestPageCallbacks) pagerFrag;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }


    OnClickListener radioListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton)v;

            switch (rb.getId()) {
                case R.id.rb1:
                    mCallbacks.onQuestItemSelected(mPageNum - 1,(mTestValue.idWord == mTestValue.idVal1));
                    break;

                case R.id.rb2:
                    mCallbacks.onQuestItemSelected(mPageNum - 1,(mTestValue.idWord == mTestValue.idVal2));
                    break;

                case R.id.rb3:
                    mCallbacks.onQuestItemSelected(mPageNum - 1,(mTestValue.idWord == mTestValue.idVal3));
                    break;

                case R.id.rb4:
                    mCallbacks.onQuestItemSelected(mPageNum - 1,(mTestValue.idWord == mTestValue.idVal4));
                    break;

                default:
                    break;
            }
        }
    };

    public interface TestPageCallbacks {
        void onQuestItemSelected(int position, Boolean check);
    }

}
