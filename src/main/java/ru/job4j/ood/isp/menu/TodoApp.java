package ru.job4j.ood.isp.menu;

import java.util.Scanner;

public class TodoApp {
    private static final ActionDelegate DEFAULT_ACTION = () -> System.out.println("Some action");

    public static void main(String[] args) {
        Menu menu = new SimpleMenu();
        MenuPrinter printer = new SimpleMenuPrinter();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add to root\n2. Add to parent\n3. Execute action\n4. Print menu\n5, or other. Exit");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> {
                    System.out.println("Enter item name:");
                    String itemName = scanner.nextLine();
                    menu.add(Menu.ROOT, itemName, DEFAULT_ACTION);
                }
                case 2 -> {
                    System.out.println("Enter parent name:");
                    String parentName = scanner.nextLine();
                    System.out.println("Enter item name:");
                    String itemName = scanner.nextLine();
                    menu.add(parentName, itemName, DEFAULT_ACTION);
                }
                case 3 -> {
                    System.out.println("Enter item name to execute action:");
                    String itemName = scanner.nextLine();
                    menu.select(itemName).ifPresent(item -> item.getActionDelegate().delegate());
                }
                case 4 -> printer.print(menu);
                default -> System.exit(0);
            }
        }
    }
}
