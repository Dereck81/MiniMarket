package pe.edu.utp.marketapi.Controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/images")
@Tag(name = "Images", description = "Images API")
@SecurityRequirement(name = "bearer-key")
public class ImagesController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping
    public ResponseEntity<String> saveImage(@RequestParam("image") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
            }

            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String extension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename())).toLowerCase();
            if (!List.of("jpg", "jpeg", "png", "gif").contains(extension)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File format not allowed");
            }

            String uniqueFileName = generateUniqueFileName(extension);
            Path filePath = Paths.get(uploadDir, uniqueFileName);

            Files.write(filePath, file.getBytes());
            return new ResponseEntity<>(uniqueFileName, HttpStatus.CREATED);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving file: " + e.getMessage());
        }
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            if (filename.contains("..")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            Path file = Paths.get(uploadDir).resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(file);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private String generateUniqueFileName(String extension) {
        return "market-" + System.currentTimeMillis() + "-" + UUID.randomUUID() + "." + extension;
    }

    private String getFileExtension(@NotNull String originalFileName) {
        int i = originalFileName.lastIndexOf('.');
        return (i >= 0) ? originalFileName.substring(i + 1) : "";
    }
}