package model;


import java.util.Objects;

public class PagePath {

    private String path;

    public PagePath(String path) {
        this.path = path;
    }

    public String getPagePath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PagePath pagePath1 = (PagePath) o;
        return Objects.equals(path, pagePath1.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
