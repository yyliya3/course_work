package com.websurvey.websurvey_basko.controller_basko;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QuestionControllerTest extends WebSurveyTest {
    @Before
    public void InitTests() throws Exception {
        CreateAdmin();
        CreateUser();
    }

    @Test
    void GetAllAnswers() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("session_id", adminSession);
        json.put("title", GenerateString());
        json.put("description", GenerateString());

        TestPostOk("/question/1/answers", json);
    }

    @Test
    void GetAllAnswersError() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", userLogin);
        json.put("session_id", userSession);
        json.put("title", GenerateString());
        json.put("description", GenerateString());

        TestPostOk("/question/1/answers", json);
    }
}