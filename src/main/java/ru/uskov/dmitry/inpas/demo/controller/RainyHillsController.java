package ru.uskov.dmitry.inpas.demo.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.uskov.dmitry.inpas.demo.beans.RainyHillSessionHistory;
import ru.uskov.dmitry.inpas.demo.service.RainyHillsService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Dmitry on 11.06.2017.
 */

@Controller
@RequestMapping("/")
public class RainyHillsController {

    @Autowired
    private RainyHillsService service;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    @RequestMapping(path = "history", method = RequestMethod.GET)
    @ResponseBody
    public List<RainyHillSessionHistory.Request> getHistory() {
        return service.getHistory();
    }

    @RequestMapping(path = "history", method = RequestMethod.DELETE)
    @ResponseBody
    public void clearHistory() {
        service.clearHistory();
    }

    @RequestMapping(path = "calculateText", method = RequestMethod.POST)
    @ResponseBody
    public RainyHillSessionHistory.Request calculate(@RequestBody int[] input) {
        return service.calculate(input);
    }

    @RequestMapping(value = "/calculateFile", method = RequestMethod.POST)
    @ResponseBody
    public RainyHillSessionHistory.Request calculate(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = request.getFile(itr.next());
        ByteArrayInputStream stream = new ByteArrayInputStream(mpf.getBytes());
        String input = IOUtils.toString(stream, "UTF-8");
        try {
            return service.calculate(input);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Некорректные данные в файле", e);
        }
    }


}