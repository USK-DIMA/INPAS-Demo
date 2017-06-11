package ru.uskov.dmitry.inpas.demo.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * Объект, хранящий в себе историю запросов на вычеслениев течении сессии
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RainyHillSessionHistory {

    /**
     * Список запросов
     */
    private LinkedList<Request> requests = new LinkedList<>();

    public List<Request> getRequests() {
        return requests;
    }

    public void add(int[] input, int ans) {
        requests.add(new Request(input, ans, requests.size() + 1));
    }

    public void clear() {
        requests = new LinkedList<>();
    }

    public Request getLast() {
        return requests.getLast();
    }

    public static class Request {

        /**
         * Входные данные для расчётов
         */
        private int[] input;

        /**
         * Полученый ответ
         */
        private int output;

        /**
         * Порядковый номер запроса. (Натуральное число)
         */
        private int index;

        public Request(int[] input, int output, int index) {
            this.input = input;
            this.output = output;
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int[] getInput() {
            return input;
        }

        public void setInput(int[] input) {
            this.input = input;
        }

        public int getOutput() {
            return output;
        }

        public void setOutput(int output) {
            this.output = output;
        }
    }

}