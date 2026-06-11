package task4.app;

import task4.model.Item;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Set<String> stringSet = new HashSet<>();
        stringSet.add("кошка");
        stringSet.add("собака");
        stringSet.add("лошадь");
        stringSet.add("енот");
        stringSet.add("гусь");

        System.out.println("Set строки, вывод через for-each:");
        for (String animal : stringSet) {
            System.out.println(animal);
        }

        stringSet.add("гусь");
        System.out.println("Set строки, вывод через Iterator:");
        Iterator<String> iteratorS = stringSet.iterator();
        while (iteratorS.hasNext()) {
            System.out.println(iteratorS.next());
        }
        System.out.println("Количество слов:" + stringSet.size());

        Set<Item> itemSet = new HashSet<>();
        itemSet.add(new Item(1, "кошка"));
        itemSet.add(new Item(2, "собака"));
        itemSet.add(new Item(3, "лошадь"));
        itemSet.add(new Item(4, "енот"));
        itemSet.add(new Item(5, "гусь"));

        System.out.println("Set Item, вывод через for-each:");
        for (Item item : itemSet) {
            System.out.println(item);
        }

        Item duplicate = new Item(1, "кошка");
        itemSet.add(duplicate);
        System.out.println("Set Item: вывод через Iterator:");
        Iterator<Item> iteratorItem = itemSet.iterator();
        while (iteratorItem.hasNext()) {
            System.out.println(iteratorItem.next());
        }
        System.out.println("Размер: " + itemSet.size());

        List<String> list = new ArrayList<>();
        list.add("карандаш");
        list.add("ручка");
        list.add("пенал");
        list.add("стёрка");

        System.out.println("List, вывод через for-each:");
        for (String s : list) {
            System.out.println(s);
        }

        System.out.println("List, вывод через for, на 3-м элементе вставка на 2-ю позицию:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
            if (i == 2) {
                list.add(1, "НОВЫЙ");
            }
        }
        System.out.println("List после вставки:");
        for (String s : list) {
            System.out.println(s);
        }

        System.out.println("List, вывод через for, на 3-м элементе удаление со 2-й позиции:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
            if (i == 2) {
                list.remove(1);
            }
        }
        System.out.println("List после удаления:");
        for (String s : list) {
            System.out.println(s);
        }

        Map<String, String> map = new HashMap<>();
        map.put("один", "карандаш");
        map.put("два", "ручка");
        map.put("три", "пенал");
        System.out.println("Map String, способ 1 (keySet):");
        for (String key : map.keySet()) {
            System.out.println(key + " = " + map.get(key));
        }
        map.put("два", "фломастер");
        System.out.println("Map String, способ 2 (entrySet), после замены значения:");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        System.out.println("Размер: " + map.size());

        Map<Item, String> itemMap = new HashMap<>();
        itemMap.put(new Item(1, "кошка"), "Мурка");
        itemMap.put(new Item(2, "собака"), "Шарик");
        itemMap.put(new Item(3, "лошадь"), "Буцефал");
        System.out.println("Map Item, способ 1 (keySet):");
        for (Item key : itemMap.keySet()) {
            System.out.println(key + " = " + itemMap.get(key));
        }
        itemMap.put(new Item(1, "кошка"), "Барсик");
        System.out.println("Map Item, способ 2 (entrySet), после замены значения:");
        for (Map.Entry<Item, String> entry : itemMap.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        System.out.println("Размер: " + itemMap.size());

        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("свинья");
        treeSet.add("корова");
        treeSet.add("гусь");
        treeSet.add("крыса");

        System.out.println("TreeSet (добавляли вразброс, вывод отсортирован):");
        for (String animal : treeSet) {
            System.out.println(animal);
        }

        TreeSet<Item> treeSetItem = new TreeSet<>();
        treeSetItem.add(new Item(2, "свинья"));
        treeSetItem.add(new Item(1, "корова"));
        treeSetItem.add(new Item(4, "гусь"));
        treeSetItem.add(new Item(3, "крыса"));

        System.out.println("TreeSet Item (сортировка по compareTo — сначала number):");
        for (Item item : treeSetItem) {
            System.out.println(item);
        }

        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("три", "пенал");
        treeMap.put("один", "карандаш");
        treeMap.put("два", "ручка");

        System.out.println("TreeMap String (ключи отсортированы):");
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

        TreeMap<Item, String> treeMapItem = new TreeMap<>();
        treeMapItem.put(new Item(3, "крыса"), "Лариса");
        treeMapItem.put(new Item(1, "корова"), "Звезда");
        treeMapItem.put(new Item(2, "свинья"), "Фунтик");

        System.out.println("TreeMap Item (ключи отсортированы по compareTo):");
        for (Map.Entry<Item, String> entry : treeMapItem.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}
