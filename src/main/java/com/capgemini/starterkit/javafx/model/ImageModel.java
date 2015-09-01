package com.capgemini.starterkit.javafx.model;
import java.util.ArrayList;
import java.util.List;

import com.capgemini.starterkit.javafx.dataprovider.data.ImageVO;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class ImageModel {

	private final StringProperty name = new SimpleStringProperty();
	private final ListProperty<ImageVO> result = new SimpleListProperty<>(
			FXCollections.observableList(new ArrayList<ImageVO>()));


	public final String getName() {
		return name.get();
	}

	public final void setName(String value) {
		name.set(value);
	}

	public StringProperty nameProperty() {
		return name;
	}


	public final List<ImageVO> getResult() {
		return result.get();
	}

	public final void setResult(List<ImageVO> value) {
		result.setAll(value);
	}

	public ListProperty<ImageVO> resultProperty() {
		return result;
	}
}




