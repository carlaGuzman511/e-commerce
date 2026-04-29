package com.kiosko.media_service.provider;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface StorageProvider {

    String upload(MultipartFile file, String folder);

    Map<String, Object> uploadAndReturn(MultipartFile file, String folder);

    boolean delete(String identifier);
}