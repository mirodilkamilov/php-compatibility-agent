package dev.mirodil;

import dev.mirodil.models.PhpVersion;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static dev.mirodil.utils.AgentUtil.*;

public class Main {
    private static final String DEFAULT_PHP_FILE = "src/main/resources/data/hello-world.php";

    public static void main(String[] args) {
        String phpFilePath = DEFAULT_PHP_FILE;
        if (isFileAttached(args)) {
            for (int i = 0; i < args.length; i++) {
                if ("-f".equals(args[i]) && i + 1 < args.length) {
                    phpFilePath = args[i + 1];
                    i++;
                }
            }
        } else {
            System.out.println("Info: PHP file is not attached. Using default one in " + phpFilePath);
        }

        File phpFile = new File(phpFilePath);
        if (!isFileExists(phpFile)) {
            System.err.println("Error: Attached file not found - " + phpFilePath);
            return;
        }
        if (!isValidPhpFile(phpFile)) {
            System.err.println("Error: Invalid PHP file - " + phpFilePath);
            return;
        }

        System.out.println("Info: PHP file attached to agent: " + phpFile.getAbsolutePath());
        Scanner scanner = new Scanner(System.in);
        System.out.println("Info: Agent is ready for conversation. Start it by saying: \"Check if this PHP code is compatible with PHP 8.0\"");
        String userMessage = scanner.nextLine();
        if (!isValidUserMessage(userMessage)) {
            System.err.println("Error: Not supported user message.");
            return;
        }

        PhpVersion phpTargetVersion;
        try {
            phpTargetVersion = extractPhpVersionFromUserMessage(userMessage);
        } catch (NoSuchElementException e) {
            System.err.println("Error: Agent doesn't support requested PHP version. Supported PHP versions: " + PhpVersion.supportedPhpVersions());
            return;
        }
        Agent agent = new Agent(phpTargetVersion);
        try {
            String report = agent.analyze(phpFile);
            System.out.println(report);
        } catch (IOException e) {
            System.err.println("Error: Agent cannot load relevant PHP version upgrade knowledge base.");
        }
    }

    private static boolean isFileAttached(String[] args) {
        for (String arg : args) {
            if ("-f".equals(arg)) {
                return true;
            }
        }
        return false;
    }
}
