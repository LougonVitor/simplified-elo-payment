package br.com.simplified_elo_payment.account.infrastructure.repository;

import br.com.simplified_elo_payment.account.infrastructure.entity.AccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountJpaEntity, Long> {
    Optional<AccountJpaEntity> findByUserId(Long id);
}