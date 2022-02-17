package org.example.Lesson4;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class MaxDTO {

    private String id;
    private String email;
    @JsonProperty ("first_name")
    private String firstName;
    @JsonProperty ("last_name")
    private String lastName;
    private String avatar;
}
