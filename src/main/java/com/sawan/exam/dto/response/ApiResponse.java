package com.sawan.exam.dto.response;

public class ApiResponse<T> {
	  private boolean success;
	    private String message;
	    private T data;
	    
	    
	    
	    
		public ApiResponse() {
			super();
			
		}
		public ApiResponse(boolean success, String message,T data) {
			super();
			this.success = success;
			this.message = message;
			this.data = data;
		}
		public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}

		public T getData() {
			return data;
		}
		public void setData(T data) {
			this.data = data;
		}
		@Override
		public String toString() {
			return "ApiResponse [success=" + success + ", message=" + message + ", data=" + data
					+ "]";
		}
	    
	    
		
	    
	    
}
