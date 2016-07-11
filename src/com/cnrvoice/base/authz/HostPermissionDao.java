package com.cnrvoice.base.authz;

public interface HostPermissionDao
{
	boolean isAuthIp(String urlUuid, String method, String appKey, String ipAddr);
}
