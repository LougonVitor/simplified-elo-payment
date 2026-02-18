package br.com.simplified_elo_payment.user.infrastructure.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "users")
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Username cannot be null!")
    private String username;
    @NotNull(message = "Email cannot be null!")
    @Column(unique = true)
    private String email;
    @NotNull(message = "Password cannot be null!")
    private String password;
    @NotNull(message = "Role cannot be null!")
    private String role;
    @Column(name = "dh_creation")
    private LocalDateTime createdAt;
}