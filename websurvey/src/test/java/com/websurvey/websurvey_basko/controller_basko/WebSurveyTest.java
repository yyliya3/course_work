package com.websurvey.websurvey_basko.controller_basko;

import com.websurvey.websurvey_basko.enums_basko.UserRole_Basko;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WebSurveyTest {
    @Autowired
    MockMvc mvc;

    String adminLogin;
    String adminPassword;
    String adminCodePhrase;
    String adminSession;

    String userLogin;
    String userPassword;
    String userCodePhrase;
    String userSession;

    void CreateAdmin() throws Exception {
        adminLogin = GenerateString();
        adminPassword = GenerateString();
        adminCodePhrase = GenerateString();

        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("password", adminPassword);
        json.put("role", UserRole_Basko.Admin.value);
        json.put("code_phrase", adminCodePhrase);

        TestPostOk("/user/register", json);

        json = new JSONObject();
        json.put("login", adminLogin);
        json.put("password", adminPassword);
        adminSession = TestPostOk("/user/login", json).getResponse().getContentAsString();
    }

    void CreateUser() throws Exception {
        userLogin = GenerateString();
        userPassword = GenerateString();
        userCodePhrase = GenerateString();

        JSONObject json = new JSONObject();
        json.put("login", userLogin);
        json.put("password", userPassword);
        json.put("role", UserRole_Basko.User.value);
        json.put("code_phrase", userCodePhrase);

        TestPostOk("/user/register", json);

        json = new JSONObject();
        json.put("login", userLogin);
        json.put("password", userPassword);
        userSession = TestPostOk("/user/login", json).getResponse().getContentAsString();
    }

    MvcResult TestPostOk(String url, JSONObject body) throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post(url)
                .accept(MediaType.APPLICATION_JSON)
                .content(body.toString());

        return mvc.perform(request).andExpect(status().isOk()).andReturn();
    }

    String GenerateString() {
        Random random = new Random();

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;

        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }
}
