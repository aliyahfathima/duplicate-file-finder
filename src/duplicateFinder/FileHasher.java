package duplicateFinder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Computes a SHA-256 hash for a file's CONTENT (not its name).
 * Two files are true "duplicates" only if their content hash matches —
 * this is why we don't just compare file names or sizes.
 */
public class FileHasher {

    // Read the file in small chunks instead of loading the whole file into
    // memory at once — important for large files (videos, ISOs, etc.)
    private static final int BUFFER_SIZE = 8192;

    public static String computeHash(File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        }

        byte[] hashBytes = digest.digest();

        // Convert raw bytes into a readable hex string, e.g. "3a7f9c..."
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}

