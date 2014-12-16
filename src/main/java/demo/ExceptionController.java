package demo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

/**
 * 예외를 처리하기 위한 공통 컨트롤러
 * @author Lee su hong
 *
 */
@Controller
public class ExceptionController {

	@ResponseBody
	@RequestMapping("/exception")
	public HttpEntity<String> exception(WebRequest webRequest) {
		Exception e = (Exception) webRequest.getAttribute(
				ExceptionControllerAdvice.OCCURRED_EXCEPTION_INFORMATION_ATTR,
				RequestAttributes.SCOPE_REQUEST);

		HttpStatus httpStatus = (HttpStatus) webRequest.getAttribute(
				ExceptionControllerAdvice.OCCURRED_HTTP_STATUS_CODE_ATTR,
				RequestAttributes.SCOPE_REQUEST);

		String message = "예외가 발생했습니다.";
		if (e != null && StringUtils.hasText(e.getMessage())) {
			message = e.getMessage();
		}
		return new ResponseEntity<String>(getExceptionMessage(message), httpStatus);
	}

	private String getExceptionMessage(String exceptionMessage) {
		return "<html>"
		+ "<head><title>예외페이지</title></head>"
		+ "<body>"+ exceptionMessage +"</body>"
		+ "</html>";
	}
}