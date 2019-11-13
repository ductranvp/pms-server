package vn.ptit.pms.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.pms.service.FileService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/file/")
public class FileResource {
    @Autowired
    FileService fileService;

    @GetMapping("/download")
    public InputStreamResource downloadFile(@RequestParam("fileId") Long fileId, HttpServletResponse response) throws IOException {
        return fileService.downloadFile(response,fileId);
    }
}
