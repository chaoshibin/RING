package com.ring.core.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 功能描述:
 * <p/>
 *
 * @author chaoshibin 新增日期：2017/10/1
 * @author chaoshibin 修改日期：2017/10/1
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Mapper<entity, example> {
    int countByExample(example example);

    int deleteByExample(example example);

    int deleteByPrimaryKey(String id);

    int insert(entity record);

    int insertSelective(entity record);

    List<entity> selectByExample(example example);

    entity selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") entity record, @Param("example") example example);

    int updateByExample(@Param("record") entity record, @Param("example") example example);

    int updateByPrimaryKeySelective(entity record);

    int updateByPrimaryKey(entity record);

    List<entity> selectByMap(Map<String, Object> map);

    int batchInsert(@Param("list") List<entity> list);
}
