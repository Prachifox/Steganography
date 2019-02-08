package com.stegnography.imageService;

import java.io.IOException;

import com.stegnography.domain.Audio;

public interface AudioService {

	void save(Audio audio);
	
	String Encode(byte[] data, String User_id, String text, int increment) throws IOException;
	
	String Decode(byte[] data, String user_id, String password);
	
}
