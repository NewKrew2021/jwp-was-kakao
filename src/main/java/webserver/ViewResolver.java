package webserver;

import view.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ViewResolver {
    private List<View> lists = new ArrayList<>();

    public ViewResolver() {
        initViews();
    }

    private void initViews() {
        lists.add(new TemplateView());
        lists.add(new StaticView());
        lists.add(new RedirectView());
        lists.add(new UserListView());
        lists.add(new FaviconView());
    }

    public Optional<View> resolveViewName(String name) {
        return lists.stream()
                .filter(it -> it.canHandle(name))
                .findFirst();
    }
}
