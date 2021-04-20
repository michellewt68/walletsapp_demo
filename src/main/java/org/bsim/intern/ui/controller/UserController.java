package org.bsim.intern.ui.controller;

import org.bsim.intern.service.iservice.IUserInterface;
import org.bsim.intern.shared.dto.UserDTO;
import org.bsim.intern.ui.model.request.UserRequest;
import org.bsim.intern.ui.model.response.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    IUserInterface userService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
//   pake list krn getusers lebih dari 1 users
    public List<UserResponse> getUsers(){
        return null;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserResponse addNewUser(@RequestBody UserRequest user){
        ModelMapper mapper=new ModelMapper();

        //tf dari userRequest ke object UserDTO
        UserDTO userDTO= mapper.map(user, UserDTO.class);
        UserDTO createdUser=userService.addNewData(userDTO);

        //dari userDTO ke response
        UserResponse response=mapper.map(createdUser, UserResponse.class);

        return response;
    }
}
