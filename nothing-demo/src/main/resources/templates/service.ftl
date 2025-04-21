package ${package};

import ${entityPackage}.${className};
import java.util.List;

public interface ${className}Service {
// 单条插入
int save(${className} record);

// 批量插入
int saveBatch(List<${className}> list);

// 批量插入或更新
int saveOrUpdateBatch(List<${className}> list);

// 根据ID查询
${className} getById(Long id);

// 批量查询
List<${className}> listByIds(List<Long> ids);

    // 条件查询
    List<${className}> listByCondition(${className} condition);

    // 更新
    int updateById(${className} record);

    // 批量更新
    int updateBatch(List<${className}> list);

    // 删除
    int removeById(Long id);

    // 批量删除
    int removeBatch(List<Long> ids);
        }