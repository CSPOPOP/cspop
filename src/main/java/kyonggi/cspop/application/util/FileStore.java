package kyonggi.cspop.application.util;

import kyonggi.cspop.domain.uploadfile.FinalFormUploadFile;
import kyonggi.cspop.domain.uploadfile.InterimFormUploadFile;
import kyonggi.cspop.domain.uploadfile.NoticeBoardUploadFile;
import kyonggi.cspop.domain.uploadfile.OtherFormUploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public List<NoticeBoardUploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<NoticeBoardUploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    public NoticeBoardUploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = urlEncodingFileName(multipartFile);
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new NoticeBoardUploadFile(originalFilename, storeFileName);
    }

    public InterimFormUploadFile storeInterimFile(MultipartFile multipartFile) throws IOException {

        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = urlEncodingFileName(multipartFile);
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new InterimFormUploadFile(originalFilename, storeFileName);
    }

    public FinalFormUploadFile storeFinalFile(MultipartFile multipartFile) throws IOException {

        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = urlEncodingFileName(multipartFile);
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new FinalFormUploadFile(originalFilename, storeFileName);
    }

    public OtherFormUploadFile storeOtherFile(MultipartFile multipartFile) throws IOException {

        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = urlEncodingFileName(multipartFile);
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new OtherFormUploadFile(originalFilename, storeFileName);
    }

    private static String urlEncodingFileName(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename()
                .replace(" ", "_")
                .replace("?", "_")
                .replace("!", "_")
                .replace("[", "_")
                .replace("]", "_")
                .replace("@", "_");
        return originalFilename;
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}

