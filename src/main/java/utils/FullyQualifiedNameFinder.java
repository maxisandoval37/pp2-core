package utils;

public class FullyQualifiedNameFinder {
    public static String find(String className, String path) {
        // Remove any trailing slashes from the path
        path = path.replaceAll("/+", ".");

        // Check if the path starts with "src.main.java."
        if (path.startsWith("src.main.java.")) {
            // Remove the "src.main.java." part
            path = path.substring("src.main.java.".length());
        }

        // Combine the modified path and class name to get the fully qualified name
        return path + "." + className;
    }

}
