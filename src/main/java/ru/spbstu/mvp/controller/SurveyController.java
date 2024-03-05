package ru.spbstu.mvp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.mvp.request.survey.CreateSurveyRequest;
import ru.spbstu.mvp.response.survey.GetSurveyResponse;
import ru.spbstu.mvp.service.SurveyService;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/survey")
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;

    @GetMapping
    public Set<GetSurveyResponse> getSurveys(Principal connectedUser) {
        return surveyService.findSurveyByToken(connectedUser);
    }

    @PostMapping("/create")
    public void createSurvey(Principal connectedUser, @RequestBody CreateSurveyRequest request) {
        surveyService.saveSurvey(connectedUser, request);
    }


}
