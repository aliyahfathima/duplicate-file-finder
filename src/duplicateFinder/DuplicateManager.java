package duplicateFinder;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Takes the hash-map built by DuplicateScanner and does something useful
 * with it: print duplicates, calculate wasted space, or delete duplicates.
 */
public class DuplicateManager {

    public void printDuplicates(Map<String, List<File>> hashMap) {
        int groupNum = 0;
        for (Map.Entry<String, List<File>> entry : hashMap.entrySet()) {
            List<File> files = entry.getValue();
            if (files.size() > 1) { // more than 1 file with the same hash = duplicates
                groupNum++;
                System.out.println("\nDuplicate Group " + groupNum
                        + " (hash: " + entry.getKey().substring(0, 10) + "...):");
                for (File f : files) {
                    System.out.println("   " + f.getAbsolutePath() + "  (" + f.length() + " bytes)");
                }
            }
        }
        if (groupNum == 0) {
            System.out.println("No duplicate files found.");
        }
    }

    /** Wasted space = size of the "extra" copies (keeping just 1 copy per group). */
    public long calculateWastedSpace(Map<String, List<File>> hashMap) {
        long wasted = 0;
        for (List<File> files : hashMap.values()) {
            if (files.size() > 1) {
                long fileSize = files.get(0).length();
                wasted += fileSize * (files.size() - 1);
            }
        }
        return wasted;
    }

    /** Keeps the FIRST file in each duplicate group, deletes the rest. */
    public int deleteDuplicates(Map<String, List<File>> hashMap) {
        int deletedCount = 0;
        for (List<File> files : hashMap.values()) {
            if (files.size() > 1) {
                for (int i = 1; i < files.size(); i++) {
                    File toDelete = files.get(i);
                    if (toDelete.delete()) {
                        deletedCount++;
                        System.out.println("Deleted: " + toDelete.getAbsolutePath());
                    } else {
                        System.err.println("Failed to delete: " + toDelete.getAbsolutePath());
                    }
                }
            }
        }
        return deletedCount;
    }
}