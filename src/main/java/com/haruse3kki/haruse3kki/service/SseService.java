package com.haruse3kki.haruse3kki.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseService {
    private final Map<Long, SseEmitter> emitters=new ConcurrentHashMap<>();

    public SseEmitter subscribe(Long userId){
        SseEmitter emitter=new SseEmitter(Long.MAX_VALUE);
        emitters.put(userId, emitter);

        //연결 끊기면 Map에서 제거
        emitter.onCompletion(()->emitters.remove(userId));
        emitter.onTimeout(()->emitters.remove(userId));
        emitter.onError(e->emitters.remove(userId));

        return emitter;
    }

    public void sendNotification(Long userId,String message){
        SseEmitter emitter=emitters.get(userId);
        if(emitter!=null){
            try{
                emitter.send(SseEmitter.event().data(message));
            }catch(IOException e){
                emitters.remove(userId);
            }
        }
    }
}
