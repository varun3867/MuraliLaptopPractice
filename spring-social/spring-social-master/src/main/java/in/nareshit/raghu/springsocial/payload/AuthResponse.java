package in.nareshit.raghu.springsocial.payload;

public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    public AuthResponse(String accessToken) {
    	System.out.println("AuthResponse.AuthResponse()");
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

	@Override
	public String toString() {
		return "AuthResponse [accessToken=" + accessToken + ", tokenType=" + tokenType + "]";
	}
    
    
}
