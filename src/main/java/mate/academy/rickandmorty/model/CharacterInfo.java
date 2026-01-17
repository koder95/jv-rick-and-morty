package mate.academy.rickandmorty.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CharacterInfo {
    @Id
    private Long id;
    private Long externalId;
    private String name;
    private Status status;
    private Gender gender;
}
