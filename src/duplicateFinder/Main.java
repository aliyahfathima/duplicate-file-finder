package duplicateFinder;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DuplicateScanner scanner = new DuplicateScanner();
        DuplicateManager manager = new DuplicateManager();

        Map<String, List<File>> hashMap = new HashMap<>();
        boolean scanned = false;

        while (true) {
            System.out.println("\n===== Duplicate File Finder & Disk Space Analyzer =====");
            System.out.println("1. Scan a folder");
            System.out.println("2. View duplicate files");
            System.out.println("3. View wasted space");
            System.out.println("4. Delete duplicates (keep first copy)");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": {
                    System.out.print("Enter folder path to scan: ");
                    String path = sc.nextLine().trim();
                    File dir = new File(path);
                    try {
                        long start = System.currentTimeMillis();
                        hashMap = scanner.scan(dir);
                        long end = System.currentTimeMillis();
                        scanned = true;
                        System.out.println("Scan complete in " + (end - start) + " ms.");
                        System.out.println("Files scanned: " + scanner.getFilesScanned());
                        System.out.println("Files skipped (unreadable): " + scanner.getFilesSkipped());
                    } catch (IllegalArgumentException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                }
                case "2":
                    if (!scanned) {
                        System.out.println("Please scan a folder first (option 1).");
                    } else {
                        manager.printDuplicates(hashMap);
                    }
                    break;

                case "3":
                    if (!scanned) {
                        System.out.println("Please scan a folder first (option 1).");
                    } else {
                        long wasted = manager.calculateWastedSpace(hashMap);
                        System.out.printf("Total wasted space: %.2f MB%n", wasted / (1024.0 * 1024.0));
                    }
                    break;

                case "4":
                    if (!scanned) {
                        System.out.println("Please scan a folder first (option 1).");
                    } else {
                        System.out.print("Are you sure you want to delete duplicate files? (yes/no): ");
                        String confirm = sc.nextLine().trim();
                        if (confirm.equalsIgnoreCase("yes")) {
                            int deleted = manager.deleteDuplicates(hashMap);
                            System.out.println("Deleted " + deleted + " duplicate file(s).");
                        } else {
                            System.out.println("Delete cancelled.");
                        }
                    }
                    break;

                case "5":
                    System.out.println("Exiting. Bye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }
}