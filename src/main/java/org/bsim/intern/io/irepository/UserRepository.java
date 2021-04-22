package org.bsim.intern.io.irepository;

import org.bsim.intern.io.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    //nambah method yg berkaitan dgn userEntity
    UserEntity findByUsername(String username);

    UserEntity findByUserid(String userid);

}
