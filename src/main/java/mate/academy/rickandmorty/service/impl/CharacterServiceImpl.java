package mate.academy.rickandmorty.service.impl;

import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.CharacterInfo;
import mate.academy.rickandmorty.repository.CharacterRepository;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class CharacterServiceImpl implements CharacterService {
    private final Random random = new Random();
    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;

    @Override
    public CharacterDto save(CharacterDto characterDto) {
        CharacterInfo model = characterMapper.toModel(characterDto);
        CharacterInfo saved = characterRepository.save(model);
        return characterMapper.toDto(saved);
    }

    @Override
    public CharacterDto random() {
        long lastId = characterRepository.getLastId();
        long randomId = random.nextLong(lastId);
        while (!characterRepository.existsById(randomId)) {
            randomId = random.nextLong(lastId);
        }
        CharacterInfo byId = characterRepository.getReferenceById(randomId);
        return characterMapper.toDto(byId);
    }

    @Override
    public Page<CharacterDto> searchByName(String name, Pageable pageable) {
        String likeLowerCase = '%' + name.toLowerCase() + '%';
        return characterRepository.findByNameLikeIgnoreCase(likeLowerCase, pageable)
                .map(characterMapper::toDto);
    }
}
