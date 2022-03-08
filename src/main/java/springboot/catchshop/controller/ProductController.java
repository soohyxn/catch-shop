package springboot.catchshop.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springboot.catchshop.domain.Product;
import springboot.catchshop.dto.PageRequestDTO;
import springboot.catchshop.dto.PageResultDTO;
import springboot.catchshop.dto.ProductDTO;
import springboot.catchshop.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Product 기능
     * author:김지연
     */
    //

    //상품 등록
    @PostMapping("products")
    public String createProduct(ProductDTO productDTO, RedirectAttributes redirectAttributes){
        Long productID = productService.addProduct(productDTO);
        redirectAttributes.addFlashAttribute("msg", productID);

        return "redirect:/products";
    }

    //상품 전체 조회
    @GetMapping("products")
    public void readProducts(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list : " + pageRequestDTO);
        PageResultDTO<ProductDTO, Product> result = productService.readProducts(pageRequestDTO);
        List<String> categoryNameList = new ArrayList<>();
        log.info("category :" + result.getDtoList().get(1).getCategory().getName());
//        for(int i = 0; i < result.getSize(); i++){
//            categoryNameList.add(result.getDtoList().get(i).getCategory().getName());
//        }
        model.addAttribute("result", result);
        //model.addAttribute("categoryname", categoryNameList);
    }



//    @GetMapping("/fragments/searchProduct")
//    public String searchProduct(@RequestParam(value = "keyword") String keyword, Model model){
//        List<ProductDTO> productDTOList = productService.searchProducts(keyword);
//        model.addAttribute("result", productDTOList);
//        return "/products";
//    }
    //상품 개별 조회
    //products/{id}로 설정하면 static에 있는 리소스들을 못읽음-> 원인찾아보기
    //나중에 restful하게 수정예정
//    @GetMapping("products/{id}")
//    public String readSingleProduct(@PathVariable Long id, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
//        ProductDTO dto = productService.readSingleProduct(id);
//        model.addAttribute("dto", dto);
//        return "/single-product";
//    }
    //상품 수정
//    @PutMapping("products/{id}")
//    public void updateProduct(){
//
//    }

    @GetMapping("single-product") //나중에 상세상품-> 관리자 모드로 들어가면 수정도 할 수 있도록 변경하기
    public void readSingleProduct(long id, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        ProductDTO dto = productService.readSingleProduct(id);
        model.addAttribute("dto", dto);
    }


    
    //상품 삭제
    @DeleteMapping("products/{id}")
    public void deleteProduct(long id, RedirectAttributes redirectAttributes){
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("msg", id);
    }



    /**
     * review 기능
     * author:김지연
     */
    //review 목록 조회
    @GetMapping("reviews")
    public String readReview() {
        log.info("================review============");
        return "review";
    }
}
