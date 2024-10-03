package ru.job4j.ood.isp.menu;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class SimpleMenuTest {

    public static final ActionDelegate STUB_ACTION = System.out::println;

    @Test
    public void whenAddThenReturnSame() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        menu.add("Купить продукты", "Купить хлеб", STUB_ACTION);
        menu.add("Купить продукты", "Купить молоко", STUB_ACTION);
        assertThat(new Menu.MenuItemInfo("Сходить в магазин",
                List.of("Купить продукты"), STUB_ACTION, "1."))
                .isEqualTo(menu.select("Сходить в магазин").get());
        assertThat(new Menu.MenuItemInfo(
                "Купить продукты",
                List.of("Купить хлеб", "Купить молоко"), STUB_ACTION, "1.1."))
                .isEqualTo(menu.select("Купить продукты").get());
        assertThat(new Menu.MenuItemInfo(
                "Покормить собаку", List.of(), STUB_ACTION, "2."))
                .isEqualTo(menu.select("Покормить собаку").get());
        menu.forEach(i -> System.out.println(i.getNumber() + i.getName()));
    }

    @Test
    public void whenSelectNonExistentItemThenReturnEmpty() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        assertThat(menu.select("Не существующий элемент")).isEmpty();
    }

    @Test
    public void whenSelectRootItemThenReturnCorrectInfo() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Task A", STUB_ACTION);
        menu.add(Menu.ROOT, "Task B", STUB_ACTION);
        menu.add("Task A", "Subtask A1", STUB_ACTION);
        menu.add("Task B", "Subtask B1", STUB_ACTION);

        assertThat(menu.select("Task A").get().getName()).isEqualTo("Task A");
        assertThat(menu.select("Subtask A1").get().getName()).isEqualTo("Subtask A1");
        assertThat(menu.select("Task B").get().getName()).isEqualTo("Task B");
        assertThat(menu.select("Subtask B1").get().getName()).isEqualTo("Subtask B1");
    }

    @Test
    public void whenPrintMenuThenCorrectOutput() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        menu.add("Купить продукты", "Купить хлеб", STUB_ACTION);
        menu.add("Купить продукты", "Купить молоко", STUB_ACTION);
        SimpleMenuPrinter menuPrinter = new SimpleMenuPrinter();
        StringBuilder expectedOutput = new StringBuilder()
                .append("1. Сходить в магазин").append(System.lineSeparator())
                .append("    1.1. Купить продукты").append(System.lineSeparator())
                .append("        1.1.1. Купить хлеб").append(System.lineSeparator())
                .append("        1.1.2. Купить молоко").append(System.lineSeparator())
                .append("2. Покормить собаку").append(System.lineSeparator());
        var outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));
        menuPrinter.print(menu);
        assertThat(outputStream.toString()).isEqualTo(expectedOutput.toString());
    }

}