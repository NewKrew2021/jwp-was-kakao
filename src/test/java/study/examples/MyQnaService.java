package study.examples;

import annotation.Autowired;
import annotation.Service;

@Service
public class MyQnaService {
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

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
