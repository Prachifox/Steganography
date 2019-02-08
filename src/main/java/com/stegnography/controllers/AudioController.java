package com.stegnography.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.stegnography.domain.Audio;
import com.stegnography.imageService.AudioService;
import com.stegnography.imageService.imageService;
import com.stegnography.utility.MailConstructor;

@Controller
public class AudioController {
	
	@Autowired
	private imageService imageservice;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailConstructor mailconstructer;
	
	@Autowired
    private AudioService audioservice;
	
	@GetMapping("/audio")
	public String audioenc(Model model)
	{
		Audio audio = new Audio();
		model.addAttribute("Audio",audio);
		return "audio";
	}
	
	@GetMapping("/audioenc_confermation")
	public String conferm(
			@RequestParam("email") String email,
			Model model
			)
	{
		model.addAttribute("email",email);
		return "audioenc_confermation";
	}
	
	@PostMapping("/audioenc")
	public String audioenc(
			@ModelAttribute("audio") Audio audio,
			@RequestParam("message") String message,
			@RequestParam("email") String email,
			Model model
			) throws IOException
	{	
		audioservice.save(audio);
		
		File file = imageservice.convert(audio.getAudio());
		
		byte[] data = new byte[(int) file.length()];
		
		FileInputStream in = new FileInputStream(file);
		in.read(data);in.close();
		
		String userid = audio.getId().toString();
		
		int increment = 55;
		String mypassword = audioservice.Encode(data , userid , message , increment);
		
		model.addAttribute("email",email);
		
		mailSender.send(mailconstructer.constructOrderConfirmationEmail1(userid, mypassword , email));
		return "redirect:/audioenc_confermation?email="+email;
	}
	
	@GetMapping("/audiodecoding")
	public String decodeaudio(
			@RequestParam("id") String userid,
			Model model
			)
	{
		model.addAttribute("id",userid);
		return "decodeaudio";
	}
	
	@PostMapping("/decode")
	public String decode(
			@RequestParam("user_id") String User_id,
			@RequestParam("password") String pass,
			Model model
			) throws IOException
	{
		File file2 = new File("src/main/resources/static/Audio/" + User_id + ".WAV");	
		byte[] data1 = new byte[(int) file2.length()];
	    FileInputStream in  = new FileInputStream(file2);
	    in.read(data1);
	    String decodedmsg = audioservice.Decode(data1, User_id, pass);
	    model.addAttribute("decodedmsg" , decodedmsg);
	     
	    return "decoded_msg_viewer";
	}
}
