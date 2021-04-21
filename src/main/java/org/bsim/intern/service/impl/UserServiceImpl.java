package org.bsim.intern.service.impl;

import org.bsim.intern.io.entity.UserEntity;
import org.bsim.intern.io.irepository.UserRepository;
import org.bsim.intern.service.iservice.IUserInterface;
import org.bsim.intern.shared.dto.UserDTO;
import org.bsim.intern.shared.utils.GenerateRandomPublicId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserInterface {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GenerateRandomPublicId generateRandomPublicId;

    @Override
    public List<UserDTO> getListUser() {

        List<UserDTO> value= new ArrayList<>();
        ModelMapper mapper=new ModelMapper();

        //get all users from db
        List<UserEntity> allUsers= userRepository.findAll();

        //List<UserEntity> --> List<UserDTO>
        for (UserEntity userEntity : allUsers){
            value.add(mapper.map(userEntity, UserDTO.class));
        }

        return value;
    }

    @Override
    public UserDTO getUserByUsername(String username) {

        UserEntity getUser = userRepository.findByUsername(username);
        if(getUser==null) return null;

        return new ModelMapper().map(getUser, UserDTO.class);
    }

    @Override
    public UserDTO addNewData(UserDTO user) {

        user.setUserId(generateRandomPublicId.generateUserId(30)); //generate userid

        ModelMapper mapper=new ModelMapper();

        //tf data dari service ke repository (UserDTO --> UserEntity)
        UserEntity entity= mapper.map(user, UserEntity.class);

        //save data (store data)
        UserEntity storedData= userRepository.save(entity);

        //UserEntity --> UserDTO (untuk return value)
        UserDTO returnValue=mapper.map(storedData, UserDTO.class);

        return returnValue;
    }

}
