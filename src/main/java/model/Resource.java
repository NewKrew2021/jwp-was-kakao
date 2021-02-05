package model;

import java.util.Arrays;
import java.util.Objects;

public class Resource {
    private final byte[] bytes;
    private final String extension;

    public Resource(byte[] bytes, String extension) {
        this.bytes = bytes;
        this.extension = extension;
    }

    public static Resource of(byte[] bytes, String extension) {
        return new Resource(bytes, extension);
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getExtension() {
        return extension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Arrays.equals(bytes, resource.bytes) && Objects.equals(extension, resource.extension);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(extension);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }
}
