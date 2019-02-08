package com.stegnography.imageService.imageServiceimpl;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.stegnography.Repository.imagesRepository;
import com.stegnography.domain.images;
import com.stegnography.imageService.imageService;

@Service
public class imagesServiceimpl implements imageService{

	@Autowired
	private imagesRepository imagesrepo;
	
	@Override
	public void save(images img) {
       imagesrepo.save(img);
	}

	@Override
	public File convert(MultipartFile file) throws IOException{
		    File convFile = new File(file.getOriginalFilename());
		    convFile.createNewFile();
		    FileOutputStream fos = new FileOutputStream(convFile);
		    fos.write(file.getBytes());
		    fos.close();
		    return convFile;
	} 
	
	@Override
	public byte[] fileresigedbytearray(File image) throws Exception
	{
		        //file to bufferedimage
				byte[] bytes = null;
		    	FileInputStream fis = new FileInputStream(image);  
		    	BufferedImage original = ImageIO.read(fis);
		    	
		    	// resized
		    	BufferedImage resized = new BufferedImage(200, 200, original.getType());
		    	Graphics2D g2 = resized.createGraphics();
		    	g2.drawImage(original, 0, 0, 200, 200, null);
		    	g2.dispose();
		    	
		    	//bufferedImage to ByteArray
		    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    	ImageIO.write(resized, "png", baos );
		    	baos.flush();
		    	bytes = baos.toByteArray();
		    	return bytes;
	}
	
	@Override
	public void upload(String name,byte[] bytes) throws Exception
	{
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream( new File("src/main/resources/static/mainimages/" + name)));
    	stream.write(bytes);
        stream.close();
	}

	@Override
	public BufferedImage toBufferedImage(byte[] bytes) {
		  ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		    try {
		        return ImageIO.read(bais);
		    } catch (IOException e) {
		        throw new RuntimeException(e);
		    }
	}
	
	

	@Override
	public int join(int main, int help, int a, int b) {
		
		int mainone = ((int)(Math.pow(2, a-b+1)) - 1) << b;
		//System.out.println(Integer.toBinaryString(mainone));
		
		int mainpart = (mainone & main) >> b;
		//System.out.println(Integer.toBinaryString(mainpart));
		
		int helppart = (help & mainone);
		//System.out.println(Integer.toBinaryString(helppart));
		
		int pixel = mainpart | helppart;
		//System.out.println(Integer.toBinaryString(pixel));
        return pixel;
	}

	@Override
	public int forone(int main, int help) {
        return join(main,help,7,4);
	}

	@Override
	public int fortwo(int main, int help, int k) {
		if(k == 1)
		{
			return join(main, help , 7 , 4);
		}
		else
			return join(main , help , 3 , 0);
		
	}

	@Override
	public int forfour(int main, int help, int k) {
		return join(main , help , 9 - 2*k , 8 - 2*k);
	}

	@Override
	public int foreight(int main, int help, int k) {
		return join(main , help , 8 - k , 8 - k);
	}

	@Override
	public int getRed(int pixel) {
		return ( pixel >> 16 ) & 0xff;	
	}

	@Override
	public int getGreen(int pixel) {
		 return ( pixel >>8 ) & 0xff;
	}

	@Override
	public int getBlue(int pixel) {
		return pixel & 0xff ;
	}

	@Override
	public int makepixel(int a, int b, int c, int d) {
		
		//System.out.println(Integer.toBinaryString(a));
		//System.out.println(Integer.toBinaryString(b));
		//System.out.println(Integer.toBinaryString(c));
		//System.out.println(Integer.toBinaryString(d));
		int temp = a;
		
		temp = (temp << 8) | b;
		
		temp = (temp << 8) | c;
		
		temp = (temp << 8) | d;
		
		System.out.println(Integer.toBinaryString(temp));
		
		return temp;
	}

	@Override
	public byte[] tobytes(BufferedImage img) throws IOException {
		byte[] bytes = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	ImageIO.write(img, "png", baos );
    	baos.flush();
    	bytes = baos.toByteArray();
    	return bytes;
	}

}
