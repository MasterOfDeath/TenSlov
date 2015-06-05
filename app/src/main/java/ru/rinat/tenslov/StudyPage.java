package ru.rinat.tenslov;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudyPage extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter scAdapter;

    public StudyPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_study_page, container, false);



        String[] from = new String[] { Word.COL_WORD, Word.COL_TRANS };
        int[] to = new int[] { R.id.tvListStudy1, R.id.tvListStudy2 };

        scAdapter =
                new SimpleCursorAdapter(getActivity(), R.layout.list_sudy, null, from, to, 0);
        ListView lvStudy = (ListView) rootView.findViewById(R.id.lvStudy);
        lvStudy.setAdapter(scAdapter);

        // создаем лоадер для чтения данных
        getLoaderManager().initLoader(0, null, this);


        return rootView;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new MyCursorLoader(getActivity(), App.mDbHelper);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        scAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }


    static class MyCursorLoader extends CursorLoader {

        DBHelper db;

        public MyCursorLoader(Context context, DBHelper db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            //Cursor cursor = db.getAllData();
            Cursor cursor = App.mDbHelper.nextTenWords();

            return cursor;
        }

    }

}
