package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserRepository userRepository;
    @Test
    @Disabled
    public  void  TestForfindByUsername(){

//        assertNotNull(userRepository.findByUsername("Yash"));
        User user=userRepository.findByUsername("Pragati");
        assertTrue(user.getJournalEntries().size()>0);

    }

//    @ParameterizedTest
//    @CsvSource({
//            "1,2,3",
//            "2,4,5"
//    })

//    @ParameterizedTest
//    @ArgumentsSource(UserArgumentProvider.class);
//    public void Test(int a,int b,int expected){
//        assertEquals(expected,a+b);
//    }
//    @ParameterizedTest
//    @ArgumentsSource(UserArgumentProvider.class)
//    public void test(User user){
//
//        assertTrue(user.getJournalEntries().size()>0);
//    }



}
