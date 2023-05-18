package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserRegisterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface UserMapper {

    /*
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "birthdate", source = "birthdate")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "areaCode", source = "areaCode")
    @Mapping(target = "cityName", source = "cityName")
    @Mapping(target = "password", source = "password")*/
    @Named("user")
    @Mapping(target = "id", source = "id")
    UserRegisterDto entityToDto(ApplicationUser user);

    /*
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "birthdate", source = "birthdate")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "areaCode", source = "areaCode")
    @Mapping(target = "cityName", source = "cityName")
    @Mapping(target = "password", source = "password")
    */

    @Mapping(target = "id", source = "id")
    ApplicationUser dtoToEntity(UserRegisterDto userRegisterDto);


}
