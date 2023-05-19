package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserCreateDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserRegisterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface UserMapper {


    @Named("user")
    @Mapping(target = "id", source = "id")
    UserRegisterDto entityToDto(ApplicationUser user);

    @Named("user")
    @Mapping(target = "id", source = "id")
    UserCreateDto entityToUserCreateDto(ApplicationUser user);


    @Mapping(target = "id", source = "id")
    ApplicationUser dtoToEntity(UserRegisterDto userRegisterDto);

    @Mapping(target = "id", source = "id")
    ApplicationUser userCreateDtoToEntity(UserCreateDto userCreateDto);


}
