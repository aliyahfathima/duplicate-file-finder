package duplicateFinder;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Walks a folder (and all its sub-folders) and groups every file it finds
 * by content hash. Files that share the same hash are duplicates.
 */
public class DuplicateScanner {

    private int filesScanned = 0;
    private int filesSkipped = 0;

    public Map<String, List<File>> scan(File rootDir) {
        if (rootDir == null || !rootDir.exists() || !rootDir.isDirectory()) {
            throw new IllegalArgumentException("Invalid directory: " + rootDir);
        }

        // key = content hash, value = list of files that share that hash
        Map<String, List<File>> hashMap = new HashMap<>();
        scanRecursive(rootDir, hashMap);
        return hashMap;
    }

    /**
     * Recursion: if we find a sub-folder, we call this same method on it.
     * This is how we go arbitrarily deep into a folder tree without
     * writing separate code for each nesting level.
     */
    private void scanRecursive(File dir, Map<String, List<File>> hashMap) {
        File[] entries = dir.listFiles();
        if (entries == null) {
            return; // folder we don't have permission to read, or it's empty
        }

        for (File entry : entries) {
            if (entry.isDirectory()) {
                scanRecursive(entry, hashMap);
            } else if (entry.isFile()) {
                try {
                    String hash = FileHasher.computeHash(entry);
                    // computeIfAbsent: if this hash's bucket doesn't exist yet,
                    // create an empty list first, then add this file to it.
                    hashMap.computeIfAbsent(hash, k -> new ArrayList<>()).add(entry);
                    filesScanned++;
                } catch (IOException | NoSuchAlgorithmException e) {
                    System.err.println("Skipping unreadable file: " + entry.getPath());
                    filesSkipped++;
                }
            }
        }
    }

    public int getFilesScanned() {
        return filesScanned;
    }

    public int getFilesSkipped() {
        return filesSkipped;
    }
}