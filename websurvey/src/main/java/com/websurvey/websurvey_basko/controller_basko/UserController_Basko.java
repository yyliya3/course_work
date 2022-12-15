package com.websurvey.websurvey_basko.controller_basko;

import com.websurvey.websurvey_basko.apis_basko.UserApi_Basko;
import com.websurvey.websurvey_basko.enums_basko.UserRole_Basko;
import com.websurvey.websurvey_basko.model_basko.UserModel_Basko;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController_Basko {
    @Autowired
    private UserApi_Basko userApi;

    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        UserModel_Basko user = new UserModel_Basko();
        user.setLogin(json.getString("login"));
        user.setRole(json.getInt("role"));
        user.setPasswordHash(String.valueOf(json.getString("password").hashCode()));
        user.setCodePhraseHash(String.valueOf(json.getString("code_phrase").hashCode()));

        if (userApi.UserExist(user.getLogin())) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        userApi.SaveUser(user);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/exist/{login}")
    public ResponseEntity<?> LoginExist(@PathVariable("login") String login) {
        Boolean response = userApi.UserExist(login);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> GetAllUsers() {
        List<Object> response = new ArrayList<>();
        for (var user : userApi.GetAllUsers()) {
            Map<String, Object> userJson = new HashMap();
            if (user.getRole() >= UserRole_Basko.Admin.value) continue;
            userJson.put("login", user.getLogin());
            userJson.put("banned", user.getBanned());
            response.add(userJson);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/ban")
    @Transactional
    public ResponseEntity<?> Ban(@RequestBody String request) {
        JSONObject json = new JSONObject(request);

        UserModel_Basko user = userApi.GetUser(json);
        UserModel_Basko targetUser = userApi.GetUserByLogin(json.getString("user"));
        if (targetUser == null || user == null) return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        if (user.getRole() < UserRole_Basko.Admin.value) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        targetUser.setBanned(true);
        userApi.SaveUser(targetUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/unban")
    @Transactional
    public ResponseEntity<?> Unban(@RequestBody String request) {
        JSONObject json = new JSONObject(request);

        UserModel_Basko user = userApi.GetUser(json);
        UserModel_Basko targetUser = userApi.GetUserByLogin(json.getString("user"));
        if (targetUser == null || user == null) return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        if (user.getRole() < UserRole_Basko.Admin.value) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        targetUser.setBanned(false);
        userApi.SaveUser(targetUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/recovery")
    public ResponseEntity<?> Recovery(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        String login = json.getString("login");
        String codePhraseHash = String.valueOf(json.getString("code_phrase").hashCode());

        UserModel_Basko user = userApi.GetUserByLogin(login);
        user.setPasswordHash(String.valueOf(json.getString("password").hashCode()));
        if (!user.getCodePhraseHash().equals(codePhraseHash)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        String sessionId = String.valueOf(ThreadLocalRandom.current().nextInt());
        userApi.UpdateSessionIdHash(user.getId(), String.valueOf(sessionId.hashCode()));

        userApi.SaveUser(user);
        return new ResponseEntity<>(sessionId, HttpStatus.OK);
    }

    @PostMapping("/surveys/uncompleted")
    public ResponseEntity<?> GetUncompletedSurveys(@RequestBody String request) {
        JSONObject json = new JSONObject(request);

        UserModel_Basko user = userApi.GetUser(json);
        if (user == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        JSONArray response = new JSONArray();   

        for (var survey: userApi.GetUncompletedSurveys(user.getId())) {
            JSONObject surveyObject = new JSONObject();
            surveyObject.put("id", survey.getId());
            surveyObject.put("title", survey.getTitle());
            surveyObject.put("description", survey.getDescription());
            surveyObject.put("author", survey.getOwner().getLogin());

            response.put(surveyObject);
        }

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @PostMapping("/surveys/my")
    public ResponseEntity<?> GetAllSurveys(@RequestBody String request) {
        JSONObject json = new JSONObject(request);

        UserModel_Basko user = userApi.GetUser(json, UserRole_Basko.Admin);
        if (user == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        JSONArray response = new JSONArray();

        for (var survey: userApi.GetMySurveys(user.getId())) {
            JSONObject surveyObject = new JSONObject();
            surveyObject.put("id", survey.getId());
            surveyObject.put("title", survey.getTitle());
            surveyObject.put("description", survey.getDescription());

            response.put(surveyObject);
        }

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @PostMapping("/session/verify")
    public ResponseEntity<?> VerifySession(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        String login = json.getString("login");
        String sessionId = json.getString("session_id");

        if (!userApi.VerifySession(login, sessionId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/session/remove")
    public ResponseEntity<?> RemoveSession(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        String login = json.getString("login");
        String sessionId = json.getString("session_id");

        if (!userApi.VerifySession(login, sessionId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        UserModel_Basko user = userApi.GetUserByLogin(login);

        userApi.UpdateSessionIdHash(user.getId(), null);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        String login = json.getString("login");
        String password = String.valueOf(json.getString("password").hashCode());

        UserModel_Basko user = userApi.GetUserByLogin(login);
        if (!user.getPasswordHash().equals(password)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        String sessionId = String.valueOf(ThreadLocalRandom.current().nextInt());
        userApi.UpdateSessionIdHash(user.getId(), String.valueOf(sessionId.hashCode()));

        return new ResponseEntity<>(sessionId, HttpStatus.OK);
    }
}
