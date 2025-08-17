package toolkit;

import java.io.InputStream;

public class ResourcesDirectory {

    public static InputStream get(String path) {
        return ResourcesDirectory.class.getResourceAsStream(path);
    }
}
