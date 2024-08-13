package com.springboot.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "CRUD REST APIs for Post resource")
public class PostController {

	private PostService postService;

	// if only one constructor we can omit @Autowired annotation
	public PostController(PostService postService) {
		this.postService = postService;
	}

	@Operation(summary = "Create Post REST API", description = "Create Post REST API is used to save post into database")
	@ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
	@SecurityRequirement(name = "Bearer Authentication")
	// create blog post rest api
	@PreAuthorize("hasRole('ADMIN')") // for role based access to api
	@PostMapping
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}

	@Operation(summary = "Get All Posts REST API", description = "Get All Posts REST API is used to get all posts from the database")
	@ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
	// get all posts rest api
	// http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title&sortDir=asc
	@GetMapping
	public PostResponse getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
	}

	@Operation(summary = "Get Post By Id REST API", description = "Get Post By Id REST API is used to get single post from the database")
	@ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
	// get post using id
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostByid(@PathVariable(name = "id") long id) {
		return ResponseEntity.ok(postService.getPostById(id));
	}

	@Operation(summary = "Update Post REST API", description = "Update Post REST API is used to update a particular post in the database")
	@ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
	@SecurityRequirement(name = "Bearer Authentication")
	// update post using id
	@PreAuthorize("hasRole('ADMIN')") // for role based access to api
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id) {
		PostDto postResponse = postService.updatePost(postDto, id);
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}

	@Operation(summary = "Delete Post REST API", description = "Delete Post REST API is used to delete a particular post from the database")
	@ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
	@SecurityRequirement(name = "Bearer Authentication")
	// delete post using id
	@PreAuthorize("hasRole('ADMIN')") // for role based access to api
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
		postService.deletePostByid(id);
		return new ResponseEntity<>("Post Entity Deleted successfully!", HttpStatus.OK);
	}

	// getPostsByCategory rest api
	@GetMapping("/category/{id}")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") Long categoryId) {
		List<PostDto> postDtos = postService.getPostsByCategory(categoryId);
		return ResponseEntity.ok(postDtos);
	}

}
