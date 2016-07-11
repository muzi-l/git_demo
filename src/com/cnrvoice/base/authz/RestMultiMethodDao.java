package com.cnrvoice.base.authz;

import java.util.Set;

public interface RestMultiMethodDao
{
	// 获取授权IP列表
	Set<String> getAuthorizedIpList(String appKey, String urlUuid);
}
