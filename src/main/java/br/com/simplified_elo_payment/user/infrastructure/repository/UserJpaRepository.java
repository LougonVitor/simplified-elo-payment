package br.com.simplified_elo_payment.user.infrastructure.repository;

import br.com.simplified_elo_payment.user.infrastructure.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
    UserJpaEntity findByUsername(String username);
}