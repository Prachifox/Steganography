package com.stegnography.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class Audio {

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long Id;
	
	@Transient
	private MultipartFile audio;
	
	private String j;
	
	private String increment;
	
	private String userid_password;
	
	

	public String getJ() {
		return j;
	}

	public void setJ(String j) {
		this.j = j;
	}

	public String getIncrement() {
		return increment;
	}

	public void setIncrement(String increment) {
		this.increment = increment;
	}

	public String getUserid_password() {
		return userid_password;
	}

	public void setUserid_password(String userid_password) {
		this.userid_password = userid_password;
	}

	public Long getId() {
		return Id;
	}

	public MultipartFile getAudio() {
		return audio;
	}

	public void setAudio(MultipartFile audio) {
		this.audio = audio;
	}

	public void setId(Long id) {
		Id = id;
	}

	
	
}
