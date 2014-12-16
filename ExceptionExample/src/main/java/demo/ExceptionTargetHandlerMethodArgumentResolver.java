package demo;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * {@link HandlerMethod} 를 파라메터로 가져 오기 위한 ArgumentResolver
 * @author Lee su hong
 *
 */
public class ExceptionTargetHandlerMethodArgumentResolver implements
		HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (HandlerMethod.class.equals(parameter.getParameterType())) {
			return true;
		}
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		return (HandlerMethod) webRequest.getAttribute(
						CustomHandlerMethodExceptionResolver.ORIGINAL_OCCURRED_EXCEPTION_HANDLER_METHOD_ATTR,
						RequestAttributes.SCOPE_REQUEST);
	}
}