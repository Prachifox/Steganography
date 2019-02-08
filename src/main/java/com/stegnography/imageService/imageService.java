package com.stegnography.imageService;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

import org.springframework.web.multipart.MultipartFile;

import com.stegnography.domain.images;

public interface imageService {

	void save(images img);
	
	File convert(MultipartFile file) throws IOException;
	
    byte[] fileresigedbytearray(File image) throws Exception;
    
    byte[] tobytes(BufferedImage img) throws IOException;
    
    void upload(String name,byte[] bytes) throws Exception;
    
    BufferedImage toBufferedImage(byte[] bytes);
    
    int join(int main,int help,int a,int b);
    
    int forone(int main,int help);
    
    int fortwo(int main,int help , int k);
    
    int forfour(int main,int help , int k);
    
    int foreight(int main,int help , int k);
    
    int getRed(int pixel);
    
    int getGreen(int pixel);
    
    int getBlue(int pixel);
    
    int makepixel(int a,int b,int c,int d);
}
