package com.cnrvoice.cheba.service.biz;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cnrvoice.base.exception.UnCheckedException;
import com.cnrvoice.base.hibernate.po.generic.anno.GenericAnnoUuidHibernatePo;

/**
 * 对列表根据sort_no字段进行排序的工具类
 * 
 * @author Guohong Mao
 * 
 */
public class SortBiz<T extends GenericAnnoUuidHibernatePo> {
	/**
	 * 排序
	 * @param list 已存在的排序队列（即已经有sort_no的）
	 * @param t 待排实体对象
	 * @param toPosition 将要排到的位置
	 * @param maxSortNo 配置的排序队列的大小
	 * @return 排序队列
	 */
	public List<T> sort(List<T> list, T t, Integer toPosition, Integer maxSortNo) {
		// 队列为空
		if (null == list) {
			list = new ArrayList<T>();
		}
		Integer currrentMaxSortNo = list.size();
		
		// 普通排序
		// 1、判断输入的位置是否正确，若不正确，则抛出异常
		Integer upper = null;
		Integer lower = null;
		if (StringUtils.isNotBlank(t.getSortNo())) { // 已经在队列内
			if (currrentMaxSortNo == maxSortNo) { // 队列已经满了
				lower = 1;
				upper = maxSortNo;
			} else {
				lower = 1;
				upper = currrentMaxSortNo;
			}
		} else { // 不在队列内
			if (currrentMaxSortNo == maxSortNo) { // 队列已经满了
				lower = 1;
				upper = maxSortNo;
			} else {
				lower = 1;
				upper = currrentMaxSortNo + 1;
			}
		}
		
		if (toPosition >= lower && toPosition <= upper) {
			// 2、位置正确，进行排序
			String sortNoStr = t.getSortNo();
			if (StringUtils.isBlank(sortNoStr)) { // 没有排序号，从队列外移动到队列内
				if (toPosition > currrentMaxSortNo) { // 目标位置为空（仅存在一种情况：排到最末尾去）
					// 上移一位
					moveForward(1, currrentMaxSortNo, list);
					// 移动到目标位置（即最后一位）
					t.setSortNo("1");
				} else { // 目标位置非空
					if (currrentMaxSortNo == maxSortNo) { // 队列已满
						T oldest = getBySortNo(list, "1");
						// 将最后一位移除
						oldest.setSortNo(null);
						
						// 后移一位
						moveBackward(2,getSortNoByIndex(toPosition, currrentMaxSortNo),list);
						t.setSortNo(getSortNoByIndex(toPosition,currrentMaxSortNo).toString());
					} else { // 队列未满
						if (1 == toPosition) { // 直接移动到第一位
							t.setSortNo(new Integer(currrentMaxSortNo + 1).toString());
							// return;
						} else {
							// 上移一位
							moveForward(
									getSortNoByIndex(toPosition,
											currrentMaxSortNo) + 1,
									currrentMaxSortNo, list);
							t.setSortNo(new Integer(getSortNoByIndex(
									toPosition, currrentMaxSortNo) + 1)
									.toString());
						}
					}
				}
				// 添加进来
				list.add(t);
			} else { // 已经有排序号，队列内移动
				Integer sortNo = Integer.valueOf(sortNoStr);
				Integer sn = getSortNoByIndex(toPosition, currrentMaxSortNo);
				t = getBySortNo(list, t.getSortNo());

				if (sn == sortNo) { // 不用排
					// return;
					// do nothing
				} else if (sortNo > sn) {
					// [sn,sortNo)上移一位
					moveForward(sn, sortNo - 1, list);
				} else if (sortNo < sn) {
					// (sortNo,sn]下移一位
					moveBackward(sortNo + 1, sn, list);
				}
				t.setSortNo(sn.toString());
			}
			return list;
		} else {
			String[] params = { lower.toString(), upper.toString() };
			throw new UnCheckedException("biz.merch.110", params);
		}
	}
	
	/**
	 * 从队列中删除一个
	 * @param list 已存在的排序队列（即已经有sort_no的）
	 * @param t 待删除实体对象
	 * @param substitution 替补（替补需根据业务规则决定是否传入，如需传入，但是没有适合的替补则传入null即可）
	 * @return 排序队列
	 */
	public List<T> deleteFromQueue(List<T> list, T t, T substitution) {
		if (null == list) {
			return null;
		}
		if (null == t) {
			return list;
		}
		if (StringUtils.isNotBlank(t.getSortNo())) {
			// 暂存
			Integer sn = Integer.valueOf(t.getSortNo());
			for (T t2 : list) {
				if(StringUtils.isNotBlank(t2.getSortNo())){
					if (t2.getSortNo().equals(t.getSortNo())) {
						// 置空
						t2.setSortNo(null);
					}
				}
			}
			// 移动
			for (T t2 : list) {
				if (StringUtils.isNotBlank(t2.getSortNo())) {
					if (Integer.valueOf(t2.getSortNo()) > sn) {
						Integer sortNo = Integer.valueOf(t2.getSortNo());
						t2.setSortNo(new Integer(sortNo - 1).toString());
					}
				}
			}

			if (null != substitution) {
				substitution.setSortNo(String.valueOf(list.size()));
				list.add(substitution);
			}
		}
		return list;
	}

	public T getBySortNo(List<T> list, String sortNo) {
		if(null != list && !list.isEmpty()){
			for (T t : list) {
				if(StringUtils.isNotBlank(t.getSortNo())){
					if (sortNo.equals(t.getSortNo())) {
						return t;
					}
				}
			}
		}
		return null;
	}
	public void moveForward(Integer start, Integer end, List<T> list) {
		for (T t : list) {
			if(StringUtils.isNotBlank(t.getSortNo())){
				Integer sn = Integer.valueOf(t.getSortNo());
				if(null == start && null != end){
					if (sn < end) {
						t.setSortNo(new Integer(sn + 1).toString());
					}
				}
				if(null != start && null != end){
					if (sn >= start && sn <= end) {
						t.setSortNo(new Integer(sn + 1).toString());
					}
				}
			}
		}
	}
	public void moveBackward(Integer start, Integer end, List<T> list) {
		for (T t : list) {
			if(StringUtils.isNotBlank(t.getSortNo())){
				Integer sn = Integer.valueOf(t.getSortNo());
				if(null != start && null == end){
					if (sn > start) {
						t.setSortNo(new Integer(sn - 1).toString());
					}
				}
				if(null != start && null != end){
					if (sn >= start && sn <= end) {
						t.setSortNo(new Integer(sn - 1).toString());
					}
				}
			}
		}
	}
	
	/**
	 * 将实体添加到排序队列
	 * @param list 已存在的排序队列（即已经有sort_no的）
	 * @param t 待添加的对象
	 * @param currentMaxSortNo 当前最大排序号
	 * @param maxSortNo 配置的排序队列的大小
	 * @return 排序队列
	 */
	public List<T> save(List<T> list,T t,Integer currentMaxSortNo,Integer maxSortNo){
		if(currentMaxSortNo < maxSortNo){   // 队列未满
			Integer sn = currentMaxSortNo+1;
			t.setSortNo(sn.toString());
		} else {   // 队列已满
			// 将最老的那条数据从队列中剔除（sort_no=1）
			T oldest = getBySortNo(list, "1");
			oldest.setSortNo(null);
			
			// 向后移动
			moveBackward(2, maxSortNo, list);
			t.setSortNo(maxSortNo.toString());
		}
		list.add(t);
		return list;
	}
	
	/**
	 * 将实体添加到排序队列(放到最后一位)
	 * @param list 已存在的排序队列（即已经有sort_no的）
	 * @param t 待添加的对象
	 * @param currentMaxSortNo 当前最大排序号
	 * @param maxSortNo 配置的排序队列的大小
	 * @return 排序队列
	 */
	public List<T> save2(List<T> list,T t,Integer currentMaxSortNo,Integer maxSortNo){
		if(currentMaxSortNo < maxSortNo){   // 队列未满
			// 向前移动
			moveForward(1, maxSortNo, list);
			t.setSortNo("1");
		} else {   // 队列已满

		}
		list.add(t);
		return list;
	}
	
	/**
	 * 还原，将实体从回收站还原。（注意：此处只是将该实体加入到排序队列，业务属性（如is_deleted未设置））
	 * @param list 已存在的排序队列（即已经有sort_no的）
	 * @param t 待添加的对象
	 * @param currentMaxSortNo 当前最大排序号
	 * @param maxSortNo 配置的排序队列的大小
	 * @return 排序队列
	 */
	public List<T> revert(List<T> list,T t,Integer currentMaxSortNo,Integer maxSortNo){
		if(currentMaxSortNo < maxSortNo){   // 队列未满
			Integer sn = currentMaxSortNo+1;
			t.setSortNo(sn.toString());
		} else {   // 队列已满
			// 将最老的那条数据从队列中剔除（sort_no=1）
			T oldest = getBySortNo(list, "1");
			oldest.setSortNo(null);
			
			// 向后移动
			moveBackward(2, maxSortNo, list);
			t.setSortNo(maxSortNo.toString());
		}
		list.add(t);
		return list;
	}
	
	/**
	 * 通过索引号（即前端列表的序号）获取排序号
	 * @param index 索引号
	 * @param length 当前队列的实际大小
	 * @return 排序号
	 */
	public static Integer getSortNoByIndex(Integer index, Integer length) {
		return length + 1 - index;
	}
	/**
	 * 通过排序号获取索引号
	 * @param sortNo 排序号
	 * @param length 当前队列的实际大小
	 * @return 索引号
	 */
	public static Integer getIndexBySortNo(Integer sortNo, Integer length) {
		return length + 1 - sortNo;
	}
}
