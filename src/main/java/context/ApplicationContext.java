package context;

import com.github.jknack.handlebars.Handlebars;
import controller.UserController;
import exception.ExceptionHandler;
import service.UserService;
import utils.template.TemplateUtils;
import view.ViewResolver;
import webserver.Dispatcher;
import webserver.SessionManager;

public class ApplicationContext {
    public static SessionManager sessionManager = new SessionManager();
    public static UserService userService = new UserService();
    public static Handlebars handlebars = TemplateUtils.getHandleBars();
    public static UserController userController = new UserController(userService);
    public static ViewResolver viewResolver = new ViewResolver();
    public static Dispatcher dispatcher = new Dispatcher();
    public static ExceptionHandler exceptionHandler = new ExceptionHandler();
}
