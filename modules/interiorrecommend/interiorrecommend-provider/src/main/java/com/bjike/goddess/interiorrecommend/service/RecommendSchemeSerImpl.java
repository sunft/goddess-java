package com.bjike.goddess.interiorrecommend.service;

import com.bjike.goddess.common.api.dto.Restrict;
import com.bjike.goddess.common.api.exception.SerException;
import com.bjike.goddess.common.jpa.service.ServiceImpl;
import com.bjike.goddess.common.utils.bean.BeanTransform;
import com.bjike.goddess.interiorrecommend.bo.RecommendSchemeBO;
import com.bjike.goddess.interiorrecommend.dto.RecommendSchemeDTO;
import com.bjike.goddess.interiorrecommend.entity.RecommendScheme;
import com.bjike.goddess.interiorrecommend.to.RecommendSchemeTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 推荐方案业务实现
 *
 * @Author: [ Jason ]
 * @Date: [ 2017-04-09 10:31 ]
 * @Description: [ 推荐方案业务实现 ]
 * @Version: [ v1.0.0 ]
 * @Copy: [ com.bjike ]
 */
@CacheConfig(cacheNames = "interiorrecommendSerCache")
@Service
public class RecommendSchemeSerImpl extends ServiceImpl<RecommendScheme, RecommendSchemeDTO> implements RecommendSchemeSer {

 
    @Override
    public RecommendSchemeBO insertModel(RecommendSchemeTO to) throws SerException {
        RecommendScheme model = BeanTransform.copyProperties(to, RecommendScheme.class, true);
        super.save(model);
        to.setId(model.getId());
        return BeanTransform.copyProperties(to, RecommendSchemeBO.class);
    }

    @Override
    public RecommendSchemeBO updateModel(RecommendSchemeTO to) throws SerException {
        setUpdate(to);
        return BeanTransform.copyProperties(to, RecommendSchemeBO.class);
    }

    @Override
    public List<RecommendSchemeBO> pageList(RecommendSchemeDTO dto) throws SerException {
        dto.getSorts().add("createTime=desc");
        List<RecommendScheme> list = super.findByPage(dto);
        return BeanTransform.copyProperties(list, RecommendSchemeBO.class);
    }

    @Override
    public void resourcesAudit(RecommendSchemeTO to) throws SerException {
        setUpdate(to);
    }

    @Override
    public void operateAudit(RecommendSchemeTO to) throws SerException {
        setUpdate(to);
    }

    @Override
    public void generalAudit(RecommendSchemeTO to) throws SerException {
        setUpdate(to);
    }

    /**
     * 更新数据（编辑、审核）
     *
     * @param to 推荐方案
     */
    public void setUpdate(RecommendSchemeTO to) throws SerException {

        if (!StringUtils.isEmpty(to.getId())) {
            RecommendScheme model = super.findById(to.getId());
            if (model != null) {
                BeanTransform.copyProperties(to, model, true);
                model.setModifyTime(LocalDateTime.now());
                super.update(model);
            } else {
                throw new SerException("更新对象不能为空");
            }
        } else {
            throw new SerException("更新ID不能为空!");
        }
    }
}