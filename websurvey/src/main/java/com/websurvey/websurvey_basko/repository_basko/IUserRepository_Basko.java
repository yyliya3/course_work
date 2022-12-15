package com.websurvey.websurvey_basko.repository_basko;

import com.websurvey.websurvey_basko.model_basko.SurveyModel_Basko;
import com.websurvey.websurvey_basko.model_basko.UserModel_Basko;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IUserRepository_Basko extends JpaRepository<UserModel_Basko, Integer> {
    UserModel_Basko findByLogin(String login);
    List<UserModel_Basko> findAll();
    Boolean existsByLogin(String login);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Users u set u.sessionIdHash = :session_id where u.id = :id")
    void updateSessionIdHashById(@Param("session_id") String sessionIdHash, @Param("id") int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("select s from Users u inner join Surveys s on u.id = s.owner where u.id = :id")
    List<SurveyModel_Basko> GetAllSurveys(@Param("id") int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("select s from Surveys s where s.id not in (select s.id from Users u join u.completedSurveys s where u.id = :id)")
    List<SurveyModel_Basko> GetUncompletedSurveys(@Param("id") int id);
}
