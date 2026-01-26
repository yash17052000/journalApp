package net.engineeringdigest.journalApp.Controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")

@Tag(name = "Userapis",description = "delete,update")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;




    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInDb=userService.findByUserName(username);

        userInDb.setUsername(user.getUsername());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);
        return new ResponseEntity<>(user, HttpStatus.OK);


    }


   @GetMapping
    public ResponseEntity<?> greetings(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
       WeatherResponse weather = weatherService.getWeather("Ghaziabad");
       String greetings="";
       if(weather!=null){

           greetings="weather feels like"+weather.getCurrent().temperature;
       }
       return new ResponseEntity<>(username+"HI"+"Todays temp is "+ greetings,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        userService.deleteByUserName(username);
        return new ResponseEntity<>( HttpStatus.OK);


    }
}
