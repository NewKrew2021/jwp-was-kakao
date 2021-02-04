package model;

import model.httpinfo.HttpMethod;

import java.util.Objects;

public class PathInfo {
    private final String path;
    private final HttpMethod method;

    public PathInfo(String path, HttpMethod method) {
        this.path = path;
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathInfo that = (PathInfo) o;
        return Objects.equals(path, that.path) && Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, method);
    }

    public String getPath() {
        return path;
    }
}
