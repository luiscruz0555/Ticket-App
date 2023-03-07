package com.cognizant.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//import javax.persistence.*;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInput {

    @Id
    private String id;

    private String firstname;

    private String lastname;

    private String address;

    private String issue;

    private String ticketstatus;

    private String authorId;

}

