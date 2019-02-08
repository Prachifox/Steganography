package com.stegnography.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.stegnography.domain.Audio;

public interface AudioRepository extends CrudRepository< Audio , Long >{
	Optional<Audio> findById(Long user_id);
}
