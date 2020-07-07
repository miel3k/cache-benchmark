package com.miel3k.cachebenchmark.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miel3k.cachebenchmark.model.Book;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class BookRepository implements BookDataSource {

    Gson gson = new Gson();

    @Override
    public Book getBook(Integer key) {
        try {
            String json = Files.readString(Paths.get("data/books.json"), StandardCharsets.US_ASCII);
            Type bookListType = new TypeToken<ArrayList<Book>>() {
            }.getType();
            ArrayList<Book> books = gson.fromJson(json, bookListType);
            return books.stream().filter(book -> book.getId().equals(key)).findFirst().orElse(null);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
