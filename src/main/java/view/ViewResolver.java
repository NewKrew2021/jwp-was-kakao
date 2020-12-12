package view;

import model.Model;
import utils.io.FileIoUtils;

public class ViewResolver {
    public View resolve(Model model, String viewPath) {
        if (!FileIoUtils.exist(viewPath)) {
            return null;
        }
        return View.of(model, viewPath);
    }
}
