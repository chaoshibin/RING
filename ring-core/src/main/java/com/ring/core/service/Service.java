package com.ring.core.service;

import java.util.List;

/**
 * 功能描述:
 * <p/>
 *
 * @author chaoshibin 新增日期：2017/10/1
 * @author chaoshibin 修改日期：2017/10/1
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Service<entity, example> {
    int countByExample(example example);

    int deleteByExample(example example);

    int deleteByPrimaryKey(String id);

    int insert(entity record);

    int insertSelective(entity record);

    List<entity> selectByExample(example example);

    entity selectByPrimaryKey(String id);

    int updateByExampleSelective(entity record, example example);

    int updateByExample(entity record, example example);

    int updateByPrimaryKeySelective(entity record);

    int updateByPrimaryKey(entity record);

    List<entity> queryPageByExample(example example, int pageNum, int pageSize);

    List<entity> queryPageByExample(example example, int pageNum, int pageSize, String orderBy);

    int batchInsert(List<entity> list);

    List<entity> selectAll(entity entity);
}
