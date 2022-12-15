package com.websurvey.websurvey_basko.service_basko;

import com.websurvey.websurvey_basko.model_basko.AnswerModel_Basko;
import com.websurvey.websurvey_basko.repository_basko.IAnswerRepository_Basko;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService_Basko implements IAnswerService_Basko {
    @Autowired
    private IAnswerRepository_Basko answerRepository;

    @Override
    public void SaveSurvey(AnswerModel_Basko answer) { answerRepository.save(answer); }

    @Override
    public AnswerModel_Basko GetAnswer(int id) { return answerRepository.findById(id); }

    @Override
    public void Delete(int id) { answerRepository.deleteById(id); }
}
