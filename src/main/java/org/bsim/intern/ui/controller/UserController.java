package org.bsim.intern.ui.controller;

import org.bsim.intern.service.iservice.IUserInterface;
import org.bsim.intern.shared.dto.UserDTO;
import org.bsim.intern.ui.model.request.UserRequest;
import org.bsim.intern.ui.model.response.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    IUserInterface userService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    //pake list krn getusers lebih dari 1 users
    //controller relate ke service
    public List<UserResponse> getUsers(){
        List<UserResponse> listUser =new ArrayList<>();
        ModelMapper mapper=new ModelMapper();

        List<UserDTO> users=userService.getListUser();
        for(UserDTO userDTO : users){
            listUser.add(mapper.map(userDTO, UserResponse.class));
        }

        return listUser;
    }

    @GetMapping(path = "/{username}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserResponse getUserByUsername(@PathVariable String username){

        UserDTO getUser= userService.getUserByUsername(username);
        if (getUser==null) return null;

        return new ModelMapper().map(getUser, UserResponse.class);
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
