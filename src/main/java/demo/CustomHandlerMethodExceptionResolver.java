package demo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

/**
 * 예외가 발생한 {@link HandlerMethod} 메소드를 가져 오기 위해
 * {@link ExceptionHandlerExceptionResolver}를 커스터마이징 클래스
 * @author Lee su hong
 *
 */
public class CustomHandlerMethodExceptionResolver extends ExceptionHandlerExceptionResolver {

	public static final String ORIGINAL_OCCURRED_EXCEPTION_HANDLER_METHOD_ATTR = "__ORIGINAL_OCCURRED_EXCEPTION_HANDLER_METHOD";

	@Override
	protected ModelAndView doResolveHandlerMethodException(
			HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handlerMethod, Exception exception) {
		request.setAttribute(ORIGINAL_OCCURRED_EXCEPTION_HANDLER_METHOD_ATTR, handlerMethod);
		return super.doResolveHandlerMethodException(request, response,
				handlerMethod, exception);
	}

	@Override
	protected List<HandlerMethodArgumentResolver> getDefaultArgumentResolvers() {
		List<HandlerMethodArgumentResolver> defaultArgumentResolvers = super
				.getDefaultArgumentResolvers();
		defaultArgumentResolvers.add(new ExceptionTargetHandlerMethodArgumentResolver());
		return defaultArgumentResolvers;
	}
}
