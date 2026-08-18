package com.ice.music.MusicCatalogue.exception;

import com.ice.music.MusicCatalogue.exception.code.ArtistExceptionCode;
import lombok.Getter;

@Getter
public class ArtistException extends RuntimeException{

    private String exceptionCode;

    public ArtistException(String message, ArtistExceptionCode exceptionCode, Throwable e){
        super(message,e);
        this.exceptionCode = exceptionCode.toString();
    }

    public ArtistException(String message, ArtistExceptionCode exceptionCode){
        super(message);
        this.exceptionCode = exceptionCode.toString();
    }
}
