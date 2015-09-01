package com.capgemini.starterkit.javafx.dataprovider.impl;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.capgemini.starterkit.javafx.dataprovider.BookDataProvider;
import com.capgemini.starterkit.javafx.dataprovider.data.BookVO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class BookDataProviderImpl implements BookDataProvider {

	private static final Logger LOG = Logger.getLogger(BookDataProviderImpl.class);


	public BookDataProviderImpl() {
	}

	@Override
	public Collection<BookVO> findBooks(String title, String author, String library) {
		Collection<BookVO> books = new ArrayList<>();
		try {
			HttpResponse response = sendRequest(title, author, library);
			parseResponse(response, books);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return books;
	}

	private void parseResponse(HttpResponse response, Collection<BookVO> books) throws IOException {
		BufferedReader jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String booksArrayInJson = "";
		while ((booksArrayInJson = jsonResponse.readLine()) != null) {
			books.addAll(Parser.convertToBookVO(booksArrayInJson));
		}

	}

	private HttpResponse sendRequest(String title, String author, String library)
			throws ClientProtocolException, IOException {

		HttpClient client = new DefaultHttpClient();
		StringBuilder requestBuilder = buildRequestParams(title, author, library);

		HttpGet request = new HttpGet("http://localhost:9721/workshop/searchBooks" + requestBuilder.toString());
		return client.execute(request);

	}

	private StringBuilder buildRequestParams(String title, String author, String library) {
		StringBuilder requestBuilder = new StringBuilder("");
		if (title != null && !title.isEmpty()) {
			requestBuilder.append("?title=" + title);
		}
		if (author != null &&  !author.isEmpty()) {
			requestBuilder.append((title == null || title.isEmpty()) ? "?" : "&");
			requestBuilder.append("authorName=" + author);
		}

		if (library != null && !library.isEmpty()) {
			requestBuilder.append((title == null || title.isEmpty()) && (author == null || author.isEmpty()) ? "?" : "&");
			requestBuilder.append("libraryName=" + library);
		}
		return requestBuilder;
	}

}
