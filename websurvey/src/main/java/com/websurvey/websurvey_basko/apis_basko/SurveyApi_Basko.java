package com.websurvey.websurvey_basko.apis_basko;

import com.websurvey.websurvey_basko.model_basko.QuestionModel_Basko;
import com.websurvey.websurvey_basko.model_basko.SurveyModel_Basko;
import com.websurvey.websurvey_basko.model_basko.UserModel_Basko;
import com.websurvey.websurvey_basko.service_basko.ISurveyService_Basko;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Configurable
public class SurveyApi_Basko {
    @Autowired
    private ISurveyService_Basko surveyService;

    @Autowired
    private UserApi_Basko userApi;

    public void SaveSurvey(SurveyModel_Basko survey) { surveyService.SaveSurvey(survey); }

    public List<QuestionModel_Basko> GetAllQuestions(int id) { return surveyService.GetAllQuestions(id); }

    public SurveyModel_Basko GetSurvey(int id) { return surveyService.GetSurvey(id); }

    public void Delete(int id) { surveyService.Delete(id); }

    public SurveyModel_Basko GetSurvey(JSONObject json, int id) {
        String login = json.getString("login");
        String sessionId = json.getString("session_id");

        if (!userApi.VerifySession(login, sessionId)) return null;
        UserModel_Basko user = userApi.GetUserByLogin(login);

        SurveyModel_Basko survey = GetSurvey(id);

        if (survey.getOwner().getId() != user.getId()) return null;
        return survey;
    }
}
