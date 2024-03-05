package ru.spbstu.mvp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.spbstu.mvp.entity.Survey;
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

import static ru.spbstu.mvp.service.UserService.getUser;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;

    public void saveSurvey(Principal connectedUser, CreateSurveyRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) connectedUser;
        Optional<User> currentUser = userRepository.findByEmail(authenticationToken.getName());
        if (currentUser.isPresent()) {
            Survey survey = Survey.builder()
                    .term(request.term())
                    .apartmentType(request.apartmentType())
                    .city(request.city())
                    .minBudget(request.minBudget())
                    .maxBudget(request.maxBudget())
                    .minArea(request.minArea())
                    .maxArea(request.maxArea())
                    .user(currentUser.get())
                    .build();
            surveyRepository.save(survey);
        } else {
            throw new UserNotFoundException("User not found by email");
        }
    }


    public Set<GetSurveyResponse> findSurveyByToken(Principal connectedUser) {
        User currentUser = getUser((UsernamePasswordAuthenticationToken) connectedUser);
        String token = currentUser.getTokens().stream().map(it -> {
            if (!it.expired && !it.revoked) {
                return it.getToken();
            }
            return null;
        }).toString();
        Set<Survey> surveys = surveyRepository.findByToken(token);
        return surveys.stream().map(survey -> GetSurveyResponse.builder().term(survey.getTerm()).apartmentType(survey.getApartmentType()).city(survey.getCity()).minArea(survey.getMinArea()).maxArea(survey.getMaxArea()).minBudget(survey.getMinBudget()).maxBudget(survey.getMaxBudget()).build()).collect(Collectors.toSet());
    }
}
