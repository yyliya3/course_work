package com.websurvey.websurvey_basko.service_basko;

import com.websurvey.websurvey_basko.model_basko.AnswerModel_Basko;
import com.websurvey.websurvey_basko.model_basko.QuestionModel_Basko;

import java.util.List;

public interface IQuestionService_Basko {
    void SaveQuestion(QuestionModel_Basko question);
    List<AnswerModel_Basko> GetAllAnswers(int id);
    QuestionModel_Basko GetQuestion(int id);
    void Delete(int id);
}
