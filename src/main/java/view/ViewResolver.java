package view;

import model.Model;
import utils.io.FileIoUtils;

import static resource.ResourceResolver.TEMPLATE_PATH_PREFIX;

public class ViewResolver {
    public View resolve(Model model, String viewPath) {
        if (!(FileIoUtils.exist(viewPath) || FileIoUtils.exist(TEMPLATE_PATH_PREFIX + viewPath))) {
            return null;
        }
        return View.of(model, viewPath);
    }
}
