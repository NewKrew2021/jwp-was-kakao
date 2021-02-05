package next.examples;

import framework.annotation.Autowired;
import framework.annotation.Service;

@Service
public class MyQnaService {
    private UserRepository userRepository;
    private QuestionRepository questionRepository;

    @Autowired
    public MyQnaService(UserRepository userRepository, QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public QuestionRepository getQuestionRepository() {
        return questionRepository;
    }
}
