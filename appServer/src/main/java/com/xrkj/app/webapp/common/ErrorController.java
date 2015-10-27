package com.xrkj.app.webapp.common;

import java.util.Map;

import static com.xrkj.app.Constants.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.xrkj.app.Constants;
import com.xrkj.app.webapp.base.controller.BaseController;
import com.xrkj.app.webapp.common.viewObj.ErrorVo;

/**
 * <pre>
 * 用于发生异常时给web容器调用或请求转发
 * 返回JSON对象
 * </pre>
 * 
 * @author ww
 *
 */
@RestController
@RequestMapping(REQUEST_MAPPING_ERROR)
public class ErrorController extends BaseController {

    /**
     * 错误类型1
     */
    public static final int ERROR_TYPE = 1;

    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    /**
     * <pre>
     * HTTP 400 - 请求无效 
     * HTTP 401.1 - 未授权：登录失败 
     * HTTP 401.2 - 未授权：服务器配置问题导致登录失败 
     * HTTP 401.3 - ACL 禁止访问资源 
     * HTTP 401.4 - 未授权：授权被筛选器拒绝 
     * HTTP 401.5 - 未授权：ISAPI 或 CGI 授权失败  
     * HTTP 403 - 禁止访问 
     * HTTP 403 - 对 Internet 服务管理器 (HTML) 的访问仅限于 Localhost 
     * HTTP 403.1 禁止访问：禁止可执行访问 
     * HTTP 403.2 - 禁止访问：禁止读访问 
     * HTTP 403.3 - 禁止访问：禁止写访问 
     * HTTP 403.4 - 禁止访问：要求 SSL 
     * HTTP 403.5 - 禁止访问：要求 SSL 128 
     * HTTP 403.6 - 禁止访问：IP 地址被拒绝 
     * HTTP 403.7 - 禁止访问：要求客户证书 
     * HTTP 403.8 - 禁止访问：禁止站点访问 
     * HTTP 403.9 - 禁止访问：连接的用户过多 
     * HTTP 403.10 - 禁止访问：配置无效 
     * HTTP 403.11 - 禁止访问：密码更改 
     * HTTP 403.12 - 禁止访问：映射器拒绝访问 
     * HTTP 403.13 - 禁止访问：客户证书已被吊销 
     * HTTP 403.15 - 禁止访问：客户访问许可过多 
     * HTTP 403.16 - 禁止访问：客户证书不可信或者无效 
     * HTTP 403.17 - 禁止访问：客户证书已经到期或者尚未生效 
     * HTTP 404.1 - 无法找到 Web 站点 
     * HTTP 404 - 无法找到文件 
     * HTTP 405 - 资源被禁止 
     * HTTP 406 - 无法接受 
     * HTTP 407 - 要求代理身份验证 
     * HTTP 410 - 永远不可用 
     * HTTP 412 - 先决条件失败 
     * HTTP 414 - 请求 - URI 太长 
     * HTTP 500 - 内部服务器错误 
     * HTTP 500.100 - 内部服务器错误 - ASP 错误 
     * HTTP 500-11 服务器关闭 
     * HTTP 500-12 应用程序重新启动 
     * HTTP 500-13 - 服务器太忙 
     * HTTP 500-14 - 应用程序无效 
     * HTTP 500-15 - 不允许请求 global.asa 
     * Error 501 - 未实现 
     * HTTP 502 - 网关错误
     * </pre>
     */

    @RequestMapping("/unauthorized")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorVo erroUnauthorized(HttpServletRequest request, HttpServletResponse response) {
        // 是否需要记录日志？

        ErrorVo evo = new ErrorVo(ERROR_TYPE, Constants.ECODE_VIEW_AUTHENTICATION, "认证未通过，请先登录");

        return evo;
    }

    @RequestMapping("/forceHTTPS")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorVo errorForceHTTPS() {
        // 错误码随意先
        ErrorVo evo = new ErrorVo(ERROR_TYPE, Constants.ECODE_VIEW_FORCE_HTTPS, "请使用HTTPS");

        return evo;
    }

    @RequestMapping("/404")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorVo error404(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        // response.setStatus(404);

        // 国际化先不做

        // 错误码随意先
        ErrorVo evo = new ErrorVo(ERROR_TYPE, Constants.ECODE_VIEW_NOT_FOUND, "请求的资源不存在");

        return evo;
    }

    @RequestMapping("/405")
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorVo error405(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {

        ErrorVo evo = new ErrorVo(ERROR_TYPE, Constants.ECODE_VIEW_METHOD_NOT_ALLOWED, "请求的方法不支持");

        return evo;
    }

    @RequestMapping("/406")
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorVo error406(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {

        // 国际化先不做

        // 错误码随意先
        ErrorVo evo = new ErrorVo(ERROR_TYPE, Constants.ECODE_VIEW_NOT_FOUND, "不支持的类型");

        return evo;
    }

    @RequestMapping("/500")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorVo error500(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        logger.error("#### 注意 ####  发生了未处理的错误 500");
        // 这里应该记录请求路径，请求参数信息？

        // 国际化先不做
        ErrorVo evo = new ErrorVo(ERROR_TYPE, Constants.ECODE_VIEW_SERVER_ERROR, "服务器内部错误0");

        return evo;
    }

    @RequestMapping("/nullException")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorVo errorNullException(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        logger.error("#### 注意 ####  发生未捕获的空异常  nullException");
        // response.setStatus(500);

        ErrorVo evo = new ErrorVo(ERROR_TYPE, Constants.ECODE_VIEW_SERVER_ERROR, "服务器内部错误1");

        return evo;
    }

    @RequestMapping("/throwable")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorVo errorThrowable(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        logger.error("#### 注意 ####  发生未捕获的未知异常  throwable");

        // response.setStatus(500);

        ErrorVo evo = new ErrorVo(ERROR_TYPE, Constants.ECODE_VIEW_SERVER_ERROR, "服务器内部错误2");

        return evo;
    }
}
