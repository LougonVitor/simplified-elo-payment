package br.com.simplified_elo_payment.user.application.mapper;

import br.com.simplified_elo_payment.user.application.dto.CreateUserCommand;
import br.com.simplified_elo_payment.user.application.dto.UserServiceResponseDto;
import br.com.simplified_elo_payment.user.domain.entity.UserEntity;

public class UserApplicationMapper {
    /**
     * Maps a {@link CreateUserCommand} data transfer object to a new {@link UserEntity}.
     * <p>
     * This factory method encapsulates the conversion logic required to initialize a
     * persistence-ready user. It ensures that the password is provided in its
     * encrypted form and roles are converted to the internal domain representation.
     * </p>
     *
     * @param command The command object containing the validated registration details.
     * @return A fresh {@code UserEntity} instance ready for persistence.
     * @throws IllegalArgumentException if mandatory fields in the command are null.
     * @see UserEntity
     * @see CreateUserCommand
     */
    public static UserEntity toConstructedEntity(CreateUserCommand command) {
        return new UserEntity(
                command.username()
                , command.email()
                , command.getEncryptedPassword()
                , command.getRoleAsEnum()
        );
    }

    /**
     * Converts a {@link UserEntity} persistence object into a {@link UserServiceResponseDto}.
     * <p>
     * This method acts as a data projection, extracting only the non-sensitive
     * fields required for external consumption. It ensures that internal
     * implementation details (such as passwords or complex relationships)
     * remain encapsulated within the service layer.
     * </p>
     *
     * @param entity The source entity retrieved from the database.
     * @return A lightweight DTO containing the user's public-facing information.
     * @throws NullPointerException if the provided {@code entity} is null.
     */
    public static UserServiceResponseDto toResponseDto(UserEntity entity) {
        return new UserServiceResponseDto(
                entity.getId()
                , entity.getUsername()
                , entity.getEmail()
        );
    }
}