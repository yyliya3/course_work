package com.websurvey.websurvey_basko.controller_basko;

import com.websurvey.websurvey_basko.apis_basko.QuestionApi_Basko;
import com.websurvey.websurvey_basko.apis_basko.SurveyApi_Basko;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
@CrossOrigin
public class QuestionController_Basko {
    @Autowired
    private QuestionApi_Basko questionApi;

    @Autowired
    private SurveyApi_Basko surveyApi;

    @PostMapping("/{id}/answers")
    public ResponseEntity<?> GetAllAnswers(@PathVariable("id") int id) {
        JSONArray response = new JSONArray();

        for (var answer: questionApi.GetAllAnswers(id)) {
            JSONObject surveyObject = new JSONObject();
            surveyObject.put("id", answer.getId());
            surveyObject.put("answer", answer.getAnswer());
            surveyObject.put("owner_id", answer.getOwner().getId());

            response.put(surveyObject);
        }

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

}
