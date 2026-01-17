package mate.academy.rickandmorty.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mate.academy.rickandmorty.dto.external.ExternalResponseDto;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.exception.InvalidConnectionException;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.service.CharacterService;
import mate.academy.rickandmorty.service.ImportService;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
@Service
@Log4j2
public class ImportServiceImpl implements ImportService {
    private static final String CHARACTER_URI = "https://rickandmortyapi.com/api/character";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CharacterService characterService;
    private final CharacterMapper characterMapper;

    @Override
    public void importCharacters() {
        log.info("Importing characters from external service...");
        nextResults(sendRequest(CHARACTER_URI)).forEach(characterService::save);
    }

    private List<CharacterDto> nextResults(ExternalResponseDto responseDto) {
        if (responseDto == null) {
            return List.of();
        }
        String next = getNextUriFromResponse(responseDto);
        if (next == null) {
            return List.of();
        }
        List<CharacterDto> results = new ArrayList<>(getResultsFromResponse(responseDto));
        ExternalResponseDto nextResponseDto = sendRequest(next);
        List<CharacterDto> nextResults = nextResults(nextResponseDto);
        results.addAll(nextResults);
        return results;
    }

    private String getNextUriFromResponse(ExternalResponseDto responseDto) {
        return responseDto.info().next();
    }

    private List<CharacterDto> getResultsFromResponse(ExternalResponseDto responseDto) {
        return responseDto.results().stream()
                .map(characterMapper::toInternal)
                .toList();
    }

    private ExternalResponseDto sendRequest(String uri) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();
        log.info("HTTP request created: {}", request);
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        String body;
        try {
            body = httpClient.send(request, bodyHandler).body();
            Thread.sleep(50); // 1000 ms / 50 ms = 200 (requests per sec)
        } catch (IOException e) {
            throw new InvalidConnectionException("Cannot send request or receive response", e);
        } catch (InterruptedException e) {
            throw new InvalidConnectionException(
                    "Sending request or receiving response was interrupted", e
            );
        }
        ExternalResponseDto result = objectMapper.readValue(body, ExternalResponseDto.class);
        log.info("Received response: {}", result);
        return result;
    }
}
