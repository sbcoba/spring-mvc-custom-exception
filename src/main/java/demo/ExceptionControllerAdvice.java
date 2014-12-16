package demo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;

/**
 * 예외를 처리 하기 위한 모든 예외를 처리하기 위한 ControllerAdvice
 * @author Lee su hong
 *
 */
@Controller
@ControllerAdvice
public class ExceptionControllerAdvice {
	public static final String OCCURRED_EXCEPTION_INFORMATION_ATTR = "__OCCURRED_EXCEPTION_INFORMATION";
	public static final String OCCURRED_HTTP_STATUS_CODE_ATTR = "__OCCURRED_HTTP_STATUS_CODE";

	/**
	 * 발생한 예외 정보를 받아 {@link ExceptionController} 포워딩 한다.
	 * @param e 발생된 예외
	 * @param handlerMethod 예외가 발생된 메소드 ( {@link ExceptionTargetHandlerMethodArgumentResolver} 에서 처리
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Object exception(Throwable e, HandlerMethod handlerMethod, HttpServletRequest request) {
		ResponseBody methodAnnotation = handlerMethod.getMethodAnnotation(ResponseBody.class);
		HttpStatus statusCode = getStatusCode(e);

		// 해당 조건은 필요에 따라 변경
		if (methodAnnotation != null) {
			return new ResponseEntity<ResponseDTO>(
					new ResponseDTO(Integer.toString(statusCode.value()),
							e.getMessage()), statusCode);
		}

		// 예외를 그대로 넘길 수도 있지만 여러가지 랩핑 시켜서 DTO를 생성하는 방법도 있음
		request.setAttribute(OCCURRED_EXCEPTION_INFORMATION_ATTR, e);
		// 예외에 따라 status코드를 다이나믹하게 변경 필요
		request.setAttribute(OCCURRED_HTTP_STATUS_CODE_ATTR, statusCode);
		return "forward:/exception";
	}

	/**
	 * 예외를 통해 status 코드를 결정
	 * @param e
	 * @return
	 */
	protected HttpStatus getStatusCode(Throwable e) {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public static class ResponseDTO {
    	String status;
    	String message;
		public ResponseDTO(String status, String message) {
			super();
			this.status = status;
			this.message = message;
		}
		public String getStatus() {
			return status;
		}
		public String getMessage() {
			return message;
		}
    }
}

