package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA() {
        Query query = new Query();
        //  query.addCriteria(Criteria.where("username").is("Yash"));

        query.addCriteria(Criteria.where("email").regex("^[^@]+@[^@]+\\.[^@]+$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
//        Criteria criteria = new Criteria();
//        criteria.orOperator(Criteria.where("email").is(true),
//                Criteria.where("sentimentAnalysis").is(true));
        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }
}
