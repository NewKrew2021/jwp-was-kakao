package next.examples;

import annotation.Autowired;
import annotation.web.Controller;

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
