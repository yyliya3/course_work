package com.websurvey.websurvey_basko.service_basko;

import com.websurvey.websurvey_basko.model_basko.QuestionModel_Basko;
import com.websurvey.websurvey_basko.model_basko.SurveyModel_Basko;

import java.util.List;

public interface ISurveyService_Basko {
    void SaveSurvey(SurveyModel_Basko survey);
    SurveyModel_Basko GetSurvey(int id);
    List<QuestionModel_Basko> GetAllQuestions(int id);
    void Delete(int id);
}
