package com.websurvey.websurvey_basko.apis_basko;

import com.websurvey.websurvey_basko.model_basko.AnswerModel_Basko;
import com.websurvey.websurvey_basko.model_basko.UserModel_Basko;
import com.websurvey.websurvey_basko.service_basko.IAnswerService_Basko;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

@Service
@Configurable
public class AnswerApi_Basko {
    @Autowired
    private IAnswerService_Basko answerService;

    @Autowired
    private UserApi_Basko userApi;

    public void SaveAnswer(AnswerModel_Basko answer) { answerService.SaveSurvey(answer); }
    public AnswerModel_Basko GetAnswer(int id) { return answerService.GetAnswer(id); }
    public void Delete(int id) { answerService.Delete(id); }

    public AnswerModel_Basko GetAnswer(JSONObject json, int id) {
        String login = json.getString("login");
        String sessionId = json.getString("session_id");

        if (!userApi.VerifySession(login, sessionId)) return null;
        UserModel_Basko user = userApi.GetUserByLogin(login);

        AnswerModel_Basko answer = GetAnswer(id);

        if (answer.getOwner().getId() != user.getId()) return null;
        return answer;
    }
}
