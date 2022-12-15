package com.websurvey.websurvey_basko.repository_basko;

import com.websurvey.websurvey_basko.model_basko.AnswerModel_Basko;
import com.websurvey.websurvey_basko.model_basko.QuestionModel_Basko;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IQuestionRepository_Basko extends JpaRepository<QuestionModel_Basko, Integer> {
    QuestionModel_Basko findById(int id);
    void deleteById(int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("select a from Questions q inner join Answers a on q.id = a.question where q.id = :id")
    List<AnswerModel_Basko> GetAllAnswers(@Param("id") int id);
}
