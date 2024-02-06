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
import ru.spbstu.mvp.entity.Flat;
import ru.spbstu.mvp.entity.Photo;
import ru.spbstu.mvp.exception.FlatNotFoundException;
import ru.spbstu.mvp.exception.PhotoUploadException;
import ru.spbstu.mvp.repository.FlatRepository;
import ru.spbstu.mvp.repository.PhotoRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private static final String USER_AGENT = "Gleb"; // как я понимаю любой
    private static final String APP_KEY = "K003xFf2gDsRRZSg+vq/ZG/zC1rcFqo"; // applicationKey:
    private static final String APP_KEY_ID = "0035c3c5567d6ce0000000005"; // keyID
    private static final String bucketId = "658c639c65a5e6278da60c1e"; //  Сегмент кода:

    private static final String bucketUrl = "https://f003.backblazeb2.com/file/gleb-bucket/";

    private final PhotoRepository photoRepository;
    private final FlatRepository flatRepository;

    private void uploadFlatPhoto(MultipartFile photo, Flat flat) {
        B2StorageClient client = B2StorageClientFactory.createDefaultFactory().create(APP_KEY_ID, APP_KEY, USER_AGENT);
        try {
            File tempFile = File.createTempFile("upload", null);
            photo.transferTo(tempFile);
            final B2ContentSource source = B2FileContentSource.build(tempFile);
            final String fileName = "demo/" + photo.getOriginalFilename();
            B2UploadFileRequest request = B2UploadFileRequest.builder(bucketId, fileName, B2ContentTypes.B2_AUTO, source).setCustomField("color", "blue").build();
            client.uploadSmallFile(request);
            Photo currentPhoto = Photo.builder().photoUrl(bucketUrl + fileName).flat(flat).build();
            photoRepository.save(currentPhoto);
        } catch (B2Exception | IOException e) {
            throw new PhotoUploadException("Photo upload exception");
        }
    }

    public void uploadFlatPhotos(List<MultipartFile> photos, int flatId) {
        Optional<Flat> optionalFlat = flatRepository.findById(flatId);
        if (optionalFlat.isPresent()) {
            photos.forEach(photo -> uploadFlatPhoto(photo, optionalFlat.get()));
        } else {
            throw new FlatNotFoundException("Flat not found");
        }
    }
}
