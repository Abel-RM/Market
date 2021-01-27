package com.abel.market.persistence;

import com.abel.market.domain.Product;
import com.abel.market.domain.repository.ProductRepository;
import com.abel.market.persistence.crud.ProductoCrudRepository;
import com.abel.market.persistence.entity.Producto;
import com.abel.market.persistence.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductoRepository implements ProductRepository {
    private ProductoCrudRepository productoCrudRepository;
    private ProductMapper mapper;

    public ProductoRepository(ProductoCrudRepository productCrudRepository, ProductMapper mapper) {
        this.productoCrudRepository = productCrudRepository;
        this.mapper = mapper;
    }



    @Override
    public List<Product> getAll(){
        List<Producto> productos =(List<Producto>) productoCrudRepository.findAll();
        return mapper.toProducts(productos);
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List<Producto> productos = productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId);
        return Optional.of(mapper.toProducts(productos));
    }

    @Override
    public Optional<List<Product>> getScarseProducts(int quantity) {
        Optional<List<Producto>> productos = productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity,true);
        return productos.map(prods -> mapper.toProducts(prods));
    }

    @Override
    public Optional<Product> getProduct(int productId) {
        return productoCrudRepository.findById(productId).map(producto -> mapper.toProduct(producto));
    }

    @Override
    public Product save(Product product) {
        Producto producto = mapper.toProducto(product);
        return mapper.toProduct(productoCrudRepository.save(producto));
    }

    public void delete(int productId){
        productoCrudRepository.deleteById(productId);
    }
}
