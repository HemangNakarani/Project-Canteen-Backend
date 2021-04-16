package io.sen.canteenia.configuration;

import io.sen.canteenia.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RmeSessionChannelInterceptor implements ChannelInterceptor {

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        MessageHeaders headers = message.getHeaders();

        SimpMessageType type = (SimpMessageType) headers.get("simpMessageType");

        if (type.equals(SimpMessageType.CONNECT)) {

            MultiValueMap<String, String> multiValueMap = headers.get(StompHeaderAccessor.NATIVE_HEADERS, MultiValueMap.class);

            List<String> tokenList = multiValueMap.getOrDefault("X-Authorization", Arrays.asList(""));

            String token = parseJwt(tokenList.get(0));

            if (token.equals("")) {
                throw new SecurityException("TOKEN IS NOT PRESENT");
            }

            if (!jwtUtils.validateJwtToken(token)) {
                throw new SecurityException("INVALID TOKEN");
            }
        }
        return message;
    }

    private String parseJwt(String token) {
        if (token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return "";
    }
}