package ru.spbstu.mvp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.mvp.request.flat.CreateFlatRequest;
import ru.spbstu.mvp.request.flat.FlatRequest;
import ru.spbstu.mvp.response.flat.FlatResponse;
import ru.spbstu.mvp.response.flat.FlatWithDescriptionResponse;
import ru.spbstu.mvp.service.FlatService;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/flats")
@RequiredArgsConstructor
public class FlatController {

    private final FlatService flatService;

    @GetMapping
    @ResponseBody
    public Set<FlatResponse> getFewFlats(@Param("request") FlatRequest request, @RequestParam(name = "limit") Integer limit, @RequestParam(name = "offset") Integer offset) {
        return flatService.getFlatsInfo(request, limit, offset);
    }

    @GetMapping("/{flatId}")
    @ResponseBody
    public FlatWithDescriptionResponse getInfAboutFlat(@PathVariable Integer flatId) {
        return flatService.getFlatInfo(flatId);
    }

    @PostMapping
    public void createFlatFromRequestWithoutPhoto(@RequestBody CreateFlatRequest request) {
        flatService.createFlatFromRequestWithoutPhoto(request);
    }
}
