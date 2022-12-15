package com.websurvey.websurvey_basko.enums_basko;

public enum QuestionType_Basko {
    TEXT(0),
    BOOLEAN(1),
    VARIANT(2);

    public final int id;

    QuestionType_Basko(int id) {
        this.id = id;
    }
}
