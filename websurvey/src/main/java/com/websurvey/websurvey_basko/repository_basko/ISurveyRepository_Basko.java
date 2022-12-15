package com.websurvey.websurvey_basko.repository_basko;

import com.websurvey.websurvey_basko.model_basko.QuestionModel_Basko;
import com.websurvey.websurvey_basko.model_basko.SurveyModel_Basko;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ISurveyRepository_Basko extends JpaRepository<SurveyModel_Basko, Integer> {
    SurveyModel_Basko findById(int id);
    void deleteById(int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("select q from Surveys s inner join Questions q on s.id = q.survey where s.id = :id")
    List<QuestionModel_Basko> GetAllQuestions(@Param("id") int id);
}
