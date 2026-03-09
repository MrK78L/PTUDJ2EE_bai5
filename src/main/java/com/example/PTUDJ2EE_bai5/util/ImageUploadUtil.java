package com.example.PTUDJ2EE_bai5.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class ImageUploadUtil {
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final String[] ALLOWED_EXTENSIONS = {"jpg", "jpeg", "png", "gif", "webp"};

    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File is empty");
        }

        // Validate file size
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IOException("File size exceeds 5MB limit");
        }

        // Validate file extension
        String originalFilename = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);
        if (!isAllowedExtension(fileExtension)) {
            throw new IOException("File type not allowed. Allowed types: jpg, jpeg, png, gif, webp");
        }

        // Create uploads directory if not exists
        File uploadsDir = new File(UPLOAD_DIR);
        if (!uploadsDir.exists()) {
            uploadsDir.mkdirs();
        }

        // Generate unique filename
        String uniqueFilename = UUID.randomUUID().toString() + "." + fileExtension;
        Path filepath = Paths.get(UPLOAD_DIR + uniqueFilename);

        // Save file
        Files.write(filepath, file.getBytes());

        return uniqueFilename;
    }

    public void deleteImage(String filename) throws IOException {
        if (filename == null || filename.isEmpty()) {
            return;
        }

        Path filepath = Paths.get(UPLOAD_DIR + filename);
        if (Files.exists(filepath)) {
            Files.delete(filepath);
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }

    private boolean isAllowedExtension(String extension) {
        for (String ext : ALLOWED_EXTENSIONS) {
            if (ext.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
}
