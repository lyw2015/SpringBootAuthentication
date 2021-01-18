package org.laiyw.act.seven.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/14 10:51
 * @Description TODO
 */
public class IpAddressUtils {

    private static final String UN_KNOWN = "unknown";

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-real-ip");
            if (ipAddress == null || ipAddress.length() == 0 || UN_KNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("x-forwarded-for");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UN_KNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UN_KNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UN_KNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }
}
