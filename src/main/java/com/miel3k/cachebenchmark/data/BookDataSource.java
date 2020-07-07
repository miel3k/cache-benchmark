package com.miel3k.cachebenchmark.data;

import com.miel3k.cachebenchmark.model.Book;

public interface BookDataSource {
    Book getBook(Integer key);
}
