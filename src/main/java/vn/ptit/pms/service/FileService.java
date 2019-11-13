package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.Attachment;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class FileService {
    @Autowired
    AttachmentService attachmentService;

    public InputStreamResource downloadFile(HttpServletResponse response, Long fileId) throws IOException {
        Attachment attachment = attachmentService.getOneById(fileId);
        InputStream input;
        input = new URL(attachment.getUrl()).openStream();
        response.setCharacterEncoding("UTF-8");
        String fileName = URLEncoder.encode(attachment.getName(), String.valueOf(StandardCharsets.UTF_8));
        fileName = fileName.replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        InputStreamResource resource = new InputStreamResource(input);
        return resource;
    }
}
