package com.websurvey.websurvey_basko.service_basko;

import com.websurvey.websurvey_basko.model_basko.SurveyModel_Basko;
import com.websurvey.websurvey_basko.model_basko.UserModel_Basko;

import java.util.List;

public interface IUserService_Basko {
    List<UserModel_Basko> GetAllUsers();
    Boolean SaveUser(UserModel_Basko user);
    Boolean UserExist(String login);
    UserModel_Basko GetUserByLogin(String login);
    void UpdateSessionIdHash(int id, String sessionIdHash);
    List<SurveyModel_Basko> GetAllSurveys(int id);
    List<SurveyModel_Basko> GetUncompletedSurveys(int id);
}
