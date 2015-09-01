package com.capgemini.starterkit.javafx.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.capgemini.starterkit.javafx.dataprovider.data.ImageVO;
import com.capgemini.starterkit.javafx.model.ImageModel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;

import java.nio.file.Files;
import java.nio.file.Path;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MenuController {

	@FXML
	ResourceBundle resources;

	@FXML
	URL location;

	@FXML
	TableView<ImageVO> resultTable;

	@FXML
	Button selectDirButton;

	@FXML
	Label selectDirLabel;

	@FXML
	TableColumn<ImageVO, String> imageNameColumn;

	private final ImageModel model = new ImageModel();

	@FXML
	Button clearButton;

	@FXML
	ScrollPane scrollPane;

	@FXML
	ImageView imageView;

	@FXML
	private void initialize() {
		initializeResultTable();
		initializeImageView();
		initializeScrollPane();
		resultTable.itemsProperty().bind(model.resultProperty());

		clearButton.disableProperty().bind(model.resultProperty().emptyProperty());

	}

	private void initializeImageView() {
		addDefaultImage();
	}

	private void initializeScrollPane() {
		scrollPane = new ScrollPane();
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);

	}

	private void addDefaultImage() {
		try {
			File file = new File(
					"C:/StarterKit-JavaFX/sources/javafx/src/main/resources/com/capgemini/starterkit/javafx/image/NoImage.jpg");
			String localUrl = file.toURI().toURL().toString();
			final Image image = new Image(localUrl);
			imageView.setImage(image);
		} catch (MalformedURLException e) {
			System.err.println(e.getMessage());
		}
	}

	private void initializeResultTable() {
		imageNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
		resultTable.setPlaceholder(new Label(resources.getString("table.emptyText")));
	}

	@FXML
	public void getImagesFromDirectory(ActionEvent event) {

		File selectedDirectory = selectDirectory();
		if (selectedDirectory != null) {
			setDirectoryDescription(selectedDirectory);
			loadImagesFromPath(selectedDirectory.toPath());
		}
	}

	private void setDirectoryDescription(File selectedDirectory) {
		selectDirLabel.setText(selectDirLabel.getText() + " " + selectedDirectory.getPath());
	}

	private File selectDirectory() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Open Resource File");
		File selectedDirectory = directoryChooser.showDialog(null);
		return selectedDirectory;
	}

	private void loadImagesFromPath(Path imagesDirPath) {
		List<Path> imagesPathList = new ArrayList<Path>();

		try (Stream<Path> find = Files.find(imagesDirPath, 1, (path, attr) -> String.valueOf(path).contains(".jpg")
				|| String.valueOf(path).contains(".png") || String.valueOf(path).contains(".jpeg"))) {
			imagesPathList = find.collect(Collectors.toList());

		} catch (IOException e) {

			e.printStackTrace();
		}
		List<ImageVO> imageVOList = new ArrayList<ImageVO>();
		for (Path imagePath : imagesPathList) {
			ImageVO createdImage = createImageVO(imagePath);
			imageVOList.add(createdImage);
		}

		model.setResult(imageVOList);
	}

	private ImageVO createImageVO(Path imagePath) {
		ImageVO createdImage = new ImageVO();
		createdImage.setName(getImageName(imagePath));
		createdImage.setPath(imagePath.toString());
		return createdImage;
	}

	private String getImageName(Path imagePath) {
		String[] imagePathSplitted = imagePath.toString().split("\\\\");
		return imagePathSplitted[imagePathSplitted.length - 1];
	}

	@FXML
	public void showImage(MouseEvent event) {
		showImage();
	}

	private void showImage() {
		if (!model.getResult().isEmpty()) {
			try {
				final Image image = loadImage();
				imageView.setImage(image);

			} catch (MalformedURLException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	private Image loadImage() throws MalformedURLException {
		File file = new File(resultTable.getSelectionModel().getSelectedItem().getPath());
		String localUrl = file.toURI().toURL().toString();
		final Image image = new Image(localUrl);
		return image;
	}

	@FXML
	public void clearButtonAction(ActionEvent event) {
		clearResultTable();
		addDefaultImage();
	}

	private void clearResultTable() {
		model.setResult(new ArrayList<ImageVO>());
		selectDirLabel.setText(resources.getString("label.selectDir"));
	}

	@FXML
	public void showImageKeyBoard(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			showImage();
		}
	}

}
