package com.android.temesgen;

import android.provider.BaseColumns;
public class BeneficiaryContract {

    public static final class BeneficiaryEntry implements BaseColumns {

        public static final String TABLE_NAME = "beneficiary";
        public static final String COLUMN_BENEFICIARY_NAME = "username";
        public static final String COLUMN_BENEFICIARY_EMAIL = "beneficiary_email";
        public static final String COLUMN_BENEFICIARY_pass = "password";
        public static final String COLUMN_BENEFICIARY_fullname = "fullname";
        public static final String COLUMN_BENEFICIARY_mobile = "mobile";
        public static final String COLUMN_BENEFICIARY_sex= "gender"
                ;

    }
}


