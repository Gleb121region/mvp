package ru.spbstu.mvp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.mvp.request.announcement.AnnouncementRequest;
import ru.spbstu.mvp.request.announcement.CreateAnnouncementRequest;
import ru.spbstu.mvp.response.flat.AnnouncementResponse;
import ru.spbstu.mvp.response.flat.AnnouncementWithDescriptionResponse;
import ru.spbstu.mvp.service.AnnouncementService;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/announcement")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping
    @ResponseBody
    public Set<AnnouncementResponse> getFewFlats(@Param("request") AnnouncementRequest request, @RequestParam(name = "limit") Integer limit, @RequestParam(name = "offset") Integer offset) {
        return announcementService.getAnnouncementsInfo(request, limit, offset);
    }

    @GetMapping("/{flatId}")
    @ResponseBody
    public AnnouncementWithDescriptionResponse getInfAboutFlat(@PathVariable Integer flatId) {
        return announcementService.getAnnouncementInfo(flatId);
    }

    @PostMapping
    public void createFlatFromRequestWithoutPhoto(@RequestBody CreateAnnouncementRequest request) {
        announcementService.createAnnouncementFromRequestWithoutPhoto(request);
    }
}
