package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

public interface PostService {
	// create resource
	PostDto createPost(PostDto postDto);

	// get all posts
	PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

	// get post by id
	PostDto getPostById(long id);

	// update post by id
	PostDto updatePost(PostDto postDto, long id);

	// delete post by id
	void deletePostByid(long id);

	List<PostDto> getPostsByCategory(Long categoryId);
}
