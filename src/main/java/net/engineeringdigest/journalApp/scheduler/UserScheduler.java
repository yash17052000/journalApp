package net.engineeringdigest.journalApp.scheduler;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.enums.Sentiment;
import net.engineeringdigest.journalApp.model.SentimentData;
import net.engineeringdigest.journalApp.repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {


    @Autowired
    private UserRepositoryImpl userRepository;



    @Autowired
    private EmailService  emailService;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private SentimentData sentimentData;

    @Autowired
    private AppCache appCache;
//    @Scheduled(cron = "0 0 9 * * SUN")
@Scheduled(cron = "0 * * ? * *")
    public  void fetchUserAndSendMail(){
        List<User> userForSA = userRepository.getUserForSA();
        for(User user:userForSA){
            List<JournalEntry> journalEntries = user.getJournalEntries();

            List<Sentiment> filterList = journalEntries.stream()
                    .filter(x -> x.getDate().isAfter(LocalDate.now().minus(7, ChronoUnit.DAYS)))
                    .map(JournalEntry::getSentiment)
                    .collect(Collectors.toList());

            Map<Sentiment, Long> sentimentFrequency = filterList.stream()
                    .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

            Map.Entry<Sentiment, Long> maxSentiment = sentimentFrequency.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .orElse(null);

            if (maxSentiment != null) {
//                System.out.println("Most frequent sentiment: " + maxSentiment.getKey());
//                emailService.sendEmail(user.getEmail(),"sentiment from last service",maxSentiment.getKey().toString());
//                System.out.println("Frequency: " + maxSentiment.getValue());

                SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days " + maxSentiment).build();
                try{
                    kafkaTemplate.send("weekly-sentiments", sentimentData.getEmail(), sentimentData);
                }catch (Exception e){
     ///               emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
                }
            }




        }


    }
    @Scheduled(cron = "0 0 9 * * SUN")
    public  void clearAppCache(){
    appCache.init();
    }
}
