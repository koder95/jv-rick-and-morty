package mate.academy.rickandmorty.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "CharacterInfo information storage")
@RequiredArgsConstructor
@RestController
@RequestMapping("/character")
public class CharacterController {
    private final CharacterService characterService;

    @Operation(summary = "Get a random character")
    @GetMapping("/random")
    public CharacterDto random() {
        return characterService.random();
    }

    @Operation(summary = "Find character by name")
    @GetMapping("/search")
    public Page<CharacterDto> search(@RequestParam String name, Pageable pageable) {
        return characterService.searchByName(name, pageable);
    }
}
