package net.engineeringdigest.journalApp.Entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import  java.util.*;

@Data
@Document(collection = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private  String username;
    @NonNull
    private  String password;

    private  String email;
    private  Boolean sentimentAnalysis;


    @DBRef
    private List<JournalEntry> journalEntries= new ArrayList<>();
    private List<String> roles;

}
