package ru.uskov.dmitry.inpas.demo.rainy.hills;

/**
 * Класс, содержащий в себе величину в ячейке исходного массива и индекс этой величины в исходном массие
 * Создан для сортировки. Позволяет рассматтивароть ячейки отсортированные одновременно и по индексу и по высоте
 */
class ArrayItem {

    /**
     * Высота ландшафта
     */
    private int value;

    /**
     * Индекс в исходном массиве
     */
    private int index;

    /**
     * true, если вершина не залита водой, т.е. её ещё надо рассматривать
     */
    private boolean active;

    public ArrayItem(int value, int index) {
        this.value = value;
        this.index = index;
        this.active = true;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getValue() {
        return value;
    }


    public int getIndex() {
        return index;
    }
}