package br.com.simplified_elo_payment.account.infrastructure.entity;

import br.com.simplified_elo_payment.account.domain.entity.AccountEntity;
import br.com.simplified_elo_payment.account.domain.valueobjects.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "account")
@Table(name = "account")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class AccountJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_account")
    private Long id;
    @Column(name = "id_user")
    private Long userId;
    private BigDecimal balance;

    @ElementCollection(targetClass = PaymentType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "account_payment_types", joinColumns = @JoinColumn(name = "id_account"))
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private Set<PaymentType> paymentTypesAccepted = new HashSet<>();

    //Constructor to create a new account
    public AccountJpaEntity(Long userId, BigDecimal initialValue, Set<PaymentType> paymentTypes) {
        this.setUserId(userId);
        this.setBalance(initialValue);
        this.setPaymentTypesAccepted(paymentTypes);
    }

    //Constructor to create a new account
    public AccountJpaEntity(AccountEntity domainEntity) {
        this.setUserId(domainEntity.getUserId());
        this.setBalance(domainEntity.getBalance());
        this.setPaymentTypesAccepted(domainEntity.getPaymentType());
    }

    public AccountJpaEntity(Long id, Long userId, BigDecimal initialValue) {
        this.setId(id);
        this.setUserId(userId);
        this.setBalance(initialValue);
    }
}