package br.com.simplified_elo_payment.user.infrastructure.repository;

import br.com.simplified_elo_payment.common.exception.UserNotFoundException;
import br.com.simplified_elo_payment.user.domain.entity.UserEntity;
import br.com.simplified_elo_payment.user.domain.repository.IUserRepository;
import br.com.simplified_elo_payment.user.domain.valueobject.UserRole;
import br.com.simplified_elo_payment.user.infrastructure.entity.UserJpaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class UserJpaAdapter implements IUserRepository {
    @Autowired
    private UserJpaRepository userJpaRepository;

    @Override
    public UserEntity createUser(UserEntity entity) {
        UserJpaEntity userJpaEntity = new UserJpaEntity();

        userJpaEntity.setUsername(entity.getUsername());
        userJpaEntity.setEmail(entity.getEmail());
        userJpaEntity.setPassword(entity.getPassword());
        userJpaEntity.setCreatedAt(LocalDateTime.now());
        userJpaEntity.setRole(entity.getRole().getRole());

        UserJpaEntity createdUser = this.userJpaRepository.save(userJpaEntity);

        return new UserEntity(
                createdUser.getId(),
                createdUser.getUsername(),
                createdUser.getEmail(),
                createdUser.getPassword(),
                UserRole.getEnumValue(createdUser.getRole()),
                createdUser.getCreatedAt()
        );
    }

    @Override
    public UserEntity findByUsername(String username) {
        UserJpaEntity userJpaEntity = this.userJpaRepository.findByUsername(username);

        if(userJpaEntity != null) {
            return new UserEntity(
                    userJpaEntity.getId(),
                    userJpaEntity.getUsername(),
                    userJpaEntity.getEmail(),
                    userJpaEntity.getPassword(),
                    UserRole.getEnumValue(userJpaEntity.getRole()),
                    userJpaEntity.getCreatedAt()
            );
        } else {
            return null;
        }
    }
}