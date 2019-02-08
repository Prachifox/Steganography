package com.stegnography.imageService.imageServiceimpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stegnography.Repository.AudioRepository;
import com.stegnography.domain.Audio;
import com.stegnography.imageService.AudioService;


@Service
public class AudioServiceImpl implements AudioService {

	@Autowired
	private AudioRepository audiorepo;
	
	@Override
	public void save(Audio audio) {
	   audiorepo.save(audio);
	}
	
	private ArrayList<ArrayList<String>> database = new ArrayList<ArrayList<String>>();

    private String function(int x){
        // any random function
        Integer res = x*7 + 55;
        return res.toString();
    }

    private int inverse_function(String x){
        // inverse of above function
        int res = (Integer.parseInt(x) - 55)/7;
        return res;
    }

	@Override
	public String Encode(byte[] data, String User_id, String text, int increment) throws IOException {
		    byte[] foo = text.getBytes();
	        int j = 0;
	      
	        for(int i = 0; i < data.length; i+= increment){
	            if(j == text.length())
	                break;
	            // do not modify first 44 bytes
	            if(i > 44){
	                data[i] = foo[j];
	                j++;
	            }
	        }
	        int k = j;
	        for(int i = 0; i < data.length; i+= increment){
	            if(k == 0)
	                break;
	            if(i > 44){
	                k--;
	            }
	        }
	        
	        File file2 = new File("src/main/resources/static/Audio/" + User_id + ".WAV" );
	        FileOutputStream out = new FileOutputStream(file2);
	        PrintWriter writer = new PrintWriter(file2);
	        writer.print("");
	        writer.close();

	        file2 = new File("src/main/resources/static/Audio/" + User_id + ".WAV" );
	        out = new FileOutputStream(file2);

	        out.write(data);
	        out.close();
	        System.out.println("Encoded");
	       // ArrayList<String> keys = new ArrayList<String>();
	        // first one stores length, 2nd one increment, 3rd one stores random_key
	        String password = UUID.randomUUID().toString();
	        Optional<Audio> audio = audiorepo.findById(Long.parseLong(User_id));
	        Audio audi = audio.get();
	        
	        audi.setJ(function(j));
	        audi.setIncrement(function(increment));
	        audi.setUserid_password(User_id + password);
	        
	        audiorepo.save(audi);
	        
//	        keys.add(function(j));
//	        keys.add(function(increment));
//	        keys.add(User_id + password); // stupid, but works
	       // database.add(keys);
	        return password;
	}
	
	@Override
	 public String Decode(byte[] data, String user_id, String password){
		
	        ArrayList<Byte> val = new ArrayList<Byte>();
	        String search = user_id + password;
	        boolean matches = false;
	        int increment = 0;
	        int key_len = 0;
	        
	        Optional<Audio> audio = audiorepo.findById(Long.parseLong(user_id));
	        
	        Audio audi = audio.get();
	        
	       
	            if(audi.getUserid_password().equals(user_id + password)){
	                matches = true;
	                increment = inverse_function(audi.getIncrement());
	                key_len = inverse_function(audi.getJ());
	            }

	        if(! matches){
	            return "Failed. The Username or Password is incorrect";
	        }
	        for(int i = 0; i < data.length; i+= increment){
	            if(key_len == 0)
	                break;
	            if(i > 44){
	                val.add(data[i]);
	                key_len--;
	            }
	        }
	        Byte[] ans = val.toArray(new Byte[val.size()]);
	        byte[] res = new byte[ans.length];
	        for(int i = 0; i < ans.length; i++)
	            res[i] = (byte) ans[i];
	        String result = new String(res);
	        return result;
	    }
}
