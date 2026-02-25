package gendev.it.serenity.common.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;
import org.springframework.web.multipart.MultipartFile;

import gendev.it.serenity.common.dto.FileDTO;

public class FileHandler {

    public String base_path = "src/main/resources/directory";
    // function misave list ana fichier
    public void saveFiles(String id, List<MultipartFile> files) throws IOException {
        String path = createDir(id);
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                saveFile(path, fileName, file);
            }
        }
    }

    public String createDir(String id) {
        String path = base_path + "/" + id;
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return path;
    }
    // function misave fichier ray
    public void saveFile(String fileDir, String fileName,
            MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(fileDir);
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    
    // function apesaina irécuperena fichier ary mamadika azy ho lasa byte
    public FileDTO getFile(String path) throws IOException {
        File file = new File(path);
        FileDTO dto = new FileDTO();
        Path route = Paths.get(path);
        dto.setData(Files.readAllBytes(route));
        dto.setNameFile(file.getName());
        dto.setType(path);
        return dto;
    }
}
