package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts() {
		return new ResponseEntity<>(productService.getAllProducts(),HttpStatus.OK);
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable int id){
		
		Product product = productService.getProductById(id);
		if(product.getId() > 0) {
			return new ResponseEntity<>(product,HttpStatus.OK);	
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		}
	}
	
	@GetMapping
	public ResponseEntity<byte[]>getProductImageById(@PathVariable int id ){
		
		Product product = productService.getProductById(id);
		if(product.getId() > 0) {
			return new ResponseEntity<>(product.getImageData(),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/products")
	public ResponseEntity<?> addProduct(@RequestPart Product product,@RequestPart MultipartFile imageFile){
		Product savedProduct;
		try {
			savedProduct = productService.addProduct(product,imageFile);
			return new ResponseEntity<>(savedProduct,HttpStatus.CREATED);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping("/products/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile){
		Product updateProduct = null;
		try {
			updateProduct = productService.updateProduct(product,imageFile);
			return new ResponseEntity<>(updateProduct,HttpStatus.OK);
		}catch(IOException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable int id){
		Product product = productService.getProductById(id);
		if(product != null) {
		 productService.deleteProduct(id);
		return new ResponseEntity<>("Deleted",HttpStatus.OK);
			
		}else {
			return new ResponseEntity<>("Deleted",HttpStatus.NOT_FOUND);		
		}
	}
	
	@GetMapping("/products/search")
	public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword){
		List<Product> products = productService.searchProducts(keyword);
		return new ResponseEntity<>(products,HttpStatus.OK);
	}
	
	
}
