package ru.rinat.tenslov;

/**
 * Created by rinat on 03.06.15.
 */
public class Word {
    public static final String TABLE_NAME = "dict";
    public static final String COL_ID = "_id";
    public static final String COL_WORD = "word";
    public static final String COL_TRANS = "trans";
    public static final String COL_STUD = "stud";


    public static final String[] FIELDS = {
            "rowid as _id", //COL_ID
            COL_WORD,
            COL_TRANS,
            COL_STUD};
}
