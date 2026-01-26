package net.engineeringdigest.journalApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User{
    @NonNull
    private  String username;
    @NonNull
    private  String password;

    private  String email;
    private  Boolean sentimentAnalysis;
}
