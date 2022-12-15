package com.websurvey.websurvey_basko.controller_basko;

import com.websurvey.websurvey_basko.enums_basko.QuestionType_Basko;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SurveyControllerTest extends WebSurveyTest {
    private Integer surveyId;

    @Test
    @Order(0)
    public void InitTests() throws Exception {
        CreateAdmin();
        CreateUser();
    }

    @Test
    @Order(1)
    void Create() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("session_id", adminSession);
        json.put("title", GenerateString());
        json.put("description", GenerateString());

        TestPostOk("/survey/create", json);

        JSONObject survey = new JSONArray(TestPostOk("/user/surveys/my", json).getResponse().getContentAsString()).getJSONObject(0);
        surveyId = survey.getInt("id");
    }

    @Test
    @Order(2)
    void GetSurvey() throws Exception {
        mvc.perform(get("/survey/" + surveyId)).andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void ModifyAllSurvey() throws Exception{
        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("session_id", adminSession);
        json.put("title", GenerateString());
        json.put("description", GenerateString());

        Random random = new Random();
        List<JSONObject> questions = new ArrayList<>();
        for (int i = 0; i < random.nextInt(10) + 5; i++) {
            JSONObject question = new JSONObject();
            question.put("wording", GenerateString());
            question.put("answer", GenerateString());
            question.put("type", random.nextInt(3));

            if (question.getInt("type") == QuestionType_Basko.VARIANT.id) {
                List<String> variants = new ArrayList<>();
                for (int j = 0; j < random.nextInt(10) + 5; j++) {
                    variants.add(GenerateString());
                }
                question.put("variants", new JSONArray(variants));
            }

            questions.add(question);
        }
        json.put("questions", new JSONArray(questions));

        TestPostOk("/survey/" + surveyId + "/modify_all_survey", json);
    }

    @Test
    @Order(4)
    void GetAllQuestions() throws Exception {
        mvc.perform(get("/survey/" + surveyId + "/questions")).andExpect(status().isOk());
    }

    @Test
    @Order(5)
    void UpdateAnswers() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", userLogin);
        json.put("session_id", userSession);

        JSONArray questions = new JSONArray(mvc.perform(get("/survey/" + surveyId + "/questions")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString());

        List<JSONObject> answers = new ArrayList<>();
        for (int i = 0; i < questions.length(); i++) {
            JSONObject answer = new JSONObject();
            answer.put("answer", GenerateString());
            answer.put("id", questions.getJSONObject(i).getString("id"));

            answers.add(answer);
        }
        json.put("answers", new JSONArray(answers));

        TestPostOk("/survey/" + surveyId + "/update_answers", json);
    }

    @Test
    @Order(6)
    void GetQuestionsWithAnswers() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("session_id", adminSession);
        TestPostOk("/survey/" + surveyId + "/questions_with_answers", json);
    }

    @Test
    @Order(7)
    void Delete() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("session_id", adminSession);
        TestPostOk("/survey/" + surveyId + "/delete", json);
    }
}