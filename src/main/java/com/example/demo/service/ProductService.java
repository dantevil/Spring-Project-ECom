package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Product;
import com.example.demo.repo.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepo productRepo;

	public List<Product> getAllProducts() {
	
		return productRepo.findAll();
	}

	public Product getProductById(int id) {
		// TODO Auto-generated method stub
		return productRepo.findById(id).orElse(null);
	}

	public Product addProduct(Product product, MultipartFile image) throws IOException {
		// TODO Auto-generated method stub
		
		product.setImageName(image.getOriginalFilename());
		product.setImageType(image.getContentType());
		product.setImageData(image.getBytes());
		return productRepo.save(product);
	}

	public Product updateProduct(Product product, MultipartFile imageFile) throws IOException {
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		product.setImageData(imageFile.getBytes());
		return productRepo.save(product);
	}

	public void deleteProduct(int id) {
	productRepo.deleteById(id);
	}

	public List<Product> searchProducts(String keyword) {
		return productRepo.searchProducts(keyword);
	}

}