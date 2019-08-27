package vn.ptit.qldaserver.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.qldaserver.dto.LoginDto;

@RestController
@RequestMapping("api/auth/")
public class AuthResource {

    @PostMapping
    public ResponseEntity login(@RequestBody LoginDto loginDto){
        return null;
    }



}
