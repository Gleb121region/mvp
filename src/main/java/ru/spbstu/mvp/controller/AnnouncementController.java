package ru.spbstu.mvp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.mvp.request.announcement.AnnouncementRequest;
import ru.spbstu.mvp.request.announcement.CreateAnnouncementRequest;
import ru.spbstu.mvp.request.announcement.UpdateAnnouncementRequest;
import ru.spbstu.mvp.response.flat.AnnouncementResponse;
import ru.spbstu.mvp.response.flat.AnnouncementWithDescriptionResponse;
import ru.spbstu.mvp.service.AnnouncementService;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/announcement")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @Operation(summary = "Get a list of announcements")
    @GetMapping
    public Set<AnnouncementResponse> getFewFlats(@Parameter(description = "Announcement request criteria") @Param("request") AnnouncementRequest request, @Parameter(description = "Maximum number of announcements to return") @RequestParam(name = "limit") Integer limit, @Parameter(description = "Offset for pagination") @RequestParam(name = "offset") Integer offset) {
        return announcementService.getAnnouncementsInfo(request, limit, offset);
    }

    @Operation(summary = "Get detailed information about an announcement")
    @GetMapping("/{flatId}")
    public AnnouncementWithDescriptionResponse getInfAboutFlat(@Parameter(description = "ID of the announcement") @PathVariable Integer flatId) {
        return announcementService.getAnnouncementInfo(flatId);
    }

    @Operation(summary = "Create a new announcement")
    @PostMapping
    public Integer createFlatFromRequestWithoutPhoto(@RequestBody CreateAnnouncementRequest request) {
        return announcementService.createAnnouncementFromRequestWithoutPhoto(request);
    }

    @Operation(summary = "Update an existing announcement")
    @PutMapping
    public void updateFlatFromRequestWithoutPhoto(@RequestBody UpdateAnnouncementRequest request) {
        announcementService.updateAnnouncementByAnnouncementId(request);
    }
}
