package com.milo.amz.review.service.impl;

import com.milo.amz.review.service.ProductService;
import com.milo.amz.review.domain.Product;
import com.milo.amz.review.repository.ProductRepository;
import com.milo.amz.review.service.dto.ProductDTO;
import com.milo.amz.review.service.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Product.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    
    @Inject
    private ProductRepository productRepository;

    @Inject
    private ProductMapper productMapper;

    /**
     * Save a product.
     *
     * @param productDTO the entity to save
     * @return the persisted entity
     */
    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        Product product = productMapper.productDTOToProduct(productDTO);
        product = productRepository.save(product);
        ProductDTO result = productMapper.productToProductDTO(product);
        return result;
    }

    /**
     *  Get all the products.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        Page<Product> result = productRepository.findAll(pageable);
        return result.map(product -> productMapper.productToProductDTO(product));
    }

    /**
     *  Get one product by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ProductDTO findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        Product product = productRepository.findOne(id);
        ProductDTO productDTO = productMapper.productToProductDTO(product);
        return productDTO;
    }

    /**
     *  Delete the  product by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.delete(id);
    }

	@Override
	public List<ProductDTO> findAll() {
		return productMapper.productsToProductDTOs(productRepository.findAll());
	}
}
