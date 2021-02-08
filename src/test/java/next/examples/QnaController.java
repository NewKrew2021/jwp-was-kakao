package next.examples;

import framework.annotation.Autowired;
import framework.annotation.web.Controller;

@Controller
public class QnaController {
    private MyQnaService qnaService;

    @Autowired
    public QnaController(MyQnaService qnaService) {
        this.qnaService = qnaService;
    }

    public MyQnaService getQnaService() {
        return qnaService;
    }
}
