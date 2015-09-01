package com.capgemini.starterkit.javafx.dataprovider;

import java.util.Collection;

import com.capgemini.starterkit.javafx.dataprovider.data.BookVO;
import com.capgemini.starterkit.javafx.dataprovider.impl.BookDataProviderImpl;

public interface BookDataProvider {

	BookDataProvider INSTANCE = new BookDataProviderImpl();
	Collection<BookVO> findBooks(String title, String author, String library);
}


