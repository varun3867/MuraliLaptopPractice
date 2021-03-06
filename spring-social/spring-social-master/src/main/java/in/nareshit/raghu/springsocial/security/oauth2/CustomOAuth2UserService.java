package in.nareshit.raghu.springsocial.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import in.nareshit.raghu.springsocial.exception.OAuth2AuthenticationProcessingException;
import in.nareshit.raghu.springsocial.model.AuthProvider;
import in.nareshit.raghu.springsocial.model.User;
import in.nareshit.raghu.springsocial.repository.UserRepository;
import in.nareshit.raghu.springsocial.security.UserPrincipal;
import in.nareshit.raghu.springsocial.security.oauth2.user.OAuth2UserInfo;
import in.nareshit.raghu.springsocial.security.oauth2.user.OAuth2UserInfoFactory;

import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
       
    	System.out.println("---------------------------Entered into CustomOAuth2UserService --> loadUser()-----------------------");
    	System.out.println("--------------Details of oAuth2UserRequest----------------");
    	System.out.println(" client ID --> "+oAuth2UserRequest.getClientRegistration().getClientId());
    	System.out.println(" client Name --> "+oAuth2UserRequest.getClientRegistration().getClientName());
    	System.out.println(" client Secret --> "+oAuth2UserRequest.getClientRegistration().getClientSecret());
    	System.out.println(" Redirection Template --> "+oAuth2UserRequest.getClientRegistration().getRedirectUriTemplate());
    	System.out.println(" REgistration ID --> "+oAuth2UserRequest.getClientRegistration().getRegistrationId());
    	System.out.println(" Scopes --> "+oAuth2UserRequest.getClientRegistration().getScopes());
    	System.out.println(" Authorization URI --> "+oAuth2UserRequest.getClientRegistration().getProviderDetails().getAuthorizationUri());
    	System.out.println("-------------------------------------------------------------------------------------------------------------------------_");
    	System.out.println();
    	System.out.println("-----------------------------clientRegistration Details-----------------------------------------------------");
    	System.out.println(oAuth2UserRequest.getClientRegistration().toString());
    	System.out.println("---------------------------------------------------------------------------------------------------------------------");
    	System.out.println();
    	OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
    	System.out.println("---------------Details of oAuth2User which is out of super.loadUser()---------------");
    	Map<String, Object> attributes = oAuth2User.getAttributes();
    	System.out.println("attributes --> "+attributes);
    	
    	String name = oAuth2User.getName();
    	System.out.println("name --> "+name);
    	
    	System.out.println("---------------------------------------------------------------------------------------");
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        System.out.println("Entered into process)Auth2User..............");
    	OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        String tokenValue = oAuth2UserRequest.getAccessToken().getTokenValue();
        System.out.println("[varun] Registration ID : "+registrationId);
        System.out.println("[varun] Token Value : "+tokenValue);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        System.out.println("[varun] oauth2user attributes"+attributes);
        
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
            if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();

        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        System.out.println("New User Registration Done................");
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setName(oAuth2UserInfo.getName());
        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }

}
