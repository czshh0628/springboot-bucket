package czs.coding.service.impl;

import czs.coding.entity.Product;
import czs.coding.mapper.ProductMapper;
import czs.coding.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author czs
 * @since 2023-09-06
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
