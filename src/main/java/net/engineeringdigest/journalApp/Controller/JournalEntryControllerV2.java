package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user=userService.findByUserName(username);
        List<JournalEntry> list=user.getJournalEntries();
        if(list!=null&&list.size()>0){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntryList){



      try {
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          String username = authentication.getName();
          journalEntryList.setDate(LocalDate.now());
          journalEntryService.saveEntry(journalEntryList,username);
            return new ResponseEntity<>(journalEntryList, HttpStatus.OK);
      } catch (Exception e) {
          return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
      }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId myId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user=userService.findByUserName(username);
        List<JournalEntry>collect= user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
       if(!collect.isEmpty()){
           Optional<JournalEntry>entry=journalEntryService.getById(myId);
           if(entry.isPresent()){
               return new ResponseEntity<>(entry.get(), HttpStatus.OK);

           }
       }

        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> deleteEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        journalEntryService.deleteById(myId,username);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{myId}")

    public ResponseEntity<?> updateEntryById(@PathVariable ObjectId myId,
                                             @RequestBody JournalEntry updatedEntry) {

        // Get currently authenticated username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Find user and their entries
        User user = userService.findByUserName(username);

        // Find the journal entry with the given ID
        Optional<JournalEntry> optionalEntry = user.getJournalEntries()
                .stream()
                .filter(entry -> entry.getId().equals(myId))
                .findFirst();

        if (optionalEntry.isPresent()) {
            JournalEntry oldEntry = optionalEntry.get();

            // Update title if provided
            if (updatedEntry.getTitle() != null && !updatedEntry.getTitle().isEmpty()) {
                oldEntry.setTitle(updatedEntry.getTitle());
            }

            // Update content if provided
            if (updatedEntry.getContent() != null && !updatedEntry.getContent().isEmpty()) {
                oldEntry.setContent(updatedEntry.getContent());
            }

            // Save updated entry (assuming you have a service/repo for this)
            // e.g., journalEntryService.saveEntry(oldEntry);
            journalEntryService.saveEntry(oldEntry);

            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }

        return new ResponseEntity<>("Entry not found", HttpStatus.NOT_FOUND);
    }


}
