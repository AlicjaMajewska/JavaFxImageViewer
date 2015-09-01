package com.capgemini.starterkit.javafx.dataprovider;

import java.util.Collection;

import com.capgemini.starterkit.javafx.dataprovider.data.ImageVO;
import com.capgemini.starterkit.javafx.dataprovider.impl.ImageDataProviderImpl;

public interface ImageDataProvider {

	ImageDataProvider INSTANCE = new ImageDataProviderImpl();
	Collection<ImageVO> findImages(String name);

}
