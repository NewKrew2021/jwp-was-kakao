package domain;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import exception.TemplateFailException;
import model.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ResponseBody {
    private final String body;

    public ResponseBody(String body) {
        this.body = body;
    }

    public static ResponseBody ofUsers(List<User> users){
        Template template = getTemplate();

        Map<String, Object> m = new HashMap<String, Object>() {{
            put("users", new ArrayList<HashMap<String, Object>>(
                    IntStream.range(0, users.size()).mapToObj(
                            index -> new HashMap<String, Object>() {{
                                put("index", index + 1);
                                put("value", users.get(index));
                            }}
                    ).collect(Collectors.toList())));
        }};
        try {
            return new ResponseBody(template.apply(m));
        } catch (IOException e) {
            throw new TemplateFailException();
        }
    }

    private static Template getTemplate() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        try {
            return new Handlebars(loader)
                    .compile("user/list");
        } catch (IOException e) {
            throw new TemplateFailException();
        }
    }

    public ResponseBody(byte[] bytes){
        this.body = new String(bytes, StandardCharsets.UTF_8);
    }

    public static ResponseBody ofEmptyBody(){
        return new ResponseBody("");
    }

    public int getByteSize(){
        return body.getBytes(StandardCharsets.UTF_8).length;
    }

    @Override
    public String toString(){
        return body;
    }
}
