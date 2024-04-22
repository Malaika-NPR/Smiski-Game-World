package core;

public class SeedHandler {
    public static long seedParser(String seed) {
        String separated = seed.replaceAll("\\D", "");
        return Long.parseLong(separated);
    }
}
