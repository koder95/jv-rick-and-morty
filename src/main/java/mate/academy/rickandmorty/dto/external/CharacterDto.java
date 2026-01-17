package mate.academy.rickandmorty.dto.external;

import java.time.LocalDateTime;

public record CharacterDto(
        Long id,
        String name,
        String status,
        String species,
        String type,
        String gender,
        String image,
        String url,
        LocalDateTime created
) {
}
