/**
 * Example of how to handle AWS EC2 instances.
 * Using the AWSHelper class.
 */

package example;

import java.io.IOException;
import java.util.Scanner;

public class EC2Instances {

    /**
     * Print a menu in the screen with the available options
     */
    private static void printMenu() {
        System.out.println("\nMENU");
        System.out.println("0 = Quit");
        System.out.println("1 = Describe all instances");
        System.out.println("2 = Run (Create) new instance");
        System.out.println("3 = Describe instance");
        System.out.println("4 = Start instance");
        System.out.println("5 = Stop instance");
        System.out.println("6 = Reboot instance");
        System.out.println("7 = Terminate instance");
        System.out.println("Enter an option?");
    }

    /**
     * Read from keyboard the option selected by user
     */
    private static int getOption(Scanner sc) {
        int option;

        String line = sc.nextLine();
        if (line != null && !line.isEmpty() && line.matches("[0-9]+")) {
            option = Integer.parseInt(line);
        } else {
            option = 100;
        }

        return option;
    }

    public static void main(String[] args) throws IOException {
        String instanceId = null;
        Scanner sc = new Scanner(System.in);
        int option;

        do {
            printMenu();
            option = getOption(sc);

            switch (option) {
                case 0:
                    System.out.println("\nBye");
                    break;
                case 1:  // Describe all instances
                    AWSHelper.describeInstances();
                    break;
                case 2:  // Run instance
                    instanceId = AWSHelper.runInstance();
                    System.out.println("Instance Id: " + instanceId);
                    break;
                case 3:  // Describe instance
                    AWSHelper.describeInstance(instanceId);
                    break;
                case 4:  // Start instance
                    AWSHelper.startInstance(instanceId);
                    break;
                case 5:  // Stop instance
                    AWSHelper.stopInstance(instanceId);
                    break;
                case 6:  // Reboot instance
                    AWSHelper.rebootInstance(instanceId);
                    break;
                case 7:  // Terminate instance
                    AWSHelper.terminateInstance(instanceId);
                    instanceId = null;
                    break;
                default:
                    System.out.println("ERROR: Enter a valid option!!");
            }
        } while (option != 0);
        sc.close();
    }
}
