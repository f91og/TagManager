package yuxue.sqlitetutorial;

import android.provider.BaseColumns;

/**
 * Created by yuxue on 2017/4/11.
 */

public class DBStructure {

    public static abstract class tableEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_ID = "userid";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DOB = "dob";
    }
}
