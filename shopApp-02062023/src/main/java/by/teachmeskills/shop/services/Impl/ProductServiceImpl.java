package by.teachmeskills.shop.services.Impl;

import by.teachmeskills.shop.enums.PagesPathEnum;
import by.teachmeskills.shop.enums.RequestParamsEnum;
import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.repositories.ProductRepository;
import by.teachmeskills.shop.services.ImageService;
import by.teachmeskills.shop.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

import static by.teachmeskills.shop.enums.RequestParamsEnum.IMAGES;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ImageService imageService;

    public ProductServiceImpl(ProductRepository productRepository, ImageService imageService) {
        this.productRepository = productRepository;
        this.imageService = imageService;
    }

    @Override
    public Product create(Product entity) {
        return productRepository.create(entity);
    }

    @Override
    public List<Product> read() {
        return productRepository.read();
    }

    @Override
    public Product update(Product entity) {
        return productRepository.update(entity);
    }

    @Override
    public void delete(int id) {
        productRepository.delete(id);
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getProductsByCategoryId(int categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> getProductsBySearchParameter(String parameter) {
        return productRepository.findBySearchParameter(parameter);
    }

    @Override
    public ModelAndView getProductData(int id) {
        ModelMap model = new ModelMap();
        Product product = productRepository.findById(id);
        if (Optional.ofNullable(product).isPresent()) {
            model.addAttribute(RequestParamsEnum.PRODUCT.getValue(), product);
            model.addAttribute(IMAGES.getValue(), imageService.getImagesByProductId(id));
        }
        return new ModelAndView(PagesPathEnum.PRODUCT_PAGE.getPath(), model);
    }
}
