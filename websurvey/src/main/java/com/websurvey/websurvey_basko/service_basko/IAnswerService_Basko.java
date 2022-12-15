package com.websurvey.websurvey_basko.service_basko;


import com.websurvey.websurvey_basko.model_basko.AnswerModel_Basko;

public interface IAnswerService_Basko {
    void SaveSurvey(AnswerModel_Basko answer);
    AnswerModel_Basko GetAnswer(int id);
    void Delete(int id);
}
