package com.websurvey.websurvey_basko.service_basko;

import com.websurvey.websurvey_basko.model_basko.SurveyModel_Basko;
import com.websurvey.websurvey_basko.model_basko.UserModel_Basko;
import com.websurvey.websurvey_basko.repository_basko.IUserRepository_Basko;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService_Basko implements IUserService_Basko {
    @Autowired
    private IUserRepository_Basko userRepository;

    @Override
    public Boolean SaveUser(UserModel_Basko user) {
        if (UserExist(user.getLogin())) return false;
        userRepository.save(user);
        return true;
    }

    @Override
    public List<UserModel_Basko> GetAllUsers() { return userRepository.findAll(); }

    @Override
    public Boolean UserExist(String login) { return userRepository.existsByLogin(login); }

    @Override
    public UserModel_Basko GetUserByLogin(String login) { return userRepository.findByLogin(login); }

    @Override
    public void UpdateSessionIdHash(int id, String sessionIdHash) { userRepository.updateSessionIdHashById(sessionIdHash, id); }

    @Override
    public List<SurveyModel_Basko> GetAllSurveys(int id) { return userRepository.GetAllSurveys(id); }

    @Override
    public List<SurveyModel_Basko> GetUncompletedSurveys(int id) { return userRepository.GetUncompletedSurveys(id); }
}
