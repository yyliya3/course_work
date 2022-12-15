package com.websurvey.websurvey_basko.apis_basko;

import com.websurvey.websurvey_basko.enums_basko.UserRole_Basko;
import com.websurvey.websurvey_basko.model_basko.SurveyModel_Basko;
import com.websurvey.websurvey_basko.model_basko.UserModel_Basko;
import com.websurvey.websurvey_basko.service_basko.IUserService_Basko;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Configurable
public class UserApi_Basko {
    @Autowired
    private IUserService_Basko userService;

    public boolean SaveUser(UserModel_Basko model) { return userService.SaveUser(model); }

    public List<UserModel_Basko> GetAllUsers() { return userService.GetAllUsers(); }

    public Boolean UserExist(String login) { return userService.UserExist(login); }

    public UserModel_Basko GetUserByLogin(String login) { return userService.GetUserByLogin(login); }

    public List<SurveyModel_Basko> GetUncompletedSurveys(int id) { return userService.GetUncompletedSurveys(id); }

    public List<SurveyModel_Basko> GetMySurveys(int id) { return userService.GetAllSurveys(id); }

    public void UpdateSessionIdHash(int id, String sessionIdHash) { userService.UpdateSessionIdHash(id, sessionIdHash); }

    public UserModel_Basko GetUser(JSONObject json) {
        return GetUser(json, UserRole_Basko.User);
    }

    public UserModel_Basko GetUser(JSONObject json, UserRole_Basko permissionLevel) {
        String login = json.getString("login");
        String sessionId = json.getString("session_id");

        if (!VerifySession(login, sessionId)) return null;
        UserModel_Basko user = GetUserByLogin(login);

        if (user.getRole() < permissionLevel.value) return null;
        return user;
    }

    public Boolean VerifySession(String login, String sessionId) {
        if (!UserExist(login)) return false;
        if (!Objects.equals(GetUserByLogin(login).getSessionIdHash(), String.valueOf(sessionId.hashCode()))) return false;
        return true;
    }
}
