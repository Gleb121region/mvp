package ru.spbstu.mvp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.spbstu.mvp.entity.Dear;
import ru.spbstu.mvp.entity.Flat;
import ru.spbstu.mvp.entity.User;
import ru.spbstu.mvp.repository.DearRepository;
import ru.spbstu.mvp.repository.FlatRepository;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DearService {
    private final DearRepository dearRepository;
    private final FlatRepository flatRepository;

    private User getCurrentUser(Principal connectedUser) {
        return (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
    }

    public Set<Dear> getDearByUserId(Principal connectedUser) {
        User user = getCurrentUser(connectedUser);
        return dearRepository.findDearsByUser(user);
    }

    // todo: очень сильно на перспективу
    public Set<Dear> getDearByFlatId(Principal connectedUser, Integer flatId) {
        User user = getCurrentUser(connectedUser);
        Optional<Flat> flat = flatRepository.findById(flatId);
        return dearRepository.findDearsByUserAndFlat(user, flat.get());
    }
}
