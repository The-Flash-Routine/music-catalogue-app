package com.ice.music.MusicCatalogue.exception;

import com.ice.music.MusicCatalogue.exception.code.ArtistOfTheDayExceptionCode;
import lombok.Getter;

@Getter
public class ArtistOfTheDayException extends RuntimeException{

    private String exceptionCode;

    public ArtistOfTheDayException(String message, ArtistOfTheDayExceptionCode exceptionCode, Throwable e){
        super(message,e);
        this.exceptionCode = exceptionCode.toString();
    }

    public ArtistOfTheDayException(String message, ArtistOfTheDayExceptionCode exceptionCode){
        super(message);
        this.exceptionCode = exceptionCode.toString();
    }
}
