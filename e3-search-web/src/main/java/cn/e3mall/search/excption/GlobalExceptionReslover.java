package cn.e3mall.search.excption;

import jdk.nashorn.internal.runtime.FindProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class GlobalExceptionReslover implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionReslover.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
//        打印控制台
        e.printStackTrace();
//        写日志文件,根据系统级别写入日志文件
//        debug<info<error
//        级别越低打印的越多
        logger.debug("测试输出的日志");
        logger.info("系统发生异常了");
        logger.error("系统发生异常",e);
//        发邮件,短信
//        使用jmail发送邮件,发短信使用第三方webservice
//        展示错误页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/exception");
        return modelAndView;
    }
}
