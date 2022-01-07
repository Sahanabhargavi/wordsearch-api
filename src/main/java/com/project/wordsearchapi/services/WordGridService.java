package com.project.wordsearchapi.services;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;



//separate instances
//@Scope("prototype")
@Service
public class WordGridService {
    private List<Coordinate> coordinates=new ArrayList<>();


    private enum Direction{
        HORIZONTAL,
        VERTICAL,
        DIAGONAL,
        HORIZONTAL_INVERSE,
        VERTICAL_INVERSE,
        DIAGONAL_INVERSE
    }
    private class Coordinate{
        int x;
        int y;
        Coordinate(int x, int y)
        {
            this.x=x;
            this.y=y;
        }
    }

    public char[][] generateGrid(int grid_size,List<String> words)
    {
        // int x = ThreadLocalRandom.current().nextInt(0, grid_size);
        // int y = ThreadLocalRandom.current().nextInt(0, grid_size);
        char [][]contents = new char[grid_size][grid_size];
        for (int i = 0; i < grid_size; i++) {
            for (int j = 0; j < grid_size; j++) {
                coordinates.add(new Coordinate(i,j));
                contents[i][j] = '_';
            }
        }
        Collections.shuffle(coordinates);
        for(String word: words) {
            for (Coordinate coordinate: coordinates) {
                int x=coordinate.x;
                int y=coordinate.y;
                Direction selectedDirection = getdirectionforFit(contents,word,coordinate);
                if(selectedDirection!=null)
                {
                    switch(selectedDirection)
                    {
                        case HORIZONTAL:
                            for (char c : word.toCharArray()) {
                                contents[x][y++] = c;
                            }
                            break;
                        case VERTICAL:
                            for (char c : word.toCharArray()) {
                                contents[x++][y] = c;
                            }
                            break;
                        case DIAGONAL:
                            for (char c : word.toCharArray()) {
                                contents[x++][y++] = c;
                            }
                            break;
                        case HORIZONTAL_INVERSE:
                            for (char c : word.toCharArray()) {
                                contents[x][y--] = c;
                            }
                            break;
                        case VERTICAL_INVERSE:
                            for (char c : word.toCharArray()) {
                                contents[x--][y] = c;
                            }
                            break;
                        case DIAGONAL_INVERSE:
                            for (char c : word.toCharArray()) {
                                contents[x--][y--] = c;
                            }
                    }
                    break;
                }

            }

        }
        randomFillGrid(contents);
        return contents;
    }

    public void displayGrid(char [][]contents)
    {
        int grid_size=contents[0].length;
        for(int i=0;i < grid_size;i++)
        {
            for(int j=0;j < grid_size;j++)
            {
                System.out.print(contents[i][j] + " ");
            }
            System.out.println(" ");
        }
    }

    private void randomFillGrid(char [][]contents)
    {
        String allCapLetters="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int grid_size=contents[0].length;
        for (int i=0; i<grid_size; i++)
        {
            for(int j=0; j<grid_size; j++)
            {
                if(contents[i][j]=='_') {
                    int randomindex = ThreadLocalRandom.current().nextInt(0, allCapLetters.length());
                    contents[i][j]= allCapLetters.charAt(randomindex);
                }
            }
        }


    }
    private Direction getdirectionforFit(char [][]contents,String word,Coordinate coordinate)
    {
        List<Direction> directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions);
        for(Direction direction:directions)
        {
            if(doesFit(contents,word,coordinate,direction)){
                return direction;
            }
        }

        return null;
    }

    private boolean doesFit(char [][]contents,String word,Coordinate coordinate,Direction direction) {
        int wordlength=word.length();
        int grid_size=contents[0].length;
        switch(direction)
        {
            case HORIZONTAL:
                if (coordinate.y + wordlength > grid_size) return false;
                for (int i = 0; i < wordlength; i++) {
                    if (contents[coordinate.x][coordinate.y+i] != '_') return false;
                }
                break;

            case VERTICAL:
                if (coordinate.x + wordlength > grid_size) return false;
                for (int i = 0; i < wordlength; i++) {
                    if (contents[coordinate.x+i][coordinate.y] != '_') return false;
                }
                break;
            case DIAGONAL:
                if (coordinate.x + wordlength > grid_size || coordinate.y + wordlength > grid_size) return false;
                for (int i = 0; i < wordlength; i++) {
                    if (contents[coordinate.x+i][coordinate.y+i] != '_') return false;
                }
                break;

            case HORIZONTAL_INVERSE:
                if (coordinate.y  < wordlength ) return false;
                for (int i = 0; i < wordlength; i++) {
                    if (contents[coordinate.x][coordinate.y-i] != '_') return false;
                }
                break;

            case VERTICAL_INVERSE:
                if (coordinate.x  < wordlength) return false;
                for (int i = 0; i < wordlength; i++) {
                    if (contents[coordinate.x-i][coordinate.y] != '_') return false;
                }
                break;

            case DIAGONAL_INVERSE:
                if (coordinate.x < wordlength || coordinate.y < wordlength) return false;
                for (int i = 0; i < wordlength; i++) {
                    if (contents[coordinate.x-i][coordinate.y-i] != '_') return false;
                }
                break;
        }

        return true;
    }

}


