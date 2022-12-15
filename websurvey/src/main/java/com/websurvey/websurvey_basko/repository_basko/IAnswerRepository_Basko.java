package com.websurvey.websurvey_basko.repository_basko;

import com.websurvey.websurvey_basko.model_basko.AnswerModel_Basko;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAnswerRepository_Basko extends JpaRepository<AnswerModel_Basko, Integer> {
    AnswerModel_Basko findById(int id);
    void deleteById(int id);
}
