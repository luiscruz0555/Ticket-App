package com.cognizant.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInputDTO {

    private String id;

    private String firstname;

    private String lastname;

    private String address;

    private String issue;

    private String ticketstatus;

    private String authorId;



}
