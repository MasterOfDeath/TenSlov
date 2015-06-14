package ru.rinat.tenslov;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

public class TestValue implements Parcelable {

    String word;
    String val1;
    String val2;
    String val3;
    String val4;
    int idWord = 0;
    int idVal1 = 0;
    int idVal2 = 0;
    int idVal3 = 0;
    int idVal4 = 0;


    TestValue(int idWord, String word,
              int idVal1, String val1,
              int idVal2, String val2,
              int idVal3, String val3,
              int idVal4, String val4){
        this.idWord = idWord; this.word = word;
        this.idVal1 = idVal1; this.val1 = val1;
        this.idVal2 = idVal2; this.val2 = val2;
        this.idVal3 = idVal3; this.val3 = val3;
        this.idVal4 = idVal4; this.val4 = val4;
    }

    public void setValue(int position, int idVal, String val){
        switch (position) {
            case 0:
                this.idWord = idVal;
                this.word = val;
                break;

            case 1:
                this.idVal1 = idVal;
                this.val1 = val;
                break;

            case 2:
                this.idVal2 = idVal;
                this.val2 = val;
                break;

            case 3:
                this.idVal3 = idVal;
                this.val3 = val;
                break;

            case 4:
                this.idVal4 = idVal;
                this.val4 = val;
                break;

            default:
                break;
        }
    }

    public void shuffleValues() {
        int idVal;
        String val;
        Random rnd = new Random();
        for (int i = 5; i > 0; i--) {
            int index = rnd.nextInt(i);

            idVal = this.idVal1;
            val = this.val1;

            switch (index){

                case 2:
                    this.idVal1 = this.idVal2;
                    this.val1 = this.val2;
                    this.idVal2 = idVal;
                    this.val2 = val;
                    break;

                case 3:
                    this.idVal1 = this.idVal3;
                    this.val1 = this.val3;
                    this.idVal3 = idVal;
                    this.val3 = val;
                    break;

                case 4:
                    this.idVal1 = this.idVal4;
                    this.val1 = this.val4;
                    this.idVal4 = idVal;
                    this.val4 = val;
                    break;

                default:
                    break;
            }
        }
    }

    protected TestValue(Parcel in) {
        word = in.readString();
        val1 = in.readString();
        val2 = in.readString();
        val3 = in.readString();
        val4 = in.readString();
        idWord = in.readInt();
        idVal1 = in.readInt();
        idVal2 = in.readInt();
        idVal3 = in.readInt();
        idVal4 = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeString(val1);
        dest.writeString(val2);
        dest.writeString(val3);
        dest.writeString(val4);
        dest.writeInt(idWord);
        dest.writeInt(idVal1);
        dest.writeInt(idVal2);
        dest.writeInt(idVal3);
        dest.writeInt(idVal4);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TestValue> CREATOR = new Parcelable.Creator<TestValue>() {
        @Override
        public TestValue createFromParcel(Parcel in) {
            return new TestValue(in);
        }

        @Override
        public TestValue[] newArray(int size) {
            return new TestValue[size];
        }
    };
}
