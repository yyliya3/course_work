package com.websurvey.websurvey_basko.controller_basko;

import com.websurvey.websurvey_basko.enums_basko.UserRole_Basko;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest extends WebSurveyTest {
    @Test
    @Order(0)
    void GetAllUsers() throws Exception {
        mvc.perform(get("/user/all").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @Order(1)
    void RegisterAdmin() throws Exception {
        adminLogin = GenerateString();
        adminPassword = GenerateString();
        adminCodePhrase = GenerateString();

        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("password", adminPassword);
        json.put("role", UserRole_Basko.Admin.value);
        json.put("code_phrase", adminCodePhrase);

        TestPostOk("/user/register", json);
    }

    @Test
    @Order(1)
    void RegisterUser() throws Exception {
        userLogin = GenerateString();
        userPassword = GenerateString();
        userCodePhrase = GenerateString();

        JSONObject json = new JSONObject();
        json.put("login", userLogin);
        json.put("password", userPassword);
        json.put("role", UserRole_Basko.User.value);
        json.put("code_phrase", userCodePhrase);

        TestPostOk("/user/register", json);
    }

    @Test
    @Order(2)
    void LoginRandomNotExist() throws Exception {
        mvc.perform(get("/user/exist/" + GenerateString())).andExpect(status().isOk()).andExpect(content().string("false"));
    }

    @Test
    @Order(2)
    void LoginAdminExist() throws Exception {
        mvc.perform(get("/user/exist/" + adminLogin)).andExpect(status().isOk()).andExpect(content().string("true"));
    }

    @Test
    @Order(2)
    void LoginUserExist() throws Exception {
        mvc.perform(get("/user/exist/" + userLogin)).andExpect(status().isOk()).andExpect(content().string("true"));
    }

    @Test
    @Order(2)
    void RecoveryAdmin() throws Exception {
        adminPassword = GenerateString();

        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("password", adminPassword);
        json.put("code_phrase", adminCodePhrase);
        TestPostOk("/user/recovery", json);
    }

    @Test
    @Order(2)
    void RecoveryUser() throws Exception {
        userPassword = GenerateString();

        JSONObject json = new JSONObject();
        json.put("login", userLogin);
        json.put("password", userPassword);
        json.put("code_phrase", userCodePhrase);
        TestPostOk("/user/recovery", json);
    }

    @Test
    @Order(3)
    void LoginUser() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", userLogin);
        json.put("password", userPassword);
        userSession = TestPostOk("/user/login", json).getResponse().getContentAsString();
    }

    @Test
    @Order(3)
    void LoginAdmin() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("password", adminPassword);
        adminSession = TestPostOk("/user/login", json).getResponse().getContentAsString();
    }

    @Test
    @Order(4)
    void VerifyAdminSession() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("session_id", adminSession);
        TestPostOk("/user/session/verify", json);
    }

    @Test
    @Order(4)
    void VerifyUserSession() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", userLogin);
        json.put("session_id", userSession);
        TestPostOk("/user/session/verify", json);
    }

    @Test
    @Order(5)
    void Ban() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("session_id", adminSession);
        json.put("user", userLogin);
        TestPostOk("/user/ban", json);
    }

    @Test
    @Order(6)
    void Unban() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("session_id", adminSession);
        json.put("user", userLogin);
        TestPostOk("/user/ban", json);
    }

    @Test
    @Order(7)
    void GetAdminUncompletedSurveys() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("session_id", adminSession);
        TestPostOk("/user/surveys/uncompleted", json);
    }

    @Test
    @Order(7)
    void GetUserUncompletedSurveys() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", userLogin);
        json.put("session_id", userSession);
        TestPostOk("/user/surveys/uncompleted", json);
    }

    @Test
    @Order(7)
    void GetAllSurveys() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("session_id", adminSession);
        TestPostOk("/user/surveys/my", json);
    }

    @Test
    @Order(8)
    void removeAdminSession() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("session_id", adminSession);
        TestPostOk("/user/session/remove", json);
    }

    @Test
    @Order(8)
    void removeUserSession() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", userLogin);
        json.put("session_id", userSession);
        TestPostOk("/user/session/remove", json);
    }
}