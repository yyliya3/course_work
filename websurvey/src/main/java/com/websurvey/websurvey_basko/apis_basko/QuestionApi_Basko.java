package com.websurvey.websurvey_basko.apis_basko;

import com.websurvey.websurvey_basko.model_basko.AnswerModel_Basko;
import com.websurvey.websurvey_basko.model_basko.QuestionModel_Basko;
import com.websurvey.websurvey_basko.model_basko.UserModel_Basko;
import com.websurvey.websurvey_basko.service_basko.IQuestionService_Basko;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Configurable
public class QuestionApi_Basko {
    @Autowired
    private IQuestionService_Basko questionService;

    @Autowired
    private SurveyApi_Basko surveyApi;

    @Autowired
    private UserApi_Basko userApi;

    public List<AnswerModel_Basko> GetAllAnswers(int id) { return questionService.GetAllAnswers(id); }

    public void SaveQuestion(QuestionModel_Basko question) { questionService.SaveQuestion(question); }

    public QuestionModel_Basko GetQuestion(int id) { return questionService.GetQuestion(id); }

    public void Delete(int id) { questionService.Delete(id); }

    public QuestionModel_Basko GetQuestion(JSONObject json, int id) {
        String login = json.getString("login");
        String sessionId = json.getString("session_id");

        if (!userApi.VerifySession(login, sessionId)) return null;
        UserModel_Basko user = userApi.GetUserByLogin(login);

        QuestionModel_Basko question = GetQuestion(id);

        if (question.getSurvey().getOwner().getId() != user.getId()) return null;
        return question;
    }
}
