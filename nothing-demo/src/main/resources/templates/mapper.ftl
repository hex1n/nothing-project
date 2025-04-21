package ${package};

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ${entityPackage}.${className};
import java.util.List;

@Mapper
public interface ${className}Mapper {
    // 单条插入
    int insert(${className} record);

    // 批量插入
    int batchInsert(@Param("list") List<${className}> list);

    // 批量插入或更新（ON DUPLICATE KEY UPDATE）
    int batchInsertOrUpdate(@Param("list") List<${className}> list);

    // 根据ID查询
    ${className} selectById(@Param("id") Long id);

    // 批量查询
    List<${className}> selectByIds(@Param("ids") List<Long> ids);

    // 条件查询
    List<${className}> selectByCondition(${className} condition);

    // 全字段更新
    int updateById(${className} record);
    
    // 选择性更新（仅更新非空字段）
    int updateSelectiveById(${className} record);

    // 批量更新
    int batchUpdate(@Param("list") List<${className}> list);

    // 删除
    int deleteById(@Param("id") Long id);

    // 批量删除
    int batchDelete(@Param("ids") List<Long> ids);
}