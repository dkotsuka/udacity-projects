package com.example.android.habittracking.data;

import android.provider.BaseColumns;

/**
 * Created by dkots on 08/12/2017.
 */

public class HabitsContract {

    private HabitsContract(){}

    public final static class HabitsEntry implements BaseColumns{

        public final static String TABLE_NAME = "habits_table";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_REGISTRATION_DATE = "reg_date";
        public final static String COLUMN_HAD_STUDIED = "had_studied";
        public final static String COLUMN_STUDY_HOURS = "study_hours";
        public final static String COLUMN_STUDY_SUBJECT = "subject";

        public final static int NO = 0;
        public final static int YES = 1;

    }
}
