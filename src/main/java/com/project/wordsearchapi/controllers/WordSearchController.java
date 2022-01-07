package com.project.wordsearchapi.controllers;

import com.project.wordsearchapi.services.WordGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController("/")
public class WordSearchController {

    @Autowired
    WordGridService wordgridservice;

    @GetMapping("/wordgrid")
    @ResponseBody
    public String createWordGrid(@RequestParam int grid_size, @RequestParam  String wordList){

        List<String> words = Arrays.asList(wordList.split(","));
        char [][] grid= wordgridservice.generateGrid(grid_size,words);
        String gridtoString = " ";

        for(int i=0;i < grid_size;i++)
        {
            for(int j=0;j < grid_size;j++)
            {
                gridtoString += grid[i][j] + " ";
            }
            gridtoString+="\r\n";
        }
        return gridtoString;
    }
}
