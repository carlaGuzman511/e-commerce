package com.kiosko.media_service.provider;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kiosko.media_service.constants.CloudinaryConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloudinaryStorageProvider implements StorageProvider {

    private final Cloudinary cloudinary;

    @Override
    public String upload(MultipartFile file, String folder) {
        try {
            HashMap<Object, Object> options = new HashMap<>();
            options.put(CloudinaryConstants.Options.FOLDER, folder);

            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get(CloudinaryConstants.Results.PUBLIC_ID);

            return cloudinary.url().secure(true).generate(publicId);
        } catch (IOException e) {
            log.error(CloudinaryConstants.Logs.UPLOAD_ERROR, e.getMessage());
            throw new RuntimeException(CloudinaryConstants.Errors.UPLOAD_FAILED, e);
        }
    }

    public Map<String, Object> uploadAndReturn(MultipartFile file, String folder) {
        try {
            Map<String, Object> options = new HashMap<>();
            options.put(CloudinaryConstants.Options.FOLDER, folder);
            options.put(CloudinaryConstants.Options.USE_FILENAME, true);
            options.put(CloudinaryConstants.Options.UNIQUE_FILENAME, true);
            options.put(CloudinaryConstants.Options.OVERWRITE, false);

            return cloudinary.uploader().upload(file.getBytes(), options);
        } catch (IOException e) {
            throw new RuntimeException(CloudinaryConstants.Errors.UPLOAD_ERROR_SPANISH, e);
        }
    }

    @Override
    public boolean delete(String publicId) {
        try {
            Map<String, Object> result = cloudinary.uploader()
                    .destroy(publicId, ObjectUtils.asMap(CloudinaryConstants.Options.INVALIDATE, true));

            log.info(CloudinaryConstants.Logs.DELETE_INFO, result);

            Object status = result.get(CloudinaryConstants.Results.RESULT);
            return CloudinaryConstants.Results.OK.equals(status)
                    || CloudinaryConstants.Results.NOT_FOUND.equals(status);

        } catch (IOException e) {
            log.error(CloudinaryConstants.Logs.DELETE_ERROR, e.getMessage());
            throw new RuntimeException(CloudinaryConstants.Errors.DELETE_FAILED, e);
        }
    }

    private String extractPublicId(String url) {
        try {
            String base = url.split("/upload/")[1];
            String pathPart = base.substring(base.indexOf("/") + 1);
            return pathPart.replaceFirst("\\.[^.]+$", "");
        } catch (Exception e) {
            throw new IllegalArgumentException(CloudinaryConstants.Errors.INVALID_URL + url);
        }
    }
}
