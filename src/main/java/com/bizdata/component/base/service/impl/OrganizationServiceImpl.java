package com.bizdata.component.base.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizdata.component.base.dao.SysOrganizationMapper;
import com.bizdata.component.base.dao.UserMapper;
import com.bizdata.component.base.entity.SysOrganization;
import com.bizdata.component.base.service.OrganizationService;

/**
 * service实现
 * 
 * @author 顾剑峰<br/>
 *         创建时间：2015年4月10日 下午3:50:55<br/>
 *         描述：
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {
	@Autowired
	private SysOrganizationMapper sysOrganizationMapper;

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public int insertOrganization(SysOrganization sysOrganization) {
		// 获取父元素
		SysOrganization parentOrganization = sysOrganizationMapper
				.selectByPrimaryKey(sysOrganization.getParent());
		// 统一增加新节点层级
		sysOrganization.setLevel(String.valueOf((Integer
				.parseInt(parentOrganization.getLevel().trim()) + 1)));
		// 设置当前子节点的父节点如果是叶子类型设置为不是叶子类型
		if (parentOrganization.getIsleaf()) {
			parentOrganization.setIsleaf(false);
			sysOrganizationMapper.updateByPrimaryKey(parentOrganization);
		}
		return sysOrganizationMapper.insert(sysOrganization);
	}

	@Override
	public int updateOrganization(SysOrganization sysOrganization) {
		return sysOrganizationMapper
				.updateByPrimaryKeySelective(sysOrganization);
	}

	@Override
	public void deleteOrganization(Long organizationId) {
		// 根据organizationId获取要删除的SysOrganization
		SysOrganization delSysOrganization = sysOrganizationMapper
				.selectByPrimaryKey(organizationId);
		// 根据id删除organization中记录
		sysOrganizationMapper.deleteByPrimaryKey(organizationId);
		// 根据父id字段查出孩子们
		List<SysOrganization> childrens = findChildens(organizationId);
		for (SysOrganization sysOrganization : childrens) {
			// 递归删除
			deleteOrganization(sysOrganization.getId());
		}
		// 根据parentId查询
		int count = sysOrganizationMapper
				.selectCountByParent(delSysOrganization.getParent());
		if (count < 1) {
			// 如果父节点下不存在子节点了，则设置父节点为叶子
			SysOrganization parentSysOrganization = sysOrganizationMapper
					.selectByPrimaryKey(delSysOrganization.getParent());
			if (null != parentSysOrganization) {
				parentSysOrganization.setIsleaf(true);
				sysOrganizationMapper
						.updateByPrimaryKeySelective(parentSysOrganization);
			}
		}
	}

	@Override
	public SysOrganization findOne(Long organizationId) {
		return sysOrganizationMapper.selectByPrimaryKey(organizationId);
	}

	@Override
	public List<SysOrganization> findAll() {
		return createFormatOrganizationList(sysOrganizationMapper.selectAll());
	}

	/**
	 * 创建符合jqgrid treegrid格式的资源列表
	 * 
	 * @param list
	 * @return
	 */
	private List<SysOrganization> createFormatOrganizationList(
			List<SysOrganization> list) {
		// 返回的list
		List<SysOrganization> rootArray = new ArrayList<SysOrganization>();
		for (SysOrganization organization : list) {
			if (organization.getParent() == 0) {
				rootArray.add(organization);
				rootArray.addAll(getNextLevelOrganization(organization, list));
			}
		}
		return rootArray;
	}

	/**
	 * 递归查找获取下一级的Organization
	 * 
	 * @return
	 */
	private List<SysOrganization> getNextLevelOrganization(
			SysOrganization currentNode, List<SysOrganization> list) {
		List<SysOrganization> temp_list = new ArrayList<SysOrganization>();
		for (SysOrganization newNode : list) {
			if (newNode.getParent() != null
					&& newNode.getParent().compareTo(currentNode.getId()) == 0) {
				temp_list.add(newNode);
				temp_list.addAll(getNextLevelOrganization(newNode, list));
			}
		}
		return temp_list;
	}

	@Override
	public List<SysOrganization> selectByCnd(
			SysOrganization excludeOraganization) {
		return sysOrganizationMapper.selectByCnd(excludeOraganization);
	}

	@Override
	public int selectChildrenCountById(Long id) {
		return sysOrganizationMapper.selectChildrenCountById(id);
	}

	@Override
	public List<SysOrganization> findChildens(Long parent_id) {
		return sysOrganizationMapper.findChildens(parent_id);
	}

	@Override
	public boolean canDeleteOrganization(Long id) {
		boolean state=true;
		List<Long> ids=new ArrayList<Long>();
		ids.add(id);
		ids.addAll(getWantDeleteId(id));
		for (Long temp_id : ids) {
			int count=userMapper.seleteCountByOrganizationId(temp_id);
			if(count>0){
				state=false;
				break;
			}
		}
		return state;
	}

	/**
	 * 获取需要删除的id的孩子的id
	 * 
	 * @return
	 */
	private List<Long> getWantDeleteId(Long id) {
		List<Long> temp_ids = new ArrayList<Long>();
		// 根据父id字段查出孩子们
		List<SysOrganization> childrens = findChildens(id);
		for (SysOrganization sysOrganization : childrens) {
			temp_ids.add(sysOrganization.getId());
			temp_ids.addAll(getWantDeleteId(sysOrganization.getId()));
		}
		return temp_ids;
	}
}
