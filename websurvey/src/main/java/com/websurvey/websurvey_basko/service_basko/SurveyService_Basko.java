package com.websurvey.websurvey_basko.service_basko;

import com.websurvey.websurvey_basko.model_basko.QuestionModel_Basko;
import com.websurvey.websurvey_basko.model_basko.SurveyModel_Basko;
import com.websurvey.websurvey_basko.repository_basko.ISurveyRepository_Basko;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyService_Basko implements ISurveyService_Basko {
    @Autowired
    private ISurveyRepository_Basko surveyRepository;

    @Override
    public void SaveSurvey(SurveyModel_Basko survey) { surveyRepository.save(survey); }

    @Override
    public SurveyModel_Basko GetSurvey(int id) { return surveyRepository.findById(id); }

    @Override
    public List<QuestionModel_Basko> GetAllQuestions(int id) { return surveyRepository.GetAllQuestions(id); }

    @Override
    public void Delete(int id) { surveyRepository.deleteById(id); }
}
