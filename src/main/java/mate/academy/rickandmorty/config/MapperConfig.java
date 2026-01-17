package mate.academy.rickandmorty.config;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

@org.mapstruct.MapperConfig(
        componentModel = "spring",
        implementationPackage = "<PACKAGE_NAME>.impl",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public class MapperConfig {
}
