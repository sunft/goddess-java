package com.bjike.goddess.attainment.service;

import com.bjike.goddess.attainment.bo.SurveyActualizeBO;
import com.bjike.goddess.attainment.dto.SurveyActualizeDTO;
import com.bjike.goddess.attainment.entity.SurveyActualize;
import com.bjike.goddess.attainment.enums.SurveyStatus;
import com.bjike.goddess.attainment.to.SurveyActualizeTO;
import com.bjike.goddess.common.api.exception.SerException;
import com.bjike.goddess.common.jpa.service.ServiceImpl;
import com.bjike.goddess.common.utils.bean.BeanTransform;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 调研实施记录业务实现
 *
 * @Author: [ dengjunren ]
 * @Date: [ 2017-04-06 10:58 ]
 * @Description: [ 调研实施记录业务实现 ]
 * @Version: [ v1.0.0 ]
 * @Copy: [ com.bjike ]
 */
@CacheConfig(cacheNames = "attainmentSerCache")
@Service
public class SurveyActualizeSerImpl extends ServiceImpl<SurveyActualize, SurveyActualizeDTO> implements SurveyActualizeSer {

    @Autowired
    private SurveyPlanSer surveyPlanSer;

    private SurveyActualizeBO transformBO(SurveyActualize entity) throws SerException {
        SurveyActualizeBO bo = BeanTransform.copyProperties(surveyPlanSer.findBOById(entity.getPlan().getId()), SurveyActualizeBO.class);
        bo.setPlan_id(entity.getPlan().getId());
        bo.setStart(bo.getStartTime());
        bo.setEnd(bo.getEndTime());
        bo.setFinish(bo.getFinishTime());
        BeanTransform.copyProperties(entity, bo, true);
        return bo;
    }

    @Transactional(rollbackFor = SerException.class)
    @Override
    public SurveyActualizeBO save(SurveyActualizeTO to) throws SerException {
        SurveyActualize entity = BeanTransform.copyProperties(to, SurveyActualize.class, true);
        entity.setPlan(surveyPlanSer.findById(to.getPlan_id()));
        entity.setStartTime(LocalDateTime.now());
        entity.setSurvey(SurveyStatus.UNDERWAY);
        super.save(entity);
        return this.transformBO(entity);
    }

    @Transactional(rollbackFor = SerException.class)
    @Override
    public SurveyActualizeBO update(SurveyActualizeTO to) throws SerException {
        if (StringUtils.isNotBlank(to.getId())) {
            try {
                SurveyActualize entity = super.findById(to.getId());
                BeanTransform.copyProperties(to, entity, true);
                entity.setModifyTime(LocalDateTime.now());
                super.update(entity);
                return this.transformBO(entity);
            } catch (SerException e) {
                throw new SerException("数据对象不能为空");
            }
        } else
            throw new SerException("数据ID不能为空");
    }

    @Transactional(rollbackFor = SerException.class)
    @Override
    public SurveyActualizeBO delete(String id) throws SerException {
        SurveyActualize entity = super.findById(id);
        super.remove(entity);
        return this.transformBO(entity);
    }

    @Transactional(rollbackFor = SerException.class)
    @Override
    public SurveyActualizeBO over(String id) throws SerException {
        SurveyActualize entity = super.findById(id);
        entity.setSurvey(SurveyStatus.FINISH);
        entity.setEndTime(LocalDateTime.now());
        super.update(entity);
        return this.transformBO(entity);
    }
}