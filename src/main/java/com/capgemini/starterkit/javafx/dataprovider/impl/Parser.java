package com.capgemini.starterkit.javafx.dataprovider.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.capgemini.starterkit.javafx.dataprovider.data.AuthorVO;
import com.capgemini.starterkit.javafx.dataprovider.data.BookVO;
import com.capgemini.starterkit.javafx.dataprovider.data.LibraryVO;

public class Parser {

	public static Collection<BookVO> convertToBookVO(String booksArrayInJson)  {

		Collection<BookVO> booksVo = new ArrayList<BookVO>();

		booksArrayInJson = removeNeedlessSigns(booksArrayInJson);
		String[] booksJson = splitJsonArray(booksArrayInJson);

		for (String bookJson : booksJson) {
			if (!bookJson.isEmpty()) {
				booksVo.add(createBookVO(bookJson));
			}
		}
		return booksVo;

	}

	private static String[] splitJsonArray(String booksArrayInJson) {
		return booksArrayInJson.split("\\{");
	}

	private static String removeNeedlessSigns(String booksArrayInJson) {
		booksArrayInJson = booksArrayInJson.replaceAll("[\\[\\]]", "");
		booksArrayInJson = booksArrayInJson.replaceAll("\\},", "");
		booksArrayInJson = booksArrayInJson.replaceAll("\\}", "");
		booksArrayInJson = booksArrayInJson.replaceAll("\"", "");
		return booksArrayInJson;
	}

	private static BookVO createBookVO(String bookJson) {
		BookVO convertedBook = new BookVO();
		String[] bookElements = bookJson.split(",");

		for (String bookElement : bookElements) {
			String[] bookElementNameAndValue = bookElement.split(":");
			switch (bookElementNameAndValue[0]) {
			case "id":
			case "libraryId":
			default:
				break;

			case "title":
				convertedBook.setTitle(bookElementNameAndValue[1]);
				break;
			case "authors":
				convertedBook.setAuthorVO(createAuthor(bookElementNameAndValue[1]));
				break;
			case "libraryName":
				convertedBook.setLibraryVO(new LibraryVO(bookElementNameAndValue[1]));
				break;
			}
		}
		return convertedBook;
	}

	private static AuthorVO createAuthor(String firstAndLastNameTogther) {
		String[] firstAndLastName = firstAndLastNameTogther.split(" ");
		AuthorVO authorVO = new AuthorVO(firstAndLastName[0], firstAndLastName[firstAndLastName.length - 1]);
		return authorVO;
	}

}
