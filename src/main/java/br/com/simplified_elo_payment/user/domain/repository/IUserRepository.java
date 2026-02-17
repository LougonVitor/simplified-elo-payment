package br.com.simplified_elo_payment.user.domain.repository;

import br.com.simplified_elo_payment.user.domain.entity.UserEntity;

public interface IUserRepository {
    UserEntity createUser(UserEntity entity);
    UserEntity findByUsername(String username);
}