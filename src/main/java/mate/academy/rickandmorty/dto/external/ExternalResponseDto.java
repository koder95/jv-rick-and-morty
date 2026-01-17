package mate.academy.rickandmorty.dto.external;

import java.util.List;

public record ExternalResponseDto(ExternalResponseInfoDto info, List<CharacterDto> results) {
}
