package com.ice.music.MusicCatalogue.exception;

import com.ice.music.MusicCatalogue.exception.code.TrackExceptionCode;
import lombok.Getter;

@Getter
public class TrackException extends RuntimeException{

    private String exceptionCode;

    public TrackException(String message, TrackExceptionCode exceptionCode, Throwable e){
        super(message,e);
        this.exceptionCode = exceptionCode.toString();
    }

    public TrackException(String message, TrackExceptionCode exceptionCode){
        super(message);
        this.exceptionCode = exceptionCode.toString();
    }
}
