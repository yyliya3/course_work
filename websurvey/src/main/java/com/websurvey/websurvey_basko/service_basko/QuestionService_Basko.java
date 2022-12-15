package com.websurvey.websurvey_basko.service_basko;

import com.websurvey.websurvey_basko.model_basko.AnswerModel_Basko;
import com.websurvey.websurvey_basko.model_basko.QuestionModel_Basko;
import com.websurvey.websurvey_basko.repository_basko.IQuestionRepository_Basko;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService_Basko implements IQuestionService_Basko{
    @Autowired
    private IQuestionRepository_Basko questionRepository;

    @Override
    public void SaveQuestion(QuestionModel_Basko question) { questionRepository.save(question); }

    @Override
    public List<AnswerModel_Basko> GetAllAnswers(int id) { return questionRepository.GetAllAnswers(id); }

    @Override
    public QuestionModel_Basko GetQuestion(int id) { return questionRepository.findById(id); }

    @Override
    public void Delete(int id) { questionRepository.deleteById(id); }
}
