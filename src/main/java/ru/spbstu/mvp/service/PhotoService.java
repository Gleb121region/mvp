package ru.spbstu.mvp.service;

import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.B2StorageClientFactory;
import com.backblaze.b2.client.contentSources.B2ContentSource;
import com.backblaze.b2.client.contentSources.B2ContentTypes;
import com.backblaze.b2.client.contentSources.B2FileContentSource;
import com.backblaze.b2.client.exceptions.B2Exception;
import com.backblaze.b2.client.structures.B2UploadFileRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class PhotoService {
    private static final String USER_AGENT = "Gleb"; // как я понимаю любой
    private static final String APP_KEY = "K003xFf2gDsRRZSg+vq/ZG/zC1rcFqo"; // applicationKey:
    private static final String APP_KEY_ID = "0035c3c5567d6ce0000000005"; // keyID
    private static final String bucketId = "658c639c65a5e6278da60c1e"; //  Сегмент кода:

    public String uploadPhoto(MultipartFile file) {
        B2StorageClient client = B2StorageClientFactory.createDefaultFactory().create(APP_KEY_ID, APP_KEY, USER_AGENT);
        try {
            File tempFile = File.createTempFile("upload", null);
            file.transferTo(tempFile);

            final B2ContentSource source = B2FileContentSource.build(tempFile);
            final String fileName = "demo/" + file.getOriginalFilename();
            B2UploadFileRequest request = B2UploadFileRequest
                    .builder(bucketId, fileName, B2ContentTypes.B2_AUTO, source)
                    .setCustomField("color", "blue")
                    .build();
            var file1 = client.uploadSmallFile(request);
            System.out.println("uploaded " + file1);

            return "File uploaded successfully: " + fileName;
        } catch (IOException | B2Exception e) {
            return "Error occurred while uploading file: " + e.getMessage();
        }
    }
}
