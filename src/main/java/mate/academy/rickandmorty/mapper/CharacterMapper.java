package mate.academy.rickandmorty.mapper;

import mate.academy.rickandmorty.config.MapperConfig;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.model.CharacterInfo;
import mate.academy.rickandmorty.model.Gender;
import mate.academy.rickandmorty.model.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface CharacterMapper {
    @Mapping(source = "gender", target = "gender", qualifiedByName = "recognizeGender")
    @Mapping(source = "status", target = "status", qualifiedByName = "recognizeStatus")
    CharacterInfo toModel(CharacterDto characterDto);

    @Mapping(source = "id", target = "externalId")
    CharacterDto toInternal(mate.academy.rickandmorty.dto.external.CharacterDto characterDto);

    @Mapping(source = "gender", target = "gender", qualifiedByName = "formatGender")
    @Mapping(source = "status", target = "status", qualifiedByName = "formatStatus")
    CharacterDto toDto(CharacterInfo characterInfo);

    @Named("recognizeStatus")
    default Status recognizeStatus(String status) {
        if (status == null || status.isBlank()) {
            return Status.UNKNOWN;
        }
        status = status.toUpperCase();
        return Status.valueOf(status);
    }

    @Named("recognizeGender")
    default Gender recognizeGender(String gender) {
        if (gender == null || gender.isBlank()) {
            return Gender.UNKNOWN;
        }
        gender = gender.toUpperCase();
        return Gender.valueOf(gender);
    }

    @Named("formatStatus")
    default String formatStatus(Status status) {
        if (status == null) {
            status = Status.UNKNOWN;
        }
        String format = status.name();
        format = format.toLowerCase();
        return java.lang.Character.toUpperCase(format.charAt(0)) + format.substring(1);
    }

    @Named("formatGender")
    default String formatGender(Gender gender) {
        if (gender == null) {
            gender = Gender.UNKNOWN;
        }
        String format = gender.name();
        format = format.toLowerCase();
        return java.lang.Character.toUpperCase(format.charAt(0)) + format.substring(1);
    }
}
