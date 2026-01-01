package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Controller.JournalEntryControllerV2;
import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import  java.util.*;

@Slf4j
@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;


//    private  static Logger logger= LoggerFactory.getLogger(JournalEntryControllerV2.class);
        @Transactional
    public void saveEntry(JournalEntry journalEntry, String username) {
        try {
            JournalEntry save = journalEntryRepository.save(journalEntry);
            User user = userService.findByUserName(username);
            user.getJournalEntries().add(save);
            // user.setUsername(null);// This is creating problem
            userService.saveUser(user);
        } catch (Exception e) {
            log.info("oajoajdxoawdo");

        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public void deleteById(ObjectId id, String username) {
        try {
            User byUserName = userService.findByUserName(username);
            boolean removed= byUserName.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
            if(removed){
                userService.saveUser(byUserName);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("this is error",e);
        }
    }



}