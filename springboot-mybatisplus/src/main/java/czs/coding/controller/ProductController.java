package czs.coding.controller;

import czs.coding.entity.Product;
import czs.coding.service.IProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 乐观锁测试
 * </p>
 * <p>
 * 乐观锁实现流程
 * 1 取出记录时，获取当前version
 * SELECT id,`name`,price,`version` FROM product WHERE id=1
 * 2 更新时，version + 1，如果where语句中的version版本不对，则更新失败
 * UPDATE product SET price=price+50, `version`=`version` + 1 WHERE id=1 AND `version`=1
 *
 * @author czs
 * @since 2023-09-06
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private IProductService productService;

    /**
     * 错误的并发更新
     */
    @GetMapping("errConcurrentUpdate")
    public void errConcurrentUpdate() {
        // 1、小李
        Product p1 = productService.getById(1L);
        System.out.println("小李取出的价格：" + p1.getPrice());

        // 2、小王
        Product p2 = productService.getById(1L);
        System.out.println("小王取出的价格：" + p2.getPrice());

        // 3、小李将价格加了50元，存入了数据库
        p1.setPrice(p1.getPrice() + 50);
        productService.updateById(p1);
        System.out.println("小李修改结果");

        // 4、小王将商品减了30元，存入了数据库
        p2.setPrice(p2.getPrice() - 30);
        productService.updateById(p2);
        System.out.println("小王修改结果");

        //最后的结果
        Product p3 = productService.getById(1L);
        //价格覆盖，最后的结果：70
        System.out.println("最后的结果：" + p3.getPrice());
    }

    /**
     * 正确的并发更新
     */
    @GetMapping("rightConcurrentUpdate")
    public void rightConcurrentUpdate() {
        // 1、小李
        Product p1 = productService.getById(1L);
        System.out.println("小李取出的价格：" + p1.getPrice());

        // 2、小王
        Product p2 = productService.getById(1L);
        System.out.println("小王取出的价格：" + p2.getPrice());

        // 3、小李将价格加了50元，存入了数据库
        p1.setPrice(p1.getPrice() + 50);
        boolean b1 = productService.updateById(p1);
        System.out.println("小李修改结果:" + b1);

        // 4、小王将商品减了30元，存入了数据库
        p2.setPrice(p2.getPrice() - 30);
        boolean b2 = productService.updateById(p2);
        System.out.println("小王修改结果:" + b2);
        if (!b2) {
            //失败重试，重新获取version并更新
            p2 = productService.getById(1L);
            p2.setPrice(p2.getPrice() - 30);
            b2 = productService.updateById(p2);
        }
        System.out.println("小王修改重试的结果:" + b2);

        //最后的结果
        Product p3 = productService.getById(1L);
        //价格覆盖，最后的结果：70
        System.out.println("最后的结果：" + p3.getPrice());
    }
}
