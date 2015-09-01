package com.capgemini.starterkit.javafx.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.capgemini.starterkit.javafx.dataprovider.BookDataProvider;
import com.capgemini.starterkit.javafx.dataprovider.data.BookVO;
import com.capgemini.starterkit.javafx.model.BookSearchModel;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class LibraryController {


	@FXML
	ResourceBundle resources;

	@FXML
	URL location;

	@FXML
	TextField titleField;

	@FXML
	Button searchButton;

	@FXML
	TableView<BookVO> resultTable;

	@FXML
	TableColumn<BookVO, String> authorFirstNameColumn;

	@FXML
	TableColumn<BookVO, String> authorLastNameColumn;

	@FXML
	TableColumn<BookVO, String> titleColumn;

	private final BookDataProvider bookDataProvider = BookDataProvider.INSTANCE;

	private final BookSearchModel model = new BookSearchModel();

	@FXML
	TableColumn<BookVO, String> libraryNameColumn;

	@FXML
	Button clearButton;

	@FXML
	TextField libraryField;

	@FXML
	TextField authorField;

	public LibraryController() {
	}

	@FXML
	private void initialize() {
		initializeResultTable();

		titleField.textProperty().bindBidirectional(model.titleProperty());
		authorField.textProperty().bindBidirectional(model.authorProperty());
		libraryField.textProperty().bindBidirectional(model.libraryProperty());
		resultTable.itemsProperty().bind(model.resultProperty());

		searchButton.disableProperty().bind(titleField.textProperty().isEmpty()
				.and(authorField.textProperty().isEmpty()).and(libraryField.textProperty().isEmpty()));
		clearButton.disableProperty().bind(model.resultProperty().emptyProperty());
	}

	private void initializeResultTable() {
		titleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitle()));
		authorFirstNameColumn.setCellValueFactory(
				cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAuthorVO().getFirstName()));
		authorLastNameColumn.setCellValueFactory(
				cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAuthorVO().getLastName()));
		libraryNameColumn.setCellValueFactory(
				cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLibraryVO().getName()));

		resultTable.setPlaceholder(new Label(resources.getString("table.emptyText")));

	}

	@FXML
	public void searchButtonAction() {

		Task<Collection<BookVO>> backgroundTask = new Task<Collection<BookVO>>() {

			@Override
			protected Collection<BookVO> call() throws Exception {
				return bookDataProvider.findBooks(model.getTitle(), model.getAuthor(), model.getLibrary());
			}
		};

		backgroundTask.stateProperty().addListener(new ChangeListener<Worker.State>() {
			@Override
			public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
				if (newValue == State.SUCCEEDED) {
					model.setResult(new ArrayList<BookVO>(backgroundTask.getValue()));
					resultTable.getSortOrder().clear();
				}
			}
		});

		new Thread(backgroundTask).start();
	}

	@FXML
	public void clearButtonAction(ActionEvent event) {
		model.setResult(new ArrayList<BookVO>());
		resultTable.setPlaceholder(new Label(resources.getString("table.emptyText")));
	}
}
