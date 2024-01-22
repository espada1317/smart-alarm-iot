package rest.arduino.smartalarm.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rest.arduino.smartalarm.domain.dto.UserDtoSaveRequest;
import rest.arduino.smartalarm.domain.entity.User;

@Mapper
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User userDtoRequestToUser(UserDtoSaveRequest userDtoRequest);

}
