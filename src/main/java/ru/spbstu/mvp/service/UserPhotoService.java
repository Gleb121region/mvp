package ru.spbstu.mvp.service;

import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.B2StorageClientFactory;
import com.backblaze.b2.client.contentSources.B2ContentSource;
import com.backblaze.b2.client.contentSources.B2ContentTypes;
import com.backblaze.b2.client.contentSources.B2FileContentSource;
import com.backblaze.b2.client.exceptions.B2Exception;
import com.backblaze.b2.client.structures.B2UploadFileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.spbstu.mvp.entity.User;
import ru.spbstu.mvp.entity.UserPhoto;
import ru.spbstu.mvp.exception.AnnouncementNotFoundException;
import ru.spbstu.mvp.exception.PhotoUploadException;
import ru.spbstu.mvp.repository.UserPhotoRepository;
import ru.spbstu.mvp.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPhotoService {
    private static final String USER_AGENT = "Gleb"; // как я понимаю любой
    private static final String APP_KEY = "K003xFf2gDsRRZSg+vq/ZG/zC1rcFqo"; // applicationKey:
    private static final String APP_KEY_ID = "0035c3c5567d6ce0000000005"; // keyID
    private static final String bucketId = "658c639c65a5e6278da60c1e"; //  Сегмент кода:

    private static final String bucketUrl = "https://f003.backblazeb2.com/file/gleb-bucket/";

    private final UserPhotoRepository userPhotoRepository;
    private final UserRepository userRepository;

    private void uploadUserPhoto(MultipartFile photo, User user) {
        B2StorageClient client = B2StorageClientFactory.createDefaultFactory().create(APP_KEY_ID, APP_KEY, USER_AGENT);
        try {
            File tempFile = File.createTempFile("upload", null);
            photo.transferTo(tempFile);
            final B2ContentSource source = B2FileContentSource.build(tempFile);
            final String fileName = "demo/user/" + photo.getOriginalFilename();
            B2UploadFileRequest request = B2UploadFileRequest.builder(bucketId, fileName, B2ContentTypes.B2_AUTO, source).setCustomField("color", "blue").build();
            client.uploadSmallFile(request);
            UserPhoto currentPhoto = UserPhoto.builder().photoUrl(bucketUrl + fileName).user(user).build();
            userPhotoRepository.save(currentPhoto);
        } catch (B2Exception | IOException e) {
            throw new PhotoUploadException("Photo upload exception");
        }
    }

    public void uploadUserPhotos(List<MultipartFile> photos, int userId) {
        var optionalFlat = userRepository.findById(userId);
        if (optionalFlat.isPresent()) {
            photos.forEach(photo -> uploadUserPhoto(photo, optionalFlat.get()));
        } else {
            throw new AnnouncementNotFoundException("Announcement not found");
        }
    }
}
