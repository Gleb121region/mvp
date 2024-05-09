package ru.spbstu.mvp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.spbstu.mvp.service.AnnouncementPhotoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/announcement/photos")
@RequiredArgsConstructor
public class AnnouncementPhotoController {

    private final AnnouncementPhotoService announcementPhotoService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void createAnnouncementPhotos(@RequestParam(value = "photos") List<MultipartFile> photos,
                                         @RequestParam(value = "userId") Integer announcementId) {
        announcementPhotoService.uploadAnnouncementPhotos(photos, announcementId);
    }
    // todo: добавить возможность удалить какие-то фотографии

}
