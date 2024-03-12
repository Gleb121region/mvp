package ru.spbstu.mvp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.spbstu.mvp.entity.SurveyAnswers;
import ru.spbstu.mvp.entity.User;
import ru.spbstu.mvp.exception.UserNotFoundException;
import ru.spbstu.mvp.repository.SurveyRepository;
import ru.spbstu.mvp.repository.UserRepository;
import ru.spbstu.mvp.request.survey.CreateSurveyRequest;
import ru.spbstu.mvp.response.survey.GetSurveyResponse;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;

    public void saveSurvey(Principal connectedUser, CreateSurveyRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) connectedUser;
        Optional<User> currentUser = userRepository.findByEmail(authenticationToken.getName());
        if (currentUser.isPresent()) {
            SurveyAnswers surveyAnswers = SurveyAnswers.builder().term(request.term()).apartmentType(request.apartmentTypes())
                    .city(request.city())
                    .minBudget(request.minBudget())
                    .maxBudget(request.maxBudget())
                    .minArea(request.minArea())
                    .maxArea(request.maxArea())
                    .user(currentUser.get())
                    .build();
            surveyRepository.save(surveyAnswers);
        } else {
            throw new UserNotFoundException("User not found by email");
        }
    }


    public Set<GetSurveyResponse> findSurveyByToken(Principal connectedUser) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) connectedUser;
        Optional<User> currentUser = userRepository.findByEmail(authenticationToken.getName());
        if (currentUser.isPresent()) {
            Set<SurveyAnswers> surveyAnswers = surveyRepository.findByUser(currentUser.get());
            return surveyAnswers.stream().map(answers -> GetSurveyResponse.builder().term(answers.getTerm()).apartmentTypes(answers.getApartmentType()).city(answers.getCity()).minArea(answers.getMinArea()).maxArea(answers.getMaxArea()).minBudget(answers.getMinBudget()).maxBudget(answers.getMaxBudget()).build()).collect(Collectors.toSet());
        } else {
            throw new UserNotFoundException("User not found by email");
        }
    }
}
