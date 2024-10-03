package ru.job4j.ood.isp.menu;

public class SimpleMenuPrinter implements MenuPrinter {

    @Override
    public void print(Menu menu) {
        for (Menu.MenuItemInfo item : menu) {
            System.out.println("    ".repeat(item.getNumber().split("\\.").length - 1) + item.getNumber() + " " + item.getName());
        }
    }
}
