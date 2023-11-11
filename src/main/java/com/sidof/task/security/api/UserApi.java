package com.sidof.task.security.api;

import com.sidof.task.model.Response;
import com.sidof.task.security.model.Appuser;
import com.sidof.task.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * @Author sidof
 * @Since 10/3/23
 * @Version v1.0
 * @YouTube @sidof8065
 */
@RestController
@RequestMapping("api/v1/task/user")
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;


    @PutMapping("/edit")
    public ResponseEntity<Response> updateUser(@RequestBody Appuser userToUpdate) {
        Response response = Response.builder()
                .timeStamp(now())
                .data(of("update", userService.edit(userToUpdate)))
                .message("edit user")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Appuser>> defaultUsers() {
        List<Appuser> users = userService.getUsers();

        return new ResponseEntity<>(users,OK);
    }

}

