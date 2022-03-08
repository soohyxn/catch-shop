package springboot.catchshop.service;

import springboot.catchshop.dto.CategoryDTO;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    //카테고리 등록
    public Long saveCategory (CategoryDTO categoryDTO);


    //카테고리 조회
    public List<CategoryDTO> getCategory(String name);

    //카테고리 삭제
    public void deleteCategory (Long id);

    //카테고리 수정
    public Long updateCategory (Long id, CategoryDTO categoryDTO);
}