package ru.uskov.dmitry.inpas.demo.rainy.hills;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Dmitry on 11.06.2017.
 */
public class RainyHillsExecutor {

    /**
     * Вся суть алгоритма заключается в просмотре всего ландшафта сверху вних и поиска таких стен, которые образуют "чашу", которую дождь может наполнить.
     * <p>
     * Мы рассматриваем ландшафт сверху вниз. Т.е. Сначала берём две самые высокие части стены (пусть одни будут под индексами i1 и i2)и заполняем между ними рассотяние водой.
     * Т.к. мы рассматриваем самые высокие две части, то определённо, между i1 и i2 не существует части стены выше, поэтому определённо заполнить "чашу" между i1 и i2 можно.
     * <p>
     * На следующей итерации мы берём следующю по высоте стенку (назовём её A) и для неё ищем из списка уже просмотренных стен ещё одну стенку (назовём её B) таку, чтобы AB образовывали чашу,
     * которую мы ещё не рассматривали.
     *
     * @param arr
     * @return
     */
    public static int execute(int... arr) {

        int size = arr.length;
        int sumWater = 0;

        List<ArrayItem> arrayItemsByIndex = sortArrayByIndex(arr);
        List<ArrayItem> arrayItemsByValue = sortArrayByValue(arrayItemsByIndex);

        //список уже просмотренных стен, отсортированных в порядке убывания высоты
        //Мы знаем, что все стены, расположенные между стенами с самым маленьким индексом стены в данном масиве и с самым большим индексом имеют поле ArrayItem#active = true
        //Поэтому мы используем TreeSet, т.к. он отсортирован и нас будут интересовать стенки с наименьшим и наибольшим индексом.
        TreeSet<ArrayItem> maxValues = new TreeSet<>(Comparator.comparingInt(ArrayItem::getIndex));
        maxValues.add(arrayItemsByValue.get(0));

        arrayItemsByValue.get(0).setActive(false);

        for (int i = 1; i < size; i++) {
            ArrayItem currentItem = arrayItemsByValue.get(i);
            if (currentItem.isActive()) {
                int minWallIndex = currentItem.getIndex(); // мы рассматриваем чашу у которой две стенки. minWallIndex -- сторона чашы с меньшей высотой.
                int maxWallIndex = getNearestIndex(maxValues, minWallIndex); // maxWallIndex -- сторона чаши с большой высотой
                sumWater += calculateWater(arrayItemsByIndex, minWallIndex, maxWallIndex);
                maxValues.add(currentItem); //стену currentItem уже рассмотрели, добавляем её в массив уже рассмотренных стен.
            }
        }

        return sumWater;
    }


    /**
     * Подсчитывает кол-во воды между стенами.
     * На вход должны приходить корректные данные, т.е. нельзя передавать minWallIndex и maxWallIndex такие, чтобы между ними была стена выше чем minWallIndex
     * P.S. получается, что стены с индексами minWallIndex и maxWallIndex образуют чашу, которая точно заполнится водой (если minWallIndex и maxWallIndex соседние, то объём чаши будет 0)
     *
     * @param arrayItemsByIndex массив стен ладншафта
     * @param minWallIndex      индекс одной стены, с меньшей высотой ландшафта.
     *                          Идея заключается в том, что уровень воды выше этой стены не поднимется, т.к. вода будет просто вылеваться
     * @param maxWallIndex      индекс второй стены, с большей стороной.
     * @return
     */
    private static int calculateWater(List<ArrayItem> arrayItemsByIndex, int minWallIndex, int maxWallIndex) {
        int sumWater = 0;
        arrayItemsByIndex.get(minWallIndex).setActive(false);

        int startIndex = Math.min(minWallIndex, maxWallIndex);
        int endIndex = Math.max(maxWallIndex, minWallIndex);

        int minWallValue = arrayItemsByIndex.get(minWallIndex).getValue();

        for (int i = startIndex + 1; i < endIndex; i++) {
            sumWater += minWallValue - arrayItemsByIndex.get(i).getValue();
            arrayItemsByIndex.get(i).setActive(false);
        }

        return sumWater;
    }


    /**
     * Возвращает индекс стенки из списка array, ближайшей к стендке с индексом index.
     * На вход должен поступать TreeSet, отсортированный по ArrayItem::getIndex() !! Это позволит не перебирать все элементы.
     */
    private static int getNearestIndex(TreeSet<ArrayItem> array, int index) {
        int leftWallIndex = array.first().getIndex();
        int rightWallIndex = array.last().getIndex();
        int deltaOnLeftWall = Math.abs(leftWallIndex - index);
        int deltaOnRightWall = Math.abs(rightWallIndex - index);

        if (deltaOnLeftWall < deltaOnRightWall) {
            return leftWallIndex;
        } else {
            return rightWallIndex;
        }

    }

    /**
     * Возвращает массив, отсортированный по высоте ландшафта в исходном массиве
     */
    private static List<ArrayItem> sortArrayByValue(List<ArrayItem> arrayItemsByIndex) {
        return arrayItemsByIndex.stream().sorted((o1, o2) -> o2.getValue() - o1.getValue()).collect(Collectors.toList());
    }

    /**
     * возвращает массив остортированный по индексам по возрастанию
     *
     * @param arr исходный массив
     * @return Список элементов ArrayItem, отсортированных по индексу
     */
    private static List<ArrayItem> sortArrayByIndex(int[] arr) {
        ArrayList<ArrayItem> arrayItems = new ArrayList<>(arr.length);
        for (int i = 0; i < arr.length; i++) {
            arrayItems.add(new ArrayItem(arr[i], i));
        }
        return arrayItems;
    }


}