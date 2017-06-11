package ru.uskov.dmitry.inpas.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.uskov.dmitry.inpas.demo.beans.RainyHillSessionHistory;
import ru.uskov.dmitry.inpas.demo.rainy.hills.RainyHillsExecutor;

import java.util.List;

/**
 * Created by Dmitry on 11.06.2017.
 */
@Service
public class RainyHillsService {

    @Autowired
    private RainyHillSessionHistory history;

    public RainyHillSessionHistory.Request calculate(int[] input) {
        int ans = RainyHillsExecutor.execute(input);
        history.add(input, ans);
        return history.getLast();
    }

    public RainyHillSessionHistory.Request calculate(String input) {
        return calculate(parseAndValidate(input));
    }

    private int[] parseAndValidate(String input) {
        String[] inputArrayString = input.trim().split("\\s*[,|;]\\s*");
        int[] ans = new int[inputArrayString.length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = Integer.valueOf(inputArrayString[i]);
        }
        return ans;
    }

    public List<RainyHillSessionHistory.Request> getHistory() {
        return history.getRequests();
    }

    public void clearHistory() {
        history.clear();
    }
}