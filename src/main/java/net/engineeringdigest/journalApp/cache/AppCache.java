package net.engineeringdigest.journalApp.cache;

import jakarta.annotation.PostConstruct;
import net.engineeringdigest.journalApp.Entity.ConfigJournalAppEntity;
import net.engineeringdigest.journalApp.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    public  Map<String,String> App_Cache;
    @Autowired
    public ConfigJournalAppRepository configJournalAppRepository;

    @PostConstruct
    public void  init(){
        App_Cache=new HashMap<>();
        List<ConfigJournalAppEntity> all= configJournalAppRepository.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity:all){
            App_Cache.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
        }

    }
}
