package com.seedshare.controller.post;

import org.springframework.http.ResponseEntity;

import com.seedshare.controller.BasicController;
import com.seedshare.entity.post.Post;

/**
 * Controller interface of {@link com.seedshare.entity.post.Post}
 * 
 * @author joao.silva
 * @author gabriel.schneider
 */
public interface PostController extends BasicController<Post, Long>{

	ResponseEntity<?> findAllByPage(Integer page, Integer size);

	ResponseEntity<?> findAllByUser(Long id, Integer page, Integer size);

	ResponseEntity<?> findAllBySpecies(Long id, Integer page, Integer size);
	
	ResponseEntity<?> findAllByState(Long id, Integer page, Integer size);
	
	ResponseEntity<?> findAllByCity(Long id, Integer page, Integer size);

}