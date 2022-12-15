package com.websurvey.websurvey_basko.controller_basko;

import com.websurvey.websurvey_basko.apis_basko.AnswerApi_Basko;
import com.websurvey.websurvey_basko.apis_basko.QuestionApi_Basko;
import com.websurvey.websurvey_basko.apis_basko.SurveyApi_Basko;
import com.websurvey.websurvey_basko.apis_basko.UserApi_Basko;
import com.websurvey.websurvey_basko.enums_basko.QuestionType_Basko;
import com.websurvey.websurvey_basko.enums_basko.UserRole_Basko;
import com.websurvey.websurvey_basko.model_basko.AnswerModel_Basko;
import com.websurvey.websurvey_basko.model_basko.QuestionModel_Basko;
import com.websurvey.websurvey_basko.model_basko.SurveyModel_Basko;
import com.websurvey.websurvey_basko.model_basko.UserModel_Basko;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/survey")
@CrossOrigin
public class SurveyController_Basko {
    @Autowired
    private SurveyApi_Basko surveyApi;

    @Autowired
    private UserApi_Basko userApi;

    @Autowired
    private AnswerApi_Basko answerApi;

    @Autowired
    private QuestionApi_Basko questionApi;

    @GetMapping("/{id}")
    public ResponseEntity<?> GetSurvey(@PathVariable("id") int id) {
        SurveyModel_Basko survey = surveyApi.GetSurvey(id);
        if (survey == null) return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);

        JSONObject response = new JSONObject();
        response.put("title", survey.getTitle());
        response.put("description", survey.getDescription());

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @GetMapping("/{id}/questions")
    public ResponseEntity<?> GetAllQuestions(@PathVariable("id") int id) {
        JSONArray response = new JSONArray();

        for (var question : surveyApi.GetAllQuestions(id)) {
            JSONObject surveyObject = new JSONObject();
            surveyObject.put("id", question.getId());
            surveyObject.put("type", question.getType());
            surveyObject.put("wording", question.getWording());
            if (question.getType() == QuestionType_Basko.VARIANT.id) surveyObject.put("variants", new JSONArray(question.getVariants()));

            response.put(surveyObject);
        }

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @PostMapping("/{id}/questions_with_answers")
    public ResponseEntity<?> GetQuestionsWithAnswers(@PathVariable("id") int id, @RequestBody String request) {
        JSONObject json = new JSONObject(request);

        SurveyModel_Basko survey = surveyApi.GetSurvey(id);
        if (survey == null) return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);

        UserModel_Basko user = userApi.GetUser(json);
        if (user == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        if (survey.getOwner() != user) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        JSONArray response = new JSONArray();

        for (var question : surveyApi.GetAllQuestions(id)) {
            JSONObject surveyObject = new JSONObject();
            surveyObject.put("id", question.getId());
            surveyObject.put("type", question.getType());
            surveyObject.put("wording", question.getWording());
            surveyObject.put("answer", question.getAnswer());

            JSONArray answers = new JSONArray();
            for (var answer : questionApi.GetAllAnswers(question.getId())) {
                JSONObject answerJson = new JSONObject();
                answerJson.put("answer", answer.getAnswer());
                answerJson.put("owner", answer.getOwner().getLogin());
                answers.put(answerJson);
            }
            surveyObject.put("answers", answers);

            if (question.getType() == QuestionType_Basko.VARIANT.id) surveyObject.put("variants", new JSONArray(question.getVariants()));

            response.put(surveyObject);
        }

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> Create(@RequestBody String request) {
        JSONObject json = new JSONObject(request);

        UserModel_Basko user = userApi.GetUser(json, UserRole_Basko.Admin);
        if (user == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        SurveyModel_Basko survey = new SurveyModel_Basko();
        survey.setTitle(json.getString("title"));
        survey.setDescription(json.getString("description"));
        survey.setOwner(user);
        surveyApi.SaveSurvey(survey);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/modify_all_survey")
    public ResponseEntity<?> ModifyAllSurvey(@PathVariable("id") int id, @RequestBody String request) {
        JSONObject json = new JSONObject(request);

        SurveyModel_Basko survey = surveyApi.GetSurvey(json, id);
        if (survey == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        UserModel_Basko user = userApi.GetUser(json);
        if (survey.getOwner() != user) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        for (var question : surveyApi.GetAllQuestions(survey.getId())) {
            questionApi.Delete(question.getId());
        }

        JSONArray questions = json.getJSONArray("questions");
        for (int i = 0; i < questions.length(); i++) {
            JSONObject questionJson = questions.getJSONObject(i);
            QuestionModel_Basko question = new QuestionModel_Basko();
            question.setWording(questionJson.getString("wording"));
            question.setType(questionJson.getInt("type"));
            if (question.getType() == QuestionType_Basko.VARIANT.id) question.setVariants(questionJson.getJSONArray("variants").toString());
            question.setAnswer(questionJson.getString("answer"));
            question.setSurvey(survey);

            questionApi.SaveQuestion(question);
        }

        survey.setTitle(json.getString("title"));
        survey.setDescription(json.getString("description"));
        surveyApi.SaveSurvey(survey);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/update_answers")
    public ResponseEntity<?> UpdateAnswers(@PathVariable("id") int id, @RequestBody String request) {
        JSONObject json = new JSONObject(request);

        SurveyModel_Basko survey = surveyApi.GetSurvey(id);
        if (survey == null) return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);

        UserModel_Basko user = userApi.GetUser(json);
        if (user == null) return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        if (user.getBanned()) return new ResponseEntity<>(HttpStatus.LOCKED);

        JSONArray answers = json.getJSONArray("answers");
        for (int i = 0; i < answers.length(); i++) {
            JSONObject answerJson = answers.getJSONObject(i);
            AnswerModel_Basko answer = new AnswerModel_Basko();
            answer.setAnswer(answerJson.getString("answer"));
            answer.setQuestion(questionApi.GetQuestion(answerJson.getInt("id")));
            answer.setOwner(user);
            answerApi.SaveAnswer(answer);
        }

        Set<SurveyModel_Basko> completedSurveys = user.getCompletedSurveys();
        if (!completedSurveys.contains(survey)) {
            completedSurveys.add(survey);
            user.setCompletedSurveys(completedSurveys);
            userApi.SaveUser(user);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<?> Delete(@PathVariable("id") int id, @RequestBody String request) {
        JSONObject json = new JSONObject(request);

        SurveyModel_Basko survey = surveyApi.GetSurvey(json, id);
        if (survey == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        surveyApi.Delete(survey.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
