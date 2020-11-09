package com.eureka_labs.challenge.domain.provider;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MarvelResponse {
    @JsonProperty("data")
    private CharacterDataContainer data;
}
